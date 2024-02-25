package net.ecbank;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.commons.lang3.ArrayUtils;

public class SimpleTest {

	public static void main(String[] args) {
//		System.out.println(LocalDateTime.now().format(DatTimeFormatter.ofPattern("yyyyMMddHHmmss")));
		
		String[] arr ={"c"};
		
		String[] a1 = {"a","b"};
		
		ArrayUtils.add(arr, a1);
	}

}
