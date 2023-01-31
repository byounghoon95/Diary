import React, { useState } from "react";
import { useNavigate } from "react-router-dom";

const Login = () => {
  const navigate = useNavigate();
  const [account, setAccount] = useState({
    id: "",
    password: "",
  });

  // e.target.name = input의 name(id,password)
  const onChangeAccount = (e) => {
    setAccount({
      ...account,
      [e.target.name]: e.target.value,
    });
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    navigate("/home");
  };
  return (
    <div className="login-wrapper">
      <h2>Login</h2>
      <form method="get" onSubmit={handleSubmit} id="login-form">
        <input
          type="text"
          name="id"
          onChange={onChangeAccount}
          placeholder="id"
        />
        <input
          type="password"
          name="password"
          onChange={onChangeAccount}
          placeholder="password"
        />
        {/*<label htmlFor="remember-check">*/}
        {/*  <input type="checkbox" id="remember-check" />*/}
        {/*  아이디 저장하기*/}
        {/*</label>*/}
        <input type="submit" value="Login" />
      </form>
    </div>
  );
};
export default Login;
