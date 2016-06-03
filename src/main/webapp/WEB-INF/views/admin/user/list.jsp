<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<tiles:insertDefinition name="_baseAdmin">
    
    <tiles:putAttribute name="body">
    
		<div class="row">
			<div class="col-lg-12">
				<h3 class="page-header">
					<i class="fa fa-user"></i> User List
				</h3>
				<ol class="breadcrumb">
					<li><i class="fa fa-home"></i><a href="/admin/dashboard">Home</a></li>
					<li><i class="fa fa-list"></i>User List</li>
				</ol>
			</div>
		</div>
		
		
		<div class="row">
			<div class="col-lg-12">
				<section class="panel">
					<header class="panel-heading"> Search User </header>
					<div class="panel-body">
						<form name="searchForm" class="form-inline" role="form" method="get" action="/admin/user/list">				
							<div class="form-group">
								<select name="condition" id="condition" class="form-control" required="required">
									<option value="firstName" <c:if test="${parameter.condition == 'firstName'}">selected</c:if>>First Name</option>
									<option value="username" <c:if test="${parameter.condition == 'username'}">selected</c:if>>Username</option>
								</select>
							</div>
							<div class="form-group">
								<input type="text" class="form-control" required="required" name="keyword" id="keyword" value="<c:out value="${parameter.keyword}" />" />
							</div>
							<button type="submit" class="btn btn-primary">Search</button>
							<button type="button" onclick="javascript:top.location='/admin/user/write?<c:out value="${parameter.parameterLink}"/>'" class="btn btn-lg btn-success pull-right">
								<i class="fa fa-pencil">&nbsp;</i> Write
							</button>
						</form>
					</div>
				</section>
			</div>
		</div>
		
		<div class="row">
			<div class="col-lg-12">
				<section class="panel table-responsive">
					<table class="table table-striped table-advance table-hover">
						<tbody>
							<tr>
								<th><i class="fa fa-info-circle">&nbsp;</i> User Name</th>
								<th class=""> First Name</th>
								<th>Last Name</th>
								<th class="" style="text-align: center;"><span class="pull-right"><i class="fa fa-gears">&nbsp;</i> Action</span></th>
							</tr>
							<c:if test="${not empty user}">
								<c:forEach var="item" items="${user}" varStatus="status">
									<tr>
										<td onclick="location.href='/admin/user/detail?username=<c:out value="${item.username}"/>&<c:out value="${parameter.parameterLink}"/>'">
											<a href="/admin/user/detail?username=<c:out value="${item.username}"/>&<c:out value="${parameter.parameterLink}"/>">
												<c:out value="${item.username}"/>
											</a>
										</td>
										<td class=""><c:out value="${item.firstName}"/></td>
										<td class=""><c:out value="${item.lastName}"/></td>
										<td class="">
											<div class="btn-group pull-right">
												<a href="/admin/user/detail?username=<c:out value="${item.username}"/>&<c:out value="${parameter.parameterLink}"/>" class="btn btn-info"><i class="fa fa-search"></i> Detail</a>
												<a href="/admin/user/edit?username=<c:out value="${item.username}"/>&<c:out value="${parameter.parameterLink}"/>" class="btn btn-success"><i class="fa fa-edit"></i> Edit</a>
											</div>
										</td>
									</tr>
								</c:forEach>
							</c:if>
							<c:if test="${empty user}">
								<tr>
									<td colspan="4">No Data</td>
								</tr>
							</c:if>		    
						</tbody>
					</table>
				</section>
			</div>
		</div>
		
		<div class="row">
			<div class="col-lg-12">
				<div class="text-center">
					<ul class="pagination">
						${paging}
					</ul>
				</div>
			</div>
		</div>    
    
    </tiles:putAttribute>
    
    <tiles:putAttribute name="js">
    	<script>
    		$(document).ready(function(){    			
    			
    		});
    	</script>
    </tiles:putAttribute>
    
</tiles:insertDefinition>
