<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
</head>
<body>
<script>
    /*------------------------------비밀번호 유효성 시작-------------------------------------*/
$(document).ready(function(){
    $('#c_pwd').on('input', password);
    $('#c_pwd_check').on('input', passwordmatch);

    function password(){
        const password = document.getElementById('c_pwd').value;

        // 비밀번호 유효성 검사 (최소 8자, 대문자, 소문자, 숫자, 특수문자 포함)
        const passwordPattern = /^(?=.*\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&*]).{8,15}$/;

        if (passwordPattern.test(password)) {
            $('#password-check').css('color', 'green').html("비밀번호가 유효합니다.")
            return true;
        } else{
            $('#password-check').css('color', 'red').html("패스워드는 8~15자 사이이며, 숫자, 문자, 특수문자를 포함해야 합니다.")
            return false;
        }
    }

    /* 비밀번호가 일치하는지 검사 */
    function passwordmatch(){
        /* 비밀번호 입력란 */
        const password = document.getElementById('c_pwd').value;
        /* 비밀번호 확인 입력란 */
        const confirmPassword = document.getElementById('c_pwd_check').value;

        if(confirmPassword===''){
            $('#password-match-check').css('color', 'red').html("패스워드는 8~15자 사이이며, 숫자, 문자, 특수문자를 포함해야 합니다.");
        } else if(password === confirmPassword){
            /* 비밀번호 일치여부 메시지 표시 변수 */
            $('#password-match-check').css('color', 'green').html("비밀번호가 일치합니다.");
            return true;
        } else{
            $('#password-match-check').css('color', 'red').html("비밀번호가 일치하지 않습니다.");
            return false;
        }
    }
})

</script>
</body>
</html>
