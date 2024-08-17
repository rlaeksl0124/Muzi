<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="<c:url value='/css/Headers.css'/>" type="text/css" rel="stylesheet"/>
    <title>FAQ 등록</title>
</head>
<body>
<%@ include file="header.jspf" %>
<div class="container">
    <div class="header">
        <h1>FAQ 등록</h1>
    </div>

    <form id="faqForm" action="${pageContext.request.contextPath}/faq/register" method="POST">
        <!-- 기존의 form elements -->

        <!-- 드롭다운으로 카테고리 선택 -->
        <div class="form-group">
            <label for="cate_no">카테고리 선택</label>
            <select id="cate_no" name="cate_no" required>
                <option value="">카테고리 선택</option>
                <c:forEach var="category" items="${categories}">
                    <option value="${category.cateNo}">${category.cateName}</option>
                </c:forEach>
            </select>
        </div>

    </form>
</div>
</body>
</html>
