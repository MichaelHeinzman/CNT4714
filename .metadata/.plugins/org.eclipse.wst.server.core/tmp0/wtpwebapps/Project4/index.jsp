<!doctype html>
<%-- 
	Don't display anything if servlet doesn't respond.
--%>

<%
    String textBox = (String) session.getAttribute("textBox");
    String result = (String) session.getAttribute("result");
    if(result == null){
        result = " ";
   }
   if(textBox == null){
       textBox = " ";
   }
%>
    
        

<html lang="en">
	<head>
	    <meta charset="utf-8">
	    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	
	    <!-- Bootstrap -->
	    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" integrity="sha384-WskhaSGFgHYWDcbwN70/dfYBj47jz9qbsMId/iRN3ewGhXQFZCSftd1LZCfmhktB"
	        crossorigin="anonymous">
	    <title>Project 4</title>
	
	    <script src="reset.js"></script>  
	</head>

	<body>
	    <div class="container-fluid ">
	        <row class="row justify-content-center">
	            <h1 class="text-center col-sm-12 col-md-12 col-lg-12">Welcome to the Fall 2020 Project 4 Remote Database System </h1>
	            
	            <div class="text-center col-sm-12 col-md-12 col-lg-12"> A Servlet/JSP - based Multi-tired Enterprise Application Using A</div>
	            <div class="text-center col-sm-12 col-md-12 col-lg-12"> TomCat Container </div>
	            <div class="text-center col-sm-12 col-md-12 col-lg-12"> You are connected to the Project 4 Enterprise System database.</div>
	            <div class="text-center col-sm-12 col-md-12 col-lg-12">Please enter any valid SQL query or update command.</div>
	            <div class="text-center col-sm-12 col-md-12 col-lg-12">All Execution results will appear below.</div>
	            
	            <form action = "/Project4/SQLQueryServlet" method = "post" style="margin-top: 15px;" class="text-center">
	                <div class="form-group row">
	                    <div class=" col-sm-12 col-md-12 col-lg-12">
	                        <textarea name="textBox" class="form-control" id="textBox" rows="8" cols="50"><%= textBox %></textarea>
	                    </div>
	                </div>
	
	                <button style="margin-bottom: 15px;" type="submit" class="btn btn-dark">Execute Command</button>
	                <button onClick="reset();" style="margin-bottom: 15px;" type="reset" class="btn btn-dark">Reset Form</button>
	            </form>
	        </row>
	    </div>
	
	    <div class="text-center">
	        <%-- jsp statement with out sql response--%>
	        <%= result %>
	    </div>
	   
	</body>

</html>