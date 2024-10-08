<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="<c:url value='/css/Headers.css'/>" type="text/css" rel="stylesheet"/>
    <title>FAQ 등록</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f7f7f7;
        }
        .container {
            width: 80%;
            margin: 0 auto;
            padding: 20px;
            background-color: #fff;
            box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
        }
        .header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            padding-bottom: 20px;
            border-bottom: 1px solid #ddd;
            margin-bottom: 20px;
        }
        .header h1 {
            margin: 0;
            font-size: 24px;
        }
        .form-group {
            margin-bottom: 15px;
        }
        .form-group label {
            display: block;
            margin-bottom: 5px;
            font-weight: bold;
        }
        .form-group input, .form-group textarea {
            width: 100%;
            padding: 10px;
            border: 1px solid #ddd;
            border-radius: 5px;
            font-size: 16px;
            box-sizing: border-box;
        }
        .form-group textarea {
            resize: vertical;
        }
        .button-group {
            display: flex;
            justify-content: flex-end;
        }
        .button-group button {
            padding: 10px 20px;
            background-color: black;
            color: #fff;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            font-size: 16px;
        }
        .button-group button:hover {
            background-color: #333;
        }
        /* Styles for the category dropdown */
        .form-group select {
            width: 100%;
            padding: 10px;
            border: 1px solid #ddd;
            border-radius: 5px;
            font-size: 16px;
            background-color: #f7f7f7;
            color: #333;
            margin-top: 5px;
        }
        .form-group select optgroup {
            color: #000;
            font-weight: bold;
            background-color: #e7e7e7; /* Light gray background for optgroup */
            padding: 5px;
            border-radius: 5px;
        }
        .form-group select option {
            padding: 5px;
            background-color: #fff; /* White background for options */
            color: #333;
        }
    </style>
</head>
<body>
<%@ include file="header.jspf" %>
<div class="container">
    <div class="header">
        <h1>FAQ 등록</h1>
    </div>

    <form id="faqForm" action="${pageContext.request.contextPath}/faq/write" method="POST">
        <div class="form-group">
            <label for="faq_title">FAQ 제목</label>
            <input type="text" id="faq_title" name="faq_title" required>
        </div>

        <!-- Drop-down for Category Selection -->
        <div class="form-group">
            <label for="cate_no">카테고리 선택</label>
            <select id="cate_no" name="cate_no" required>
                <option value="">카테고리 선택</option>
                <!-- Example category data -->
                <optgroup label="제품정보안내">
                    <option value="101">소파류</option>
                    <option value="102">침대류</option>
                    <option value="103">테이블류</option>
                    <option value="104">수납선반류</option>
                    <option value="105">전자용품</option>
                    <option value="106">패브릭</option>
                    <option value="107">배송관련</option>
                    <option value="108">기타</option>
                </optgroup>
                <optgroup label="회원혜택/서비스">
                    <option value="201">L.POINT 회원</option>
                    <option value="202">메일매거진</option>
                    <option value="203">회원헤택</option>
                    <option value="204">회원등급</option>
                    <option value="205">상품평</option>
                    <option value="206">L-money</option>
                    <option value="207">L.POINT</option>
                    <option value="208">카탈로그</option>
                </optgroup>
                <optgroup label="회원정보">
                    <option value="301">정보변경</option>
                    <option value="302">아이디/비밀번호</option>
                    <option value="303">탈퇴</option>
                </optgroup>
                <optgroup label="주문/결제방법">
                    <option value="401">주문 방법 및 확인</option>
                    <option value="402">대량구매</option>
                    <option value="403">주문 내용 변경</option>
                    <option value="404">입금확인</option>
                    <option value="405">세트판매확인</option>
                    <option value="406">결제수단</option>
                    <option value="407">소량재고</option>
                </optgroup>
                <optgroup label="취소/교환/반품">
                    <option value="501">반품</option>
                    <option value="502">주문취소</option>
                    <option value="503">교환/AS방법</option>
                </optgroup>
                <optgroup label="배송관련">
                    <option value="601">배송확인/기간</option>
                    <option value="602">배송지변경</option>
                    <option value="603">배송기간</option>
                </optgroup>
                <optgroup label="영수증">
                    <option value="701">현금영수증</option>
                    <option value="702">신용카드 매출전표</option>
                </optgroup>
                <optgroup label="사이트 이용문의">
                    <option value="801">PC문제해결/오류</option>
                    <option value="802">MUJI 고객센터 이용 안내</option>
                </optgroup>
            </select>
        </div>

        <div class="form-group">
            <label for="faq_content">FAQ 내용</label>
            <textarea id="faq_content" name="faq_content" rows="5" required></textarea>
        </div>

        <div class="form-group">
            <label for="faq_writer">FAQ 작성자</label>
            <input type="text" id="faq_writer" name="faq_writer" required>
        </div>

        <div class="form-group">
            <label for="faq_order">FAQ 순서</label>
            <input type="number" id="faq_order" name="faq_order" required>
        </div>

        <div class="button-group">
            <button type="submit">등록</button> <!-- Form submit button -->
        </div>

    </form>
</div>

<script>
    let msg = "${msg}";
    if (msg == "Submit_Write_ERR") alert("게시글을 등록하는데 실패했습니다. 다시 시도해 주세요.");

    document.getElementById('faqForm').addEventListener('submit', function(event) {
        // Get the form elements
        var faqTitle = document.getElementById('faq_title').value;
        var faqWriter = document.getElementById('faq_writer').value;
        var faqOrder = document.getElementById('faq_order').value;

        // Validate lengths
        if (faqTitle.length > 100) {
            alert("FAQ 제목은 100자 이내로 입력해 주세요.");
            event.preventDefault(); // Prevent form submission
            return;
        }

        if (faqWriter.length > 10) {
            alert("FAQ 작성자는 10자 이내로 입력해 주세요.");
            event.preventDefault(); // Prevent form submission
            return;
        }

        if (faqOrder.length > 10) {
            alert("FAQ 순서는 100자 이내로 입력해 주세요.");
            event.preventDefault(); // Prevent form submission
            return;
        }
    });
</script>

</body>
</html>
