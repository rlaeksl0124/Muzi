package com.Toy2.Cust.Service;

import com.Toy2.Cust.Dao.CustDao;
import com.Toy2.Cust.Domain.CustDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustLoginFailCntHandler implements AuthenticationFailureHandler {
    @Autowired
    private CustDao custDao;

    @Autowired
    private CustService custService;


    /* 로그인 실패 핸들러 */
    /* 5회 로그인시도시 계정잠김 */
    @Override
    public void onAuthenticationFailure(HttpServletRequest req, HttpServletResponse res, AuthenticationException e) throws IOException, ServletException {
        try {
            String c_email = req.getParameter("c_email");
            CustDto custDto = custDao.selectEmail(c_email);

            if(custDto != null){
                custService.failedLoginCnt(custDto);
            }

            res.sendRedirect("/login?error=true");

        } catch (Exception es) {
            es.printStackTrace();
        }


    }
}
