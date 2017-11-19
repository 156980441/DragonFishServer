package com.fanyl.web;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.portlet.ModelAndView;

import com.fanyl.dao.ServerDao;
import com.fanyl.domain.Advertise;
import com.fanyl.domain.Page;
import com.fanyl.domain.StartPageBean;
import com.liang.web.util.StringUtil;

@Controller
@RequestMapping(value = "/login")
public class ServerLoginController {
	
	Logger logger = Logger.getLogger(ServerLoginController.class);

	@Autowired
	private ServerDao serverDaoImpl;

	@RequestMapping(value = "/in")
	@ResponseBody
	public String loginIn(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return serverDaoImpl.findUser(request, response);
	}

	@RequestMapping(value = "/out", method = RequestMethod.GET)
	public void loginOut(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.getSession().removeAttribute("LoginUser");
		request.getRequestDispatcher("/").forward(request, response);
	}

	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public ModelAndView loginHome(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap)
			throws Exception {
		return new ModelAndView("/login/home", modelMap);
	}

	@RequestMapping(value = "/video")
	public ModelAndView video(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap)
			throws Exception {
		modelMap.put("videoList", serverDaoImpl.getObjectList(request, "sys.login.findUser"));
		return new ModelAndView("/login/video", modelMap);
	}

	// 这些参数是怎么来的？
	@RequestMapping(value = "/viewUserList")
	public ModelAndView viewUserList(HttpServletRequest request, Page page, ModelMap modelMap) throws Exception {
		List<Object> userList = serverDaoImpl.getObjectList(request, "sys.login.findUser");
		int userTotalCount = userList.size();
		page.setTotalCount(userTotalCount);
		modelMap.put("page", page);
		modelMap.put("videoList", serverDaoImpl.getObjectListByPage(request, "sys.login.findUserByPage", page));
		return new ModelAndView("/login/viewUserList", modelMap);
	}

	@RequestMapping(value = "/viewAddUserInfo")
	public ModelAndView viewAddResumeInfo(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap)
			throws Exception {
		List<Object> userInfoList = serverDaoImpl.getObjectList(request, "sys.login.findUserInfo");
		if (userInfoList != null && userInfoList.size() > 0) {
			modelMap.put("userInfo", userInfoList.get(0));
		}
		return new ModelAndView("/login/viewAddUserInfo", modelMap);
	}

	@RequestMapping(value = "/addUserInfo")
	@ResponseBody
	public Map<String, String> addUserInfo(HttpServletRequest request) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		// seq 不为空：修改，为空：新增
		String seq = StringUtil.checkNull(request.getParameter("SEQ"));
		int result = 1;
		if (!"".equals(seq)) {
			result = this.serverDaoImpl.addInfo(request, "sys.login.updateUserInfo");
		} else {
			Map<String, String> userMap = new LinkedHashMap<String, String>();
			userMap.put("USER_NAME", request.getParameter("USER_NAME"));
			List<Object> userList = this.serverDaoImpl.getInfoList(userMap, "sys.login.findUser");
			if (userList == null || userList.size() == 0) {
				result = this.serverDaoImpl.addInfo(request, "sys.login.addUserInfo");
			} else {
				result = 2;
			}
		}
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", "保存成功");
			map.put("formId", "viewUserInfoForm");
		} else if (result == 2) {
			map.put("statusCode", "300");
			map.put("message", "用户名已存在");
		} else {
			map.put("statusCode", "300");
			map.put("message", "保存失败");
		}
		return map;
	}

	@RequestMapping(value = "/deleteUserInfo")
	public @ResponseBody Map deleteUserInfo(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		// seq不为空：修改，为空：新增
		String seq = StringUtil.checkNull(request.getParameter("SEQ"));
		int result = 1;
		if (!"".equals(seq)) {
			result = this.serverDaoImpl.deleteInfo(seq, "sys.login.deleteUserInfo");
		}
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", "删除成功");
			map.put("formId", "viewUserInfoForm");
			map.put("callbackType", "forward");
			map.put("forward", "/login/viewUserList");
		} else {
			map.put("statusCode", "300");
			map.put("message", "删除失败");
		}
		return map;
	}

	@RequestMapping(value = "/deleteMachineInfo")
	public @ResponseBody Map deleteMachineInfo(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		// seq不为空：修改，为空：新增
		String seq = StringUtil.checkNull(request.getParameter("SEQ"));
		int result = 1;
		if (!"".equals(seq)) {
			result = this.serverDaoImpl.deleteInfo(seq, "sys.login.deleteMachineInfo");
		}
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", "删除成功");
			map.put("formId", "viewMachineInfoForm");
			map.put("callbackType", "forward");
			map.put("forward", "/business/viewMachineList");
		} else {
			map.put("statusCode", "300");
			map.put("message", "删除失败");
		}
		return map;
	}

	@RequestMapping(value = "/deleteAdvertiseInfo")
	public @ResponseBody Map deleteAdvertiseInfo(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		// seq不为空：修改，为空：新增
		String seq = StringUtil.checkNull(request.getParameter("SEQ"));
		int result = 1;
		if (!"".equals(seq)) {
			// 获取图片所在的路径
			Map advertiesMap = new LinkedHashMap();
			advertiesMap.put("SEQ", seq);
			List<Advertise> advertisList = serverDaoImpl.getInfoList(advertiesMap,
					"sys.business.viewAdvertiseListBySEQ");
			String[] pic_urls = new String[3];
			if (advertisList != null && advertisList.size() > 0) {
				Advertise obj = advertisList.get(0);
				pic_urls[0] = obj.getPic_url1();
				pic_urls[1] = obj.getPic_url2();
				pic_urls[2] = obj.getPic_url3();
			}
			String path = Thread.currentThread().getContextClassLoader().getResource("").getPath();
			path = path.replace("\\", "/").replace("WEB-INF/classes/", "");
			// System.out.println(path);
			// 删除数据库中的数据
			result = this.serverDaoImpl.deleteInfo(seq, "sys.login.deleteAdvertiseInfo");
			// 删除图片
			try {
				for (int i = 0; i < pic_urls.length; i++) {
					String pic_url = pic_urls[i];
					if (pic_url != null && !"".equals(pic_url)) {
						File file = new File(path + pic_url);
						if (file.isFile())
							file.delete();
					}
				}
			} catch (Exception e) {
				result = 0;
			}
		}
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", "删除成功");
			map.put("formId", "viewAdvertiseInfoForm");
			map.put("callbackType", "forward");
			map.put("forward", "/business/viewAdvertiseList");
		} else {
			map.put("statusCode", "300");
			map.put("message", "删除失败");
		}
		return map;
	}

	@RequestMapping(value = "/deleteStartPageInfo")
	public @ResponseBody Map deleteStartPageInfo(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		// seq不为空：修改，为空：新增
		String seq = StringUtil.checkNull(request.getParameter("SEQ"));
		int result = 1;
		if (!"".equals(seq)) {
			// 获取图片所在的路径
			Map<String, String> startPageMap = new LinkedHashMap<String, String>();
			startPageMap.put("SEQ", seq);
			List<Object> advertisList = serverDaoImpl.getInfoList(startPageMap,
					"sys.business.viewStartPageListBySEQ");
			String pic_url = null;
			if (advertisList != null && advertisList.size() > 0) {
				StartPageBean obj = (StartPageBean)advertisList.get(0);
				pic_url = obj.getPic_url();
			}
			String path = Thread.currentThread().getContextClassLoader().getResource("").getPath();
			path = path.replace("\\", "/").replace("WEB-INF/classes/", "");
			// System.out.println(path);
			// 删除数据库中的数据
			result = this.serverDaoImpl.deleteInfo(seq, "sys.login.deleteStartPageInfo");
			// 删除图片
			try {
				if (pic_url != null && !"".equals(pic_url)) {
					File file = new File(path + pic_url);
					if (file.isFile())
						file.delete();
				}
			} catch (Exception e) {
				result = 0;
			}
		}
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", "删除成功");
			map.put("formId", "viewAdvertiseInfoForm");
			map.put("callbackType", "forward");
			map.put("forward", "/business/viewAdvertiseList");
		} else {
			map.put("statusCode", "300");
			map.put("message", "删除失败");
		}
		return map;
	}
}
