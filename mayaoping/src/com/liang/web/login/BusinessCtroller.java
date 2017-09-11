package com.liang.web.login;

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

import com.liang.sys.bean.AdminBean;
import com.liang.sys.bean.RelaySwitch;
import com.liang.sys.service.InfoSer;
import com.liang.web.util.SocketThread;
import com.liang.web.util.TcpSocketService;

@Controller
@RequestMapping(value = "/interface")
public class BusinessCtroller {

	Logger logger = Logger.getLogger(BusinessCtroller.class);

	@Autowired
	private InfoSer infoStr;
	/**
	 * http://localhost:8000/interface/getAdvertiseList?LAYOUT=1
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/getAdvertiseList/{layout}", method = RequestMethod.GET)  
    public @ResponseBody Object getAdvertiseList(@PathVariable String layout) { 
		Map map = new LinkedHashMap();
		map.put("LAYOUT", layout);
		return infoStr.getInfoList(map, "sys.business.viewAdvertiseList"); 
    }  
	
	/**
	 * http://localhost:8000/interface/getAdvertiseListByCity?city=1
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/getAdvertiseListByCity/{city}", method = RequestMethod.GET)  
    public @ResponseBody Object getAdvertiseListByCity(@PathVariable String city) { 
		Map map = new LinkedHashMap();
		map.put("city", city);
		return infoStr.getInfoList(map, "sys.business.viewAdvertiseList"); 
    }
	
	/**
	 * http://localhost:8000/interface/getStartPageListByCity?city=1
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/getStartPageListByCity/{city}", method = RequestMethod.GET)  
    public @ResponseBody Object getStartPageListByCity(@PathVariable String city) { 
		Map map = new LinkedHashMap();
		map.put("city", city);
		map.put("ACTIVITY", 1);
		return infoStr.getInfoList(map, "sys.business.viewStartPageList"); 
    }
	
	/**
	 * http://localhost:8000/interface/getMachineList?USER_NO=XXX
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/getMachineList/{userNo}", method = RequestMethod.GET)  
    public @ResponseBody Object getMachineList(@PathVariable String userNo) {
		Map map = new LinkedHashMap();
		map.put("USER_NO", userNo); 
		return infoStr.getInfoList(map, "sys.business.viewMachineListForApp");
    }  

	/**
	 * http://localhost:8000/interface/getMachineInfo?SEQ=XXX
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/getMachineInfo/{seq}", method = RequestMethod.GET)
    public @ResponseBody Object getMachineInfo(@PathVariable String seq) {
		Map map = new LinkedHashMap();
		map.put("SEQ", seq);
		return infoStr.getInfoList(map, "sys.business.viewMachineList");
    }

	/**
	 * http://localhost:8000/interface/addMachineInfo?jsonData=[{"USER_NO":"5","MACHINE_ID":"111","MACHINE_TITLE":"设备"}]
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/addMachineInfo",method=RequestMethod.POST)  
    public @ResponseBody Object addMachineInfo(@RequestBody Map map,Model model) {  
		String msg = infoStr.addMachineInfo(map, "sys.business.addMachineInfoForApp");
		if("OK".equals(msg)){
			model.addAttribute("statusCode", "200");
		}else{
			model.addAttribute("statusCode", "300");
		}
		model.addAttribute("message", msg);
        return model;
    }

	/**
	 * http://localhost:8000/interface/addUserInfo?jsonData=[{"USER_NAME":"admin5","PASSWORD":"123456"}]
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/addUserInfo",method=RequestMethod.POST)  
    public @ResponseBody Object addUserInfo(@RequestBody Map map,Model model) { 
		String msg = infoStr.addUserInfo(map, "sys.login.addUserInfoForApp");
		if("OK".equals(msg)){
			model.addAttribute("statusCode", "200");
		}else{
			model.addAttribute("statusCode", "300");
		}
		model.addAttribute("message", msg);
        return model;
    }

	/**
	 * http://localhost:8000/interface/login?jsonData=[{"USER_NAME":"admin5","PASSWORD":"123456"}]
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/login",method=RequestMethod.POST)
    public @ResponseBody Object login(@RequestBody Map map,Model model) {
		AdminBean admin = infoStr.login(map, "sys.login.findUserForApp");
		if(admin != null){
			model.addAttribute("statusCode", "200");
		}else{
			model.addAttribute("statusCode", "300");
		}
		model.addAttribute("admin", admin);
        return model;
    }
	
	/**
	 * http://localhost:8000/interface/deleteMachineInfo/USER_NO/MACHINE_ID
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/deleteMachineInfo/{USER_NO}/{MACHINE_ID}",method=RequestMethod.GET)  
    public @ResponseBody Object deleteMachineInfo(@PathVariable String USER_NO, @PathVariable String MACHINE_ID, Model model) {  
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put("USER_NO", USER_NO);
		map.put("MACHINE_ID", MACHINE_ID);
		String msg = infoStr.deleteMachineInfo(map, "sys.login.deleteMachineInfoForApp");
		if("OK".equals(msg)){
			model.addAttribute("statusCode", "200");
		}else{
			model.addAttribute("statusCode", "300");
		}
		model.addAttribute("message", msg);
        return model;
    }
	
	/**
	 * 设置继电器状态
	 * @param USER_NO
	 * @param USER_NO
	 * @return
	 */
	@RequestMapping(value="/setRelaySwitch/{MACHINE_ID}/{status}",method=RequestMethod.GET)
	public @ResponseBody Object setRelaySwitch(@PathVariable String MACHINE_ID, @PathVariable String status) {
		Socket socket = null;
		DataOutputStream dos = null;
		RelaySwitch relaySwitch = new RelaySwitch();
		relaySwitch.setCode("300");
		try {
			if(SocketThread.socketMap.containsKey(MACHINE_ID)) {
				TcpSocketService service = SocketThread.socketMap.get(MACHINE_ID);
				socket = service.connectedsocket;
			}
			if(socket != null) {
				dos = new DataOutputStream(socket.getOutputStream());
			}
			if(dos != null) {
				if(status.equals("0")) {
					String str = "&R,0!";
					if(dos != null) dos.write(str.getBytes());
					relaySwitch.setCode("200");
				} else if(status.equals("1")){
					String str = "&R,1!";
					if(dos != null) dos.write(str.getBytes());
					relaySwitch.setCode("200");
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(dos != null)dos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return relaySwitch;
	}
	
	/**
	 * 设置机器的名称
	 * @param MACHINE_ID
	 * @return
	 */
	@RequestMapping(value="/setMechineName/{MACHINE_ID}/{MACHINE_NAME}",method=RequestMethod.GET)
	public @ResponseBody Object setMechineName(@PathVariable String MACHINE_ID, @PathVariable String MACHINE_NAME) {
		RelaySwitch relaySwitch = new RelaySwitch();
		relaySwitch.setCode("300");
		try {
			MACHINE_NAME = new String(MACHINE_NAME.getBytes("iso-8859-1"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		if(MACHINE_ID != null && !MACHINE_ID.equals("") && MACHINE_NAME != null && !"".equals(MACHINE_NAME)) {
			Map<String, String> paramMap = new LinkedHashMap<String, String>();
			paramMap.put("TITLE", MACHINE_NAME);
			paramMap.put("ID", MACHINE_ID);
			int result = infoStr.updateInfo(paramMap, "sys.business.updateMachineInfoByMachineId");
			if(result == 1) {
				relaySwitch.setCode("200");
			}
		}
		return relaySwitch;
	}
}
