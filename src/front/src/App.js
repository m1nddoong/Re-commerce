import React from 'react';
import axios from 'axios';

const onNaverLogin = () => {
    window.location.href = 'http://localhost:8080/oauth2/authorization/naver';
};

const updateProfile = () => {
    fetch("http://localhost:8080/jwt/verify", {
        method: "GET",
        credentials: "include" // 쿠키를 포함하여 요청을 보냅니다
    })
        .then((res) => res.json()) // 'res'로 수정
        .then((data) => {
            alert(data);
        })
        .catch((error) => alert(error));
}

function App() {
    return (
        <>
            <button onClick={onNaverLogin}>NAVER LOGIN</button>
            <button onClick={updateProfile}>프로필 업데이트</button>
        </>
    );
}

export default App;
