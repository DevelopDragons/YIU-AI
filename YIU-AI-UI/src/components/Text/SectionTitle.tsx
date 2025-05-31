/** @jsxImportSource @emotion/react */
import { css } from "@emotion/react";
import { colors } from "../../assets/styles/colors";
import TitleBgImg from "../../assets/images/page_title_bg_img.jpg";
import { useResponsive } from "../../hooks/ResponsiveContext";

interface TitleProps {
  title: string; // 제목
  size?: number;
  desc?: string;
  descSize?: number;
}

const SectionTitle = ({
  title,
  size,
  desc,
  descSize,
}: TitleProps): React.ReactElement => {
  // 반응형 화면
  const { isMobile, isNotMobile, isTablet, isDesktopOrLaptop } =
    useResponsive();

  const border = `5px solid ${colors.gray.brightGray}`;

  return (
    <div css={css({ display: "flex", flexDirection: "column", gap: 10 })}>
      <div
        css={css({
          fontSize: size ?? (isMobile ? 25 : 30),
          fontWeight: 800,
        })}
      >
        {title}
      </div>
      {desc && (
        <div
          css={css({
            fontSize: descSize ?? (isMobile ? 15 : 18),
            fontWeight: 500,
            color: colors.gray.mediumGray,
          })}
        >
          {desc}
        </div>
      )}
    </div>
  );
};

export default SectionTitle;
