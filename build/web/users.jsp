<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!doctype html>
<html lang="en">
  <head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css">
    <!--<link rel="stylesheet" href="vendors/bootstrap-4.1.3-dist/css/bootstrap.min.css">-->

    <!--Own CSS-->
    <link rel="stylesheet" href="css/footer.css">

    <title>Osteoporosis Patients</title>
  </head>
  <body>
    <jsp:include page="header.jsp"/>
    <br>
    <main class="container" role="main">
      <div class="starter-template">
        
        <!-- Messages: Success or Error -->
        <c:choose>
            <c:when test="${messages.success != null}">
                <span class="alert alert-success" role="alert">${messages.success}</span>
            </c:when>
            <c:when test="${messages.error != null}">
                <span class="alert alert-danger" role="alert">${messages.error}</span>
            </c:when>
            <c:otherwise>
            </c:otherwise>
        </c:choose>
        <br>
        <br>
        
        <!-- List all users with modify and delete buttons if needed -->
        <c:if test="${users != null}">
            <table class="table table-striped">
              <thead class="thead-dark">
                <tr>
                  <th scope="col">Username</th>
                  <th scope="col">Password</th>
                  <th scope="col">Role</th>
                    <c:choose>
                        <c:when test="${showModifyButtons}">
                        <th scope="col">Modify User</th>
                        </c:when>
                        <c:when test="${showDeleteButtons}">
                        <th scope="col">Delete User</th>
                        </c:when>
                    </c:choose>
                </tr>
              </thead>
              <tbody>
                <c:if test="${showFormAdd}">
                <form action="users" method="POST">
                  <tr>
                    <td scope="row"><input type="text" placeholder="Username" name="username"></td>
                    <td scope="row"><input type="text" placeholder="Password" name="password"></td>
                    <td scope="row">
                      <select class="custom-select" name="role">
                        <option value="">Choose...</option>
                        <option value="basic">Basic</option>
                        <option value="admin">Admin</option>
                      </select>
                    </td>
                    <!-- Extra column for the Add User button -->
                    <td scope="row"><button type="submit" class="btn btn-success" name="action" value="add">Add User</button></td>
                  </tr>
                </form>
              </c:if>
              <c:forEach items="${users}" var="user">
                  <tr>
                    <td scope="row">${user.username}</td>
                    <td scope="row">${user.password}</td>
                    <td scope="row">${user.role}</td>
                    <!--Modify / Delete buttons appear if the according option is clicked-->
                    <c:choose>
                        <c:when test="${showModifyButtons}">
                            <td scope="row">
                              <button class="btn btn-warning" type="submit" value="${user.username};${user.password};${user.role}" name="user">Modify</button>
                              <input type="hidden" name="action" value="user_to_modify"/>
                            </td>
                        </c:when>
                        <c:when test="${showDeleteButtons}">
                            <td scope="row">
                              <button class="btn btn-danger" type="submit" value="${user.username};${user.password};${user.role}" name="user" onclick="confirm(Are you sure?)">Delete</button>
                              <input type="hidden" name="action" value="user_to_delete"/>
                            </td>
                        </c:when>
                    </c:choose>
                  </tr>
              </c:forEach>

              </tbody>
            </table>
        </c:if>

        <!-- Forms: Add and Modify -->
        <c:if test="${showFormAdd || user_to_modify != null}" >
            <form action="users" method="POST">
              <div class="form-group row">
                <label for="inputUsername" class="col-sm-3">Username:</label>
                <input type="text" class="form-control col-sm-6" id="inputUsername" name="username" placeholder="Username" 
                       value="<c:out value="${user_to_modify.username}"/>">
                <c:if test="${messages != null}"><span class="text-danger col-sm-3">${messages.username}</span></c:if>

                </div>
                <div class="form-group row">
                  <label for="inputPassword" class="col-sm-3">Password:</label>
                  <input type="password" class="form-control col-sm-6" id="inputPassword" name="password" placeholder="Password">
                <c:if test="${messages != null}"><span class="text-danger col-sm-3">${messages.password}</span></c:if>

                </div>
                <div class="form-group row">
                  <label for="inputPasswordRepeat" class="col-sm-3">Repeat password:</label>
                  <input type="password" class="form-control col-sm-6" id="inputPasswordRepeat" name="passwordRepeat" placeholder="Repeat password">
                <c:if test="${messages != null}"><span class="text-danger col-sm-3">${messages.passwordRepeat}</span></c:if>

                </div>
              <c:if test="${user_to_modify != null}">
                  <div class="form-group row">
                    <label for="inputRole" class="col-sm-3">Role:</label>
                    <select class="form-control col-sm-6 custom-select" id="inputRole" name="role">
                      <option value="">Choose...</option>
                      <option value="basic">Basic</option>
                      <option value="admin">Admin</option>
                    </select>
                  </div>
              </c:if>
              <c:choose>
                  <c:when test="${showFormAdd}">
                      <button type="submit" class="btn btn-primary" value="add" name="action">Add User</button>
                  </c:when>
                  <c:when test="${user_to_modify != null}">
                      <input type="hidden" value="${user_to_modify.username}" name="username"/>
                      <button type="submit" class="btn btn-primary" name="action" value="modify">Modify User</button>
                  </c:when>
                  <c:otherwise>
                  </c:otherwise>
              </c:choose>
            </form>
        </c:if>

      </div>
    </main>
    <jsp:include page="html/footer.html"/>
    <jsp:include page="html/scripts.html"/>
  </body>
</html>
