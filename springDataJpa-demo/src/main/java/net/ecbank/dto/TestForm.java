package net.ecbank.dto;


import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TestForm {
	
	@Size(max = 5, message = "param1은 5자리 이상 입력 하지 마세요.")
	private String param1;
	
	@Max(value = 10, message = "param2은 10 이상 입력 하지 마세요.")
	private int param2;
}
