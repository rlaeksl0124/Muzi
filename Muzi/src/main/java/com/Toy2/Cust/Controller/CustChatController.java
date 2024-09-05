package com.Toy2.Cust.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Controller
public class CustChatController {

    private final RestTemplate restTemplate = new RestTemplate();

    @GetMapping("/chat")
    public String showChatbot(){
        return "index";
    }

    @PostMapping("/chat")
    public String custChat(String user_input, Model m){
        String apiUrl = "http://127.0.0.1:5000/api/custChat";
        Map<String, String> request = Map.of("user_input", user_input);

        Map<String, Object> response = restTemplate.postForObject(apiUrl, request, Map.class);

        System.out.println(response);

        m.addAttribute("result", response.get("result"));
        m.addAttribute("cust_data", response.get("cust_data"));

        return "index";
    }
}
