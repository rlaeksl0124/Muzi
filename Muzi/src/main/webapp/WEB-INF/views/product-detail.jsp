<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html>
<head>
	<title>Product-Home</title>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

</head>
<body>
<%@ include file="header.jspf" %>
    <h1>Product Details</h1>
    <form id="cartForm" method="post">
        <c:if test="${not empty product}">
            <table border="1">
                <tr>
                    <th>상품 번호</th>
                    <td>${product.productNumber}</td>
                    <input type="hidden" name="productNo" value="${product.productNumber}">
                </tr>
                <tr>
                    <th>상품 이름</th>
                    <td>${product.productName}</td>
                </tr>
                <tr>
                    <th>상품 가격</th>
                    <td>${product.productPrice}</td>
                    <input type="hidden" name="productPrice" value="${product.productPrice}">
                </tr>
                <tr>
                    <th>배송비</th>
                    <td>${product.deliveryFee}</td>
                    <c:set var="totalAmount" value="${product.productPrice * 2}" />
                    <c:choose>
                        <c:when test="${totalAmount >= 30000}">
                            <input type="hidden" name="productDeliveryPrice" value="0" readonly>
                            <span>0원</span>
                        </c:when>
                        <c:otherwise>
                            <input type="hidden" name="productDeliveryPrice" value="${product.deliveryFee}" readonly>
                            <span>${product.deliveryFee}원</span>
                        </c:otherwise>
                    </c:choose>
                </tr>
                <tr>
                    <th>수량</th>
                    <td>2</td>
                    <input type="hidden" name="productCnt" value="2">
                </tr>
                <tr>
                    <th>옵션</th>
                    <th>L</th>
                    <input type="hidden" name="productOption" value="L">
                </tr>
            </table>
        </c:if>
        <button type="submit" id="cartAdd">장바구니</button>
        <button type="submit" id="order">바로구매</button>
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
<script>
    $(document).ready(function() {
    // 장바구니 버튼 클릭 시
    $("#cartAdd").click(function() {
        $("#cartForm").attr("action", "/cart/add").submit();
        alert("장바구니에 담았습니다.");
        return false;
    });

    // 바로구매 버튼 클릭 시
    $("#order").click(function() {
        if (confirm("정말 구매하시겠습니까??") == true){    //확인
            $("#cartForm").attr("action", "/cart/order").submit();
        }else{   //취소
            return false;
        }
    });
});
</script>

</body>
</html>
