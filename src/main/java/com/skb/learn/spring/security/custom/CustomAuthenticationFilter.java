package com.skb.learn.spring.security.custom;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// This will be entered into our FilterProxyChain as other filter. This filter will incorporate our CustomAuthentication
// Token object into the filter chain
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    // Using the attemptAuthentication() the filter will pull the User information out of the request and submit it so
    // that we can build our authentication object
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response)
                                throws AuthenticationException {

        String username = super.obtainUsername(request);
        String password = super.obtainPassword(request);
        String make = request.getParameter("make");

        CustomAuthenticationToken customAuthenticationToken = new CustomAuthenticationToken(username, password, make);

        // This puts the information about the request in the Authentication object
        super.setDetails(request, customAuthenticationToken);

        // Now we invoke the authenticate() in our AuthenticationProvider (now CustomAuthenticationProvider)
        return this.getAuthenticationManager().authenticate(customAuthenticationToken);
    }
}
