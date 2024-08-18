<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>FAQ 목록 관리</title>
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
        .header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            padding-bottom: 20px;
            border-bottom: 1px solid #ddd;
        }
        .header button {
            padding: 10px 20px;
            background-color: black;
            color: #fff;
            border: none;
            border-radius: 5px;
            cursor: pointer;
        }
        .header button:hover {
            background-color: black;
        }
        .faq-table {
            width: 100%;
            margin-top: 20px;
            border-collapse: collapse;
        }
        .faq-table th, .faq-table td {
            padding: 10px;
            border: 1px solid #ddd;
            text-align: left;
        }
        .faq-table th {
            background-color: #f2f2f2;
        }
    </style>
    <link rel="stylesheet" href="/css/Headers.css" />
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
</head>
<body>

<%@include file="header.jspf"%>
<div class="container">
    <div class="header">
        <div>
            <button onclick="location.href='${pageContext.request.contextPath}/faq/write'">FAQ 등록</button>
        </div>
        <div>
            <span>사용자: <strong>${faq_writer}</strong> 님</span>
            <button onclick="location.href='/logout'">로그아웃</button>
        </div>
    </div>

<%--    <form action="/faq" class="search-form" method="GET">--%>
<%--        <select class="search-optoin" name="option">--%>
<%--            <option value="A">제목+내용</option>--%>
<%--            <option value="T">제목만</option>--%>
<%--        </select>--%>
<%--    </form>--%>

    <form action="/faq" class="search-form" method="GET" style="display: flex; gap: 10px; align-items: center;">
        <select class="search-option" name="option">
            <option value="A">제목+내용</option>
            <option value="T">제목만</option>
        </select>
        <input type="text" name="keyword" placeholder="검색어를 입력하세요" value="${param.keyword}" style="padding: 5px;">
        <button type="submit" style="padding: 5px 10px; background-color: black; color: white; border: none; cursor: pointer;">검색</button>
    </form>


    <table class="faq-table">
        <thead>
        <tr>
            <th>분류유형</th>
            <th>FAQ 제목</th>
            <th>작성자</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="faqDto" items="${list}">
            <tr>
                <td>${faqDto.categoryName}</td>
                <td class="faq_title">
                    <a href="${pageContext.request.contextPath}/faq/read?faq_no=${faqDto.faq_no}">
                            ${faqDto.faq_title}
                    </a>
                </td>
                <td class="faq_writer">${faqDto.faq_writer}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
</body>
<script>
    let msg = "${msg}";
    if (msg == "No_Grand_ERR") alert("게시글 조회 권한이 없습니다. 로그인 해주세요.");
    if (msg == "ReadList_ERR") alert("게시글 목록을 가져오는데 실패했습니다. 다시 시도해 주세요.");
    if (msg == "ReadOne_ERR") alert("게시글을 가져오는데 실패했습니다. 다시 시도해 주세요.");
    if (msg == "Write_ERR") alert("게시글을 등록 페이지를 로드하는데 실패했습니다. 다시 시도해 주세요.");
</script>
</html>
