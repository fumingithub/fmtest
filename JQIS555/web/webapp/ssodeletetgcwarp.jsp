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
//单点登陆 -- 单点登出 jsonp 代理页面
$(document).ready(function(){
	$.getJSON("http://192.168.44.39:8080/sso-server/sso/del_tgc.action?callback=?",function(data){
		window.location.href="${ctx}/admin/index.do"; 
	}) 
});
</script>
</head>
<body>
</body>
</html>