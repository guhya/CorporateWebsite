<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<tiles:insertDefinition name="_baseAdmin">
    
    <tiles:putAttribute name="body"> 
		<form name="userForm" method="post" id="userForm">
			<input type="hidden" name="username"	value="<c:out value="${user.username}"/>"/>
			<input type="hidden" name="page"		value="<c:out value="${parameter.page}"/>"/>
			<input type="hidden" name="pageSize"	value="<c:out value="${parameter.pageSize}"/>"/>
			<input type="hidden" name="condition"	value="<c:out value="${parameter.condition}"/>"/>
			<input type="hidden" name="keyword"		value="<c:out value="${parameter.keyword}"/>"/>
		</form>
				
		<div class="row">
			<div class="col-lg-12">
				<h3 class="page-header">
					<i class="fa fa-user"></i> User
				</h3>
				<ol class="breadcrumb">
					<li><i class="fa fa-home"></i><a href="/admin/dashboard">Home</a></li>
					<li><i class="fa fa-info-circle"></i>User Information</li>
				</ol>
			</div>
		</div>
		
		<div class="row">
			<div class="col-lg-12">
				<section class="panel">
					<header class="panel-heading">
		            	User Information
					</header>		
					<div class="panel-body">
						<h1></h1>
						<div class="form form-horizontal">
							<div class="form-group row">
								<label for="thumbnailImage" class="control-label col-lg-2">Thumbnail Image</label>
								<div class="col-lg-4">
									<div class="fileList">
										<c:if test="${not empty user.thumbnailImageSeq}">
											<img class="lazy" data-original="/image/<c:out value="${user.thumbnailImageSeq}"/>" width="200" />										
										</c:if>
										<c:if test="${empty user.thumbnailImageSeq}">
											<p>-</p>									
										</c:if>
									</div>
								</div>
							</div>
							
							<div class="form-group row">
								<label for="username" class="control-label col-lg-2">Username <span class="required">*</span></label>
								<div class="col-lg-4">
									<p><h4 style="font-weight:bold"><c:out value="${user.username}"/></h4></p>
								</div>
							</div>
							
							<div class="form-group row">
								<label for="firstName" class="control-label col-lg-2">First Name</label>
								<div class="col-lg-4">
									<p><c:out value="${user.firstName}"/></p>
								</div>
								<label for="lastName" class="control-label col-lg-2">Last Name</label>
								<div class="col-lg-4">
									<p><c:out value="${user.lastName}"/></p>								
								</div>
							</div>
							
							<div class="form-group row">
								<label for="email" class="control-label col-lg-2">Email</label>
								<div class="col-lg-10">
									<p><c:out value="${user.email}"/></p>
								</div>
							</div>
							
							<div class="form-group">
								<div class="col-lg-offset-2 col-lg-10">
									<button class="btn btn-success" type="button" onclick="top.location = '/admin/user/edit?username=<c:out value="${user.username}"/>&<c:out value="${parameter.parameterLink}" />'">Edit</button>
									<button class="btn btn-primary" type="button" onclick="top.location = '/admin/user/list?<c:out value="${parameter.parameterLink}" />'">List</button>
									<button class="btn btn-danger" type="button" onclick="deleteItem();">Delete User</button>
								</div>
							</div>
								
						</div>
		
					</div>
				</section>
			</div>
		</div>
    </tiles:putAttribute>
    
    <tiles:putAttribute name="js">
		<script>
			var deleteItem = function(){
				showAlert("Confirm item deletion", "Are you sure you want to delete this user?", "deleteProc();");
			}
			var deleteProc = function(){
				var frm = document.userForm;
			
				frm.encoding		= "multipart/form-data";
				frm.action			= "/admin/user/delete";
				frm.submit();
			}	
		</script>
    </tiles:putAttribute>
    
</tiles:insertDefinition>
