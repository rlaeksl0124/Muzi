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
        .back-button {
            padding: 10px 20px;
            background-color: #007bff;
            color: #fff;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            margin-top: 20px; /* Adds space between the message and the button */
        }
        .back-button:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>

<div class="container">
    <div class="header">
        <h2 class="faq-title">${faqDto.faq_title}</h2>
    </div>

    <div class="faq-meta">
        <div><strong>분류:</strong> ${faqDto.cate_no}</div>
        <div><strong>작성자:</strong> ${faqDto.faq_writer}</div>
        <div><strong>작성일:</strong> ${faqDto.formattedRegDate}</div>
    </div>

    <div class="faq-content">
        ${faqDto.faq_content}
    </div>

    <div class="faq-closing">
        ${faqDto.faq_closing}
    </div>

    <button class="back-button" onclick="location.href='${pageContext.request.contextPath}/faq'">목록으로 돌아가기</button>
</div>

</body>
</html>