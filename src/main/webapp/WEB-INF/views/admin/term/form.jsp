<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<tiles:insertDefinition name="_baseAdmin">
    
    <tiles:putAttribute name="body"> 
		<div class="row">
			<div class="col-lg-12">
				<h3 class="page-header">
					<i class="fa fa-tags"></i> Tag / Category Management
				</h3>
				<ol class="breadcrumb">
					<li><i class="fa fa-home"></i><a href="/admin/dashboard">Home</a></li>
					<li><i class="fa fa-edit"></i>Tag / Category Management</li>
				</ol>
			</div>
		</div>
		
		<div class="row">
			<div class="col-lg-12">
				<section class="panel">
					<header class="panel-heading">
		            	Tag / Category Information
					</header>		
					<div class="panel-body">
						<h1></h1>
						<div class="form">
							<form class="form-validate form-horizontal" id="termForm" name="termForm" method="post">
								<input type="hidden" name="seq"			value="<c:out value="${term.seq}"/>"/>
								<input type="hidden" name="returnParam"	value="<c:out value="${parameter.returnParam}" />"/>
								
								<c:if test="${action == 'edit'}">
									<input type="hidden" name="parent"		value="<c:out value="${term.parent}"/>"/>
									<input type="hidden" name="taxonomy"	value="<c:out value="${term.taxonomy}"/>"/>
								</c:if>
								
								<div class="form-group row">
									<label for="taxonomy" class="control-label col-lg-2">Tag / Category<span class="required">*</span></label>
									<div class="col-lg-4">
									<c:if test="${action == 'write'}">
										<select class="form-control" id="taxonomy" name="taxonomy" required>
											<option value="">Select</option>
											<option value="tag" <c:if test="${parameter.taxonomy == 'tag'}">selected</c:if>>Tag</option>
											<option value="cat" <c:if test="${parameter.taxonomy == 'cat'}">selected</c:if>>Category</option>
										</select>
										<p class="help-block"></p>
									</c:if>
									<c:if test="${action == 'edit'}">
										<c:if test="${term.taxonomy == 'tag'}">Tag</c:if>
										<c:if test="${term.taxonomy == 'cat'}">Category</c:if>
									</c:if>
									</div>
								</div>
								
								<div class="form-group row">
									<label for="name" class="control-label col-lg-2">Name <span class="required">*</span></label>
									<div class="col-lg-4">
										<input class="form-control" id="name" name="name" minlength="4" maxlength="200" type="text" required="true" value="<c:out value="${term.name}"/>">
										<p class="help-block"></p>
									</div>
								</div>
								
								<c:if test="${action == 'write' && parameter.taxonomy == 'cat'}">
								<div class="form-group row">
									<label for="parent" class="control-label col-lg-2">Parent</label>
									<div class="col-lg-4">
										<select class="form-control" id="parent" name="parent">
											<option value="0">-</option>
											<c:forEach var="item" items="${category}" varStatus="status">
												<option value="<c:out value="${item.seq}"/>" <c:if test="${item.seq == term.parent}">selected</c:if>>
													<c:out value="${item.padding}"/> <c:out value="${item.name}"/>
												</option>
											</c:forEach>
										</select>
										<p class="help-block"></p>
									</div>
								</div>
								</c:if>
								
								<c:if test="${action == 'edit' && term.taxonomy == 'cat'}">
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
									<label for="description" class="control-label col-lg-2">Description</label>
									<div class="col-lg-10">
										<textarea class="form-control" id="description" name="description" rows="10"><c:out value="${term.description}"/></textarea>								
										<p class="help-block"></p>
									</div>
								</div>
								
								<div class="form-group">
									<div class="col-lg-offset-2 col-lg-10">
										<input type="submit" name="submitHandler" style="display:none"/>
										<button class="btn btn-primary" type="button" onclick="saveProc();">Save</button>
										<button class="btn btn-default" type="button" onclick="top.location = '/admin/term/list?<c:out value="${parameter.parameterLink}" />'">Cancel</button>
									</div>
								</div>
								
							</form>
						</div>
		
					</div>
				</section>
			</div>
		</div>
    </tiles:putAttribute>
    
    <tiles:putAttribute name="js">
		<script>	
	var saveProc = function(){
		var frm = document.termForm;

		frm.encoding		= "multipart/form-data";
		frm.submitHandler.click();
	}	

	$(document).ready(function(){
		<c:forEach var="item" items="${errors}" varStatus="status">
			$("#<c:out value="${item.key}"/>").parent().addClass("has-error");
			$("#<c:out value="${item.key}"/>").parent().find(".help-block").html("<c:out value="${item.value}"/>");
		</c:forEach>
	});	
</script>
    </tiles:putAttribute>
    
</tiles:insertDefinition>
