/** @jsxImportSource @emotion/react */
import { css } from "@emotion/react";
import { colors } from "../../assets/styles/colors";
import Title from "../../components/Text/Title";
// import { temp_mou } from "../../assets/data/temp/temp_mou";
import { useResponsive } from "../../hooks/ResponsiveContext";
import MouCard from "../../components/Group/MouCard";
import { Mou } from "../../models/mou";
import { useQuery } from "@tanstack/react-query";
import { defaultAPI } from "../../services";
import LoadingSpin from "../../components/Spin/LoadingSpin";
import GetDataErrorResultView from "../../components/Result/GetDataError";

const MOUPage = (): React.ReactElement => {
  const { isMobile, isNotMobile, isTablet, isDesktopOrLaptop } =
    useResponsive();

  // MOU 데이터 가져오기
  const {
    data: mous,
    isLoading,
    error,
  } = useQuery<Mou[]>({
    queryKey: ["mous"],
    queryFn: async () => {
      const res = await defaultAPI.get(`/mou`);
      return res.data;
    }
  });

  if (isLoading) return <LoadingSpin />;
  if (error) return <GetDataErrorResultView />
  
  return (
    <div>
      <Title title="MOU 기관" />
      <div
        css={css({
          marginTop: 50,
          marginBottom: 100,
          display: "grid",
          gridTemplateColumns: "repeat(auto-fill, minmax(250px, 1fr))", // 자동으로 열 수를 조정하고 최소 크기 설정
          gap: 20, // 카드 간격
        })}
      >
        {mous?.map((item) => (
          <MouCard item={item} />
        ))}
      </div>
    </div>
  );
};

export default MOUPage;
