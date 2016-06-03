<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<tiles:insertDefinition name="_baseAdmin">
    
    <tiles:putAttribute name="body"> 
		<form name="settingForm" method="post" id="settingForm">
			<input type="hidden" name="seq"			value="<c:out value="${setting.seq}"/>"/>
			<input type="hidden" name="page"		value="<c:out value="${parameter.page}"/>"/>
			<input type="hidden" name="pageSize"	value="<c:out value="${parameter.pageSize}"/>"/>
			<input type="hidden" name="condition"	value="<c:out value="${parameter.condition}"/>"/>
			<input type="hidden" name="keyword"		value="<c:out value="${parameter.keyword}"/>"/>
		</form>
				
		<div class="row">
			<div class="col-lg-12">
				<h3 class="page-header">
					<i class="fa fa-cog"></i> Setting
				</h3>
				<ol class="breadcrumb">
					<li><i class="fa fa-home"></i><a href="/admin/dashboard">Home</a></li>
					<li><i class="fa fa-info-circle"></i>Setting Information</li>
				</ol>
			</div>
		</div>
		
		<div class="row">
			<div class="col-lg-12">
				<section class="panel">
					<header class="panel-heading">
		            	Setting Information
					</header>		
					<div class="panel-body">
						<h1></h1>
						<div class="form form-horizontal">
							<div class="form-group row">
								<label for="thumbnailImage" class="control-label col-lg-2">Website Logo <span class="required">*</span></label>
								<div class="col-lg-4">
									<div class="fileList">
										<c:if test="${not empty setting.thumbnailImageSeq}">
											<img class="lazy" data-original="/image/<c:out value="${setting.thumbnailImageSeq}"/>" width="200" />										
										</c:if>
										<c:if test="${empty setting.thumbnailImageSeq}">
											<p>-</p>									
										</c:if>
									</div>
								</div>
							</div>
							
							<div class="form-group row">
								<label for="mainImage" class="control-label col-lg-2">Background Image <span class="required">*</span></label>
								<div class="col-lg-4">
									<div class="fileList">
									<c:if test="${not empty setting.mainImageSeq}">
										<img class="lazy" data-original="/image/<c:out value="${setting.mainImageSeq}"/>" width="200" />
									</c:if>										
									<c:if test="${empty setting.mainImageSeq}">
										<p>-</p>
									</c:if>									
									</div>
								</div>
							</div>
		
							<div class="form-group row">
								<label for="startDate" class="control-label col-lg-2">Start Date <span class="required">*</span></label>
								<div class="col-lg-2">
									<p><h4 style="font-weight:bold"><fmt:formatDate value="${setting.startDate}" pattern="yyyy-MM-dd"/></h4></p>
								</div>
								<label for="endDate" class="control-label col-lg-2">End Date <span class="required">*</span></label>
								<div class="col-lg-2">
									<p><h4 style="font-weight:bold"><fmt:formatDate value="${setting.endDate}" pattern="yyyy-MM-dd"/></h4></p>
								</div>
							</div>

							<div class="form-group row">
								<label for="name" class="control-label col-lg-2">Title <span class="required">*</span></label>
								<div class="col-lg-10">
									<p><h4 style="font-weight:bold"><c:out value="${setting.title}"/></h4></p>
								</div>
							</div>
							
							<div class="form-group">
								<div class="col-lg-offset-2 col-lg-10">
									<button class="btn btn-success" type="button" onclick="top.location = '/admin/setting/edit?seq=<c:out value="${setting.seq}"/>&<c:out value="${parameter.parameterLink}" />'">Edit</button>
									<button class="btn btn-primary" type="button" onclick="top.location = '/admin/setting/list?<c:out value="${parameter.parameterLink}" />'">List</button>
									<button class="btn btn-danger" type="button" onclick="deleteItem();">Delete Setting</button>
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
				showAlert("Confirm item deletion", "Are you sure you want to delete this setting?", "deleteProc();");
			}
			var deleteProc = function(){
				var frm = document.settingForm;
			
				frm.encoding		= "multipart/form-data";
				frm.action			= "/admin/setting/delete";
				frm.submit();
			}	
		</script>
    </tiles:putAttribute>
    
</tiles:insertDefinition>
