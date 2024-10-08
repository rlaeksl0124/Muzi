<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/styles.css">

    <title>주문상세목록</title>
</head>
<body>

<script type="text/javascript">
    function confirmCancel() {
        return confirm("정말 주문을 취소하시겠습니까?");
    }
</script>
<%@ include file="header.jspf" %>
<link rel="stylesheet" href="/css/orderDetail.css" />
<c:if test="${not empty errorMessage}">
    <script>
        alert('${errorMessage}');
    </script>
</c:if>
<h2>주문상세 페이지</h2>

<h4>1. 주문상품</h4>
<table id="orderDetailTable">
    <thead>
    <tr>
        <th>제품명</th>
        <th>가격</th>
        <th>수량</th>
        <th>옵션</th>
        <th>배송비</th>
        <th>주문상태</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${orderDetailList}" var="item">
        <tr>
            <td>
                <a href="${pageContext.request.contextPath}/product/detail?productNumber=${item.productNo}">
                    ${item.orderDetailProductName}
                </a>
            </td>
            <td>${item.orderDetailPrice}</td>
            <td>${item.orderDetailCnt}</td>
            <td>${item.orderDetailOption}</td>
            <td>${item.orderDetailDeliveryPrice}</td>

            <td>
                <c:choose>
                    <c:when test="${item.orderDetailStatus == 'OC1'}">
                        주문완료
                        <form action="/orders/cancel" method="get" onsubmit="return confirmCancel()">
                            <!-- 주문 상세 번호와 상태를 숨겨서 전송 -->
                            <input type="hidden" name="orderDetailNo" value="${item.orderDetailNo}">
                            <input type="hidden" name="status" value="OC2"> <!-- 상태를 OC2로 변경 -->
                            <input type="hidden" name="orderNo" value="${item.orderNo}"> <!-- 주문 번호 추가 -->
                            <input type="submit" value="주문취소">
                        </form>
                    </c:when>
                    <c:when test="${item.orderDetailStatus == 'OC2'}">
                        주문취소
                    </c:when>
                </c:choose>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>

<h4>2. 배송지</h4>
    <p>받는 사람 이름: ${delivery.deliveryName}</p>
    <p>받는 사람 번호: ${delivery.deliveryPhone}</p>
    <p>받는 사람 주소: ${delivery.deliveryRoadAddress} ${delivery.deliveryDetailAddress}</p>
    <p>배송메시지: ${delivery.deliveryMessage}</p>
<h4>3. 총 결제금액</h4>
    <c:set var="totalProductPrice" value="0" />
    <c:set var="maxDeliveryCost" value="0" />
    <c:forEach items="${orderDetailList}" var="orderDetail">
        <c:set var="totalProductPrice" value="${totalProductPrice + (orderDetail.orderDetailPrice * orderDetail.orderDetailCnt)}" />
        <c:choose>
            <c:when test="${orderDetail.orderDetailDeliveryPrice > maxDeliveryCost}">
                <c:set var="maxDeliveryCost" value="${orderDetail.orderDetailDeliveryPrice}" />
            </c:when>
        </c:choose>
    </c:forEach>

    <!-- 총 배송비 계산 -->
    <c:set var="deliveryCost" value="${totalProductPrice >= 30000 ? 0 : maxDeliveryCost}" />

    <!-- 총 예상 금액 계산 -->
    <c:set var="totalEstimatedPrice" value="${totalProductPrice + deliveryCost}" />
    ${totalEstimatedPrice} 원
</body>
</html>