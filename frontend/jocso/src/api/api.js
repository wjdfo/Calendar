import axios from 'axios';
import { BASE_URL } from './constants';

const api = axios.create({
  baseURL: BASE_URL,
  withCredentials: true, // 쿠키 전송을 위해 필수
});

api.interceptors.response.use(
  (response) => response,
  async (error) => {
    const originalRequest = error.config;

    // 401 에러이고, 재시도한 요청이 아닐 경우
    if (error.response.status === 401 && !originalRequest._retry) {
      originalRequest._retry = true; // 재시도 플래그 설정

      try {
        // 새로운 access token을 요청 (별도의 axios 인스턴스를 사용하지 않아도, refresh 로직은 인터셉터를 통과하지 않음)
        const res = await axios.post(`${BASE_URL}/api/accounts/refresh`, {}, { withCredentials: true });
        
        if (res.status === 200) {
          const { accessToken } = res.data;
          // 새로운 access token으로 기본 헤더 설정
          api.defaults.headers.common['Authorization'] = `Bearer ${accessToken}`;
          // 실패했던 원래 요청의 헤더도 새로운 토큰으로 변경
          originalRequest.headers['Authorization'] = `Bearer ${accessToken}`;
          // 원래 요청 재시도
          return api(originalRequest);
        }
      } catch (refreshError) {
        // refresh token이 유효하지 않은 경우 (로그아웃 처리 필요)
        // 여기서 logout 처리를 직접 호출하기보다, 에러를 반환하여
        // AuthContext나 호출한 컴포넌트에서 처리하도록 유도
        return Promise.reject(refreshError);
      }
    }
    return Promise.reject(error);
  }
);

export default api;
