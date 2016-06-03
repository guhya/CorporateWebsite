<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<tiles:insertDefinition name="_baseAdmin">
    
    <tiles:putAttribute name="body"> 
		<div class="row">
			<div class="col-lg-12">
				<h3 class="page-header">
					<i class="fa fa-download"></i> Catalog Management
				</h3>
				<ol class="breadcrumb">
					<li><i class="fa fa-home"></i><a href="/admin/dashboard">Home</a></li>
					<li><i class="fa fa-edit"></i>Catalog Management</li>
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
						<div class="form">
							<form class="form-validate form-horizontal" id="catalogForm" name="catalogForm" method="post">
								<input type="hidden" name="seq"					value="<c:out value="${catalog.seq}"/>"/>
								<input type="hidden" name="returnParam"			value="<c:out value="${parameter.returnParam}" />"/>
																
								<input type="hidden" name="deletedFiles" 		value=""/>
								
								<input type="hidden" name="thumbnailImageSeq" 	value="<c:out value="${catalog.thumbnailImageSeq}"/>"/>								
								<input type="hidden" name="mainImageSeq" 		value="<c:out value="${catalog.mainImageSeq}"/>"/>
								<input type="hidden" name="attachmentSeq" 		value="<c:out value="${catalog.attachmentSeq}"/>"/>
								
								<div class="form-group row">
									<label for="thumbnailImage" class="control-label col-lg-2">Thumbnail Image</label>
									<div class="col-lg-4">
										<div class="fileList thumbnailImage">
											<c:if test="${not empty catalog.thumbnailImageSeq}">
												<img class="lazy" data-original="/image/<c:out value="${catalog.thumbnailImageSeq}"/>" width="200" />										
												<p class="help-block">
													<a href="/download/<c:out value="${catalog.thumbnailImageSeq}"/>"/><span><c:out value="${catalog.thumbnailImageOriginalName}"/></span></a>
													<a href="javascript:void(0);" onclick="deleteFile('<c:out value="${catalog.thumbnailImageSeq}"/>', 'thumbnailImage');"><i class="alert-danger fa fa-remove"></i></a>
												</p>
											</c:if>							
											<c:if test="${empty catalog.thumbnailImageSeq}">
												<p style="display:none"><img src="" id="imgthumbnailImage" alt="thumbnailImage"/></p>						
												<input type="file" id="thumbnailImage" name="thumbnailImage" title="file" class="file" />										
												<p class="help-block">*Only (GIF, JPG, PNG).</p>
											</c:if>									
										</div>
									</div>
								</div>
								<div class="form-group row">
									<label for="mainImage" class="control-label col-lg-2">Main Image</label>
									<div class="col-lg-4">
										<div class="fileList mainImage">
											<c:if test="${not empty catalog.mainImageSeq}">
												<img class="lazy" data-original="/image/<c:out value="${catalog.mainImageSeq}"/>" width="200" />										
												<p class="help-block">
													<a href="/download/<c:out value="${catalog.mainImageSeq}"/>"/><span><c:out value="${catalog.mainImageOriginalName}"/></span></a>
													<a href="javascript:void(0);" onclick="deleteFile('<c:out value="${catalog.mainImageSeq}"/>', 'mainImage');"><i class="alert-danger fa fa-remove"></i></a>
												</p>
											</c:if>							
											<c:if test="${empty catalog.mainImageSeq}">
												<p style="display:none"><img src="" id="imgmainImage" alt="mainImage"/></p>						
												<input type="file" id="mainImage" name="mainImage" title="file" class="file" />										
												<p class="help-block">*Only (GIF, JPG, PNG).</p>
											</c:if>									
										</div>
									</div>
								</div>
								
								<div class="form-group row">
									<label for="name" class="control-label col-lg-2">Name <span class="required">*</span></label>
									<div class="col-lg-4">
										<input class="form-control" id="name" name="name" minlength="4" maxlength="200" type="text" required="true" value="<c:out value="${catalog.name}"/>">
										<p class="help-block"></p>
									</div>
								</div>
								
								<div class="form-group row">
									<label for="categories" class="control-label col-lg-2">Category <span class="required">*</span></label>
									<div class="col-lg-4">
										<select class="form-control" id="category" name="category" required="true">
											<option value="">Select Category</option>
											<c:forEach var="item" items="${category}" varStatus="status">
												<option value="<c:out value="${item.seq}"/>" <c:if test="${item.seq == catalog.category}">selected</c:if>>
													<c:out value="${item.padding}"/> <c:out value="${item.name}"/>
												</option>
											</c:forEach>
										</select>
										<p class="help-block"></p>
									</div>
								</div>
								
								<div class="form-group row">
									<label for="about" class="control-label col-lg-2">Description</label>
									<div class="col-lg-10">
										<textarea class="form-control" id="description" name="description" rows="10"><c:out value="${catalog.description}"/></textarea>								
										<p class="help-block"></p>
									</div>
								</div>
								
								<div class="form-group row">
									<label for="tags" class="control-label col-lg-2">Tags</label>
									<div class="col-lg-10">
										<c:forEach var="item" items="${tag}" varStatus="status">											
											<input type="checkbox" id="tags_${status.index}" name="tags" value="<c:out value="${item.seq}"/>"
												<c:set var="arrTags" value="${fn:split(catalog.tags, ',')}" />												
												<c:forEach var="i" items="${arrTags}" varStatus="iStatus">
													<c:set var="arrPairs" value="${fn:split(i, '|')}" />												
													<c:if test="${item.seq == arrPairs[0]}">checked</c:if>
												</c:forEach> 
											>
											<label for="tags_${status.index}"><c:out value="${item.name}"/></label>&nbsp;&nbsp;
										</c:forEach>
										<p class="help-block"></p>
									</div>
								</div>						
								
									
								<div class="form-group row">
									<label for="attachment" class="control-label col-lg-2">Attachment</label>
									<div class="col-lg-4">
										<div class="fileList attachment">
											<c:if test="${not empty catalog.attachmentSeq}">
												<p class="help-block">
													<a href="/download/<c:out value="${catalog.attachmentSeq}"/>"/><span><c:out value="${catalog.attachmentOriginalName}"/>"</span></a>
													<a href="javascript:void(0);" onclick="deleteFile('<c:out value="${catalog.attachmentSeq}"/>', 'attachment');"><i class="alert-danger fa fa-remove"></i></a>
												</p>
											</c:if>							
											<c:if test="${empty catalog.attachmentSeq}">
												<input type="file" id="attachment" name="attachment" title="file" class="file" />										
												<p class="help-block">*Only (PDF, ZIP, RAR).</p>
											</c:if>									
										</div>
									</div>
								</div>
								
								<div class="form-group">
									<div class="col-lg-offset-2 col-lg-10">
										<input type="submit" name="submitHandler" style="display:none"/>
										<button class="btn btn-primary" type="button" onclick="saveProc();">Save</button>
										<button class="btn btn-default" type="button" onclick="top.location = '/admin/catalog/list?<c:out value="${parameter.parameterLink}" />'">Cancel</button>
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
		<script src="/resources/admin/assets/ckeditor/ckeditor.js"></script>
		<script>	
			var saveProc = function(){
				var frm = document.catalogForm;
		
				var imgs = ["thumbnailImage", "mainImage"];
				for(i in imgs){
					if(frm[imgs[i]] && frm[imgs[i]].value !== ""){
						if (frm[imgs[i]].value.match(/(.jpg|.jpeg|.gif|.png)$/i) === null){
							showAlert("Alert", "Only (.gif, .jpg, .png).", "document.catalogForm['"+imgs[i]+"'].focus()", true);
							return;
						}
					}
				}
				
				if(frm.attachment && frm.attachment.value !== ""){
					if (frm.attachment.value.match(/(.pdf|.zip|.rar)$/i) === null){
						showAlert("Alert", "Only (.pdf, .zip, .rar).", "document.catalogForm['attachment'].focus()", true);
						return;
					}
				}
							
				frm.encoding		= "multipart/form-data";
				frm.submitHandler.click();
			}	
		
			var deletedFiles = [];
			var deleteFile = function(fileSeq, id){
				showAlert("Confirm file deletion", "Are you sure you want to delete this file?", "deleteFileProc('"+fileSeq+"', '"+id+"');");
			}
			var deleteFileProc = function(fileSeq, id){
				console.log("delete called");
				var frm = document.catalogForm;
		
				deletedFiles.push(fileSeq);
				frm.deletedFiles.value = deletedFiles.join();			
		
				var fc;
				if(id==="attachment"){
					fc	= '<input type="file" id="'+id+'" name="'+id+'" title="'+id+'" />';
					fc	+= '<p class="help-block">*Only (PDF, ZIP, RAR).</p>';		
				}else{
					fc	 = '<p style="display:none"><img src="" id="img'+id+'" alt="'+id+'"/></p>';
					fc	+= '<input type="file" id="'+id+'" name="'+id+'" title="'+id+'" />';
					fc	+= '<p class="help-block">*Only (GIF, JPG, PNG).</p>';
				}
		
				$(".fileList."+id).html(fc);
			}
			
			var readURL = function(input, id) {
		
				if (id==undefined) return;
				console.log("called");
				
			    if (input.files && input.files[0]) {
			        var reader = new FileReader();
			        reader.onload = function (e) {
			            $("#img"+id).attr("src", e.target.result);
			            $("#img"+id).attr("style", "max-width: 200px;");
			            $("#img"+id).parent().fadeIn();
			        }
			        reader.readAsDataURL(input.files[0]);
			    }
			}
		
			$(document).ready(function(){
				CKEDITOR.replace("description");
				$(".fileList").on("change", "#thumbnailImage, #mainImage", function(){
					var id = $(this).attr("id");
				    readURL(this, id);
				});
				
				<c:forEach var="item" items="${errors}" varStatus="status">
					$("#<c:out value="${item.key}"/>").parent().addClass("has-error");
					$("#<c:out value="${item.key}"/>").parent().find(".help-block").html("<c:out value="${item.value}"/>");
				</c:forEach>
			});	
		</script>
    </tiles:putAttribute>
    
</tiles:insertDefinition>
