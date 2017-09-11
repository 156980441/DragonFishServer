package com.liang.web.login;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.portlet.ModelAndView;

import com.liang.sys.bean.Page;
import com.liang.sys.bean.TournamentContent;
import com.liang.sys.service.InfoSer;
import com.liang.web.util.StringUtil;

@Controller
@RequestMapping(value = "/business")
public class LoginBusinessCtroller {

	Logger logger = Logger.getLogger(LoginBusinessCtroller.class);

	@Autowired
	private InfoSer infoStr;

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewMachineList")
	public ModelAndView viewMachineList(HttpServletRequest request,
			Page page, ModelMap modelMap) throws Exception {
		List mechineList = infoStr.getInfoList(request, "sys.business.viewMachineList");
		int mechineTotalCount = mechineList.size();
		page.setTotalCount(mechineTotalCount);
		modelMap.put("page", page);
		modelMap.put("viewMachineList", infoStr.getInfoListByPage(request, "sys.business.viewMachineListByPage", page));
		return new ModelAndView("/business/viewMachineList", modelMap);
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewAdvertiseList")
	public ModelAndView viewAdvertiseList(HttpServletRequest request,
			Page page, ModelMap modelMap) throws Exception {
		List advertiseList = infoStr.getInfoList(request, "sys.business.viewAdvertiseList");
		int avertiseTotalCount = advertiseList.size();
		page.setTotalCount(avertiseTotalCount);
		modelMap.put("page", page);
		modelMap.put("viewAdvertiseList", infoStr.getInfoListByPage(request, "sys.business.viewAdvertiseListByPage", page));
		return new ModelAndView("/business/viewAdvertiseList", modelMap);
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewAddAdvertiseInfo")
	public ModelAndView viewAddAdvertiseInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		List advertiseList = infoStr.getInfoList(request, "sys.business.viewAdvertiseList");
		if(advertiseList != null &&advertiseList.size()>0){
			modelMap.put("advertiseInfo", advertiseList.get(0));
		}
		return new ModelAndView("/business/viewAddAdvertiseInfo", modelMap);
	}
	
	@RequestMapping(value = "/viewStartPageList")
	public ModelAndView viewStartPageList(HttpServletRequest request,
			Page page, ModelMap modelMap) throws Exception {
		List startPageList = infoStr.getInfoList(request, "sys.business.viewStartPageList");
		int startPageTotalCount = startPageList.size();
		page.setTotalCount(startPageTotalCount);
		modelMap.put("page", page);
		modelMap.put("viewStartPageList", infoStr.getInfoListByPage(request, "sys.business.viewStartPageListByPage", page));
		return new ModelAndView("/business/viewStartPageList", modelMap);
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewAddStartPageInfo")
	public ModelAndView viewAddStartPageInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		List startPageList = infoStr.getInfoList(request, "sys.business.viewStartPageList");
		if(startPageList != null &&startPageList.size()>0){
			modelMap.put("startPageInfo", startPageList.get(0));
		}
		return new ModelAndView("/business/viewAddStartPageInfo", modelMap);
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addStartPageInfo")
	@ResponseBody
	public Map addStartPageInfo(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		//seq不为空：修改，为空：新增
		String seq = StringUtil.checkNull(request.getParameter("SEQ"));
		int result = 1;
		if(!"".equals(seq)){
			result = this.infoStr.addStartPageInfo(request, "sys.business.updateStartPageInfo");
		}else{
			result = this.infoStr.addStartPageInfo(request, "sys.business.addStartPageInfo");
		}
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", "保存成功");
			map.put("formId", "viewStartPageListForm");
			map.put("callbackType", "closeCurrent");
		} else {
			map.put("statusCode", "300");
			map.put("message", "保存失败");
		}
		return map;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addAdvertiseInfo")
	@ResponseBody
	public Map addAdvertiseInfo(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		//seq不为空：修改，为空：新增
		String seq = StringUtil.checkNull(request.getParameter("SEQ"));
		int result = 1;
		if(!"".equals(seq)){
			result = this.infoStr.addInfo(request, "sys.business.updateAdvertiseInfo");
		}else{
			result = this.infoStr.addInfo(request, "sys.business.addAdvertiseInfo");
		}
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", "保存成功");
			map.put("formId", "viewAdvertiseListForm");
			map.put("callbackType", "closeCurrent");
		} else {
			map.put("statusCode", "300");
			map.put("message", "保存失败");
		}
		return map;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewAddMachineInfo")
	public ModelAndView viewAddResumeInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		List machineInfoList = infoStr.getInfoList(request, "sys.business.viewMachineList");
		if(machineInfoList != null &&machineInfoList.size()>0){
			modelMap.put("machineInfo", machineInfoList.get(0));
		}
		return new ModelAndView("/business/viewAddMachineInfo", modelMap);
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addMachineInfo")
	@ResponseBody
	public Map addMachineInfo(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		//seq不为空：修改，为空：新增
		String seq = StringUtil.checkNull(request.getParameter("SEQ"));
		int result = 1;
		if(!"".equals(seq)){
			result = this.infoStr.addInfo(request, "sys.business.updateMachineInfo");
		}else{
			result = this.infoStr.addInfo(request, "sys.business.addMachineInfo");
		}
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", "保存成功");
			map.put("formId", "viewMachineInfoForm");
		} else {
			map.put("statusCode", "300");
			map.put("message", "保存失败");
		}
		return map;
	}
	/**
	 * 附件上传
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @Description: TODO
	 * @author lwei liangwei@ait.net.cn
	 * @date 2013-9-12 下午4:18:11
	 * @version V1.0
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/uploadBatch")
	public ModelAndView uploadBatch(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		MultipartHttpServletRequest multipartRequest = null;
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
		if (!(request instanceof MultipartHttpServletRequest)
				&& multipartResolver.isMultipart(request)) {
			try {
				multipartResolver.setMaxUploadSize(524288000);
				multipartRequest = multipartResolver.resolveMultipart(request);
			} catch (MaxUploadSizeExceededException e) {
				return null;
			}
		} else if (request instanceof MultipartHttpServletRequest) {
			multipartRequest = (MultipartHttpServletRequest) request;
		} else {
			return null;
		}
		String uuid = String.valueOf(UUID.randomUUID());

		/** 构建附件保存的目录 **/
		String logoPathDir = "/resources/temp";
		/** 得到附件保存目录的真实路径 **/
		String logoRealPathDir = request.getSession().getServletContext()
				.getRealPath(logoPathDir);
		/** 根据真实路径创建目录 **/
		File logoSaveFile = new File(logoRealPathDir);
		if (!logoSaveFile.exists())
			logoSaveFile.mkdirs();
		/** 页面控件的文件流 **/
		MultipartFile multipartFile = multipartRequest.getFile("file");
		// 构建文件名称
		String logImageName = multipartFile.getOriginalFilename();
		/** 获取文件的后缀 **/
		String suffix = multipartFile.getOriginalFilename().substring(
				multipartFile.getOriginalFilename().lastIndexOf("."));
		/** 使用UUID生成文件名称 **/
		String fileName = logoRealPathDir + File.separator + uuid + suffix;
		/** 拼成完整的文件保存路径加文件 **/
		File file = new File(fileName);
		try {
			multipartFile.transferTo(file);
			modelMap.put("sign", 1);
			modelMap.put("logImageName", logImageName);
			modelMap.put("fileUrl", uuid + suffix);
		} catch (IllegalStateException e) {
			e.printStackTrace();
			modelMap.put("sign", -1);
		} catch (IOException e) {
			e.printStackTrace();
			modelMap.put("sign", -1);
		}
		return new ModelAndView("/business/uploadBatch", modelMap);
	}
	
	@RequestMapping("/jsonfeed")  
    public @ResponseBody Object getJSON(Model model) {  
        List<TournamentContent> tournamentList = new ArrayList<TournamentContent>();  
        tournamentList.add(TournamentContent.generateContent("FIFA", new Date(), "World Cup", "www.fifa.com/worldcup/"));  
        tournamentList.add(TournamentContent.generateContent("FIFA", new Date(), "U-20 World Cup", "www.fifa.com/u20worldcup/"));  
        tournamentList.add(TournamentContent.generateContent("FIFA", new Date(), "U-17 World Cup", "www.fifa.com/u17worldcup/"));  
        tournamentList.add(TournamentContent.generateContent("FIFA", new Date(), "Confederations Cup", "www.fifa.com/confederationscup/"));  
        model.addAttribute("items", tournamentList);  
        model.addAttribute("status", 0);  
        return model;
    }  
	
}
