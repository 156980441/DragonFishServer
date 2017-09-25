<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="../inc/initTaglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>鱼管家后台管理系统</title>

<!-- 国产jQuery UI框架 - DWZ富客户端框架(J-UI.com) -->
<link href="/resources/themes/default/style.css" rel="stylesheet"
	type="text/css" media="screen" />
<link href="/resources/themes/css/core.css" rel="stylesheet"
	type="text/css" media="screen" />
<link href="/resources/themes/css/print.css" rel="stylesheet"
	type="text/css" media="print" />
<link href="/resources/uploadify/css/uploadify.css" rel="stylesheet"
	type="text/css" media="screen" />

<!--[if IE]>
<link href="/resources/themes/css/ieHack.css" rel="stylesheet" type="text/css" media="screen"/>
<![endif]-->

<!--[if lt IE 9]>
<script src="/resources/js/speedup.js" type="text/javascript"></script>
<script src="/resources/js/jquery-1.11.3.min.js" type="text/javascript"></script>
<![endif]-->
<!--[if gte IE 9]><!-->
<script src="/resources/js/jquery-2.1.4.min.js" type="text/javascript"></script>
<!--<![endif]-->

<script src="/resources/js/jquery.cookie.js" type="text/javascript"></script>
<script src="/resources/js/jquery.validate.js" type="text/javascript"></script>
<script src="/resources/js/jquery.bgiframe.js" type="text/javascript"></script>
<script src="/resources/xheditor/xheditor-1.2.2.min.js"
	type="text/javascript"></script>
<script src="/resources/xheditor/xheditor_lang/zh-cn.js"
	type="text/javascript"></script>
<script src="/resources/uploadify/scripts/jquery.uploadify.js"
	type="text/javascript"></script>

<!-- svg图表  supports Firefox 3.0+, Safari 3.0+, Chrome 5.0+, Opera 9.5+ and Internet Explorer 6.0+ -->
<script type="text/javascript" src="/resources/chart/raphael.js"></script>
<script type="text/javascript" src="/resources/chart/g.raphael.js"></script>
<script type="text/javascript" src="/resources/chart/g.bar.js"></script>
<script type="text/javascript" src="/resources/chart/g.line.js"></script>
<script type="text/javascript" src="/resources/chart/g.pie.js"></script>
<script type="text/javascript" src="/resources/chart/g.dot.js"></script>

<script src="/resources/js/dwz.core.js" type="text/javascript"></script>
<script src="/resources/js/dwz.util.date.js" type="text/javascript"></script>
<script src="/resources/js/dwz.validate.method.js"
	type="text/javascript"></script>
<script src="/resources/js/dwz.barDrag.js" type="text/javascript"></script>
<script src="/resources/js/dwz.drag.js" type="text/javascript"></script>
<script src="/resources/js/dwz.tree.js" type="text/javascript"></script>
<script src="/resources/js/dwz.accordion.js" type="text/javascript"></script>
<script src="/resources/js/dwz.ui.js" type="text/javascript"></script>
<script src="/resources/js/dwz.theme.js" type="text/javascript"></script>
<script src="/resources/js/dwz.switchEnv.js" type="text/javascript"></script>
<script src="/resources/js/dwz.alertMsg.js" type="text/javascript"></script>
<script src="/resources/js/dwz.contextmenu.js" type="text/javascript"></script>
<script src="/resources/js/dwz.navTab.js" type="text/javascript"></script>
<script src="/resources/js/dwz.tab.js" type="text/javascript"></script>
<script src="/resources/js/dwz.resize.js" type="text/javascript"></script>
<script src="/resources/js/dwz.dialog.js" type="text/javascript"></script>
<script src="/resources/js/dwz.dialogDrag.js" type="text/javascript"></script>
<script src="/resources/js/dwz.sortDrag.js" type="text/javascript"></script>
<script src="/resources/js/dwz.cssTable.js" type="text/javascript"></script>
<script src="/resources/js/dwz.stable.js" type="text/javascript"></script>
<script src="/resources/js/dwz.taskBar.js" type="text/javascript"></script>
<script src="/resources/js/dwz.ajax.js" type="text/javascript"></script>
<script src="/resources/js/dwz.pagination.js" type="text/javascript"></script>
<script src="/resources/js/dwz.database.js" type="text/javascript"></script>
<script src="/resources/js/dwz.datepicker.js" type="text/javascript"></script>
<script src="/resources/js/dwz.effects.js" type="text/javascript"></script>
<script src="/resources/js/dwz.panel.js" type="text/javascript"></script>
<script src="/resources/js/dwz.checkbox.js" type="text/javascript"></script>
<script src="/resources/js/dwz.history.js" type="text/javascript"></script>
<script src="/resources/js/dwz.combox.js" type="text/javascript"></script>
<script src="/resources/js/dwz.file.js" type="text/javascript"></script>
<script src="/resources/js/dwz.print.js" type="text/javascript"></script>

<!-- 可以用dwz.min.js替换前面全部dwz.*.js (注意：替换是下面dwz.regional.zh.js还需要引入)
<script src="bin/dwz.min.js" type="text/javascript"></script>
-->

<script src="/resources/js/dwz.regional.zh.js" type="text/javascript"></script>

<script type="text/javascript">
	$(function() {
		DWZ.init("/resources/dwz.frag.xml", {
			loginUrl : "login_dialog.html",
			loginTitle : "登录", // 弹出登录对话框
			//		loginUrl:"login.html",	// 跳到登录页面
			statusCode : {
				ok : 200,
				error : 300,
				timeout : 301
			}, //【可选】
			pageInfo : {
				pageNum : "pageNum",
				numPerPage : "numPerPage",
				orderField : "orderField",
				orderDirection : "orderDirection"
			}, //【可选】
			debug : false, // 调试模式 【true|false】
			callback : function() {
				initEnv();
			}
		});
	});

	/**
	 *实时显示系统时间
	 */
	function getLangDate() {
		var dateObj = new Date(); //表示当前系统时间的Date对象 
		var year = dateObj.getFullYear(); //当前系统时间的完整年份值
		var month = dateObj.getMonth() + 1; //当前系统时间的月份值 
		var date = dateObj.getDate(); //当前系统时间的月份中的日
		var day = dateObj.getDay(); //当前系统时间中的星期值
		var weeks = [ "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" ];
		var week = weeks[day]; //根据星期值，从数组中获取对应的星期字符串 
		var hour = dateObj.getHours(); //当前系统时间的小时值 
		var minute = dateObj.getMinutes(); //当前系统时间的分钟值
		var second = dateObj.getSeconds(); //当前系统时间的秒钟值
		//如果月、日、小时、分、秒的值小于10，在前面补0
		if (month < 10) {
			month = "0" + month;
		}
		if (date < 10) {
			date = "0" + date;
		}
		if (hour < 10) {
			hour = "0" + hour;
		}
		if (minute < 10) {
			minute = "0" + minute;
		}
		if (second < 10) {
			second = "0" + second;
		}
		var newDate = year + "年" + month + "月" + date + "日 " + week + " "
				+ hour + ":" + minute + ":" + second;
		document.getElementById("dateStr").innerHTML = newDate;
		setTimeout("getLangDate()", 1000);//每隔1秒重新调用一次该函数 
	}
</script>
</head>

<body scroll="no" onload="getLangDate()">
	<div id="layout">
		<div id="header">
			<div class="headerNav">
				<div id="dateStr" class="word_grey"
					style="color: #FFFFFF; font-size: 18px; margin-top: 10px"></div>
				<ul class="nav">
					<li><a href="/login/out">退出</a></li>
				</ul>
			</div>
		</div>
		<div id="leftside">
			<div id="sidebar_s">
				<div class="collapse">
					<div class="toggleCollapse">
						<div></div>
					</div>
				</div>
			</div>
			<div id="sidebar">
				<div class="toggleCollapse">
					<h2>主菜单</h2>
					<div>收缩</div>
				</div>
				<div class="accordion" fillSpace="sidebar">
					<div class="accordionHeader">
						<h2>
							<span>Folder</span>后台管理
						</h2>
					</div>
					<div class="accordionContent">
						<ul class="tree treeFolder">
							<li><a href="/login/viewUserList" target="navTab"
								rel="sys001">用户管理</a></li>
							<li><a href="/business/viewAdvertiseList" target="navTab"
								rel="sys002">广告管理</a></li>
							<li><a href="/business/viewMachineList" target="navTab"
								rel="sys003">设备管理</a></li>
							<li><a href="/business/viewStartPageList" target="navTab"
								rel="sys004">启动页面</a></li>
						</ul>
					</div>
				</div>
			</div>
		</div>
		<div id="container">
			<div id="navTab" class="tabsPage">
				<div class="tabsPageHeader">
					<div class="tabsPageHeaderContent">
						<!-- 显示左右控制时添加 class="tabsPageHeaderMargin" -->
						<ul class="navTab-tab">
							<li tabid="main" class="main"><a href="javascript:;"><span><span
										class="home_icon">我的主页</span></span></a></li>
						</ul>
					</div>
					<div class="tabsLeft">left</div>
					<!-- 禁用只需要添加一个样式 class="tabsLeft tabsLeftDisabled" -->
					<div class="tabsRight">right</div>
					<!-- 禁用只需要添加一个样式 class="tabsRight tabsRightDisabled" -->
					<div class="tabsMore">more</div>
				</div>
				<ul class="tabsMoreList">
					<li><a href="javascript:;">我的主页</a></li>
				</ul>
				<div class="navTab-panel tabsPageContent layoutBox">
					<div class="page unitBox">
						<div class="pageFormContent" layoutH="80"
							style="margin-right: 230px">

							<pre style="margin: 5px; line-height: 1.4em">
APP功能需求：
分为两部分完成整体功能，设备管理和广告管理

设备管理：
1、注册用户可添加多台设备，添加设备根据ID号来完成验证，设备ID在后台自动生成后，打印出来贴到每台设备上；
2、后台可查询设备的相关数据，主要查询用户信息和设备区域，为分区域广告
广告管理：
1、启动画面，可以任意添加多个启动画面图片，分区域发布到手机端
2、广告位图是3副图片自动切换循环显示，后台可以增加多组发布到不同区域
3、链接广告，和上述基本一致，多一项可以添加链接，没有添加时点击无效，添加后进去。

其他功能：
1、软件要能自动更新，方便后期维护；
2、注册需要验证，
3、如果服务器长时间接收不到数据，即收不到终端盒数据，有可能是终端关闭电源或者网络中断，该情况软件可以报警，有提示音等方式

接口说明：

用户登陆：http://115.29.147.177:8099/interface/login {"USER_NAME":"admin5","PASSWORD":"123456"}   post
注册用户：http://115.29.147.177:8099/interface/addUserInfo {"USER_NAME":"admin5","PASSWORD":"123456"}   post
添加设备：http://115.29.147.177:8099/interface/addMachineInfo {"USER_NO":"5","MACHINE_ID":"111","MACHINE_TITLE":"设备"}   post

位图广告：http://115.29.147.177:8099/interface/getAdvertiseList/1   get
链接广告：http://115.29.147.177:8099/interface/getAdvertiseList/0   get
设备列表：http://115.29.147.177:8099/interface/getMachineList/XXX   get
设备信息：http://115.29.147.177:8099/interface/getMachineInfo/XXX   get
删除设备：http://139.224.223.73:8099/interface/deleteMachineInfo/USER_NO/MACHINE_ID get
设置继电器状态:http://139.224.223.73:8099/interface/setRelaySwitch/MACHINE_ID/status(0,1) get
设置机器名称:http://139.224.223.73:8099/interface/setMechineName/{MACHINE_ID}/{MACHINE_NAME} get

1.连接方式
仪器以TCP的client连接服务器，服务器端口8647。

2.通信协议
（1）仪器->服务器
（a）第1步，仪器连接上服务器后，先发送仪器序列号，以字符串形式，如
“ID557M64K4U6Z5K8” 

（b）第2步，仪器定时给服务器发送传感器数据，如
"#27.5,28.4,7.5,301,0!"  
‘#’起始符号, ‘!’为结束符，’,’为分隔符，均为英文字符。
27.5:为LM35采集的温度（空气温度）；
28.4:为DS18B20采集的温度（水温）；
7.5: 为pH值；
301: 为tds值；
0:   为继电器状态,0断开1闭合。

仪器运行过程中，按照一定的时间间隔重复执行（b），发送数据。

（2）服务器->仪器
（a）设置继电器状态
&R,0!
‘&’起始符号, ‘!’为结束符，’,’为分隔符，均为英文字符；
0:为继电器状态,0断开1闭合。
</pre>
						</div>
					</div>
				</div>
			</div>
		</div>

	</div>

	<div id="footer">Copyright &copy; 2016</div>

</body>
</html>
