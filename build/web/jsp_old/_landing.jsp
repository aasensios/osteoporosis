<%-- 
    Document   : landing
    Created on : Jan 30, 2019, 3:40:48 PM
    Author     : aasensio
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Osteoporosis</title>
        <!--VENDORS-->
        <link rel="stylesheet" href="vendors/bootstrap-4.1.3-dist/css/bootstrap.min.css">
        <script src="vendors/jquery/jquery-3.3.1.min.js"></script>
        <!--CONTROL-->
        <script type="text/javascript" src="./js/control/utils.js"></script>
        <script type="text/javascript" src="./js/control/index.js"></script>
    </head>
    <body>
        <header>
            <%@include file="menu.jsp" %>
            <%@include file="form_filterpatient.jsp" %>
        </header>
        <main>
            <%@include file="patients_table.jsp" %>
            <%@include file="users_table.jsp" %>
        </main>
        <footer>
            <%@include file="footer.jsp" %>
        </footer>
    </body>
</html>
