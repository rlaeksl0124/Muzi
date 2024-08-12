<%@ page import="com.Toy2.Notice.domain.NoticeDto" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <title>작성</title>
    <link rel="stylesheet" href="css/NoticeWrite.css" />
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<body>
    <header>
        <div class="mainHeader">
            <ul>
                <li class="headText">Muji 無印良品</li>
                <li class="headerSearch">
                    <form action="" method="get">
                        <select>
                            <option value="title">제목</option>
                            <option value="contents">내용</option>
                        </select>
                        <input class="mainSearch" type="text" />
                        <button type="submit">입력</button>
                    </form>
                </li>
                <li>로그인</li>
                <li>주문배송</li>
                <li>장바구니</li>
            </ul>
        </div>
        <div class="subHeader">
            <ul>
                <li>상품</li>
                <li>고객 센터</li>
            </ul>
        </div>
    </header>
    <main>
        <form id="form" action="${requestScope['javax.servlet.forward.servlet_path']=='/modify'?'modify':'/write'}" method="post">
            <div class="form-group">
                <label for="title">제목</label>
                <input id="title"
                       placeholder="제목 입력(2-100)" name="title"
                       maxlength="100" required="required"
                       value="${NoticeDto.n_title}"
                />
            </div>
            <div class="form-group">
                <label for="contents">내용</label>
                <textarea class="form-control" rows="5" id="contents"
                          name="contents" placeholder="내용 작성" ${readonly}>${NoticeDto.n_contents}</textarea>
            </div>
            <div class="form-group">
                <label>작성자</label>
                <input type="text" class="form-control" id="ad_id"
                       placeholder="작성자(2자-10자)" name="ad_id" ${readonly} value=${c_email==null?NoticeDto.c_email:c_email}>
            </div>
            <c:if test = "${requestScope['javax.servlet.forward.servlet_path']=='/modify'}">

                <div class="form-group">
                    <label>작성일</label>
                    <input type="text" class="form-control" id="createDate" name="n_createDate" value="<fmt:formatDate value="${NoticeDto.n_createDate}" pattern="yyyy-MM-dd" />"
                      readonly />
                </div>
            </c:if>
        </form>
    </main>
</body>
</html>
