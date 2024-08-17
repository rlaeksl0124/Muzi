<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/styles.css">
    <title>주문중</title>
</head>
<body>
<%@ include file="header.jspf" %>

<link rel="stylesheet" href="/css/order.css" />
<h2>주문 상세 정보</h2>
<h3>
    <input type="hidden" name="customerEmail" value="${orderDto.customerEmail}">
    주문자 : ${orderDto.customerEmail}
</h3>
<div class="order-section">
    <h3>1. 주문상품</h3>
    <form action="${pageContext.request.contextPath}/orders/complete" method="post">
    <table>
        <thead>
        <tr>
            <th>제품명</th>
            <th>가격</th>
            <th>수량</th>
            <th>옵션</th>
            <th>배송비</th>
            <th>제품총가격</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${orderDetailList}" var="orderDetail" varStatus="status">
            <tr>
                <td>
                    <input type="hidden" name="orderDetails[${status.index}].productNo" value="${orderDetail.productNo}">
                    <input type="hidden" name="orderDetails[${status.index}].orderDetailProductName"
                           value="${orderDetail.orderDetailProductName}">
                        ${orderDetail.orderDetailProductName}
                </td>
                <td>
                    <input type="hidden" name="orderDetails[${status.index}].orderDetailPrice" value="${orderDetail.orderDetailPrice}">
                        ${orderDetail.orderDetailPrice}
                </td>
                <td>
                    <input type="hidden" name="orderDetails[${status.index}].orderDetailCnt" value="${orderDetail.orderDetailCnt}">
                        ${orderDetail.orderDetailCnt}
                </td>
                <td>
                    <input type="hidden" name="orderDetails[${status.index}].orderDetailOption" value="${orderDetail.orderDetailOption}">
                        ${orderDetail.orderDetailOption}
                </td>
                <td>
                    <input type="hidden" name="orderDetails[${status.index}].orderDetailDeliveryPrice" value="${orderDetail.orderDetailDeliveryPrice}">
                        ${orderDetail.orderDetailDeliveryPrice}
                </td>
                <c:set var="totalPrice" value="${orderDetail.orderDetailPrice * orderDetail.orderDetailCnt}" />
                <td>
                        ${totalPrice}
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
<%--</div>--%>
        <!-- 총 상품 가격 계산 -->
        <c:set var="totalProductPrice" value="0" />
        <c:forEach items="${orderDetailList}" var="orderDetail">
            <c:set var="totalProductPrice" value="${totalProductPrice + (orderDetail.orderDetailPrice * orderDetail.orderDetailCnt)}" />
        </c:forEach>

        <!-- 총 배송비 계산 -->
        <c:set var="deliveryCost" value="${totalProductPrice >= 30000 ? 0 : 3000}" />

        <!-- 총 예상 금액 계산 -->
        <c:set var="totalEstimatedPrice" value="${totalProductPrice + deliveryCost}" />

<%--<div class="order-section">--%>
    <h3>2. 배송지</h3>

        <p>주문 하는 사람: ${orderDto.customerEmail}</p>
        <label>받는 사람 이름: <input type="text" name="deliveryName"></label><br>
        <label>받는 사람 번호: <input type="text" name="deliveryPhone"></label><br>
        <label>받는 사람 주소: <input type="text" name="deliveryRoadAddress"></label><br>
        <label>상세 주소 : <input type="text" name="deliveryDetailAddress"></label><br>
        <label>배송지명: <input type="text" name="deliveryAddressName"></label><br>
        <label>배송메시지: <textarea name="deliveryMessage"></textarea></label><br>


        <p>
            총 상품 가격: ${totalProductPrice}원
        </p>
        <p>
            총 배송비: ${deliveryCost}원
        </p>
        <p>
            총 예상 금액: ${totalEstimatedPrice}원
        </p>

        <input type="submit" value="결제하기">
    </form>

</div>
</body>
</html>