<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="loginOutLink" value="${sessionScope.c_email==null ? '/login' : '/logout'}"/>
<c:set var="loginOut" value="${sessionScope.c_email==null ? '로그인' : '로그아웃'}"/>


<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
    <link href="<c:url value='/css/custChatBot.css'/>" type="text/css" rel="stylesheet"/>
    <title>Title</title>
</head>
<body>
    <!-- 챗봇 UI 시작 -->
    <div class="chatbot-icon" id="chatbotIcon">
        💬
    </div>

    <div class="chatbot-container" id="chatbotContainer">
        <div class="chatbot-header" id="chatbotHeader">
            Chatbot
        </div>
        <div class="chatbot-messages" id="chatbotMessages"></div>

        <form class="chatbot-input-form" id="chatbotForm" action="http://127.0.0.1:5000/api/custChat" method="POST">
            <input type="text" class="chatbot-input" id="chatbotInput" placeholder="메시지를 입력하세요" required />
            <button type="submit" class="chatbot-send-button">전송</button>
        </form>
    </div>
<script>
    const chatbotIcon = document.getElementById('chatbotIcon');
    const chatbotContainer = document.getElementById('chatbotContainer');
    const chatbotForm = document.getElementById('chatbotForm');
    const chatbotInput = document.getElementById('chatbotInput');
    const chatbotMessages = document.getElementById('chatbotMessages');

    chatbotIcon.addEventListener('click', ()=> {
        chatbotContainer.style.display = chatbotContainer.style.display === 'flex' ? 'none' : 'flex';
    })

    chatbotForm.addEventListener('submit', function (event) {
        event.preventDefault();

        const custMessage = chatbotInput.value;

        addMessage('c_email', custMessage);

        setTimeout(function(){
            const botResponse = '안녕하세요 무엇을도와드릴까요?';
            addMessage('custBot', botResponse);
        }, 1000);

        chatbotInput.value='';
    });

    function addMessage(sender, text){
        const messageDiv = document.createElement('div');
        messageDiv.classList.add('message', sender);
        messageDiv.textContent = text;
        chatbotMessages.appendChild(messageDiv);

        chatbotMessages.scrollTop = chatbotMessages.scrollHeight;
    }



</script>
</body>
</html>
