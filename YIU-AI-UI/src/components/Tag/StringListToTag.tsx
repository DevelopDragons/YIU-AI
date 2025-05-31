/** @jsxImportSource @emotion/react */
import { css } from "@emotion/react";
import { colors } from "../../assets/styles/colors";
import TitleBgImg from "../../assets/images/page_title_bg_img.jpg";
import { useResponsive } from "../../hooks/ResponsiveContext";
import altImg from "../../assets/images/alt_professor.jpg";
import { Member } from "../../models/member";

const StringListToTag = ({ str }: { str: string }): React.ReactElement => {
  const { isMobile, isTablet, isDesktopOrLaptop } = useResponsive();

  return (
    <div
      css={css({
        display: "flex",
        flexDirection: "row",
        justifyContent: "start",
        alignItems: "center",
        gap: 10,
        flexWrap: "wrap", // 🔥 줄바꿈 가능하도록 설정
        maxWidth: "100%", // 부모 크기를 벗어나지 않도록 설정
      })}
    >
      {str.split(",").map((item, index) => (
        <div
          key={index}
          css={css({
            backgroundColor: colors.gray.lightGray,
            color: colors.text.black,
            fontSize: 13,
            fontWeight: 600,
            padding: "5px 10px",
            whiteSpace: "nowrap", // 개별 태그 자체는 줄바꿈되지 않도록 함
            minWidth: "auto", // 최소 크기 자동 조정
            flexShrink: 1, // 내용이 많으면 자동으로 크기 조절
          })}
        >
          {item.trim()}
        </div>
      ))}
    </div>
  );
};

export default StringListToTag;
