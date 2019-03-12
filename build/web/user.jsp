<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<jsp:include page="menu.jsp"/>

<!-- List all users with modify and delete buttons if needed -->
<c:if test="${users != null}">
    <table class="table table-striped">
      <thead class="thead-dark">
        <tr>
          <th scope="col">Username</th>
          <th scope="col">Password</th>
          <th scope="col">Role</th>
            <c:choose>
                <c:when test="${param.action == 'modify_form'}">
                <th scope="col">Modify User</th>
                </c:when>
                <c:when test="${param.action == 'delete_form'}">
                <th scope="col">Delete User</th>
                </c:when>
            </c:choose>
        </tr>
      </thead>
      <tbody>
        <c:if test="${param.showFormAdd != null}">
        <form action="user_controller" method="POST">
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
        
        
        
        
        
        <!--TODO-->
      </c:if>
      <c:forEach items="${users}" var="user">
          <tr>
            <c:if test="${username != null}">
                <td scope="row">${user.age}</td>
            </c:if>
            <td scope="row">${user.ageGroup}</td>
            <td scope="row">${user.weight}</td>
            <c:if test="${username != null}">
                <td scope="row">${user.height}</td>
            </c:if>
            <td scope="row">${user.imc}</td>
            <td scope="row">${user.classification}</td>
            <c:if test="${username != null}">
                <td scope="row">${user.menarche}</td>
                <td scope="row">${user.menopause}</td>
                <td scope="row">${user.menopauseType}</td>
            </c:if>
          </tr>
      </c:forEach>
    </tbody>
</table>
</c:if>

<!-- User Form (add and modify) -->
<c:if test="${param.showFormAdd != null || user_to_modify != null}" >
    <form action="user_controller" method="POST">
      <div class="form-group row">
        <label for="inputName" class="col-sm-3">User Name:</label>
        <input type="text" class="form-control col-sm-9" id="inputName" name="name" placeholder="User Name">
      </div>
      <div class="form-group row">
        <label for="inputPhone" class="col-sm-3">User Phone:</label>
        <input type="text" class="form-control col-sm-9" id="inputPhone" name="phone" placeholder="User Phone">
      </div>
      <div class="form-group row">
        <label for="inputAge" class="col-sm-3">User Age:</label>
        <input type="number" class="form-control col-sm-9" id="inputAge" name="age" placeholder="User Age">
      </div>
      <div class="form-group row">
        <label for="inputCategory" class="col-sm-3">New User Category:</label>
        <input type="text" class="form-control col-sm-9" id="inputCategory" name="category" placeholder="User Category ID">
      </div>
      <c:choose>
          <c:when test="${param.showFormAdd != null}">
              <button type="submit" class="btn btn-primary" value="add" name="action">Add User</button>
          </c:when>
          <c:when test="${user_to_modify != null}">
              <input type="hidden" value="${user_to_modify.id}" name="id"/>
              <button type="submit" class="btn btn-primary" name="action" value="modify">Modify User</button>
          </c:when>
          <c:otherwise>
          </c:otherwise>
      </c:choose>
    </form>
</c:if>

<!-- Messages -->
<c:choose>
    <c:when test="${success != null}">
        <div class="alert alert-success" role="alert">${success}</div>
    </c:when>
    <c:when test="${error != null}">
        <div class="alert alert-danger" role="alert">${error}</div>
    </c:when>
    <c:otherwise>
    </c:otherwise>
</c:choose>

<jsp:include page="html/footer.html"/>