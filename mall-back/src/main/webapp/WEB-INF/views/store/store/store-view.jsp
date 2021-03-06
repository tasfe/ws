<%@ page language="java" pageEncoding="utf-8"%>

<%@ include file="../../includes/importer.jsp"%>
<!doctype html>
<html>
<head>
    <ht:head/>
<script type="text/javascript">
function audit(){
	
	window.location.href="../auditPage?id=${store.id}&auditStep=${auditStep}&status=${store.status+1}";
	
}

</script>
</head>
<body>
<br />
<div id="search-menu">
      <ul>
        
    </ul>
    <br style="clear: left" />
</div>
<div id="content">
.<!-- forms -->
<div class="box">
	<div  class="title">
				<h5>
				<c:if test="${store.shopClass==2 }">商户基本信息查看</c:if>
				<c:if test="${store.shopClass==3 }">渠道商基本信息查看</c:if>	
				</h5>
			</div>
 
        <div class="form">
            <div class="fields">
                
                <div class="field">
                  <div class="label">
                        <label for="name" >名称：</label>
                    </div>
                    <div class="input"><!-- small medium large -->
                       ${store.name }&nbsp;
                    </div>
              </div>
              <div class="field">
                  <div class="label">
                        <label for="accountType" >资金来源：</label>
                    </div>
                    <div class="input"><!-- small medium large -->
                       ${accountTypeMap[store.accountType] }&nbsp;
                    </div>
              </div>
             
             
               
                <div class="field">
                    <div class="label">
                        <label for="areaCode" >归属地市:</label>
                    </div>
                    <div class="input">
                     ${store.areaName }&nbsp;
                     </div>
                </div>
                 <div class="field">
                    <div class="label">
                        <label for="areaId" >行政编码:</label>
                    </div>
                    <div class="input">
                     ${store.areaId }&nbsp;
                    </div>
                </div>
                <!--  
                <div class="field">
                    <div class="label label-radio">
                        <label>是否移动签约:</label>
                    </div>
                    <div class="radios">
                        <div class="radio">
                            <input type="radio" id="radio-1" name="sort" value="1"/><label for="radio-1">是</label>
                            <input type="radio" id="radio-2" name="sort" checked="checked" value="0"/><label for="radio-2">否</label>
                        </div>
                        <span class="error" id="advice-validate-one-required-radioex1" style="display:none"></span>
                    </div>
                </div>
                 -->
                
                
               <c:if test="${store.shopClass == 3 }">
                <div class="field">
                    <div class="label">
                        <label for="sleepTime" >代理结算商户:</label>
                    </div>
                    <div class="input">&nbsp;&nbsp;
                  	   <c:forEach items="${storeList}" var="item">
                  	   <a href="../storeView/${item.id}" >${item.name }</a>&nbsp;&nbsp;&nbsp;&nbsp;
                  	   </c:forEach>
                    </div>
                </div>
                </c:if>
                
               
                  <div class="field">
                    <div class="label">
                        <label for="address" >地址:</label>
                    </div>
                    <div class="input">
                     ${store.address }&nbsp;
                    </div>
                </div>
               
                <div class="field">
                    <div class="label">
                        <label for="linkName" >联系人:</label>
                    </div>
                    <div class="input">
                     ${store.linkName }&nbsp;
                    </div>
                </div>
                <div class="field">
                    <div class="label">
                        <label for="linkPhone" >联系电话:</label>
                    </div>
                    <div class="input">
                      ${store.linkPhone }&nbsp;
                    </div>
                </div>
                <div class="field">
                    <div class="label label-radio">
                        <label>商品发布免审:</label>
                    </div>
                    <div class="radios">
                        <div class="radio">
                          ${store.itemPublishAuditFlagName  }&nbsp;
                        </div>
                        <span class="error" id="advice-validate-one-required-radioex1" style="display:none"></span>
                    </div>
                </div>
               
                 <div class="field">
                    <div class="label label-radio">
                        <label>商品编辑免审:</label>
                    </div>
                    <div class="radios">
                        <div class="radio">
                         ${store.itemEditAuditFlagName  }&nbsp;
                        </div>
                        <span class="error" id="advice-validate-one-required-radioex1" style="display:none"></span>
                    </div>
                </div>
                
                <c:if test="${store.shopClass ==2 }">
						<div class="field">
							<div class="label label-radio">
								<label>门店编辑免审:</label>
							</div>
							<div class="radios">
								 ${store.shopEditAuditFlagName  }&nbsp;
							</div>
						</div>	
						</c:if>
                
                 <div class="field">
                    <div class="label">
                        <label for="shortName" >简称:</label>
                    </div>
                    <div class="input">
                      ${store.shortName }&nbsp;
                    </div>
                </div>
                
                <div class="field">
                    <div class="label">
                        <label for="linkFax" >联系传真:</label>
                    </div>
                    <div class="input">
                      ${store.linkFax }&nbsp;
                    </div>
                </div>
                
                
                <div class="field">
                    <div class="label">
                        <label for="bsManagerName" >业务主管:</label>
                    </div>
                    <div class="input">
                     ${store.bsManagerName }&nbsp;
                    </div>
                </div>
                <div class="field">
                    <div class="label">
                        <label for="bsManagerPhone" >业务主管电话:</label>
                    </div>
                    <div class="input">
                     ${store.bsManagerPhone }&nbsp;
                    </div>
                </div>
                
                <div class="field">
                    <div class="label">
                        <label for="fcManagerName" >财务主管:</label>
                    </div>
                    <div class="input">
                      ${store.fcManagerName }&nbsp;
                    </div>
                </div>
                 <div class="field">
                    <div class="label">
                        <label for="fcManagerPhone" >财务主管电话:</label>
                    </div>
                    <div class="input">
                       ${store.fcManagerPhone }&nbsp;
                    </div>
                </div>
                
               
                
              
                 <div class="field">
                    <div class="label">
                        <label for="servicePhone" >客服电话:</label>
                    </div>
                    <div class="input">
                     ${store.servicePhone }&nbsp;
                    </div>
                </div>
                
                  
                <div class="field">
                    <div class="label label-textarea">
                        <label for="bsScope">营业范围：</label>
                    </div>
                    <div class="input">
                     ${store.bsScope }&nbsp;
                    </div>
                </div>
                 <c:if test="${!empty auditStepList}">
                <div class="field">
							<div class="label">
								<label for="remarkL" >审核意见:</label>
							</div>
							<div class="input">
								<c:forEach items="${auditStepList}" var="item">
			                         <label ><ct:time source="${item.updateTime}" tfmt="yyyy-MM-dd HH:mm:ss"/>&nbsp;&nbsp;&nbsp;&nbsp;${item.remarkL}</label><br/>
                       			</c:forEach>
							</div>
				</div>
                </c:if>
                 <div class="field">
							<div class="label">
								<label for="statusName" >审核状态:</label>
							</div>
							<div class="input">
								${store.statusName }&nbsp;
								</div>
				</div>
                
                <div class="field">
							<div class="label">
								<label for="areaId" >同步高阳状态:</label>
							</div>
							<div class="input">
								${store.syncGyFlagName }&nbsp;
								</div>
				</div>
				
                 <div class="field" style="display: none">
							<div class="label">
								<label for="logisticsFee" >物流运费:</label>
								
							</div>
							<div class="input">
							    <c:forEach items="${mylogisticsList}" var="item" varStatus="i">
							    ${item.name },${item.feeNum }
							    </c:forEach>&nbsp;
							</div>
				</div>
				<div class="field">
                    <div class="label label-textarea">
                        <label for="createTime">创建时间：</label>
                    </div>
                    <div class="input">
                     <ct:time source="${store.createTime}" tfmt="yyyy-MM-dd HH:mm:ss"/>
                    </div>
                </div>
                <div class="buttons">
                  <div class="highlight">
                        <input type="button" class="common_btn" onclick="history.back();" value="返回" />
                    </div>
                </div>
              
            </div>
        </div>

</div>
<!-- end forms -->
</div>





</body>
</html>