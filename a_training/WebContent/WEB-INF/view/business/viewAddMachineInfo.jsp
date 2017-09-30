<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage=""%>
<%@ include file="../inc/initTaglibs.jsp"%>
<div class="pageContent">
	<form method="post" action="/business/addMachineInfo" class="pageForm required-validate"  onsubmit="return validateCallback(this,dialogAjaxDoneWithForm);">
		<div class="pageFormContent" layoutH="56">
			<p>
				<label>设备名称：</label>
				<input name="SEQ" type="hidden" value="${machineInfo.SEQ}"/>
				<input name="TITLE" type="text" size="30" value="${machineInfo.TITLE}" class="required"/>
			</p>
			<p>
				<label>ID：</label>
				<input name="SEQ" type="hidden" value="${machineInfo.SEQ}"/>
				<c:if test="${empty machineInfo.SEQ}">
					<input name="ID" type="text" size="30" value="${machineInfo.ID}" class="required"/>
				</c:if>
				<c:if test="${ not empty machineInfo.SEQ}">
					${machineInfo.ID}
				</c:if>
			</p>
			<p>
				<label>温度：</label>
				${machineInfo.TEMPERATURE}
			</p>
			<p>
				<label>TDS：</label>
				${machineInfo.TDS}
			</p>
			<p>
				<label>PH：</label>
				${machineInfo.PH}
			</p>
			<p>
				<label>STATE：</label>
				<c:if test="${machineInfo.STATE eq 1}">开</c:if>
				<c:if test="${machineInfo.STATE eq 0}">关</c:if>
			</p>
			<p>
				<label>状态：</label>
				<select name="ACTIVITY" class="required combox">
					<option value="1" <c:if test="${machineInfo.ACTIVITY eq 1}">selected</c:if>>启用</option>
					<option value="0" <c:if test="${machineInfo.ACTIVITY eq 0}">selected</c:if>>禁用</option>
				</select>
			</p>
		</div>
		<div class="formBar">
			<ul>
				<!--<li><a class="buttonActive" href="javascript:;"><span>保存</span></a></li>-->
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">保存</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div></li>
			</ul>
		</div>
	</form>
</div>
