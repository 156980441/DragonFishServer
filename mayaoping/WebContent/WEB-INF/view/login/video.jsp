<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="../inc/initTaglibs.jsp"%>

<div class="pageContent">
<div class="formBar">
	<ul class="toolBar">
			<li>
				<a class="buttonActive" onclick="openOnRight('/evs/manage/viewAddResumeInfo?SEQ=0&evsType=${evsType}','viewEvsResumeList_${evsType}_unit');" href="#">
					<span>添加</span>
				</a>
			</li>
			<li>
				<a class="buttonActive" onclick="validateDeleteEvsResumeInfoCallback('viewAddEvsResumeInfo_${evsType}',navTabAjaxDone)" href="#"><span>删除</span></a>
			</li>
			<li>
				<a class="buttonActive" onclick="validateAddEvsResumeInfoCallback('viewAddEvsResumeInfo_${evsType}',navTabAjaxDone)" href="#"><span>保存</span></a>
			</li>
	</ul>
</div>
	<div id="viewEvsResumeList_${evsType}_left" sysLong="printDiv" style="float:left; display:block; overflow:auto;width:430px; height:auto; border:solid 1px #CCC; line-height:21px; background:#fff">
		<div class="user_table" style="font:bold 12px/20px arial,sans-serif;">Total:${resumeSize}</div>
		<table class="list" width="430px;">
			<thead>
				<tr>
					<th width="30px;">No.</th>
					<th width="100px">评价名</th>
					<th width="100px">变更时间</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${videoList}" var="item" varStatus="i">
					<tr>
						<td class='td_center'>${i.count}</td>
						<td>${item.localName}</td>
						<td></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</div>