/** @jsxImportSource @emotion/react */
import { css } from "@emotion/react";
import { colors } from "../../assets/styles/colors";
import { useResponsive } from "../../hooks/ResponsiveContext";
import { Button } from "@mui/material";

interface FullWidthButtonProps {
  title: String;
  onClick: () => void;
  sxOverride?: object;
}

const FullWidthButton = (props: FullWidthButtonProps): React.ReactElement => {
  // 반응형 화면
  const { isMobile, isNotMobile, isTablet, isDesktopOrLaptop } =
    useResponsive();

  return (
    <Button
      variant="contained"
      fullWidth
      size="large"
      css={css({
        height: 50,
        backgroundColor: colors.yiu.green,
        color: colors.gray.white,
        fontWeight: 800,
        borderRadius: 8,
        padding: "10px 0",
        "&:hover": {
          backgroundColor: colors.yiu.green_dark,
        },
      })}
      onClick={props.onClick}
    >
      {props.title}
    </Button>
  );
};

export default FullWidthButton;
