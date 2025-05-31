/** @jsxImportSource @emotion/react */
import React from "react";
import { css } from "@emotion/react";
import Title from "../../components/Text/Title";
// import { temp_curriculum } from "../../assets/data/temp/temp_curriculum";
import { useResponsive } from "../../hooks/ResponsiveContext";
import CurriculumTable from "../../components/Group/CurriculumTable";
import { useQuery } from "@tanstack/react-query";
import { Curriculum } from "../../models/curriculum";
import { defaultAPI } from "../../services";
import LoadingSpin from "../../components/Spin/LoadingSpin";
import GetDataErrorResultView from "../../components/Result/GetDataError";

const CurriculumPage = (): React.ReactElement => {
  // 반응형 화면
  const { isMobile, isNotMobile, isTablet, isDesktopOrLaptop } =
    useResponsive();

  // curriculum 데이터 가져오기
  const {
    data: curriculums,
    isLoading,
    error,
  } = useQuery<Curriculum[]>({
    queryKey: ["curriculums"],
    queryFn: async () => {
      const res = await defaultAPI.get(`/curriculum`);
      return res.data;
    }
  });

  if (isLoading) return <LoadingSpin />;
  if (error) return <GetDataErrorResultView />

  return (
    <div>
      <Title title="교육과정" />
      <div css={css({ marginTop: 50, marginBottom: 100, border: "none" })}>
        <CurriculumTable data={curriculums} />
      </div>
    </div>
  );
};

export default CurriculumPage;
