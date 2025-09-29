package my.project.bankingsystem.bank.jwtsecurity;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JWTHelper {
	
	public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;
	
	private String secret = "vjnbnbjeibneasmcdmfvnuibnerfvfbvbvjsniwdebhudhhsfjvsvjksvnhsbfvsslnskvnsjvbhvskjdcnsdcnlqeerererown";
	
	// to get the user name from token
	public String getUserNameFromToken(String token) {
		return getClaimFromToken(token, Claims::getSubject);
	}
	
	// to check if token is expired
	public Date getExpirationDateFromToken(String token) {
		return getClaimFromToken(token, Claims::getExpiration);
	}
	
	public <T> T getClaimFromToken(String token, Function<Claims, T> claimResolver) {
		final Claims claims = getAllClaimsFromToken(token);
		return claimResolver.apply(claims);
	}
	
	// for retrieving any information from token we will need the secret key
	private Claims getAllClaimsFromToken(String token) {
		return Jwts.parserBuilder().setSigningKey(Keys.hmacShaKeyFor(secret.getBytes()))
				//.setSigningKey(secret)	- deprecated
				.build().parseClaimsJws(token).getBody();
	}
	
	//check if token is expired
	private boolean isTokenExpired(String token) {
		final Date expiration = getExpirationDateFromToken(token);
		return expiration.before(new Date());
	}
	
	//generate token for user
	public String generateToken(UserDetails userDetails) {
		Map<String, Object> claims = new HashMap<>();
		return doGenerateToken(claims, userDetails.getUsername());
	}
	
	
	// while creating the token :-
	//1. Define claims for the token, like issuer, Expiration, Subject, and the ID
	//2. Sign the JWT using the HS512 algorithm and secret key
	//3. According to JWS Compact Serialization 
	
	private String doGenerateToken(Map<String, Object> claims, String subject) {
		return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY *1000))
				.signWith(SignatureAlgorithm.HS512, secret).compact();
	}
	
	// validate token
	public boolean validateToken(String token, UserDetails userDetails) {
		final String username = getUserNameFromToken(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}
	
	
	
	
	
	
	
	
	

}
