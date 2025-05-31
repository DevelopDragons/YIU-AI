import { css } from "@emotion/react";

// 교수 그리드 스타일
export const professorGrid = css({
  display: "grid",
  gridTemplateColumns: "repeat(auto-fill, minmax(350px, 1fr))", // 그리드 2개씩
  gap: 50, // 항목 간 간격
  paddingTop: 50,
  paddingBottom: 100,
});

export const assistantProfessorGrid = css({
  display: "grid",
  gridTemplateColumns: "repeat(auto-fill, minmax(180px, 1fr))", // 그리드 2개씩
  gap: 50, // 항목 간 간격
  paddingTop: 50,
  paddingBottom: 100,
});
