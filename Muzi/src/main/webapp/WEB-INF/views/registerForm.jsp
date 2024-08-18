<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
    <link href="<c:url value='/css/signup.css'/>" type="text/css" rel="stylesheet"/>

<%--    <link rel="stylesheet" href="/css/signup.css" />--%>
    <title>Signup</title>
</head>
<body>

<div class="container">

    <h2>회원가입</h2>
    <form action="/signup/add" method="post" class="signup-form">
        <p id="auth-check" class="hiddenmsg"></p>
        <div class="form-group">
            <label for="c_email">이메일 <span class="required">*</span></label>
            <input type="hidden" id="c_email" name="c_email"/>
            <input type="text" id="email" name="email" required>
            <select class="domain_list" id="domain_list" name="domain_list">
                <option disabled selected>이메일선택</option>
                <option value="@naver.com">@naver.com</option>
                <option value="@gmail.com">@gmail.com</option>
                <option value="@hanmail.net">@hanmail.net</option>
                <option value="@nate.com">@nate.com</option>
                <option value="@kakao.com">@kakao.com</option>
            </select>
        </div>
        <!-- 메일링 인증 메시지를 표시할 영역 -->
        <p id="resultMessage" class="result-message hiddenmsg"></p>
        <div class="form-group">
            <label for="authCode">인증번호 <span class="required">*</span></label>
            <input type="text" id="authCode" class="authCode" name="authCode" maxlength="10" required>
            <input type="button" id="verify"  value="인증번호 받기" class="verify-btn" required>
        </div>
        <!-- 비밀번호 유효성 검사 결과를 표시 -->
        <p id="password-check" class="feedback hiddenmsg"></p>
        <div class="form-group">
            <label for="c_pwd">비밀번호 <span class="required">*</span></label>
            <input type="password" id="c_pwd" name="c_pwd" required>

        </div>
        <!-- 비밀번호 일치 여부 유효성 검사 메시지를 표시 -->
        <p id="password-match-check" class="feedback hiddenmsg"></p>
        <div class="form-group">
            <label for="c_pwd_check">비밀번호 확인 <span class="required">*</span></label>
            <input type="password" id="c_pwd_check" name="c_pwd_check" required>

        </div>
        <p id="name-check" class="hiddenmsg"></p>
        <div class="form-group">
            <label for="c_name">이름 <span class="required">*</span></label>
            <input type="text" id="c_name" name="c_name" required>
        </div>
        <p id="nick-check" class="hiddenmsg"></p>
        <div class="form-group">
            <label for="c_nick">닉네임 <span class="required">*</span></label>
            <input type="text" id="c_nick" name="c_nick" required>
        </div>

        <div class="form-group">
            <label for="c_birth">생년월일 <span class="required">*</span></label>
            <input type="hidden" id="c_birth" name="c_birth" />
            <div class="birth-gender-group">
                <div class="birth-date-group">
                    <select id="birth_year" name="birth_year" required>
                        <option disabled selected>연</option>
                    </select>
                    <select id="birth_month" name="birth_month" required>
                        <option disabled selected>월</option>
                    </select>
                    <select id="birth_day" name="birth_day" required>
                        <option disabled selected>일</option>
                    </select>
                </div>

                <div class="gender-select-group">
                    <label for="c_gnd">성별 <span class="required">*</span></label>
                    <select class="c_gnd" id="c_gnd" name="c_gnd" required>
                        <option disabled selected>성별</option>
                        <option value="W">여</option>
                        <option value="M">남</option>
                    </select>
                </div>
            </div>
        </div>

        <p id="phn-check" class="hiddenmsg"></p>
        <div class="form-group">
            <label for="c_phn">휴대폰 번호 <span class="required">*</span></label>
            <input type="text" id="c_phn" name="c_phn" required>
        </div>

        <!-- 주소 입력 부분 시작 -->
        <div class="form-group">
            <label for="c_zip">주소</label>
            <div class="address-inputs">
                <div class="zip-search">
                    <input type="text" id="c_zip" name="c_zip" placeholder="우편번호" required>
                    <%@ include file="postcode.jsp" %>
                    <input type="button" class="zip-btn" onclick="postcode()" value="우편번호">
                </div>
                <input type="text" id="c_road_a" name="c_road_a" placeholder="기본주소" required>
                <input type="text" id="c_det_a" name="c_det_a" placeholder="나머지주소" required>
            </div>
        </div>
        <!-- 주소 입력 부분 끝 -->
        <p id="finalMsg" class="finalMessage hiddenmsg"></p>
        <button type="submit" class="submit-btn" id="submit-btn">가입하기</button>
    </form>
</div>
<script>
    let getdate = new Date();

    /* year 리스트 목록 생성여부 false */
    let isYearOption = false;
    let isMonthOption = false;

    /* 생년월일 리스트목록을 생성하는 function */
    function birthOption(selectElement, start, end){
        /* 기존옵션을 초기화 */
        selectElement.innerHTML = '';
        for(let i = start; i<=end; i++){
            const option = document.createElement('option');
            option.value=i;
            option.innerText=i;
            selectElement.appendChild(option);
        }
    }

    /* 출생연도 option 포커스되었을때 이벤트 생성*/
    document.querySelector('#birth_year').addEventListener('focus', function (){
        if(!isYearOption){
            birthOption(this, 1950, getdate.getFullYear());
            isYearOption=true;
        }
    })
    /* 출생월 option 포커스되었을때 이벤트 생성*/
    document.querySelector('#birth_month').addEventListener('focus', function(){
        if(!isMonthOption){
            birthOption(this,1,12);
            isMonthOption=true;
        }
    })
    /* 출생일 option 포커스되었을때 이벤트 생성*/
    document.querySelector('#birth_day').addEventListener('focus',function (){
        /* year 와 month의 selected value를 가져온다 */
        let year = $('#birth_year option:selected').val();
        let month = $('#birth_month option:selected').val();
        /* 값이 존재할경우 */
        if(year && month){
            /* 새로운 Date를 생성하여 선택한 마지막 일자를 가져온다 */
            const lastDay = new Date(year, month, 0).getDate();
            /* birth_day를 1일부터 마지막날짜까지 option 추가 */
            birthOption(document.querySelector('#birth_day'), 1, lastDay);
        }
    })

    /* 생년월일의 value를 저장해서 서버로 넘긴다 */
    function combineDate(){
        let birth_year = document.getElementById("birth_year").value;
        let birth_month = document.getElementById("birth_month").value;
        let birth_day = document.getElementById("birth_day").value;

        if(!(birth_year && birth_month && birth_day)){
            return null;
        }

        /* year, month, day를 문자열로 변환 */
        birth_year = birth_year.toString();
        birth_month = birth_month.toString();
        birth_day = birth_day.toString();

        /* month와 day의 길이가 1인경우 앞에 0을 붙인다 */
        if(birth_month.length===1){
            birth_month='0'+birth_month;
        }
        if(birth_day.length===1){
            birth_day='0'+birth_day;
        }

        /* year,month,day 문자열 합치기 */
        let birth_date = birth_year+birth_month+birth_day;
        /* c_birth 컬럼에 문자열 넣기 */
        document.getElementById("c_birth").value = birth_date;
        return birth_date;
    }

    /* birth_day 태그에 값이 변하면 c_birth 에 연월일 값이 저장된다 */
    document.querySelector('#birth_day').addEventListener('change', function(){
        combineDate();
    })


    /* 이메일 중복체크 */
    $('#email').on('input', function(){
        emailCheck();
    })
    $('#domain_list').on('change', function(){
        emailCheck();
    });



    function emailCheck(){
        /* email과 도메인을 변수에 저장 */
        let email = document.getElementById("email").value.trim();    //trim(): email의 양쪽 공백을 제거
        let domain = document.getElementById("domain_list").value;
        /* 이메일 유효성검사 메시지 출력 */
        let authCheck = document.getElementById("auth-check");
        /* 인증번호 받기 버튼 */
        let verifyBtn = $('#verify');
        let submitBtn = $('#submit-btn');


        /* email이 공백일경우 */
        if(!email){
            authCheck.style.color = "red";
            authCheck.innerHTML = "이메일을 입력해주세요";
            /* 버튼 비활성화 */
            verifyBtn.prop('disabled', true);
            submitBtn.prop('disabled', true);
            return;
        }

        /* 이메일과 도메인을 합쳐 c_email에 저장 */
        let c_email = document.getElementById("c_email").value= email+domain;

        /* 이메일 패턴 작성 */
        let emailPattern = /^[\w-\.]+@([\w-]+\.)+[\w-]{2,4}$/;

        /* 이메일 패턴이 유효하지 않을 경우 */
        if(!emailPattern.test(c_email)){
            authCheck.style.color = "red";
            authCheck.innerHTML = "이메일 형식을 다시 확인해주세요";
            /* 버튼 비활성화 */
            verifyBtn.prop('disabled', true);
            submitBtn.prop('disabled', true);
            return;
        }

        console.log("c_email: ",c_email);

        /* 이메일 패턴이 유효한 경우 */
        /* 이메일 중복검사 */
        $.ajax({
            /*    /signup/emailDumpCheck 경로로 POST 요청 */
            type: "get",
            url : "/signup/emailDumpCheck",
            data: {
                "c_email":c_email
            },
            /* 성공할경우 */
            success: function(result){
                console.log("result: ",result);
                /* DB에 중복 메일이 없는경우 result는 true */
                if(result===true){
                    authCheck.style.color = "green";
                    authCheck.innerHTML = "인증받기를 진행해주세요.";
                    /* 인증번호 받기 버튼 활성화 */
                    verifyBtn.prop('disabled', false);
                    submitBtn.prop('disabled', false);
                } else {
                    /* DB에 중복 메일이 있는 경우 result는 false */
                    authCheck.style.color = "red";
                    authCheck.innerHTML = "이미 사용중인 이메일입니다.";
                    /* 버튼 비활성화 */
                    verifyBtn.prop('disabled', true);
                    submitBtn.prop('disabled', true);
                }
            },
            error: function (err){
                console.log("에러발생", err);
                authCheck.style.color = "red";
                authCheck.innerHTML = "서버에 문제가 발생하였습니다.";
                /* 버튼 비활성화 */
                verifyBtn.prop('disabled', true);
                submitBtn.prop('disabled', true);
            }
        });
    }

    $('#submit-btn').on('click', function (e){
        let finalMsg = document.getElementById("finalMsg");

        /* 패스워드 유효성검사와, 비밀번호 1, 2가 서로 일치하는지 검사하는 함수를 호출한다 */
        const isPasswordValid = password();
        const isPasswordMatch = passwordmatch();

        /* 인증번호 받기 버튼이 disabled 일경우 || 비밀번호 유효성검사가 false일경우 */
        if(!isAuthVerify || !isPasswordValid || !isPasswordMatch){
            /* 회원가입폼 제출 중단 */
            e.preventDefault();
            /* 이메일 인증여부가 false 일경우 */
            if(!isAuthVerify){
                finalMsg.innerHTML = "이메일 인증을 완료해주세요";
            }
            if(!isPasswordValid){
                finalMsg.innerHTML = "비밀번호를 다시 확인해주세요";
            }
            if(!isPasswordMatch){
                finalMsg.innerHTML = "비밀번호가 일치하지않습니다";
            }

        }
    })

    /* 비밀번호 유효성검사 */
    document.getElementById('c_pwd').addEventListener('input', password);
    document.getElementById('c_pwd_check').addEventListener('input', passwordmatch);

    function password(){
        const password = document.getElementById('c_pwd').value;
        const passwordcheck = document.getElementById('password-check');

        // 비밀번호 유효성 검사 (최소 8자, 대문자, 소문자, 숫자, 특수문자 포함)
        const passwordPattern = /^(?=.*\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&*]).{8,15}$/;

        if (passwordPattern.test(password)) {
            passwordcheck.style.color = "green";
            passwordcheck.innerHTML = "비밀번호가 유효합니다.";
            return true;
        } else{
            passwordcheck.style.color = "red";
            passwordcheck.innerHTML = "패스워드는 8~15자 사이이며, 숫자, 문자, 특수문자를 포함해야 합니다.";
            return false;
        }
    }

    /* 비밀번호가 일치하는지 검사 */
    function passwordmatch(){
        /* 비밀번호 입력란 */
        const password = document.getElementById('c_pwd').value;
        /* 비밀번호 확인 입력란 */
        const confirmPassword = document.getElementById('c_pwd_check').value;

        /* 비밀번호 일치여부 메시지 표시 변수 */
        const checkpasswordmatch = document.getElementById('password-match-check');

        if(password === confirmPassword){
            checkpasswordmatch.style.color = "green";
            checkpasswordmatch.innerHTML = "비밀번호가 일치합니다."
            return true;
        } else{
            checkpasswordmatch.style.color = "red";
            checkpasswordmatch.innerHTML = "비밀번호가 일치하지 않습니다."
            return false;
        }
    }

    /* 이메일 인증 여부를 확인하는 변수 */
    let isAuthVerify = false;


    /* 이메일 인증번호 요청 */
    $('#verify').on('click', function(){
        let email = $('#c_email').val(); /* 고객 이메일 */
        console.log('완성된 이메일 : ' + email); /* 고객이메일 콘솔 출력 */
        const verify = $('#verify') /* 고객이 인증번호 입력하는태그 */

        $.ajax({
            type:"get",
            url: "/signup/mailAuth?email="+email,
            success: function(response) {
                verify.attr('disabled', true);
                console.log(response);
                $('#resultMessage').html(response);
                $('#resultMessage').css('color', 'green');
                // 이메일 인증번호 요청 후 인증번호 입력 받기
                $('#authCode').on('input', function() {
                    authNumber(); // 인증번호 입력 시 검증 함수 호출
                });
            },
            error: function (xhr){
                /* 인증실패시 */
                let errorMsg = xhr.responseText;
                $('#resultMessage').html(errorMsg);
                $('#resultMessage').css('color', 'red');
                isAuthVerify=false;
                console.log('인증실패:', xhr.responseText);
            }
        })
    });

    function authNumber(){
        /* 고객이 입력한 인증번호 */
        let inputCode = $('#authCode').val().trim();
        let email = $('#c_email').val().trim();

        if(inputCode==null){
            $('#resultMessage').css('color', 'red');
            $('#resultMessage').html("인증번호를 입력해주세요");
        }

        console.log("전송할 데이터:", JSON.stringify({email: email, authCode: inputCode}));

        $.ajax({
            type:"POST",
            url: "/signup/verifyAuthCode",
            contentType: 'application/json; charset=UTF-8',
            /* 서버에 전송 */
            data: JSON.stringify({email: email, authCode: inputCode}),
            success: function (response){
                /* 인증성공시 */
                $('#resultMessage').html(response);
                $('#resultMessage').css('color', 'green');
                isAuthVerify=true;
            },
            error: function (xhr){
                /* 인증실패시 */
                let errorMsg = xhr.responseText;
                $('#resultMessage').html(errorMsg);
                $('#resultMessage').css('color', 'red');
                isAuthVerify=false;
                console.log('인증실패:', xhr.responseText);
            }
        });
        /* ajax 요청이 비동기이므로 기본폼 제출 막기 */
        return false;
    }

    /* 이름 */
    $('#c_name').on('input', function(){
        let name = $('#c_name').val().trim();
        if(name===""){
            /* input 메시지 띄우기 */
            $('#name-check').css('color','red');
            $('#name-check').html('이름을 입력해주세요');
        } else {
            $('#name-check').html("");
        }
    })

    /* 닉네임 체크 */
    $('#c_nick').on('input', function (){
        let nick = document.getElementById("c_nick").value.trim();
        if(nick===""){
            $('#nick-check').css('color','red');
            $('#nick-check').html('닉네임을 입력해주세요');
        } else {
            $('#nick-check').html("");
        }
    })

    /* 핸드폰 체크 */
    $('#c_phn').on('input', function (){
        let phn = document.getElementById("c_phn").value.trim();
        let phnCheck = document.getElementById("phn-check");
        if(phn===""){
            $('#phn-check').css('color','red');
            $('#phn-check').html('핸드폰번호를 입력해주세요');
        } else {
            $('#phn-check').html("");
        }
    })


</script>
</body>
</html>
