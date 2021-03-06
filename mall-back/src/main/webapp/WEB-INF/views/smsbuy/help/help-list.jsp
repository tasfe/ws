<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="../../includes/importer.jsp"%>
<!doctype html>	
<html>
<head>
	<ht:head/>
</head>
<body>
<br/>
<div id="search-menu">
    <ul>
        <ht:menu-btn type="search"/>
        <ct:display model="smsbuy_act_help_list" btn="add_btn">
        	<ht:menu-btn text="创建短信购帮助信息" url="/smsbuy/help/preAdd" type="add"/>
        </ct:display>
	</ul>
    <br style="clear: left" />
</div>
<div class="queryContainer" >
    <form name="queryForm" id="queryForm" action="?" method="get">
        <table>
            <tr>
                
             <td>特服号：</td>
             <td><input type="text" name="helpSpcode" value="${param.helpSpcode}" class="txt" style="width:120px"/></td>
             
                <td width="70">推荐类型：</td>
                <td width="200">
                    <select name="recommendType">
                        <option value="">--请选择--</option>
                    	<c:forEach items="${recommendTypeMap }" var="item">
                        	    <option value="${item.key }" <c:if test="${param.recommendType == item.key}">selected="selected"</c:if>>${item.value }</option>
                        </c:forEach>
                    </select>
                </td>
                
            </tr>
            <tr>
                <td colspan="4">
                    <ct:btn type="search" />
                </td>
            </tr>
        </table>
    </form>
</div>

<div class="container">
    <br/>
    <h3>短信购帮助信息列表</h3>

    <div class="mainbox">
        <c:if test="${not empty pageData}">

        <table class="datalist fixwidth">
            <tr>
                <th nowrap="nowrap">特服号</th>
                <th nowrap="nowrap">推荐类型</th>
                <th nowrap="nowrap">回复语特服号</th>
                <th nowrap="nowrap">操作</th>
            </tr>

            <c:forEach items="${pageData.data}" var="item">
            <tr>
                <td nowrap="nowrap">${item.helpSpcode}</td>
                <td nowrap="nowrap">${item.recommendTypeName}</td>
                <td nowrap="nowrap">${item.recommendSpcode}</td>
                <td nowrap="nowrap">
                <ct:display model="smsbuy_act_help_list" btn="view_btn">
                <a href="./view?helpSpcode=${item.helpSpcode}&helpArea=${item.helpArea}">查看</a>
                </ct:display>
                <ct:display model="smsbuy_act_help_list" btn="mod_btn">
                <a href="./preEdit?helpSpcode=${item.helpSpcode}&helpArea=${item.helpArea}">修改</a>
                </ct:display>
                <ct:display model="smsbuy_act_help_list" btn="del_btn">
                <a href="#" onclick="deleteItem('./delete.do?helpSpcode=${item.helpSpcode}&helpArea=${item.helpArea}');">删除</a>
                </ct:display>
                </td>
            </tr>
            </c:forEach>
        </table>

        <ht:page pageData="${pageData}" />

        </c:if>
        <c:if test="${empty pageData}">
        <div class="note">
            <p class="i">目前没有相关记录!</p>
        </div>
        </c:if>
    </div>

</div>

</body>
</html>