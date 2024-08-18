<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%--<%@ page contentType="text/html;charset=UTF-8" language="java" %>--%>
<%--<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>--%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>FAQ 목록</title>
    <style>
        body {
            font-family: Arial, sans-serif;
        }
        .faq-container {
            max-width: 800px;
            margin: 0 auto;
        }
        .faq-item {
            border-bottom: 1px solid #ccc;
            padding: 10px 0;
            cursor: pointer;
        }
        .faq-title {
            font-weight: bold;
        }
        .faq-category {
            color: #888;
            font-size: 0.9em;
        }
        .faq-content {
            display: none;
            margin-top: 10px;
            color: #555;
            font-size: 1em; /* Regular font size for faq_content */
        }
        .faq-closing {
            margin-top: 5px;
            color: #777;
            font-size: 0.85em; /* Smaller font size for faq_closing */
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
            <div class="faq-title">${faq.faq_title}</div>
            <div class="faq-category">${faq.categoryName}</div>
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
</body>
<script>
    function toggleFaqContent(faqId) {
        var content = document.getElementById('content-' + faqId);
        if (content.style.display === 'none') {
            content.style.display = 'block';
        } else {
            content.style.display = 'none';
        }
    }
</script>
</html>
