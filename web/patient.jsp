<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<jsp:include page="menu.jsp"/>

<!-- List All Patients with modify and delete buttons if needed -->
<c:if test="${patients != null}">
    <table class="table table-striped">
      <thead class="thead-dark">
        <tr>
          <c:if test="${username != null}">
              <th scope="col">Age</th>
              </c:if>
          <th scope="col">Age Group</th>
          <th scope="col">Weight (kg)</th>
            <c:if test="${username != null}">
            <th scope="col">Height (cm)</th>
            </c:if>
          <th scope="col">IMC</th>
          <th scope="col">Classification</th>
            <c:if test="${username != null}">
            <th scope="col">Menarche</th>
            <th scope="col">Menopause</th>
            <th scope="col">Menopause Type</th>
            </c:if>
            <c:choose>
                <c:when test="${param.action == 'modify_form'}">
                <th scope="col">Modify Patient</th>
                </c:when>
                <c:when test="${param.action == 'delete_form'}">
                <th scope="col">Delete Patient</th>
                </c:when>
            </c:choose>
        </tr>
      </thead>
      <tbody>
        <c:if test="${param.showFormAdd != null}">
        <form action="patient_controller" method="POST">
          <tr>
            <td scope="row"><input type="number" placeholder="Age" name="age"></td>
            <td scope="row"><input type="text" placeholder="Age Group" name="ageGroup"></td>
            <td scope="row"><input type="text" placeholder="Weight" name="weight"></td>
            <td scope="row"><input type="text" placeholder="Height" name="height"></td>
            <td scope="row"><input type="text" placeholder="IMC" name="imc" disabled=""></td>
            <td scope="row">
              <select class="custom-select" name="classification">
                <option value="">Choose...</option>
                <option value="normal">Normal</option>
                <option value="osteopenia">Osteopenia</option>
                <option value="osteoporosi">Osteoporosi</option>
              </select>
            </td>
            <td scope="row"><input type="number" placeholder="Age when first period" name="menarche"></td>
            <td scope="row">
              <div class="form-check">
                <input class="form-check-input" type="radio" name="menopause" value="yes">
                <label class="form-check-label">
                  Yes
                </label>
              </div>
              <div class="form-check">
                <input class="form-check-input" type="radio" name="menopause" value="no">
                <label class="form-check-label">
                  No
                </label>
              </div>
            </td>
            <td scope="row">
              <select class="custom-select" name="menopauseType">
                <option value="">Choose...</option>n
                <option value="no_consta">No consta</option>n
                <option value="natural">Natural</option>
                <option value="ovariectomia">Ovariectomia</option>
                <option value="histeroctomia">Histeroctomia</option>
                <option value="ambdues">Ambdues</option>
              </select>
            </td>
            <!-- Extra column for the Add Patient button -->
            <td scope="row"><button type="submit" class="btn btn-success" name="action" value="add">Add Patient</button></td>
          </tr>
          
          
        </form>
      </c:if>
      <c:forEach items="${patients}" var="patient">
          <tr>
            <c:if test="${username != null}">
                <td scope="row">${patient.age}</td>
            </c:if>
            <td scope="row">${patient.ageGroup}</td>
            <td scope="row">${patient.weight}</td>
            <c:if test="${username != null}">
                <td scope="row">${patient.height}</td>
            </c:if>
            <td scope="row">${patient.imc}</td>
            <td scope="row">${patient.classification}</td>
            <c:if test="${username != null}">
                <td scope="row">${patient.menarche}</td>
                <td scope="row">${patient.menopause}</td>
                <td scope="row">${patient.menopauseType}</td>
            </c:if>
          </tr>
      </c:forEach>
    </tbody>
</table>
</c:if>

<!-- Patient Form (add and modify) -->
<c:if test="${param.showFormAdd != null || patient_to_modify != null}" >
    <form action="patient_controller" method="POST">
      <div class="form-group row">
        <label for="inputName" class="col-sm-3">Patient Name:</label>
        <input type="text" class="form-control col-sm-9" id="inputName" name="name" placeholder="Patient Name">
      </div>
      <div class="form-group row">
        <label for="inputPhone" class="col-sm-3">Patient Phone:</label>
        <input type="text" class="form-control col-sm-9" id="inputPhone" name="phone" placeholder="Patient Phone">
      </div>
      <div class="form-group row">
        <label for="inputAge" class="col-sm-3">Patient Age:</label>
        <input type="number" class="form-control col-sm-9" id="inputAge" name="age" placeholder="Patient Age">
      </div>
      <div class="form-group row">
        <label for="inputCategory" class="col-sm-3">New Patient Category:</label>
        <input type="text" class="form-control col-sm-9" id="inputCategory" name="category" placeholder="Patient Category ID">
      </div>
      <c:choose>
          <c:when test="${param.showFormAdd != null}">
              <button type="submit" class="btn btn-primary" value="add" name="action">Add Patient</button>
          </c:when>
          <c:when test="${patient_to_modify != null}">
              <input type="hidden" value="${patient_to_modify.id}" name="id"/>
              <button type="submit" class="btn btn-primary" name="action" value="modify">Modify Patient</button>
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