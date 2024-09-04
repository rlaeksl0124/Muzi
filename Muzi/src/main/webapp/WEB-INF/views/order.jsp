<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
    <meta charset="UTF-8">
<%--    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/styles.css">--%>
    <title>주문중</title>
    <link rel="shortcut icon" type="image/x-icon" href="favicon.ico" />

</head>
<body>
<script src="https://js.tosspayments.com/v1/payment"></script>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script>
    function execDaumPostcode() {
        var postLayer = document.getElementById("postLayer");
        new daum.Postcode({
            oncomplete: function(data) {
                // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

                // 도로명 주소의 노출 규칙에 따라 주소를 표시한다.
                // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
                var roadAddr = data.roadAddress; // 도로명 주소 변수
                var extraRoadAddr = ''; // 참고 항목 변수
                var jibunAddress = data.autoJibunAddress;
                // 법정동명이 있을 경우 추가한다. (법정리는 제외)
                // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
                if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
                    extraRoadAddr += data.bname;
                }
                // 건물명이 있고, 공동주택일 경우 추가한다.
                if(data.buildingName !== '' && data.apartment === 'Y'){
                    extraRoadAddr += (extraRoadAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                }
                // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
                if(extraRoadAddr !== ''){
                    extraRoadAddr = ' (' + extraRoadAddr + ')';
                }

                // 우편번호와 주소 정보를 해당 필드에 넣는다.
                document.getElementById('Postcode').value = data.zonecode;
                document.getElementById("deliveryRoadAddress").value = roadAddr;
                document.getElementById("deliveryStreetAddress").value = jibunAddress;
            },
            onclose: function(state) {
                //state는 우편번호 찾기 화면이 어떻게 닫혔는지에 대한 상태 변수 이며, 상세 설명은 아래 목록에서 확인하실 수 있습니다.
                if(state === 'FORCE_CLOSE'){
                    //사용자가 브라우저 닫기 버튼을 통해 팝업창을 닫았을 경우, 실행될 코드를 작성하는 부분입니다.

                } else if(state === 'COMPLETE_CLOSE'){
                    //사용자가 검색결과를 선택하여 팝업창이 닫혔을 경우, 실행될 코드를 작성하는 부분입니다.
                    //oncomplete 콜백 함수가 실행 완료된 후에 실행됩니다.
                }
            }
        }).embed(postLayer); <!-- .open()-->
    }
</script>
<%@ include file="header.jspf" %>

<link rel="stylesheet" href="/css/order.css" />
<h2>주문 상세 정보</h2>
<h3>
    <input type="hidden" name="customerEmail" value="${orderDto.customerEmail}">
    주문자 : ${orderDto.customerEmail}
</h3>
<div class="order-section">
    <h3>1. 주문상품</h3>
    <form id="orderForm" action="${pageContext.request.contextPath}/orders/complete" method="post">
        <input type="hidden" id="orderType" name="orderType" value="${orderType}">
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
                <c:set var="totalPrice" value="${orderDetail.orderDetailPrice * orderDetail.orderDetailCnt+orderDetail.orderDetailDeliveryPrice}" />
                <td>
                        ${totalPrice}
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
<%--</div>--%>
        <c:set var="totalProductPrice" value="0" />
        <c:set var="maxDeliveryCost" value="0" />

        <!-- 총 상품 가격 계산 -->
        <c:set var="totalProductPrice" value="0" />
        <c:forEach items="${orderDetailList}" var="orderDetail">
            <c:set var="totalProductPrice" value="${totalProductPrice + (orderDetail.orderDetailPrice * orderDetail.orderDetailCnt)}" />

            <!-- 최대 배송비 계산 -->
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

<%--<div class="order-section">--%>
    <h3>2. 배송지</h3>

        <p>주문 하는 사람: ${orderDto.customerEmail}</p>
        <label>받는 사람 이름: <input type="text" id="deliveryName" name="deliveryName" placeholder="받는사람이름"></label><br>
        <label>받는 사람 번호: <input type="text" id="deliveryPhone" name="deliveryPhone" placeholder="받는사람번호"></label><br>
        <label>받는 사람 주소: <input type="text" id="deliveryRoadAddress" name="deliveryRoadAddress" placeholder="도로명주소" readonly>
            <div id="postLayer"></div>
            <input type="hidden" name="deliveryStreetAddress" id="deliveryStreetAddress" value=""></label>
        <input type="hidden" name="Postcode" id="Postcode">

        <input type="button" onclick="execDaumPostcode()" value="주소검색"/><br>
        <label>상세 주소 : <input type="text" id="deliveryDetailAddress" name="deliveryDetailAddress" placeholder="상세주소"></label><br>
        <label>배송지명: <input type="text" name="deliveryAddressName" placeholder="배송지명"></label><br>
        <label>배송메시지: <textarea name="deliveryMessage" placeholder="배송메시지"></textarea></label><br>


        <p>
            총 상품 가격: ${totalProductPrice}원
        </p>
        <p>
            총 배송비: ${deliveryCost}원
        </p>
        <p>
            총 예상 금액: ${totalEstimatedPrice}원
        </p>
        <input type="submit" id="payment-button" value="결제하기">
    </form>
</div>
<script>
    const clientKey = '${tossClientKey}';
    const tossPayments = TossPayments(clientKey);
    const allAmount = '${totalProductPrice + deliveryCost}';
    const orderEmail = '${customerEmail}';
    const orderFirstName = '${orderProductFirstName}외 ${orderDetailSize}'
    const orderNO = 'order_Test_${orderID}';

    document.getElementById('payment-button').addEventListener('click', function (event) {
        event.preventDefault(); // 기본 폼 제출 동작을 방지합니다.

        // TossPayments 결제 요청
        tossPayments.requestPayment('CARD', {
            amount: allAmount,
            orderId: orderNO,
            orderName: orderFirstName,
            customerName: orderEmail,
            customerEmail: orderEmail,
        }).then(function (response) {
            // 결제 성공 후 response에서 paymentKey를 가져옵니다.
            const paymentKey = response.paymentKey;

            // 필요한 경우 추가적인 서버 작업을 위해 AJAX 호출
            var formData = $('#orderForm').serialize();

            $.ajax({
                type: "POST",
                url: "${pageContext.request.contextPath}/orders/complete",
                data: formData,
                success: function (response) {
                    if (response.success) {
                        // AJAX 요청이 성공하면 수동으로 successUrl로 리다이렉트
                        window.location.href = 'http://localhost:8080/orders/orderSuccess?paymentKey=' + paymentKey +
                            '&orderId=' + orderNO + '&amount=' + allAmount;
                    } else {
                        alert('Order failed. Please try again.');
                    }
                },
                error: function (xhr, status, error) {
                    alert('An error occurred. Please try again.');
                }
            });
        }).catch(function (error) {
            // 결제 실패 시 처리
            console.error(error); // 결제 창 표시 중 오류가 발생하면 콘솔에 표시합니다.
            alert('Payment failed. Please try again.');
            window.location.href = 'http://localhost:8080/cart/cart'; // 실패 시 failUrl로 이동
        });
    });
</script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
</body>
</html>