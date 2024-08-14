<%--<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>--%>
<%--<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>--%>
<%--<html>--%>
<%--<head>--%>
<%--    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/cart.css">--%>
<%--</head>--%>
<%--<body>--%>
<%--<h2>장바구니 페이지</h2>--%>
<%--<form id="cartFrom" action="" accept-charset="UTF-8">--%>
<%--<table>--%>
<%--    <thead>--%>
<%--    <tr>--%>
<%--        <th></th>--%>
<%--        <th>제품명</th>--%>
<%--        <th>가격</th>--%>
<%--        <th>수량</th>--%>
<%--        <th>옵션</th>--%>
<%--        <th>배송비</th>--%>
<%--        <th></th>--%>
<%--    </tr>--%>
<%--    </thead>--%>

<%--    <tbody>--%>
<%--    <c:forEach items="${cartDto}" var="item">--%>
<%--        <tr>--%>
<%--            <td>--%>
<%--                <!-- 선택할 수 있는 체크박스 -->--%>
<%--                <input type="checkbox" name="cartNo" value="${item.cartNo}">--%>
<%--            </td>--%>
<%--            <td>--%>
<%--                <input id="productNo" type="hidden" name="productNo" value="${item.cartProductNo}">--%>
<%--                    ${item.cartProductNo}--%>
<%--            </td>--%>
<%--            <td>--%>
<%--                <input id="productPrice" type="hidden" name="productPrice" value="${item.cartProductPrice}">--%>
<%--                    ${item.cartProductPrice}--%>
<%--            </td>--%>
<%--            <td>--%>
<%--                <input id="productCnt" type="number" name="productCnt" value="${item.cartProductCnt}" id="productCnt_${item.cartProductNo}" min="1" >--%>
<%--            </td>--%>
<%--            <td>--%>
<%--                <select class="productOption" name="productOption" id="productOption" value="${item.cartProductOption}">--%>
<%--                    <option value="${item.cartProductOption}">${item.cartProductOption}</option>--%>
<%--                    <option value="${item.cartProductOption}">L</option>--%>
<%--                    <option value="${item.cartProductOption}">M</option>--%>
<%--                </select>--%>
<%--            </td>--%>
<%--            <td>--%>
<%--                <input id="deliveryPrice" type="hidden" name="productDeliveryPrice" value="${item.cartDelivery}" readonly>--%>
<%--                    ${item.cartDelivery}--%>
<%--            </td>--%>
<%--            <td>--%>
<%--                <input type="button" id="modifyBtn" data-product-no="${item.cartNo}" value="수정">    </input>--%>
<%--            </td>--%>
<%--        </tr>--%>
<%--    </c:forEach>--%>

<%--    </tbody>--%>

<%--    </table>--%>
<%--    <input type="button" id="deleteSelectedBtn" value="선택삭제">--%>
<%--    <input type="button" id="orderBtn" value="선택주문">--%>
<%--</form>--%>
<%--<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>--%>
<%--<script>--%>
<%--    $(document).ready(function(){--%>
<%--        // 수정 버튼 클릭 시--%>
<%--        $('#modifyBtn').click(function(){--%>
<%--            // 버튼에 있는 data-product-no 속성에서 cartNo 값을 가져옴--%>
<%--            const cartNo = $(this).data('product-no');--%>

<%--            // 해당 cartNo에 맞는 옵션과 수량 값을 가져옴--%>
<%--            const option = $('#productOption_' + cartNo).val();--%>
<%--            const cnt = $('#productCnt_' + cartNo).val();--%>

<%--            let form = $("#cartForm");--%>

<%--            if(option && cnt) {--%>
<%--                form.attr("action", "/cart/modify");--%>
<%--                form.attr("method", "POST");--%>

<%--                // 추가적인 hidden input을 생성하여 해당 cartNo를 포함시킴--%>
<%--                form.append('<input type="hidden" name="cartNo" value="' + cartNo + '">');--%>

<%--                form.submit();--%>
<%--            } else {--%>
<%--                alert("수정할 번호와 옵션을 넣어주세요!");--%>
<%--            }--%>
<%--        });--%>

<%--        $('#orderBtn').click(function() {--%>
<%--            const cartNo = $('input[name="cartNo"]:checked'); // 체크된 항목들--%>

<%--            if (cartNo.length === 0) {--%>
<%--                alert("주문할 항목을 선택하세요.");--%>
<%--                return;--%>
<%--            }--%>

<%--            // 폼 데이터 생성--%>
<%--            let form = $("#cartForm");--%>
<%--            form.empty(); // 기존 폼 데이터를 초기화 (기존 데이터 제거)--%>

<%--            cartNo.each(function() {--%>
<%--                const productNo = $(this).val(); // 체크된 제품의 productNo--%>
<%--                const productPrice = $(this).closest('tr').find('input[name="productPrice"]').val();--%>
<%--                const productCnt = $(this).closest('tr').find('input[name="productCnt"]').val();--%>
<%--                const productOption = $(this).closest('tr').find('select[name="productOption"]').val();--%>
<%--                const deliveryPrice = $(this).closest('tr').find('input[name="productDeliveryPrice"]').val();--%>

<%--                // 선택된 항목의 데이터를 form에 hidden input으로 추가--%>
<%--                form.append('<input type="hidden" name="productNo" value="' + productNo + '">');--%>
<%--                form.append('<input type="hidden" name="productPrice" value="' + productPrice + '">');--%>
<%--                form.append('<input type="hidden" name="productCnt" value="' + productCnt + '">');--%>
<%--                form.append('<input type="hidden" name="productOption" value="' + productOption + '">');--%>
<%--                form.append('<input type="hidden" name="productDeliveryPrice" value="' + deliveryPrice + '">');--%>
<%--            });--%>

<%--            form.attr("action", "/cart/order");--%>
<%--            form.attr("method", "POST");--%>
<%--            form.submit();--%>
<%--        });--%>
<%--        $('#deleteSelectedBtn').click(function() {--%>
<%--            const cartNo = $('input[name="cartNo"]:checked');--%>

<%--            if (cartNo.length === 0) {--%>
<%--                alert("삭제할 항목을 선택하세요.");--%>
<%--                return;--%>
<%--            }--%>

<%--            if (confirm('선택된 항목을 정말로 삭제하시겠습니까?')) {--%>
<%--                // 선택된 항목만 서버로 전송하여 삭제 요청--%>
<%--                $('#cartForm').attr("action", "/cart/remove");--%>
<%--                $('#cartForm').attr("method", "POST");--%>
<%--                $('#cartForm').submit();--%>
<%--            }--%>
<%--        });--%>
<%--    });--%>
<%--</script>--%>
<%--</body>--%>
<%--</html>--%>


<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/cart.css">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script> <!-- jQuery 사용 -->
</head>
<body>
<h2>장바구니 페이지</h2>
<form action="${pageContext.request.contextPath}/cart/order" method="post">
    <table>
        <thead>
        <tr>
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
                    <input type="hidden" name="productNo" value="${item.cartProductNo}">
                        ${item.cartProductNo}
                </td>
                <td>
                    <input type="hidden" name="productPrice" value="${item.cartProductPrice}">
                        ${item.cartProductPrice}
                </td>
                <td>
                    <input type="number" name="productCnt" value="${item.cartProductCnt}" id="productCnt_${item.cartProductNo}">
                </td>
                <td>
                    <input type="text" name="productOption" value="${item.cartProductOption}" id="productOption_${item.cartProductNo}">
                </td>
                <td>
                    <input type="hidden" name="productDeliveryPrice" value="${item.cartDelivery}" readonly>
                        ${item.cartDelivery}
                </td>
                <td>
                    <button type="button" onclick="modifyCart(${item.cartProductNo})">수정</button>
                </td>
            </tr>
        </c:forEach>

        </tbody>

    </table>
    <input type="submit" value="주문하기">
</form>
</body>
</html>