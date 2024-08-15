<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
    <title>FAQ 목록</title>
    <style>
        .faq-content {
            display: none; /* 기본적으로 숨김 처리 */
            margin-top: 10px;
            padding: 5px;
            border: 1px solid #ddd;
        }
        .faq-title {
            cursor: pointer;
            font-weight: bold;
            padding: 5px;
            border-bottom: 1px solid #ddd;
        }
    </style>
    <link rel="stylesheet" href="/css/Headers.css" />
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
</head>
<body>

<div class="category-tab">
    <ol class="tabnav">
        <!-- 카테고리 탭 생성 -->
<%--        <c:forEach items="${category}" var="faqCateDto">--%>
<%--            <li>--%>
<%--                <a href="/faq/showFaq?cate_no=${faqCateDto.cate_no}">${faqCateDto.categoryName}</a>--%>
<%--            </li>--%>
<%--        </c:forEach>--%>
    </ol>
</div>

<div class="faq-list">
    <!-- FAQ 목록 -->
    <c:forEach items="${lists}" var="faqDto">
        <div class="faq-item">
            <!-- FAQ 제목 -->
            <div class="faq-title" onclick="toggleContent(${faqDto.faq_no})">
                    ${faqDto.faq_title}
            </div>
            <!-- FAQ 내용 (숨김 처리) -->
            <input type="hidden" id="faq_no" value="${faqDto.faq_no}"/>
            <div id="content-${faqDto.faq_no}" class="faq-content">
                    ${faqDto.faq_content}
            </div>
        </div>
    </c:forEach>
</div>
<script>
    function toggleContent(faq_no) {
        var content = document.getElementById('content-' + faq_no);
        if (content.style.display === "none" || content.style.display === "") {
            content.style.display = "block";
        } else {
            content.style.display = "none";
        }
    }
</script>
</body>
</html>
