package org.cityu.group6.generator.util;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.cityu.group6.generator.entity.FileGenerationInfo;
import org.cityu.group6.generator.entity.PageEnum;

/**
 * @author Kevin
 *
 */
public class ParameterManager {

	private static Map<String, Object> parameterMap= new HashMap<>();

	public static void putParam(String key, Object value) {
		parameterMap.put(key, value);
	}

	public static Object getParam(String key) {
		return parameterMap.get(key);
	}
	
	public static boolean isGenerate(String fileName) {
		LinkedHashMap<String, FileGenerationInfo> fileMap = (LinkedHashMap<String, FileGenerationInfo>) ParameterManager.getParam(PageEnum.FOURTH_PAGE.getPageName());
		if (fileMap.get(fileName) != null) {
			return true;
		} else {
			return false;
		}
	}
}
