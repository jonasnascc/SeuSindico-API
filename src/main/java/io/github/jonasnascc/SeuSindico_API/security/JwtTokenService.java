package io.github.jonasnascc.SeuSindico_API.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import io.github.jonasnascc.SeuSindico_API.entitiy.Usuario.Usuario;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Service
public class JwtTokenService {
    @Value("${api.security.token.secret}")
    private String SECRET_KEY;

    private String ISSUER = "seusindico-api";

    public String generateToken(UserDetailsImpl user){
        try{
            Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
            return JWT.create()
                    .withIssuer(ISSUER)
                    .withIssuedAt(creationDate())
                    .withExpiresAt(expirationDate())
                    .withSubject(user.getUsername())
                    .sign(algorithm);
        }catch (JWTCreationException exception){
            throw new JWTCreationException("Erro ao gerar token.", exception);
        }
    }

    public String getSubject(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
            return JWT.require(algorithm)
                    .withIssuer(ISSUER)
                    .build()
                    .verify(token.replace("Bearer ", ""))
                    .getSubject();
        } catch (JWTVerificationException e) {
            throw new JWTVerificationException("Token inválido ou expirado. ");
        }
    }

    private Instant creationDate(){
        return ZonedDateTime.now(ZoneId.of("-03:00")).toInstant();
    }

    private Instant expirationDate() {
        return ZonedDateTime.now(ZoneId.of("-03:00")).plusHours(4).toInstant();
    }

}
