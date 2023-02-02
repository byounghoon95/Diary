import React, { useState } from "react";
import { useNavigate } from "react-router-dom";

const Join = () => {
  const navigate = useNavigate();
  const [account, setAccount] = useState({
    id: "",
    password: "",
  });
  const inputStyle = {
    width: 'calc(100% - 60px)',
  }

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
      <h2>JOIN</h2>
      <form method="get" onSubmit={handleSubmit} id="login-form">
        <div>Id</div>
        <input
          type="text"
          name="id"
          style={inputStyle}
          onChange={onChangeAccount}
          placeholder="id"
        />
        <button className="chk-btn">중복확인</button>

        <div>Password</div>
        <input
          type="password"
          name="password"
          style={{marginBottom:0}}
          onChange={onChangeAccount}
          placeholder="password"
        />
        <spna>sadfdsf</spna>
        <input  type="submit" value="Login" />
      </form>
    </div>
  );
};
export default Join;
