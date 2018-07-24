package com.huidao.common.util;

import java.util.regex.Pattern;

public class ValidateUtil {
	/**
	 * 验证手机
	 * @param mobile
	 * @return
	 */
	public static boolean isMobile(String mobile) {
		return Pattern.matches("^1[0-9]{10}$", mobile);
	}
	

}
