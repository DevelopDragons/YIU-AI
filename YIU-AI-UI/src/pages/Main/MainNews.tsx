import { useEffect, useRef, useState } from "react";
/** @jsxImportSource @emotion/react */
import { css } from "@emotion/react";
import { colors } from "../../assets/styles/colors";
import MainSectionHeader from "../../components/Group/MainSectionHeader";
import { border1 } from "../../assets/styles/borderLine";
import dayjs from "dayjs";
import { useNavigate } from "react-router-dom";
import { useResponsive } from "../../hooks/ResponsiveContext";
import { useRecoilState } from "recoil";
import { SelectedNewsAtom } from "../../recoil/notice";

import { useQuery } from "@tanstack/react-query";
import { defaultAPI } from "../../services";
import LoadingSpin from "../../components/Spin/LoadingSpin";
import GetDataErrorResultView from "../../components/Result/GetDataError";
import { News } from "../../models/news";

const CARD_MIN_WIDTH = 280;
const CARD_GAP = 20;

const MainNews = (): React.ReactElement => {
  const navigate = useNavigate();

  const { isMobile, isNotMobile, isTablet, isDesktopOrLaptop } =
    useResponsive();

  const [selectedNews, setSelectedNews] = useRecoilState(SelectedNewsAtom);

  const containerRef = useRef<HTMLDivElement>(null);
  const [visibleCardCount, setVisibleCardCount] = useState(1);

  useEffect(() => {
    const updateCardCount = () => {
      if (containerRef.current) {
        const containerWidth = containerRef.current.offsetWidth - 200;
        const count = Math.floor(
          (containerWidth + CARD_GAP) / (CARD_MIN_WIDTH + CARD_GAP)
        );
        setVisibleCardCount(Math.max(1, count));
      }
    };

    updateCardCount();
    window.addEventListener("resize", updateCardCount);
    return () => window.removeEventListener("resize", updateCardCount);
  }, []);

  // 학과 뉴스 데이터 가져오기
  const {
    data: news,
    isLoading,
    error,
  } = useQuery<News[]>({
    queryKey: ["news"],
    queryFn: async () => {
      const res = await defaultAPI.get(`/news`);
      return res.data;
    }
  });

  if (isLoading) return <LoadingSpin />;
  if (error) return <GetDataErrorResultView />

  return (
    <div
      css={css({
        display: "flex",
        flexDirection: "column",
        gap: 30,
      })}
    >
      <MainSectionHeader
        title={"학부 소식"}
        desc={"AI융합학부의 다양한 소식을 만나보세요."}
        buttonText="VIEW MORE"
        link="/news"
      />
      <div
        ref={containerRef}
        css={css({
          width: "100%",
          display: "grid",
          gridTemplateColumns: `repeat(${visibleCardCount}, 1fr)`,
          gap: CARD_GAP,
        })}
      >
        {news!.slice(0, visibleCardCount).map((item, index) => (
          <div
            key={index}
            css={css({
              minWidth: CARD_MIN_WIDTH,
              border: border1,
              borderRadius: 10,
              padding: 25,
              height: 250,
              display: "flex",
              flexDirection: "column",
              gap: 20,
              boxShadow: "0 4px 10px rgba(0,0,0,0.1)",
              cursor: "pointer",
            })}
            onClick={() => {
              setSelectedNews(item);
              navigate("/news"); // 먼저 스택에 /news 삽입
              navigate(`/news/${item.id}`);
            }}
          >
            <div css={css({ fontWeight: 650, color: colors.yiu.green })}>
              학부 뉴스
            </div>
            <div css={css({ fontSize: 20, fontWeight: 700 })}>{item.title}</div>
            <div
              css={css({
                overflow: "hidden",
                display: "-webkit-box",
                WebkitLineClamp: 4,
                WebkitBoxOrient: "vertical",
                fontSize: 16,
                fontWeight: 500,
                color: colors.gray.mediumGray,
              })}
            >
              {item.contents}
            </div>
            <div
              css={css({
                display: "flex",
                justifyContent: "flex-end",
                marginTop: "auto",
                color: colors.gray.mediumGray,
                fontWeight: 600,
              })}
            >
              {dayjs(item.createAt).format("YYYY-MM-DD")}
            </div>
          </div>
        ))}
      </div>
    </div>
  );
};

export default MainNews;
