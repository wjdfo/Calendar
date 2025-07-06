import React, { useState } from 'react';

// LoginForm은 이제 onLogin 함수를 prop으로 받습니다.
function LoginForm({ onLogin }) {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');

  const handleSubmit = (e) => {
    e.preventDefault();
    // 부모 컴포넌트(LoginPage)로부터 받은 onLogin 함수를 호출합니다.
    onLogin({ email, password });
  };

  return (
    <form onSubmit={handleSubmit} style={styles.form}>
      <div style={styles.inputGroup}>
        <label htmlFor="email" style={styles.label}>Email*</label>
        <input
          type="email"
          id="email"
          value={email}
          onChange={(e) => setEmail(e.target.value)}
          style={styles.input}
          required
        />
      </div>
      <div style={styles.inputGroup}>
        <label htmlFor="password" style={styles.label}>Password*</label>
        <input
          type="password"
          id="password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
          style={styles.input}
          required
        />
      </div>
      <button type="submit" style={styles.loginButton}>Log In</button>
      <button type="button" style={styles.backButton}>Back</button>
    </form>
  );
}

// 스타일은 그대로 유지합니다.
const styles = {
  form: {
    display: 'flex',
    flexDirection: 'column',
    alignItems: 'center',
    width: '100%',
    maxWidth: '593px',
  },
  inputGroup: {
    width: '100%',
    marginBottom: '20px',
    textAlign: 'left',
  },
  label: {
    fontSize: '18px',
    fontWeight: 'bold',
    color: '#000',
    marginBottom: '8px',
    display: 'block',
  },
  input: {
    width: '100%',
    padding: '15px',
    borderRadius: '100px',
    border: '1px solid #000',
    backgroundColor: '#FFF',
    fontSize: '16px',
  },
  loginButton: {
    backgroundColor: 'rgba(66, 119, 54, 0.8)',
    color: '#FFF',
    fontSize: '19px',
    fontWeight: 'bold',
    padding: '15px 30px',
    borderRadius: '100px',
    border: '1px solid #FFF',
    cursor: 'pointer',
    marginTop: '20px',
    width: '138px',
    height: '51px',
  },
  backButton: {
    backgroundColor: 'rgba(66, 119, 54, 0.8)',
    color: '#FFF',
    fontSize: '19px',
    fontWeight: 'bold',
    padding: '15px 30px',
    borderRadius: '100px',
    border: '1px solid #FFF',
    cursor: 'pointer',
    marginTop: '10px',
    width: '138px',
    height: '51px',
  },
};

export default LoginForm;
