<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html>
<head>
	<title>Product-Home</title>
<script>
</script>
</head>
<body>
<%@ include file="header.jspf" %>
    <h1>Product Details</h1>
    <form action="/cart/add" method="post">
        <c:if test="${not empty product}">
            <table border="1">
                <tr>
                    <th>상품 번호</th>
                    <td>${product.productNumber}</td>
                </tr>
                <tr>
                    <th>상품 이름</th>
                    <td>${product.productName}</td>
                </tr>
                <tr>
                    <th>상품 가격</th>
                    <td>${product.productPrice}</td>
                </tr>
                <tr>
                    <th>배송비</th>
                    <td>${product.deliveryFee}</td>
                </tr>
            </table>
        </c:if>
        <input type="submit" value="장바구니">
    </form>
    <c:forEach var="entry" items="${attribute}">
        <c:if test = "${entry.key==0}">
        <h2>상품 예시 이미지</h2>
        </c:if>
        <c:if test = "${entry.key==1}">
        <h2>상품 상세 이미지</h2>
        </c:if>

        <ul>
            <c:forEach var="picture" items="${entry.value}">
                <li>
                    <img src="${picture.pictureUrl}" alt="${picture.productDetailTitle}" />
                    <p>ID: ${picture.pictureId}</p>
                </li>
            </c:forEach>
        </ul>
    </c:forEach>


</body>
</html>
