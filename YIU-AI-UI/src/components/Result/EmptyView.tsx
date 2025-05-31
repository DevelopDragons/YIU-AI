/** @jsxImportSource @emotion/react */
import { css } from "@emotion/react";
import React from "react";
import { colors } from "../../assets/styles/colors";

const EmptyView = () => {
  return (
    <div>데이터 없음</div>
    // <Result
    //   status="500"
    //   title="데이터를 불러올 수 없음"
    //   subTitle={`잠시 후에 다시 시도해주세요.`}
    //   extra={
    //     <SquareColorButton
    //     text={"메인 화면으로 이동"}
    //     fontColor={colors.gray.white}
    //     fontColor_hover={colors.gray.white}
    //     bgColor={colors.yiu.darkBlue}
    //     bgColor_hover={colors.yiu.darkBlue_dark}
    //     onClick={handleSubmit}
    //   />
    //   }
    // />
  );
};
export default EmptyView;
