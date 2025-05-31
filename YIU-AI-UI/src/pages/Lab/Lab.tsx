/** @jsxImportSource @emotion/react */
import { css } from "@emotion/react";
import { colors } from "../../assets/styles/colors";
import Title from "../../components/Text/Title";
import { temp_mou } from "../../assets/data/temp/temp_mou";
import { border1 } from "../../assets/styles/borderLine";
import { useResponsive } from "../../hooks/ResponsiveContext";
import { fullTimeMembers } from "../../assets/data/temp/temp_professor";
import LabCard from "../../components/Group/LabCard";
import { useQuery } from "@tanstack/react-query";
import { Member, MemberRole, ProfessorType } from "../../models/member";
import { defaultAPI } from "../../services";
import LoadingSpin from "../../components/Spin/LoadingSpin";
import GetDataErrorResultView from "../../components/Result/GetDataError";

const LabPage = (): React.ReactElement => {
  const { isMobile, isNotMobile, isTablet, isDesktopOrLaptop } =
    useResponsive();

  // // 학과 연혁 데이터 가져오기
  // const {
  //   data: history,
  //   isLoading,
  //   error,
  // } = useQuery<Member[]>({
  //   queryKey: ["member"],
  //   queryFn: async () => {
  //     const res = await defaultAPI.get(`/major/history?id=78`);

  //     return res.data;
  //   },
  // });

  // 교수 데이터 가져오기
  const {
    data: professor,
    isLoading,
    error,
  } = useQuery<Member[]>({
    queryKey: ["professor"],
    queryFn: async () => {
      const res = await defaultAPI.get(`/member`);
      return res.data.filter((member: Member) => member.role === MemberRole.PROFESSOR)
    }
  })

  if (isLoading) return <LoadingSpin />;
  if (error) return <GetDataErrorResultView />

  // 교수 데이터 중 타입이 FULL_TIME 인 교수 필터링
  const fullTimeMembers = professor?.filter(
    (item) => item.type === ProfessorType.FULL_TIME
  ) || [];

  return (
    <div>
      <Title title="연구실 소개" />
      <div
        css={css({
          marginTop: 50,
          marginBottom: 100,
          display: "grid",
          gridTemplateColumns: `repeat(auto-fill, minmax(${
            isMobile ? "400px" : "500px"
          }, 1fr))`, // 자동으로 열 수를 조정하고 최소 크기 설정
          gap: 20, // 카드 간격
        })}
      >
        {fullTimeMembers.map(
          (item) => item.labName.length > 0 && <LabCard item={item} />
        )}
      </div>
    </div>
  );
};

export default LabPage;
