<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="../inc/initTaglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<form id="pagerForm" method="post" action="/login/viewUserList">  
    <input type="hidden" name="status" value="${page.status}">  
    <input type="hidden" name="keywords" value="${page.keywords}" />  
    <input type="hidden" name="pageNum" value="${page.pageNum}" />  
    <input type="hidden" name="numPerPage" value="${page.numPerPage}" />  
    <input type="hidden" name="orderField" value="${page.orderField}" />  
    <input type="hidden" name="orderDirection" value="${page.orderDirection}" />  
</form>
<div class="pageHeader">
	<form id="viewUserInfoForm" onsubmit="return navTabSearch(this);" action="/login/viewUserList" method="post">
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td>
					关键字：<input type="text" name="seach_KEY" value="${KEY }"/>
				</td>
			</tr>
		</table>
		<div class="subBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">检索</button></div></div></li>
			</ul>
		</div>
	</div>
	</form>
</div>
<div class="pageContent">
	<div class="panelBar">
		<ul class="toolBar">
			<li><a class="add" href="/login/viewAddUserInfo" target="dialog" rel="viewAddUserInfo" task="true" width="400" height="300"><span>添加</span></a></li>
			<li><a class="edit" href="/login/viewAddUserInfo?SEQ={SEQ}" target="dialog" rel="viewAddUserInfo" task="true" width="400" height="300"><span>修改</span></a></li>
			<li><a class="delete" href="/login/deleteUserInfo?SEQ={SEQ}" target="ajaxTodo" rel="deleteUserInfo"><span>删除</span></a></li>
		</ul>
	</div>
	<table class="table" width="100%" layoutH="138">
		<thead>
			<tr>
				<th width="80">No</th>
				<th width="100" orderField="USER_NAME" <c:if test='${page.orderField == "USER_NAME"}'>class="${page.orderDirection}"</c:if>>用户名</th>
				<th width="80" orderField="PASSWORD" <c:if test='${page.orderField == "PASSWORD"}'>class="${page.orderDirection}"</c:if>>密码</th>
				<th width="80" orderField="IS_ADMIN" <c:if test='${page.orderField == "IS_ADMIN"}'>class="${page.orderDirection}"</c:if>>是否管理员</th>
				<th width="80" orderField="ACTIVITY" <c:if test='${page.orderField == "ACTIVITY"}'>class="${page.orderDirection}"</c:if>>状态</th>
				<th width="80" orderField="CREATE_DATE" <c:if test='${page.orderField == "CREATE_DATE"}'>class="${page.orderDirection}"</c:if>>建档日期</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${videoList}" var="item" varStatus="i">
				<tr target="SEQ" rel="${item.userNo }">
					<td>${i.count + (page.pageNum - 1) * page.numPerPage}</td>
					<td>${item.username }</td>
					<td>${item.password }</td>
					<td>
						<c:if test="${item.activity eq 1 }">启用</c:if>
						<c:if test="${item.activity ne 1 }">禁用</c:if>
					</td>
					<td>
						<c:if test="${item.isAdmin eq 1 }">是</c:if>
						<c:if test="${item.isAdmin ne 1 }">否</c:if>
					</td>
					<td>${item.createDate }</td>
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
        <div class="pagination" targetType="navTab" totalCount="${page.totalCount}" numPerPage="${page.numPerPage}" pageNumShown="10" currentPage="${page.pageNum}"></div>  
	</div>
</div>