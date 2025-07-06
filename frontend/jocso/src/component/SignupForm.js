import React, { useState } from 'react';

function SignupForm({ onSignup }) {
  const [userInfo, setUserInfo] = useState({
    name: '',
    email: '',
    password: '',
    passwordConfirm: '',
  });

  const handleChange = (e) => {
    const { name, value } = e.target;
    setUserInfo(prevState => ({ ...prevState, [name]: value }));
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    if (userInfo.password !== userInfo.passwordConfirm) {
      alert('Passwords do not match!');
      return;
    }
    // passwordConfirm 필드를 제외하고 부모에게 전달
    const { passwordConfirm, ...signupData } = userInfo;
    onSignup(signupData);
  };

  return (
    <form onSubmit={handleSubmit} style={styles.form}>
      <div style={styles.inputGroup}>
        <label htmlFor="name" style={styles.label}>Name</label>
        <input
          type="text"
          id="name"
          name="name"
          value={userInfo.name}
          onChange={handleChange}
          style={styles.input}
          required
        />
      </div>
      <div style={styles.inputGroup}>
        <label htmlFor="email" style={styles.label}>Email*</label>
        <input
          type="email"
          id="email"
          name="email"
          value={userInfo.email}
          onChange={handleChange}
          style={styles.input}
          required
        />
      </div>
      <div style={styles.inputGroup}>
        <label htmlFor="password" style={styles.label}>Password*</label>
        <input
          type="password"
          id="password"
          name="password"
          value={userInfo.password}
          onChange={handleChange}
          style={styles.input}
          required
        />
      </div>
      <div style={styles.inputGroup}>
        <label htmlFor="passwordConfirm" style={styles.label}>Password again*</label>
        <input
          type="password"
          id="passwordConfirm"
          name="passwordConfirm"
          value={userInfo.passwordConfirm}
          onChange={handleChange}
          style={styles.input}
          required
        />
      </div>
      <button type="submit" style={styles.signupButton}>Sign Up</button>
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
  signupButton: {
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
};

export default SignupForm;
