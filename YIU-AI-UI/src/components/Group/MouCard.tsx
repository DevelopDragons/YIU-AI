/** @jsxImportSource @emotion/react */
import { css } from "@emotion/react";
import { colors } from "../../assets/styles/colors";
import { useResponsive } from "../../hooks/ResponsiveContext";
import { border1 } from "../../assets/styles/borderLine";
import { Mou } from "../../models/mou";

// Member 인터페이스를 props로 받아오도록 설정
interface MouProps {
  item: Mou; // props로 item을 받음
  size?: number;
  visibleText?: boolean;
  borderless?: boolean;
}

const MouCard = ({
  item,
  size,
  visibleText = true,
  borderless = false,
}: MouProps): React.ReactElement => {
  // 반응형 화면
  const { isMobile, isNotMobile, isTablet, isDesktopOrLaptop } =
    useResponsive();

  return (
    <div
      css={css({
        border: borderless ? "none" : border1,
        width: size ?? 250,
        height: size ?? 250, // 고정된 높이
        display: "flex",
        flexDirection: "column",
        justifyContent: "flex-start",
        backgroundColor: colors.gray.white,
      })}
    >
      <div
        css={{
          display: "flex",
          justifyContent: "center",
          alignItems: "center",
          padding: 20,
          height: 200, // 이미지 영역의 높이
        }}
      >
        <img
          src={`${process.env.REACT_APP_URL}/files/show?id=${item.image?.[0]?.id}`}
          css={{
            maxWidth: "100%",
            maxHeight: 120,
            objectFit: "contain",
          }}
        />
      </div>
      {visibleText && (
        <div
          css={css({
            height: 50,
            backgroundColor: colors.gray.brightLightGray,
            fontSize: 15,
            fontWeight: 600,
            textAlign: "center",
            display: "flex",
            alignItems: "center", // 세로 가운데 정렬
            justifyContent: "center", // 가로 가운데 정렬
            padding: 15,
          })}
        >
          {item.name}
        </div>
      )}
    </div>
  );
};

export default MouCard;
