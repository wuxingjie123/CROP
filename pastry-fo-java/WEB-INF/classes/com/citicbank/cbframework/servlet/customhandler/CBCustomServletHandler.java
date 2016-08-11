package com.citicbank.cbframework.servlet.customhandler;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface CBCustomServletHandler {
	void handleRequest(HttpServletRequest request,HttpServletResponse response) throws IOException;
}
