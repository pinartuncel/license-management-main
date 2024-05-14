package de.hse.gruppe8.util;

import io.fusionauth.jwt.Signer;
import io.fusionauth.jwt.Verifier;
import io.fusionauth.jwt.domain.JWT;
import io.fusionauth.jwt.hmac.HMACSigner;
import io.fusionauth.jwt.hmac.HMACVerifier;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

@ApplicationScoped
public class JwtToken {

    @ConfigProperty(name = "app.jwt.secret", defaultValue = "too many secrets")
    String jwtSecret;

    // User ID aufgrund von loose coupling. Weniger Abhängigkeiten.
    public String createUserToken(Long userId) {
        // Build an HMAC signer using a SHA-256 hash
        Signer signer = HMACSigner.newSHA256Signer(jwtSecret);

        // Build a new JW-Token with an issuer(iss), issued at(iat), subject(sub), user (uid) and expiration(exp)
        JWT jwt = new JWT().setIssuer("license-management")
                .setIssuedAt(ZonedDateTime.now(ZoneOffset.UTC))
                .setSubject("usertoken")
                .addClaim("uid", userId)
                .setExpiration(ZonedDateTime.now(ZoneOffset.UTC).plusMinutes(60)); //TODO: Change Interval

        // Sign and encode the JWT to a JSON string representation
        return JWT.getEncoder().encode(jwt, signer);
    }

    public Long verifyUserToken(String token) {
        // Build an HMC verifier using the same secret that was used to sign the JWT
        Verifier verifier = HMACVerifier.newVerifier(jwtSecret);

        // Verify and decode the encoded string JWT to a rich object
        JWT jwt = JWT.getDecoder().decode(token, verifier);

        return jwt.getLong("uid");
    }

}