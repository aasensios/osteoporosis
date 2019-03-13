<%-- 
    Document   : addpatient
    Created on : Feb 13, 2019, 1:07:18 AM
    Author     : aasensio
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Add patient</title>
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
        </header>
        <main>
            <% if (request.getAttribute("patient_added") != null) {
                    //int result = request.getAttribute("patient_added");
                    Integer i = 1; // testing purposes only, NOT FINAL
                    String msg = "";
                    switch (i) {
                        case -1:
                            msg = "Patient already exists; patient not added.";
                            break;
                        case 0:
                            msg = "Error occurred while accessing the patients file.";
                            break;
                        case 1:
                            msg = "New patient has been added successfully";
                            break;
                        default:
                            throw new AssertionError();
                    }
                    out.println(msg);
                }
            %>
            <%@include file="form_addpatient.jsp" %>
            <button class="btn btn-warning"><a href='landing.jsp'>Go back</a></button>
        </main>
        <footer>
            <%@include file="footer.jsp" %>
        </footer>
    </body>
</html>
