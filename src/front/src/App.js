import React from 'react';
import axios from 'axios';

const onNaverLogin = () => {
    window.location.href = 'http://localhost:8080/oauth2/authorization/naver';
};

const onGoogleLogin = () => {
    window.location.href = 'http://localhost:8080/oauth2/authorization/google';
};

function App() {
    return (
        <>
            <button onClick={onNaverLogin}>NAVER LOGIN</button>
            <button onClick={onGoogleLogin}>GOOGLE LOGIN</button>
        </>
    );
}

export default App;
