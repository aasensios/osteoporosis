<%-- 
    Document   : modify
    Created on : Jan 16, 2019, 4:57:57 PM
    Author     : aasensio
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Modify</title>
    </head>
    <body>
        <h1>Modify Page</h1>
        <%@include file="menu.jsp" %>
        <form method="post" action="user">
            New username: <input type="text" name="new-username" value="<%if (session.getAttribute("username") != null) {
                    out.println(session.getAttribute("username"));
                }%>" required/>
            <br/>
            Current password: <input type="password" name="current-password" required/>
            <br/>
            New password: <input type="password" name="new-password" required/>
            <br/>
            Repeat new password: <input type="password" name="new-password-repeat" required/>
            <br/>
            <input type="submit" name="action" value="Modify"/>
        </form>
        <%
            if (request.getParameter("error") != null) {
                String error = request.getParameter("error");
                switch (error) {
                    case "old-passwd":
                        out.println("<p>La contraseña actual no es correcta.</p>");
                        break;
                    case "not-equal-passwords":
                        out.println("<p>La nueva contraseña no coincide con su repetición.</p>");
                        break;
                }
            }
        %>
    </body>
</html>
