package com.Toy2.Cust.Service;

import com.Toy2.Cust.Dao.CustDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustLoginSuccessHandler implements AuthenticationSuccessHandler {
    @Autowired
    private CustService custService;

    /* 로그인 성공 핸들러 */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest req, HttpServletResponse res, Authentication authentication) throws IOException, ServletException {
        try {
            String c_email = authentication.getName();
            custService.resetFailedCnt(c_email);

            res.sendRedirect("/");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
