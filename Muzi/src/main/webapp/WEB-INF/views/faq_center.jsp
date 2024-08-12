<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Customer Center</title>
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
        }
        .header button {
            padding: 10px 20px;
            background-color: #007bff;
            color: #fff;
            border: none;
            border-radius: 5px;
            cursor: pointer;
        }
        .header button:hover {
            background-color: #0056b3;
        }
        .faq-table {
            width: 100%;
            margin-top: 20px;
            border-collapse: collapse;
        }
        .faq-table th, .faq-table td {
            padding: 10px;
            border: 1px solid #ddd;
            text-align: left;
        }
        .faq-table th {
            background-color: #f2f2f2;
        }
        .checkbox {
            text-align: center;
        }
    </style>
</head>
<body>

<div class="container">
    <div class="header">
        <div>
            <button onclick="location.href='${pageContext.request.contextPath}/faq/register'">FAQ 등록</button>
            <button onclick="deleteSelected()">FAQ 삭제</button>
        </div>
        <div>
            <span>사용자: <strong>${faq_writer}</strong> 님</span>
            <button onclick="location.href='logout.jsp'">로그아웃</button>
        </div>
    </div>

    <table class="faq-table">
        <thead>
        <tr>
            <th class="checkbox"><input type="checkbox" onclick="selectAll(this)"></th>
            <th>FAQ 제목</th>
            <th>작성자</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="faqDto" items="${list}">
            <tr>
                <td class="checkbox"><input type="checkbox" name="faq" value="${faqDto.faq_no}"></td>
                <td class="faq_title">
                    <a href="${pageContext.request.contextPath}/faq/view?faq_no=${faqDto.faq_no}">
                            ${faqDto.faq_title}
                    </a>
                </td>
                <td class="faq_writer">${faqDto.faq_writer}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

<script>
    function selectAll(source) {
        checkboxes = document.getElementsByName('faq');
        for (var i = 0; i < checkboxes.length; i++) {
            checkboxes[i].checked = source.checked;
        }
    }

    function deleteSelected() {
        // Implement delete functionality here
        alert('Selected FAQs will be deleted.');
    }
</script>
</body>
</html>
