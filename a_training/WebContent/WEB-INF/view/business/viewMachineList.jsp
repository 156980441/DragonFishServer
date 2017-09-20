<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage=""%>
<%@ include file="../inc/initTaglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<form id="pagerForm" method="post" action="/business/viewMachineList">
	<input type="hidden" name="status" value="${page.status}"> 
	<input type="hidden" name="keywords" value="${page.keywords}" />
	<input type="hidden" name="pageNum" value="${page.pageNum}" />
	<input type="hidden" name="numPerPage" value="${page.numPerPage}" />
	<input type="hidden" name="orderField" value="${page.orderField}" />
	<input type="hidden" name="orderDirection" value="${page.orderDirection}" />
</form>

<div class="pageHeader">
	<form id="viewMachineInfoForm" onsubmit="return navTabSearch(this);" action="/business/viewMachineList" method="post">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>关键字：<input type="text" name="seach_KEY" value="${KEY }" /></td>
				</tr>
			</table>
			<div class="subBar">
				<ul>
					<li>
					<div class="buttonActive">
							<div class="buttonContent">
								<button type="submit">检索</button>
							</div>
						</div>
						</li>
				</ul>
			</div>
		</div>
	</form>
</div>

<div class="pageContent">
	<div class="panelBar">
		<ul class="toolBar">
			<li><a class="add"
				href="/business/viewAddMachineInfo?SEQ=19900114" target="dialog"
				rel="viewAddMachineInfo" task="true" width="400" height="500"><span>添加</span></a></li>
			<li><a class="edit"
				href="/business/viewAddMachineInfo?SEQ={SEQ}" target="dialog"
				rel="viewAddMachineInfo" task="true" width="400" height="500"><span>修改</span></a></li>
			<li><a class="delete" href="/login/deleteMachineInfo?SEQ={SEQ}"
				target="ajaxTodo" rel="deleteMachineInfo"><span>删除</span></a></li>
		</ul>
	</div>
	<table class="table" width="100%" layoutH="138">
		<thead>
			<tr>
				<th width="40">No</th>
				<th width="120" orderField="TITLE"
					<c:if test='${page.orderField == "TITLE"}'>class="${page.orderDirection}"</c:if>>设备名称</th>
				<th width="80" orderField="ID"
					<c:if test='${page.orderField == "ID"}'>class="${page.orderDirection}"</c:if>>设备ID</th>
				<th width="80" orderField="TEMPERATURE"
					<c:if test='${page.orderField == "TEMPERATURE"}'>class="${page.orderDirection}"</c:if>>温度</th>
				<th width="100" orderField="TDS"
					<c:if test='${page.orderField == "TDS"}'>class="${page.orderDirection}"</c:if>>TDS</th>
				<th width="150" orderField="PH"
					<c:if test='${page.orderField == "PH"}'>class="${page.orderDirection}"</c:if>>PH</th>
				<th width="80" orderField="STATE"
					<c:if test='${page.orderField == "STATE"}'>class="${page.orderDirection}"</c:if>>设备开关</th>
				<th width="120" orderField="UPDATE_DATE"
					<c:if test='${page.orderField == "UPDATE_DATE"}'>class="${page.orderDirection}"</c:if>>更新时间</th>
				<th width="80" orderField="ACTIVITY"
					<c:if test='${page.orderField == "ACTIVITY"}'>class="${page.orderDirection}"</c:if>>状态</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${viewMachineList}" var="item" varStatus="i">
				<tr target="SEQ" rel="${item.SEQ }">
					<td>${i.count + (page.pageNum - 1) * page.numPerPage}</td>
					<td>${item.TITLE }</td>
					<td>${item.ID }</td>
					<td>${item.TEMPERATURE }</td>
					<td>${item.TDS }</td>
					<td>${item.PH }</td>
					<td><c:if test="${item.STATE eq 1 }">开</c:if> <c:if test="${item.STATE ne 1 }">关</c:if></td>
					<td>${item.UPDATE_DATE }</td>
					<td><c:if test="${item.ACTIVITY eq 1 }">启用</c:if> <c:if test="${item.ACTIVITY ne 1 }">禁用</c:if></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="panelBar">
		<div class="pages">
			<span>显示</span> 
			<select class="combox" name="numPerPage" onchange="navTabPageBreak({numPerPage:this.value})">
				<option value="10">10</option>
				<option value="15">15</option>
				<option value="20">20</option>
			</select>
			<script>
				$("select[name='numPerPage']").val('${page.numPerPage}');
			</script>
			<span>条，共${page.totalCount}条</span>
		</div>
		<div class="pagination" targetType="navTab" totalCount="${page.totalCount}" numPerPage="${page.numPerPage}"	pageNumShown="10" currentPage="${page.pageNum}"></div>
	</div>
</div>