/** @jsxImportSource @emotion/react */
import { css } from "@emotion/react";
import { colors } from "../../assets/styles/colors";
import Title from "../../components/Text/Title";
// import {
//   fullTimeMembers,
//   notFullTimeMembers,
//   retiredMembers,
// } from "../../assets/data/temp/temp_professor";
import Professor from "../../components/Group/Professor";
import { professorGrid } from "./styles";
import { useQuery } from "@tanstack/react-query";
import { defaultAPI } from "../../services";
import { Member, MemberRole, ProfessorType } from "../../models/member";
import LoadingSpin from "../../components/Spin/LoadingSpin";
import GetDataErrorResultView from "../../components/Result/GetDataError";

const ProfessorPage = (): React.ReactElement => {
  // 교수 데이터 가져오기
  const {
    data: professor,
    isLoading,
    error,
  } = useQuery<Member[]>({
    queryKey: ["professor"],
    queryFn: async () => {
      const res = await defaultAPI.get(`/member`);
      return res.data.filter((member: Member) => member.role === MemberRole.PROFESSOR);
    }
  })

  if (isLoading) return <LoadingSpin />;
  if (error) return <GetDataErrorResultView />

  // 교수 타입별 필터링
  // 전임교수
  const fullTimeMembers = professor?.filter(
    (item) => item.type === ProfessorType.FULL_TIME
  ) || [];

  // 겸임/초빙/객원교수
  const notFullTimeMembers = professor?.filter(
    (item) => item.type === ProfessorType.ADJUNCT || item.type === ProfessorType.INVITED || item.type === ProfessorType.VISITING
  ) || [];

  // 명예(퇴임)교수
  const retiredMembers = professor?.filter(
    (item) => item.type === ProfessorType.RETIRED
  ) || [];

  return (
    <div>
      <Title title="전임교수" />
      <div css={professorGrid}>
        {fullTimeMembers.map((item, index) => {
          return <Professor item={item} />;
        })}
      </div>

      <Title title="겸임/초빙/객원교수" />
      <div css={professorGrid}>
        {notFullTimeMembers.map((item, index) => {
          return <Professor item={item} />;
        })}
      </div>

      <Title title="명예(퇴임)교수" />
      <div css={professorGrid}>
        {retiredMembers.map((item, index) => {
          return <Professor item={item} />;
        })}
      </div>
    </div>
  );
};

export default ProfessorPage;
