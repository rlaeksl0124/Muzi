package com.Toy2.Cust.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


@Configuration
@EnableWebSecurity
@ComponentScan(basePackages = {"com.Toy2.Cust"})
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustLoginFailCntHandler custLoginFailCntHandler;

    @Autowired
    private CustLoginSuccessHandler custLoginSuccessHandler;


    /* 5회 이상 로그인실패시 spring security 핸들러 등록 */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests().anyRequest().permitAll()
                .and()
                .formLogin()
                    .loginPage("/login")
                    /* 로그인 실패시 핸들러 */
                    .failureHandler(custLoginFailCntHandler)
                    /* 로그인 성공시 핸들러 */
                    .successHandler(custLoginSuccessHandler)
                    /* 로그인페이지에 누구나 접근허용 */
                    .permitAll();
    }
}
