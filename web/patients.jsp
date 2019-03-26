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

        <!--CreatePDF button-->
        <c:if test="${filtered}">
            <a class="btn btn-info" href="patients?action=createPDF">Crate PDF</a>
            <br>
            <br>
        </c:if>

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

        <!-- Patients table -->
        <c:if test="${patients != null}">
            <form action="patients" method="post">
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
                          <c:when test="${showModifyButtons}">
                          <th scope="col">Modify Patient</th>
                          </c:when>
                          <c:when test="${showDeleteButtons}">
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
                            <c:when test="${showModifyButtons}">
                                <td scope="row">
                                  <button class="btn btn-warning" type="submit" value="${patient.registerId};${patient.age};${patient.ageGroup};${patient.weight};${patient.height};${patient.imc};${patient.classification};${patient.menarche};${patient.menopause};${patient.menopauseType}" name="patient">Modify</button>
                                  <input type="hidden" name="action" value="patient_to_modify"/>
                                </td>
                            </c:when>
                            <c:when test="${showDeleteButtons}">
                                <td scope="row">
                                  <button class="btn btn-danger" type="submit" value="${patient.registerId};${patient.age};${patient.ageGroup};${patient.weight};${patient.height};${patient.imc};${patient.classification};${patient.menarche};${patient.menopause};${patient.menopauseType}" name="patient" onclick="confirm(Are you sure?)">Delete</button>
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

        <!-- Forms: Add and Modify -->
        <c:if test="${showFormAdd || patient != null}" >

            <form action="patients" method="post">
              <div class="form-group row">
                <label for="inputAge" class="col-sm-3">Age:</label>
                <input type="number" class="form-control col-sm-6" id="inputAge" name="age" placeholder="Age"
                       value="<c:out value="${patient.age}"/>">
                <c:if test="${messages != null}"><span class="text-danger col-sm-3">${messages.age}</span></c:if>

                </div>
                <div class="form-group row">
                  <label for="inputWeight" class="col-sm-3">Weight (kg):</label>
                  <input type="number" class="form-control col-sm-6" id="inputWeight" name="weight" placeholder="Weight"
                         value="<c:out value="${patient.weight}"/>">
                <c:if test="${messages != null}"><span class="text-danger col-sm-3">${messages.weight}</span></c:if>

                </div>
                <div class="form-group row">
                  <label for="inputHeight" class="col-sm-3">Height (m):</label>
                  <input type="number" class="form-control col-sm-6" id="inputHeight" name="height" placeholder="Height"
                         value="<c:out value="${patient.height}"/>">
                <c:if test="${messages != null}"><span class="text-danger col-sm-3">${messages.height}</span></c:if>

                </div>
                <div class="form-group row">
                  <label for="inputClassification" class="col-sm-3">Classification:</label>
                  <select class="form-control col-sm-6 custom-select" name="classification" id="inputClassification">
                    <option value="">Choose...</option>
                    <option value="normal">Normal</option>
                    <option value="osteopenia">Osteopenia</option>
                    <option value="osteoporosi">Osteoporosi</option>
                  </select>
                <c:if test="${messages != null}"><span class="text-danger col-sm-3">${messages.classification}</span></c:if>

                </div>
                <div class="form-group row">
                  <label for="inputMenarche" class="col-sm-3">Menarche:</label>
                  <input type="number" class="form-control col-sm-6" id="inputMenarche" name="menarche" placeholder="Menarche"
                         value="<c:out value="${patient.menarche}"/>">
                <c:if test="${messages != null}"><span class="text-danger col-sm-3">${messages.menarche}</span></c:if>

                </div>
                <div class="form-group row">
                  <label for="inputMenopause" class="col-sm-3">Menopause:</label>
                  <div class="col-sm-6">
                    <div class="form-check">
                      <input class="form-check-input" type="radio" id="inputMenopause" name="menopause" value="yes">
                      <label class="form-check-label">
                        SI
                      </label>
                    </div>
                    <div class="form-check">
                      <input class="form-check-input" type="radio" id="inputMenopause" name="menopause" value="no">
                      <label class="form-check-label">
                        NO
                      </label>
                    </div>
                  </div>
                <c:if test="${messages != null}"><span class="text-danger col-sm-3">${messages.menopause}</span></c:if>

                </div>
                <div class="form-group row">
                  <label for="inputMenopauseType" class="col-sm-3">Menopause Type:</label>
                  <select class="form-control col-sm-6 custom-select" name="menopauseType" id="inputMenopauseType">
                    <option value="">Choose...</option>n
                    <option value="no consta">No consta</option>
                    <option value="natural">Natural</option>
                    <option value="ovariectomia">Ovariectomia</option>
                    <option value="histeroctomia">Histeroctomia</option>
                    <option value="ambdues">Ambdues</option>
                  </select>
                <c:if test="${messages != null}"><span class="text-danger col-sm-3">${messages.menopauseType}</span></c:if>
                </div>

              <c:choose>
                  <c:when test="${showFormAdd}">
                      <button type="submit" class="btn btn-success" name="action" value="add">Add Patient</button>
                  </c:when>
                  <c:when test="${patient != null}">
                      <input type="hidden" value="${patient.registerId}" name="registerId"/>
                      <button type="submit" class="btn btn-warning" name="action" value="modify">Modify Patient</button>
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
