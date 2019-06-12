package com.sf.utils.baseutil;

import android.content.Context;
import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegExpressCheck {
	
	public static boolean isEmail(String email){
		 String str="^[a-zA-Z0-9_+.-]+\\@([a-zA-Z0-9-]+\\.)+[a-zA-Z0-9]{2,4}$";
	        Pattern p = Pattern.compile(str);     
	        Matcher m = p.matcher(email);     
	        return m.matches(); 
	}
	
	 public static boolean isMoney(String money){     
	       String str="^([1-9][0-9]*|0)(\\.[\\d]{2})?$";
	        Pattern p = Pattern.compile(str);     
	        Matcher m = p.matcher(money);     
	        return m.matches();     
	    } 
	 public static boolean isDouble(String money){     
	       String str="^([1-9][0-9]*|0)(\\.[\\d]{2})$";
	        Pattern p = Pattern.compile(str);     
	        Matcher m = p.matcher(money);     
	        return m.matches();     
	    } 
	 
	 public static boolean isExtend_nuber(String extend_nuber){     
	       String str="^\\d*$";
	        Pattern p = Pattern.compile(str);     
	        Matcher m = p.matcher(extend_nuber);     
	        return m.matches();     
	    } 
	 
	 public static boolean isPrice(String price){     
	       String str="^([\\d]{1,2})(\\.[\\d]{1})?$";
	        Pattern p = Pattern.compile(str);     
	        Matcher m = p.matcher(price);     
	        return m.matches();     
	    }

	/**
	 *
	 * @param context
	 * @param pw
	 *            ，密码
	 * @param minLength
	 *            ,密码的最小长度
	 * @param maxLength
	 *            ，密码的最大长度
	 * @return 是否符合要求
	 */
	public static boolean checkPassWord(Context context, String pw,
										int minLength, int maxLength) {
		if (minLength < 0) {
			throw new IllegalAccessError("minLength can't be less than 0");
		}
		if (maxLength < minLength) {
			throw new IllegalAccessError(
					"maxLength is less than minLength;maxLength: " + maxLength
							+ " minlength: +" + minLength);
		}
		if (TextUtils.isEmpty(pw)) {
			SFToast.showToast( "请输入密码");
			return false;
		}
		if (pw.length() <= minLength|| pw.length() > maxLength) {
			SFToast.showToast( "密码不符合要求");
			return false;
		}
		return true;
	}


	/**
	 * 核对手机号码的格式 这个还有问题，后面改
	 * @param phone
	 * @return
	 */
	public static boolean checkPhone(Context context, String phone) {
		if (phone == null || TextUtils.isEmpty(phone)) {
			SFToast.showToast("请输入手机号码");
			return false;
		}
		Pattern pattern = Pattern.compile("^13/d{9}||15[8,9]/d{8}$");
		Matcher matcher = pattern.matcher(phone);

		if (matcher.matches()) {
			return true;
		}
		SFToast.showToast("手机号码格式不对");
		return false;
	}

	/**
	 * 检查邮件
	 * @param context
	 * @param email
	 * @return
	 */

	public static boolean checkEmail(Context context, String email) {
		if (email == null || TextUtils.isEmpty(email)) {
			SFToast.showToast("请输入邮件地址");
			return false;
		}
		Pattern pattern = Pattern.compile("\\w+@(\\w+.)+[a-z]{2,3}");
		Matcher matcher = pattern.matcher(email);
		if (matcher.matches()) {
			return true;
		}
		SFToast.showToast("邮件地址格式不对");
		return false;
	}

	 public static void main(String[] args) {
		 System.out.println(isEmail("60111283910qq@23.com"));
	}
}
