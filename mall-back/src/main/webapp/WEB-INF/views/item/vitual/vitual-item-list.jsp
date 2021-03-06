<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="../../includes/importer.jsp"%>
<!doctype html>
<html>
<head>
<ht:head />
<script type="text/javascript">
$().ready(function() {	
	$(".view").click(function() {
			var id = $(this).attr("id");
			showDialog("商品内容详细信息", "${ctx}/item/vitual/view?id="+id,function(doc){
			},{"Width":768,"Height":600,"ShowButtonRow" : true});			
	});
	$("#all").click(function() {
			if($("#all").attr("checked")){
				$("input[type=checkbox]").attr("checked","checked");
			}else{
				$("input[type=checkbox]").removeAttr("checked");
			}
			
	});
	selectRegionCallBack("#areaName",function(id,txt){ //选择归属地市
		$("#areaCode").val(id);
		$("#areaName").val(txt);
	},'${pid}',{maxItems:1,flag:1});
});
//关联协议
function chackSettlesStatus(el){
	if(el.attr("status")!='3'){
		simpleAlert("审核通过，才能关联协议！");
		return false;
	}
	return true;
}
//上架
function chackGroundingStatus(){
	if($("input[name='itemId']:checked").length == 0 ){
		 alert("请选择需要上架的商品");
		 return false;
	 }
	 var str=""; 
	 $("input[name='itemId']:checked").each(function(){  
	 	str+=$(this).val()+",";  
	 })
	 dealInfo("确认上架","goodsOnLine/online?itemId="+str);
}
//下架
function chackUndercarriageStatus(el){
	if($("input[name='itemId']:checked").length == 0 ){
		 alert("请选择需要下架的商品");
		 return false;
	 }
	 var str=""; 
	 $("input[name='itemId']:checked").each(function(){  
	 	str+=$(this).val()+",";  
	 })
	 dealInfo("确认下架","goodsOnLine/offline?itemId="+str);
}

//删除
function checkDel(el){
	if($("input[name='itemId']:checked").length == 0 ){
		 alert("请选择需要删除的商品");
		 return false;
	 }
	 var str=""; 
	 $("input[name='itemId']:checked").each(function(){  
	 	str+=$(this).val()+",";  
	 })
	 dealInfo("确认删除","delete?itemId="+str);
}

//同步
function syncGy(el){
	if($("input[name='itemId']:checked").length == 0 ){
		 alert("请选择需要同步的商品");
		 return false;
	 }
	 var str=""; 
	 $("input[name='itemId']:checked").each(function(){  
	 	str+=$(this).val()+",";  
	 })
	 dealInfo("确认同步","getSettles/batchSyncGy?itemId="+str);
}
</script>
</head>
<body>
	<br />
	<div id="search-menu">
		<ul>
			<ht:menu-btn type="search" />
			<ct:display model="vitual_item_list" btn="add_btn">
				<ht:menu-btn text="添加商品" url="/item/vitual/preAdd.do" />
			</ct:display>
		</ul>
		<br style="clear: left" />
	</div>
	<div class="queryContainer">
		<form name="queryForm" id="queryForm" action="?" method="get">
		<input type="hidden" name="iseckillFlag" value="${iseckillFlag}" />
			<table>
				<tr>
					<td>商品名称：</td>
					<td><input type="text" name="name" value="${param.name}"
						class="txt" style="width:120px" /></td>
						
			

					<td width="70">创建时间：</td>
					<td width="300"><input type="text" id="inputStartTime"
						name="inputStartTime" value="${param.inputStartTime}"
						class="txt Wdate" readOnly
						onclick="WdatePicker({vel:'saleStartTime',realDateFmt:'yyyyMMdd',maxDate:'#F{$dp.$D(\'inputEndTime\')||\'2020-10-01\'}'})" />
						<input type="hidden" name="saleStartTime" id="saleStartTime"
						value="${param.startTime}" /> 至 <input type="text"
						id="inputEndTime" name="inputEndTime"
						value="${param.inputEndTime}" class="txt Wdate" readOnly
						onclick="WdatePicker({vel:'saleStopTime',realDateFmt:'yyyyMMdd',minDate:'#F{$dp.$D(\'inputStartTime\')}',maxDate:'2020-10-01'})" />
						<input type="hidden" name="saleStopTime" id="saleStopTime"
						value="${param.endTime}" />
					</td>
						<td>商品分类：</td>
					<td><input type="text" name="typeName" value="${param.typeName}"
						class="txt" style="width:120px" /></td>
				</tr>
				<tr>
				
							
						<td>商户名称：</td>
					<td><input type="text" name="storeName" value="${param.storeName}"
						class="txt" style="width:120px" /></td>


	
						<td>商户编号：</td>
					<td><input type="text" name="storeId" title="商户编号" value="${param.storeId}"
						class="txt validate-number" style="width:120px" /></td>
				
				
					<td>上下架状态：</td>
						<td width="300"><select name="isValid">
							<option value="">--请选择--</option>
							<c:forEach items="${isValidMap}" var="item" varStatus="index">
								<option value="${item.key }"
									<c:if test="${item.key == param.isValid}">selected="selected"</c:if>>${item.value}</option>
							</c:forEach>
					</select>
					</td>
				</tr>
				<tr>
					<td>商品编号：</td>
					<td width="200"><input id="ordernumber" title="商品编号" type="text" name="id" value="${param.id}"	class="txt validate-number" style="width:120px" />
					</td>
					<td>状态：</td>
					<td width="200"><select name="status">
							<option value="">--请选择--</option>
							<c:forEach items="${statusMap}" var="item" varStatus="index">
								<option value="${item.key }"
									<c:if test="${item.key == param.status}">selected="selected"</c:if>>${item.value
									}</option>
							</c:forEach>
					</select>
					</td>
				</tr>
					<tr>
					<td>商品来源：</td>
					<td width="200"><select name="itemSource">
							<option value="">--请选择--</option>
							<c:forEach items="${sourceMap}" var="item" varStatus="index">
								<option value="${item.key }"
									<c:if test="${item.key == param.itemSource}">selected="selected"</c:if>>${item.value}</option>
							</c:forEach>
					</select>
					</td>
					<td>地市：</td>
					<td>
						<input id="areaName" name="saleAreaName" type="text" value="${param.saleAreaName}"class="txt" style="width:120px" readonly="readonly"/>
						<input id="areaCode" name="saleAreaCode" type="hidden" class="txt" style="width:120px" value="${param.saleAreaCode }"/>
					</td>
					<td>
					<ct:btn type="search" />
					</td>
				</tr>
			</table>
		</form>
	</div>
	<div id="abc">
	</div>

	<div class="container">
		<br />
		<h3>商品发布列表</h3>

		<div class="mainbox">

			<c:if test="${not empty pageData}">

				<table class="datalist fixwidth">
					<colgroup>
			        	<col width="80"></col>
			        	<col width=""></col>
			        	<col width=""></col>
			        	<col width="60"></col>
			        	<col width="60"></col>
			        	<col width="60"></col>
			        	<col width="60"></col>
			        	<col width="60"></col>
			        	<col width="60"></col>
			        	<col width="200"></col>
        			</colgroup>
					 <tr>
			        	<td colspan="10" align="left">
			        	<!--  
			        	<ht:table-action-btn text="关联协议" url="/item/item/getSettles" open="true" onAction="chackSettlesStatus"/>
			        	<ht:table-action-btn text="管理图片" url="/item/item/img" open="true"/>
			        	-->
			        	<ct:display model="vitual_item_list" btn="up_line_btn">
			        		<ht:table-action-btn text="上架" url="/item/vitual/goodsOnLine/online" onAction="chackGroundingStatus"/>
			        	</ct:display>
			        	<ct:display model="vitual_item_list" btn="down_line_btn">
			        		<ht:table-action-btn text="下架" url="/item/vitual/goodsOnLine/offline"  onAction="chackUndercarriageStatus"/>
			        	</ct:display>
			        	<ct:display model="vitual_item_list" btn="del_btn">
			        		<ht:table-action-btn text="删除" url="/item/vitual/delete"  onAction="checkDel"/>
			        	</ct:display>
			        	</td>
        			</tr>
					<tr>
						<th nowrap="nowrap" width="80"><input type="checkbox"  class="checkall" />编号</th>
						<th nowrap="nowrap" width="80">商户</th>
						<th nowrap="nowrap" width="80">商品名称</th>
						<th nowrap="nowrap" width="60">商品类型</th>
						<th nowrap="nowrap" width="60">商品分类</th>
						<%--<th nowrap="nowrap" width="60">品牌</th>--%>
						<th nowrap="nowrap" width="60">前台展示</th>
						<th nowrap="nowrap" width="60">结算状态</th>
						<th nowrap="nowrap" width="60">状态</th>
						<th nowrap="nowrap" >上架状态</th>
						<th nowrap="nowrap">操作</th>
					</tr>

					<c:forEach items="${pageData.data}" var="item">
						<tr>
							<td nowrap="nowrap" ><input type="checkbox"  name="itemId" value="${item.id }" />${item.id}</td>
							<td nowrap="nowrap" class="ellipsis">${item.storeName}</td>
							<td nowrap="nowrap" class="ellipsis"><a href="view/${item.id}">${item.name}</a></td>
							<td nowrap="nowrap" class="ellipsis">${item.itemModeName}</td>
							<td nowrap="nowrap" class="ellipsis">${item.typeName}</td>
							<%--<td nowrap="nowrap" class="ellipsis">${item.brand}</td>--%>
							<td nowrap="nowrap" class="ellipsis">${item.isViewName}</td>
							<td nowrap="nowrap" class="ellipsis">${item.syncGyFlagName}</td>
							<td nowrap="nowrap" class="ellipsis">${item.statusName}</td>
							<td nowrap="nowrap">
							<c:if test="${item.isValid == null || item.isValid == 0 }" var="flg">下架</c:if>
							<c:if test="${!flg}">上架</c:if>
							</td>
							<td nowrap="nowrap">
							 <c:if test="${item.status==3}">
							    <ct:display model="vitual_item_list" btn="preview_btn">
								 	<a href="#this" id="${item.id}" class="view">预览</a>&nbsp; &nbsp;
								</ct:display>
							 </c:if>
							 <c:if test="${item.status!=-1}">	
								<ct:display model="vitual_item_list" btn="mod_btn">
									<a href="preEdit/${item.id}">修改</a>&nbsp; &nbsp;
								</ct:display>
							    <c:if test="${item.status==0}">
									<ct:display model="vitual_item_list" btn="send_btn">
										<a href="#" onclick="dealInfo('确认送审？','sendToAudit?id=${item.id}');">送审</a> &nbsp; &nbsp;
		                        	</ct:display>
								</c:if>
								<ct:display model="vitual_item_list" btn="settle_btn">
									<a href="getSettles?itemId=${item.id}">关联协议</a>&nbsp; &nbsp;
								</ct:display>
								<ct:display model="vitual_item_list" btn="img_btn">
									<a href="img?itemId=${item.id}">管理图片</a>
								</ct:display>
							</c:if>
							</td>
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