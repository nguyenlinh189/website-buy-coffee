package com.example.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.web.client.RestTemplate;

import com.example.model.CartItem;
import com.example.model.Provider;
import com.example.model.User;
import com.example.user.CustomOAuth2User;
import com.example.user.CustomOAuth2UserService;

//,"/login/**", "/shop/**","/assets/**", "/updateCart","/blog/**","/assets/img/blog/**",
//"/coffee-products/**", "/product-details/**", "/tea/**","/payment/**","/assets/img/logo/**",
//"/cake&snack/**","/addToCart/**", "/deleteFromCart/**", "/cart/**","/categoryp/**"

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
	@Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
	
	@Autowired
    private CustomOAuth2UserService oauth2UserService;
	
	private RestTemplate rest = new RestTemplate();
	
	@Override
    protected void configure(HttpSecurity http) throws Exception {
		
        http.authorizeHttpRequests(requests -> requests
                .antMatchers("/**").permitAll()
                .anyRequest().authenticated())
                .formLogin(login -> login.loginPage("/login").permitAll())
                .oauth2Login(login -> login
                        .loginPage("/login")
                        .userInfoEndpoint()
                        .userService(oauth2UserService)
                        .and()
                        .successHandler(new AuthenticationSuccessHandler() {
                            @Override
                            public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                                                                                org.springframework.security.core.Authentication authentication)
                                    throws IOException, ServletException {
                                // TODO Auto-generated method stub
                            	String url = request.getRequestURI().toString();
                                CustomOAuth2User oauthUser = (CustomOAuth2User) authentication.getPrincipal();
                                User user = new User();
                                user.setEmail(oauthUser.getEmail());
                                user.setName(oauthUser.getName());
                                if(url.contains("facebook")) user.setProvider(Provider.facebook);
                                else user.setProvider(Provider.google);
                                user.setRole("customer");
                                user.setIsadmin(0);
                                user = rest.postForObject("http://localhost:8082/user/save", user, User.class);
                                HttpSession session = request.getSession();
                                List<CartItem> cart = Arrays.asList(rest.getForObject("http://localhost:8082/cart/user?userId="+user.getId(), CartItem[].class));
                                float subtotal = 0;
                    			for(CartItem i:cart) {
                    				subtotal += i.getProduct().getPrice();
                    			}
                    			session.setAttribute("subtotal", subtotal);
                    			session.setAttribute("total", subtotal+30000);
                    			session.setAttribute("cart", cart);
                    			session.setAttribute("cartsize", cart.size());
                    			session.setAttribute("user", user);
                    			session.setAttribute("name", user.getName());
                                response.sendRedirect("/");
                            }
                        }));
        			
    }
	
	
	@Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(userDetailsService());
        auth.setPasswordEncoder(passwordEncoder());
        return auth;
    }
     
    
}
