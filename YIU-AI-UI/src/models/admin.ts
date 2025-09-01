// 하위 메뉴 아이템
export interface AdminSubMenuItem {
  name: string;
  path: string;
}

// 상위 메뉴 아이템
export interface AdminMenuItem {
  name: string;
  path?: string; // path가 없을 수도 있음 (하위 메뉴가 있는 경우)
  children?: AdminSubMenuItem[]; // 하위 메뉴가 있을 경우
}

// 메뉴 데이터
export const adminMenu: AdminMenuItem[] = [
  { name: "HOME", path: "/admin" },
  // {
  //   name: "홈페이지 관리",
  //   children: [
  //     { name: "학과 정보", path: "/admin/faculty/info" },
  //     { name: "학부장 인사말", path: "/admin/faculty/greetings" },
  //     { name: "교수/조교", path: "/admin/faculty/member" },
  //     { name: "학생회", path: "/admin/faculty/student-council" },
  //     { name: "학부 소개", path: "/admin/undergraduate/curriculum" },
  //     { name: "학부 교육과정", path: "/admin/undergraduate/curriculum" },
  //     {
  //       name: "학부 융합전공/MD",
  //       path: "/admin/undergraduate/convergence-md",
  //     },
  //     { name: "학부 졸업기준", path: "/admin/undergraduate/graduation" },
  //     { name: "대학원 소개", path: "/admin/graduate" },
  //     { name: "연구실", path: "/admin/lab" },
  //     { name: "MOU", path: "/admin/mou" },
  //   ],
  // },
  { name: "학과 뉴스", path: "/admin/news" },
  // {
  //   name: "학생 관리",
  //   children: [{ name: "전체 학생", path: "/admin/student/all" }],
  // },
  // {
  //   name: "졸업 관리",
  //   children: [
  //     { name: "졸업 요건", path: "/admin/graduation/requirements" },
  //     { name: "졸업 프로젝트", path: "/admin/graduation/project" },
  //   ],
  // },
  // { name: "설정", path: "/admin/settings" },
];
