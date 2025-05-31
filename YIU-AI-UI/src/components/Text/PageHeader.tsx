/** @jsxImportSource @emotion/react */
import { css } from "@emotion/react";
import { colors } from "../../assets/styles/colors";
import TitleBgImg from "../../assets/images/page_title_bg_img.jpg";
import { useResponsive } from "../../hooks/ResponsiveContext";

interface TitleProps {
  backgroundImage: string; // 배경 이미지
  title: string; // 제목
}

const PageHeader = ({
  backgroundImage,
  title,
}: TitleProps): React.ReactElement => {
  // 반응형 화면
  const { isMobile, isNotMobile, isTablet, isDesktopOrLaptop } =
    useResponsive();

  return (
    <div>
      <div
        css={css({
          position: "relative", // 자식 div의 위치를 절대적으로 설정할 수 있게
          height: 300,
          width: "100%",
          backgroundImage: `url(${backgroundImage ?? TitleBgImg})`,
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
            paddingLeft: isMobile ? 0 : "15%",
            backgroundColor: "rgba(0, 0, 0, 0.5)",
          })}
        >
          <div
            css={css({
              fontSize: isMobile ? 50 : 70,
              fontWeight: 700,
              textAlign: isMobile ? "center" : "start",
            })}
          >
            {title}
          </div>
        </div>
      </div>
    </div>
  );
};

export default PageHeader;
