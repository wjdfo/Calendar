import React from 'react';
import { Link, useNavigate } from 'react-router-dom';
import LoginForm from '../component/LoginForm';
import { useAuth } from '../context/AuthContext';

function LoginPage() {
  const navigate = useNavigate();
  const { login } = useAuth();

  const handleLogin = async (credentials) => {
    try {
      await login(credentials);
      navigate('/'); // 로그인 성공 시 메인 페이지로 이동
    } catch (error) {
      console.error("Login failed:", error);
      // 사용자에게 에러 메시지 표시 (예: alert, toast)
      alert('로그인에 실패했습니다. 아이디와 비밀번호를 확인해주세요.');
    }
  };

  return (
    <div style={styles.container}>
      <div style={styles.card}>
        <h1 style={styles.title}>Log In</h1>
        <LoginForm onLogin={handleLogin} />
        <p style={styles.signupText}>
          Not a member yet? <Link to="/signup" style={styles.signupLink}>Sign Up Here</Link>
        </p>
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
  signupText: {
    marginTop: '20px',
    fontSize: '18px',
    fontWeight: 'bold',
    color: '#000',
  },
  signupLink: {
    color: '#000',
    textDecoration: 'none',
    marginLeft: '5px',
  },
};

export default LoginPage;
