<%-- 
    Document   : adduser
    Created on : Feb 13, 2019, 1:55:09 PM
    Author     : aasensio
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Add user</title>
        <!--VENDORS-->
        <link rel="stylesheet" href="vendors/bootstrap-4.1.3-dist/css/bootstrap.min.css">
        <link rel="stylesheet" href="css/login.css">
        <script src="vendors/jquery/jquery-3.3.1.min.js"></script>
        <!--CONTROL-->
        <script type="text/javascript" src="./js/control/utils.js"></script>
        <script type="text/javascript" src="./js/control/index.js"></script>
    </head>
    <body class="text-center" data-gr-c-s-loaded="true">
        <%@include file="form_adduser.jsp" %>
        <% if (request.getAttribute("user_added") != null) {
                //int result = request.getAttribute("user_added");
                Integer i = 1; // testing purposes only, NOT FINAL
                String msg = "";
                switch (i) {
                    case -1:
                        msg = "User already exists; user not added.";
                        break;
                    case 0:
                        msg = "Error occurred while accessing the patients file.";
                        break;
                    case 1:
                        msg = "New user has been added successfully";
                        break;
                    default:
                        throw new AssertionError();
                }
                out.println(msg);
            }
        %>
        
    </body>
</html>
