package com.bookstore.utility;

import com.bookstore.dto.LoginDTO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.xml.ws.Service;
import java.util.HashMap;

@Component
public class JWTUtility {

    final String SECRET_KEY = "this-is-a-very-special-secret-key-for-this-project";

    @Autowired
    LoginDTO login;

    public String generateToken(LoginDTO loginDetails){

        HashMap<String,Object> claims = new HashMap<>();
        claims.put("username",loginDetails.getUsername());
        claims.put("password",loginDetails.getPassword());
        return Jwts.builder().addClaims(claims).signWith(SignatureAlgorithm.HS256,SECRET_KEY).compact();

    }

    public LoginDTO decodeToken(String token){

        Claims claims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
        login.setUsername((String) claims.get("username"));
        login.setPassword((String) claims.get("password"));
        return login;


    }




}
