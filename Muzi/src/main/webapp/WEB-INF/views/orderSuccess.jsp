<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
    <title>주문완료</title>
</head>
<body>
<%@ include file="header.jspf" %>

<link rel="stylesheet" href="/css/orderSuccess.css" />
    <h2> 주문완료 </h2>
    <tr>
        <th>
            제품 : ${orderProductName}
        </th>
        <th>
            총 가격 : ${orderProductPrice}
        </th>
        <th>
            결제 날짜 : ${orderProductDate}
        </th>
    </tr>
    <form action="${pageContext.request.contextPath}/orders/orderList" method="get">
        <input type="submit" value="주문내역으로">
    </form>
</body>
</html>
