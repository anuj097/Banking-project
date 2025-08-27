package my.project.fundtransfer.configuration;

import java.io.IOException;
import com.fasterxml.jackson.databind.ObjectMapper;

/*
 * 
 * 			THIS CLASS IS A IMPLEMENTATION OF FEIGEN CLIENT CONFIG CLASS.
 * 			THE USE OF THIS CLASS IS TO CATCH THE EXCEPTION'S OCCURRED 
 * 			IN BANKING APPLICATION
 * 
 * 
 * 
 */

import feign.Response;
import feign.codec.ErrorDecoder;
import my.project.fundtransfer.Exception.ResourceNotFoundEx;
import my.project.fundtransfer.dto.ErrorResponse;

public class CustomErrorDecoder implements ErrorDecoder{
	
	private final ObjectMapper oMapper = new ObjectMapper();
	private final ErrorDecoder decode = new Default();

	@Override
	public Exception decode(String methodKey, Response response) {
		if(response.status()==404) {
			try {
				ErrorResponse error = oMapper.readValue(response.body().asInputStream(), ErrorResponse.class);
				return new ResourceNotFoundEx(error.getMessage());
			} catch (IOException e) {
				return new ResourceNotFoundEx("Account not found (raw 404 response)");
			}
			
		}
		
		return decode.decode(methodKey, response);
	}

}
