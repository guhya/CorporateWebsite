<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<c:set var="url" 		value="${requestScope['javax.servlet.forward.request_uri']}"/>
<c:set var="baseURL" 	value=""/>
<c:set var="menu" 		value="${fn:split(url, '/')[1]}" />

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="Corporate website administrator login page">
<meta name="author" content="Corporate website ">
<meta name="keyword" content="Corporate website , Bank, Financial, Personal, Corporate">
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

<% //############################################################################################################################## %>
<tiles:insertAttribute name="css" />
<% //############################################################################################################################## %>
</head>
<body>
	<!-- container section start -->
	<section id="container" class="">
		<header class="header dark-bg">
			<div class="toggle-nav">
				<i class="fa fa-bars"></i>
				<div class="icon-reorder tooltips" data-original-title="Toggle Navigation" data-placement="bottom"></div>
			</div>
			<!--logo start-->
			<a href="/admin/dashboard" class="logo">CORPORATE-<span class="lite">WEBSITE</span></a>
			<!--logo end-->
			<div class="top-nav notification-row">
				<!-- notification dropdown start-->
				<ul class="nav pull-right top-menu">
					<!-- 
					<li class="dropdown">
						<c:set var="localeCode" value="${pageContext.response.locale}" />
						<a data-toggle="dropdown" class="dropdown-toggle" href="#"> 
							<c:if test="${localeCode == 'en'}">English</c:if>
							<c:if test="${localeCode == 'ko'}">한국어</c:if> 
							<c:if test="${localeCode == 'in'}">Bahasa Indonesia</c:if>
							<b class="caret"></b>
						</a>
						<ul class="dropdown-menu extended logout">
							<div class="log-arrow-up"></div>							
							<li><a href="${baseUrl}?lang=in">Bahasa Indonesia</a></li>
							<li><a href="${baseUrl}?lang=en">English</a></li>
							<li><a href="${baseUrl}?lang=ko">한국어</a></li>
						</ul>
					</li>
					-->
					<li class="dropdown">
					
						<sec:authorize access="isAuthenticated()">
							<sec:authentication property="principal.userVo.firstName" 			var="principalFirstName"/>						
							<sec:authentication property="principal.userVo.thumbnailImageSeq" 	var="thumbnailImageSeq"/>						
						
							<a data-toggle="dropdown" class="dropdown-toggle" href="#"> 
								<span class="profile-ava">
                                	<img class="lazy" data-original="/image/<c:out value="${thumbnailImageSeq}"/>" width="30">
                            	</span>
								<span class="username"><spring:message code="home.hello" arguments="${fn:escapeXml(principalFirstName)},"/></span> <b class="caret"></b>
							</a>
						</sec:authorize>

						<ul class="dropdown-menu extended logout">
							<div class="log-arrow-up"></div>
							<sec:authorize access="isAuthenticated()">
								<li><a href="/admin/user/mydetail"><i class="fa fa-user"></i> My Info</a></li>
								<li><a href="/admin/logout"><i class="fa fa-lock"></i> <spring:message code="home.logout" /></a></li>
							</sec:authorize>
							<sec:authorize access="isAnonymous()">
								<li><a href="/admin/login"><i class="fa fa-lock"></i> <spring:message code="home.login" /></a></li>
							</sec:authorize>
						</ul>
					</li>
					
				</ul>
				<!-- notification dropdown end-->
			</div>
		</header>
		<!--header end-->

		<!--sidebar start-->
		<aside>
			<div id="sidebar" class="nav-collapse " tabindex="5000" style="overflow: hidden; outline: none;">
				<!-- sidebar menu start-->
				<ul class="sidebar-menu">
					<li class="<c:if test="${menu == 'dashboard' }">active</c:if>">
						<a class="" href="/admin/dashboard"> <i class="fa fa-home"></i> <span>Dashboard</span></a>
					</li>
					<li class="<c:if test="${menu == 'user' }">active</c:if>">
						<a class="" href="/admin/user/list"> <i class="fa fa-user"></i> <span>User</span></a>
					</li>
					<!-- 
					<li class="<c:if test="${menu == 'product' }">active</c:if>">
						<a class="" href="/admin/product/list"> <i class="fa fa-gift"></i> <span>Product</span></a>
					</li>
					-->
					<li class="sub-menu <c:if test="${menu == 'catalog' }">active</c:if>">
						<a class="" href="javascript:void(0);"> <i class="fa fa-download"></i> <span>Catalog</span><span class="menu-arrow arrow_carrot-right"></span></a>
						<ul class="sub">
							<li><a class="" href="/admin/catalog/list?category=89"><span>Report</span></a></li>
							<li><a class="" href="/admin/catalog/list?category=123"><span>Picture</span></a></li>
							<li><a class="" href="/admin/catalog/list?category=124"><span>Video</span></a></li>
						</ul>						
					</li>
					<li class="sub-menu <c:if test="${menu == 'news' }">active</c:if>">
						<a class="" href="javascript:void(0);"> <i class="fa fa-file-text"></i> <span>News & CSR</span><span class="menu-arrow arrow_carrot-right"></span></a>
						<ul class="sub">
							<li><a class="" href="/admin/news/list?category=111"><span>News</span></a></li>
							<li><a class="" href="/admin/news/list?category=112"><span>CSR</span></a></li>
						</ul>
					</li>
					<li class="<c:if test="${menu == 'event' }">active</c:if>">
						<a class="" href="/admin/event/list"> <i class="fa fa-calendar-o"></i> <span>Event</span></a>
					</li>
					<li class="<c:if test="${menu == 'career' }">active</c:if>">
						<a class="" href="/admin/career/list"> <i class="fa fa-graduation-cap"></i> <span>Career</span></a>
					</li>
					<li class="sub-menu <c:if test="${menu == 'term' }">active</c:if>">
						<a class="" href="javascript:void(0);"> <i class="fa fa-tags"></i> <span>Tag / Category</span><span class="menu-arrow arrow_carrot-right"></span></a>
						<ul class="sub">
							<li><a class="" href="/admin/term/list?taxonomy=cat"><span>Category</span></a></li>
							<li><a class="" href="/admin/term/list?taxonomy=tag"><span>Tag</span></a></li>
						</ul>
					</li>
					<li class="<c:if test="${menu == 'setting' }">active</c:if>">
						<a class="" href="/admin/setting/list"> <i class="fa fa-cog"></i> <span>Setting</span></a>
					</li>
				</ul>
				<!-- sidebar menu end-->
			</div>
		</aside>
		<!--sidebar end-->

		<!--main content start-->
		<section id="main-content">
			<section class="wrapper">
			
				<% //############################################################################################################################## %>				
				<tiles:insertAttribute name="body" />
				<% //############################################################################################################################## %>
				
			</section>
		</section>
		<!--main content end-->
	</section>
	<!-- container section start -->
	
	<!-- Alert -->
	<div class="modal fade" id="myAlert" tabindex="-1" role="dialog"></div>
	
	<!-- javascripts -->
	<script src="/resources/admin/js/jquery.js"></script>
	<script src="/resources/admin/js/jquery-ui-1.10.4.min.js"></script>
	<script src="/resources/admin/js/jquery-ui-1.9.2.custom.min.js"></script>
	<script src="/resources/admin/js/jquery.lazyload.min.js"></script>
	
	<!-- bootstrap -->
	<script src="/resources/admin/js/bootstrap.min.js"></script>
	<script src="/resources/admin/js/bootstrap-datepicker.js"></script>	

	<script src="/resources/admin/js/scripts.js"></script>
	<script src="/resources/admin/js/jquery.autosize.min.js"></script>
	<script src="/resources/admin/js/jquery.placeholder.min.js"></script>
	<script src="http://share.donreach.com/buttons.js"></script>
	
	<script>
		var myAlert = '' 
			+'<div class="modal-dialog">'
				+'<div class="modal-content">'
					+'<div class="modal-header">'
						+'<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>'
							+'<h4 class="modal-title" id="alertTitle"></h4>'
					+'</div>'
					+'<div class="modal-body">'
						+'<p id="alertMessage"></p>'
					+'</div>'
					+'<div class="modal-footer">'
						+'<button type="button" class="btn btn-default" id="noAction" data-dismiss="modal">No</button>'
						+'<button type="button" class="btn btn-primary" id="alertAction">Yes</button>'
					+'</div>'
				+'</div>'
			+'</div>';

		var showAlert = function(title, message, action, ret){
			$("#myAlert").html(myAlert);
			
			if(action){
				$("#alertAction").attr("onclick", "$('#myAlert').modal('hide');"+action);
				if(ret){
					$("#noAction").remove();
					$("#alertAction").attr("class", "btn btn-primary").html("OK");				
				}
			}else{
				$("#alertAction").remove();
				$("#noAction").attr("class", "btn btn-primary").html("OK");
			}
			
			$("#alertTitle").html(title);
			$("#alertMessage").html(message);

			$("#myAlert").modal("show");
		};

		$(document).ready(function(){
			$("img.lazy").lazyload({
			    effect : "fadeIn"			
			});
			
			$("form[name=searchForm], form[name=sortForm]").find("select:not(#condition), input").each(function(){
				if($(this).val() != ""){
					$(this).css("background-color", "#394A59");
					$(this).css("color", "#FFF");
				}else{
					$(this).css("background-color", "#FFF");					
					$(this).css("color", "#797979");
				}		
			});
		});
		
		var sort = function(){
			var tso = document.sortForm.tmpSortOrder.value;
			var tsc = document.sortForm.tmpSortColumn.value;
			
			if(tsc != "" && tso == ""){
				showAlert("Alert", "Please select sort column.", "document.sortForm['tmpSortColumn'].focus()", true);
				return;
			}
			if(tso !== "" && tsc == ""){
				showAlert("Alert", "Please select sort order.", "document.sortForm['tmpSortOrder'].focus()", true);
				return;
			}
			
			var frm = document.searchForm; 
			frm.sortColumn.value 	= tsc;  
			frm.sortOrder.value 	= tso;  
			frm.submit();    			
		};
		
		var showRows = function(){
			var ps = document.getElementById("tmpPageSize").value;
			
			var frm = document.searchForm; 
			frm.pageSize.value = ps;  
			frm.submit();    			
		}; 		
	</script>
	
	<% //############################################################################################################################## %>
		<tiles:insertAttribute name="js" />
	<% //############################################################################################################################## %>

</body>
</html>
