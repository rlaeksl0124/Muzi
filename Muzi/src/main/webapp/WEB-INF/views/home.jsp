<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="loginOutLink" value="${sessionScope.c_email==null ? '/login' : '/logout'}"/>
<c:set var="loginOut" value="${sessionScope.c_email==null ? '로그인' : '로그아웃'}"/>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<c:set var="loginOutLink" value="${sessionScope.c_email==null ? '/login' : '/logout'}"/>
<c:set var="loginOut" value="${sessionScope.c_email==null ? '로그인' : '로그아웃'}"/>

<html>
<head>
	<title>Home</title>
</head>
<body>
<%@ include file="header.jspf" %>
<h1>
	Hello world!  
</h1>

<P>  The time on the server is ${serverTime}. </P>
<a class="cart_link" id="logoutLink" href="<c:url value='${loginOutLink}'/>">${loginOut}</a>

</body>
</html>
