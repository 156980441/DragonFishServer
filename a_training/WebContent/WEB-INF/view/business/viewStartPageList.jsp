<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="../inc/initTaglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<form id="pagerForm" method="post" action="/business/viewStartPageList">  
    <input type="hidden" name="status" value="${page.status}">  
    <input type="hidden" name="keywords" value="${page.keywords}" />  
    <input type="hidden" name="pageNum" value="${page.pageNum}" />  
    <input type="hidden" name="numPerPage" value="${page.numPerPage}" />  
    <input type="hidden" name="orderField" value="${page.orderField}" />  
    <input type="hidden" name="orderDirection" value="${page.orderDirection}" />  
</form>
<div class="pageHeader">
	<form id="viewStartPageListForm" onsubmit="return navTabSearch(this);" action="/business/viewStartPageList" method="post">
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
			<li><a class="add" href="/business/viewAddStartPageInfo?SEQ=19900114" target="navTab" rel="viewAddStartPageInfo"><span>添加</span></a></li>
			<li><a class="edit" href="/business/viewAddStartPageInfo?SEQ={SEQ}" target="navTab" rel="viewAddStartPageInfo" ><span>修改</span></a></li>
			<li><a class="delete" href="/login/deleteStartPageInfo?SEQ={SEQ}" target="ajaxTodo" rel="deleteStartPageInfo"><span>删除</span></a></li>
		</ul>
	</div>
	<table class="table" width="100%" layoutH="138">
		<thead>
			<tr>
				<th width="40">No</th>
				<th width="80" orderField="PIC_NAME" <c:if test='${page.orderField == "PIC_NAME"}'>class="${page.orderDirection}"</c:if>>图片名称</th>
				<th width="40" orderField="ACTIVITY" <c:if test='${page.orderField == "ACTIVITY"}'>class="${page.orderDirection}"</c:if>>是否启用</th>
				<th width="100" orderField="CREATE_DATE" <c:if test='${page.orderField == "CREATE_DATE"}'>class="${page.orderDirection}"</c:if>>创建时间</th>
				<th width="100" orderField="UPDATE_DATE" <c:if test='${page.orderField == "UPDATE_DATE"}'>class="${page.orderDirection}"</c:if>>更新时间</th>
				<th width="80" orderField="city" <c:if test='${page.orderField == "city"}'>class="${page.orderDirection}"</c:if>>所在省份</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${viewStartPageList}" var="item" varStatus="i">
				<tr target="SEQ" rel="${item.SEQ }">
					<td>${i.count + (page.pageNum - 1) * page.numPerPage}</td>
					<td>${item.PIC_NAME }</td>
					<td>
						<c:if test="${item.ACTIVITY eq 0 }">禁用</c:if>
						<c:if test="${item.ACTIVITY eq 1 }">启用</a></c:if>
					</td>
					<td>${item.CREATE_DATE }</td>
					<td>${item.UPDATE_DATE }</td>
					<td>
						<c:if test="${item.city eq 0}">北京市</c:if>
						<c:if test="${item.city eq 1}">天津市</c:if>
						<c:if test="${item.city eq 2}">河北省</c:if>
						<c:if test="${item.city eq 3}">山西省</c:if>
						<c:if test="${item.city eq 4}">内蒙古自治区</c:if>
						<c:if test="${item.city eq 5}">辽宁省</c:if>
						<c:if test="${item.city eq 6}">吉林省</c:if>
						<c:if test="${item.city eq 7}">黑龙江省</c:if>
						<c:if test="${item.city eq 8}">上海市</c:if>
						<c:if test="${item.city eq 9}">江苏省</c:if>
						<c:if test="${item.city eq 10}">浙江省</c:if>
						<c:if test="${item.city eq 11}">安徽省</c:if>
						<c:if test="${item.city eq 12}">福建省</c:if>
						<c:if test="${item.city eq 13}">江西省</c:if>
						<c:if test="${item.city eq 14}">山东省</c:if>
						<c:if test="${item.city eq 15}">河南省</c:if>
						<c:if test="${item.city eq 16}">湖北省</c:if>
						<c:if test="${item.city eq 17}">湖南省</c:if>
						<c:if test="${item.city eq 18}">广东省</c:if>
						<c:if test="${item.city eq 19}">广西壮族自治区</c:if>
						<c:if test="${item.city eq 20}">海南省</c:if>
						<c:if test="${item.city eq 21}">重庆市</c:if>
						<c:if test="${item.city eq 22}">四川省</c:if>
						<c:if test="${item.city eq 23}">贵州省</c:if>
						<c:if test="${item.city eq 24}">云南省</c:if>
						<c:if test="${item.city eq 25}">西藏自治区</c:if>
						<c:if test="${item.city eq 26}">陕西省</c:if>
						<c:if test="${item.city eq 27}">甘肃省</c:if>
						<c:if test="${item.city eq 28}">青海省</c:if>
						<c:if test="${item.city eq 29}">宁夏回族自治区</c:if>
						<c:if test="${item.city eq 30}">新疆维吾尔自治区</c:if>
						<c:if test="${item.city eq 31}">香港特别行政区</c:if>
						<c:if test="${item.city eq 32}">澳门特别行政区</c:if>
						<c:if test="${item.city eq 33}">台湾省</c:if>
					</td>
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