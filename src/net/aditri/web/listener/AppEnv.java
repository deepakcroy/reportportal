package net.aditri.web.listener;

import java.io.InputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedHashMap;

import javax.servlet.ServletContext;

import com.app.module.Module;

public class AppEnv implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private static String configFilePath = null;
	private static String webRootPath = null;
	private static ServletContext servletContext = null;
	private static LinkedHashMap<String, Module> modules = null;

	public static LinkedHashMap<String, Module> getModules() {
		return modules;
	}

	public static void setModules(LinkedHashMap<String, Module> modules) {
		AppEnv.modules = modules;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static HashMap<Object, Object> configMap = new HashMap();

	public static String getWebRootPath() {
		return webRootPath;
	}

	public static void setWebRootPath(String _webRootPath) {
		webRootPath = _webRootPath;
	}

	public static void setConfigFilePath(String path) {
		configFilePath = path;
	}

	public static String getConfigFilePath() {
		return configFilePath;
	}

	public static void setObject(Object key, Object obj) {
		configMap.put(key, obj);
	}

	public static Object getObject(Object key) {
		return configMap.get(key);
	}

	public static String getParameter(String key) {
		return (String) configMap.get(key);
	}

	public static ServletContext getServletContext() {
		return servletContext;
	}

	public static void setServletContext(ServletContext _servletContext) {
		servletContext = _servletContext;
	}

	public static InputStream getConfigFileStream(String path) {
		return getServletContext().getResourceAsStream(path);
	}
}
