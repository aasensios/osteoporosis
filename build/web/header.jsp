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
        <c:if test="${logged_in}">
            <li class="nav-item dropdown">
              <a class="nav-link dropdown-toggle" href="#" id="dropdown01" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Patients</a>
              <div class="dropdown-menu" aria-labelledby="dropdown01">
                <a class="dropdown-item" href="patients?action=list">List</a>
                <a class="dropdown-item" href="patients?action=add_form">Add</a>
                <a class="dropdown-item" href="patients?action=modify_form">Modify</a>
                <a class="dropdown-item" href="patients?action=delete_form">Delete</a>
                <a class="dropdown-item" href="patients?action=filter_form">Filter</a>
                <a class="dropdown-item" href="patients?action=make_graphic_ageGroups">Make Graphic</a>
                <!--<a class="dropdown-item" href="patients?action=make_graphic_classifications">Make Graphic</a>-->
              </div>
            </li>
            <c:if test="${role == 'admin'}">
                <li class="nav-item dropdown">
                  <a class="nav-link dropdown-toggle" href="#" id="dropdown02" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Users</a>
                  <div class="dropdown-menu" aria-labelledby="dropdown02">
                    <a class="dropdown-item" href="users?action=list">List</a>
                    <a class="dropdown-item" href="users?action=add_form">Add</a>
                    <a class="dropdown-item" href="users?action=modify_form">Modify</a>
                    <a class="dropdown-item" href="users?action=delete_form">Delete</a>
                  </div>
                </li>
            </c:if>
        </c:if>
      </ul>
    </div>
    <!--TODO: unified search bar...-->
    <!--    <form class="form-inline mt-2 mt-md-0" action="patients" method="GET">
          <input class="form-control mr-sm-2" type="text" placeholder="TODO: unified search bar..." aria-label="Search" name="searchCriteria">
          <button class="btn btn-outline-success my-2 my-sm-0" type="submit" name="action" value="filter">Search</button>
        </form>-->
    <c:choose>
        <c:when test="${!logged_in}">
            <a class="btn btn-primary my-2 my-sm-0" href='login.jsp'>Login</a>
        </c:when>
        <c:otherwise>
            <a class="btn text-warning">${username}</a>
            <a class="btn btn-warning my-2 my-sm-0" href="users?action=logout">Logout</a>
        </c:otherwise>
    </c:choose>
  </nav>
</header>
