/** @jsxImportSource @emotion/react */
import { css } from "@emotion/react";
import { colors } from "../../assets/styles/colors";
import TitleBgImg from "../../assets/images/page_title_bg_img.jpg";
import { useResponsive } from "../../hooks/ResponsiveContext";

interface TitleProps {
  icon: React.ReactElement;
  title: string; // 제목
}

const IconTextGroup = ({ icon, title }: TitleProps): React.ReactElement => {
  // 반응형 화면
  const { isMobile, isNotMobile, isTablet, isDesktopOrLaptop } =
    useResponsive();

  return (
    <div
      css={css({
        display: "flex",
        flexDirection: "row",
        alignItems: "center",
        gap: 10,
      })}
    >
      {icon}
      {title}
    </div>
  );
};

export default IconTextGroup;
