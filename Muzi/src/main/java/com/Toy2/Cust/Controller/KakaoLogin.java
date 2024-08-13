package com.Toy2.Cust.Controller;

import com.Toy2.Cust.Dao.CustDao;
import com.Toy2.Cust.Domain.CustDto;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
public class KakaoLogin {
    @Value("#{properties['kakao.api_key']}")//confidential.properties에 저장된 키 가져오기
    private String kakaoApiKey;
    @Value("#{properties['kakao.redirect_uri']}")
    private String kakaoRedirectUri;

    @Autowired
    CustDao custDao;

    @GetMapping("/kakao/login")
    public String redirectToKakaoLogin() {
        // Kakao OAuth 인증 URL 생성
        String kakaoAuthUrl = "https://kauth.kakao.com/oauth/authorize?client_id=" + kakaoApiKey +
                "&redirect_uri=" + kakaoRedirectUri +
                "&response_type=code";

        // Kakao 로그인 페이지로 리다이렉트
        return "redirect:" + kakaoAuthUrl;
    }

    /* 콜백 요청 */
    @RequestMapping("/kakao/callback")
    public String kakaoLogin(@RequestParam String code, Model m, HttpServletRequest request) throws Exception {
        try{
            /* access Token 얻기 */
            String accessToken = getKakaoAccessToken(code);

            /* 사용자 정보 요청 */
            Map<String, Object> userInfo = getUserInfo(accessToken);
            System.out.println(accessToken);
            System.out.println(userInfo);

            /* 받아온 사용자정보의 이메일과 고객의 닉네임을 변수에 저장 */
            String custEmail = userInfo.get("email").toString();
            String custName = userInfo.get("nickname").toString();
            System.out.println("custEmail = " + custEmail);
            System.out.println("custName = " + custName);

            /* 해당 아이디가 DB에 있는지 조회 */
            CustDto custDto = custDao.selectEmail(custEmail);

            /* 고객정보가 DB에 있을경우 */
            if(custDto!=null){
                custDao.updateLogin(custEmail);
                HttpSession session = request.getSession();
                session.setAttribute("c_email", custDto.getC_admin());
                return "redirect:/";
            }

            /* 고객정보가 DB에 없을경우 */
            custDto = new CustDto();
            custDto.setC_email(custEmail);
            custDto.setC_pwd("");
            custDto.setC_name(custName);
            custDto.setC_nick("");
            custDto.setC_birth("");
            custDto.setC_gnd("");
            custDto.setC_phn("");
            custDto.setC_zip("");
            custDto.setC_road_a("");
            custDto.setC_det_a("");


            /* 고객을 DB에 insert 한다 */
            custDao.insert(custDto);

            /* 세션을 생성하고 저장한다 */
            HttpSession session = request.getSession();
            session.setAttribute("c_email", custDto.getC_email());
        }catch (Exception e){
            e.printStackTrace();
            return "errorPageC";
        }
        return "redirect:/";
    }

    /* Access Token 얻기 */
    private String getKakaoAccessToken(String code){
        String requestUrl = "https://kauth.kakao.com/oauth/token";

        RestTemplate restTemplate = new RestTemplate();

        /* 하나의 키에 여러개의 값을 매핑할수있음 */
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", kakaoApiKey);
        params.add("redirect_uri", kakaoRedirectUri);
        params.add("code", code);

        /* 요청헤더 생성*/
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        /* 토큰요청 */
        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest =
                new HttpEntity<>(params, headers);

        /* 응답 받기 */
        ResponseEntity<String> response = restTemplate.exchange(
                requestUrl,
                HttpMethod.POST,
                kakaoTokenRequest,
                String.class
        );

        /* 응답 JSON 파싱 */
        JsonElement element = JsonParser.parseString(response.getBody());
        String accessToken = element.getAsJsonObject().get("access_token").getAsString();
        return accessToken;
    }

    /* 받은 엑세스토큰으로 kakao api 호출하여 사용자 정보 얻기 */
    private Map<String, Object> getUserInfo(String accessToken){
        String requestUrl = "https://kapi.kakao.com/v2/user/me";
        RestTemplate restTemplate = new RestTemplate();

        /* 요청헤더 설정*/
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);

        /* 요청헤더 생성 */
        HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest =
                new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                requestUrl,
                HttpMethod.POST,
                kakaoProfileRequest,
                String.class
        );

        JsonElement element = JsonParser.parseString(response.getBody());
        JsonObject properties = element.getAsJsonObject().get("properties").getAsJsonObject();
        JsonObject kakaoAccount = element.getAsJsonObject().get("kakao_account").getAsJsonObject();

        String nickname = properties.get("nickname").getAsString();
        String email = kakaoAccount.get("email").getAsString();

        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("nickname", nickname);
        userInfo.put("email", email);
        return userInfo;
    }
}