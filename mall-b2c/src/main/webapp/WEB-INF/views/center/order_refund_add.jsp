<%@page contentType="text/html;charset=utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="ct" uri="/cplatform-tag"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="../css/base.css" rel="stylesheet" type="text/css" />
<link href="../css/layout.css" rel="stylesheet" type="text/css" />
<link href="../css/usercenter.css" rel="stylesheet" type="text/css" />
<link href="../css/sidebar.css" rel="stylesheet" type="text/css" />
<link href="../css/pro.css" rel="stylesheet" type="text/css" />
<link href="../js/lab/default/dialog.css" rel="stylesheet" type="text/css"/>
<script type="text/javascript" src="../js/date-picker/WdatePicker.js"></script>
<script type="text/javascript" src="../js/jquery.min.js"></script>
<script type="text/javascript" src="../js/jquery.form.js" charset="utf-8"></script>
<script type="text/javascript" src="../js/lab/jquery.dialog.js"></script>
<script type="text/javascript" src="../js/cart-common.js"></script>
<script type="text/javascript" src="../js/ext.js"></script>
<script type="text/javascript" src="../js/usercenter.js"></script>
<script type="text/javascript" src="../js/ad.js"></script>
<script type="text/javascript" src="../js/global.js"></script>
<script type="text/javascript" src="../js/base.js"></script>
<script type="text/javascript" src="../js/pagescroll.js"></script>
<script type="text/javascript" src="../js/jquery.cookie.js"></script>
 <style>
		.tk_goods_info { margin-bottom:10px; font-size:12px;}
		.tk_goods_info dl{ padding-left:120px; position:relative; line-height:30px; }
    	.tk_goods_info dt{ position:absolute;top:0; left:0; color:#999;}
		.tk_goods_info dd select{ font-size:14px; margin-bottom:5px; padding:3px;}
		.tk_goods_info dd table { border-right:1px #DEDEDE solid; border-left:1px #DEDEDE solid; line-height:24px;	 }
		.tk_goods_info dd table th,.tk_goods_info dd table td{border-top:1px #DEDEDE solid;border-bottom:1px #DEDEDE solid;  color:#666;padding:3px 5px;}
		.tk_goods_info dd table td{ background:#FFF;}
		.disinput{ border:1px solid #ddd; padding:0 5px; font-size:16px; width:80px; text-align:right; background:#F3F3F3; height:24px; }
		textarea{border: 1px solid #ddeaf6;font-size: 14px;height: 90px;margin: 0 auto;padding: 6px;text-align: left;width: 580px;}
    </style>
<script>
	var webRoot = '';
	var web_url = '我的退款';

	$(function() {
		if ($(".order_select").val() == "other") {
			$(".reason_textarea").css("display", "block");
		}
		$(".order_select").change(function() {
			if ($(this).val() == "other") {
				$(".reason_textarea").css("display", "block");
			} else {
				$(".reason_textarea").css("display", "none");
			}
		});
		
		$('#submit_apply').click(function(){
			if(!$('select[name=reasonSel]').val()){
				simpleAlert('请选择您的退款理由');
				return false;
			}else if($('select[name=reasonSel]').val() == '其他'){
				if(!$('#textarea_value').val() || $.trim($('#textarea_value').val()) == '请输入退款理由...'){
					simpleAlert('请选择您的退款理由');
					return false;
				}else{
					$('input[name=reason]').val($.trim($('#textarea_value').val()));
				}
			}else{
				$('input[name=reason]').val($('select[name=reasonSel]').val());
			}
			$('#fm').submit();
		});
		
		$('select[name=reasonSel]').change(function(){
			var valueSel = $(this).val();
			if(valueSel == '其他'){
				$('#reason_textarea').show();
			}else{
				$('#reason_textarea').hide();
			}
		});
	});
	
</script>
<title>我的退款</title>

</head>
<body id="usercenter">
	<script type="text/javascript" src="../js/head.js"></script>
	<div class="wrapper mauto">

		<div class="breadcrumb">
			<strong><a href="../">首页</a></strong><span>&nbsp;&gt;&nbsp;<a
				href="./orderManager.chtml">个人中心</a></span><span>&nbsp;&gt;&nbsp;我的退款申请</span>
		</div>
		 <form:form method="post" action="/center/applyRefund.chtml" id="fm"  commandName="orderRefund" htmlEscape="true" acceptCharset="utf-8" cssClass="center_applyRefund">
			<input type="hidden" id="orderId" name="orderId" value="${actOrderInfo.id }"/>
		 	<input type="hidden" id="storeId" name="storeId" value="${actOrderInfo.shopId }" />
         	<input type="hidden" id="refundType" name="refundType" value="${refundType }"/>
         	<input type="hidden" id="itemMode" name="itemMode" value="${mapPayMent.itemMode }"/>
            <input type="hidden" name="cashLimit" value="${mapPayMent.totalCash }"/>
            <input type="hidden" name="coinLimit" value="${mapPayMent.totalCoin }"/>
            <input type="hidden" name="scoreLimit" value="${mapPayMent.totalScore }"/>
            <input type="hidden" name="phoneLimit" value="${mapPayMent.totalBalance }"/>
            <input type="hidden" name="redpackageLimit" value="${mapPayMent.totalRedpack }"/>
		<div id="main" class="fr  w750">

			<div class="detail">
				<div class="tab">
					<div class="tab_nav">
						<ul class="tab">
							<li class="curr"><a href="#info1">我的退款申请</a></li>
						</ul>
					</div>
				</div>
			</div>
			<div class="content">
				<div class="tk_goods_info">
					<dl>
						<dt>订单编号：</dt>
						<dd>${actOrderInfo.id }</dd>
					</dl>
					<dl>
						<dt>退款单号：</dt>

						<dd>自动生成</dd>
					</dl>
					<dl>

						<dt>订单价格：</dt>
						<dd>

							<span class="f14 red">${mapPayMent.orderSum }</span>元 <span class="col999">(包含物流费：${mapPayMent.expressCost }元)</span>
						</dd>
					</dl>
					<dl>
						<dt>已退金额：</dt>
						<dd>
							${mapPayMent.refunded_yuan }<span>元</span>
						</dd>
					</dl>

					<dl class="pt10 pb15">
						<dt>可退商品：</dt>
						<dd>
							<table width="100%" bgcolor="#F3F3F3" cellspacing="0"
								cellpadding="0">
								<tr>
									<th scope="col">商品</th>
									<!--<th scope="col">商品属性</th> -->
									<th scope="col">购买数量</th>
									<th scope="col">已退数量</th>

									<th scope="col">退货数量</th>
								</tr>
								<c:if test="${not empty mapPayMent && not empty mapPayMent.actOrderGoodsList }">
									<c:forEach items="${mapPayMent.actOrderGoodsList }" var="item">
										<tr>
											<td>${item.GOODSNAME}</td>
											<td>${item.count}</td>
											<td>${item.backNumber }</td>
											<td>
												${item.count - item.backNumber}
												<input type="hidden" name="orderRefundGoods" value="${item.id}_${item.goods_id}_${item.count - item.backNumber}" />
											</td>
										</tr>
									</c:forEach>
								</c:if>						
							</table>


						</dd>
					</dl>
					
					<c:if test="${(mapPayMent.totalCash > 0) || (mapPayMent.totalCoin > 0) || (mapPayMent.totalScore > 0) || (mapPayMent.totalBalance > 0) || (mapPayMent.totalRedpack > 0) }">
						<dl>
							<dt>本次退款金额：</dt>
							<dd>
								<c:if test="${mapPayMent.totalCash > 0}">
									现&nbsp;&nbsp;&nbsp;金：<input type="text" value="${mapPayMent.totalCash }" name="cashFee" class="disinput" readonly="readonly" /> 元 <br />
								</c:if>
								<c:if test="${mapPayMent.totalCoin > 0}">
									商城币：<input type="text" value="${mapPayMent.totalCoin }" name="coinFee"   class="disinput" readonly="readonly" /> 元 <br />
								</c:if>
								<c:if test="${mapPayMent.totalScore > 0}">
									积&nbsp;&nbsp;&nbsp;分：<input type="text" value="${mapPayMent.totalScore }" name="scoreFee" class="disinput" readonly="readonly" /> 元 <br />
								</c:if>
								<c:if test="${mapPayMent.totalBalance > 0}">
									话&nbsp;&nbsp;&nbsp;费：<input type="text" value="${mapPayMent.totalBalance }" name="phoneFee" class="disinput" readonly="readonly" /> 元 <br />
								</c:if>
								<c:if test="${mapPayMent.totalRedpack > 0}">
									红&nbsp;&nbsp;&nbsp;包：<input type="text" value="${mapPayMent.totalRedpack }" name="redpackageFee" class="disinput" readonly="readonly" /> 元 <br />
								</c:if>
							</dd>
						</dl>					
					
					<dl class="pt10 pb15">
						<dt>
							退款理由<span class="red">*</span>：
						</dt>
						<dd>
							<div>
								<select name="reasonSel" class="order_select">
									<option value="" selected="selected">请选择您的退款理由</option>
									<option value="现在不想买了">现在不想买了</option>
									<option value="商品你价格较贵">商品价格较贵</option>
									<option value="卖家长期未发货">卖家长期未发货</option>
									<option value="错误重复下单">错误或重复下单</option>
									<option value="其他">其他</option>
								</select>
								<input type="hidden" value="" name="reason" />
							</div>
							<div id="reason_textarea" class="reason_textarea" style="display:none;">
								<textarea id="textarea_value">请输入退款理由...</textarea>
							</div>
						</dd>
					</dl>
					</c:if>
					<dl>
						<dd>
							<c:if test="${(mapPayMent.totalCash > 0) || (mapPayMent.totalCoin > 0) || (mapPayMent.totalScore > 0) || (mapPayMent.totalBalance > 0) || (mapPayMent.totalRedpack > 0) }">
								<input id="submit_apply" type="button" class="btn bodnone" value="提交申请" />
							</c:if>
							<input type="button" class="btn bodnone" value="取消" id="cancelBtn" />
						</dd>
					</dl>
				</div>
			</div>
		</div>
	</form:form>
		<%@include file="../center/left_menu.jsp"%>
		<span class="clr"></span>
		
	</div>
	<script type="text/javascript"
		src="http://mall.12580life.com/js/foot.js"></script>
	<script>
	    $(function(){
	        $("#"+$.cookie('backLeftId')).addClass("col_link");
	        $('#cancelBtn').click(function(){
	        	window.location.href = "${ctx}/center/"+$.cookie("backLeftHref");
	        });
	    });
	</script>
</body>
</html>