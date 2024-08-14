<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>FAQ 페이지</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
        }
        .faq-container {
            width: 60%;
            margin: 0 auto;
        }
        .faq-header {
            font-size: 24px;
            margin-bottom: 20px;
        }
        .faq-item {
            border-bottom: 1px solid #ccc;
            padding: 10px 0;
        }
        .faq-item td {
            padding: 5px;
        }
        .faq-question {
            font-weight: bold;
            cursor: pointer;
        }
        .faq-answer {
            display: none;
            padding-left: 20px;
        }
        .faq-reply {
            color: #666;
            margin-top: 10px;
        }
    </style>
</head>
<body>

<div class="faq-container">
    <div class="faq-header">FAQ 빠른검색</div>

    <table class="faq-table">
        <tbody>

        <c:forEach var="faqDto" items="${list}">
            <tr class="faq-item">
                <td class="select"> </td>
                <td class="faq-question" onclick="toggleAnswer(${faqDto.faq_no})">
                        ${faqDto.faq_title}
                </td>
            </tr>
            <tr class="replies" style="display: none;" id="answer-${faqDto.faq_no}">
                <td colspan="2">
                    <input type="hidden" name="faq_no" value="${faqDto.faq_no}">
                    <div class="faq_content">${faqDto.faq_content}</div>
                    <p class="faq_closing">${faqDto.faq_closing}</p>
                </td>
            </tr>
        </c:forEach>

        </tbody>
    </table>
</div>

<script>
    function toggleAnswer(id) {
        var element = document.getElementById('answer-' + id);
        if (element.style.display === 'none' || element.style.display === '') {
            element.style.display = 'table-row';
        } else {
            element.style.display = 'none';
        }
    }
</script>

</body>
</html>