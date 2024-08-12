<!-- faq_view : 게시글 하나를 보여주는 페이지  -->

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">              <!-- 인코딩 정보 -->
    <meta name="viewport" content="width=device-width, initial-scale=1.0">      <!-- 페이지 사이즈를 줄여도 유지 -->
    <title>${faqDto.faq_title} FAQ</title>          <!-- 게시글의 제목을 faqDto에서 가져오기 -->
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f7f7f7;
        }
        .container {
            width: 80%;
            margin: 0 auto;
            padding: 20px;
            background-color: #fff;
            box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
        }
        .faq-detail {
            margin-top: 20px;
        }
        .faq-detail h2 {
            margin-bottom: 10px;
        }
        .faq-detail p {
            margin-bottom: 5px;
        }
    </style>
</head>
<body>

<div class="container">
        <h3>${faqDto.faq_title}</h3>
</div>

<div class ="container">
    <p><strong>분류 유형</strong><p>
    <p>${faqDto.cate_no}</p>
</div>

<div class = "container">
    <p><strong>작성자</strong> <p>
    <p>${faqDto.faq_writer}</p>
</div>

<div class = "container">
    <p>${faqDto.faq_content}</p>
    <p>${faqDto.faq_closing}</p>
</div>


<%--<div class = "container">--%>
<%--    <p><strong>작성일</strong></p>--%>
<%--    <p><fmt:formatDate value="${faqDto.faq_reg_date}" pattern="yyyy-MM-dd" /></p>  <!-- Registration Date -->--%>
<%--</div>--%>

<div class="container">
    <p><strong>작성일</strong></p>
    <p>${faqDto.formattedRegDate}</p>
</div>

<button onclick="location.href='${pageContext.request.contextPath}/faq'">목록으로 돌아가기</button>

</body>
</html>
