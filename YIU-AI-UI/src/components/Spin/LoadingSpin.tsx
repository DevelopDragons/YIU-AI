/** @jsxImportSource @emotion/react */
import { css } from "@emotion/react";
import React from "react";
import { colors } from "../../assets/styles/colors";

const LoadingSpin: React.FC = () => (
  <div
    css={css({
      height: 650,
      display: "flex",
      justifyContent: "center",
      alignItems: "center",
    })}
  >
    <p>로딩중...</p>
  </div>
);

export default LoadingSpin;
