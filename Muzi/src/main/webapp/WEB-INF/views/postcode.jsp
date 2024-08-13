<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>Post API</title>
</head>
<body>
<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script>
    function postcode(){
        new daum.Postcode({
            oncomplete: function(data) {
                /* 도로명 주소의 노출 규칙에 따라 주소를 표시한다. */
                /* 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다. */
                let roadAddr = data.roadAddress; // 도로명 주소 변수
                let extraRoadAddr = ''; // 참고 항목 변수

                /* 법정동명이 있을 경우 추가한다. (법정리는 제외) */
                /* 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다. */
                if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
                    extraRoadAddr += data.bname;
                }
                /*  건물명이 있고, 공동주택일 경우 추가한다. */
                if(data.buildingName !== '' && data.apartment === 'Y'){
                    extraRoadAddr += (extraRoadAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                }

                /* 우편번호 */
                document.getElementById('c_zip').value = data.zonecode;

                /* 도로명 R : 지번 J*/
                /* 사용자가 선택한 주소가 도로명 주소일경우 */
                if(data.userSelectedType === 'R'){
                    /* 도로명주소를 c_road_a 에 입력 */
                    document.getElementById("c_road_a").value = roadAddr;

                /* 사용자가 선택한 주소가 지번 주소일경우 */
                } else if(data.userSelectedType === 'J'){
                    /* 지번주소를 c_road_a 에 입력 */
                    document.getElementById("c_road_a").value = data.jibunAddress;
                }
            }
        }).open();
    }
</script>
</body>
</html>
