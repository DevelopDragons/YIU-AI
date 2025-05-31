/** @jsxImportSource @emotion/react */
import { css } from "@emotion/react";
import { colors } from "../../assets/styles/colors";
import main_background_img from "../../assets/images/main_background_img.jpeg";
import yiuInfo from "../../assets/data/yiu_info";
import { yiuAiInfo } from "../../assets/data/yiu_ai_info";
import { useResponsive } from "../../hooks/ResponsiveContext";

const MainCarousel = (): React.ReactElement => {
  const { isMobile, isNotMobile, isTablet, isDesktopOrLaptop } =
    useResponsive();

  return (
    <div
      css={css({
        position: "relative", // 자식 div의 위치를 절대적으로 설정할 수 있게
        height: 400,
        width: "100%",
        backgroundImage: `url(${main_background_img})`,
        backgroundSize: "cover",
        backgroundPosition: "center",
      })}
    >
      <div
        css={css({
          position: "absolute", // 부모 div와 동일한 위치에 텍스트 div를 배치
          top: 0,
          left: 0,
          right: 0,
          bottom: 0,
          display: "flex",
          flexDirection: "column",
          justifyContent: "center", // 텍스트를 아래로 정렬
          gap: 10,
          color: colors.gray.white,
          paddingLeft: isMobile ? "5%" : "15%",
          backgroundColor: "rgba(0, 0, 0, 0.2)",
        })}
      >
        <div
          css={css({
            fontSize: 60,
            fontFamily: "Arvo Bold",
          })}
        >
          {yiuInfo.name_en}
        </div>
        <div
          css={css({
            fontSize: 40,
            fontWeight: 700,
          })}
        >
          {yiuAiInfo.engName}
        </div>
      </div>
    </div>
  );
};

export default MainCarousel;
