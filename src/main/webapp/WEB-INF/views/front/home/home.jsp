<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<tiles:insertDefinition name="_baseFront">
    
    <tiles:putAttribute name="body"> 
		<div class="container">
			<h1>${serverTime}</h1>
		</div>
    </tiles:putAttribute>
    
    <tiles:putAttribute name="js">
    	<script>
    		$(document).ready(function(){
    			top.location = "/admin/login";
    		});
    	</script>
    </tiles:putAttribute>
    
</tiles:insertDefinition>
