<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <meta charset="UTF-8">
    <title>작성</title>
    <link rel="stylesheet" href="/css/NoticeWrite.css" />
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <link rel="stylesheet" href="/css/Header.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<body>
    <%@include file="header.jspf"%>
    <main>
        <form id="form" action="${requestScope['javax.servlet.forward.servlet_path']=='/Notice/Modify'?'/Notice/Modify':'/Notice/Write'}" method="post">
            <div class="form-group">
                <label for="title">제목</label>
                <input id="title"
                       placeholder="제목 입력(2-100)" name="n_title"
                       maxlength="100" required="required"
                       value="${NoticeDto.n_title}"
                />
            </div>
            <div class="form-group">
                <label for="contents">내용</label>
                <textarea class="form-control" rows="5" id="contents"
                          name="n_contents" placeholder="내용 작성" ${readonly}>${NoticeDto.n_contents}</textarea>
            </div>
            <div class="form-group">
                <label>작성자</label>
                <input type="text" class="form-control" id="c_email"
                       placeholder="작성자(2자-10자)" name="c_email" ${readonly} value=${c_email==null?NoticeDto.c_email:c_email}>
            </div>
            <c:if test = "${requestScope['javax.servlet.forward.servlet_path']=='/Notice/Modify'}">

                <div class="form-group">
                    <label>작성일</label>
                    <input type="text" class="form-control" id="createDate" name="n_createDate" value="<fmt:formatDate value="${NoticeDto.n_createDate}" pattern="yyyy-MM-dd" />"
                      readonly />
                </div>
                <input type="hidden" name="notice_no" value='${NoticeDto.notice_no}' />
                <input type="hidden" name="page" value="${param.page}" />
            </c:if>
            <button type="submit">작성</button>
        </form>
    </main>
</body>
</html>
