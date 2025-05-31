/** @jsxImportSource @emotion/react */
import { css } from "@emotion/react";
import { colors } from "../../assets/styles/colors";
import Title from "../../components/Text/Title";
import { assistantProfessorGrid } from "./styles";
// import { assistantMembers } from "../../assets/data/temp/temp_professor";
import AssistantProfessor from "../../components/Group/AssistantProfessor";
import { useQuery } from "@tanstack/react-query";
import { defaultAPI } from "../../services";
import { Member, MemberRole, ProfessorType } from "../../models/member";
import LoadingSpin from "../../components/Spin/LoadingSpin";
import GetDataErrorResultView from "../../components/Result/GetDataError";

const AssistantProfessorPage = (): React.ReactElement => {
  // 조교 데이터 가져오기
  const {
    data: assistant,
    isLoading,
    error,
  } = useQuery<Member[]>({
    queryKey: ["assistant"],
    queryFn: async () => {
      const res = await defaultAPI.get(`/member`);
      return res.data.filter((member: Member) => member.role === MemberRole.ASSISTANT);
    }
  })

  if (isLoading) return <LoadingSpin />;
  if (error) return <GetDataErrorResultView />

  return (
    <div>
      <Title title="조교 소개" />
      <div css={assistantProfessorGrid}>
        {assistant?.map((item, index) => {
          return <AssistantProfessor item={item} />;
        })}
      </div>
    </div>
  );
};

export default AssistantProfessorPage;
