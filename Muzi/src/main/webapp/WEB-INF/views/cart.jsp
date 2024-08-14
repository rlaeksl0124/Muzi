<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/cart.css">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script> <!-- jQuery 사용 -->
</head>
<body>
<%@ include file="header.jspf" %>
<h2>장바구니 페이지</h2>
<form id="cartForm" method="post">
    <table>
        <thead>
        <tr>
            <th><input type="checkbox" id="selectAll"> 전체선택</th>
            <th>제품명</th>
            <th>가격</th>
            <th>수량</th>
            <th>옵션</th>
            <th>배송비</th>
            <th>수정</th>
        </tr>
        </thead>

        <tbody>
        <c:forEach items="${cartDto}" var="item">
            <tr>
                <td>
                    <input type="checkbox" name="cartNo" value="${item.cartNo}" class="selectItem">
                </td>
                <td>
                    <input type="hidden" name="productNo" value="${item.cartProductNo}">
                        ${item.cartProductNo}
                </td>
                <td>
                    <input type="hidden" name="productPrice" value="${item.cartProductPrice}">
                        ${item.cartProductPrice}
                </td>
                <td>
                    <input type="number" name="productCnt" value="${item.cartProductCnt}" id="productCnt_${item.cartProductNo}" min="1">
                </td>
                <td>
                    <input type="text" name="productOption" value="${item.cartProductOption}" id="productOption_${item.cartProductNo}">
                </td>
                <td>
                    <c:set var="totalAmount" value="${item.cartProductPrice * item.cartProductCnt}" />
                    <c:choose>
                        <c:when test="${totalAmount >= 30000}">
                            <input type="hidden" name="productDeliveryPrice" value="0" readonly>
                            <span>0원</span>
                        </c:when>
                        <c:otherwise>
                            <input type="hidden" name="productDeliveryPrice" value="${item.cartDelivery}" readonly>
                            <span>${item.cartDelivery}원</span>
                        </c:otherwise>
                    </c:choose>
                </td>
                <td>
                    <button type="button" onclick="modifyCart(${item.cartProductNo})">수정</button>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <button type="button" onclick="submitForm('${pageContext.request.contextPath}/cart/remove')">선택삭제</button>
    <button type="button" onclick="submitForm('${pageContext.request.contextPath}/cart/order')">주문하기</button>
</form>

<script>
    // 전체 선택/해제 기능
    $("#selectAll").click(function() {
        $(".selectItem").prop('checked', this.checked);
    });

    // 개별 선택 시 전체 선택 체크박스 상태 업데이트
    $(".selectItem").click(function() {
        if ($(".selectItem:checked").length === $(".selectItem").length) {
            $("#selectAll").prop('checked', true);
        } else {
            $("#selectAll").prop('checked', false);
        }
    });

    // 폼 제출 기능
    function submitForm(actionUrl) {
        // 폼의 액션 설정
        $("#cartForm").attr("action", actionUrl);
        $("#cartForm").submit();
    }
</script>
</body>
</html>