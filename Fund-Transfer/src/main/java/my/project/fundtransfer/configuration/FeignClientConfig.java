package my.project.fundtransfer.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import feign.RequestInterceptor;
import feign.codec.ErrorDecoder;

@Configuration
public class FeignClientConfig {
	
	@Bean
	public ErrorDecoder errorDecoder() {
		return new CustomErrorDecoder();
	}
	
	@Bean
	public RequestInterceptor requestInterceptor() {
		return requestTemplate -> {
			requestTemplate.header("Authorization", "Basic QWRtaW46UGFzc3dvcmQ=","");
		};
	}
	
}
