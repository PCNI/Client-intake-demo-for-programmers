<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!-- @author Haridharan Durairaj -->
<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Login Page</title>
    <!-- Bootstrap Core CSS -->
    <link href="${contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">

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
                	
                    <div class="col-xs-12 col-sm-12 col-md-12 center-block">
                    	<a class="navbar-brand" href="index.html">Login</a>
                    </div>
                    
                </div>
            </div>
        </nav>

        <div id="page-wrapper">

            <div class="container">
            		<div class="row">
                        <div class="col-xs-12 col-sm-12 col-md-12">
                            <img src="${contextPath}/resources/img/logo.jpg" class="logo center-block">
                        </div>
                    </div>
                 <div class="row">
                      <form id="login-form"  class="form-signin" action="LoginServlet" method="POST" onsubmit="return(validate());" autocomplete="off">
                      	<c:if test="${not empty error_message}">
                            <p style="color: red !important; text-align: center;">${error_message}</p>
                        </c:if>
                        <label for="inputEmail" class="sr-only">User ID</label>
                        <input type="text" name="j_username" id="j_username" class="form-control" placeholder="User ID" autofocus>
                        <label for="inputPassword" class="sr-only">Password</label>
                        <input type="password" name="j_password" id="j_password" class="form-control" placeholder="Password" >
                        <div class="checkbox">
                          <label>
                            <input type="checkbox" value="remember-me"> Remember me
                          </label>
                        </div>
                        <button type="submit" data-role="button" data-transition="slide" class="btn btn-lg btn-primary btn-block">Submit</button>
                        <!--<a href="Client_add.html" class="btn btn-lg btn-primary btn-block" type="submit">Submit</a>-->
                      </form>
                    </div>
            </div>
            <!-- /.container-fluid -->

        </div>
        <!-- /#page-wrapper -->

    </div>
    <!-- /#wrapper -->

    <!-- jQuery -->
    <script type="text/javascript">
<!--
// Form validation code will come here.
function validate()
{
   
   var username=myTrim(document.getElementById("j_username").value);
   var password=myTrim(document.getElementById("j_password").value);
     
   if(username == "")
   {
     alert( "Please provide your Username!" );
     document.getElementById("j_username").focus() ;
     return false;
   }
   if(password== "")
   {
     alert( "Please provide your Password!" );
     document.getElementById("j_password").focus() ;
     return false;
   } 
   document.getElementById("j_username").value=username;
   document.getElementById("j_password").value=password;
  
   return( true );
}

function myTrim(x) {
    return x.replace(/^\s+|\s+$/gm,'');
}

      
</body>

</html>
