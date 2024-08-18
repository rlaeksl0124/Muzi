<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>FAQ 목록</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #fff;
            color: #000;
            margin: 0;
            padding: 20px;
        }
        .faq-container {
            max-width: 800px;
            margin: 0 auto;
            padding: 20px;
        }
        h1 {
            text-align: center;
            font-size: 1.5em;
            margin-bottom: 20px;
            color: #000;
        }
        .faq-item {
            border-bottom: 1px solid #000;
            padding: 10px 0;
            cursor: pointer;
        }
        .faq-item:hover {
            background-color: #f9f9f9;
        }
        .faq-header {
            font-weight: bold; /* Bold 스타일 적용 */
            font-size: 1.1em;
            color: #000;
        }
        .faq-content {
            display: none;
            margin-top: 10px;
            font-size: 1em;
            color: #333;
        }
        .faq-closing {
            margin-top: 5px;
            font-size: 0.9em;
            color: #666;
        }
    </style>
    <link rel="stylesheet" href="/css/Headers.css" />
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
</head>
<body>

<%@include file="header.jspf"%>

<div class="faq-container">
    <h1>FAQ 목록</h1>

    <c:forEach var="faq" items="${faqList}">
        <div class="faq-item" onclick="toggleFaqContent('${faq.faq_no}')">
            <div class="faq-header">${faq.categoryName} | ${faq.faq_title}</div>
            <div class="faq-content" id="content-${faq.faq_no}">
                <div class="faq-answer">${faq.faq_content}</div>
                <div class="faq-closing">${faq.faq_closing}</div>
            </div>
        </div>
    </c:forEach>

    <c:if test="${empty faqList}">
        <p>FAQ 목록이 더 이상 없습니다.</p>
    </c:if>
</div>

<script>
    function toggleFaqContent(faqId) {
        var content = document.getElementById('content-' + faqId);
        if (content.style.display === 'none' || content.style.display === '') {
            content.style.display = 'block';
        } else {
            content.style.display = 'none';
        }
    }
</script>

</body>
</html>