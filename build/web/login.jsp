<%-- 
    Document   : login
    Created on : Dec 19, 2018, 3:39:25 PM
    Author     : aasensio
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login</title>
        <!--VENDORS-->
        <link rel="stylesheet" href="vendors/bootstrap-4.1.3-dist/css/bootstrap.min.css">
        <link rel="stylesheet" href="css/login.css">
        <script src="vendors/jquery/jquery-3.3.1.min.js"></script>
        <!--CONTROL-->
        <script type="text/javascript" src="./js/control/utils.js"></script>
        <script type="text/javascript" src="./js/control/index.js"></script>
    </head>
    <body class="text-center" data-gr-c-s-loaded="true">
        <form class="form-signin" method="post" action="user">
            <img class="mb-4" src="images/icon-bone.png" alt="icon-bone" width="72" height="72">
            <p>Log in</p>
            <label for="inputUsername" class="sr-only">Username</label>
            <input type="text" name="username" id="inputUsername" class="form-control" placeholder="Username" required autofocus>
            <label for="inputPassword" class="sr-only">Password</label>
            <input type="password" name="password" id="inputPassword" class="form-control" placeholder="Password" required>
            <%
                if (request.getParameter("error") != null) {
                    out.println("<p id='error'>Usuario y/o contraseña incorrectas.</p>");
                }
            %>
            <button class="btn btn-lg btn-primary btn-block" type="submit" name="action" value="login">Log in</button>
            <p class="mt-5 mb-3 text-muted">Institut Provençana © 2018-2019</p>
        </form>
    </body>
</html>
