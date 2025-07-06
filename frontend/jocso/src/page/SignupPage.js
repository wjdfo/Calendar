import React from 'react';
import { useNavigate } from 'react-router-dom';
import SignupForm from '../component/SignupForm';
import signupApi from '../api/signup';

function SignupPage() {
  const navigate = useNavigate();

  const handleSignup = async (userInfo) => {
    try {
      await signupApi(userInfo);
      alert('회원가입이 완료되었습니다. 로그인 페이지로 이동합니다.');
      navigate('/login'); // 회원가입 성공 시 로그인 페이지로 이동
    } catch (error) {
      console.error("Signup failed:", error);
      // 사용자에게 에러 메시지 표시
      alert(error.response?.data?.message || '회원가입에 실패했습니다.');
    }
  };

  return (
    <div style={styles.container}>
      <div style={styles.card}>
        <h1 style={styles.title}>Sign Up</h1>
        <SignupForm onSignup={handleSignup} />
      </div>
    </div>
  );
}

// 스타일은 그대로 유지합니다.
const styles = {
  container: {
    display: 'flex',
    justifyContent: 'center',
    alignItems: 'center',
    minHeight: '100vh',
    backgroundColor: '#f5f5f5',
  },
  card: {
    backgroundColor: 'rgba(255, 255, 255, 0.5)',
    borderRadius: '40px',
    padding: '40px',
    boxShadow: '0px 4px 4px rgba(0, 0, 0, 0.25)',
    textAlign: 'center',
    width: '1103px',
    height: '741px',
    display: 'flex',
    flexDirection: 'column',
    justifyContent: 'center',
    alignItems: 'center',
  },
  title: {
    fontSize: '64px',
    fontWeight: 'bold',
    color: '#6CBF58',
    marginBottom: '20px',
  },
};

export default SignupPage;
