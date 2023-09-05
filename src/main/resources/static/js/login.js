function handleNaverLogout() {
    const accessToken = localStorage.getItem('access');
    if (!accessToken) {
        console.error("접근 토큰 없음.");
        return;
    }

    // AJAX를 이용하여 백엔드에 로그아웃 요청을 보낸다.
    var xhr = new XMLHttpRequest();
    xhr.open('POST', '/auth/naver/logout', true);
    xhr.setRequestHeader('Content-Type', 'application/json');

    xhr.onload = function() {
        if (xhr.status >= 200 && xhr.status < 400) {
            // 로그아웃 성공
            localStorage.removeItem('access');
            console.log('로그아웃 성공');
            alert('로그아웃 되었습니다.');
            window.location.href = "/";
        } else {
            // 서버에서 오류 응답
            console.error('로그아웃 중 에러 발생', xhr.responseText);
            alert('로그아웃에 실패했습니다.');
            window.location.href = "/";
        }
    };

    xhr.onerror = function() {
        // 연결 중 문제 발생
        console.error('로그아웃 요청 실패');
        alert('로그아웃에 실패했습니다.');
        window.location.href = "/";
    };

    xhr.send(JSON.stringify(accessToken));
}
