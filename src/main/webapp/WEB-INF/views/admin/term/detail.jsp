<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<tiles:insertDefinition name="_baseAdmin">
    
    <tiles:putAttribute name="body"> 
		<form name="termForm" method="post" id="termForm">
			<input type="hidden" name="seq"			value="<c:out value="${term.seq}"/>"/>
			<input type="hidden" name="page"		value="<c:out value="${parameter.page}"/>"/>
			<input type="hidden" name="pageSize"	value="<c:out value="${parameter.pageSize}"/>"/>
			<input type="hidden" name="lineage"		value="<c:out value="${term.lineage}"/>"/>
			<input type="hidden" name="condition"	value="<c:out value="${parameter.condition}"/>"/>
			<input type="hidden" name="keyword"		value="<c:out value="${parameter.keyword}"/>"/>
			<input type="hidden" name="category"	value="<c:out value="${parameter.category}"/>"/>
			<input type="hidden" name="taxonomy"	value="<c:out value="${parameter.taxonomy}"/>"/>
		</form>
		
		<div class="row">
			<div class="col-lg-12">
				<h3 class="page-header">
					<i class="fa fa-tags"></i> Tag / Category
				</h3>
				<ol class="breadcrumb">
					<li><i class="fa fa-home"></i><a href="/admin/dashboard">Home</a></li>
					<li><i class="fa fa-info-circle"></i>Tag / Category Information</li>
				</ol>
			</div>
		</div>
		
		<div class="row">
			<div class="col-lg-12">
				<section class="panel">
					<header class="panel-heading">
		            	Term Information
					</header>		
					<div class="panel-body">
						<h1></h1>
						<div class="form form-horizontal">
							<div class="form-group row">
								<label for="name" class="control-label col-lg-2">Name <span class="required">*</span></label>
								<div class="col-lg-4">
									<p><h4 style="font-weight:bold"><c:out value="${term.name}"/></h4></p>
								</div>
							</div>
							
							<div class="form-group row">
								<label for="taxonomy" class="control-label col-lg-2">Tag / Category<span class="required">*</span></label>
								<div class="col-lg-4">
									<c:if test="${term.taxonomy == 'tag'}">Tag</c:if>
									<c:if test="${term.taxonomy == 'cat'}">Category</c:if>
								</div>
							</div>
							
							<c:if test="${term.taxonomy == 'cat'}">
							<div class="form-group row">
								<label for="parent" class="control-label col-lg-2">Full Path</label>
								<div class="col-lg-4">
									<c:set var="fullpath" value="${fn:split(term.fullPath, ',')}" />
									<c:set var="path" value="" />
									<c:set var="labelRef" value="" />
									<c:forEach var="i" items="${fullpath}" varStatus="status">
										<c:set var="j" value="${fn:split(i, '|')}" />
										<c:set var="labelRef" 
											value="<a href='/admin/term/detail?seq=
													${fn:escapeXml(j[0])}&
													${fn:escapeXml(parameter.parameterLink)}'>
														${fn:escapeXml(j[1])}
													</a>"
										/>
										<c:set var="path" value="${path} > ${labelRef}"/>
									</c:forEach>
									<c:set var="path" value="${fn:substringAfter(path, '>')}" />
									${path}
								</div>
							</div>
							</c:if>					
																	
							<div class="form-group row">
								<label for="about" class="control-label col-lg-2">Description</label>
								<div class="col-lg-10">
									<p><c:out value="${term.description}"/></p>								
								</div>
							</div>
							
							<div class="form-group">
								<div class="col-lg-offset-2 col-lg-10">
									<button class="btn btn-success" type="button" onclick="top.location = '/admin/term/edit?seq=<c:out value="${term.seq}"/>&<c:out value="${parameter.parameterLink}" />'">Edit</button>
									<button class="btn btn-primary" type="button" onclick="top.location = '/admin/term/list?<c:out value="${parameter.parameterLink}" />'">List</button>
									<c:if test="${term.fixYn != 'Y'}">
									<button class="btn btn-danger" type="button" onclick="deleteItem();">Delete Term</button>
									</c:if>
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
				showAlert("Confirm item deletion", "Are you sure you want to delete this term?", "deleteProc();");
			}
			var deleteProc = function(){
				var frm = document.termForm;
			
				frm.encoding		= "multipart/form-data";
				frm.action			= "/admin/term/delete";
				frm.submit();
			}	
		</script>
    </tiles:putAttribute>
    
</tiles:insertDefinition>
