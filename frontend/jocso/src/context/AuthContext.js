import React, { createContext, useState, useContext, useEffect, useCallback } from 'react';
import api from '../api/api';
import refresh from '../api/refresh';
import loginApi from '../api/login';
import logoutApi from '../api/logout';

const AuthContext = createContext(null);

export const AuthProvider = ({ children }) => {
  const [user, setUser] = useState(null);
  const [loading, setLoading] = useState(true); // 로딩 상태 추가

  const attemptRefresh = useCallback(async () => {
    try {
      const { accessToken } = await refresh();
      api.defaults.headers.common['Authorization'] = `Bearer ${accessToken}`;
      setUser({ loggedIn: true }); // 간단히 로그인 상태만 표시
    } catch (error) {
      setUser(null); // 실패 시 사용자 상태 null
      delete api.defaults.headers.common['Authorization'];
    }
  }, []);

  useEffect(() => {
    const checkLoginStatus = async () => {
      await attemptRefresh();
      setLoading(false); // 상태 확인 후 로딩 종료
    };
    checkLoginStatus();
  }, [attemptRefresh]);

  const login = async (credentials) => {
    try {
      const { accessToken } = await loginApi(credentials);
      api.defaults.headers.common['Authorization'] = `Bearer ${accessToken}`;
      setUser({ loggedIn: true });
    } catch (error) {
      setUser(null);
      delete api.defaults.headers.common['Authorization'];
      throw error; // 로그인 실패 시 에러를 다시 던져서 컴포넌트에서 처리
    }
  };

  const logout = async () => {
    try {
      await logoutApi(); // 서버에 로그아웃 요청
    } catch (error) {
      // 서버와의 통신에 실패하더라도 클라이언트 측에서는 로그아웃 처리를 계속 진행합니다.
      console.error("Logout API call failed:", error);
    } finally {
      // API 요청 성공 여부와 관계없이 클라이언트 상태를 초기화합니다.
      setUser(null);
      delete api.defaults.headers.common['Authorization'];
    }
  };

  const authContextValue = { user, login, logout, loading };

  return (
    <AuthContext.Provider value={authContextValue}>
      {!loading && children}
    </AuthContext.Provider>
  );
};

export const useAuth = () => useContext(AuthContext);
