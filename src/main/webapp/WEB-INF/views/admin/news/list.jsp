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
					<i class="fa fa-file-text"></i> News List
				</h3>
				<ol class="breadcrumb">
					<li><i class="fa fa-home"></i><a href="/admin/dashboard">Home</a></li>
					<li><i class="fa fa-list"></i>News List</li>
				</ol>
			</div>
		</div>
		
		
		<div class="row">
			<div class="col-lg-12">
				<section class="panel">
					<header class="panel-heading"> Search News </header>
					<div class="panel-body">
						<form name="searchForm" class="form-inline" role="form" method="get" action="/admin/news/list">				
							<div class="form-group">
								<select name="condition" id="condition" class="form-control" required="required">
									<option value="title" <c:if test="${parameter.condition == 'title'}">selected</c:if>>Title</option>
									<option value="content" <c:if test="${parameter.condition == 'content'}">selected</c:if>>Content</option>
								</select>
							</div>
							<div class="form-group">
								<input type="text" class="form-control"  name="keyword" id="keyword" value="<c:out value="${parameter.keyword}" />" />
							</div>
							<div class="form-group">
								<select name="category" id="category" class="form-control">
									<option value="">Select Category</option>
									<c:forEach var="item" items="${category}" varStatus="status">
										<option value="<c:out value="${item.seq}"/>" <c:if test="${item.seq == parameter.category}">selected</c:if>>
											<c:out value="${item.padding}"/> <c:out value="${item.name}"/>
										</option>
									</c:forEach>
								</select>
							</div>
							<input type="hidden" name="sortColumn" 	value="<c:out value="${parameter.sortColumn}"/>" />
							<input type="hidden" name="sortOrder" 	value="<c:out value="${parameter.sortOrder}"/>" />
							<input type="hidden" name="pageSize" 	value="<c:out value="${parameter.pageSize}"/>" />
							
							<button type="submit" class="btn btn-primary">Search</button>
							<button type="button" onclick="javascript:top.location='/admin/news/write?<c:out value="${parameter.parameterLink}" />'" class="btn btn-lg btn-success pull-right">
								<i class="fa fa-pencil">&nbsp;</i> Write
							</button>
						</form>
					</div>
				</section>
			</div>
			
			<div class="col-lg-12">
				<section class="panel">
					<header class="panel-heading"> Sorting </header>
					<div class="panel-body">
						<form name="sortForm" class="form-inline" role="form">
							<div class="form-group">
								<div class="input-group" style="max-width: 320px;">
									<span class="input-group-addon" id="sb">Sort Column</span>
									<select name="tmpSortColumn" id="tmpSortColumn" class="form-control" aria-describedby="sb">
										<option value="">Select Column</option>
										<option value="title" <c:if test="${parameter.sortColumn == 'title'}">selected</c:if>>News Title</option>
										<option value="regDate" <c:if test="${parameter.sortColumn == 'regDate'}">selected</c:if>>Reg. Date</option>
									</select>
								</div>
							</div>						
							<div class="form-group">
								<div class="input-group" style="max-width: 320px;">
									<span class="input-group-addon" id="so">Sort Order</span>
									<select name="tmpSortOrder" id="tmpSortOrder" class="form-control" aria-describedby="so">
										<option value="">Select Order</option>
										<option value="ASC" <c:if test="${parameter.sortOrder == 'ASC'}">selected</c:if>>Ascending</option>
										<option value="DESC" <c:if test="${parameter.sortOrder == 'DESC'}">selected</c:if>>Descending</option>
									</select>
								</div>
							</div>
							<button type="button" onclick="sort();" class="btn btn-primary">Sort</button>
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
								<th class="col-md-3"><i class="fa fa-info-circle">&nbsp;</i> News Title</th>
								<th class="col-md-4"><i class="fa fa-comment">&nbsp;</i> News Content</th>
								<th class="col-md-2"><i class="fa fa-bars">&nbsp;</i> Category</th>
								<th class="col-md-1"><i class="fa fa-calendar-o">&nbsp;</i> Reg. Date</th>
								<th class="col-md-2" style="text-align: center;"><span class="pull-right"><i class="fa fa-gears">&nbsp;</i> Action</span></th>
							</tr>
							
							<c:if test="${not empty news}">
								<c:forEach var="item" items="${news}" varStatus="status">
									<tr>
										<td onclick="location.href='/admin/news/detail?seq=<c:out value="${item.seq}" />&<c:out value="${parameter.parameterLink}" />'">
											<a href="/admin/news/detail?seq=<c:out value="${item.seq}" />&<c:out value="${parameter.parameterLink}" />">
												<p><c:out value="${item.title}"/></p>
											</a>
										</td>
										<td class="">
											<c:set var="desc" value="${ew:stripTags(item.content)}" />
											<p><c:out value="${ew:stringCut(desc, 150)}" /></p>
										</td>
										<td class=""><c:out value="${item.category}" /></td>
										<td class=""><fmt:formatDate value="${item.regDate}" pattern="yyyy-MM-dd"/></td>
										<td class="">
											<div class="btn-group pull-right">
												<a href="/admin/news/detail?seq=<c:out value="${item.seq}" />&<c:out value="${parameter.parameterLink}" />" class="btn btn-info"><i class="fa fa-search"></i> Detail</a>
												<a href="/admin/news/edit?seq=<c:out value="${item.seq}" />&<c:out value="${parameter.parameterLink}" />" class="btn btn-success"><i class="fa fa-edit"></i> Edit</a>
											</div>
										</td>
									</tr>
								</c:forEach>
							</c:if>
							<c:if test="${empty news}">
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
						<li>
							<div class="form-group">
								<div class="input-group" style="max-width: 200px;" class="pull-right">
									<span class="input-group-addon" id="sp">Show</span>
									<select name="tmpPageSize" id="tmpPageSize" class="form-control" aria-describedby="sp" onchange="showRows();">
										<option value="5" <c:if test="${parameter.pageSize == '5'}">selected</c:if>>5 Rows</option>
										<option value="10" <c:if test="${parameter.pageSize == '10'}">selected</c:if>>10 Rows</option>
										<option value="50" <c:if test="${parameter.pageSize == '50'}">selected</c:if>>50 Rows</option>
										<option value="100" <c:if test="${parameter.pageSize == '100'}">selected</c:if>>100 Rows</option>
									</select>
								</div>
							</div>
						</li>
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
