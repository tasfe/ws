<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="../../includes/importer.jsp"%>
<!doctype html>
<html>
<head>
<ht:head />

<script type="text/javascript">



</script>
</head>
<body>
	<br />
	<div class="queryContainer">
		<form name="queryForm" id="queryForm" action="?" method="get">
			<table>
				<tr>
					<td>商户名称：</td>
					<td><input type="text" name="name" value="${param.name}" class="txt" style="width:80px" />
					</td>
					<td>商户编号：</td>
					<td><input id="ordernumber" type="text" name="id" value="${param.id}" title="商户编号" class="txt validate-number" style="width:50px" />
					</td>
					<td>地市：</td>
					<td><input type="text" name="areaName" value="${param.areaName}" class="txt" style="width:50px" />
					</td>
				
					<td >&nbsp; <ct:btn type="search" /></td>
				</tr>
			</table>
		</form>
	</div>
	<div class="container">
		<br />
		<h3>商户列表</h3>
		<div class="mainbox">
			<c:if test="${not empty pageData}">
				<table class="datalist fixwidth">
					<tr>
						<th nowrap="nowrap" width="80px">选择</th>
						<th nowrap="nowrap">商户名称</th>
						<!-- <th nowrap="nowrap">是否移动签约</th>
						<th nowrap="nowrap">商户状态</th>
						<th nowrap="nowrap">是否成功同步高阳</th> -->
						<th nowrap="nowrap" width="100px">地市</th>
					</tr>

					  <c:forEach items="${pageData.data}" var="item" varStatus="index">
            			<tr>
             				<td nowrap="nowrap"><input type="radio"  id="radio-${index.count}" name="shopIdSelector" shopClass="${item.shopClass }"  shopName="${item.name}" areaName="${item.areaName}" value="${item.id}"  class="validate-one-required"/></td>
							<td nowrap="nowrap"  class="ellipsis">${item.name}</td>
							<!-- <td nowrap="nowrap">${item.sortName}</td>
							<td nowrap="nowrap">${item.statusName }</td>
							<td nowrap="nowrap">${item.syncGyFlagName }</td> -->
							<td nowrap="nowrap">${item.areaName}</td>
						</tr>
					</c:forEach>
				</table>
				<ht:page pageData="${pageData}" />

			</c:if>
			<c:if test="${empty pageData.data}">
				<div class="note">
					<p class="i">目前没有相关记录!</p>
				</div>
			</c:if>
		</div>

	</div>

</body>
</html>