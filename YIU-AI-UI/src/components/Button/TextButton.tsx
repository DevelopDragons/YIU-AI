/** @jsxImportSource @emotion/react */
import { css } from "@emotion/react";
import { colors } from "../../assets/styles/colors";
import { useResponsive } from "../../hooks/ResponsiveContext";
import { Button } from "@mui/material";

interface TextButtonProps {
  title: String;
  onClick: () => void;
  sxOverride?: object;
}

const TextButton = (props: TextButtonProps): React.ReactElement => {
  // 반응형 화면
  const { isMobile, isNotMobile, isTablet, isDesktopOrLaptop } =
    useResponsive();

  return (
    <Button
      disableRipple
      disableElevation
      size="small"
      sx={{
        color: colors.gray.black,
        fontSize: 13,
        textTransform: "none", // 대문자 변환 방지
        backgroundColor: "transparent", // 배경 제거
        "&:hover": {
          backgroundColor: "transparent", // hover 시 배경 없음
        },
        cursor: "pointer",
        ...props.sxOverride,
      }}
      onClick={props.onClick}
    >
      {props.title}
    </Button>
  );
};

export default TextButton;
