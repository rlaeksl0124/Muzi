<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@100..900&display=swap" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.9.0/css/all.min.css" rel="stylesheet" />
    <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
    <link href="<c:url value='/css/editInfo.css'/>" type="text/css" rel="stylesheet"/>
    <title>회원정보수정</title>
</head>
<body>
    <div class="container">
        <h2>회원정보 수정</h2>
        <form action="#" method="post">

            <div class="form-group">
                <label for="c_email">이메일</label>
                <div class="email-container">
                    <input type="text" id="c_email" name="c_email" value="${custDto.c_email}" disabled>
                </div>
            </div>


            <div class="form-group">
                <label for="c_name">이름</label>
                <input type="text" id="c_name" name="c_name" value="${custDto.c_name}" required>
            </div>

            <p id="phn-check" class="hiddenmsg"></p>
            <div class="form-group">
                <label for="c_phn">휴대폰 번호</label>
                <input type="text" id="c_phn" name="c_phn" value="${custDto.c_phn}" required>
            </div>


            <!-- 비밀번호 유효성 검사 결과를 표시 -->
            <p id="password-check" class="feedback hiddenmsg"></p>
            <div class="form-group">
                <label for="c_pwd">비밀번호 <span class="required">*</span></label>
                <input type="password" id="c_pwd" name="c_pwd" required>

            </div>
            <!-- 비밀번호 일치 여부 유효성 검사 메시지를 표시 -->
            <p id="password-match-check" class="feedback hiddenmsg"></p>
            <div class="form-group">
                <label for="c_pwd_check">비밀번호 확인 <span class="required">*</span></label>
                <input type="password" id="c_pwd_check" name="c_pwd_check" required>

            </div>

            <div class="form-group">
                <label for="c_birth">생년월일</label>
                <input type="text" id="c_birth" name="c_birth" value="${custDto.c_birth}" disabled/>
            </div>

            <!-- 주소 입력 부분 시작 -->
            <div class="form-group">
                <label for="c_zip">주소</label>
                <div class="address-inputs">
                    <div class="zip-search">
                        <input type="text" id="c_zip" name="c_zip" value="${custDto.c_zip}" required>
                        <%@ include file="postcode.jsp" %>
                        <input type="button" class="zip-btn" onclick="postcode()" value="우편번호">
                    </div>
                    <input type="text" id="c_road_a" name="c_road_a" placeholder="기본주소" value="${custDto.c_road_a}" required>
                    <input type="text" id="c_det_a" name="c_det_a" placeholder="나머지주소" value="${custDto.c_det_a}" required>
                    <div class="checkbox-container">
                        <input type="checkbox" id="save-address">
                        <label for="save-address">기본배송지로 저장</label>
                    </div>
                </div>
            </div>
            <!-- 주소 입력 부분 끝 -->


            <div class="form-group">
                <label for="bank">환불계좌(선택)</label>
                <select id="bank">
                    <option value="은행선택">은행 선택</option>
                    <!-- 다른 옵션 추가 가능 -->
                </select>
            </div>

            <button type="submit" class="submit-btn" id="submit-btn">저장하기</button>
        </form>
    </div>
</body>

</html>
