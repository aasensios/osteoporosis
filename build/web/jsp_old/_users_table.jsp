<%@page import="java.util.List"%>
<%@page import="model.User"%>
<form method="post" action="user">
    <%
        if (request.getAttribute("users") != null) {
            List<User> users = (List<User>) request.getAttribute("users");
            StringBuilder dynamicTable = new StringBuilder("");
            if (session.getAttribute("username") != null) {
                dynamicTable.append("<table><thead><tr>"
                        + "<th scope='col'>Select one user</th>"
                        + "<th scope='col'>Username</th>"
                        + "</tr></thead><tbody>");
                for (User user : users) {
                    dynamicTable.append(String.format("<tr>"
                            + "<td scope='row'><input type='radio' name='selected' value='%s'></td>"
                            + "<td>%s</td></tr>",
                            user.getUsername(),
                            user.getUsername()
                    ));
                }
            }
            out.println(dynamicTable.toString());
            out.println("<button type='submit' class='btn btn-danger' name='action' value='delete'>Delete</button>");
        }
    %>

</form>
