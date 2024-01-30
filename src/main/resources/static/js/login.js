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

// 회원가입 유효성 검사
$(document).ready(function() {
    // 검사관련 변수
    let timeoutEmail = null;
    let timeoutPw1 = null;
    let timeoutPw2 = null;
    let timeoutName = null;
    let userEmail = '';
    let userPw1 = '';

    // 완료버튼 활성화 변수
    var isEmailValid = false;
    var isPasswordValid = false;
    var isConfirmPasswordValid = false;
    var isUsernameValid = false;

    // 이메일 유효성, 중복 검사
    $('#userEmail').on('input', function() {
        userEmail = $(this).val();
        clearTimeout(timeoutEmail);
        timeoutEmail = setTimeout(() => {
            var email = $(this).val();

            if (emailRegexTest(email)) {
                $.ajax({
                    url: '/emailDupCheck',
                    type: 'GET',
                    data: {userEmail: email },
                    success: function(response) {
                        if (response.duplicate) {
                            $('#error_msg_email .error').text('이미 사용 중인 이메일 주소입니다.');
                        } else {
                            $('#error_msg_email .error').text('');
                            isEmailValid = true;
                            updateSubmitButtonState();
                        }
                    },
                    error: function() {
                        $('#error_msg_email .error').text('서버 오류가 발생했습니다.');
                    }
                });
            } else {
                $('#error_msg_email .error').text('올바른 이메일 형식을 입력해주세요.');
            }
        }, 500);
    });

    function emailRegexTest(email) {
        var regex = /^[\w-\.]+@([\w-]+\.)+[\w-]{2,4}$/;
        return regex.test(email);
    }


    // 비밀번호 유효성 검사
    $('#userPw1').on('input', function() {
        clearTimeout(timeoutPw1);
        timeoutPw1 = setTimeout(() => {
            var pw1 = $(this).val();
            userPw1 = pw1;

            if (userEmail === pw1) {
                $('#error_msg_pw .error').text('비밀번호는 이메일과 달라야 사용할 수 있습니다.');
            } else {
                if (pwRegexTest(pw1)) {
                    $('#error_msg_pw .error').text('');
                    isPasswordValid = true;
                    updateSubmitButtonState();
                } else {
                    $('#error_msg_pw .error').html('비밀번호는 8~16자이고, 영문, 숫자, 특수문자<br>하나씩을 포함해야 사용할 수 있습니다.');
                }
            }
        }, 500);
    });

    function pwRegexTest(pw1) {
        var regex = /^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,16}$/;
        return regex.test(pw1);
    }


    // 비밀번호 동일여부 확인 검사
    $('#userPw2').on('input', function() {
        clearTimeout(timeoutPw2);
        timeoutPw2 = setTimeout(() => {
            var pw2 = $(this).val();

            if (userPw1 === pw2) {
                $('#error_msg_pwr .error').text('');
                isConfirmPasswordValid = true;
                updateSubmitButtonState();
            } else {
                $('#error_msg_pwr .error').text('비밀번호가 일치하지 않습니다.');
            }
        }, 500);
    });


    // 닉네임 유효성, 중복 검사
    $('#userName').on('input', function() {
        var name = $(this).val();
        var nameLength = 0;
        var specialCheck = /[`~!@#$%^&*|\\\'\";:/?]/gi;

        // 한글은 길이 2, 영문 및 기타 문자는 길이 1로 계산
        for (var i = 0; i < name.length; i++) {
            var ch = name.charAt(i);
            if (escape(ch).length > 4) {
                nameLength += 2;
            } else {
                nameLength += 1;
            }
        }

        clearTimeout(timeoutName);
        timeoutName = setTimeout(() => {

            if (name == null || name == "") {
                $('#error_msg_name .error').text('닉네임 입력은 필수입니다.');
                return;
            }
            if (name.search(/\s/) != -1) {
                $('#error_msg_name .error').text("닉네임은 빈 칸을 포함할 수 없습니다.");
                return;
            }
            if (nameLength < 2 || nameLength > 20) {
                $('#error_msg_name .error').text("닉네임은 한글 1~10자, 영문 및 숫자 2~20자 입니다.");
                return;
            }
            if (specialCheck.test(name)) {
                $('#error_msg_name .error').text("닉네임은 특수문자를 포함할 수 없습니다.");
                return;
            }

            $.ajax({
                url: '/nameDupCheck',
                type: 'GET',
                data: {userName: name},
                success: function(response) {
                    if (response.duplicate) {
                        $('#error_msg_name .error').text('이미 사용 중인 닉네임입니다.');
                    } else {
                        $('#error_msg_name .error').text('');
                        isUsernameValid = true;
                        updateSubmitButtonState();
                    }
                },
                error: function() {
                    $('#error_msg_name .error').text('서버 오류가 발생했습니다.');
                }
            });
        }, 500);
    });

    function updateSubmitButtonState() {

        if (isEmailValid && isPasswordValid && isConfirmPasswordValid && isUsernameValid) {
            $('.input-submit').prop('disabled', false);
        } else {
            $('.input-submit').prop('disabled', true);
        }
    }
});
