package my.project.bankingsystem.bank.dtos;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {

	private String status;
	private List<T> response;
	private Object message;
}
