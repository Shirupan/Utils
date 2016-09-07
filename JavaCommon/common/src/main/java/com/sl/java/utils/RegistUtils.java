/**
 * 2016.09.01
 * Shirp
 * 正则表达式
 * */
package com.sl.java.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegistUtils {
	/*
	 * 判断邮箱格式是否正确
	 */

	public static boolean isEmail(String emailStr) {

		String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
		Pattern p = Pattern.compile(str);
		Matcher m = p.matcher(emailStr);
		return m.matches();
	}

	/*
	 * 判断手机号格式是否正确
	 */
	public static boolean isMobileNO(String mobiles) {
		Pattern p = Pattern
				.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
		Matcher m = p.matcher(mobiles);

		return m.matches();
	}

}
