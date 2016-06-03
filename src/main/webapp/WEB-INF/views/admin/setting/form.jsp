<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@	taglib uri="http://ewideplus.com" prefix="ew" %>

<tiles:insertDefinition name="_baseAdmin">
    
    <tiles:putAttribute name="body"> 
		<div class="row">
			<div class="col-lg-12">
				<h3 class="page-header">
					<i class="fa fa-cog"></i> Setting Management
				</h3>
				<ol class="breadcrumb">
					<li><i class="fa fa-home"></i><a href="/admin/dashboard">Home</a></li>
					<li><i class="fa fa-edit"></i>Setting Management</li>
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
						<div class="form">
							<form class="form-validate form-horizontal" id="settingForm" name="settingForm" method="post">
								<input type="hidden" name="seq"					value="<c:out value="${setting.seq}"/>"/>
								<input type="hidden" name="returnParam"			value="<c:out value="${parameter.returnParam}" />"/>
																
								<input type="hidden" name="deletedFiles" 		value=""/>
								
								<input type="hidden" name="thumbnailImageSeq" 	value="<c:out value="${setting.thumbnailImageSeq}"/>"/>								
								<input type="hidden" name="mainImageSeq" 		value="<c:out value="${setting.mainImageSeq}"/>"/>
								
								<div class="form-group row">
									<label for="thumbnailImage" class="control-label col-lg-2">Website Logo <span class="required">*</span></label>
									<div class="col-lg-4">
										<div class="fileList thumbnailImage">
											<c:if test="${not empty setting.thumbnailImageSeq}">
												<img class="lazy" data-original="/image/<c:out value="${setting.thumbnailImageSeq}"/>" width="200" />										
												<p class="help-block">
													<a href="/download/<c:out value="${setting.thumbnailImageSeq}"/>"/><span><c:out value="${setting.thumbnailImageOriginalName}"/></span></a>
													<a href="javascript:void(0);" onclick="deleteFile('<c:out value="${setting.thumbnailImageSeq}"/>', 'thumbnailImage');"><i class="alert-danger fa fa-remove"></i></a>
												</p>
											</c:if>							
											<c:if test="${empty setting.thumbnailImageSeq}">
												<p style="display:none"><img src="" id="imgthumbnailImage" alt="thumbnailImage"/></p>						
												<input type="file" id="thumbnailImage" name="thumbnailImage" title="file" class="file" required="true" />										
												<p class="help-block">*Only (GIF, JPG, PNG).</p>
											</c:if>									
										</div>
									</div>
								</div>
								<div class="form-group row">
									<label for="mainImage" class="control-label col-lg-2">Background Image <span class="required">*</span></label>
									<div class="col-lg-4">
										<div class="fileList mainImage">
											<c:if test="${not empty setting.mainImageSeq}">
												<img class="lazy" data-original="/image/<c:out value="${setting.mainImageSeq}"/>" width="200" />										
												<p class="help-block">
													<a href="/download/<c:out value="${setting.mainImageSeq}"/>"/><span><c:out value="${setting.mainImageOriginalName}"/></span></a>
													<a href="javascript:void(0);" onclick="deleteFile('<c:out value="${setting.mainImageSeq}"/>', 'mainImage');"><i class="alert-danger fa fa-remove"></i></a>
												</p>
											</c:if>							
											<c:if test="${empty setting.mainImageSeq}">
												<p style="display:none"><img src="" id="imgmainImage" alt="mainImage"/></p>						
												<input type="file" id="mainImage" name="mainImage" title="file" class="file"  required="true"/>										
												<p class="help-block">*Only (GIF, JPG, PNG).</p>
											</c:if>									
										</div>
									</div>
								</div>
								
								<div class="form-group row">
									<label for="startDate" class="control-label col-lg-2">Start Date <span class="required">*</span></label>
									<div class="col-lg-2">
										<input class="form-control" id="startDate" name="startDate" data-date-format="yyyy-mm-dd" minlength="4" maxlength="200" type="text" 
										required="true" value="<c:out value="${ew:formatDate(setting.startDate, 'yyy-MM-dd')}" />">
										<p class="help-block"></p>
									</div>
									<label for="endDate" class="control-label col-lg-2">End Date <span class="required">*</span></label>
									<div class="col-lg-2">
										<input class="form-control" id="endDate" name="endDate" data-date-format="yyyy-mm-dd" minlength="4" maxlength="200" type="text" 
										required="true" value="<c:out value="${ew:formatDate(setting.endDate, 'yyy-MM-dd')}" />">
										<p class="help-block"></p>
									</div>
								</div>
								
								
								<div class="form-group row">
									<label for="title" class="control-label col-lg-2">Title <span class="required">*</span></label>
									<div class="col-lg-4">
										<input class="form-control" id="title" name="title" minlength="4" maxlength="200" type="text" required="true" value="<c:out value="${setting.title}"/>">
										<p class="help-block"></p>
									</div>
								</div>
								
								<div class="form-group">
									<div class="col-lg-offset-2 col-lg-10">
										<input type="submit" name="submitHandler" style="display:none"/>
										<button class="btn btn-primary" type="button" onclick="saveProc();">Save</button>
										<button class="btn btn-default" type="button" onclick="top.location = '/admin/setting/list?<c:out value="${parameter.parameterLink}" />'">Cancel</button>
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
				var frm = document.settingForm;
		
				var imgs = ["thumbnailImage", "mainImage"];
				for(i in imgs){
					if(frm[imgs[i]] && frm[imgs[i]].value !== ""){
						if (frm[imgs[i]].value.match(/(.jpg|.jpeg|.gif|.png)$/i) === null){
							showAlert("Alert", "Only (.gif, .jpg, .png).", "document.settingForm['"+imgs[i]+"'].focus()", true);
							return;
						}
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
				var frm = document.settingForm;
		
				deletedFiles.push(fileSeq);
				frm.deletedFiles.value = deletedFiles.join();			
		
				var fc;
				if(id==="attachment"){
					fc	= '<input type="file" id="'+id+'" name="'+id+'" title="'+id+'" />';
					fc	+= '<p class="help-block">*Only (PDF, ZIP, RAR).</p>';		
				}else{
					fc	 = '<p style="display:none"><img src="" id="img'+id+'" alt="'+id+'"/></p>';
					fc	+= '<input type="file" id="'+id+'" name="'+id+'" title="'+id+'" required="true" />';
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
				$("#startDate").datepicker();
				$("#endDate").datepicker();
				
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
