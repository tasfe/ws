<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="../../includes/importer.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %> 
<!doctype html>
<html>
<head>
    <ht:head/>
    
     <script type="text/javascript">
    		$(document).ready(function(){
    		   $("#itemName").click(function(){
		    		   selectSinggleGood($("#itemId").val(),function(itemId,itemName){
		    					$id('itemId').value =itemId;
		    					$id('itemName').value =itemName;
		    					
		    					$id('itemName').focus();//?
    							$id('itemName').blur();//?
		    			});
	    		});
    			
    			$("#addItemRouter").click(function(){
    				$("#fm").submit();
    			});
    			//获取支付类型
    			var paytype = "${router.payType}";
    			//默认选中
    			if(paytype == null || paytype == "") {
    				$("input[name='payType']").get(0).checked = true;
    			}
    			
    			$("#radio-1").click(function(){
    				 $("#single").show();
    				 $("#bacth").hide();
    			});
    		    $("#radio-2").click(function(){
    				$("#single").hide();
    				$("#bacth").show();
    			});
    			$("#marketMsg").keyup(function(){
    			  if ($("#marketMsg").val().length > 130) {
    			    alert("商品营销短信，不得超过130字！");
    			  	$("#marketMsg").val($("#marketMsg").val().substring(0,130))
    			  }
				});
				$("#validate").click(function(){
				      var spCode = "${smsbuyItemRouter.spCode }";
				      var command = $("#command").val();
				      if (command == "") {
				      	alert("请输入指令内容！");
				      	return;
				      }
				      var id = "${router.id }";
				      var url = "validateRouter?spCode="+spCode+"&command="+command+"&id="+id;
					  $.post(url,function(data){
					  	  alert(data);
					  });
					});
				
    			
    		});	
    		 function sub() {
		    	$("#filepath").val($("#uploaditemfile").val());
		    }
    </script>
    
</head>
<body>
<div id="content">
<!-- forms -->
<div class="box">
    <!-- box / title -->
    <div class="title">
        <h5>新增商品指令</h5>
    </div>
    <!-- end box / title -->
    <form:form method="post" id="fm" action="addItemRouter" commandName="smsbuyItemRouter"  htmlEscape="true" acceptCharset="utf-8" cssClass="required-validate" onsubmit="return sub();" enctype="multipart/form-data">
        <div class="form">
        <form:hidden path="actId"/>
        <input type="hidden" name="id" id="id" value="${router.id }">
        <input type="hidden" name="spCode" value="${smsbuyItemRouter.spCode }">
            <div class="fields">
	             <div class="field">
	                    <div class="label label-radio">
	                        <label>商品上传方式:</label>
	                    </div>
	                    <div class="radios">
	                        <div class="radio">
	                            <input type="radio" id="radio-1" name="uploadtype" checked="checked"  value="1"/><label for="radio-1">单个商品</label>
	                            <input type="radio" id="radio-2" name="uploadtype" value="2"/><label for="radio-2">批量商品</label>
	                        </div>
	                    </div>
	               </div>
            	   <div class="field" id="single">
           	   			<div class="field">
							<div class="label">
								<label for="itemName"  class="req">选择商品：</label>
							</div>
							<div class="input">
								<form:input path="itemName" cssClass="small required"
									maxlength="100" readonly="true" value="${router.itemName }" />
								<form:hidden path="itemId" value="${router.itemId }" />
								<span class="error" id="advice-required-itemName"
									style="display:none"></span>
							</div>
						</div>
						<div class="field">
							<div class="label">
								<label for="rootSpcode">特服号：</label>
							</div>
							<div style="margin-left: 120px">
								${smsbuyItemRouter.spCode }
							</div>
						</div>
						<div class="field">
							<div class="label">
								<label for="command"  class="req">指令内容：</label>
							</div>
							<div class="input">
								<form:input path="command" id="command" cssClass="small required" maxlength="50"   style="width:80px" value="${router.command }"/>
	                    		&nbsp;&nbsp;<button type="button" id="validate" class="common_btn">检查是否重复</button>
							</div>
						</div>
						<div class="field">
							<div class="label label-radio">
								<label>商品支付方式：</label>
							</div>
							<div class="radios">
								<div class="radio">
								<c:forEach items="${payTypeMap}" var="item" varStatus="index">
	                      	 			<input type="radio" id="radio1-${index.count }" name="payType" <c:if test="${item.key == router.payType }">checked="checked"</c:if> value="${item.key}"/>
	                      	 			<label for="radio1-${index.count }">${item.value }</label>
                       			</c:forEach>
                       			</div>
							</div>
						</div>
						<div class="field">
							<div class="label">
								<label for="itemPrice">商品购买价格(元)：</label>
							</div>
							<div class="input">
							<form:input path="itemPrice" cssClass="validate-number" maxlength="9" style="width:55px" value="${router.itemPrice/100 }" onkeyup="if(this.value==this.value2)return;if(this.value.search(/^\d*(?:\.\d{0,4})?$/)==-1)this.value=(this.value2)?this.value2:'';else this.value2=this.value;" />
							</div>
						</div>
						<!--  
						<div class="field">
							<div class="label">
								<label for="marketMsg" >商品营销短信：</label>
							</div>
							<div class="input">
							<textarea id="marketMsg" name="marketMsg" rows="8" cols="50">${router.marketMsg }</textarea>
	                    		<font color="red">商品营销短信，不得超过130字</font>
					            <span class="error" id="advice-validate-one-required-marketMsg" style="display:none"></span>
							</div>
						</div>
						-->
						
						<div class="buttons">
							<button type="button" class="common_btn" id="addItemRouter">
							<c:if test="${router.id > 0 }" var="flg">修改指令</c:if>
							<c:if test="${!flg }">增加指令</c:if>
							</button>
							<button type="button" class="common_btn" id="addItemRouter" onclick="javascript:history.go(-1);">返回</button>
	              		</div>
					</div>
					 <div class="field" id="bacth" style="display: none">
	                    <div class="label">
	                        <label for="uploaditemfile">批量上传商品:</label>
	                    </div>
	                    <div class="input input-file">
	                        <input type="file" id="uploaditemfile" name="uploaditemfile" size="40" class="required validate-file-xls-xlsx" />
	                        <span class="tip">excel格式</span>
	                        <span class="error" id="advice-validate-file-uploaditemfile" style="display:none"></span>
	                        <span class="error" id="advice-required-uploaditemfile" style="display:none"></span>
	                        <input type="hidden" id="filepath" name="filepath">
	                        <span class="tip"><a href="${ctx }/static/resources/smsModel.xlsx">下载模版</a></span>
	                    </div>
	                    <div class="buttons">
	                    	<button type="submit" class="common_btn">提交</button>
	              		</div>
	                </div>
   			</div>
        </div>
    </form:form>
</div>
<!-- end forms -->
</div>
</body>
</html>