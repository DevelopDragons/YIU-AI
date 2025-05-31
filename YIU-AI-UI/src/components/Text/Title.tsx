/** @jsxImportSource @emotion/react */
import { css } from "@emotion/react";
import { colors } from "../../assets/styles/colors";
import TitleBgImg from "../../assets/images/page_title_bg_img.jpg";
import { useResponsive } from "../../hooks/ResponsiveContext";
import { border5 } from "../../assets/styles/borderLine";

interface TitleProps {
  title: string; // 제목
}

const Title = ({ title }: TitleProps): React.ReactElement => {
  // 반응형 화면
  const { isMobile, isNotMobile, isTablet, isDesktopOrLaptop } =
    useResponsive();

  return (
    <div>
      <div
        css={css({
          fontSize: 40,
          fontWeight: 700,
          color: colors.yiu.green,
          paddingTop: 10,
          paddingBottom: 10,
          borderTop: border5,
          borderBottom: border5,
        })}
      >
        {title}
      </div>
    </div>
  );
};

export default Title;
