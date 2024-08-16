<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <style>
        .tab {
            overflow: hidden;
            border: 1px solid #ccc;
            background-color: #f1f1f1;
        }

        /* Style the buttons that are used to open the tab content */
        .tab button {
            background-color: inherit;
            float: left;
            border: none;
            outline: none;
            cursor: pointer;
            padding: 14px 16px;
            transition: 0.3s;
        }

        /* Change background color of buttons on hover */
        .tab button:hover {
            background-color: #ddd;
        }

        /* Create an active/current tablink class */
        .tab button.active {
            background-color: #ccc;
        }

        /* Style the tab content */
        .tabcontent {
            display: none;
            padding: 6px 12px;
            border: 1px solid #ccc;
            border-top: none;
        }

    </style>
    <link rel="stylesheet" href="/css/Headers.css" />
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
</head>
<body>


    <%-- 카테고리 탭 --%>
    <div id="tab" class="tab">
        <button class="tablinks" onclick="openTab(event, 'subtab1')">제품정보안내</button>
        <button class="tablinks" onclick="openTab(event, '2')">회원혜택/서비스</button>
        <button class="tablinks" onclick="openTab(event, '3')">회원정보</button>
        <button class="tablinks" onclick="openTab(event, '4')">주문/결제 방법</button>
        <button class="tablinks" onclick="openTab(event, '5')">취소/교환/반품</button>
        <button class="tablinks" onclick="openTab(event, '6')">배송관련</button>
        <button class="tablinks" onclick="openTab(event, '7')">영수증</button>
        <button class="tablinks" onclick="openTab(event, '8')">사이트 이용문의</button>
    </div>


    <%-- 탭 내용 --%>
    <div id="subtab1" class="tab">
        <button id="button" class="tablinks" onclick="toggle('101')">소파류</button>
        <button class="tablinks" onclick="toggle('102')">침대류</button>
        <button class="tablinks" onclick="toggle('103')">테이블류</button>
        <button class="tablinks" onclick="toggle('104')">수납선반류</button>
        <button class="tablinks" onclick="toggle('105')">전자용품</button>
        <button class="tablinks" onclick="toggle('106')">패브릭</button>
        <button class="tablinks" onclick="toggle('107')">배송관련</button>
        <button class="tablinks" onclick="toggle('108')">기타</button>
    </div>

    <table class="faq-table">
        <thead>
        <tr>
            <th>FAQ 제목</th>
            <th>작성자</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="faqDto" items="${list}">
            <tr>
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

</body>

<script>
    let toggle = () => {

        let element = document.getElementById("button");
        let hidden = element.getAttribute("hidden");

        if (hidden) {
            element.removeAttribute("hidden");
        }
    }
</script>

</html>