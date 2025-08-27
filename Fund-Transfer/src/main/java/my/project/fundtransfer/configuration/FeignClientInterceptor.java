package my.project.fundtransfer.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import brave.Tracer;
import feign.Feign;
import feign.RequestInterceptor;
import feign.RequestTemplate;


/*
 * 
 * 		THIS CONFIG CLASS IS FOR APPLYING THE AUTHORIZATION TOKEN
 * 								AS A HEADER IN ALL THE REQUEST GOING 
 * 												TO BANKING APPLICATION
 * 
 * 
 */

@Configuration
public class FeignClientInterceptor implements RequestInterceptor{
	
//	public FeignClientInterceptor(Tracer tracer) {
//	}

	// For adding the Trace Id in Feign requests
//	@Bean
//	public Feign.Builder feignBuilder(Tracer tracer) {
//	    return Feign.builder()
//	            .requestInterceptor(new FeignClientInterceptor(tracer));
//	}

	
	// for adding the auth token in feign client
	@Override
	public void apply(RequestTemplate template) {
		String token = fetchAuthToken();
		template.header("Authorization", "Basic "+ token);
		
	}
	
	// custom code to get auth token
	private String fetchAuthToken() {
        // Logic to fetch the token (can be from SecurityContextHolder or external source)
        return "QWRtaW46UGFzc3dvcmQ=";
    }

}
