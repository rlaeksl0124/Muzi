<%--
  Created by IntelliJ IDEA.
  User: MY
  Date: 24. 8. 9.
  Time: 오전 11:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>NoticeMain</title>
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <link rel="stylesheet" href="/css/NoticeRead.css" />
    <link rel="stylesheet" href="/css/Header.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
</head>
<body>
    <!-- 삭제/수정 선택 모달 -->
    <div id="actionModal" class="modal">
        <div class="modal-content">
            <span class="close">&times;</span>
            <h2>작업 선택</h2>
            <p>무엇을 하시겠습니까?</p>
            <button id="confirmModify" class="modalBtn">수정</button>
            <button id="confirmDelete" class="modalBtn modalBtn-danger">삭제</button>
        </div>
    </div>

    <!-- 삭제 확인 모달 -->
    <div id="confirmDeleteModal" class="modal">
        <div class="modal-content">
            <span class="close">&times;</span>
            <h2>삭제 확인</h2>
            <p>정말 삭제하시겠습니까?</p>
            <button id="cancelDelete" class="modalBtn">아니요</button>
            <button id="finalDelete" class="modalBtn modalBtn-danger">예, 삭제합니다</button>
        </div>
    </div>

    <%@include file="header.jspf"%>
    <!-- main 테그의 레이아웃 조정 -->
    <main class="main-content">
        <div class="mainSearch">
            <form action="" method="get">
                <select>
                    <option value="title">제목</option>
                    <option value="contents">내용</option>
                </select>
                <input class="mainSearch" type="text" />
                <button type="submit">입력</button>
            </form>
        </div>
        <c:if test="${sessionScope.c_admin=='Y'}">
        <a href="/Notice/Write">작성</a>
        </c:if>
        <table class="notice-table">
            <tr>
                <th class="number">번호</th>
                <th class="title">제목</th>
                <th class="createDate">작성일</th>
                <th class="writer">작성자</th>
                <th></th>
            </tr>
            <c:forEach items="${NoticeArr}" var="Notice" varStatus="status">
                <tr class="Notice">
                    <td>${Notice.notice_no}</td>
                    <td class="NoticeTitle">${Notice.n_title}</td>
                    <td><fmt:formatDate value="${Notice.n_createDate}" pattern="yyyy-MM-dd" /></td>
                    <td>${Notice.c_email}</td>
                    <c:if test="${sessionScope.c_admin=='Y'}">
                        <td><button class="deleteModify">수정/삭제</button></td>
                    </c:if>
                </tr>
                <tr class="NoticeContents">
                    <td colspan="5" id="NoticeContents${status.count}"></td>
                </tr>
            </c:forEach>
        </table>

        <!-- 페이징 처리 -->
        <div class="pagination">
            <c:if test="${ph.prevPage}">
                <a href="/Notice?page=1&cd=${cd}"><<</a>
                <a href="/Notice?page=${ph.beginPage-1}"><</a>
            </c:if>
            <c:forEach var="page" begin="${ph.beginPage}" end="${ph.endPage}">
                <a href="/Notice?page=${page}">${page}</a>
            </c:forEach>
            <c:if test="${ph.nextPage}">
                <a href="/Notice?page=${ph.endPage+1}">></a>
                <a href="/Notice?page=${ph.totalPage}">>></a>
            </c:if>
        </div>
    </main>
    <script src="/js/NoticeRead.js"></script>

</body>
</html>
