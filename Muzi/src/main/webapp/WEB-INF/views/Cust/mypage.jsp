<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="loginOutLink" value="${sessionScope.c_email==null ? '/login' : '/logout'}"/>
<c:set var="loginOut" value="${sessionScope.c_email==null ? '로그인' : '로그아웃'}"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
    <link href="<c:url value='/css/mypage.css'/>" type="text/css" rel="stylesheet"/>
    <title>MyPage</title>
</head>
<body>
<%@ include file="../header.jspf"%>
    <div class="mypage-container">
        <header>
            <h1>MY PAGE</h1>
            <p>Muzi 마이페이지</p>
        </header>

        <nav class="menu">
            <div class="menu-item">
                <img src="<c:url value='/img/restaurants-and-cafes-vector-free-icon-set-24.png'/>" alt="주문내역조회">
                <p>주문내역조회</p>
            </div>
            <div class="menu-item">
                <img src="<c:url value='/img/restaurants-and-cafes-vector-free-icon-set-23.png'/>" alt="적립금확인">
                <p>적립금확인</p>
            </div>
            <div class="menu-item">
                <img src="<c:url value='/img/restaurants-and-cafes-vector-free-icon-set-22.png'/>" alt="예치금확인">
                <p>예치금확인</p>
            </div>
            <div class="menu-item">
                <img src="<c:url value='/img/restaurants-and-cafes-vector-free-icon-set-21.png'/>" alt="쿠폰확인">
                <p>쿠폰확인</p>
            </div>
            <div class="menu-item">
                <img src="<c:url value='/img/restaurants-and-cafes-vector-free-icon-set-25.png'/>" alt="위시리스트">
                <p>위시리스트</p>
            </div>
            <div class="menu-item">
                <a href="/editInfo"><img src="<c:url value='/img/restaurants-and-cafes-vector-free-icon-set-30.png'/>" alt="회원정보수정"></a>
                <p>회원정보수정</p>
            </div>
            <div class="menu-item">
                <a href=""><img src="<c:url value='/img/restaurants-and-cafes-vector-free-icon-set-32.png'/>" alt="회원탈퇴신청"></a>
                <p>회원탈퇴신청</p>
            </div>
        </nav>

        <section class="user-info">
            <p>🎃 안녕하세요 ${custDto.c_name} 회원님</p>
        </section>

        <section class="details">
            <p><strong>김다니님</strong>님의 Muzi Mypage 간단 정보입니다.</p>
            <table>
                <tr>
                    <th>회원 가입일</th>
                    <td><fmt:formatDate value="${custDto.reg_date}" pattern="yyyy-MM-dd"/></td>
                </tr>
                <tr>
                    <th>최근 로그인</th>
                    <td><fmt:formatDate value="${custDto.login_dt}" pattern="yyyy-MM-dd hh:mm:ss"/></td>
                </tr>
                <tr>
                    <th>진행중 주문</th>
                    <td>0개</td>
                </tr>
                <tr>
                    <th>상품 보관함</th>
                    <td>0개</td>
                </tr>
                <tr>
                    <th>사용가능 예치금</th>
                    <td>0원</td>
                </tr>
                <tr>
                    <th>사용가능 적립금</th>
                    <td>0원</td>
                </tr>
                <tr>
                    <th>사용가능 쿠폰</th>
                    <td>0장</td>
                </tr>
            </table>
        </section>
    </div>
</body>
</html>