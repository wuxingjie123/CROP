package com.citicbank.cbframework.servlet.customhandler;

import org.json.JSONObject;

public class CBBusinessHandlerResult {
	public final static int RESULT_DONE = 0;
	public final static int RESULT_FAILED = 1;
	
	private int result;
	private JSONObject data;

	public CBBusinessHandlerResult(int result,JSONObject data) {
		this.result = result;
		this.data = data;
	}
	
	public JSONObject getData() {
		return data;
	}
	
	public int getResult() {
		return result;
	}
}
