import api from './api';

const refresh = async () => {
  const response = await api.post('/api/accounts/refresh');
  return response.data;
};

export default refresh;
