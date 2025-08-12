/** @jsxImportSource @emotion/react */
import { css } from "@emotion/react";
import { colors } from "../../assets/styles/colors";
import { useResponsive } from "../../hooks/ResponsiveContext";
import { Button } from "@mui/material";

interface MediumButtonProps {
  title: String;
  onClick: () => void;
  sxOverride: object;
}

const MediumButton = (props: MediumButtonProps): React.ReactElement => {
  // 반응형 화면
  const { isMobile, isNotMobile, isTablet, isDesktopOrLaptop } =
    useResponsive();

  return (
    <Button
      variant="contained"
      color="primary"
      size="medium"
      onClick={props.onClick}
      sx={{
        fontWeight: 700,
        backgroundColor: colors.yiu.green,
        ":hover": {
          backgroundColor: colors.yiu.green_dark,
          transition: "all 0.3s",
        },
        ...props.sxOverride,
      }}
    >
      {props.title}
    </Button>
  );
};

export default MediumButton;
