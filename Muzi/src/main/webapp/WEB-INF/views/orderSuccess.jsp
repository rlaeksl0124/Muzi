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
            제품 : ${orderProductName} 외
        </th>
        <th>
            총 가격 : ${orderPrice}
        </th>
        <th>
            결제 날짜 : ${orderDate}
        </th>
        <p id="paymentKey"></p>
        <p id="orderId"></p>
        <p id="amount"></p>
    </tr>
    <form action="${pageContext.request.contextPath}/orders/orderList" method="get">
        <input type="submit" value="주문내역으로">
    </form>
<script>
    // 쿼리 파라미터 값이 결제 요청할 때 보낸 데이터와 동일한지 반드시 확인하세요.
    // 클라이언트에서 결제 금액을 조작하는 행위를 방지할 수 있습니다.
    const urlParams = new URLSearchParams(window.location.search);
    const paymentKey = urlParams.get("paymentKey");
    const orderId = urlParams.get("orderId");
    const amount = urlParams.get("amount");

    async function confirm() {
        const requestData = {
            paymentKey: paymentKey,
            orderId: orderId,
            amount: amount,
        };

        const response = await fetch("${pageContext.request.contextPath}/orders/confirm", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify(requestData),
        });

        const json = await response.json();

        if (!response.ok) {
            // 결제 실패 비즈니스 로직을 구현하세요.
            console.log(json);
            window.location.href = `/fail?message=${json.message}&code=${json.code}`;
        }

        // 결제 성공 비즈니스 로직을 구현하세요.
        console.log(json);
    }
    confirm();

    const paymentKeyElement = document.getElementById("paymentKey");
    const orderIdElement = document.getElementById("orderId");
    const amountElement = document.getElementById("amount");

    orderIdElement.textContent = "주문번호: " + orderId;
    amountElement.textContent = "결제 금액: " + amount;
    paymentKeyElement.textContent = "paymentKey: " + paymentKey;
</script>
</body>
</html>
