/** @jsxImportSource @emotion/react */
import { css } from "@emotion/react";
import { colors } from "../../assets/styles/colors";
import { News } from "../../models/news";
import dayjs from "dayjs";

interface NewsNavButtonProps {
  type: "prev" | "next";
  newsItem: News;
  onClick: (item: News) => void;
}

const NewsNavButton: React.FC<NewsNavButtonProps> = ({
  type,
  newsItem,
  onClick,
}) => {
  const label = type === "prev" ? "▲ 이전글" : "▼ 다음글";
  return (
    <div
      css={css({
        display: "flex",
        justifyContent: "space-between",
        padding: 10,
        cursor: "pointer",
        "&:hover": { backgroundColor: colors.gray.brightLightGray },
      })}
      onClick={() => onClick(newsItem)}
    >
      <span>
        <span css={css({ marginRight: 10, color: colors.yiu.green })}>
          {label}
        </span>
        {newsItem.title}
      </span>
      <span>{dayjs(newsItem.createAt).format("YY.MM.DD")}</span>
    </div>
  );
};

export default NewsNavButton;
