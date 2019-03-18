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

        <!-- Patients table -->
        <c:if test="${patients != null}">
            <form action="patient_controller" method="POST">
              <table class="table table-striped">
                <thead class="thead-dark">
                  <tr>
                    <c:if test="${logged_in}">
                        <th scope="col">Age</th>
                        </c:if>
                    <th scope="col">Age Group</th>
                    <th scope="col">Weight (kg)</th>
                      <c:if test="${logged_in}">
                      <th scope="col">Height (cm)</th>
                      </c:if>
                    <th scope="col">IMC (kg/m2)</th>
                    <th scope="col">Classification</th>
                      <c:if test="${logged_in}">
                      <th scope="col">Menarche</th>
                      <th scope="col">Menopause</th>
                      <th scope="col">Menopause Type</th>
                      </c:if>
                      <c:choose>
                          <c:when test="${showFormFilter}">
                          <th scope="col">Filter Patients</th>
                          </c:when>
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

                  <!-- Filter form in a single row -->
                  <c:if test="${logged_in && showFormFilter}">
                      <tr style="background-color: lightblue">
                        <td scope="row">
                          <!--<input type="number" name="age" placeholder="Age">-->
                        </td>
                        <td scope="row">
                          <!--<input type="text" name="ageGroup" placeholder="Age Group">-->
                        </td>
                        <td scope="row">
                          <!--<input type="number" name="weight" placeholder="Weight">-->
                        </td>
                        <td scope="row">
                          <!--<input type="number" name="height" placeholder="Height">-->
                        </td>
                        <td scope="row">
                          <!--<input type="text" name="imc" placeholder="IMC">-->
                        </td>
                        <td scope="row">
                          <select class="custom-select" name="classification">
                            <option value="">Choose...</option>
                            <option value="normal">Normal</option>
                            <option value="osteopenia">Osteopenia</option>
                            <option value="osteoporosi">Osteoporosi</option>
                          </select>
                        </td>
                        <td scope="row">
                          <!--<input type="number" placeholder="Menarche" name="menarche">-->
                        </td>
                        <td scope="row">
                          <div class="form-check">
                            <input class="form-check-input" type="radio" name="menopause" value="yes">
                            <label class="form-check-label">
                              SI
                            </label>
                          </div>
                          <div class="form-check">
                            <input class="form-check-input" type="radio" name="menopause" value="no" checked>
                            <label class="form-check-label">
                              NO
                            </label>
                          </div>
                        </td>
                        <td scope="row">
                          <select class="custom-select" name="menopauseType">
                            <option value="">Choose...</option>n
                            <option value="no consta">No consta</option>n
                            <option value="natural">Natural</option>
                            <option value="ovariectomia">Ovariectomia</option>
                            <option value="histeroctomia">Histeroctomia</option>
                            <option value="ambdues">Ambdues</option>
                          </select>
                        </td>
                        <!--Extra column for the Filter Patient button--> 
                        <td scope="row"><button type="submit" class="btn btn-primary" name="action" value="filter">Filter</button></td>
                      </tr>
                  </c:if>

                  <!--List of all patients-->
                  <c:forEach items="${patients}" var="patient">
                      <tr>
                        <c:if test="${logged_in}">
                            <td scope="row">${patient.age}</td>
                        </c:if>
                        <td scope="row">${patient.ageGroup}</td>
                        <td scope="row">${patient.weight}</td>
                        <c:if test="${logged_in}">
                            <td scope="row">${patient.height}</td>
                        </c:if>
                        <td scope="row">${patient.imc}</td>
                        <td scope="row">${patient.classification}</td>
                        <c:if test="${logged_in}">
                            <td scope="row">${patient.menarche}</td>
                            <td scope="row">${patient.menopause ? "SI" : "NO"}</td>
                            <td scope="row">${patient.menopauseType}</td>
                        </c:if>
                        <!--Modify / Delete buttons appear if the according option is clicked-->
                        <c:choose>
                            <c:when test="${param.action == 'modify_form'}">
                                <td scope="row">
                                  <button class="btn btn-warning" type="submit" value="${patient.registerId};${patient.age};${patient.ageGroup};${patient.weight};${patient.height};${patient.imc};${patient.classification};${patient.menarche};${patient.menopause};${patient.menopauseType}" name="patient">Modify</button>
                                  <input type="hidden" name="action" value="patient_to_modify"/>
                                </td>
                            </c:when>
                            <c:when test="${param.action == 'delete_form'}">
                                <td scope="row">
                                  <button class="btn btn-danger" type="submit" value="${patient.registerId};${patient.age};${patient.ageGroup};${patient.weight};${patient.height};${patient.imc};${patient.classification};${patient.menarche};${patient.menopause};${patient.menopauseType}" name="patient" onlcick="confirm(Are you sure?)">Delete</button>
                                  <input type="hidden" name="action" value="patient_to_delete"/>
                                </td>
                            </c:when>
                        </c:choose>
                      </tr>
                  </c:forEach>
                </tbody>
              </table>
            </form>
        </c:if>

        <!-- Patient Form (add and modify) -->
        <c:if test="${param.showFormAdd != null || patient_to_modify != null}" >
            <form action="patient_controller" method="POST">
              <div class="form-group row">
                <label for="inputAge" class="col-sm-3">Age:</label>
                <input type="number" class="form-control col-sm-9" id="inputAge" name="age" placeholder="Age">
              </div>
              <div class="form-group row">
                <label for="inputWeight" class="col-sm-3">Weight (kg):</label>
                <input type="number" class="form-control col-sm-9" id="inputWeight" name="weight" placeholder="Weight">
              </div>
              <div class="form-group row">
                <label for="inputHeight" class="col-sm-3">Height (m):</label>
                <input type="number" class="form-control col-sm-9" id="inputHeight" name="height" placeholder="Height">
              </div>
              <div class="form-group row">
                <label for="inputClassification" class="col-sm-3">Classification:</label>
                <select class="form-control col-sm-9 custom-select" name="classification" id="inputClassification">
                  <option value="">Choose...</option>
                  <option value="normal">Normal</option>
                  <option value="osteopenia">Osteopenia</option>
                  <option value="osteoporosi">Osteoporosi</option>
                </select>
              </div>
              <div class="form-group row">
                <label for="inputMenarche" class="col-sm-3">Menarche:</label>
                <input type="number" class="form-control col-sm-9" id="inputMenarche" name="menarche" placeholder="Menarche">
              </div>
              <div class="form-group row">
                <label for="inputMenopause" class="col-sm-3">Menopause:</label>
                <div class="form-check">
                  <input class="form-check-input" type="radio" name="menopause" value="yes">
                  <label class="form-check-label">
                    SI
                  </label>
                </div>
                <div class="form-check">
                  <input class="form-check-input" type="radio" name="menopause" value="no">
                  <label class="form-check-label">
                    NO
                  </label>
                </div>
              </div>
              <div class="form-group row">
                <label for="inputMenopauseType" class="col-sm-3">Menopause Type:</label>
                <select class="form-control col-sm-9 custom-select" name="menopauseType" id="inputMenopauseType">
                  <option value="">Choose...</option>n
                  <option value="no consta">No consta</option>
                  <option value="natural">Natural</option>
                  <option value="ovariectomia">Ovariectomia</option>
                  <option value="histeroctomia">Histeroctomia</option>
                  <option value="ambdues">Ambdues</option>
                </select>
              </div>
              <c:choose>
                  <c:when test="${param.showFormAdd != null}">
                      <button type="submit" class="btn btn-success" name="action" value="add">Add Patient</button>
                  </c:when>
                  <c:when test="${patient_to_modify != null}">
                      <input type="hidden" value="${patient_to_modify.registerId}" name="id"/>
                      <button type="submit" class="btn btn-warning" name="action" value="modify">Modify Patient</button>
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
      </div>
    </main>
    <jsp:include page="html/footer.html"/>
    <jsp:include page="html/scripts.html"/>
  </body>
</html>
