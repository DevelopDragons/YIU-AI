/** @jsxImportSource @emotion/react */
import * as React from "react";
import Box from "@mui/material/Box";
import Typography from "@mui/material/Typography";
import { css } from "@emotion/react";
import { colors } from "../../../assets/styles/colors";
import YIU_emblem from "../../../assets/images/YIU_emblem.jpg";
import { useNavigate } from "react-router-dom";
import { yiuAiInfo } from "../../../assets/data/yiu_ai_info";
import { useResponsive } from "../../../hooks/ResponsiveContext";
import { border1 } from "../../../assets/styles/borderLine";

const Footer: React.FC = () => {
  const { isMobile, isNotMobile, isTablet, isDesktopOrLaptop } =
    useResponsive();

  const navigate = useNavigate();

  return (
    <div
      css={css({
        // backgroundColor: colors.basic.footer_bg,
        // color: colors.basic.footer_text,
        display: "flex",
        flexDirection: isMobile ? "column" : "row",
        alignItems: "center",
        paddingTop: 50,
        paddingBottom: 50,
        paddingLeft: isMobile ? 0 : "10%",
        gap: 30,
        borderTop: border1,
      })}
    >
      <img
        src={YIU_emblem}
        css={{
          width: 60,
          objectFit: "contain",
          ":hover": {
            cursor: "pointer",
          },
        }}
        onClick={() =>
          window.open("http://yongin.ac.kr", "_blank", "noopener, noreferrer")
        }
      />
      {/* 학과 정보 */}
      <div
        css={css({
          display: "flex",
          flexDirection: "column",
          gap: 5,
        })}
      >
        {/* 용인대학교 | 학부이름 */}
        <div
          css={css({
            display: "flex",
            flexDirection: "row",
            justifyContent: "start",
            alignItems: "center",
            fontWeight: 700,
            color: colors.yiu.green,
          })}
        >
          용인대학교 | {yiuAiInfo.name}
        </div>
        <div
          css={css({
            display: "flex",
            flexDirection: "column",
            gap: 5,
            justifyContent: "start",
            color: colors.text.black,
            fontWeight: 600,
            fontSize: 14,
          })}
        >
          {/* 주소 */}
          <div>{yiuAiInfo.address}</div>
          {/* 전화번호 | 이메일 | 학과 홈페이지 링크 */}
          <div>{`${yiuAiInfo.tel} | ${yiuAiInfo.mail} | https://www.yongin.ac.kr`}</div>
        </div>
      </div>
    </div>
  );
};

export default Footer;
