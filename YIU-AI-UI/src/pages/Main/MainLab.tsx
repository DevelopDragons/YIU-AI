/** @jsxImportSource @emotion/react */
import { css } from "@emotion/react";
import { colors } from "../../assets/styles/colors";
import { useResponsive } from "../../hooks/ResponsiveContext";
import SectionTitle from "../../components/Text/SectionTitle";
import MainSectionHeader from "../../components/Group/MainSectionHeader";

const MainLab = (): React.ReactElement => {
  const { isMobile, isNotMobile, isTablet, isDesktopOrLaptop } =
    useResponsive();

  return (
    <div>
      <MainSectionHeader
        title={"연구실"}
        desc={"연구실의 연구 주제와 다양한 연구 성과를 확인해보세요."}
        buttonText="VIEW MORE"
        link="/research/lab"
      />
    </div>
  );
};

export default MainLab;
