<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<header>
  <!-- Fixed navbar -->
  <!--<nav class="navbar navbar-expand-md navbar-dark bg-dark fixed-top">-->
  <nav class="navbar navbar-expand-md navbar-dark bg-dark">
    <a class="navbar-brand" href="index.jsp">
      <img src="images/icon-bone.png" width="72" height="72" class="d-inline-block align-middle" alt="icon-bone">
      Osteoporosis
    </a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarsExampleDefault" aria-controls="navbarsExampleDefault" aria-expanded="false" aria-label="Toggle navigation">
      <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarsExampleDefault">
      <ul class="navbar-nav mr-auto">
        <li class="nav-item active">
          <a class="nav-link" href="index.jsp">Home</a>
        </li>
        <li class="nav-item dropdown">
          <a class="nav-link dropdown-toggle" href="#" id="dropdown01" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Patients</a>
          <div class="dropdown-menu" aria-labelledby="dropdown01">
            <a class="dropdown-item" href="patient_controller?action=list_all">List All Patients</a>
            <a class="dropdown-item" href="patient_controller?action=add_form">Add Patient</a>
            <a class="dropdown-item" href="patient_controller?action=modify_form">Modify Patient</a>
            <a class="dropdown-item" href="patient_controller?action=delete_form">Delete Patient</a>
          </div>
        </li>
        <c:if test="${logged_in && role == 'admin'}">
            <li class="nav-item dropdown">
              <a class="nav-link dropdown-toggle" href="#" id="dropdown02" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Users</a>
              <div class="dropdown-menu" aria-labelledby="dropdown02">
                <a class="dropdown-item" href="user_controller?action=list_all">List All Users</a>
                <!--<a class="dropdown-item" href="user_controller?action=add_form">Add User</a>-->
                <!--<a class="dropdown-item" href="user_controller?action=modify_form">Modify User</a>-->
                <!--<a class="dropdown-item" href="user_controller?action=delete_form">Delete User</a>-->
              </div>
            </li>
        </c:if>
      </ul>
    </div>
    <c:if test="${!logged_in}">
        <a class="btn btn-primary my-2 my-sm-0" href='login.jsp'>Login</a>
    </c:if>
    <c:if test="${logged_in}">
        <a class="btn text-success">${username}</a>
        <a class="btn btn-secondary my-2 my-sm-0" href='user_controller?action=logout'>Logout</a>
    </c:if>
    <!--        <form class="form-inline mt-2 mt-md-0">
              <input class="form-control mr-sm-2" type="text" placeholder="Search" aria-label="Search">
              <button class="btn btn-outline-success my-2 my-sm-0" type="submit">Search</button>
            </form>-->
  </nav>
</header>
