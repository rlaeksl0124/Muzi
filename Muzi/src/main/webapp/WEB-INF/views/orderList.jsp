<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/styles.css">
    <style>
        /* 주문 상세 정보 섹션을 처음에 숨깁니다. */
        #orderDetailSection {
            display: none;
            margin-top: 20px;
            border: 1px solid #000;
            padding: 10px;
        }
        #orderDetailTable {
            width: 100%;
            border-collapse: collapse;
        }
        #orderDetailTable, #orderDetailTable th, #orderDetailTable td {
            border: 1px solid black;
        }
        #orderDetailTable th, #orderDetailTable td {
            padding: 8px;
            text-align: left;
        }
    </style>
    <title>주문목록</title>
</head>
<body>
<%@ include file="header.jspf" %>
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

<script>
    function submitOrderForm(orderNo) {
        // 주문 번호를 form의 hidden input에 설정하고 제출합니다.
        document.getElementById("orderNo").value = orderNo;
        document.getElementById("orderForm").submit();
    }
</script>

</body>
</html>