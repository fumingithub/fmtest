<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/workflow/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html lang="en">
<head>
    <title>流程部署</title>
    <script src="${ctx}/workflow/jquery.min.js" type="text/javascript"></script>
</head>

<body>

<div class="col-md-10" style="padding:10px">
    <div class="panel panel-default">
        <div class="panel-heading">
            <h3 class="panel-title"><a href="#">流程部署</a></h3>
        </div>
        <div class="panel-body">
            <form id="inputForm" action="${ctx }/admin/workflow/process!processDeploy.do" method="post" enctype="multipart/form-data">
                <table class="table_all" align="center" border="0" cellpadding="0"
                       cellspacing="0" style="margin-top: 0px">
                    <tr>
                        <td class="td_table_1">
                            <span>上传流程定义文件：</span>
                        </td>
                        <td class="td_table_2" colspan="3">
                            <input type="file" class="input_file" id="attach" name="attach"/>
                        </td>
                    </tr>
                </table>
                <br/>
                <table align="center" border="0" cellpadding="0"
                       cellspacing="0">
                    <tr align="left">
                        <td colspan="1">
                            <input type="submit" class="btn btn-default" name="submit" value="提交">
                            &nbsp;&nbsp;
                            <input type="button" class="btn btn-orange hecv-nav-close"value="返回">
                        </td>
                    </tr>
                </table>

            </form>
        </div>
    </div>
</div>

</body>
</html>
