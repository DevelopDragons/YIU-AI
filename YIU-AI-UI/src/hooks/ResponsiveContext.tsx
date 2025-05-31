import React, { createContext, useContext, useState, useEffect } from "react";
import { useMediaQuery } from "react-responsive";

// 반응형 상태를 관리할 Context
interface ResponsiveContextType {
  isMobile: boolean;
  isTablet: boolean;
  isDesktopOrLaptop: boolean;
  isNotMobile: boolean;
}

// 'children' 프로퍼티를 포함한 타입 정의
export const ResponsiveContext = createContext<
  ResponsiveContextType | undefined
>(undefined);

interface ResponsiveProviderProps {
  children: React.ReactNode; // children을 받을 수 있도록 타입 추가
}

export const ResponsiveProvider: React.FC<ResponsiveProviderProps> = ({
  children,
}) => {
  const isDesktopOrLaptop = useMediaQuery({ minWidth: 992 });
  const isTablet = useMediaQuery({ minWidth: 768, maxWidth: 991 });
  const isMobile = useMediaQuery({ maxWidth: 767 });
  const isNotMobile = useMediaQuery({ minWidth: 768 });

  const value = {
    isMobile,
    isTablet,
    isDesktopOrLaptop,
    isNotMobile,
  };

  return (
    <ResponsiveContext.Provider value={value}>
      {children}
    </ResponsiveContext.Provider>
  );
};

// Context에서 값 추출하는 커스텀 훅
export const useResponsive = (): ResponsiveContextType => {
  const context = useContext(ResponsiveContext);
  if (!context) {
    throw new Error("useResponsive must be used within a ResponsiveProvider");
  }
  return context;
};
