<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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

    <title>Client List</title>
 
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
                <div class="col-xs-2 col-sm-2 col-md-2">                      
                    </div>
                   <div class="col-xs-8 col-sm-8 col-md-8">
                    	<a class="navbar-brand center-block" href="Client_add.html">Client List</a>
                    </div>

                </div>
            </div>
        </nav>

        <div id="page-wrapper">

            <div class="container">
                <div class="home_alignment">
                    <div class="row">
                    <div class="col-md-8 col-sm-10 col-xs-12 col-md-offset-2 col-sm-offset-1">
                        <table class="table">
                              <thead>
                                <tr>
                                  <th>Sr No</th>
                                  <th>Client Key</th>
                                  <th>Client Name</th>
                                  <th>Client DOB</th>
                                  <th>Ssn Number</th>
                                </tr>
                              </thead>
                              <c:forEach items="${clientList}" var="clientList"
                                  varStatus="itemCount">
                                  <tr>
                                  <td>${itemCount.index+1}</td>
                                      <td><a
                                          href="${contextPath}/SearchDetailsServlet/${clientList.clientKey}"><c:out
                                                  value="${clientList.clientKey}" /></a></td>
                                      <td><c:out value="${clientList.nameFirst}" />&nbsp;<c:out
                                              value="${clientList.nameMiddle}" />&nbsp;<c:out
                                              value="${clientList.nameLast}" /></td>
                                      <td><c:out value="${clientList.dateOfBirth}" /></td>
                                      <td><c:out value="${clientList.socSecNumber}" /></td>
              
                                  </tr>
                              </c:forEach>
                            </table>
                        </div>
                        
                   </div>
                 </div>
                
            </div>
            
            <!-- /.container-fluid -->

        </div>
        <!-- /#page-wrapper -->

    </div>
    <!-- /#wrapper -->

</body>

</html>
