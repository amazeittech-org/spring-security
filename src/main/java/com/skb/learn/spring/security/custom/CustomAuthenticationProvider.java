package com.skb.learn.spring.security.custom;

import com.skb.learn.spring.security.domain.entities.AutoUser;
import com.skb.learn.spring.security.domain.repositories.AutoUserRepository;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component("customAuthenticationProvider")
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private AutoUserRepository autoUserRepository;

    public CustomAuthenticationProvider(AutoUserRepository autoUserRepository) {
        this.autoUserRepository = autoUserRepository;
    }

    // Holds the logic to authenticate the user. If the user fails authentication then we raise AuthenticationException
    // which will break the authentication chain.
    // But if the authentication is successful then we return a new implementation of the authentication object that
    // contains the Authorities granted to the user.
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        // Because through our supports() below we know that the Authentication mechanism supported is
        // UsernamePasswordAuthenticationToken therefore we can perform the hard type-cast of the authentication
        // object
        CustomAuthenticationToken token = (CustomAuthenticationToken) authentication;

        // Get the user details from the repository
        AutoUser user = autoUserRepository.findByUsername(token.getName());

        // Handle authentication failure
        if(user == null) {
            throw new BadCredentialsException("Invalid username supplied");
            // Ignoring case just to show that this CustomAuthenticationProvider is working
        } else if (!user.getPassword().equalsIgnoreCase(token.getCredentials().toString()) ||
                token.getMake().equalsIgnoreCase("Subaru")) {
            throw new BadCredentialsException("Invalid password supplied");
        }

        // Hurray... Successfully authenticated!! Now we need to build an Authentication object
        return new CustomAuthenticationToken(user, user.getPassword(), user.getAuthorities(), token.getMake());
    }

    // Accepts a parameter of type Class and this is going to be a authentication class that we will support for this
    // Authentication Provider. For our case we will support UsernamePasswordAuthenticationToken.
    // But there can (are) usecases where we can support other authentication mechanism like X509 certificcate.
    // So if the authentication mechanism validating X509 certificates then Spring security will know that it does
    // NOT need to invoke this authentication mechanism
    @Override
    public boolean supports(Class<?> authentication) {
        //
        return CustomAuthenticationToken.class.equals(authentication);

        //
    }
}
