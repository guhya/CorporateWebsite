<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>


<c:set var="baseURL" value="${fn:replace(req.requestURL, fn:substring(req.requestURI, 1, fn:length(req.requestURI)), req.contextPath)}" />

<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>${pageTitle}</title>
	<link rel="stylesheet" type="text/css" href="/resources/admin/css/bootstrap.min.css" />
	<link rel="stylesheet" type="text/css" href="/resources/admin/css/font-awesome.min.css" />
	
	<tiles:insertAttribute name="css" />
</head>
<body style="padding-top: 20px;padding-bottom: 20px; margin-bottom: 20px;">
	<div class="container">
		<nav class="navbar navbar-default">
			<div class="container-fluid">
			
				<div class="navbar-header">
					<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
						<span class="sr-only">Toggle navigation</span>
						<span class="icon-bar"></span>
						<span class="icon-bar"></span>
						<span class="icon-bar"></span>
					</button>
					<a class="navbar-brand" href="#">Corporate Website</a>
				</div>
				
				<div id="navbar" class="navbar-collapse collapse">
					<ul class="nav navbar-nav">
						<sec:authorize access="isAuthenticated()">
							<li>
								<a href="/admin/user/mydetail">
									<sec:authentication property="principal.userVo.firstName" var="principalFirstName"/> 
									<spring:message code="home.hello" arguments="${principalFirstName},"/>
								</a>
							</li>
						</sec:authorize>
					</ul>
					<ul class="nav navbar-nav navbar-right">
						<sec:authorize access="isAuthenticated()">
							<li><a href="/admin/logout"><i class="fa fa-lock"></i> <spring:message code="home.logout" /></a></li>
						</sec:authorize>
						<sec:authorize access="isAnonymous()">
							<li><a href="/admin/login"><i class="fa fa-lock"></i> <spring:message code="home.login" /></a></li>
						</sec:authorize>
					</ul>
				</div>				
				
			</div>
		</nav>
		<div class="row">
			<div class="col-lg-12">
				<tiles:insertAttribute name="body" />			
			</div>
		</div>
	</div>
	
	<footer class="footer">
		<div class="container">
			<div class="row">
				<div class="col-lg-12 text-center" style="margin-top: 50px;">
					<p class="text-muted">
						<c:set var="localeCode" value="${pageContext.response.locale}" />
					
						<c:if test="${localeCode == 'en'}">English</c:if>
						<c:if test="${localeCode != 'en'}"><a href="${baseUrl}?lang=en">English</a></c:if>
						| 					
						<c:if test="${localeCode == 'ko'}">한국어</c:if>
						<c:if test="${localeCode != 'ko'}"><a href="${baseUrl}?lang=ko">한국어 </a></c:if> 
						|
						<c:if test="${localeCode == 'in'}">Bahasa Indonesia</c:if> 
						<c:if test="${localeCode != 'in'}"><a href="${baseUrl}?lang=in">Bahasa Indonesia</a></c:if>
					</p>
				</div>
			</div>
		</div>
	</footer>
	
	<script src="/resources/admin/js/jquery.js"></script>
	<script src="/resources/admin/js/bootstrap.min.js"></script>
	
	<tiles:insertAttribute name="js" />	
	
</body>
</html>
