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
        .category-tabs {
            margin-bottom: 20px;
            border-bottom: 1px solid #ccc;
            display: flex;
            list-style: none;
            padding: 0;
        }
        .category-tabs li {
            margin-right: 20px;
        }
        .category-tabs a {
            text-decoration: none;
            color: #333;
            padding: 10px 15px;
            display: inline-block;
        }
        .category-tabs a.active {
            border-bottom: 3px solid #333;
            font-weight: bold;
        }
        .faq-item {
            border-bottom: 1px solid #ccc;
            padding: 10px 0;
        }
        .faq-question {
            font-weight: bold;
            cursor: pointer;
        }
        .faq-answer {
            display: none;
            padding-left: 20px;
        }
    </style>

</head>
<body>

<div class="faq-container">

    <div class="faq-header">FAQ 빠른검색</div>

    <!-- 카테고리 탭 -->
    <ol class="category-tabs">          <!-- ordered list 사용 : 목록 클래스 이름은 "category-tabs"-->
        <li>        <!-- list item으로 목록의 단일 항목 - 여기서는 FAQ 카테고리 선택에 있는 탭 중 하나를 나타냄  -->
            <%-- cate_no 매개변수가 URL ('${empty param.cate_no}')에 없는 경우 class 속성은 "active" 클래스에 동적으로 할당됨  --%>
            <%-- 즉 특정 카테고리를 선택하지 않으면 TOP 10 탭이 활성화 됨  --%>
            <a href="${pageContext.request.contextPath}/faq/showFaq" class="<c:if test='${empty param.cate_no}'>active</c:if>">TOP 10</a>
        </li>

        <c:forEach var="faq" items="${list}">       <!-- 루프의 각 항목은 faq 변수에 저장됨; items="${list}" 는 FAQ 카테고리를 나타내는 FaqDto 객체의 목록 list -->
            <li>
                <a href="${pageContext.request.contextPath}/faq/showFaq?cate_no=${faq.cate_no}"
                   class="<c:if test='${param.cate_no == faq.cate_no}'>active</c:if>">
                        ${faq.categoryName}
                </a>
            </li>
        </c:forEach>

    </ol>

    <!-- FAQ 리스트 -->
    <table class="faq-table">
        <tbody>
        <c:forEach var="faqDto" items="${list}">
            <c:if test="${empty param.cate_no || param.cate_no == faqDto.cate_no}">
                <tr class="faq-item">
                    <td class="faq-question" onclick="toggleAnswer(${faqDto.faq_no})">
                            ${faqDto.faq_title}
                    </td>
                </tr>
                <tr class="faq-answer" id="answer-${faqDto.faq_no}">
                    <td colspan="2">
                            ${faqDto.faq_content}
                    </td>
                </tr>
            </c:if>
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
