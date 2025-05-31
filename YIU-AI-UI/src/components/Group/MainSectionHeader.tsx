/** @jsxImportSource @emotion/react */
import { css } from "@emotion/react";
import { colors } from "../../assets/styles/colors";
import { useResponsive } from "../../hooks/ResponsiveContext";
import SectionTitle from "../Text/SectionTitle";
import { useNavigate } from "react-router-dom";

interface MainSectionHeaderProps {
  title: string; // 제목
  desc?: string;
  descSize?: number;
  buttonText?: string;
  link?: string;
}

const MainSectionHeader = ({
  title,
  desc,
  descSize = 18,
  buttonText,
  link,
}: MainSectionHeaderProps): React.ReactElement => {
  const { isMobile, isNotMobile, isTablet, isDesktopOrLaptop } =
    useResponsive();
  const navigate = useNavigate();

  return (
    <div
      css={css({
        display: "flex",
        flexDirection: "row",
        justifyContent: "space-between",
      })}
    >
      <SectionTitle title={title} desc={desc} />
      {buttonText && (
        <div
          css={css({
            display: "flex",
            alignItems: "center",
            justifyContent: "center",
            minWidth: 100,
            maxHeight: isMobile ? 20 : 25,
            padding: isMobile ? 7 : 10,
            cursor: "pointer",
            borderRadius: 50,
            textAlign: "center",
            color: colors.gray.white,
            fontSize: isMobile ? 14 : 16,
            fontWeight: 700,
            backgroundColor: colors.yiu.green,
            ":hover": {
              backgroundColor: colors.yiu.green_dark,
              transition: "all 0.3s",
            },
          })}
          onClick={() => {
            if (link) navigate(link);
          }}
        >
          {buttonText}
        </div>
      )}
    </div>
  );
};

export default MainSectionHeader;
