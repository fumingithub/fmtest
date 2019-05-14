${'<#include "../../common/_public.html"/>'}
<div class="bjui-pageHeader">
    <form id="pagerForm" data-toggle="ajaxsearch" method="post" action="${r'${ctx}'}/${packageInfo.moduleName?lower_case}/${packageInfo.className?lower_case}.do">
        <div class="office">
            <ul class="offbtn">
                <li> <a  data-toggle="dialog" data-width="500" data-height="450"  data-id="${packageInfo.className?lower_case}_add"  data-url="${r'${ctx}'}/${packageInfo.moduleName?lower_case}/${packageInfo.className?lower_case}!edit.do?" data-xternal="true" data-title="${packageInfo.className}新增"> <span class="fa fa-file-text-o font24 iconbg01"></span>新增</a> </li>
                <li> <a  data-toggle="dialog" data-width="500" data-height="450"  data-id="${packageInfo.className?lower_case}_edit" data-url="${r'${ctx}'}/${packageInfo.moduleName?lower_case}/${packageInfo.className?lower_case}!edit.do?id={#bjui-selected}" data-title="${packageInfo.className}编辑" data-xternal="true"> <span class="fa fa-edit  font24 iconbg02"></span>编辑</a> </li>
                <li> <a  data-url="${r'${ctx}'}/${packageInfo.className?lower_case}!delete.do?id={#bjui-selected}" data-toggle="doajax" data-xternal="true" data-isrefsh="true" data-confirm-msg="确定要删除选中项吗？"><span class="fa fa-trash font24 iconbg03"></span>删除</a> </li>
                <li><span class="ttt"></span></li>
                <li> <a id="refresh" data-toggle="reloadsearch" data-target="#bjui-pageContent"data-icon="search" ><span class="fa fa-refresh font24 iconbg02"></span>刷新</a> </li>
            	<li> <a  class="closecutab"><span class="iconbg03"><i class="fa fa-remove  font24"></i></span>关闭</a> </li> 
            </ul>
        </div>
        <div class="bjui-searchBar">
            <input type="hidden" name="limit" value="${r'${limit}'}">
            <input type="hidden" name="start" value="${r'${start}'}">
            <lable>XXX</lable>
            <input name="XXX" class="form-control" size="15" type="text" value="${r'${filter.parameters.name!}'}" />
            <button type="submit" class="btn-default"  data-toggle="ajaxsearch" data-icon="search">查询</button>&nbsp;
            <a class="btn btn-orange" href="javascript:;" onclick="${r'$(this).navtab('}'reloadForm'${', true);'}" data-icon="undo">清空</a>

        </div>
    </form>
   </div>
<div class="bjui-pageContent">

    <table data-width="100%" data-nowrap="true" data-selected-multi="false"  class="table table-bordered table-hover table-striped table-top" >
        <thead>
        <tr>
            <th width="20"></th>
            <th width="30">NO.</th>
              <#list tableInfo.columns as column>
                 <th style="text-align:center">${column.description!}</th>
             </#list>
        </tr>
        </thead>
        <tbody>

        ${'<#list entitys as entity>'}
            <tr >
                <td><input type="checkbox"  value="${r'${entity.id}'}" name="ids" data-toggle="icheck"> </td>
                <td>${r'${entity_index+1+(start-1)*limit}'}</td>
                  <#list tableInfo.columns as column>
                        <td>${'${'}entity.${column.name}}</td>
                 </#list>
            </tr>
        ${'</#list>'}
        </tbody>
    </table>
</div>
${'<#include "../../common/_page.html"/>'}
