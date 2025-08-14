/** @jsxImportSource @emotion/react */
import { css } from "@emotion/react";
import { colors } from "../../assets/styles/colors";
import { useRecoilState } from "recoil";
import { SelectedNewsAtom } from "../../recoil/notice";
import dayjs from "dayjs";
import PageHeader from "../../components/Text/PageHeader";
import TitleBgImg from "../../assets/images/PageHeader/news.jpg";
import { useQuery } from "@tanstack/react-query";
import { defaultAPI } from "../../services";
import { News } from "../../models/news";
import { useNavigate } from "react-router-dom";
import ListIcon from "@mui/icons-material/List";
import CalendarMonthIcon from "@mui/icons-material/CalendarMonth";
import { border1 } from "../../assets/styles/borderLine";
import NewsNavButton from "../../components/Button/NewsNavButton";

const NewsDetailPage = (): React.ReactElement => {
  const [selectedNews, setSelectedNews] = useRecoilState(SelectedNewsAtom);
  const navigate = useNavigate();

  const { data: news } = useQuery<News[]>({
    queryKey: ["news"],
    queryFn: async () => {
      const res = await defaultAPI.get(`/news`);
      return res.data;
    },
  });

  if (!selectedNews) return <div>뉴스를 선택해주세요.</div>;

  const currentIndex = news?.findIndex((n) => n.id === selectedNews.id) ?? -1;
  const prevNews = currentIndex > 0 ? news?.[currentIndex - 1] : null;
  const nextNews =
    news && currentIndex < news.length - 1 ? news[currentIndex + 1] : null;

  return (
    <div>
      <PageHeader backgroundImage={TitleBgImg} title={"학부소식"} />

      <div
        css={css({
          padding: "5%",
          maxWidth: 1000,
          margin: "0 auto",
          display: "flex",
          flexDirection: "column",
          gap: 50,
          marginTop: 50,
          marginBottom: 100,
        })}
      >
        {/* 제목 */}
        <div
          css={css({
            fontSize: 30,
            color: colors.yiu.green,
            fontWeight: 700,
            textAlign: "center",
          })}
        >
          {selectedNews.title}
        </div>

        {/* 날짜 및 정보 */}
        <div
          css={css({
            display: "flex",
            flexDirection: "row",
            // justifyContent: "space-between",
            gap: 5,
            alignItems: "center",
            borderTop: border1,
            borderBottom: border1,
            paddingTop: 15,
            paddingBottom: 15,
            color: colors.gray.mediumGray,
          })}
        >
          <CalendarMonthIcon fontSize="small" />
          <span>
            {dayjs(selectedNews.createAt).format("YYYY-MM-DD hh:mm:ss")}
          </span>
        </div>
        {/* 썸네일 */}
        {selectedNews.thumbnails?.[0] && (
          <img
            src={`${process.env.REACT_APP_URL}/files/show?id=${selectedNews.thumbnails?.[0]?.id}`}
            // src={boss}
            css={css({
              width: "100%",
              maxWidth: 900,
              objectFit: "contain",
              border: border1,
              alignSelf: "center",
            })}
          />
        )}
        {/* 단신 */}
        <div
          css={css({
            backgroundColor: colors.gray.brightLightGray,
            color: colors.yiu.darkBlue,
            fontSize: 20,
            fontWeight: 700,
            padding: 20,
            textAlign: "center",
          })}
        >
          {selectedNews.shorts}
        </div>

        {/* 본문 */}
        <div
          css={css({
            fontSize: 16,
            color: colors.text.black,
            lineHeight: 1.5,
          })}
        >
          {selectedNews.contents}
        </div>
        {/* 이전/목록/다음 리스트 */}
        <div
          css={css({
            borderTop: border1,
            marginTop: 50,
            marginBottom: 50,
            display: "flex",
            flexDirection: "column",
            gap: 10,
            fontSize: 14,
            color: colors.gray.darkGray,
          })}
        >
          {/* 목록 버튼 (우측 정렬) */}
          <div
            css={css({
              alignSelf: "end",
              display: "flex",
              alignItems: "center",
              padding: 10,
              gap: 5,
              cursor: "pointer",
              color: colors.gray.mediumGray,
            })}
            onClick={() => navigate("/news")}
          >
            <ListIcon />
            <span>목록</span>
          </div>

          {/* 이전글 */}
          {prevNews && (
            <NewsNavButton
              type="prev"
              newsItem={prevNews}
              onClick={(item) => {
                setSelectedNews(item);
                navigate(`/news/:${item.id}`, { replace: true });
              }}
            />
          )}

          {/* 다음글 */}
          {nextNews && (
            <NewsNavButton
              type="next"
              newsItem={nextNews}
              onClick={(item) => {
                setSelectedNews(item);
                navigate(`/news/:${item.id}`, { replace: true });
              }}
            />
          )}
        </div>
      </div>
    </div>
  );
};

export default NewsDetailPage;
