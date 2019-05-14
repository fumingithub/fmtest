${'<#include "../../common/_public.html"/>'}

<div class="bjui-pageContent">
	<form action="" id="pageform"  data-alertmsg="true" data-isrefsh="false">
		<div style="margin:15px auto 0; width:800px;">
			<fieldset>
				<legend>
					<h3 class="listtitle">${packageInfo.className}信息</h3>
					<input type="hidden" name="id" value="${r'${entity.id!}'}"/>
				</legend>
				<table class="table table-condensed" width="100%">
					<tbody>
					<#list tableInfo.columns as column>
					<tr>
						<td class="right" style="width: 130px">${column.description!column.name}</td>
						<td class="left" colspan="3">
							<input name="entity.${column.name}" type="text" data-rule="required" value="${r'${entity.'}${column.name}${'!}'}" size="20" />
						</td>
					</tr>
					</#list>
				</table>
			</fieldset>
		</div>
	</form>
</div>
<div class="bjui-pageFooter">
    <ul>
        <li>
            <button type="button" class="btn-close closecutab" data-icon="close">关闭</button>
        </li>
        <li>
            <button onclick="saveForm(this,'${r'${ctx}'}/${packageInfo.moduleName?lower_case}/${packageInfo.className?lower_case}!save.do','pageform')" class="btn-default" data-icon="save">保存</button>
        </li>
    </ul>
</div>