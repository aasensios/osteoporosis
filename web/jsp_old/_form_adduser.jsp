<form class="form-signin" method="post" action="user">
    <img class="mb-4" src="images/icon-bone.png" alt="icon-bone" width="72" height="72">
    <p>Add new user</p>
    <label for="inputUsername" class="sr-only">Username</label>
    <input type="text" name="username" id="inputUsername" class="form-control" placeholder="Username" required autofocus>
    <label for="inputPassword" class="sr-only">Password</label>
    <input type="password" name="password" id="inputPassword" class="form-control" placeholder="Password" required>
    <label for="inputPassword" class="sr-only">Password confirm</label>
    <input type="password" name="password_confirm" id="inputPassword" class="form-control" placeholder="Repeat password" required>
    <%
        if (request.getParameter("error") != null) {
            out.println("<p id='error'>Ha ocurrido alg'un error.</p>");
        }
    %>
    <button class="btn btn-lg btn-success btn-block" type="submit" name="action" value="add">Register</button>
    <p class="mt-5 mb-3 text-muted">Institut Provençana © 2018-2019</p>
</form>
<!--<button class="btn btn-warning"><a href='landing.jsp'>Go back</a></button>-->