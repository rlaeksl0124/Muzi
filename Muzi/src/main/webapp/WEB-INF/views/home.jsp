<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>

<c:set var="loginOutLink" value="${sessionScope.c_email==null ? '/login' : '/logout'}"/>
<c:set var="loginOut" value="${sessionScope.c_email==null ? '로그인' : '로그아웃'}"/>
<c:set var="mypageLink" value="${sessionScope.c_email==null ? '' : '/mypage'}"/>
<c:set var="mypage" value="${sessionScope.c_email==null ? '' : '마이페이지'}"/>

<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8" />
	<meta name="viewport" content="width=device-width, initial-scale=1.0" />
	<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
	<title>Home</title>
</head>
<body>
<%@ include file="header.jspf" %>
<h1>
	Hello world!  
</h1>

<P>  The time on the server is ${serverTime}. </P>
<a class="cart_link" id="logoutLink" href="<c:url value='${loginOutLink}'/>">${loginOut}</a>
<a class="cart_link" id="mypage" href="<c:url value='${mypageLink}'/>">${mypage}</a>
</body>
</html>
