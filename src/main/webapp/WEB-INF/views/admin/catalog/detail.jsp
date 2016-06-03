<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<tiles:insertDefinition name="_baseAdmin">
    
    <tiles:putAttribute name="body"> 
		<form name="catalogForm" method="post" id="catalogForm">
			<input type="hidden" name="seq"			value="<c:out value="${catalog.seq}"/>"/>
			<input type="hidden" name="page"		value="<c:out value="${parameter.page}"/>"/>
			<input type="hidden" name="pageSize"	value="<c:out value="${parameter.pageSize}"/>"/>
			<input type="hidden" name="condition"	value="<c:out value="${parameter.condition}"/>"/>
			<input type="hidden" name="keyword"		value="<c:out value="${parameter.keyword}"/>"/>
			<input type="hidden" name="category"	value="<c:out value="${parameter.category}"/>"/>
		</form>
				
		<div class="row">
			<div class="col-lg-12">
				<h3 class="page-header">
					<i class="fa fa-download"></i> Catalog
				</h3>
				<ol class="breadcrumb">
					<li><i class="fa fa-home"></i><a href="/admin/dashboard">Home</a></li>
					<li><i class="fa fa-info-circle"></i>Catalog Information</li>
				</ol>
			</div>
		</div>
		
		<div class="row">
			<div class="col-lg-12">
				<section class="panel">
					<header class="panel-heading">
		            	Catalog Information
					</header>		
					<div class="panel-body">
						<h1></h1>
						<div class="form form-horizontal">
							<div class="form-group row">
								<label for="thumbnailImage" class="control-label col-lg-2">Thumbnail Image</label>
								<div class="col-lg-4">
									<div class="fileList">
										<c:if test="${not empty catalog.thumbnailImageSeq}">
											<img class="lazy" data-original="/image/<c:out value="${catalog.thumbnailImageSeq}"/>" width="200" />										
										</c:if>
										<c:if test="${empty catalog.thumbnailImageSeq}">
											<p>-</p>									
										</c:if>
									</div>
								</div>
							</div>
							
							<div class="form-group row">
								<label for="mainImage" class="control-label col-lg-2">Main Image</label>
								<div class="col-lg-4">
									<div class="fileList">
									<c:if test="${not empty catalog.mainImageSeq}">
										<img class="lazy" data-original="/image/<c:out value="${catalog.mainImageSeq}"/>" width="200" />
									</c:if>										
									<c:if test="${empty catalog.mainImageSeq}">
										<p>-</p>
									</c:if>									
									</div>
								</div>
							</div>
		
							<div class="form-group row">
								<label for="name" class="control-label col-lg-2">Name <span class="required">*</span></label>
								<div class="col-lg-10">
									<p><h4 style="font-weight:bold"><c:out value="${catalog.name}"/></h4></p>
								</div>
							</div>
							
							<div class="form-group row">
								<label for="name" class="control-label col-lg-2">Category <span class="required">*</span></label>
								<div class="col-lg-4">
									<p><h5 style="font-weight:bold"><c:out value="${catalog.categoryName}"/></h5></p>
								</div>
							</div>
							
							<div class="form-group row">
								<label for="about" class="control-label col-lg-2">Description</label>
								<div class="col-lg-10">
									<p>${catalog.description}</p>								
								</div>
							</div>
							
							<div class="form-group row">
								<label for="tags" class="control-label col-lg-2">Tags</label>
								<div class="col-lg-4">
									<c:set var="csv" value=""/>
									<c:set var="arrTags" value="${fn:split(catalog.tags, ',')}" />												
									<c:forEach var="i" items="${arrTags}" varStatus="iStatus">
										<c:set var="arrPairs" value="${fn:split(i, '|')}" />
										<c:set var="csv" value="${csv}, <strong>${fn:escapeXml(arrPairs[1])}</strong>"/>																						
									</c:forEach> 								
									<c:set var="csv" value="${fn:substringAfter(csv, ',')}" />
									${csv}
									<p class="help-block"></p>
								</div>
							</div>						
							
							<div class="form-group row">
								<label for="attachment" class="control-label col-lg-2">Attachment</label>
								<div class="col-lg-4">
									<div class="fileList">
										<c:if test="${not empty catalog.attachmentSeq}">
											<p class="help-block">											
												<a href="/download/<c:out value="${catalog.attachmentSeq}"/>"/><span><c:out value="${catalog.attachmentOriginalName}"/></span></a>										
											</p>
										</c:if>							
										<c:if test="${empty catalog.attachmentSeq}">
											<p>-</p>									
										</c:if>									
									</div>
								</div>
							</div>
							
							<div class="form-group">
								<div class="col-lg-offset-2 col-lg-10">
									<button class="btn btn-success" type="button" onclick="top.location = '/admin/catalog/edit?seq=<c:out value="${catalog.seq}"/>&<c:out value="${parameter.parameterLink}" />'">Edit</button>
									<button class="btn btn-primary" type="button" onclick="top.location = '/admin/catalog/list?<c:out value="${parameter.parameterLink}" />'">List</button>
									<button class="btn btn-danger" type="button" onclick="deleteItem();">Delete Catalog</button>
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
				showAlert("Confirm item deletion", "Are you sure you want to delete this catalog?", "deleteProc();");
			}
			var deleteProc = function(){
				var frm = document.catalogForm;
			
				frm.encoding		= "multipart/form-data";
				frm.action			= "/admin/catalog/delete";
				frm.submit();
			}	
		</script>
    </tiles:putAttribute>
    
</tiles:insertDefinition>
