<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/styles.css">

    <title>주문목록</title>
</head>
<body>
<%@ include file="header.jspf" %>
<link rel="stylesheet" href="/css/orderList.css" />
<h2>주문내역 페이지</h2>

<!-- 주문 번호를 서버로 전달하기 위한 form -->
<form id="orderForm" action="${pageContext.request.contextPath}/orders/orderDetailList" method="get">
    <input type="hidden" id="orderNo" name="orderNo">
</form>

<table>
    <thead>
    <tr>
        <th>번호</th>
        <th>제품명</th>
        <th>총상품가격</th>
        <th>배송비</th>
        <th>총가격</th>
        <th>주문날짜</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${orderList}" var="order">
        <tr onclick="submitOrderForm('${order.orderNo}')">
            <td>${order.orderNo}</td>
            <td>${order.productName}</td>
            <td>${order.orderPrices}</td>
            <td>${order.orderDeliveryPrices}</td>
            <td>
                <c:set var="calculatedTotalPrice" value="${order.orderPrices + order.orderDeliveryPrices}" />
                <c:out value="${calculatedTotalPrice}" />
            </td>
            <td>${order.orderDate}</td>
        </tr>
    </c:forEach>
    </tbody>


</table>
<br>
<div class="pagination">
    <c:if test="${ph.showPre}">
        <a href="/orders/orderList?page=1&cd=${cd}"><<</a>
        <a href="/orders/orderList?page=${ph.beginPage-1}"><</a>
    </c:if>
    <c:forEach var="page" begin="${ph.beginPage-1}" end="${ph.endPage}">
        <a href="/orders/orderList?page=${page}">${page+1}</a>
    </c:forEach>
    <c:if test="${ph.showNext}">
        <a href="/orders/orderList?page=${ph.endPage+1}">></a>
        <a href="/orders/orderList?page=${ph.totalPage}">>></a>
    </c:if>
</div>

<script>
    function submitOrderForm(orderNo) {
        // 주문 번호를 form의 hidden input에 설정하고 제출합니다.
        document.getElementById("orderNo").value = orderNo;
        document.getElementById("orderForm").submit();
    }
</script>

</body>
</html>