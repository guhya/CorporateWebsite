<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<tiles:insertDefinition name="_baseAdmin">
    
    <tiles:putAttribute name="body"> 
		<form name="careerForm" method="post" id="careerForm">
			<input type="hidden" name="seq"			value="<c:out value="${career.seq}"/>"/>
			<input type="hidden" name="page"		value="<c:out value="${parameter.page}"/>"/>
			<input type="hidden" name="pageSize"	value="<c:out value="${parameter.pageSize}"/>"/>
			<input type="hidden" name="condition"	value="<c:out value="${parameter.condition}"/>"/>
			<input type="hidden" name="keyword"		value="<c:out value="${parameter.keyword}"/>"/>
			<input type="hidden" name="category"	value="<c:out value="${parameter.category}"/>"/>
		</form>
				
		<div class="row">
			<div class="col-lg-12">
				<h3 class="page-header">
					<i class="fa fa-graduation-cap"></i> Career
				</h3>
				<ol class="breadcrumb">
					<li><i class="fa fa-home"></i><a href="/admin/dashboard">Home</a></li>
					<li><i class="fa fa-info-circle"></i>Career Information</li>
				</ol>
			</div>
		</div>
		
		<div class="row">
			<div class="col-lg-12">
				<section class="panel">
					<header class="panel-heading">
		            	Career Information
					</header>		
					<div class="panel-body">
						<h1></h1>
						<div class="form form-horizontal">
							<div class="form-group row">
								<label for="thumbnailImage" class="control-label col-lg-2">Thumbnail Image</label>
								<div class="col-lg-4">
									<div class="fileList">
										<c:if test="${not empty career.thumbnailImageSeq}">
											<img class="lazy" data-original="/image/<c:out value="${career.thumbnailImageSeq}"/>" width="200" />										
										</c:if>
										<c:if test="${empty career.thumbnailImageSeq}">
											<p>-</p>									
										</c:if>
									</div>
								</div>
							</div>
							
							<div class="form-group row">
								<label for="mainImage" class="control-label col-lg-2">Main Image</label>
								<div class="col-lg-4">
									<div class="fileList">
									<c:if test="${not empty career.mainImageSeq}">
										<img class="lazy" data-original="/image/<c:out value="${career.mainImageSeq}"/>" width="200" />
									</c:if>										
									<c:if test="${empty career.mainImageSeq}">
										<p>-</p>
									</c:if>									
									</div>
								</div>
							</div>
		
							<div class="form-group row">
								<label for="startDate" class="control-label col-lg-2">Start Date <span class="required">*</span></label>
								<div class="col-lg-2">
									<p><h4 style="font-weight:bold"><fmt:formatDate value="${career.startDate}" pattern="yyyy-MM-dd"/></h4></p>
								</div>
								<label for="endDate" class="control-label col-lg-2">End Date <span class="required">*</span></label>
								<div class="col-lg-2">
									<p><h4 style="font-weight:bold"><fmt:formatDate value="${career.endDate}" pattern="yyyy-MM-dd"/></h4></p>
								</div>
							</div>

							<div class="form-group row">
								<label for="name" class="control-label col-lg-2">Title <span class="required">*</span></label>
								<div class="col-lg-10">
									<p><h4 style="font-weight:bold"><c:out value="${career.title}"/></h4></p>
								</div>
							</div>
							
							<div class="form-group row">
								<label for="name" class="control-label col-lg-2">Category <span class="required">*</span></label>
								<div class="col-lg-4">
									<p><h5 style="font-weight:bold"><c:out value="${career.categoryName}"/></h5></p>
								</div>
							</div>
							
							<div class="form-group row">
								<label for="about" class="control-label col-lg-2">Content</label>
								<div class="col-lg-10">
									<p>${career.content}</p>								
								</div>
							</div>
							
							<div class="form-group row">
								<label for="tags" class="control-label col-lg-2">Tags</label>
								<div class="col-lg-4">
									<c:set var="csv" value=""/>
									<c:set var="arrTags" value="${fn:split(career.tags, ',')}" />												
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
										<c:if test="${not empty career.attachmentSeq}">
											<p class="help-block">											
												<a href="/download/<c:out value="${career.attachmentSeq}"/>"/><span><c:out value="${career.attachmentOriginalName}"/></span></a>										
											</p>
										</c:if>							
										<c:if test="${empty career.attachmentSeq}">
											<p>-</p>									
										</c:if>									
									</div>
								</div>
							</div>
							
							<div class="form-group">
								<div class="col-lg-offset-2 col-lg-10">
									<button class="btn btn-success" type="button" onclick="top.location = '/admin/career/edit?seq=<c:out value="${career.seq}"/>&<c:out value="${parameter.parameterLink}" />'">Edit</button>
									<button class="btn btn-primary" type="button" onclick="top.location = '/admin/career/list?<c:out value="${parameter.parameterLink}" />'">List</button>
									<button class="btn btn-danger" type="button" onclick="deleteItem();">Delete Career</button>
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
				showAlert("Confirm item deletion", "Are you sure you want to delete this career?", "deleteProc();");
			}
			var deleteProc = function(){
				var frm = document.careerForm;
			
				frm.encoding		= "multipart/form-data";
				frm.action			= "/admin/career/delete";
				frm.submit();
			}	
		</script>
    </tiles:putAttribute>
    
</tiles:insertDefinition>
