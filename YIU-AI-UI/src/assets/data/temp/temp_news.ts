import { News } from "../../../models/news";

export const temp_news: News[] = [
  {
    id: 1,
    title: `청색광, 알고 보니 더 교묘하게 내 눈 건강 위협한다?`,
    shorts: `UNIST 민두영·권태혁·민승규 교수팀, 항산화 시스템 피하는 청색광의 단백질 손상 경로 규명
    단백질 구조 안에 포획된 산소가 산화 손상 유발 … Nature Communications 게재`,
    contents: `청색광이 항산화 시스템을 피해 세포 단백질을 손상시키는 과정이 밝혀졌다.

UNIST(총장 박종래) 화학과 민두영·권태혁·민승규 교수팀은 청색광(블루라이트; blue light)에 의해 세포 단백질이 손상되는 새로운 경로를 규명했다고 6일 밝혔다.

청색광은 햇빛, LED 기반 디스플레이 기기, 실내조명에서 방출되는 빛이다. 고에너지 가시광선이라 일반적인 자외선 차단제로는 제대로 막을 수 없고, 눈의 각막과 수정체를 통과해 망막까지 도달할 수 있는 특성이 있다.

체내에 도달한 청색광은 세포 단백질의 산화 손상을 유발해 피부와 눈 건강을 해칠 수 있는 것으로 알려져 있다. 체내에 녹아 있던 산소가 청색광을 흡수해 반응성이 높은 활성산소로 바뀌면, 이 활성산소가 세포의 단백질 사이를 헤집고 다니면서 단백질 표면을 산화 손상시키는 방식이다. 세포 내 항산화 시스템은 이러한 활성산소를 무력화시켜 단백질 손상을 줄일 수 있다.`,
    thumbnails:
      [],
    gallery: [],
    file: [],
    createAt: "2025-02-04T15:54:51.484107",
    updateAt: "2025-02-04T15:54:51.484107",
  },
//   {
//     id: 2,
//     title: `미세영역 온도 변화 실시간 포착하는 ‘나노온도계’ 개발`,
//     shorts: `UNIST 권오훈 교수팀, 투과전자현미경 내 미세시료 온도 측정 기술 개발
// 온도계 소재 개선해 실시간 영상 관찰에 적용 가능 … ACS Nano 논문 게재`,
//     contents: `UNIST 권오훈 교수팀, 투과전자현미경 내 미세시료 온도 측정 기술 개발
// 온도계 소재 개선해 실시간 영상 관찰에 적용 가능 … ACS Nano 논문 게재`,
//     thumbnails:
//       "https://chemistry.unist.ac.kr/wp-content/uploads/2025/01/%EA%B6%8C%EC%98%A4%ED%9B%88-%EA%B5%90%EC%88%98%ED%8C%80-800x428-1.png",
//     gallery: [],
//     file: [],
//     createAt: "2025-01-27T15:54:51.484107",
//     updateAt: "2025-01-27T15:54:51.484107",
//   },
//   {
//     id: 3,
//     title: `화학과 김지연 박사, 아시안결정학회 신진연구자상 수상`,
//     shorts: `탄소중립 혁신 설계법 ‘Up-Down Approach’ 학계 인정
// Nature Synthesis 실려… ‘Rising Star’ 세션서 성과 발표`,
//     contents: `탄소중립 혁신 설계법 ‘Up-Down Approach’ 학계 인정
// Nature Synthesis 실려… ‘Rising Star’ 세션서 성과 발표`,
//     thumbnails:
//       "https://chemistry.unist.ac.kr/wp-content/uploads/2025/01/JYK_%EC%82%AC%EC%A7%841-3-800x228-1.png",
//     gallery: [],
//     files: [],
//     createdAt: "2024-09-15T15:54:51.484107",
//     updatedAt: "2024-09-15T15:54:51.484107",
//   },
//   {
//     id: 4,
//     title: `청색광, 알고 보니 더 교묘하게 내 눈 건강 위협한다?`,
//     shorts: `UNIST 민두영·권태혁·민승규 교수팀, 항산화 시스템 피하는 청색광의 단백질 손상 경로 규명
//     단백질 구조 안에 포획된 산소가 산화 손상 유발 … Nature Communications 게재`,
//     contents: `UNIST 민두영·권태혁·민승규 교수팀, 항산화 시스템 피하는 청색광의 단백질 손상 경로 규명
//     단백질 구조 안에 포획된 산소가 산화 손상 유발 … Nature Communications 게재`,
//     thumbnails:
//       "https://chemistry.unist.ac.kr/wp-content/uploads/2025/01/%EB%AF%BC%EB%91%90%EC%98%81-%EA%B5%90%EC%88%98%ED%8C%80-800x428-1.png",
//     gallery: [],
//     files: [],
//     createdAt: "2025-02-04T15:54:51.484107",
//     updatedAt: "2025-02-04T15:54:51.484107",
//   },
//   {
//     id: 5,
//     title: `미세영역 온도 변화 실시간 포착하는 ‘나노온도계’ 개발`,
//     shorts: `UNIST 권오훈 교수팀, 투과전자현미경 내 미세시료 온도 측정 기술 개발
// 온도계 소재 개선해 실시간 영상 관찰에 적용 가능 … ACS Nano 논문 게재`,
//     contents: `UNIST 권오훈 교수팀, 투과전자현미경 내 미세시료 온도 측정 기술 개발
// 온도계 소재 개선해 실시간 영상 관찰에 적용 가능 … ACS Nano 논문 게재`,
//     thumbnails:
//       "https://chemistry.unist.ac.kr/wp-content/uploads/2025/01/%EA%B6%8C%EC%98%A4%ED%9B%88-%EA%B5%90%EC%88%98%ED%8C%80-800x428-1.png",
//     gallery: [],
//     files: [],
//     createdAt: "2025-01-27T15:54:51.484107",
//     updatedAt: "2025-01-27T15:54:51.484107",
//   },
//   {
//     id: 6,
//     title: `화학과 김지연 박사, 아시안결정학회 신진연구자상 수상`,
//     shorts: `탄소중립 혁신 설계법 ‘Up-Down Approach’ 학계 인정
// Nature Synthesis 실려… ‘Rising Star’ 세션서 성과 발표`,
//     contents: `탄소중립 혁신 설계법 ‘Up-Down Approach’ 학계 인정
// Nature Synthesis 실려… ‘Rising Star’ 세션서 성과 발표`,
//     thumbnails:
//       "https://chemistry.unist.ac.kr/wp-content/uploads/2025/01/JYK_%EC%82%AC%EC%A7%841-3-800x228-1.png",
//     gallery: [],
//     files: [],
//     createdAt: "2024-09-15T15:54:51.484107",
//     updatedAt: "2024-09-15T15:54:51.484107",
//   },
//   {
//     id: 7,
//     title: `청색광, 알고 보니 더 교묘하게 내 눈 건강 위협한다?`,
//     shorts: `UNIST 민두영·권태혁·민승규 교수팀, 항산화 시스템 피하는 청색광의 단백질 손상 경로 규명
//     단백질 구조 안에 포획된 산소가 산화 손상 유발 … Nature Communications 게재`,
//     contents: `UNIST 민두영·권태혁·민승규 교수팀, 항산화 시스템 피하는 청색광의 단백질 손상 경로 규명
//     단백질 구조 안에 포획된 산소가 산화 손상 유발 … Nature Communications 게재`,
//     thumbnails:
//       "https://chemistry.unist.ac.kr/wp-content/uploads/2025/01/%EB%AF%BC%EB%91%90%EC%98%81-%EA%B5%90%EC%88%98%ED%8C%80-800x428-1.png",
//     gallery: [],
//     files: [],
//     createdAt: "2025-02-04T15:54:51.484107",
//     updatedAt: "2025-02-04T15:54:51.484107",
//   },
//   {
//     id: 8,
//     title: `미세영역 온도 변화 실시간 포착하는 ‘나노온도계’ 개발`,
//     shorts: `UNIST 권오훈 교수팀, 투과전자현미경 내 미세시료 온도 측정 기술 개발
// 온도계 소재 개선해 실시간 영상 관찰에 적용 가능 … ACS Nano 논문 게재`,
//     contents: `UNIST 권오훈 교수팀, 투과전자현미경 내 미세시료 온도 측정 기술 개발
// 온도계 소재 개선해 실시간 영상 관찰에 적용 가능 … ACS Nano 논문 게재`,
//     thumbnails:
//       "https://chemistry.unist.ac.kr/wp-content/uploads/2025/01/%EA%B6%8C%EC%98%A4%ED%9B%88-%EA%B5%90%EC%88%98%ED%8C%80-800x428-1.png",
//     gallery: [],
//     files: [],
//     createdAt: "2025-01-27T15:54:51.484107",
//     updatedAt: "2025-01-27T15:54:51.484107",
//   },
//   {
//     id: 9,
//     title: `화학과 김지연 박사, 아시안결정학회 신진연구자상 수상`,
//     shorts: `탄소중립 혁신 설계법 ‘Up-Down Approach’ 학계 인정
// Nature Synthesis 실려… ‘Rising Star’ 세션서 성과 발표`,
//     contents: `탄소중립 혁신 설계법 ‘Up-Down Approach’ 학계 인정
// Nature Synthesis 실려… ‘Rising Star’ 세션서 성과 발표`,
//     thumbnails:
//       "https://chemistry.unist.ac.kr/wp-content/uploads/2025/01/JYK_%EC%82%AC%EC%A7%841-3-800x228-1.png",
//     gallery: [],
//     files: [],
//     createdAt: "2024-09-15T15:54:51.484107",
//     updatedAt: "2024-09-15T15:54:51.484107",
//   },
//   {
//     id: 10,
//     title: `청색광, 알고 보니 더 교묘하게 내 눈 건강 위협한다?`,
//     shorts: `UNIST 민두영·권태혁·민승규 교수팀, 항산화 시스템 피하는 청색광의 단백질 손상 경로 규명
//     단백질 구조 안에 포획된 산소가 산화 손상 유발 … Nature Communications 게재`,
//     contents: `UNIST 민두영·권태혁·민승규 교수팀, 항산화 시스템 피하는 청색광의 단백질 손상 경로 규명
//     단백질 구조 안에 포획된 산소가 산화 손상 유발 … Nature Communications 게재`,
//     thumbnails:
//       "https://chemistry.unist.ac.kr/wp-content/uploads/2025/01/%EB%AF%BC%EB%91%90%EC%98%81-%EA%B5%90%EC%88%98%ED%8C%80-800x428-1.png",
//     gallery: [],
//     files: [],
//     createdAt: "2025-02-04T15:54:51.484107",
//     updatedAt: "2025-02-04T15:54:51.484107",
//   },
//   {
//     id: 11,
//     title: `미세영역 온도 변화 실시간 포착하는 ‘나노온도계’ 개발`,
//     shorts: `UNIST 권오훈 교수팀, 투과전자현미경 내 미세시료 온도 측정 기술 개발
// 온도계 소재 개선해 실시간 영상 관찰에 적용 가능 … ACS Nano 논문 게재`,
//     contents: `UNIST 권오훈 교수팀, 투과전자현미경 내 미세시료 온도 측정 기술 개발
// 온도계 소재 개선해 실시간 영상 관찰에 적용 가능 … ACS Nano 논문 게재`,
//     thumbnails:
//       "https://chemistry.unist.ac.kr/wp-content/uploads/2025/01/%EA%B6%8C%EC%98%A4%ED%9B%88-%EA%B5%90%EC%88%98%ED%8C%80-800x428-1.png",
//     gallery: [],
//     files: [],
//     createdAt: "2025-01-27T15:54:51.484107",
//     updatedAt: "2025-01-27T15:54:51.484107",
//   },
//   {
//     id: 12,
//     title: `화학과 김지연 박사, 아시안결정학회 신진연구자상 수상`,
//     shorts: `탄소중립 혁신 설계법 ‘Up-Down Approach’ 학계 인정
// Nature Synthesis 실려… ‘Rising Star’ 세션서 성과 발표`,
//     contents: `탄소중립 혁신 설계법 ‘Up-Down Approach’ 학계 인정
// Nature Synthesis 실려… ‘Rising Star’ 세션서 성과 발표`,
//     thumbnails:
//       "https://chemistry.unist.ac.kr/wp-content/uploads/2025/01/JYK_%EC%82%AC%EC%A7%841-3-800x228-1.png",
//     gallery: [],
//     files: [],
//     createdAt: "2024-09-15T15:54:51.484107",
//     updatedAt: "2024-09-15T15:54:51.484107",
//   },
];
