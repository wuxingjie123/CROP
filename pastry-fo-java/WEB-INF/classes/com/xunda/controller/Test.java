package com.xunda.controller;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		isMobilePhone();
	}

	public static boolean isMobilePhone() {
		String str = "170208957856X";
		try {
			long i = Long.parseLong(str);
			boolean is = str.matches("^(13|15|18|17)\\d{9}$");
			System.out.println(is);
			return true;
		} catch (NumberFormatException e) {
			System.out.println(e.getMessage());
			return false;
		}

	}
}
