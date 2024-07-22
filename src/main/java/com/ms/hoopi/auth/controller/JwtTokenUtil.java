package com.ms.hoopi.auth.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.ms.hoopi.auth.AuthDto.JwtToken;
import com.ms.hoopi.entity.Users;
import com.ms.hoopi.join.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

import java.util.Date;

@Slf4j
@Component
public class JwtTokenUtil {

    @Value("${jwt.key}")
    private String secretKey;

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final RedisService redisService;

    public JwtTokenUtil(UserRepository userRepository, RedisService redisService) {
        this.userRepository = userRepository;
        this.redisService = redisService;
    }


    //AccessToken을 발급하는 메소드
    public String createAcs(String id) {
        Instant now = Instant.now();
        Instant expiration = now.plus(1, ChronoUnit.HOURS);
        String usersRole = userRepository.findRoleByUserId(id);

        return Jwts.builder()
                            .setSubject(id)
                            .claim("role", usersRole)
                            .setIssuedAt(Date.from(now))
                            .setExpiration(Date.from(expiration))//1시간
                            .signWith(SignatureAlgorithm.HS256, secretKey)
                            .compact();
    }

    //RefreshToken을 발급하는 메소드
    public String createRfr(String id) {
        Instant now = Instant.now();
        Instant expiration = now.plus(7, ChronoUnit.DAYS);
        String usersRole = userRepository.findRoleByUserId(id);

        return Jwts.builder()
                            .setSubject(id)
                            .claim("role", usersRole)
                            .setIssuedAt(Date.from(now))
                            .setExpiration(Date.from(expiration))//7시간
                            .signWith(SignatureAlgorithm.HS256, secretKey)
                            .compact();
    }

    //AcsToken, RfrToken을 새로 발급, Redis에 저장하는 메소드
    public String chkStoreTokens(String loginRfrToken) {
        //rfrToken이 null이거나 ''라면 return false
        if(loginRfrToken == null || loginRfrToken.isEmpty()) {
            return null;
        }

        //login한 rfrToken의 정보를 이용하기 위한 메소드
        DecodedJWT jwt = JWT.decode(loginRfrToken);
        String loginId = jwt.getId();

        //1. redis안에 jwtToken 존재하는지 확인
        if(!isStoredRfrToken(loginRfrToken)){
            return null;
        } else {
            return loginRfrToken;
        }
    }

    // RfrToken의 유효기간 확인하기
    private boolean isValidToken(String token) {
        DecodedJWT jwt = JWT.decode(token);
        if(jwt.getExpiresAt().before(new Date())){
            return false;
        }
        return true;
    }

    //RfrToken의 id 확인하기
    private String idFromToken(String token) {
        DecodedJWT jwt = JWT.decode(token);
        return jwt.getSubject();
    }

    //Redis에 저장된 RfrToken과 같은지 확인하기
    private boolean isStoredRfrToken(String loginRfrToken) {
        String rfrToken = redisService.getRfrToken(idFromToken(loginRfrToken));
        if(rfrToken == null){
            return false;
        }
        return true;
    }

}


