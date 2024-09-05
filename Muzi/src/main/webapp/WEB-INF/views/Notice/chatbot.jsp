<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Chatbot</title>
    <style>
        /* 기본 모달창 스타일 */
        .chatbot-container {
            position: fixed;
            bottom: 20px;
            right: 20px;
            width: 300px;
            height: 500px;
            background-color: #f1f1f1;
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            display: none;
            flex-direction: column;
            justify-content: space-between;
        }

        /* 채팅창 헤더 */
        .chatbot-header {
            background-color: #007bff;
            color: white;
            padding: 10px;
            border-radius: 10px 10px 0 0;
            font-weight: bold;
            display: flex;
            justify-content: space-between;
        }

        /* 카테고리 제목 표시 */
        .chatbot-category {
            font-size: 14px;
            padding-left: 10px;
        }

        /* 닫기 버튼 */
        .close-btn {
            cursor: pointer;
            background-color: transparent;
            border: none;
            color: white;
        }

        /* 채팅 메시지를 표시하는 영역 */
        .chatbot-messages {
            flex: 1;
            padding: 10px;
            overflow-y: auto;
            font-size: 14px;
            background-color: white;
            border-bottom: 1px solid #ddd;
        }

        /* 사용자와 챗봇 메시지 스타일 */
        .user-message {
            text-align: right;
            margin: 5px 0;
            background-color: #e9e9e9;
            padding: 5px;
            border-radius: 5px;
        }

        .bot-message {
            text-align: left;
            margin: 5px 0;
            background-color: #007bff;
            color: white;
            padding: 5px;
            border-radius: 5px;
        }

        /* 사용자 입력창과 버튼 영역 */
        .chatbot-input {
            display: flex;
            padding: 10px;
            border-top: 1px solid #ddd;
        }

        .chatbot-input input {
            flex: 1;
            padding: 8px;
            border: 1px solid #ddd;
            border-radius: 5px;
        }

        .chatbot-input button {
            margin-left: 10px;
            background-color: #007bff;
            color: white;
            border: none;
            border-radius: 5px;
            padding: 8px;
            cursor: pointer;
        }

        /* 챗봇 열기 버튼 */
        .open-chatbot-btn {
            position: fixed;
            bottom: 20px;
            right: 20px;
            background-color: #007bff;
            color: white;
            border: none;
            padding: 10px;
            border-radius: 50%;
            cursor: pointer;
        }
    </style>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body>

<!-- 챗봇 열기 버튼 -->
<button class="open-chatbot-btn" onclick="toggleChatbot()">💬</button>

<!-- 챗봇 모달창 -->
<div class="chatbot-container" id="chatbot">
    <div class="chatbot-header">
        <span>Chatbot</span>
        <button class="close-btn" onclick="toggleChatbot()">X</button>
    </div>

    <!-- 카테고리 표시 -->
    <div class="chatbot-category" id="chatbot-category">
        선택한 카테고리: 없음
    </div>

    <!-- 채팅 메시지 표시 영역 -->
    <div class="chatbot-messages" id="chatbot-messages">
        <div class="bot-message">안녕하세요! 무엇을 도와드릴까요?</div>
    </div>

    <!-- 사용자 입력 필드 및 전송 버튼 -->
    <div class="chatbot-input">
        <input type="text" id="userInput" placeholder="메시지를 입력하세요..." onkeypress="checkEnter(event)">
        <button onclick="sendMessage()">보내기</button>
    </div>

    <!-- 버튼들 (공지사항, FAQ, 주문, 상품) -->
    <div class="chatbot-buttons" style="padding: 10px; display: flex; justify-content: space-around;">
        <button onclick="sendCategory('일상대화', '/')">일상대화</button>
        <button onclick="sendCategory('공지사항', '/notice')">공지사항</button>
        <button onclick="sendCategory('FAQ', '/faq')">FAQ</button>
        <button onclick="sendCategory('주문', '/order')">주문</button>
        <button onclick="sendCategory('상품', '/product')">상품</button>
    </div>
</div>

<script>
    let endpoint = "/"
    // 챗봇 열고 닫는 함수
    function toggleChatbot() {
        var chatbot = $("#chatbot");
        if (chatbot.css("display") === "none" || chatbot.css("display") === "") {
            chatbot.css("display", "flex");
        } else {
            chatbot.css("display", "none");
        }
    }

    // 메시지 전송 함수
    function sendMessage() {
        var userInput = $("#userInput");
        var message = userInput.val().trim();

        // 입력값이 있는지 확인
        if (message === "") return;

        // 사용자 메시지를 채팅창에 추가
        var chatbox = $("#chatbot-messages");
        var userMessage = $("<div></div>").addClass("user-message").text("사용자: " + message);
        chatbox.append(userMessage);

        // 채팅창을 아래로 스크롤
        chatbox.scrollTop(chatbox.prop("scrollHeight"));

        // 입력창 초기화
        userInput.val("");

        // 서버로 메시지 전송 (기본 경로로 AJAX 요청)
        sendAjaxRequest(endpoint, message);
    }

    // 카테고리 선택 시 요청 보내는 함수
    function sendCategory(category, url) {
        $("#chatbot-category").text("선택한 카테고리: " + category);
        endpoint = url;
        // 사용자 선택을 채팅창에 추가
        var chatbox = $("#chatbot-messages");
        var userMessage = $("<div></div>").addClass("user-message").text("사용자: " + category + "을(를) 선택했습니다.");
        chatbox.append(userMessage);

        // 채팅창을 아래로 스크롤
        chatbox.scrollTop(chatbox.prop("scrollHeight"));

    }

    // jQuery로 AJAX 요청 함수
    function sendAjaxRequest(endpoint, data) {
        $.ajax({
            url: "http://localhost:5000" + endpoint,
            type: "POST",
            contentType: "application/json",
            data: JSON.stringify({contents:data}),
            success: function (response) {
                // 서버 응답을 채팅창에 추가
                var chatbox = $("#chatbot-messages");
                var botMessage = $("<div></div>").addClass("bot-message").text("챗봇: " + response);
                chatbox.append(botMessage);

                // 채팅창을 아래로 스크롤
                chatbox.scrollTop(chatbox.prop("scrollHeight"));
            },
            error: function (xhr, status, error) {
                console.error("Error: " + error);
            }
        });
    }

    // Enter 키로 메시지 전송
    function checkEnter(event) {
        if (event.key === "Enter") {
            sendMessage();
        }
    }
</script>
</body>
</html>