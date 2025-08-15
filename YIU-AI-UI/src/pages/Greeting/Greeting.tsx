/** @jsxImportSource @emotion/react */
import { css } from "@emotion/react";
import { colors } from "../../assets/styles/colors";
import Title from "../../components/Text/Title";
import { yiuAiInfo } from "../../assets/data/yiu_ai_info";
import altImg from "../../assets/images/aihakbu_img1.jpeg";
import { useQuery } from "@tanstack/react-query";
import { Greeting } from "../../models/greeting";
import { defaultAPI } from "../../services";
import LoadingSpin from "../../components/Spin/LoadingSpin";
import GetDataErrorResultView from "../../components/Result/GetDataError";
import { useResponsive } from "../../hooks/ResponsiveContext";

const GreetingPage = (): React.ReactElement => {
  const { isMobile, isNotMobile, isTablet, isDesktopOrLaptop } =
    useResponsive();

  // 학부장 인사말 데이터 가져오기
  const {
    data: greeting,
    isLoading,
    error,
  } = useQuery<Greeting[]>({
    queryKey: ["greeting"],
    queryFn: async () => {
      const res = await defaultAPI.get(`/greeting`);
      return res.data;
    },
  });

  if (isLoading) return <LoadingSpin />;
  if (error) return <GetDataErrorResultView />;

  return (
    <div>
      <Title title="학부장 인사말" />
      <div
        css={css({
          marginTop: 30,
          position: "relative",
        })}
      >
        {/* 이미지 */}
        {greeting?.[0].image[0] && (
          <img
            src={`${process.env.REACT_APP_URL}/files/show?id=${greeting?.[0].image[0].id}`}
            css={{
              float: "right",
              width: "40%",
              maxWidth: 400,
              height: "auto",
              marginLeft: 20,
              marginBottom: 10,
              objectFit: "cover",
              "@media (max-width: 1340px)": {
                float: "none",
                width: "100%",
                maxWidth: "100%",
                marginLeft: 0,
              },
            }}
          />
        )}

        {/* 제목 */}
        <div
          css={css({
            fontSize: 30,
            fontWeight: 500,
            marginBottom: 20,
          })}
        >
          <span css={css({ fontWeight: 800, color: colors.yiu.green })}>
            {`용인대학교 ${yiuAiInfo.name} `}
          </span>
          <span>
            홈페이지에
            <br />
            방문하신 여러분을 진심으로 환영합니다.
          </span>
        </div>

        {/* 본문 */}
        <div
          css={css({
            fontSize: 17,
            color: colors.gray.darkGray,
            lineHeight: 1.5,
          })}
        >
          {greeting?.[0].greeting}
        </div>

        {/* 하단 학부장 사인 */}
        <div
          css={css({
            display: "flex",
            flexDirection: "row",
            justifyContent: "end",
            alignItems: "center",
            gap: 10,
            textAlign: "left",
            fontWeight: 500,
            color: colors.gray.darkGray,
            fontSize: 22,
            marginTop: 20,
          })}
        >
          <span>AI융합학부 학부장</span>
          <span css={css({ fontWeight: 800, color: colors.yiu.green })}>
            {greeting?.[0].name}
          </span>
          {greeting?.[0].autograph[0] && (
            <img
              src={`${process.env.REACT_APP_URL}/files/show?id=${greeting?.[0].autograph[0].id}`}
              css={{
                width: 170,
                objectFit: "contain",
              }}
            />
          )}
        </div>
      </div>
    </div>
  );
};

export default GreetingPage;
