/** @jsxImportSource @emotion/react */
import { css } from "@emotion/react";
import { colors } from "../../assets/styles/colors";
import Title from "../../components/Text/Title";
import { ai_info } from "../../assets/data/yiu_ai_info";
import BasicGroup from "../../components/Group/BasicGroup";
import { border1 } from "../../assets/styles/borderLine";

const IntroPage = (): React.ReactElement => {
  return (
    <div
      css={css({
        display: "flex",
        flexDirection: "column",
        gap: 25,
        marginTop: 100,
        marginBottom: 50,
        lineHeight: 1.5,
      })}
    >
      {/* <Title title="전공 소개" /> */}
      <div
        css={css({
          fontSize: 30,
          fontWeight: 800,
          textAlign: "center",
        })}
      >
        {ai_info.slogan}
      </div>
      <div
        css={css({
          fontWeight: 500,
          fontSize: 17,
          color: colors.text.body,
        })}
      >
        {ai_info.shorts}
      </div>
      <img
        src={ai_info.iamge}
        // src={boss}
        alt="학부 대표이미지"
        css={css({
          width: "100%",
          minHeight: 300,
          objectFit: "contain",
          border: border1,
          alignSelf: "center",
        })}
      />
      <div
        css={css({
          display: "flex",
          flexDirection: "column",
          gap: 50,
          marginTop: 50,
        })}
      >
        <BasicGroup title="교육목적" contents={ai_info.goal} />
        <BasicGroup title="교육목표" contents={ai_info.objective} />
        <BasicGroup title="졸업 후 진로" contents={ai_info.afterGraduation} />
        <BasicGroup
          title="취득가능 자격 및 면허"
          contents={ai_info.certificate}
        />
      </div>
    </div>
  );
};

export default IntroPage;
