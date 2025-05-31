/** @jsxImportSource @emotion/react */
import { css } from "@emotion/react";
import { colors } from "../../assets/styles/colors";
import { useResponsive } from "../../hooks/ResponsiveContext";
import { News } from "../../models/news";
import { border1 } from "../../assets/styles/borderLine";
import dayjs from "dayjs";

interface NewsListItemProps {
  item: News;
  onClick: () => void;
}

const NewsListItem = ({
  item,
  onClick,
}: NewsListItemProps): React.ReactElement => {
  // 반응형 화면
  const { isMobile, isNotMobile, isTablet, isDesktopOrLaptop } =
    useResponsive();

  return (
    <div
      css={css({
        display: "flex",
        flexDirection: isMobile ? "column" : "row",
        justifyContent: "start",
        alignItems: "center",
        gap: 30,
        padding: 30,
        borderBottom: border1,
        cursor: "pointer",
        ":hover": {
          backgroundColor: colors.gray.lightGray,
          transition: "all 0.2s",
        },
      })}
      onClick={onClick}
    >
      {/* 썸네일 */}
      <img
        // src={item.thumbnails}
        src={`${process.env.REACT_APP_URL}/files/show?id=${item.thumbnails?.[0]?.id}`}
        // src={boss}
        css={css({
          width: 300,
          objectFit: "contain",
          border: border1,
          alignSelf: "center",
        })}
      />
      {/* 제목 및 정보 */}
      <div
        css={css({
          display: "flex",
          flexDirection: "column",
          gap: 10,
          fontSize: 15,
          color: colors.text.black,
        })}
      >
        {/* 제목 */}
        <div
          css={css({
            fontSize: 20,
            fontWeight: 700,
            color: colors.yiu.green,
          })}
        >
          {item.title}
        </div>
        {/* 날짜 */}
        <div>{dayjs(item.createAt).format("YYYY-MM-DD")}</div>
        {/* 단신 */}
        <div css={css({ marginTop: 15 })}>{item.shorts}</div>
      </div>
    </div>
  );
};

export default NewsListItem;
