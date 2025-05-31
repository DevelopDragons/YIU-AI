export interface NavItem {
  label: string; // 상위 메뉴 이름
  link?: string;
  subMenu: SubMenuItem[]; // 모든 항목은 객체 형태로 link 포함
}

export interface SubMenuItem {
  label: string;
  link: string;
}

export const navItems: NavItem[] = [
  {
    label: "소개",
    link: "/faculty/greetings",
    subMenu: [
      { label: "학부장 인사말", link: "/faculty/greetings" },
      { label: "교수 소개", link: "/faculty/professor" },
      { label: "조교 소개", link: "/faculty/assistant-professor" },
      { label: "학생회 소개", link: "/faculty/student-council" },
      { label: "연락처 및 오시는 길", link: "/faculty/contact" },
    ],
  },
  {
    label: "학부",
    link: "/undergraduate/intro",
    subMenu: [
      { label: "소개", link: "/undergraduate/intro" },
      { label: "교육과정", link: "/undergraduate/curriculum" },
      { label: "융합전공/MD", link: "/undergraduate/convergence-md" },
      { label: "졸업기준", link: "/undergraduate/graduation" },
    ],
  },
  // {
  //   label: "학부과정",
  //   subMenu: [
  //     { label: "전공 소개", link: "/course/intro" },
  //     { label: "교과 안내", link: "/course/curriculum" },
  //     { label: "융합전공/MD", link: "/course/convergence-major" },
  //     { label: "졸업기준", link: "/course/graduation" },
  //   ],
  // },
  {
    label: "대학원",
    link: "/graduate",
    subMenu: [],
  },
  {
    label: "교육/연구",
    link: "/research/lab",
    subMenu: [
      { label: "연구실", link: "/research/lab" },
      { label: "MOU기관", link: "/research/mou" },
    ],
  },
  {
    label: "소식",
    link: "/news",
    subMenu: [],
    // subMenu: [{ label: "학부소식", link: "/news" }],
  },
  {
    label: "입학",
    subMenu: [
      { label: "대학", link: "https://ipsi.yongin.ac.kr/index.do" },
      { label: "대학원", link: "https://graduate.yongin.ac.kr/" },
    ],
  },
];
