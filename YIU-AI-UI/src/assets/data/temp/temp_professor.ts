import { Member, MemberRole, ProfessorType } from "../../../models/member";

export const fullTimeMembers: Member[] = [
  // {
  //   id: 1,
  //   name: "이경재",
  //   image: [],
  //   mail: "kjlee@yongin.ac.kr",
  //   tel: "031-8020-3696",
  //   labName: "인공지능 비전 연구실",
  //   labImage:
  //     "https://lh5.googleusercontent.com/aqY_FtUHG-yFXRBszkhMfmDBcZARmYKJ7t-npiE6KTXICtaYTjV9uSzmtD6MD4JY_8K9D7gAmXMK0d8N0f2giTAQik5gIF1XSC2Wc1M9vbZTF_1TcPoTI-dJHhNvzxxZmg=w1280",
  //   labLink: "https://milab.yongin.ac.kr/",
  //   labCategory: "AI, Machine Learning, Computer Vision",
  //   role: MemberRole.PROFESSOR, // 교수 역할
  //   type: ProfessorType.FULL_TIME, // 전임 교수
  //   description:
  //     "본 연구실에서는 AI 기법 중 하나인 딥러닝을 영상인식 분야에 적용하는 연구를 진행하고 있습니다. 구체적으로 딥러닝 기법을 이용해 영상 속 객체를 검출하고 이를 분석하는 알고리즘을 개발, 적용하는 연구를 수행합니다. 특히, MRI나 CT를 활용하는 Medical Image Processing에 대한 영역분할기법 연구와 GAN(Generative Adversarial Networks)를 통한 고품질 이미지 생성 알고리즘, Vision Transformer를 이용한 보행자 검출 및 Multi-Clothing Detection 등 다양한",
  // },
  // {
  //   id: 2,
  //   name: "이경재",
  //   image: "",
  //   mail: "kjlee@yongin.ac.kr",
  //   tel: "031-8020-3696",
  //   labName: "사물 인터넷 및 탐지 연구실",
  //   labImage: "https://avatars.githubusercontent.com/u/151107645?s=200&v=4",
  //   labLink: "https://milab.yongin.ac.kr/",
  //   labCategory: "IOT, Object Detection",
  //   role: MemberRole.PROFESSOR,
  //   type: ProfessorType.FULL_TIME,
  //   description:
  //     "신뢰할 수 있는 사물 인터넷의 구현과 각종 센서들에 의해 수집되는 데이터로부터 탐지하는 것을 연구를 합니다. 그리고, Scale-free networks, IoT 망의 신뢰성 분석, 내장형 운영체제, Deep learning on graph를 포함하는 기계 학습을 연구합니다.",
  // },
  // {
  //   id: 3,
  //   name: "이경재",
  //   image: "",
  //   mail: "kjlee@yongin.ac.kr",
  //   tel: "031-8020-3696",
  //   labName: "패턴인식 및 휴먼 인터페이스 연구실",
  //   labImage: "",
  //   labLink: "https://milab.yongin.ac.kr/",
  //   labCategory: "Pattern Recognition, Human Interface",
  //   role: MemberRole.PROFESSOR,
  //   type: ProfessorType.FULL_TIME,
  //   description:
  //     "본 연구실에서는 컴퓨터와 인간의 자연스러운 의사소통에 관한 상호 작용(프로세스)의 설계와 구현으로 사용의 편의성을 요구하는 사용자에게 친숙한 환경을 제공하는데 기본적인 목표를 두고 있습니다. 주요 연구 관심 분야로는 이미지, 음성, 위성을 통한 패턴 인식(Pattem Recognition), 임베디드 소프트웨어 아키텍처, 그리고 사람들이 좀 더 쉽고 편리하게 사용할 수 있는 시스템(모바일 시스템) 개발에 관심을 두고 있습니다.",
  // },
  // {
  //   id: 4,
  //   name: "이경재",
  //   image: "",
  //   mail: "kjlee@yongin.ac.kr",
  //   tel: "031-8020-3696",
  //   labName: "",
  //   labImage: "https://via.placeholder.com/150",
  //   labLink: "https://milab.yongin.ac.kr/",
  //   labCategory: "AI & Machine Learning",
  //   role: MemberRole.PROFESSOR, // 교수 역할
  //   type: ProfessorType.FULLTIME, // 전임 교수
  //   description:
  //     "이경재 is a leading expert in AI and machine learning, with over 20 years of experience in the field.",
  // },
  // {
  //   id: 5,
  //   name: "이경재",
  //   image: "",
  //   mail: "kjlee@yongin.ac.kr",
  //   tel: "031-8020-3696",
  //   labName: "",
  //   labImage: "https://via.placeholder.com/150",
  //   labLink: "https://milab.yongin.ac.kr/",
  //   labCategory: "AI & Machine Learning",
  //   role: MemberRole.PROFESSOR,
  //   type: ProfessorType.FULLTIME,
  //   description:
  //     "이경재 is a leading expert in AI and machine learning, with over 20 years of experience in the field.",
  // },
  // {
  //   id: 6,
  //   name: "이경재",
  //   image: "",
  //   mail: "kjlee@yongin.ac.kr",
  //   tel: "031-8020-3696",
  //   labName: "",
  //   labImage: "https://via.placeholder.com/150",
  //   labLink: "https://milab.yongin.ac.kr/",
  //   labCategory: "AI & Machine Learning",
  //   role: MemberRole.PROFESSOR,
  //   type: ProfessorType.FULLTIME,
  //   description:
  //     "이경재 is a leading expert in AI and machine learning, with over 20 years of experience in the field.",
  // },
];

export const notFullTimeMembers: Member[] = [
  // {
  //   id: 1,
  //   name: "이경재",
  //   image: "",
  //   mail: "kjlee@yongin.ac.kr",
  //   tel: "031-8020-3696",
  //   labName: "",
  //   labImage: "https://via.placeholder.com/150",
  //   labLink: "https://milab.yongin.ac.kr/",
  //   labCategory: "AI & Machine Learning",
  //   role: MemberRole.PROFESSOR,
  //   type: ProfessorType.ADJUNCT,
  //   description: "한국전자통신연구원",
  // },
  // {
  //   id: 2,
  //   name: "이경재",
  //   image: "",
  //   mail: "kjlee@yongin.ac.kr",
  //   tel: "031-8020-3696",
  //   labName: "",
  //   labImage: "https://via.placeholder.com/150",
  //   labLink: "https://milab.yongin.ac.kr/",
  //   labCategory: "AI & Machine Learning",
  //   role: MemberRole.PROFESSOR,
  //   type: ProfessorType.INVITED,
  //   description: "한국전자통신연구원",
  // },
  // {
  //   id: 3,
  //   name: "이경재",
  //   image: "",
  //   mail: "kjlee@yongin.ac.kr",
  //   tel: "031-8020-3696",
  //   labName: "",
  //   labImage: "https://via.placeholder.com/150",
  //   labLink: "https://milab.yongin.ac.kr/",
  //   labCategory: "AI & Machine Learning",
  //   role: MemberRole.PROFESSOR,
  //   type: ProfessorType.VISITING,
  //   description: "한국전자통신연구원",
  // },
];

export const retiredMembers: Member[] = [
//   {
//     id: 1,
//     name: "이경재",
//     image: "",
//     mail: "kjlee@yongin.ac.kr",
//     tel: "031-8020-3696",
//     labName: "",
//     labImage: "https://via.placeholder.com/150",
//     labLink: "https://milab.yongin.ac.kr/",
//     labCategory: "AI & Machine Learning",
//     role: MemberRole.PROFESSOR,
//     type: ProfessorType.RETIRED,
//     description:
//       "이경재 is a leading expert in AI and machine learning, with over 20 years of experience in the field.",
//   },
//   {
//     id: 2,
//     name: "이경재",
//     image: "",
//     mail: "kjlee@yongin.ac.kr",
//     tel: "031-8020-3696",
//     labName: "컴퓨터비전 연구실",
//     labImage: "https://via.placeholder.com/150",
//     labLink: "https://milab.yongin.ac.kr/",
//     labCategory: "AI & Machine Learning",
//     role: MemberRole.PROFESSOR,
//     type: ProfessorType.RETIRED,
//     description:
//       "이경재 is a leading expert in AI and machine learning, with over 20 years of experience in the field.",
//   },
// ];

// export const assistantMembers: Member[] = [
//   {
//     id: 1,
//     name: "강주현",
//     image: "",
//     mail: "kjlee@yongin.ac.kr",
//     tel: "031-8020-3696",
//     labName: "컴퓨터비전 연구실",
//     labImage: "https://via.placeholder.com/150",
//     labLink: "https://milab.yongin.ac.kr/",
//     labCategory: "AI & Machine Learning",
//     role: MemberRole.ASSISTANT,
//     type: ProfessorType.FULL_TIME,
//     description:
//       "이경재 is a leading expert in AI and machine learning, with over 20 years of experience in the field.",
//   },
//   {
//     id: 2,
//     name: "변미현",
//     image: "",
//     mail: "kjlee@yongin.ac.kr",
//     tel: "031-8020-3696",
//     labName: "",
//     labImage: "https://via.placeholder.com/150",
//     labLink: "https://milab.yongin.ac.kr/",
//     labCategory: "AI & Machine Learning",
//     role: MemberRole.ASSISTANT,
//     type: ProfessorType.FULL_TIME,
//     description:
//       "이경재 is a leading expert in AI and machine learning, with over 20 years of experience in the field.",
//   },
];
