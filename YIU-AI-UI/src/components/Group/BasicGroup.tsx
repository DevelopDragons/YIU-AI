/** @jsxImportSource @emotion/react */
import { css } from "@emotion/react";
import { colors } from "../../assets/styles/colors";
import TitleBgImg from "../../assets/images/page_title_bg_img.jpg";
import { useResponsive } from "../../hooks/ResponsiveContext";
import altImg from "../../assets/images/alt_professor.jpg";

interface BasicGroupProps {
  title: string;
  contents?: string;
}

const BasicGroup = ({
  title,
  contents,
}: BasicGroupProps): React.ReactElement => {
  // 반응형 화면
  const { isMobile, isNotMobile, isTablet, isDesktopOrLaptop } =
    useResponsive();

  return (
    <div
      css={css({
        display: "flex",
        flexDirection: "column",
        justifyContent: "start",
        alignItems: "start",
        gap: 20,
      })}
    >
      <div
        css={css({
          fontWeight: 800,
          fontSize: 22,
          color: colors.gray.black,
          paddingBottom: 5,
          borderBottom: `2px solid ${colors.yiu.green}`,
        })}
      >
        {title}
      </div>
      {contents && (
        <div
          css={css({
            fontWeight: 500,
            fontSize: 16,
            color: colors.text.black,
          })}
        >
          {contents}
        </div>
      )}
    </div>
  );
};

export default BasicGroup;
