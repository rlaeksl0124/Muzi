<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="loginOutLink" value="${sessionScope.c_email==null ? '/login' : '/logout'}"/>
<c:set var="loginOut" value="${sessionScope.c_email==null ? '로그인' : '로그아웃'}"/>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<c:set var="loginOutLink" value="${sessionScope.c_email==null ? '/login' : '/logout'}"/>
<c:set var="loginOut" value="${sessionScope.c_email==null ? '로그인' : '로그아웃'}"/>
<c:set var="mypageLink" value="${sessionScope.c_email==null ? '' : '/mypage'}"/>
<c:set var="mypage" value="${sessionScope.c_email==null ? '' : '마이페이지'}"/>

<html>
<head>
	<title>Home</title>
	<style>
		/* 챗봇 버튼 스타일 */
		#chatbotButton {
			position: fixed;
			bottom: 30px; /* 아래에서 30px 띄움 */
			right: 30px; /* 오른쪽에서 30px 띄움 */
			background-color: #007BFF; /* 버튼 배경색 (파란색) */
			color: white; /* 텍스트 색상 (흰색) */
			border: none;
			border-radius: 50px; /* 둥근 버튼 모양 */
			padding: 15px 20px; /* 버튼 내부 여백 */
			font-size: 16px; /* 폰트 크기 */
			cursor: pointer;
			box-shadow: 0px 4px 6px rgba(0, 0, 0, 0.1); /* 그림자 추가 */
			transition: background-color 0.3s ease; /* 배경색 변경 시 애니메이션 효과 */
		}

		#chatbotButton:hover {
			background-color: #0056b3; /* 버튼에 마우스 오버 시 배경색 */
		}

		/* iframe을 중앙에 배치 */
		#iframeContainer {
			display: flex;
			justify-content: center;
			align-items: center;
			position: fixed;
			bottom: 80px;
			right: 30px;
			width: 600px;
			height: 400px;
			box-shadow: 0px 4px 6px rgba(0, 0, 0, 0.1);
			background-color: white;
			border-radius: 10px;
			overflow: hidden;
			display: none; /* 기본적으로 숨김 */
		}
	</style>
</head>
<body>
<%@ include file="header.jspf" %>
<h1>
	Hello world!  
</h1>

<P>  The time on the server is ${serverTime}. </P>
<a class="cart_link" id="logoutLink" href="<c:url value='${loginOutLink}'/>">${loginOut}</a>
<a class="cart_link" id="mypage" href="<c:url value='${mypageLink}'/>">${mypage}</a>

<button id="chatbotButton" onclick="loadContent()">챗봇</button>

<div id="iframeContainer">
	<iframe src="/orders/index" width="100%" height="100%"></iframe>
</div>

<script>
	function loadContent() {
		var container = document.getElementById("iframeContainer");
		if (container.style.display === "none" || container.style.display === "") {
			container.style.display = "flex"; // iframe 보여주기
		} else {
			container.style.display = "none"; // iframe 숨기기
		}
	}
</script>
</body>
</html>
