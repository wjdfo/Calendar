import api from './api';

const logoutApi = async () => {
  // 백엔드에 로그아웃 요청을 보내 refresh token을 무효화하도록 합니다.
  // 별도의 응답 데이터가 필요하지 않을 수 있습니다.
  return api.post('/api/accounts/logout');
};

export default logoutApi;
