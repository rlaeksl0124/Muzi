<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="loginOutLink" value="${sessionScope.c_email==null ? '/login' : '/logout'}"/>
<c:set var="loginOut" value="${sessionScope.c_email==null ? '로그인' : '로그아웃'}"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
    <link href="<c:url value='/css/login.css'/>" type="text/css" rel="stylesheet"/>
    <title>login</title>
</head>
<body>
<%-- attr 컨트롤러에서 msg로 전달받은 값이 비어있지 않을때만 코드를 실행 --%>
<c:if test="${not empty msg}">
    <script type="text/javascript">
        var message = "${msg}";
        alert(message);
    </script>
</c:if>


<div id="snap-sync-body" class="snap-sync-pc">
    <%@ include file="../header.jspf"%>
    <div id="snap-sync-wrapper">
        <div class="snap-sync-container">
            <div class="snap-sync-content snap-sync-login-type">
                <div class="snap-sync-logo">
                    <a href="/">
                        <img src="">
                    </a>
                </div>
                <div class="sync-content-area">
                    <div class="sync-info-area">
                        <div class="sync-info-text">
                            <div class="sync-info-tit">
                                <span>간편하게 로그인하기</span>
                            </div>
                            <div class="sync-info-con">
                                <div>
                                    <span>귀찮은 입력 없이</span>
                                </div>
                                <div>
                                    <span>카카오로 쉽고 빠르게 로그인해보세요.</span>
                                </div>
                            </div>
                        </div>
                        <div class="sync-buttons">
                            <a href="/kakao/login">
                                <img id="kakao" src="<c:url value='/img/kakao_login_medium_wide.png'/>" alt="카카오로 로그인" style="max-width: 100%; height: auto;">
                            </a>
                        </div>
                        <div class="sync-info-image">
                            <div class="sync-info-img">
                                <img src="https://cdn.snapfit.co.kr/stores/mayblue/event_image/KakaoTalk_20210609_113914857.jpg">
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="snap-sync-line vertical"></div>
            <div class="sync-login-area">
                <div>
                    <div class="sync-login-form">
                        <div class="sync-login-tab">
                            <div class="tab-button">
                                <a class="active">일반회원 로그인</a>
                            </div>
                            <form action="<c:url value='/login'/>" method="post">
                                <div class="sync-input-box">
                                    <input type="hidden" name="toURL" value="${param.toURL}">
                                    <div class="sync-form-input">
                                        <input type="text" name="c_email" maxlength="30" class="idInput" value placeholder="이메일을 입력해주세요." required/>
                                    </div>
                                    <div class="sync-form-input">
                                        <input type="password" name="c_pwd" maxlength="40" class="pwdInput" value placeholder="비밀번호를 입력해주세요." required/>
                                    </div>
                                    <button type="submit" class="loginBtn">로그인</button>
                                    <div class="searchId_pwd">
                                        <a href="">
                                            이메일 찾기
                                            <span class="division">|</span>
                                            비밀번호 찾기
                                        </a>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
                <div class="snap-sync-line2 vertical"></div>
                <div class="sync-sns-login">
                    <div class="sync-sns-tit">
                        <span>SNS 로그인</span>
                    </div>
                    <div class="sync-sns-buttons btn-type">
                        <div class="sns-btn-div"><button type="button" class="sns-btn sync-sns-naver naver">네이버 계정으로 로그인</button></div>
                        <div class="sns-btn-div"><button type="button" class="sns-btn sync-sns-apple">애플 계정으로 로그인</button></div>
                    </div>
                </div>
                <div class="sync-join-button-area">
                    <div class="sync-join-button">
                        <a href="/signup/add" class="sync-join-btn">
                            <span>회원가입</span>
                            <span class="move-arrow">></span>
                        </a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script>
    let loginFail = "${loginFail}"
    if(loginFail == "msg") alert("아이디 또는 비밀번호를 잘못 입력하셨습니다.");

</script>
</body>
</html>