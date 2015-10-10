<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="../../includes/importer.jsp"%>
<!doctype html>
<html>
<head>
    <head>
    <ht:head/>
    <script type="text/javascript">
    
    $(document).ready(function(){
    	
    	
    
    $("#itemName").click(function(){
    	var storeId=$("#storeId").val();
        	if(storeId==""){
        		alert("请选择商户！");
        		$("#storeName").focus();
        		return;
        	}
			selectGoods($("#itemIds").val(),function(ids,txts,doc,win){
					$id('itemIds').value =ids.join(",");
					$id('itemName').value =txts.join("\n");
					var totalPrice = 0;
					var minStockNum = 9999999;
					var limitStock = false;//是否限制库存
					$("#second option",doc).each(function(){
						totalPrice += parseFloat($(this).attr("shopPrice"));
						var stock = parseInt($(this).attr("stockNum"));
						if(stock != -1000 &&  stock < minStockNum){
							minStockNum = parseInt($(this).attr("stockNum"));
							limitStock = true;
						}
					});
					
					if(!limitStock){ //不限库存
						$("#stockNumName").val(999999999);
						$("#stockNumSpan").text('温馨提示：该套餐商品最大库存为999999999(不限)');
						$("#unlimitedStockNum").removeAttr("disabled");
						$("#unlimitedStockNum").attr("checked",true);
						$("#stockNum").val("-1000");
						$("#stockNumVali").val(999999999);
					}else{
						$("#stockNumName").val(minStockNum);
						$("#unlimitedStockNum").attr("checked",false);
						$("#unlimitedStockNum").attr("disabled","true");
						$("#stockNum").val(minStockNum);
						$("#stockNumSpan").text('温馨提示：该套餐商品最大库存为'+minStockNum);
						$("#stockNumVali").val(minStockNum);
					}
					$("#marketPrice").val(totalPrice.toFixed(2));
					$id('itemName').focus();
					$id('itemName').blur();
					
			},{'storeId':storeId,'itemMode':0});
			
			
		
		});
    	
    	
    	//选择商户
    	$("#storeName").click(function(){
			selectStore("storeId","storeName","shopClass");
			$("#itemName").val('');
			$("#itemIds").val('');
			$("#name").val('');
			$("#stockNumName").val('');
			$("#marketPrice").val('');
			$("#shopPrice").val('');
			$("#stockNumSpan").text('');
			
		});
    	
    	//是否需要物流触发的事件
        $(':radio[name="postFlag"]').click(function(){
    	    var val= $(':radio[name="postFlag"]:checked').val(); 
    	    if(val==0) {//选择不发码
    	    	$("#logisticsFeeType").attr("checked",'');//
    		    $("#isPost").hide();
    		    $("#postArea").val(""); 
    		    $("#postAreaCode").val("");
    		    $("#logisticsFee").val("");
    	    }
    	    if(val==1){
    		    $("#isPost").show();  
    		    $("#logisticsFeeType").attr("checked",true);//
    	    }
        });
    	//配送区域
    	selectRegion("#postArea","postAreaCode","postArea",0,{index:1});
    	
    	$("#unlimitedStockNum").click(function(){
    		if($("#unlimitedStockNum").attr("checked") == 'checked'){
    			$("#stockNumName").val(999999999);
   			}else{
    			$("#stockNumName").val('');
    		}
    		
    	});
    	
    	
    	$("#stockNumName").blur(function(){
    		
    		var stockNum = $("#stockNum").val();
    		var stockNumName = $("#stockNumName").val();
    		
    		if( stockNumName != 999999999){
    			$("#stockNum").val(stockNumName);
    		}else{
    			$("#stockNum").val(-1000);
    		}
    		
    	});
    	
	});	
  
    </script>
 
    </head>
<body>

<div id="content">
<!-- forms -->
<div class="box">
    <!-- box / title -->
    <div class="title">
        <h5>添加套餐商品</h5>
    </div>
    <!-- end box / title -->
<form:form method="post" id="fm" commandName="itemSale" htmlEscape="true" acceptCharset="utf-8" cssClass="required-validate" enctype="multipart/form-data" >
        <input type="hidden" name ="groupFlag" value="1" />
        <div class="form">
            <div class="fields">
            <div class="field">
						<div class="label">
							<label for="shopId" class="req">商户名称：</label>
						</div>
						<div class="input">
						    <form:hidden path="storeId" />
							<form:hidden path="shopClass"  />
							<form:input path="storeName" maxlength="100" readonly="true" cssClass="small required" />
							<span class="error" id="advice-required-shopId" style="display:none"></span>
						</div>
				</div>
                
                       <div class="field"  id="itemIdsDiv" >
                    <div class="label">
                        <label for="itemIds" class="req">添加组合套餐:</label>
                    </div>
                    <div class="input">&nbsp;&nbsp;
                     <form:hidden path="itemIds"  id="itemIds"/>
					<textarea id="itemName" name="itemName" readonly="readonly" rows="10" cols="30"  class="required">${itemName}</textarea>
                   <span class="error" id="advice-required-itemName" style="display:none"></span>
                    </div>
                </div>
                    
                   
                <div class="field">
                    <div class="label">
                        <label for="name" class="req">套餐名称：</label>
                    </div>
                    <div class="input"><!-- small medium large -->
                        <form:input id="name" path="name"  maxlength="100" cssClass="small required"/>
                         <span class="error" id="advice-required-name" style="display:none"></span>
                    </div>
                </div>
              
       					<span style="color:red" id="stockNumSpan"></span>
       					<input type="hidden" id="stockNumVali"/>
						<div class="field" >
							<div class="label">
								<label for="stockNum" class="req">套餐库存量:</label>
							</div>
							<div class="input">
								<input id="stockNumName" name="stockNumName"  maxlength="9" class=" validate-number less-than-equal-stockNumVali  required "/><input type="checkbox" id="unlimitedStockNum">不限
								<span style="color:red;" id=""></span>
							    <form:hidden path="stockNum" cssClass=" required validate-number"	maxlength="8"  readonly="true"/> 
								<span class="error" id="advice-required-stockNum"
									style="display:none"></span>
										<span class="error" id="advice-required-stockNumName"
									style="display:none"></span>
							</div>
						</div>
					
						<div class="field" >
							<div class="label">
								<label for="marketPrice" class="req">商城价:</label>
							</div>
							<div class="input">
								<form:input id="marketPrice" path="marketPrice" cssClass=" validate-number float-range--0-9999999.99"
									maxlength="10" readonly="true"/>
								<span class="error" id="advice-required-marketPrice"
									style="display:none"></span>
							</div>
						</div>
						<div class="field" >
							<div class="label">
								<label for="shopPrice" class="req">优惠后价格:</label>
							</div>
							<div class="input">
								<form:input id="shopPrice" path="shopPrice" cssClass=" less-than-equal-marketPrice required validate-number float-range--0-9999999.99"

									maxlength="10" />
								<span class="error" id="advice-required-shopPrice"
									style="display:none"></span>
							</div>
						</div>
						 
						 <!-- 需要发码此div显示end -->

						<!-- 不需要发码此div显示 begin -->
                        <div id="selLogistics">

						<!-- 不发码显示是否需要物流 -->
						<div class="field">
							<div class="label label-radio">
								<label class="req">物流信息:</label>
							</div>
							<div class="radios">
								<div class="radio">
									<input type="radio" id="radio-9" name="postFlag" value="0" class="validate-one-required"  checked="checked"/><label for="radio-9">用户自取</label> 
									<input type="radio" id="radio-10" name="postFlag" value="1" class="validate-one-required" /><label for="radio-10">物流配送</label>
								</div>
								<span class="error" id="advice-validate-one-required-postFlag"
									style="display:none"></span>
							</div>
						</div>
						
						<!-- 需要物流配送此div显示 begin-->
						<div id="isPost" style="display: none;">
							<div class="field" style="display:none">
								<div class="label">
									<label for="postArea" class="req">配送区域选择:</label>
								</div>
								<div class="input">
									<form:input path="postArea" cssClass="small required"
										maxlength="50" readonly="true"/><form:hidden path="postAreaCode"/>
										
									<span class="error" id="advice-required-postArea"
										style="display:none"></span>
								</div>
							</div>	
							<div class="field">	
								<div class="label">
									<label for="logisticsFee" class="req">物流运费:</label>
								</div>
								<div class="input">
									 <form:input path="logisticsFee" cssClass=" required validate-pattern-/^\d+(\.\d+)?$/ float-range--0-9999999.99" maxlength="10"/>
	                				 <input type="checkbox" id="logisticsFeeType" name="logisticsFeeType" value="1"   />累计计算
									<span class="error"  id="advice-validate-pattern-logisticsFee" 
										style="display:none" ></span>
									 <span class="error" id="advice-required-logisticsFee" style="display:none"></span>
								</div>
								
							</div>
						</div>
						<!-- 需要物流配送此div显示 end -->
						</div>
						<!-- 不需要发码此div显示  end-->
						
						
						
						 
						 
						 
					
						   <div class="buttons">
                    <div class="highlight">
                        <input id="submitButton" type="submit" name="submit.highlight"  value="提交" />
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
</body>
</html>