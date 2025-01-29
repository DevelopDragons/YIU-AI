/** @jsxImportSource @emotion/react */
import { css } from "@emotion/react";
import { colors } from "../../assets/styles/colors";
import SideMenu from "../../components/Menu/SideMenu";
import { navItems } from "../../models/menu";
import { Outlet } from "react-router-dom";
import PageHeader from "../../components/Text/PageHeader";
import TitleBgImg from "../../assets/images/PageHeader/news.jpg";
import { useResponsive } from "../../hooks/ResponsiveContext";

const NewsPage = (): React.ReactElement => {
  const { isMobile, isNotMobile, isTablet, isDesktopOrLaptop } =
    useResponsive();
  return (
    <div>
      <PageHeader backgroundImage={TitleBgImg} title={"학부소식"} />
      <div
        css={css({
          flex: 1, // 사이드바를 제외한 나머지 공간을 차지하도록 설정
          padding: "20px", // 내용에 여백 추가
          // backgroundColor: colors.gray.light, // 배경 색상 설정
        })}
      >
        <Outlet /> {/* 해당 경로에 맞는 하위 페이지가 여기에 렌더링됩니다 */}
      </div>
    </div>
  );
};

export default NewsPage;
