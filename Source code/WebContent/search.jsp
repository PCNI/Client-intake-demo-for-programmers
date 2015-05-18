<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page session="false"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html lang="en">

<head>

<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">

<title>Client Search</title>

<!-- Bootstrap Core CSS -->
<link href="${contextPath}/resources/css/bootstrap.min.css"
	rel="stylesheet">

<!-- Custom CSS -->
<link href="${contextPath}/resources/css/custom.css" rel="stylesheet">


</head>

<body>

	<div id="wrapper">

		<!-- Navigation -->
		<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
			<!-- Brand and toggle get grouped for better mobile display -->
			<div class="navbar-header">
				<div class="row">
					<div class="col-xs-2 col-sm-2 col-md-2"></div>
					<div class="col-xs-8 col-sm-8 col-md-8">
						<a class="navbar-brand center-block" href="Client_add.html">Client
							List </a>
					</div>
					
				</div>
			</div>
		</nav>
		
     
		<div id="page-wrapper">

			<div class="container">
				<div class="home_alignment">
					<!--<div class="row">
                    	<div class="col-lg-2 col-sm-4 col-xs-6 pull-right">
                        	<a href="#" class="btn btn-lg btn-default btn-block">Add New Client</a>
                        </div>
                    </div>-->
                    
                    
					<div class="row">
						<div
							class="col-xs-12 col-sm-8 col-md-6 col-sm-offset-2 col-md-offset-3">
							<form action="SearchResultsServlet">
								 <c:if test="${message>100}">
      						<font color="red"> No results have been found, Please change search criteria and try again</font> 
     				 			</c:if>
     				 			<br><br>
								<div class="form-group">
									<label for="inputEmail3" class="col-sm-2 control-label">FirstName</label>
									<div class="col-sm-10">
										<input class="form-control" id="firstName" name="firstName"
											placeholder="First Name">
									</div>
								</div>


								<div class="form-group">
									<div class="col-xs-12 col-sm-4 col-md-4 pull-right">
										<button type="submit" data-role="button"
											data-transition="slide"
											class="btn btn-lg btn-primary btn-block">Go</button>

									</div>
								</div>
							 </form>
						</div>
					</div>
				</div>
				<!-- /.container-fluid -->

			</div>
			<!-- /#page-wrapper -->

		</div>
	</div>
	<!-- /#wrapper -->

</body>

<script type="text/javascript">
<!--
	// Form validation code will come here.
	function validate() {

		var firstName = myTrim(document.getElementById("firstName").value);

		if (firstName == "") {
			alert("Please provide your Firstname!");
			document.getElementById("firstName").focus();
			return false;
		}

		document.getElementById("firstName").value = firstName;

		return (true);
	}

	function myTrim(x) {
		return x.replace(/^\s+|\s+$/gm, '');
	}
//-->
</script>



</html>
