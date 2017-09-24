<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="../inc/initTaglibs.jsp"%>

<style type="text/css" media="screen">
.my-uploadify-button {
	background: none;
	border: none;
	text-shadow: none;
	border-radius: 0;
}

.uploadify:hover .my-uploadify-button {
	background: none;
	border: none;
}

.fileQueue {
	width: 400px;
	height: 150px;
	overflow: auto;
	border: 1px solid #E5E5E5;
	margin-bottom: 10px;
}
</style>

<script type="text/javascript">
	//文件上传的js方法
	function uploadifySuccess_upload(file, data, response) {
		//获取后台返回到前台的文件名，添加到隐藏域,多文件用";"号隔开
		var files = $("#fileNmae1_upload").html();
		var fileUrl = $("#fileUrl_upload").val();
		var fileName = $("#fileName_upload").val();
		var fileResult = data.split(";");
		//第一个文件
		if (files == "" || files == null) {
			files = fileResult[0];
			fileName = fileResult[0];
			fileUrl = "/resources/temp/" + fileResult[1];
			$("#picTable").html("");
			$("#fileUrl_upload").html("");
		} else {
			files += ";" + fileResult[0];
			fileName += ";" + fileResult[0];
			fileUrl += ";" + "/resources/temp/" + fileResult[1];
		}
		$("#fileNmae1_upload").html(files);
		$("#fileUrl_upload").val(fileUrl);
		$("#fileName_upload").val(fileName);
		$("#picTable")
				.html(
						$("#picTable").html()
								+ "<td><img name='orgImage' src='/resources/temp/" + fileResult[1] + "' border=1 style='width: 150px; height: 60px;'></td>");
	}

	function validateCallbackAdv(form) {

		var $form = $(form);

		if (!$form.valid()) {
			return false;
		}

		$.ajax({
			type : form.method || 'POST',
			url : $form.attr("action"),
			data : $form.serializeArray(),
			dataType : "json",
			cache : false,
			success : function(json) {
				DWZ.ajaxDone(json);
				navTab.closeCurrentTab();
				navTabSearch($("#" + json.formId));
			},
			error : DWZ.ajaxError
		});

		return false;
	}
</script>

<div class="pageContent">
	<form method="post" action="/business/addAdvertiseInfo"
		class="pageForm required-validate"
		onsubmit="return validateCallbackAdv(this);">
		<div class="pageFormContent" layoutH="56">
			<table>
				<tr>
					<td>广告标题：</td>
					<td><input name="SEQ" type="hidden"
						value="${advertiseInfo.SEQ}" /> <input name="TITLE" type="text"
						size="30" value="${advertiseInfo.TITLE}" class="required" /></td>
				</tr>
				<tr>
					<td>广告位置：</td>
					<td><input type="radio" name="LAYOUT" value="1"
						<c:if test="${advertiseInfo.LAYOUT ne 0}">checked</c:if>>位图广告
						<input type="radio" name="LAYOUT" value="0"
						<c:if test="${advertiseInfo.LAYOUT eq 0}">checked</c:if>>链接广告</td>
				</tr>
				<tr>
					<td>广告图片上传：</td>
					<td><input id="testFileInput_upload" type="file" name="file"
						uploaderOption="{
							swf:'/resources/uploadify/scripts/uploadify.swf',
						    uploader:'/business/uploadBatch',
							formData:{ajax:1},
							queueID:'fileQueue_upload',
							buttonText:'请选择',
							height:25,
							width:50,
							auto:false,
							onUploadSuccess:uploadifySuccess_upload,
							removeTimeout:1
						    }" />
						<div id="fileNmae1_upload"></div>
						<div id="fileQueue_upload" class="fileQueue"></div> <input
						type="hidden" id="fileUrl_upload" name="fileUrl"
						value="<c:if test="${ not empty advertiseInfo.PIC_URL1}">${advertiseInfo.PIC_URL1}</c:if>
						<c:if test="${ not empty advertiseInfo.PIC_URL2}">;${advertiseInfo.PIC_URL2}</c:if>
						<c:if test="${ not empty advertiseInfo.PIC_URL3}">;${advertiseInfo.PIC_URL3}</c:if>" />
						<input type="hidden" id="fileName_upload" name="fileName" value="" />

						<div class="buttonActive">
							<div class="buttonContent">
								<button type="button"
									onclick="$('#testFileInput_upload').uploadify('upload', '*');return false;">
									上传</button>
							</div>
						</div>
						<div class="buttonActive">
							<div class="buttonContent">
								<!--提交-->
								<button type="button"
									onclick="$('#testFileInput_upload').uploadify('cancel', '*');return false;">
									取消</button>
							</div>
						</div></td>
				</tr>
				<tr>
					<td>广告链接：</td>
					<td><input name="ADV_URL" type="text" size="30"
						value="${advertiseInfo.ADV_URL}" /></td>
				</tr>
				<tr>
					<td>状态：</td>
					<td><select name="ACTIVITY" class="required combox">
							<option value="1"
								<c:if test="${advertiseInfo.ACTIVITY eq 1}">selected</c:if>>启用</option>
							<option value="0"
								<c:if test="${advertiseInfo.ACTIVITY eq 0}">selected</c:if>>禁用</option>
					</select></td>
				</tr>
				<tr>
					<td>广告所在区域:</td>
					<td><select name="city" class="required combox">
							<option value="0"
								<c:if test="${advertiseInfo.city eq 0}">selected</c:if>>北京市</option>
							<option value="1"
								<c:if test="${advertiseInfo.city eq 1}">selected</c:if>>天津市</option>
							<option value="2"
								<c:if test="${advertiseInfo.city eq 2}">selected</c:if>>河北省</option>
							<option value="3"
								<c:if test="${advertiseInfo.city eq 3}">selected</c:if>>山西省</option>
							<option value="4"
								<c:if test="${advertiseInfo.city eq 4}">selected</c:if>>内蒙古自治区</option>
							<option value="5"
								<c:if test="${advertiseInfo.city eq 5}">selected</c:if>>辽宁省</option>
							<option value="6"
								<c:if test="${advertiseInfo.city eq 6}">selected</c:if>>吉林省</option>
							<option value="7"
								<c:if test="${advertiseInfo.city eq 7}">selected</c:if>>黑龙江省</option>
							<option value="8"
								<c:if test="${advertiseInfo.city eq 8}">selected</c:if>>上海市</option>
							<option value="9"
								<c:if test="${advertiseInfo.city eq 9}">selected</c:if>>江苏省</option>
							<option value="10"
								<c:if test="${advertiseInfo.city eq 10}">selected</c:if>>浙江省</option>
							<option value="11"
								<c:if test="${advertiseInfo.city eq 11}">selected</c:if>>安徽省</option>
							<option value="12"
								<c:if test="${advertiseInfo.city eq 12}">selected</c:if>>福建省</option>
							<option value="13"
								<c:if test="${advertiseInfo.city eq 13}">selected</c:if>>江西省</option>
							<option value="14"
								<c:if test="${advertiseInfo.city eq 14}">selected</c:if>>山东省</option>
							<option value="15"
								<c:if test="${advertiseInfo.city eq 15}">selected</c:if>>河南省</option>
							<option value="16"
								<c:if test="${advertiseInfo.city eq 16}">selected</c:if>>湖北省</option>
							<option value="17"
								<c:if test="${advertiseInfo.city eq 17}">selected</c:if>>湖南省</option>
							<option value="18"
								<c:if test="${advertiseInfo.city eq 18}">selected</c:if>>广东省</option>
							<option value="19"
								<c:if test="${advertiseInfo.city eq 19}">selected</c:if>>广西壮族自治区</option>
							<option value="20"
								<c:if test="${advertiseInfo.city eq 20}">selected</c:if>>海南省</option>
							<option value="21"
								<c:if test="${advertiseInfo.city eq 21}">selected</c:if>>重庆市</option>
							<option value="22"
								<c:if test="${advertiseInfo.city eq 22}">selected</c:if>>四川省</option>
							<option value="23"
								<c:if test="${advertiseInfo.city eq 23}">selected</c:if>>贵州省</option>
							<option value="24"
								<c:if test="${advertiseInfo.city eq 24}">selected</c:if>>云南省</option>
							<option value="25"
								<c:if test="${advertiseInfo.city eq 25}">selected</c:if>>西藏自治区</option>
							<option value="26"
								<c:if test="${advertiseInfo.city eq 26}">selected</c:if>>陕西省</option>
							<option value="27"
								<c:if test="${advertiseInfo.city eq 27}">selected</c:if>>甘肃省</option>
							<option value="28"
								<c:if test="${advertiseInfo.city eq 28}">selected</c:if>>青海省</option>
							<option value="29"
								<c:if test="${advertiseInfo.city eq 29}">selected</c:if>>宁夏回族自治区</option>
							<option value="30"
								<c:if test="${advertiseInfo.city eq 30}">selected</c:if>>新疆维吾尔自治区</option>
							<option value="31"
								<c:if test="${advertiseInfo.city eq 31}">selected</c:if>>香港特别行政区</option>
							<option value="32"
								<c:if test="${advertiseInfo.city eq 32}">selected</c:if>>澳门特别行政区</option>
							<option value="33"
								<c:if test="${advertiseInfo.city eq 33}">selected</c:if>>台湾省</option>
					</select></td>
				</tr>
			</table>
			<table>
				<tr id="picTable">
					<c:if test="${ not empty advertiseInfo.PIC_URL1}">
						<td><img name='orgImage' src='${advertiseInfo.PIC_URL1 }'
							border=1 style='width: 150px; height: 60px;'></td>
					</c:if>
					<c:if test="${ not empty advertiseInfo.PIC_URL2}">
						<td><img name='orgImage' src='${advertiseInfo.PIC_URL2 }'
							border=1 style='width: 150px; height: 60px;'></td>
					</c:if>
					<c:if test="${ not empty advertiseInfo.PIC_URL3}">
						<td><img name='orgImage' src='${advertiseInfo.PIC_URL3 }'
							border=1 style='width: 150px; height: 60px;'></td>
					</c:if>
				</tr>
			</table>
		</div>
		<div class="formBar">
			<ul>
				<!--<li><a class="buttonActive" href="javascript:;"><span>保存</span></a></li>-->
				<li>
					<div class="buttonActive">
						<div class="buttonContent">
							<button type="submit">保存</button>
						</div>
					</div>
				</li>
				<li>
					<div class="button">
						<div class="buttonContent">
							<button type="button" class="close">取消</button>
						</div>
					</div>
				</li>
			</ul>
		</div>
	</form>
</div>
