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
					<i class="fa fa-home"></i> Dashboard
				</h3>
			</div>
		</div>
		
		
		<div class="row">
				
			<div class="col-md-6">
	
				<ol class="breadcrumb">
					<li><i class="fa fa-bar-chart"></i>Content Stats</li>
				</ol>
	
				<canvas id="myBarChart" height="100"></canvas>
				<br/><br/>				
			</div>
			
			<div class="col-md-6">
	
				<ol class="breadcrumb">
					<li><i class="fa fa-pie-chart"></i>Content Stats</li>
				</ol>
	
				<canvas id="myPieChart" height="100"></canvas>
				<br/><br/>				
			</div>
			
			<div class="col-md-4">			
				<ol class="breadcrumb">
					<li><i class="fa fa-cog"></i>Current Website Setting</li>
				</ol>
				
				<section class="panel">
				<div class="panel-body">
					<c:if test="${not empty setting}">
						<div class="form quick-post">
							<form class="form-horizontal">
								<div class="form-group">
									<label class="control-label col-lg-2" for="logo">Logo</label>
									<div class="col-lg-10"> 
										<img class="lazy" data-original="/image/<c:out value="${setting.thumbnailImageSeq}"/>" width="100">
									</div>
								</div>      
								<div class="form-group">
									<label class="control-label col-lg-2" for="back">Background</label>
									<div class="col-lg-10"> 
										<img class="lazy" data-original="/image/<c:out value="${setting.mainImageSeq}"/>" height="180" style="max-width:100%;">
									</div>
								</div>      
							</form>
						</div>
					</c:if>
					<c:if test="${empty setting}">
						<h4>You haven't assign any logo and/or background for the website.</h4>
						<p>Please check your setting</p>
						<a href="/admin/setting/list" class="btn btn-primary">Website Setting</a>
					</c:if>
				</div>
				</section>
			
			</div>	

			<div class="col-md-4">
			
				<ol class="breadcrumb">
					<li><i class="fa fa-file-text"></i>Recent News</li>
				</ol>
				
				<section class="panel table-responsive">
					<table class="table table-striped table-advance table-hover">
						<tbody>
							<tr>
								<th class="col-md-9"><i class="fa fa-info-circle">&nbsp;</i> News Title</th>
								<th class="col-md-3"><i class="fa fa-calendar-o">&nbsp;</i> Reg. Date</th>
							</tr>
							
							<c:if test="${not empty news}">
								<c:forEach var="item" items="${news}" varStatus="status">
									<tr>
										<td onclick="location.href='/admin/news/detail?seq=<c:out value="${item.seq}" />&category=111'">
											<a href="/admin/news/detail?seq=<c:out value="${item.seq}" />&category=111">
												<c:set var="tit" value="${ew:stripTags(item.title)}" />
												<p><c:out value="${ew:stringCut(tit, 150)}" /></p>
											</a>
										</td>
										<td class=""><fmt:formatDate value="${item.regDate}" pattern="yyyy-MM-dd"/></td>
									</tr>
								</c:forEach>
							</c:if>
							<c:if test="${empty news}">
								<tr>
									<td colspan="2">No Data</td>
								</tr>
							</c:if>					
		
						</tbody>
					</table>
				</section>
			</div>
			
			<div class="col-md-4">
			
				<ol class="breadcrumb">
					<li><i class="fa fa-file-text"></i>Recent CSR</li>
				</ol>
				
				<section class="panel table-responsive">
					<table class="table table-striped table-advance table-hover">
						<tbody>
							<tr>
								<th class="col-md-9"><i class="fa fa-info-circle">&nbsp;</i> CSR Title</th>
								<th class="col-md-3"><i class="fa fa-calendar-o">&nbsp;</i> Reg. Date</th>
							</tr>
							
							<c:if test="${not empty csr}">
								<c:forEach var="item" items="${csr}" varStatus="status">
									<tr>
										<td onclick="location.href='/admin/news/detail?seq=<c:out value="${item.seq}" />&category=112'">
											<a href="/admin/news/detail?seq=<c:out value="${item.seq}" />&category=112">
												<c:set var="tit" value="${ew:stripTags(item.title)}" />
												<p><c:out value="${ew:stringCut(tit, 150)}" /></p>
											</a>
										</td>
										<td class=""><fmt:formatDate value="${item.regDate}" pattern="yyyy-MM-dd"/></td>
									</tr>
								</c:forEach>
							</c:if>
							<c:if test="${empty csr}">
								<tr>
									<td colspan="2">No Data</td>
								</tr>
							</c:if>					
		
						</tbody>
					</table>
				</section>
			</div>
						
			<div class="clearfix"></div>

			<div class="col-md-6">
			
				<ol class="breadcrumb">
					<li><i class="fa fa-calendar-o"></i>Recent Event</li>
				</ol>
				
				<section class="panel table-responsive">
					<table class="table table-striped table-advance table-hover">
						<tbody>
							<tr>
								<th class="col-md-9"><i class="fa fa-info-circle">&nbsp;</i> Event Title</th>
								<th class="col-md-3"><i class="fa fa-calendar-o">&nbsp;</i> Reg. Date</th>
							</tr>
							
							<c:if test="${not empty event}">
								<c:forEach var="item" items="${event}" varStatus="status">
									<tr>
										<td onclick="location.href='/admin/event/detail?seq=<c:out value="${item.seq}" />'">
											<a href="/admin/event/detail?seq=<c:out value="${item.seq}" />">
												<c:set var="tit" value="${ew:stripTags(item.title)}" />
												<p><c:out value="${ew:stringCut(tit, 150)}" /></p>
											</a>
										</td>
										<td class=""><fmt:formatDate value="${item.regDate}" pattern="yyyy-MM-dd"/></td>
									</tr>
								</c:forEach>
							</c:if>
							<c:if test="${empty event}">
								<tr>
									<td colspan="2">No Data</td>
								</tr>
							</c:if>					
		
						</tbody>
					</table>
				</section>
			</div>
			
			<div class="col-md-6">
			
				<ol class="breadcrumb">
					<li><i class="fa fa-graduation-cap"></i>Recent Career</li>
				</ol>
			
				<section class="panel table-responsive">
					<table class="table table-striped table-advance table-hover">
						<tbody>
							<tr>
								<th class="col-md-9"><i class="fa fa-info-circle">&nbsp;</i> Career Title</th>
								<th class="col-md-3"><i class="fa fa-calendar-o">&nbsp;</i> Reg. Date</th>
							</tr>
							
							<c:if test="${not empty career}">
								<c:forEach var="item" items="${career}" varStatus="status">
									<tr>
										<td onclick="location.href='/admin/career/detail?seq=<c:out value="${item.seq}" />'">
											<a href="/admin/career/detail?seq=<c:out value="${item.seq}" />">
												<c:set var="tit" value="${ew:stripTags(item.title)}" />
												<p><c:out value="${ew:stringCut(tit, 150)}" /></p>
											</a>
										</td>
										<td class=""><fmt:formatDate value="${item.regDate}" pattern="yyyy-MM-dd"/></td>
									</tr>
								</c:forEach>
							</c:if>
							<c:if test="${empty career}">
								<tr>
									<td colspan="2">No Data</td>
								</tr>
							</c:if>					
		
						</tbody>
					</table>
				</section>
			</div>
			
			<div class="clearfix"></div>

			<div class="col-md-4">
			
				<ol class="breadcrumb">
					<li><i class="fa fa-download"></i>Recent Report</li>
				</ol>
			
				<section class="panel table-responsive">
					<table class="table table-striped table-advance table-hover">
						<tbody>
							<tr>
								<th class="col-md-9"><i class="fa fa-info-circle">&nbsp;</i> Report Title</th>
								<th class="col-md-3"><i class="fa fa-calendar-o">&nbsp;</i> Reg. Date</th>
							</tr>
							
							<c:if test="${not empty report}">
								<c:forEach var="item" items="${report}" varStatus="status">
									<tr>
										<td onclick="location.href='/admin/catalog/detail?seq=<c:out value="${item.seq}" />&category=89'">
											<a href="/admin/catalog/detail?seq=<c:out value="${item.seq}" />&category=89">
												<c:set var="tit" value="${ew:stripTags(item.name)}" />
												<p><c:out value="${ew:stringCut(tit, 150)}" /></p>
											</a>
										</td>
										<td class=""><fmt:formatDate value="${item.regDate}" pattern="yyyy-MM-dd"/></td>
									</tr>
								</c:forEach>
							</c:if>
							<c:if test="${empty report}">
								<tr>
									<td colspan="2">No Data</td>
								</tr>
							</c:if>					
		
						</tbody>
					</table>
				</section>
			</div>
			
			<div class="col-md-4">
			
				<ol class="breadcrumb">
					<li><i class="fa fa-file-picture-o"></i>Recent Picture</li>
				</ol>
			
				<section class="panel table-responsive">
					<table class="table table-striped table-advance table-hover">
						<tbody>
							<tr>
								<th class="col-md-9"><i class="fa fa-info-circle">&nbsp;</i> Picture Title</th>
								<th class="col-md-3"><i class="fa fa-calendar-o">&nbsp;</i> Reg. Date</th>
							</tr>
							
							<c:if test="${not empty picture}">
								<c:forEach var="item" items="${picture}" varStatus="status">
									<tr>
										<td onclick="location.href='/admin/catalog/detail?seq=<c:out value="${item.seq}" />&category=123'">
											<a href="/admin/catalog/detail?seq=<c:out value="${item.seq}" />&category=123">
												<c:set var="tit" value="${ew:stripTags(item.name)}" />
												<p><c:out value="${ew:stringCut(tit, 150)}" /></p>
											</a>
										</td>
										<td class=""><fmt:formatDate value="${item.regDate}" pattern="yyyy-MM-dd"/></td>
									</tr>
								</c:forEach>
							</c:if>
							<c:if test="${empty picture}">
								<tr>
									<td colspan="2">No Data</td>
								</tr>
							</c:if>					
		
						</tbody>
					</table>
				</section>
			</div>	
			
									
			<div class="col-md-4">
			
				<ol class="breadcrumb">
					<li><i class="fa fa-youtube-play"></i>Recent Video</li>
				</ol>
			
				<section class="panel table-responsive">
					<table class="table table-striped table-advance table-hover">
						<tbody>
							<tr>
								<th class="col-md-9"><i class="fa fa-info-circle">&nbsp;</i> Video Title</th>
								<th class="col-md-3"><i class="fa fa-calendar-o">&nbsp;</i> Reg. Date</th>
							</tr>
							
							<c:if test="${not empty video}">
								<c:forEach var="item" items="${video}" varStatus="status">
									<tr>
										<td onclick="location.href='/admin/catalog/detail?seq=<c:out value="${item.seq}" />&category=124'">
											<a href="/admin/catalog/detail?seq=<c:out value="${item.seq}" />&category=124">
												<c:set var="tit" value="${ew:stripTags(item.name)}" />
												<p><c:out value="${ew:stringCut(tit, 150)}" /></p>
											</a>
										</td>
										<td class=""><fmt:formatDate value="${item.regDate}" pattern="yyyy-MM-dd"/></td>
									</tr>
								</c:forEach>
							</c:if>
							<c:if test="${empty video}">
								<tr>
									<td colspan="2">No Data</td>
								</tr>
							</c:if>					
		
						</tbody>
					</table>
				</section>
			</div>	

		</div>

	</tiles:putAttribute>
    
    <tiles:putAttribute name="js">
		<script src="/resources/admin/js/Chart.min.js"></script>
    	<script>
    		var barLabel = [], barData = [], barColor = [], pieColor = [];
			<c:forEach var="item" items="${itemStatsList}" varStatus="status">
				<c:forEach var="child" items="${item}" varStatus="childStatus">
	         		barLabel.push('<c:out value="${child.key}"/>');
	         		barData.push(<c:out value="${child.value}"/>);
             	</c:forEach>
             	barColor.push("#39<c:out value="${status.index}"/>A59");
             	pieColor.push('#'+(Math.random()*0xFFFFFF<<0).toString(16));
         	</c:forEach>

    		
	    	var barCtx = document.getElementById("myBarChart");
	    	var myBarChart = new Chart(barCtx, {
	    	    type: 'bar',
	    	    data: {
	    	        labels: barLabel,
	    	        datasets: [{
	    	            label: 'Number of published contents',
	    	            data: barData,
	    	            backgroundColor: pieColor,
	    	            borderColor: pieColor,
	    	            borderWidth: 1
	    	        }]
	    	    },
	    	    options: {
	    	    	responsive : true,
	    	        scales: {
	    	            yAxes: [{
	    	                ticks: {
	    	                    beginAtZero:true
	    	                }
	    	            }]
	    	        }
	    	    }
	    	});
	    	
	    	var pieCtx = document.getElementById("myPieChart");
	    	var myPieChart = new Chart(pieCtx, {
	    	    type: 'doughnut',
	    	    data: {
	    	        labels: barLabel,
	    	        datasets: [{
	    	            label: 'Number of published contents',
	    	            data: barData,
	    	            backgroundColor: pieColor,
	    	            borderColor: pieColor,
	    	            borderWidth: 1
	    	        }]
	    	    },
	    	    options: {
	    	    	responsive : true,
	    	        scales: {
	    	            yAxes: [{
	    	                ticks: {
	    	                    beginAtZero:true
	    	                }
	    	            }]
	    	        }
	    	    }
	    	});
	    	
    	</script>
    </tiles:putAttribute>
    
</tiles:insertDefinition>
