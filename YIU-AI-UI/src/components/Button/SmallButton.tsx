/** @jsxImportSource @emotion/react */
import { css } from "@emotion/react";
import { colors } from "../../assets/styles/colors";
import { useResponsive } from "../../hooks/ResponsiveContext";
import { Button } from "@mui/material";
import { border1 } from "../../assets/styles/borderLine";

interface SmallButtonProps {
  title: String;
  onClick: () => void;
  bordered?: boolean;
  colored: boolean;
  color?: string;
  hoverColor?: string;
}

const SmallButton = (props: SmallButtonProps): React.ReactElement => {
  // 반응형 화면
  const { isMobile, isNotMobile, isTablet, isDesktopOrLaptop } =
    useResponsive();

  return (
    <div
      css={css({
        padding: "10px 15px",
        display: "flex",
        justifyContent: "space-between",
        alignItems: "center",
        cursor: "pointer",
        borderRadius: 10,
        backgroundColor: props.colored
          ? props.color ?? colors.yiu.green
          : colors.gray.white,
        color: props.colored ? colors.gray.white : colors.gray.gray,
        textDecoration: "none",
        transition: "all 0.3s",
        fontWeight: 600,
        border: props.bordered ? border1 : "none",
        ":hover": {
          color: props.colored ? colors.gray.white : colors.yiu.green,
          backgroundColor: props.colored
            ? props.hoverColor ?? colors.yiu.green_dark
            : colors.gray.lightGray,
        },
      })}
      onClick={props.onClick}
    >
      {props.title}
    </div>
  );
};

export default SmallButton;
