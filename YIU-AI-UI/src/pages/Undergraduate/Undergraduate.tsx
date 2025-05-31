/** @jsxImportSource @emotion/react */
import { css } from "@emotion/react";
import { colors } from "../../assets/styles/colors";
import TitleBgImg from "../../assets/images/PageHeader/student.jpg";
import PageHeader from "../../components/Text/PageHeader";
import { Tab, Tabs } from "@mui/material";
import { useState, useEffect } from "react";
import { useResponsive } from "../../hooks/ResponsiveContext";
import SideMenu from "../../components/Menu/SideMenu";
import { navItems } from "../../models/menu";
import { Outlet, useLocation, useNavigate } from "react-router-dom";

const UndergraduatePage = (): React.ReactElement => {
  // 반응형 화면
  const { isMobile, isNotMobile, isTablet, isDesktopOrLaptop } =
    useResponsive();
  const navigate = useNavigate();
  const location = useLocation();
  const subMenu = navItems[1].subMenu; // 학부 하위 메뉴 가져오기

  // 현재 URL에 맞게 초기 상태 설정
  const [value, setValue] = useState(location.pathname);

  useEffect(() => {
    setValue(location.pathname);
  }, [location.pathname]);

  const handleChange = (event: React.SyntheticEvent, newValue: string) => {
    setValue(newValue);
    navigate(newValue); // 페이지 이동
  };

  return (
    <div>
      <PageHeader backgroundImage={TitleBgImg} title={"학부"} />
      <div
        css={css({
          display: "flex",
          padding: "5%",
          maxWidth: 1400,
          margin: "0 auto",
        })}
      >
        {/* 사이드바 */}
        {isDesktopOrLaptop && <SideMenu menuItem={navItems[1]} />}

        {/* 메인 콘텐츠 */}
        <div css={css({ flex: 1 })}>
          <Outlet /> {/* 하위 페이지 렌더링 */}
        </div>
      </div>
    </div>
  );
};

export default UndergraduatePage;
