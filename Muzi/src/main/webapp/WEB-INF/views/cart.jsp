<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/cart.css">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script> <!-- jQuery 사용 -->
    <title>장바구니</title>
</head>

<body>
<%@ include file="header.jspf" %>
<link rel="stylesheet" href="/css/cart.css" />
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
                        ${item.cartProductName} <!-- 제품 이름 출력 -->
                    <input type="hidden" name="productNo" value="${item.cartProductNo}">
                </td>
                <td>
                    <input type="hidden" name="productPrice" value="${item.cartProductPrice}">
                        ${item.cartProductPrice}
                </td>
                <td>
                    <input type="number" name="productCnt_${item.cartNo}" value="${item.cartProductCnt}"
                           id="productCnt_${item.cartNo}" min="1" oninput="checkValue(this)">
                    <input type="hidden" name="productCnt" value="${item.cartProductCnt}" id="productCnt_${item.cartNo}" min="1">
                </td>
                <td>
                    <input type="text" name="productOption" value="${item.cartProductOption}" id="productOption_${item.cartProductNo}" readonly>
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
                    <button type="button" onclick="modifyCart(${item.cartNo})">수정</button>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <input type="hidden" id="orderType" name="orderType" value="장바구니구매">
    <button type="button" onclick="removeSelectedItems('${pageContext.request.contextPath}/cart/remove')">선택삭제</button>
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
    function removeSelectedItems(actionUrl) {
        if ($(".selectItem:checked").length === 0) {
            alert("삭제할 항목을 선택해주세요."); // 경고 메시지 표시
            return; // 폼 제출 중단
        }
        $("#cartForm").attr("action", actionUrl);
        $("#cartForm").submit();
    }
    function submitForm(actionUrl) {
        // 폼의 액션 설정 후 제출
        $("#cartForm").attr("action", actionUrl);
        $("#cartForm").submit();
    }
    function modifyCart(cartNo) {
        // 기존의 hidden 필드 제거
        $("#cartForm input[name='cartNo']").remove();
        $("#cartForm input[name='productCnt']").remove();

        var productCnt = $("#productCnt_" + cartNo).val(); // 해당 cartNo의 수량 가져오기

        // form에 hidden input으로 cartNo와 productCnt를 추가하여 서버로 전송
        $('<input>').attr({
            type: 'hidden',
            name: 'cartNo',
            value: cartNo
        }).appendTo('#cartForm');

        $('<input>').attr({
            type: 'hidden',
            name: 'productCnt',
            value: productCnt
        }).appendTo('#cartForm');

        $("#cartForm").attr("action", "/cart/modify");
        $("#cartForm").submit();
    }
    function checkValue(input) {
        if (input.value < 1) {
            alert("0이하는 불가능합니다.")
            input.value = 1;  // 만약 값이 1보다 작다면 1로 설정
        }
    }
</script>
</body>
</html>