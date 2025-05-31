/** @jsxImportSource @emotion/react */
import { css } from "@emotion/react";
import { colors } from "../../assets/styles/colors";
import TitleBgImg from "../../assets/images/PageHeader/member.jpg";
import PageHeader from "../../components/Text/PageHeader";
import { useState, useEffect } from "react";
import { useResponsive } from "../../hooks/ResponsiveContext";
import SideMenu from "../../components/Menu/SideMenu";
import { navItems } from "../../models/menu";
import { Outlet, useLocation, useNavigate } from "react-router-dom";
import BasicGroup from "../../components/Group/BasicGroup";
import { ai_info } from "../../assets/data/yiu_ai_info";
import { border1 } from "../../assets/styles/borderLine";
import { temp_curriculum } from "../../assets/data/temp/temp_curriculum";
import CurriculumTable from "../../components/Group/CurriculumTable";

const GraduatePage = (): React.ReactElement => {
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
      <PageHeader backgroundImage={TitleBgImg} title={"대학원"} />
      <div
        css={css({
          maxWidth: 1000,
          padding: "5%",
          margin: "0 auto",
        })}
      >
        <div
          css={css({
            display: "flex",
            flexDirection: "column",
            gap: 25,
            marginTop: 100,
            marginBottom: 50,
            lineHeight: 1.5,
          })}
        >
          {/* <Title title="전공 소개" /> */}
          <div
            css={css({
              fontSize: 30,
              fontWeight: 800,
              textAlign: "center",
            })}
          >
            {ai_info.slogan}
          </div>
          <div
            css={css({
              fontWeight: 500,
              fontSize: 17,
              color: colors.text.body,
            })}
          >
            {ai_info.shorts}
          </div>
          <img
            src={ai_info.iamge}
            // src={boss}
            alt="학부 대표이미지"
            css={css({
              width: "100%",
              minHeight: 300,
              objectFit: "contain",
              border: border1,
              alignSelf: "center",
            })}
          />
          <div
            css={css({
              display: "flex",
              flexDirection: "column",
              gap: 50,
              marginTop: 50,
            })}
          >
            <BasicGroup title="교육목적" contents={ai_info.goal} />
            <BasicGroup title="교육목표" contents={ai_info.objective} />
            <BasicGroup
              title="졸업 후 진로"
              contents={ai_info.afterGraduation}
            />
            <BasicGroup
              title="취득가능 자격 및 면허"
              contents={ai_info.certificate}
            />
            <BasicGroup
              title="이수 과정"
              // contents={ai_info.certificate}
            />
            <CurriculumTable data={temp_curriculum} />
          </div>
        </div>
      </div>
    </div>
  );
};
export default GraduatePage;
