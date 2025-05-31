// Member 역할 구분 (교수 / 조교)
export enum MemberRole {
  PROFESSOR = "PROFESSOR",
  ASSISTANT = "ASSISTANT",
}

// 교수 유형 구분 (전임 / 겸임 / 초빙 / 객원 / 퇴직)
export enum ProfessorType {
  FULL_TIME = "FULL_TIME",
  ADJUNCT = "ADJUNCT",
  INVITED = "INVITED",
  VISITING = "VISITING",
  RETIRED = "RETIRED",
}
// export enum ProfessorType {
//   FULLTIME = "FULLTIME",
//   ADJUNCT = "ADJUNCT",
//   INVITED = "INVITED",
//   VISITING = "VISITING",
//   RETIRED = "RETIRED",
// }

export interface Member {
  id: number;
  name: string;
  image: ImageProps[];
  mail: string;
  tel: string;
  labName: string;
  labImage: ImageProps[];
  labLink: string;
  labCategory: string;
  role: MemberRole; // 역할
  type: ProfessorType | null; // 교수일 경우 type이 필요
  description: string;
  createdAt: string;
  updatedAt: string;
}

export interface ImageProps {
    id: number,
    type: string,
    typeId: number,
    category: string,
    originName: string,
    saveName: string,
    size: number,
    createdAt: string,
}
