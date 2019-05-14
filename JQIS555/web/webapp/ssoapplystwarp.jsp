<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<title>z-console</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<script type="text/javascript" src="${ctx}/BJUI/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript">
//单点登陆 ST票据申请 JSONP 代理页面
$(document).ready(function(){
		
 	$.getJSON("http://192.168.44.39:8080/sso-server/sso/app_st.action?callback=?",function(data){
 		if(data){
			$("#sso_st").val(data);
			$("#index").submit();
		}else{
			 window.location.href="${ctx}/portal/login.do"; 
		}
	}) 
});
</script>
</head>
<body>
<form id="index" action="${ctx}/admin/index.do" method="post">
    <input type='hidden' id='sso_st' name='sso_st'>
</form>



</body>
</html>