import React, {useEffect, useState} from "react";
import { useNavigate } from "react-router-dom";

const Join = () => {
  const navigate = useNavigate();
  const [account, setAccount] = useState({
    id: "",
    password: "",
  });
  const inputStyle = {
    width: 'calc(100% - 60px)',
    marginBottom:0
  }
  const [check, setCheck] = useState({
    idChk: "",
    pwChk: "",
  });

  // e.target.name = input의 name(id,password)
  const onChangeAccount = (e) => {
    setAccount({
      ...account,
      [e.target.name]: e.target.value,
    });
  };

  const login = () => {
    navigate("/");
  };

  const doubleCheck = () => {
    if (account.id.length >= 5) {
      setCheck({
        ...check,
        idChk: "중복된 아이디가 없습니다"
      });
    } else {
      setCheck({
        ...check,
        idChk: "아이디가 중복됩니다"
      });
    }
  }

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
        <button type="button" className="chk-btn" onClick={doubleCheck}>중복확인</button>
        <span id="id-chk" name="idChk"></span>

        <div>Password</div>
        <input
          type="password"
          name="password"
          style={{marginBottom:0}}
          onChange={onChangeAccount}
          placeholder="password"
        />
        <span id="pw-chk" name="pwChk"></span>
        <div>Name</div>
        <input
            type="text"
            name="name"
            onChange={onChangeAccount}
            placeholder="name"
        />
        <input type="submit" value="회원가입" />
        <div className="login-box">
          <span onClick={login}>로그인</span><span> | </span><span>아이디 찾기</span><span> | </span><span>비밀번호 찾기</span>
        </div>
      </form>
    </div>
  );
};
export default Join;
