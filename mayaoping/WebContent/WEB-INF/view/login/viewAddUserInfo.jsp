<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="../inc/initTaglibs.jsp"%>
<script type="text/javascript">
function validateCallbackUserInfo(form) {
	
	var $form = $(form);
	
	if (!$form.valid()) {
		return false;
	}
	
	$.ajax({
		type: form.method || 'POST',
		url:$form.attr("action"),
		data:$form.serializeArray(),
		dataType:"json",
		cache: false,
		success: function(json){
			DWZ.ajaxDone(json);
			navTabSearch($("#viewUserInfoForm"));
			$.pdialog.closeCurrent();
		},
		error: DWZ.ajaxError
	});
	
	return false;
}
</script>
<div class="pageContent">
	<form method="post" action="/login/addUserInfo" class="pageForm required-validate"  onsubmit="return validateCallbackUserInfo(this);">
		<div class="pageFormContent" layoutH="56">
			<p>
				<label>用户名：</label>
				<input name="SEQ" type="hidden" value="${userInfo.SEQ}"/>
				<c:if test="${empty userInfo.SEQ}">
					<input name="USER_NAME" type="text" size="30" value="${userInfo.USER_NAME}" class="required"/>
				</c:if>
				<c:if test="${ not empty userInfo.SEQ}">
					${userInfo.USER_NAME}
				</c:if>
			</p>
			<p>
				<label>密码：</label>
				<input name="PASSWORD" type="text" class="required" value="${userInfo.PASSWORD}" size="30"/>
			</p>
			<p>
				<label>是否管理员：</label>
				<select name="IS_ADMIN" class="required combox">
					<option value="1" <c:if test="${userInfo.IS_ADMIN eq 1}">selected</c:if>>是</option>
					<option value="0" <c:if test="${userInfo.IS_ADMIN ne 1}">selected</c:if>>否</option>
				</select>
			</p>
			<p>
				<label>状态：</label>
				<select name="ACTIVITY" class="required combox">
					<option value="1" <c:if test="${userInfo.ACTIVITY eq 1}">selected</c:if>>启用</option>
					<option value="0" <c:if test="${userInfo.ACTIVITY eq 0}">selected</c:if>>禁用</option>
				</select>
			</p>
		</div>
		<div class="formBar">
			<ul>
				<!--<li><a class="buttonActive" href="javascript:;"><span>保存</span></a></li>-->
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">保存</button></div></div></li>
				<li>
					<div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div>
				</li>
			</ul>
		</div>
	</form>
</div>
