/** @jsxImportSource @emotion/react */
import { css } from "@emotion/react";
import { colors } from "../../assets/styles/colors";
import main_background_img from "../../assets/images/main_background_img.jpeg";
import yiuInfo from "../../assets/data/yiu_info";
import { yiuAiInfo } from "../../assets/data/yiu_ai_info";
import { useResponsive } from "../../hooks/ResponsiveContext";
import MainCarousel from "./MainCarousel";
import MainNews from "./MainNews";
import MainLab from "./MainLab";
import MainMou from "./MainMOU";
import SectionTitle from "../../components/Text/SectionTitle";

const MainPage = (): React.ReactElement => {
  const { isMobile, isNotMobile, isTablet, isDesktopOrLaptop } =
    useResponsive();

  return (
    <div>
      <MainCarousel />
      <div
        css={css({
          margin: "0 auto",
          maxWidth: 1400,
          display: "flex",
          flexDirection: "column",
          gap: 100,
          paddingTop: 50,
          paddingBottom: 50,
          paddingLeft: isMobile ? 50 : 100,
          paddingRight: isMobile ? 50 : 100,
        })}
      >
        <MainNews />
        {/* <MainLab /> */}
        <MainMou />
      </div>
    </div>
  );
};

export default MainPage;
