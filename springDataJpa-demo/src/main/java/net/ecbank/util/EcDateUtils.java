package net.ecbank.util;

import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoField;
import java.util.Date;

/**
 *
 * 날짜 관련 Util
 *
 * @author ECBANK-ecbank1
 * @since 2023-06-02
 */
public class EcDateUtils {

	/**
	 * 현재일자 입력한 포멧으로.
	 * @param pattern
	 * @return
	 */
    public static String format(String pattern) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(pattern, java.util.Locale.KOREA);
			return sdf.format(new Date());
		} catch (RuntimeException e) {
			return null;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 문자열의 길이가 6인 경우에는 "HHmmss" 형식으로 변환
	 * @param timeString
	 * @return
	 */
	public static String convertStringToTime(String timeString) {
		try {
			// 문자열의 길이가 6인 경우에는 "HHmmss" 형식으로 변환
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HHmmss");
			LocalTime time = LocalTime.parse(timeString, formatter);

			// 시간 형식을 "HH:mm:ss"로 변환하여 반환
			return String.valueOf(LocalTime.parse(time.format(DateTimeFormatter.ofPattern("HH:mm:ss"))));
		} catch (DateTimeParseException e) {
			return null;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 문자열의 길이가 8인 경우에는 "yyyyMMdd" 형식으로 변환
	 * @param dateString
	 * @return
	 */
	public static String convertStringToDate(String dateString) {
		try {
			// 문자열의 길이가 8인 경우에는 "yyyyMMdd" 형식으로 변환
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
			LocalDate date = LocalDate.parse(dateString, formatter);

			// 날짜 형식을 "yyyy-MM-dd"로 변환하여 반환
			return String.valueOf(LocalDate.parse(date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))));
		} catch (DateTimeParseException e) {
			return "";
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * 날짜 시간 포멧 변경
	 * @param inputDate
	 * @param pattern
	 * @return
	 */
	public static String convertDateTimeFormat(String inputDate, String iPattern, String oPattern) {
		try {
			DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern(iPattern);
			DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern(oPattern);

			LocalDateTime localDateTime = LocalDateTime.parse(inputDate, inputFormatter);

			return localDateTime.format(outputFormatter);
		} catch (RuntimeException e) {
			return null;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * yyyyMMddHHmmss -> yyyy-MM-dd HH:mm:ss.SSS
	 * @param inputDate
	 * @return
	 */
	public static String convertDateMsTimeFormat(String inputDate) {
		//밀리세컨드 파싱
		try {
			DateTimeFormatter inputFormatter  = new DateTimeFormatterBuilder().appendPattern("yyyyMMddHHmmss").appendValue(ChronoField.MILLI_OF_SECOND, 3).toFormatter();
			DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
			LocalDateTime localDateTime = LocalDateTime.parse(inputDate, inputFormatter);
			return localDateTime.format(outputFormatter);
		} catch (RuntimeException e) {
			return null;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * yyyy-MM-dd HH:mm:ss.SSS -> yyyyMMddHHmmss
	 * @param inputDate
	 * @return
	 */
	public static String convertDateMsTimeToStrFormat(String inputDate) {
		//밀리세컨드 파싱
		try {
			DateTimeFormatter inputFormatter  = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
			DateTimeFormatter outputFormatter = new DateTimeFormatterBuilder().appendPattern("yyyyMMddHHmmss").appendValue(ChronoField.MILLI_OF_SECOND, 3).toFormatter();
			LocalDateTime     localDateTime   = LocalDateTime.parse(inputDate, inputFormatter);
			return localDateTime.format(outputFormatter);
		} catch (RuntimeException e) {
			return null;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 기간의 일자가 유효하지 않은 날짜인지를 체크
	 * true: 유효하지 않은 일자
	 * false: 유효한 일자
	 * @param from
	 * @param to
	 * @return
	 */
	public static boolean isNotValidPeriodDate(String from, String to) {
		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

			LocalDateTime fromDate = LocalDateTime.parse(from, formatter);
			LocalDateTime toDate = LocalDateTime.parse(to, formatter);

			LocalDateTime currentDate = LocalDateTime.now();

			return fromDate.isBefore(currentDate) && toDate.isBefore(currentDate);
		} catch (RuntimeException e) {
			return true;
		} catch (Exception e) {
			return true;
		}
	}

	/**
	 * 현재일자에서 daysToSubtract일 이전 날짜를 foramt 으로 return
	 * @param daysToSubtract
	 * @param format
	 * @return
	 */
	public static String subtractDaysFromDate(int daysToSubtract, String format) {
		LocalDate currentDate = LocalDate.now();
		LocalDate calculatedDate = currentDate.minusDays(daysToSubtract);

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
		return calculatedDate.format(formatter);
	}

	/**
	 * yyyyMMdd -> yyyy-MM-dd
	 * @param inputDate
	 * @return
	 */
	public static String convertToHyphenFormat(String inputDate) {
		SimpleDateFormat inputFormat = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");

		try {
			if(StringUtils.hasText(inputDate)) {
				Date date = inputFormat.parse(inputDate);
				return outputFormat.format(date);
			}
			else {
				return null;
			}
		} catch (ParseException e) {
			return null; // 날짜 파싱 오류 시 null 반환
		}
	}

	/**
	 * 두 날짜 사이의 개월수 구하기
	 * @param dateStr1
	 * @param dateStr2
	 * @return
	 */
	public static Period monthsBetweenDates(String dateStr1, String dateStr2) {

		String cDate1 = convertToHyphenFormat(dateStr1);
		String cDate2 = convertToHyphenFormat(dateStr2);

		if(StringUtils.hasText(cDate1) && StringUtils.hasText(cDate2)) {
			LocalDate date1 = LocalDate.parse(cDate1);
			LocalDate date2 = LocalDate.parse(cDate2);

			// 날짜 차이를 개월로 변환
			return Period.between(date1, date2);
		}
		else {
			throw new RuntimeException(String.format("시작일 또는 종료일이 정상적이지 않습니다. (%s,%s)", cDate1, cDate2));
		}
	}

	/**
	 * 두 날짜간의 개월수가 입력한 기준개월수 초과하는지 확인
	 * 초과: true
	 * 초과하지 않은: false
	 * @param from		: 시작일
	 * @param to		: 종료일
	 * @param tMonth	: 기준개월수
	 * @return
	 * @throws ParseException
	 */
	public static boolean checkMonthsExceed(String from, String to, int tMonth) throws RuntimeException {
		
		//입력한 날짜의 Period set
		Period betweenDates = monthsBetweenDates(from, to);

		long toTotalMonths = betweenDates.toTotalMonths();	//입력한 날짜의 개월수
		int days = (betweenDates.getDays() + 1);			//입력한 날짜의 개월수 이외의 잔여일

		//EX> 20231029 ~ 20240228 인 경우 잔여일이 31일 남아버림. 31을 1개월로 처리 하기 위해 다음과 같은 조치.
		if(days == 31) {
			toTotalMonths = toTotalMonths + 1;
			days = 0;
		}

		//개월수가 기준월보다 큰경우 초과
		if(toTotalMonths > tMonth) {
			return true;
		}
		else {
			//개월수가 기준월과 동일하고, 잔여일이 1일 이상이면 초과
			return toTotalMonths == tMonth && days > 0;
		}
	}
}