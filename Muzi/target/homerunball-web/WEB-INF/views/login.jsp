<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <link rel="icon" type="image/x-icon" href="/img/icon_logo.png">
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>

    <title>login</title>
</head>
<body>
<div id="snap-sync-body" class="snap-sync-pc">
    <div id="snap-sync-wrapper">
        <div class="snap-sync-container">
            <!-- <div class="left" style="background-image: url(&quot;&quot;); height: 943px;"></div> -->
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
                            <div>
                                <a href="/list/API/login_kakao_sync.html" class="sync-common-btn sync-kakao-btn"></a>
                            </div>
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
                                        <input type="text" name="c_pwd" maxlength="40" class="pwdInput" value placeholder="비밀번호를 입력해주세요." required/>
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
                <div class="sync-sns-login">
                    <div class="sync-sns-tit">
                        <span>SNS 로그인</span>
                    </div>
                    <div class="sync-sns-buttons btn-type">
                        <div><button type="button" class="sns-btn sync-sns-naver naver">네이버 계정으로 로그인</button></div>
                        <div><button type="button" class="sns-btn sync-sns-apple apple">카카오 계정으로 로그인</button></div>
                    </div>
                </div>
            </div>
            <div class="sync-join-button-area">
                <div class="sync-join-button">
                    <div>
                        <a href="/signup/add" class="sync-join-btn">
                            <span>회원가입</span>
                            <span class="move-arrow"></span>
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
