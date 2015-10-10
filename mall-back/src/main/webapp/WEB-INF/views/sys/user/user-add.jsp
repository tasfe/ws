<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="../../includes/importer.jsp"%>
<!doctype html>
<html>
<head>
    <head>
    <ht:head/>
    
<script type="text/javascript">
$().ready(function() {
	selectRegion("#regonName","regon","regonName",${pid},{maxItems:10});
    
});  
</script>
    </head>
<body>


<div id="content">
<!-- forms -->
<div class="box">
    <!-- box / title -->
    <div class="title">
        <h5>添加用户</h5>
    </div>
    <!-- end box / title -->
    <form:form method="post" id="fm" commandName="user" htmlEscape="true" acceptCharset="utf-8" cssClass="required-validate">
        <div class="form">
            <div class="fields">
                <div class="field">
                    <div class="label noinput">用户ID：</div>
                    <div class="input">自动生成</div>
                </div>

                <div class="field">
                    <div class="label">
                        <label for="userCode" class="req">账号：</label>
                    </div>
                    <div class="input"><!-- small medium large -->
                        <form:input path="userCode" cssClass="small required  max-length-50" maxlength="50" />
                    </div>
                </div>

                <div class="field">
                    <div class="label">
                        <label for="userPwd" class="req">用户密码：</label>
                    </div>
                    <div class="input">
                        <form:password path="userPwd" cssClass="small required min-length-6 max-length-25" maxlength="20"/>
                        <span class="tip">支持6位英文字母或是数字或特殊字符，且必须以字母开头、包含特殊字符.</span>
                        <span class="error" id="advice-required-userPwd" style="display:none"></span>
                        <span class="error" id="advice-min-length-userPwd" style="display:none"></span>
                        <span class="error" id="advice-max-length-userPwd" style="display:none"></span>
                        <span class="error" id="advice-server-userPwd" style="display:none"></span>
                    </div>
                </div>

                <div class="field">
                    <div class="label">
                        <label for="confirmPass" class="req">确认密码：</label>
                    </div>
                    <div class="input">
                        <form:password path="confirmPass" cssClass="small required min-length-6 max-length-25" maxlength="20"/>
                        <span class="tip">支持6位英文字母或是数字或特殊字符，且必须以字母开头、包含特殊字符.</span>
                        <span class="error" id="advice-required-confirmPass" style="display:none"></span>
                        <span class="error" id="advice-min-length-confirmPass" style="display:none"></span>
                        <span class="error" id="advice-max-length-confirmPass" style="display:none"></span>
                        <span class="error" id="advice-server-confirmPass" style="display:none"></span>
                    </div>
                </div>
 				<c:if test="${unitId ==0}">
                <div class="field">
                    <div class="label label-textarea">
                        <label for="unitId" >选择单位：</label>
                    </div>
                    <div class="input">
                        <select name="unitId">
                        <option value="">请选择</option>
                         <c:forEach items="${unitList}" var="item">
                          <option value="${item.id }">${item.name }</option>
                         </c:forEach>
                        </select>
                    </div>
                </div>
                </c:if>
                <div class="field">
                    <div class="label">
                        <label for="terminalId" class="req">手机号码：</label>
                    </div>
                    <div class="input">
                        <form:input path="terminalId" cssClass="small required validate-mobile-phone" maxlength="13"/>
                    </div>
                </div>

                <div class="field">
                    <div class="label">
                        <label for="email" class="req">注册邮箱：</label>
                    </div>
                    <div class="input">
                        <input type="text" name="email" id="email" class="small required validate-email" maxlength="50" v/>
                    </div>
                </div>

                <div class="field">
                    <div class="label">
                        <label for="userName" class="req">真实姓名：</label>
                    </div>
                    <div class="input">
                        <input type="text" name="userName" id="userName" class="small required min-length-0 max-length-10" maxlength="10"  />
                    </div>
                </div>
                
                  
                   <div class="field" style="display: none">
                    <div class="label">
                        <label for="flag" class="req">标识：</label>
                    </div>
                    <div class="input">
                      <select name="flag">
                     <c:forEach items="${userTypeMap }" var="item">
                        	    <option value="${item.key }" <c:if test="${unitType == item.key}">selected="selected"</c:if>>${item.value }</option>
                        </c:forEach>
                        </select>
                    </div>
                </div>
                
                 <div class="field">
                    <div class="label label-textarea">
                        <label for="role" class="req">选择角色：</label>
                    </div>
                    <div class="checkboxes">
                        <div class="checkbox1">
                         <c:forEach items="${sysRoleList}" var="item">
                         <input type="checkbox" name="role" value="${item.id }" />
                         <label >${item.roleName }</label>
                         
                         </c:forEach>
                         </div>
                         
                    </div>
                    
                </div>
               
				<div class="field">
                    <div class="label label-textarea">
                        <label for="unitId" >选择区域：</label>
                    </div>
                     <div class="checkboxes">
                         <input type="text" name ="regonName" id="regonName" value="" />
                         <input type="hidden" name ="regon" id="regon" value="" />
                    </div>
                 </div>
                <div class="field">
                    <div class="label label-textarea">
                        <label for="remark">备注：</label>
                    </div>
                    <div class="input">
                        <form:textarea path="remark" cols="50" rows="8" cssClass="max-length-100" />
                    </div>
                </div>

                <%--
                <div class="field">
                    <div class="label">
                        <label for="date">日期输入:</label>
                    </div>
                    <div class="input">
                        <input type="text" id="date" name="input.date" class="date required" onfocus="WdatePicker({realDateFmt:'yyyyMMdd'})" readonly/>
                    </div>
                </div>
                <div class="field">
                    <div class="label">
                        <label for="select">选择框:</label>
                    </div>
                    <div class="select">
                        <select id="select" name="select" class="validate-selection">
                            <option value="">空， 请选择</option>
                            <option value="1">选项1</option>
                            <option value="2">选项2</option>
                            <option value="3">选项3</option>
                        </select>
                    </div>
                </div>
                <div class="field">
                    <div class="label label-checkbox">
                        <label>多选框:</label>
                    </div>
                    <div class="checkboxes">
                        <div class="checkbox">
                            <input type="checkbox" id="checkbox-4" name="checkboxex" value="f" class="validate-one-required"/><label for="checkbox-4">选项 #1</label>
                            <input type="checkbox" id="checkbox-5" name="checkboxex" value="a" class="validate-one-required"/><label for="checkbox-5">Option #1</label>
                            <input type="checkbox" id="checkbox-6" name="checkboxex" value="v" class="validate-one-required"/><label for="checkbox-6">Option #1</label>
                            <input type="checkbox" id="checkbox-7" name="checkboxex" value="d" class="validate-one-required"/><label for="checkbox-7">Option #1</label>
                        </div>
                        <div class="checkbox">
                            <input type="checkbox" id="checkbox-8" name="checkboxex" /><label for="checkbox-4">选项 #1</label>
                            <input type="checkbox" id="checkbox-9" name="checkboxex" /><label for="checkbox-5">Option #1</label>
                            <input type="checkbox" id="checkbox-3" name="checkboxex" /><label for="checkbox-6">Option #1</label>
                            <input type="checkbox" id="checkbox-1" name="checkboxex" /><label for="checkbox-7">Option #1</label>
                        </div>
                        <span class="error" id="advice-validate-one-required-checkboxex" style="display:none"></span>
                    </div>
                </div>
                <div class="field">
                    <div class="label label-radio">
                        <label>单选框:</label>
                    </div>
                    <div class="radios">
                        <div class="radio">
                            <input type="radio" id="radio-1" name="radioex1" class="validate-one-required"/><label for="radio-1">选项 #1</label>
                            <input type="radio" id="radio-2" name="radioex1" /><label for="radio-2">Option #1</label>
                            <input type="radio" id="radio-3" name="radioex1" /><label for="radio-3">Option #1</label>
                            <input type="radio" id="radio-4" name="radioex1" /><label for="radio-4">Option #1</label>
                        </div>
                        <div class="radio">
                            <input type="radio" id="radio-5" name="radioex1" /><label for="radio-5">选项 #1</label>
                            <input type="radio" id="radio-6" name="radioex1" /><label for="radio-6">Option #1</label>
                            <input type="radio" id="radio-7" name="radioex1" /><label for="radio-7">Option #1</label>
                            <input type="radio" id="radio-8" name="radioex1" /><label for="radio-8">Option #1</label>
                        </div>
                        <span class="error" id="advice-validate-one-required-radioex1" style="display:none"></span>
                    </div>
                </div>
                <div class="field">
                    <div class="label">
                        <label for="file">文件选择框:</label>
                    </div>
                    <div class="input input-file">
                        <input type="file" id="file1" name="file1" size="40" class="validate-file-png-jpg" />
                        <span class="error" id="advice-validate-file-file1" style="display:none"></span>
                    </div>

                </div>
                --%>
                <div class="buttons">
                    <div class="highlight">
                        <input type="submit" name="submit.highlight" value="提交" />
                    </div>
                    <input type="reset" name="reset" value="重置" />
                    <a href="${ctx}/sys/user/list" class="btnAnchor">返回</a>
                </div>
            </div>
        </div>
    </form:form>
</div>
<!-- end forms -->
</div>


</body>
</html>