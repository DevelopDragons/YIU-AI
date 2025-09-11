/** @jsxImportSource @emotion/react */
import { css } from "@emotion/react";
import { Paper, Typography } from "@mui/material";
import { useNavigate } from "react-router-dom";
import { News } from "../../../models/news";
import { useQuery } from "@tanstack/react-query";
import { defaultAPI } from "../../../services";
import LoadingSpin from "../../../components/Spin/LoadingSpin";
import GetDataErrorResultView from "../../../components/Result/GetDataError";
import SmallButton from "../../../components/Button/SmallButton";
import dayjs from "dayjs";
import { border1 } from "../../../assets/styles/borderLine";
import { colors } from "../../../assets/styles/colors";
import SectionTitle from "../../../components/Text/SectionTitle";
import { selectedNewsState } from "../../../recoil/news";
import { useSetRecoilState } from "recoil";

const NewsList = (): React.ReactElement => {
  const navigate = useNavigate();

  const setSelectedNews = useSetRecoilState(selectedNewsState);

  const moveToForm = (item: News | null) => {
    setSelectedNews(item);
    navigate(`/admin/news/form`);
  };

  const {
    data: news,
    isLoading,
    error,
  } = useQuery<News[]>({
    queryKey: ["news"],
    queryFn: async () => {
      const res = await defaultAPI.get(`/news`);
      return res.data.slice().reverse();
    },
  });

  if (isLoading) return <LoadingSpin />;
  if (error) return <GetDataErrorResultView />;

  return (
    <div css={css({ margin: "50px 30px" })}>
      {/* 상단 헤더 */}
      <div
        css={css({
          display: "flex",
          justifyContent: "space-between",
          alignItems: "center",
          marginBottom: 15,
          marginLeft: 10,
          marginRight: 10,
        })}
      >
        <SectionTitle title="학과 뉴스" />
        <SmallButton
          title="등록"
          onClick={() => moveToForm(null)}
          colored={true}
        />
      </div>

      {/* 뉴스 리스트 */}
      <Paper
        elevation={0}
        css={css({
          display: "flex",
          flexDirection: "column",
          padding: "10px 15px",
          borderRadius: 15,
        })}
      >
        {/* 리스트 헤더 */}
        <div
          css={css({
            display: "grid",
            gridTemplateColumns: "60px 1fr 100px",
            gap: 10,
            padding: 10,
            paddingBottom: 15,
            borderBottom: border1,
            "@media (max-width: 600px)": {
              display: "none",
            },
          })}
        >
          <Typography variant="body2" fontWeight={700}>
            번호
          </Typography>
          <Typography variant="body2" fontWeight={700}>
            제목
          </Typography>
          <Typography
            variant="body2"
            fontWeight={700}
            align="left"
            css={css({ whiteSpace: "nowrap" })}
          >
            날짜
          </Typography>
        </div>

        {/* 리스트 데이터 */}
        {news && news.length > 0 ? (
          news.map((item, index) => (
            <div
              key={item.id}
              onClick={() => moveToForm(item)}
              css={css({
                cursor: "pointer",
                padding: 10,
                borderRadius: 5,
                "&:not(:last-of-type)": {
                  borderBottom: "1px solid #f0f0f0",
                },
                "&:hover": {
                  transition: "all 0.2s",
                  backgroundColor: colors.gray.lightGray,
                },
                display: "grid",
                gridTemplateColumns: "60px 1fr 100px",
                gap: 10,
                alignItems: "center",
                "@media (max-width: 600px)": {
                  display: "flex",
                  flexDirection: "column",
                  alignItems: "flex-start",
                  gap: 0,
                },
              })}
            >
              {/* 번호 + 날짜 (모바일) */}
              <div
                css={css({
                  "@media (min-width: 601px)": {
                    display: "flex",
                    alignItems: "center",
                  },
                  "@media (max-width: 600px)": {
                    display: "flex",
                    justifyContent: "space-between",
                    width: "100%",
                    marginBottom: 5,
                  },
                })}
              >
                <Typography variant="body2" fontWeight={500}>
                  {String(index + 1).padStart(4, "0")}
                </Typography>
                <Typography
                  variant="body2"
                  color="text.secondary"
                  css={css({
                    display: "none",
                    "@media (max-width: 600px)": {
                      display: "block",
                      whiteSpace: "nowrap",
                    },
                  })}
                >
                  {dayjs(item.createAt).format("YYYY-MM-DD")}
                </Typography>
              </div>

              {/* 제목 + 단신 */}
              <div
                css={css({
                  overflow: "hidden",
                })}
              >
                <Typography
                  variant="body1"
                  fontWeight={600}
                  css={css({
                    whiteSpace: "nowrap",
                    overflow: "hidden",
                    textOverflow: "ellipsis",
                  })}
                >
                  {item.title}
                </Typography>
                <Typography
                  variant="body2"
                  color="text.secondary"
                  css={css({
                    paddingRight: 15,
                    fontSize: "0.85rem",
                    overflow: "hidden",
                    textOverflow: "ellipsis",
                    whiteSpace: "nowrap",
                    marginTop: 4,
                  })}
                >
                  {item.shorts}
                </Typography>
              </div>

              {/* 날짜 (데스크톱) */}
              <Typography
                variant="body2"
                color="text.secondary"
                align="left"
                css={css({
                  whiteSpace: "nowrap",
                  "@media (max-width: 600px)": {
                    display: "none",
                  },
                })}
              >
                {dayjs(item.createAt).format("YYYY-MM-DD")}
              </Typography>
            </div>
          ))
        ) : (
          <div
            css={css({
              padding: "20px 0",
              textAlign: "center",
              color: "#888",
            })}
          >
            <Typography variant="body1">등록된 뉴스가 없습니다.</Typography>
          </div>
        )}
      </Paper>
    </div>
  );
};

export default NewsList;
