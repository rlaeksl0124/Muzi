<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html>
<head>
	<title>Product-Home</title>

<style>
        .toggle-content {
            display: none;
        }
        .toggle-button {
            cursor: pointer;
            color: blue;
            text-decoration: underline;
        }

</style>
<script>
        function toggleContent(id) {
            var content = document.getElementById(id);
            var isVisible = content.style.display === 'block';
            content.style.display = isVisible ? 'none' : 'block';
        }

</script>
</head>
<body>

    <h1>Product Details</h1>

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


        <form action="/Muzi_project/product/submit" method="post">

                <c:forEach var="option" items="${productOption}">
                        <label>${option.key} : </label>
                        <select id="${option.key}" name="${option.key}">
                            <c:forEach var="detail" items="${option.value}">
                                <option value="${detail}">${detail}</option>
                            </c:forEach>
                        </select>
                        <br>
                </c:forEach>

            <br><br>

            <!-- 수량 입력 -->
            <label for="quantity">수량:</label>
            <input type="number" id="quantity" name="quantity" min="1" required>
            <input type ="hidden" id="product" name="product" value="${product}">
            <br><br>

            <!-- 제출 버튼 -->
            <button type="submit">제출</button>
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
