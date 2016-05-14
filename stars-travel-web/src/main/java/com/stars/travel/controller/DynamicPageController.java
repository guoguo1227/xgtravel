/*
* @(#)DynamicPageController.java 1.0 2014-12-20
*
* Copyright (c) 2013-2014 JiangXi Hangtian PoHuYun(JXHTPHY), Inc. 
* All Rights Reserved.
*
* This software is the confidential and proprietary information of JiangXi 
* Hangtian PoHuYun, Inc. ("Confidential Information"). You shall not
* disclose such Confidential Information and shall use it only in
* accordance with the terms of the license agreement you entered into
* with JXHTPHY.
*
* JXHTPHY MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE SUITABILITY OF
* THE SOFTWARE, EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED
* TO THE IMPLIED WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
* PARTICULAR PURPOSE, OR NON-INFRINGEMENT. JXHTPHY SHALL NOT BE LIABLE FOR
* ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR
* DISTRIBUTING THIS SOFTWARE OR ITS DERIVATIVES.
*/
package com.stars.travel.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * @类名: DynamicPageController
 * @描述: 动态首页控制器
 * @版本: 
 * @创建日期: 2014-12-20下午12:37:43
 * @作者: gzs
 * @JDK: 1.6
 */
/*
 * 类的横向关系：
 */
@Controller
public class DynamicPageController extends BaseController{
	
	/**
	 * Logger log
	 */
	private static final Logger log = LoggerFactory.getLogger(DynamicPageController.class);
	
	/**
	 * String TPL_INDEX
	 */
	private static final String TPL_INDEX = "tpl.index";
	
	/**
	 * String INDEX
	 */
	private static final String INDEX = "index";

	/**
	 * String GROUP_FORBIDEN
	 */
	private static final String GROUP_FORBIDEN = "view.groupAccessForbidden";
	
	/**
	 * String RESOURCE_NOT_VALID
	 */
	private static final String RESOURCE_NOT_VALID = "view.notValid";
	
	/**
	 * index
	 * @描述: TOMCAT的默认路径
	 * @作者: gzs
	 * @创建时间: 2014-12-23上午10:46:08
	 */
	 
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index(HttpServletRequest request, ModelMap modelMap) {
		return "index";
	}

	/**
	 * indexForWeblogic
	 * @描述: WEBLOGIC的默认路径
	 * @作者: gzs
	 * @创建时间: 2014-12-23上午10:46:36
	 */
	 
	@RequestMapping(value = "/index.jhtml", method = RequestMethod.GET)
	public String indexForWeblogic(HttpServletRequest request, ModelMap modelMap) {
		return index(request, modelMap);
	}
	
	@RequestMapping(value = "/error.jhtml", method = RequestMethod.GET)
	public String errorForWeb(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
        return "index";
	}
}
