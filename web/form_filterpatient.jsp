<% if (request.getAttribute("form_filterpatient") != null) { %>
<nav class="navbar navbar-nav bg-light align-right">
    <form class="form-inline align-right" id="filter_form" method="post" action="patient">
        <div class="input-group mb-2 mr-sm-2">
            <div class="input-group-prepend">
                <div class="input-group-text">Classification</div>
            </div>
            <select class="form-control custom-select mr-sm-2" name="classification" id="classification_filter">
                <option value="">---</option>
                <option value="normal">normal</option>
                <option value="osteopenia">osteopenia</option>
                <option value="osteoporosi">osteoporosi</option>
            </select>
        </div>
        <div class="input-group mb-2 mr-sm-2">
            <div class="input-group-prepend">
                <div class="input-group-text">Menopause</div>
            </div>
            <div class="custom-control custom-radio custom-control-inline">
                <input type="radio" class="custom-control-input" name="menopause" value="si">
                <label class="custom-control-label">Sí</label>
            </div>
            <div class="custom-control custom-radio custom-control-inline">
                <input type="radio" class="custom-control-input" name="menopause" value="no" checked>
                <label class="custom-control-label">No</label>
            </div>
        </div>
        <div class="input-group mb-2 mr-sm-2">
            <div class="input-group-prepend">
                <div class="input-group-text">Menopause type</div>
            </div>
            <select class="form-control custom-select mr-sm-2" name="menopausetype">
                <option value="">---</option>n
                <option value="no_consta">no consta</option>n
                <option value="natural">natural</option>
                <option value="ovariectomia">ovariectomia</option>
                <option value="histeroctomia">histeroctomia</option>
                <option value="ambdues">ambdues</option>
            </select>
        </div>
        <button type="submit" form="filter_form" class="btn btn-primary mb-2" name="action" value="filter">Filter</button>
    </form>
</nav>
<% }%>