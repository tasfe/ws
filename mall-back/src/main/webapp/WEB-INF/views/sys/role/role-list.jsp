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
        <ct:display model="role_list" btn="add_btn">
             <ht:menu-btn text="添加角色" url="/sys/role/roleAdd.do?parentRegon=${parentRegon}&regonLevel=${regonLevel}" type="add"/>
        </ct:display>
       
    </ul>
    <br style="clear: left" />
</div>
<div class="queryContainer" >
    <form name="queryForm" id="queryForm" action="?" method="get">
        <table>
             <tr>
                <td width="70">角色名称：</td>
                <td width="100"/>
               <input type="text" name="roleName" value="${param.roleName}" class="txt" style="width:150px"/>
                </td>
                 <td colspan="2">
                    <ct:btn type="search" />
                </td>
               
            </tr>
        </table>
    </form>
</div>

<div class="container">
    <br/>
    <h3>角色列表</h3>

    <div class="mainbox">
        <c:if test="${not empty pageData}">

        <table class="datalist fixwidth">
            <tr>
                <th nowrap="nowrap" width="80">角色编号</th>
                <th nowrap="nowrap" width="250">名称</th>
                <th nowrap="nowrap">备注</th>
                <th nowrap="nowrap" width="150">操作</th>
            </tr>

            <c:forEach items="${pageData.data}" var="item">
           <tr>
                <td nowrap="nowrap" >${item.id}</td>
                <td nowrap="nowrap" >${item.roleName}</td>
                <td nowrap="nowrap"  class="ellipsis">${item.remark}</td>
                <td  nowrap="nowrap">
                 <ct:display model="role_list" btn="view_btn">
                        <a href="roleView?id=${item.id}">查看</a>
                    </ct:display>
                    &nbsp;&nbsp;
                    <ct:display model="role_list" btn="mod_btn">
                        <a href="roleEdit?id=${item.id}">修改</a>
                    </ct:display>
                    &nbsp;&nbsp;
                    <ct:display model="role_list" btn="del_btn">
                        <a href="#" onclick="deleteItem('roleDel?id=${item.id}');">删除</a>
                    </ct:display>
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