<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="Corporate website administrator login page">
<meta name="author" content="Corporate Website">
<meta name="keyword" content="Corporate Website, Bank, Financial, Personal, Corporate">
<link rel="shortcut icon" href="/resources/admin/favicon.ico" type="image/x-icon">
<link rel="icon" href="/resources/admin/favicon.ico" type="image/x-icon">

<title><c:out value="${pageTitle}"/></title> 

<!-- Bootstrap CSS -->
<link href="/resources/admin/css/bootstrap.min.css" rel="stylesheet">
<!-- bootstrap theme -->
<link href="/resources/admin/css/bootstrap-theme.css" rel="stylesheet">
<link href="/resources/admin/css/datepicker.css" rel="stylesheet">

<!--external css-->
<!-- font icon -->
<link href="/resources/admin/css/elegant-icons-style.css" rel="stylesheet">
<link href="/resources/admin/css/font-awesome.min.css" rel="stylesheet">

<link href="/resources/admin/css/widgets.css" rel="stylesheet">
<link href="/resources/admin/css/style.css" rel="stylesheet">
<link href="/resources/admin/css/style-responsive.css" rel="stylesheet">
<link href="/resources/admin/css/jquery-ui-1.10.4.min.css" rel="stylesheet">

<!-- HTML5 shim and Respond.js IE8 support of HTML5 -->
<!--[if lt IE 9]>
      <script src="/resources/admin/js/html5shiv.js"></script>
      <script src="/resources/admin/js/respond.min.js"></script>
      <script src="/resources/admin/js/lte-ie7.js"></script>
<![endif]-->
</head>

<body class="login-img3-body">
	<div class="container">
		<form action="/j_spring_security_check" name="loginForm" class="login-form" method="post">
			<div class="login-wrap">
				<p class="login-img">
					<i class="icon_lock_alt"></i>
				</p>
				<div class="input-group">
					<span class="input-group-addon"><i class="icon_profile"></i></span> 
					<input type="text" name="j_username" id="j_username" value="admin" class="form-control" placeholder="Username" required="true" autofocus>
				</div>
				<div class="input-group">
					<span class="input-group-addon"><i class="icon_key_alt"></i></span> 
					<input type="password" name="j_password" id="j_password" value="admin" class="form-control" placeholder="Password" required="true">
				</div>
				
				<c:if test="${not empty error}">
					<c:set var="loginError" value="${SPRING_SECURITY_LAST_EXCEPTION.message}" />
					<c:if test="${empty loginError}">
						<c:set var="loginError" value="Username not found" />
					</c:if>								
					<p class="help-block" style="color: red;"><c:out value="${loginError}"/></p>
				</c:if>
				
				<label class="checkbox"> <input type="checkbox" id="_spring_security_remember_me" name="_spring_security_remember_me" /> Remember me</label>
				<input type="submit" name="submitHandler" style="display:none"/>
				<button class="btn btn-primary btn-lg btn-block" type="button" onclick="loginProc();">Login</button>
			</div>
		</form>
	</div>
</body>

<script src="/resources/admin/js/jquery.js"></script>
<script>
	var loginProc = function(){
		var frm = document.loginForm;
		frm.submitHandler.click();
	}

	$(document).ready(function() {
		$("#j_username").focus();		
	    $("#j_password").keypress(function(e) {
		    if(e.which === 13) {
		    	loginProc();
		    }
		});	    
	});
</script>

</html>
