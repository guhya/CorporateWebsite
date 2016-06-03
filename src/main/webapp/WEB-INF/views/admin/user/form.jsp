<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<tiles:insertDefinition name="_baseAdmin">
    
    <tiles:putAttribute name="body"> 
		<div class="row">
			<div class="col-lg-12">
				<h3 class="page-header">
					<i class="fa fa-user"></i> User Management
				</h3>
				<ol class="breadcrumb">
					<li><i class="fa fa-home"></i><a href="/admin/dashboard">Home</a></li>
					<li><i class="fa fa-edit"></i>User Management</li>
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
						<div class="form">
							<form class="form-validate form-horizontal" id="userForm" name="userForm" method="post">
								<input type="hidden" name="seq"					value="<c:out value="${user.seq}"/>"/>
								<input type="hidden" name="returnParam"			value="<c:out value="${parameter.returnParam}" />"/>
								
								<input type="hidden" name="deletedFiles" 		value=""/>
								<input type="hidden" name="thumbnailImageSeq" 	value="<c:out value="${user.thumbnailImageSeq}"/>"/>
								
								<div class="form-group row">
									<label for="thumbnailImage" class="control-label col-lg-2">Avatar</label>
									<div class="col-lg-4">
										<div class="fileList thumbnailImage">
											<c:if test="${not empty user.thumbnailImageSeq}">
												<img class="lazy" data-original="/image/<c:out value="${user.thumbnailImageSeq}"/>" width="200" />										
												<p class="help-block">
													<a href="/download/<c:out value="${user.thumbnailImageSeq}"/>"/><span><c:out value="${user.thumbnailImageOriginalName}"/></span></a>
													<a href="javascript:void(0);" onclick="deleteFile('<c:out value="${user.thumbnailImageSeq}"/>', 'thumbnailImage');"><i class="alert-danger fa fa-remove"></i></a>
												</p>
											</c:if>							
											<c:if test="${empty user.thumbnailImageSeq}">
												<p style="display:none"><img src="" id="imgthumbnailImage" alt="thumbnailImage"/></p>						
												<input type="file" id="thumbnailImage" name="thumbnailImage" title="file" class="file" />										
												<p class="help-block">*Only (GIF, JPG, PNG).</p>
											</c:if>									
										</div>
									</div>
								</div>
								<div class="form-group row">
									<label for="username" class="control-label col-lg-2">Username <span class="required">*</span></label>
									<div class="col-lg-4">
									<c:if test="${action == 'write'}">
										<input class="form-control" id="username" name="username" minlength="4" maxlength="200" type="text" required="true" value="<c:out value="${user.username}"/>">
										<p class="help-block"></p>
									</c:if>
									<c:if test="${action == 'edit'}">
										<p><h4 style="font-weight:bold"><c:out value="${user.username}"/></h4></p>
										<input type="hidden" name="username"	value="<c:out value="${user.username}"/>"/>
									</c:if>
									</div>
								</div>
								
								<c:if test="${action == 'edit'}">
								<div class="form-group row">
									<label for="oldPassword" class="control-label col-lg-2">Old Password <span class="required">*</span></label>
									<div class="col-lg-4">
										<input class="form-control" id="oldPassword" name="oldPassword" minlength="4" maxlength="200" type="password" required="true">
										<p class="help-block"></p>
									</div>
								</div>
								</c:if>
								
								<div class="form-group row">
									<label for="password" class="control-label col-lg-2">Password <span class="required">*</span></label>
									<div class="col-lg-4">
										<input class="form-control" id="password" name="password" minlength="4" maxlength="200" type="password" required="true">
										<p class="help-block"></p>
									</div>
									<label for="rePassword" class="control-label col-lg-2">Re-Password <span class="required">*</span></label>
									<div class="col-lg-4">
										<input class="form-control" id="rePassword" name="rePassword" minlength="4" maxlength="200" type="password" required="true">
										<p class="help-block"></p>
									</div>
								</div>
								
								<div class="form-group row">
									<label for="firstName" class="control-label col-lg-2">First Name</label>
									<div class="col-lg-4">
										<input class="form-control" id="firstName" name="firstName" minlength="4" maxlength="200" type="text" value="<c:out value="${user.firstName}"/>">
										<p class="help-block"></p>
									</div>
									<label for="lastName" class="control-label col-lg-2">Last Name</label>
									<div class="col-lg-4">
										<input class="form-control" id="lastName" name="lastName" minlength="4" maxlength="200" type="text" value="<c:out value="${user.lastName}"/>">
										<p class="help-block"></p>
									</div>
								</div>
								
								<div class="form-group row">
									<label for="email" class="control-label col-lg-2">Email</label>
									<div class="col-lg-10">
										<input class="form-control" id="email" name="email" minlength="4" maxlength="200" type="text" value="<c:out value="${user.email}"/>">
										<p class="help-block"></p>
									</div>
								</div>
								
								<div class="form-group">
									<div class="col-lg-offset-2 col-lg-10">
										<input type="submit" name="submitHandler" style="display:none"/>
										<button class="btn btn-primary" type="button" onclick="saveProc();">Save</button>
										<button class="btn btn-default" type="button" onclick="top.location = '/admin/user/list?<c:out value="${parameter.parameterLink}" />'">Cancel</button>
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
				var frm = document.userForm;
		
				if(frm.password.value !== frm.rePassword.value){
					showAlert("Alert", "Re-Password mismatch.", "document.userForm.rePassword.focus()", true);
					return;
				}
				
				if (frm.email.value !== "" && !validateEmail(frm.email.value)){
					showAlert("Alert", "Incorrect email format.", "document.userForm.email.focus()", true);
					return;
				}
				
				var imgs = ["thumbnailImage"];
				for(i in imgs){
					if(frm[imgs[i]] && frm[imgs[i]].value !== ""){
						if (frm[imgs[i]].value.match(/(.jpg|.jpeg|.gif|.png)$/i) === null){
							showAlert("Alert", "Only (.gif, .jpg, .png).", "document.userForm['"+imgs[i]+"'].focus()", true);
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
				var frm = document.userForm;
		
				deletedFiles.push(fileSeq);
				frm.deletedFiles.value = deletedFiles.join();			
		
				var fc;
				if(id==="brochure"){
					fc	= '<input type="file" id="'+id+'" name="'+id+'" title="'+id+'" />';
					fc	+= '<p class="help-block">*Only (PDF).</p>';
				}else if(id==="attachment"){
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
			
			var validateEmail = function(email) {
				var re = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;

				return re.test(email);
			}
			
			$(document).ready(function(){
				$(".fileList").on("change", "#thumbnailImage", function(){
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
