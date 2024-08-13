<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${faqDto.faq_title} FAQ</title>
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
            padding-bottom: 10px;
            border-bottom: 1px solid #ddd;
            margin-bottom: 20px;
        }
        .faq-title {
            font-size: 24px;
            margin-bottom: 10px;
        }
        .faq-meta {
            display: flex;
            justify-content: space-between;
            font-size: 14px;
            color: #555;
            margin-bottom: 20px;
        }
        .faq-meta div {
            margin-right: 20px;
        }
        .faq-content {
            font-size: 16px;
            line-height: 1.6;
            margin-bottom: 20px;
        }
        .faq-closing {
            font-size: 16px;
            font-weight: bold;
            color: #333;
            margin-top: 20px;
        }
        .additional-info {
            margin-top: 40px;  /* Adds more space before this section */
            font-size: 14px;
            color: #555;
        }
        .button-group {
            display: flex;
            justify-content: space-between;
            margin-top: 20px; /* Adds space between the message and the button */
        }
        .back-button, .edit-button, .delete-button {
            padding: 10px 20px;
            background-color: #007bff;
            color: #fff;
            border: none;
            border-radius: 5px;
            cursor: pointer;
        }
        .back-button:hover, .edit-button:hover, .delete-button:hover {
            background-color: #0056b3;
        }
        .edit-button {
            margin-right: 10px; /* Space between edit and delete buttons */
        }
        .delete-button {
            background-color: #dc3545;
        }
        .delete-button:hover {
            background-color: #c82333;
        }
    </style>
</head>
<body>

<div class="container">
    <div class="header">
        <h2 class="faq-title">${faqDto.faq_title}</h2>
    </div>

    <div class="faq-meta">
        <div><strong>분류유형:</strong> ${faqDto.categoryName}</div>
        <div><strong>작성자:</strong> ${faqDto.faq_writer}</div>
        <div><strong>작성일:</strong> ${faqDto.formattedRegDate}</div>
    </div>

    <div class="faq-content">
        ${faqDto.faq_content}
    </div>

    <div class="faq-closing">
        ${faqDto.faq_closing}
    </div>

    <!-- 버튼 그룹 -->
    <div class="button-group">
        <button class="back-button" onclick="location.href='${pageContext.request.contextPath}/faq'">목록으로 돌아가기</button>
        <div>
            <button class="edit-button" onclick="location.href='${pageContext.request.contextPath}/faq/edit?faq_no=${faqDto.faq_no}'">수정</button>
            <button class="delete-button" onclick="deleteFaq(${faqDto.faq_no})">삭제</button>
        </div>
    </div>
</div>


<%-- JavaScript로 작성한 코드 --%>
<script>
    // deleteFaq 함수 정의 - FAQ를 삭제하는 함수
    function deleteFaq(faq_no) {
        if (confirm("FAQ를 삭제하시겠습니까?")) {        // alert 창 표시; 'cancel', 'ok' 택 1
            fetch(`${pageContext.request.contextPath}/faq/remove?faq_no=${faq_no}`, {
                method: 'DELETE'        //  HTTP 메서드로 DELETE를 사용하여 서버에 데이터 삭제 요청
            })
                .then(response => {     // 요청에 대한 응답
                    if (response.ok) {      // 서버 응답 성공 시
                        alert("FAQ가 성공적으로 삭제되었습니다.");       // alert 창으로 성공 안내
                        window.location.href = `${pageContext.request.contextPath}/faq`;     // 삭제 후 FAQ로 리다이렉트
                    } else {        // 서버 응답 실패 시
                        alert("FAQ 삭제 실패했습니다.");        // alert 창으로 삭제 안내
                    }
                })
                .catch(error => {       // 요청 중 에러가 발생한 경우
                    console.error('Error:', error);         // 콘솔에 오류 메세지 출력
                    alert("오류가 발생했습니다. 다시 시도해 주세요.");       // alert 창으로 오류 안내
                });
        }
    }
</script>

</body>
</html>