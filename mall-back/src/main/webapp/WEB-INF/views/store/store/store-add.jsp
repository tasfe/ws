<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="../../includes/importer.jsp"%>
<!doctype html>
<html>
<head>
<ht:head />
<script type="text/javascript">

$().ready(function() {
	
	$("#showadvanced").click(function(){
		var txt = $("#showadvanced").text();
		if(txt == "显示参数"){
			$("#showadvanced").text("隐藏参数");
			$("#advanced").show();
		}else{
			$("#showadvanced").text("显示参数");
			$("#advanced").hide();
		}
	
	  });
	
	 var selecthtml = $("#qdIds").html();
	 
	 $(':radio[name="selectChannel"]').click(function(){
		    var val= $(':radio[name="selectChannel"]:checked').val(); 
		    if(val==0) {
			    //$("#channelId").show(); 
			    $("#channelId").hide();
			    
		    }
		    if(val==1) {
			    //$("#channelId").hide(); 
			    $("#channelId").show();  
		    }
	    });
	 
    
	$("#getChannel").click(function(){
		
		selectMultiGoods($("#storeIds").val(),'3',function(ids,txts){
			$("#storeIds").val(ids.join(","));
			$("#storeName").val(txts.join("\n"));
		});
			
	});
	//selectRegion("#areaName","areaCode","areaName",'${pid}',{maxItems:1,showLevel:2});
	
	selectRegionCallBack("#areaName",function(id,txt){ //选择归属地市，自动填充 行政地市
		$("#areaCode").val(id);
		$("#areaName").val(txt);
		//自动填充归属地市
		$("#areaId").val(id);
		$("#areaIdName").val(id);
		
		$("#areaName").focus();
		$("#areaName").blur();
	},'${pid}',{maxItems:1,flag:1});
	
	//selectRegionCallBack("#areaIdName",function(id,txt){ //选择 行政地市
		//自动填充归属地市
		//$("#areaId").val(id);
		//$("#areaIdName").val(txt);
	//},'${pid}',{maxItems:1,index:100});
	
	 $("#addRow").click(function(){
			
		   // tr_id = $("#TbData>tbody>tr:last").attr("id"); 
		    tr_id = $("#TbData tr").size(); 
		    tr_id++;       
		    str= " <tr  id='tr"+tr_id+"'>"+
		      " <td align='right'>物流渠道:</td><td><select  name='qdIds' >"+selecthtml+"</sclect>"+
				"<td align='right'>运费:</td><td><input id='logisticsFee' name='logisticsFee' class='validate-number' type='text' maxlength='50' value='0' /></td>"+
				"<td align='center' width='30'><a href='javascript:void(0);' onClick=\"deltr('"+tr_id+"')\">删除</a></td>";
		    $('#TbData').append(str);  
		    
	 });
});

function deltr(id){   
    $('#tr'+id).remove();    
} 

function sub(){
	
	   if($("#advanced").css("display")=="none"){
		   $("#radio-6").attr("checked","checked");
		   $("#radio-8").attr("checked","checked");
		   $("#radio-10").attr("checked","checked");
		   $("#shortName").val("");
		   $("#bsScope").val("");
		   $("#linkFax").val("");
		   $("#servicePhone").val("");
		   $("#bsManagerName").val("");
		   $("#bsManagerPhone").val("");
		   $("#fcManagerName").val("");
		   $("#fcManagerPhone").val("");
	   }
}

</script>

</head>
<body>
	<div id="content">
		<!-- forms -->
		<div class="box">
			<!-- box / title -->
			<div  class="title">
				<h5>
				<c:if test="${store.shopClass==2 }">商户基本信息添加</c:if>
				<c:if test="${store.shopClass==3 }">渠道商基本信息添加</c:if>	
				</h5>
			</div>
			<!-- end box / title -->
			<form:form method="post" id="fm" commandName="store" onsubmit="sub();"
				htmlEscape="true" acceptCharset="utf-8" cssClass="required-validate" >
				<input type="hidden" name="sort" value="${store.sort }" />
				<input type="hidden" name="shopClass" value="${store.shopClass }" />
				<input type="hidden" name="itemEditAuditFlag" value="0" />
				<input type="hidden" name="itemPublishAuditFlag" value="0" />
				<input type="hidden" name="shopEditAuditFlag" value="0" />
				
				<div class="form">
					<div class="fields">
					
					

						<div class="field">
							<div class="label">
								<label for="name" class="req">商户名称：</label>
							</div>
							<div class="input">
								<!-- small medium large -->
								<form:input path="name" cssClass="small required" maxlength="25" />
								<span class="error" id="advice-required-userPwd"
									style="display:none"></span>
							</div>
						</div>
						
					<div class="field">
                    	<div class="label">
                       		<label for="accountType" class="req">资金来源：</label>
                    	</div>
            			 <div class="input">
                     		<form:select path="accountType" items="${accountTypeMap }"></form:select>
                		 </div>
                	</div>
						
						<div class="field">
							<div class="label">
								<label for="areaName" class="req">归属地市:</label>
							</div>
							<div class="input">
								<form:input path="areaName" cssClass="small required"
									maxlength="100" readonly="true"/>
								<form:hidden path="areaCode"  />	
								<span class="error" id="advice-required-userPwd"
									style="display:none"></span>
							</div>
						</div>
						<div class="field">
                    		<div class="label">
                        	<label for="areaId" >行政编码:</label>
                    		</div>
                    		<div class="input">
                    		<form:hidden path="areaId"  />
                    		<input type="text" name="areaIdName" id="areaIdName" class="small" maxlength="10" value="${store.areaName }" readonly="true"/>
                    		</div>
                		</div>
                		
                		<div class="field">
							<div class="label">
								<label for="address" class="req">商户地址:</label>
							</div>
							<div class="input">
								<form:input path="address" cssClass="small required"
									maxlength="50" />
								<span class="error" id="advice-required-userPwd"
									style="display:none"></span>
							</div>
						</div>
											
						<div class="field">
                    	<div class="label">
                        <label for="linkName" class="req">联系人:</label>
                    	</div>
                    	<div class="input">
                     	<form:input path="linkName" cssClass="small required validate-chinese"  maxlength="50" />
                         <span class="error" id="advice-validate-chinese-linkName" style="display:none"></span>
                    	</div>
               		</div>
						
					<div class="field">
                    	<div class="label">
                        <label for="linkPhone" class="req">联系电话:</label>
                    	</div>
                    	<div class="input">
                     	<form:input path="linkPhone" cssClass="small required " maxlength="20" />
                         <span class="error" id="advice-required-phone" style="display:none"></span>
                    	</div>
                	</div>
                	<div class="field">
                    	<div class="label">
                        <label for="linkPhone" >其它参数</label>
                    	</div>
                    	<div class="input">
                     	<a id="showadvanced" style="cursor: pointer;">显示参数</a>
                    	</div>
                	</div>
                	
                	<div id="advanced" style="display: none;">
                	
                <!-- 
                	<div class="field">
							<div class="label label-radio">
								<label>商品编辑免审:</label>
							</div>
							<div class="radios">
								<div class="radio">
									<input type="radio" id="radio-5" name="itemEditAuditFlag"
										value="1" /><label for="radio-5">免审</label> <input
										type="radio" id="radio-6" name="itemEditAuditFlag"
										checked="checked" value="0" /><label for="radio-6">需要审核</label>
								</div>
								<span class="error" id="advice-validate-one-required-radioex1"
									style="display:none"></span>
							</div>
						</div>

						<div class="field">
							<div class="label label-radio">
								<label>商品发布免审:</label>
							</div>
							<div class="radios">
								<div class="radio">
									<input type="radio" id="radio-7" name="itemPublishAuditFlag"
										value="1" /><label for="radio-7">免审</label> <input
										type="radio" id="radio-8" name="itemPublishAuditFlag"
										checked="checked" value="0" /><label for="radio-8">需要审核</label>
								</div>
								<span class="error" id="advice-validate-one-required-radioex1"
									style="display:none"></span>
							</div>
						</div>
						<c:if test="${store.shopClass ==2 }">
						<div class="field">
							<div class="label label-radio">
								<label>门店编辑免审:</label>
							</div>
							<div class="radios">
								<div class="radio">
									<input type="radio" id="radio-9" name="shopEditAuditFlag"
										value="1" /><label for="radio-9">免审</label> <input
										type="radio" id="radio-10" name="shopEditAuditFlag"
										checked="checked" value="0" /><label for="radio-10">需要审核</label>
								</div>
								<span class="error" id="advice-validate-one-required-radioex1"
									style="display:none"></span>
							</div>
						</div>
                	</c:if>
                 -->	
                		
						<div class="field">
							<div class="label">
								<label for="shortName" >商户简称:</label>
							</div>
							<div class="input">
								<form:input path="shortName" cssClass="small "  
									maxlength="10" />
								<span class="error" id="advice-shortName"
									style="display:none"></span>
							</div>
						</div>
						
						<div class="field">
							<div class="label label-textarea">
								<label for="bsScope">营业范围：</label>
							</div>
							<div class="input">
								<form:textarea path="bsScope" cols="36" rows="3"
									cssClass="max-length-60" />
							</div>
						</div>
                
                	<div class="field">
                    	<div class="label">
                        	<label for="linkFax" >联系传真:</label>
                    	</div>
                    	<div class="input">
                     	<form:input path="linkFax" cssClass="small validate-phone" maxlength="13" />
                         <span class="error" id="advice-validate-phone-linkFax" style="display:none"></span>
                    	</div>
               	 	</div>
	               <div class="field">
                    	<div class="label">
                       	 <label for="servicePhone" >客服电话:</label>
                    	</div>
                   	 	<div class="input">
                     	<form:input path="servicePhone" cssClass="small " maxlength="20" />
                         <span class="error" id="advice-validate-phone-servicePhone" style="display:none"></span>
                    	</div>
                	</div>
                	

								<div class="field">
		                    		<div class="label">
		                        <label for="bsManagerName">业务主管:</label>
		                   		 </div>
		                    		<div class="input">
		                     		<form:input path="bsManagerName" cssClass="small  validate-chinese" maxlength="25" />
		                         <span class="error" id="advice-validate-chinese-bsManagerName" style="display:none"></span>
		                   		 </div>
		                		</div>
		                		
		                		<div class="field">
				                    <div class="label">
				                        <label for="bsManagerPhone" >业务主管电话:</label>
				                    </div>
				                    <div class="input">
				                     <form:input path="bsManagerPhone" cssClass="small  validate-mobile-phone" maxlength="13" />
				                         <span class="error" id="advice-validate-mobile-phone-bsManagerPhone" style="display:none"></span>
				                    </div>
				                </div>

						 <div class="field">
		                    <div class="label">
		                        <label for="fcManagerName" >财务主管:</label>
		                    </div>
		                    <div class="input">
		                     <form:input path="fcManagerName" cssClass="small  validate-chinese" maxlength="50" />
		                         <span class="error" id="advice-validate-chinese-fcManagerName" style="display:none"></span>
		                    </div>
		                </div>
						
						<div class="field">
                    	<div class="label">
                        <label for="fcManagerPhone" >财务主管电话:</label>
                    </div>
                    <div class="input">
                     <form:input path="fcManagerPhone" cssClass="small  validate-mobile-phone" maxlength="13" />
                         <span class="error" id="advice-validate-mobile-phone-fcManagerPhone" style="display:none"></span>
                    </div>
                	</div>
				</div>	
				
				
						
						

						<select id="qdIds"  style="display: none;">
							      <c:forEach items="${logisticsList}" var="item">
							       <option value="${item.id }">${item.name }</option>
							       </c:forEach>
							       
					</select>
							       
                 <div class="field" style="display: none">
							<div class="label">
								<label for="logisticsFee" >物流运费选择:</label>
							</div>
							<div class="input">
							<label>
								<a href="javascript:void(0);" id="addRow">增加</a>
								</label>
							  <table width="500"   id="TbData" name="TbData"  cellpadding="0" cellspacing="0" >
							    
							  </table>
							   
								
								<span class="error" id="advice-required-userPwd" style="display:none"></span>
							</div>
				</div>
                

						<div class="buttons">
							<div class="highlight">
								<input type="submit" name="submit.highlight" value="提交" />
							</div>
							<input type="reset" name="reset" value="重置" /> 
							<input type="button" class="common_btn" onclick="history.back();" value="返回" />
						</div>
					</div>
				</div>
			</form:form>
		</div>
		<!-- end forms -->
	</div>


	<script type="text/javascript">
	<!--
		ajaxFormSubmit('#fm');
	//-->
	</script>



</body>
</html>