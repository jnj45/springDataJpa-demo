package net.ecbank.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorResponse {
	
	PAGE_NOT_FOUND(404, "page.not.found"),
	INVALID_PERMISSION(401, "권한이 없습니다."),
	SERVER_ERROR(500, "서버 내부 오류가 발생했습니다."),
	SESSION_TIME_OUT(408, "로그인 정보가 만료되었습니다."),
	BIZ_ERROR(900, "비즈니스 오류"),
	VALIDATE_ERROR(998, null),
	DB_ERROR(999, "데이터 처리중 오류가 발생하였습니다.");

	private int status;
	private String message;
}
