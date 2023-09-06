function logoutNaver() {
    if (confirm("네이버 로그아웃 페이지로 이동하시겠습니까?")) {
        fetch('/naver/logout',{
            method: 'POST'
        })
        .then(response => {
            if(response.ok) {
                window.open("https://nid.naver.com/nidlogin.logout", "_blank");
                window.location.reload();
            } else {
                console.error('세션 종료 실패');
            }
        })
        .catch(error => console.error('에러 발생:', error));
    }



}