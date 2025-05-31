/** @jsxImportSource @emotion/react */
import { css } from "@emotion/react";
import { colors } from "../../assets/styles/colors";
import { useResponsive } from "../../hooks/ResponsiveContext";
import { border1 } from "../../assets/styles/borderLine";
import { Mou } from "../../models/mou";
import { Member } from "../../models/member";
import altLabImage from "../../assets/images/YIU_logo.png";
import StringListToTag from "../Tag/StringListToTag";
import { Divider } from "@mui/material";

// Member 인터페이스를 props로 받아오도록 설정
interface LabProps {
  item: Member; // props로 item을 받음
}

const LabCard = ({ item }: LabProps): React.ReactElement => {
  // 반응형 화면
  const { isMobile, isNotMobile, isTablet, isDesktopOrLaptop } =
    useResponsive();

  return (
    <div
      css={css({
        border: border1,
        width: isMobile ? 350 : 450,
        height: isMobile ? 300 : 250, // 고정된 높이
        display: "flex",
        flexDirection: "column",
        justifyContent: "flex-start",
        padding: 20,
        gap: 20,
      })}
    >
      <div
        css={css({
          display: "flex",
          flexDirection: "row",
          justifyContent: "start",
          alignItems: "start",
          gap: 20,
        })}
      >
        <img
          src={item.labImage.length > 0 ? `${process.env.REACT_APP_URL}/files/show?id=${item.labImage?.[0]?.id}` : altLabImage}
          onClick={() =>
            window.open(item.labLink, "_blank", "noopener, noreferrer")
          }
          css={{
            width: 75,
            maxHeight: 75,
            objectFit: "contain",
            borderRadius: 5,
            cursor: "pointer",
          }}
        />
        <div
          css={css({
            display: "flex",
            flexDirection: "column",
            justifyContent: "start",
            alignItems: "start",
            gap: 10,
            paddingTop: 10,
          })}
        >
          <div
            css={css({
              fontWeight: 700,
              fontSize: 20,
              color: colors.yiu.green,
            })}
          >
            {item.labName}
          </div>
          <StringListToTag str={item.labCategory} />
        </div>
      </div>
      <Divider
        css={css({
          backgroundColor: colors.yiu.green,
        })}
      />
      <div
        css={css({
          fontSize: 14,
        })}
      >
        {item.description}
      </div>
    </div>
  );
};

export default LabCard;
