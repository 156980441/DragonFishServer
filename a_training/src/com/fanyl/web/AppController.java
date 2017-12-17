package com.fanyl.web;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fanyl.dao.AppDao;
import com.fanyl.domain.RelaySwitch;
import com.fanyl.domain.User;
import com.liang.web.util.TCPSocketThread;
import com.liang.web.util.TCPSocketService;

/*处理手机客户端发来的请求*/

// SpringMVC
// @PathVariable 是用来获得请求 url 中的动态参数的。
// @ResponseBody 转换为指定格式后，这里是 List，写入到 Response 对象的 body 数据区
// @RequestMapping 是一个用来处理请求地址映射的注解，可用于类或方法上。用于类上，表示类中的所有响应请求的方法都是以该地址作为父路径。
// @Controller 它标记的类就是一个SpringMVC Controller 对象。分发处理器将会扫描使用了该注解的类的方法。通俗来说，被Controller标记的类就是一个控制器，这个类中的方法，就是相应的动作。
// @Service("userService")注解是告诉spring，当Spring要创建UserServiceImpl的的实例时，bean的名字必须叫做"userService"，这样当Action需要使用UserServiceImpl的的实例时,就可以由Spring创建好的"userService"，然后注入给Action。

@Controller
@RequestMapping(value = "/interface")

public class AppController {

	// 获取日志记录器，这个记录器将负责控制日志信息。Name 一般取本类的名字。
	Logger logger = Logger.getLogger(AppController.class);

	@Autowired
	private AppDao appDaoImp;

	/**
	 * http://localhost:8000/interface/getAdvertiseList?LAYOUT=1
	 */
	@RequestMapping(value = "/getAdvertiseList/{layout}", method = RequestMethod.GET)
	public @ResponseBody Object advertiseList(@PathVariable String layout) {
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put("LAYOUT", layout);
		return appDaoImp.getInfoList(map, "sys.business.viewAdvertiseList");
	}

	/**
	 * http://localhost:8000/interface/getAdvertiseListByCity?city=1
	 */
	@RequestMapping(value = "/getAdvertiseListByCity/{city}", method = RequestMethod.GET)
	public @ResponseBody Object advertiseListByCity(@PathVariable String city) {
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put("city", city);
		return appDaoImp.getInfoList(map, "sys.business.viewAdvertiseList");
	}

	/**
	 * http://localhost:8000/interface/getStartPageListByCity?city=1
	 */
	@RequestMapping(value = "/getStartPageListByCity/{city}", method = RequestMethod.GET)
	public @ResponseBody Object getStartPageListByCity(@PathVariable String city) {
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put("city", city);
		map.put("ACTIVITY", "1");
		return appDaoImp.getInfoList(map, "sys.business.viewStartPageList");
	}

	/**
	 * http://localhost:8000/interface/getMachineList?USER_NO=XXX
	 */
	@RequestMapping(value = "/getMachineList/{userNo}", method = RequestMethod.GET)
	public @ResponseBody Object getMachineList(@PathVariable String userNo) {
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put("USER_NO", userNo);
		return appDaoImp.getInfoList(map, "sys.business.viewMachineListForApp");
	}

	/**
	 * http://localhost:8000/interface/getMachineInfo?SEQ=XXX
	 */
	@RequestMapping(value = "/getMachineInfo/{seq}", method = RequestMethod.GET)
	public @ResponseBody Object getMachineInfo(@PathVariable String seq) {
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put("SEQ", seq);
		return appDaoImp.getInfoList(map, "sys.business.viewMachineList");
	}

	/**
	 * http://localhost:8080/interface/addMachineInfo
	 * {"USER_NO":"5","MACHINE_ID":"9999","MACHINE_TITLE":"fanyl_test_device"}
	 */
	@RequestMapping(value = "/addMachineInfo", method = RequestMethod.POST)
	public @ResponseBody Object addMachineInfo(@RequestBody Map<String, String> map, Model model) {
		String msg = appDaoImp.addMachineInfo(map, "sys.business.addMachineInfoForApp");
		if ("OK".equals(msg)) {
			model.addAttribute("statusCode", "200");
		} else {
			model.addAttribute("statusCode", "300");
		}
		model.addAttribute("message", msg);
		return model;
	}

	/**
	 * http://localhost:8080/interface/addUserInfo
	 * {"USER_NAME":"admin5","PASSWORD":"123456","MAIL":"156980441@qq.com"}
	 */
	@RequestMapping(value = "/addUserInfo", method = RequestMethod.POST)
	public @ResponseBody Object addUserInfo(@RequestBody Map<String, String> map, Model model) {
		String msg = appDaoImp.addUserInfo(map, "sys.login.addUserInfoForApp");
		if ("OK".equals(msg)) {
			model.addAttribute("statusCode", "200");
		} else {
			model.addAttribute("statusCode", "300");
		}
		model.addAttribute("message", msg);
		return model;
	}

	/**
	 * http://localhost:8080/interface/login test:firefox restclient
	 * {"USER_NAME":"admin","PASSWORD":"123456"} Content-Type application/json
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public @ResponseBody Object appUserLogin(@RequestBody Map<String, String> map, Model model) {
		User admin = appDaoImp.login(map, "sys.login.findUserForApp");
		if (admin != null) {
			model.addAttribute("statusCode", "200");
		} else {
			model.addAttribute("statusCode", "300");
		}
		model.addAttribute("admin", admin);
		return model;
	}

	/**
	 * http://localhost:8000/interface/deleteMachineInfo/USER_NO/MACHINE_ID
	 */
	@RequestMapping(value = "/deleteMachineInfo/{USER_NO}/{MACHINE_ID}", method = RequestMethod.GET)
	public @ResponseBody Object deleteMachineInfo(@PathVariable String USER_NO, @PathVariable String MACHINE_ID,
			Model model) {
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put("USER_NO", USER_NO);
		map.put("MACHINE_ID", MACHINE_ID);
		String msg = appDaoImp.deleteMachineInfo(map, "sys.login.deleteMachineInfoForApp");
		if ("OK".equals(msg)) {
			model.addAttribute("statusCode", "200");
			// delete device and release thread source
			if (TCPSocketThread.socketMap.containsKey(MACHINE_ID)) {
				TCPSocketService service = TCPSocketThread.socketMap.get(MACHINE_ID);
				Socket socket = service.connectedsocket;
				try {
					socket.shutdownInput(); // 这里关掉之后并没有释放线程资源
					socket.shutdownOutput();// 这里关掉之后释放线程资源
				} catch (IOException e) {
					System.out.println("deleteMachineInfo shutdownInput exception");
					e.printStackTrace();
				}
			}
		} else {
			model.addAttribute("statusCode", "300");
		}
		model.addAttribute("message", msg);
		return model;
	}

	/**
	 * set device open or close
	 * http://localhost:8080/interface/setRelaySwitch/{MACHINE_ID}/{status}
	 */
	@RequestMapping(value = "/setRelaySwitch/{MACHINE_ID}/{status}", method = RequestMethod.GET)
	public @ResponseBody Object setRelaySwitch(@PathVariable String MACHINE_ID, @PathVariable String status) {
		
		logger.debug("APP 设备开关设置 "+ MACHINE_ID + " 将要 " + status);
		
		RelaySwitch relaySwitch = new RelaySwitch();
		relaySwitch.setCode("300");

		// 针对当前设备有没有开启连接服务
		if (TCPSocketThread.socketMap.containsKey(MACHINE_ID)) {
			Socket socket = null;
			DataOutputStream dos = null;
			TCPSocketService service = TCPSocketThread.socketMap.get(MACHINE_ID);
			socket = service.connectedsocket;
			try {
				dos = new DataOutputStream(socket.getOutputStream());
				
				if (status.equals("0")) {
					String str = "&R,0!";
					dos.write(str.getBytes());
				} else if (status.equals("1")) {
					String str = "&R,1!";
					dos.write(str.getBytes());
				}
				
				Map<String, String> paramMap = new LinkedHashMap<String, String>();
				paramMap.put("STATE", status);
				paramMap.put("ID", MACHINE_ID);
				appDaoImp.updateInfo(paramMap, "sys.business.appUpdateMachineStateById");
				relaySwitch.setCode("200");
				
				logger.debug("APP 设备开关设置 "+ MACHINE_ID + " 成功 ");
				
			} catch (IOException e) {
				logger.debug("APP 设备开关设置 "+ MACHINE_ID + " 失败 " + e.getLocalizedMessage());
				e.printStackTrace();
			}
		} else {
			logger.debug("APP 设备开关设置 "+ MACHINE_ID + " 将要 " + status + " 失败，该设备没有连接服务器。");
		}

		return relaySwitch;
	}

	/**
	 * set device name
	 */
	@RequestMapping(value = "/setMechineName/{MACHINE_ID}/{MACHINE_NAME}", method = RequestMethod.GET)
	public @ResponseBody Object setMechineName(@PathVariable String MACHINE_ID, @PathVariable String MACHINE_NAME) {
		RelaySwitch relaySwitch = new RelaySwitch();
		relaySwitch.setCode("300");
		try {
			MACHINE_NAME = new String(MACHINE_NAME.getBytes("iso-8859-1"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		if (MACHINE_ID != null && !MACHINE_ID.equals("") && MACHINE_NAME != null && !"".equals(MACHINE_NAME)) {
			Map<String, String> paramMap = new LinkedHashMap<String, String>();
			paramMap.put("TITLE", MACHINE_NAME);
			paramMap.put("ID", MACHINE_ID);
			int result = appDaoImp.updateInfo(paramMap, "sys.business.updateMachineInfoByMachineId");
			if (result == 1) {
				relaySwitch.setCode("200");
			}
		}
		return relaySwitch;
	}
}
