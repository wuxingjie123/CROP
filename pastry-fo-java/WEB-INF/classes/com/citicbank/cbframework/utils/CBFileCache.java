package com.citicbank.cbframework.utils;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.citicbank.cbframework.CBFramework;
import com.citicbank.cbframework.common.util.CBConverter;
import com.citicbank.cbframework.common.util.CBFileOperator;
import com.citicbank.cbframework.common.util.CBHash;
import com.citicbank.cbframework.usercontext.CBUserContext;

public class CBFileCache {
	private static Map<String,byte[]> fileMap=Collections.synchronizedMap(new HashMap<String,byte[]>());
	private static Map<String,String> fileMD5Map=Collections.synchronizedMap(new HashMap<String,String>());
	private static Map<String,String> fileSHA1Map=Collections.synchronizedMap(new HashMap<String,String>());
	private static String BASEURL;
	
	static{
		BASEURL=String.format("%scbframework/data/", CBFramework.WEBROOT);
	}
	
	public static byte[] getFile(CBUserContext context, String url){
		url=getTerminalUrl(context, url);
		byte[] data=fileMap.get(url);
		if(data==null){
			//读取文件
			if(readFile(url))
				data=fileMap.get(url);
		}
		return data;
	}
	
	private static String getTerminalUrl(CBUserContext context, String url) {
		return String.format("%s%s%s",context.getClientType().toLowerCase(), File.separator,url);
	}

	private static boolean readFile(String url) {
		String hash=null;
		try {
			byte[] data=CBFileOperator.getFileContent(BASEURL+url);
			hash=CBConverter.bytesToHex(CBHash.getHashByBytes(CBHash.MD5, data));
			fileMD5Map.put(url,hash);
			hash=CBConverter.bytesToHex(CBHash.getHashByBytes(CBHash.SHA1, data));
			fileSHA1Map.put(url,hash);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public static String getFileHash(CBUserContext context,String url,String type){
		url=getTerminalUrl(context, url);
		String hash=getHash(url, type);
		if(hash==null){
			//读取文件
			if(readFile(url))
				hash=getHash(url, type);
		}
		return hash;
	}
	
	private static String getHash(String url,String type){
		String hash=null;
		if("MD5".equals(type)){
			hash=fileMD5Map.get(url);
		}else if("SHA1".equals(type)){
			hash=fileSHA1Map.get(url);
		}
		return hash;
	}
}
