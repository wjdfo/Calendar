import api from './api';

const signup = async (userInfo) => {
  const response = await api.post('/api/accounts/signup', userInfo);
  return response.data;
};

export default signup;
