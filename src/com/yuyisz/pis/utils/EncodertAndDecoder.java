package com.yuyisz.pis.utils;

public class EncodertAndDecoder {

	// 加密方法 ,使用base64加密3次
	public static String encoder(String pwd) {
		for (int i = 0; i < 3; i++) {
			pwd = SystemApi.exec("echo '" + pwd + "'|base64").trim();
		}
		return pwd;
	}

	// 解密方法 ,使用base64解密3次
	public static String decoder(String pwd) {
		for (int i = 0; i < 3; i++) {
			pwd = SystemApi.exec("echo '" + pwd + "'|base64 -d").trim();
		}
		return pwd;
	}
}
