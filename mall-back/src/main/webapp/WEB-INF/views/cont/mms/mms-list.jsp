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
        <ct:display model="mms_list" btn="add_btn">
        	<ht:menu-btn text="添加彩信" url="/cont/mms/preAdd.do"/>
        </ct:display>
    </ul>
    <br style="clear: left" />
</div>
<div class="queryContainer" >
    <form name="queryForm" id="queryForm" action="?" method="get">
        <table>
            <tr>
            <td>彩信标题：</td>
             <td><input type="text" name="title" value="${param.title}" class="txt" style="width:120px"/></td>
            
             <td>状态：</td>
                 <td width="200">
                    <select name="status">
                        <option value="">--请选择--</option>
                        <c:forEach items="${statusMap}" var="item">
                        <option value="${item.key}"  <c:if test="${item.key==param.status}">selected="selected"</c:if>>${item.value}</option>
                        </c:forEach>
                    </select>
                </td>

			<td width="70">创建时间：</td>
                <td width="300"/>
                    <input type="text" id="inputStartTime" name="inputStartTime" value="${param.inputStartTime}" class="txt Wdate"
                           readOnly onclick="WdatePicker({vel:'startTime',realDateFmt:'yyyyMMdd',maxDate:'#F{$dp.$D(\'inputEndTime\')||\'2020-10-01\'}'})" />
                    <input type="hidden" name="startTime" id="startTime" value="${param.startTime}"/>
                    至
                    <input type="text" id="inputEndTime" name="inputEndTime" value="${param.inputEndTime}" class="txt Wdate"
                           readOnly onclick="WdatePicker({vel:'endTime',realDateFmt:'yyyyMMdd',minDate:'#F{$dp.$D(\'inputStartTime\')}',maxDate:'2020-10-01'})" />
                    <input type="hidden" name="endTime" id="endTime" value="${param.endTime}" />
                </td>
            </tr>
            <tr>
             <td>内容源名称：</td>
             <td><input type="text" name="contentSrc" value="${param.contentSrc}" class="txt" style="width:120px"/></td>
                <td colspan="4">
                    <ct:btn type="search" />
                </td>
            </tr>
        </table>
    </form>
</div>

<div class="container">
    <br/>
    <h3>彩信内容列表</h3>

    <div class="mainbox">
        <c:if test="${not empty pageData}">

        <table class="datalist fixwidth">
            <tr>
                <th nowrap="nowrap">彩信编号</th>
                <th nowrap="nowrap">内容源</th>
                <th nowrap="nowrap">标题</th>
                <th nowrap="nowrap">开始时间</th>
                <th nowrap="nowrap">结束时间</th>
                 <!-- <th nowrap="nowrap">地区</th> -->
                 <th nowrap="nowrap">状态</th>
                 <th nowrap="nowrap">修改时间</th>
                <th nowrap="nowrap">操作</th>
            </tr>

            <c:forEach items="${pageData.data}" var="item">
            <tr>
                <td width="50" nowrap="nowrap">${item.id}</td>
                <td nowrap="nowrap" width="100">${item.contentSrc}</td>
                <td nowrap="nowrap" class="ellipsis" width="100">${item.title}</td>
                <td nowrap="nowrap" width="100"><ct:time source="${item.startTime}"/></td>
              	<td nowrap="nowrap" width="100"><ct:time source="${item.endTime}"/></td>
              	<!-- <td nowrap="nowrap" width="100">${item.areaCode}</td> -->
              	<td nowrap="nowrap" width="100"><c:choose><c:when test="${item.status == 0}">审核中</c:when><c:when test="${item.status == 1}">审核通过</c:when><c:when test="${item.status == 2}">审核驳回</c:when></c:choose></td>
              	<td nowrap="nowrap" width="100"><ct:time source="${item.updateTime}"/></td>
                <td width="100" nowrap="nowrap">
                <ct:display model="mms_list" btn="view_btn">
                        <a href="./view?id=${item.id}">查看</a>
                    </ct:display>
                 
                    <c:if test="${item.status != 1 }">
                    <ct:display model="mms_list" btn="mod_btn">
                        <a href="./preEdit?id=${item.id}">修改</a>
                    </ct:display>
                   
                    <ct:display model="mms_list" btn="del_btn">
                        <a href="#" onclick="deleteItem('./delete/${item.id}');">删除</a>
                    </ct:display>
                  
                    </c:if>
                    <c:if test="${item.status == 0}">
                    <ct:display model="mms_list" btn="audit_btn">
                         <a href="./preAudit?id=${item.id}">审核</a>
                    </ct:display>
                    </c:if>
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