// src/utils/browser.ts

export const browser = {
  /**
   * 브라우저 새로고침
   */
  refresh: () => {
    window.location.reload();
  },

  /**
   * 특정 경로로 이동
   */
  goTo: (url: string) => {
    window.location.href = url;
  },

  /**
   * 뒤로 가기
   */
  back: () => {
    window.history.back();
  },

  /**
   * 앞으로 가기
   */
  forward: () => {
    window.history.forward();
  },

  /**
   * 새 탭에서 열기
   */
  openInNewTab: (url: string) => {
    window.open(url, "_blank");
  },

  /**
   * 현재 URL 가져오기
   */
  getCurrentUrl: (): string => {
    return window.location.href;
  },

  /**
   * 클립보드에 텍스트 복사
   */
  copyToClipboard: async (text: string) => {
    try {
      await navigator.clipboard.writeText(text);
      return true;
    } catch (err) {
      console.error("클립보드 복사 실패:", err);
      return false;
    }
  },
};
