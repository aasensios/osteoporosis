<fieldset>
    <form method="post" action="patient">
        <div class="form-group row">
            <label class="col-sm-2 col-form-label">Your age</label>
            <div class="col-sm-10">
                <input type="number" class="form-control" name="age" placeholder="Age">
            </div>
        </div>
        <div class="form-group row">
            <label class="col-sm-2 col-form-label">Weight</label>
            <div class="col-sm-10">
                <input type="number" class="form-control" name="weight" placeholder="kg">
            </div>
        </div>
        <div class="form-group row">
            <label class="col-sm-2 col-form-label">Height</label>
            <div class="col-sm-10">
                <input type="number" class="form-control" name="height" placeholder="cm">
            </div>
        </div>
        <div class="form-group row">
            <label class="col-sm-2 col-form-label">Classification</label>
            <select class="col-sm-10 custom-select" name="classification">
                <option value="">--- Select one option ---</option>
                <option value="normal">Normal</option>
                <option value="osteopenia">Osteopenia</option>
                <option value="osteoporosi">Osteoporosi</option>
            </select>
        </div>
        <div class="form-group row">
            <label class="col-sm-2 col-form-label">Menarche</label>
            <div class="col-sm-10">
                <input type="number" class="form-control" name="menarche" placeholder="Age when first period">
            </div>
        </div>
        <fieldset class="form-group">
            <div class="row">
                <legend class="col-form-label col-sm-2 pt-0">Menopause</legend>
                <div class="col-sm-10">
                    <div class="form-check">
                        <input class="form-check-input" type="radio" name="menopause" value="si">
                        <label class="form-check-label">
                            Sí
                        </label>
                    </div>
                    <div class="form-check">
                        <input class="form-check-input" type="radio" name="menopause" value="no">
                        <label class="form-check-label">
                            No
                        </label>
                    </div>
                </div>
            </div>
        </fieldset>
        <div class="form-group row">
            <label class="col-sm-2 col-form-label">Menopause type</label>
            <select class="col-sm-10 custom-select" name="menopauseType">
                <option value="">--- Select one option ---</option>n
                <option value="no_consta">No consta</option>n
                <option value="natural">Natural</option>
                <option value="ovariectomia">Ovariectomia</option>
                <option value="histeroctomia">Histeroctomia</option>
                <option value="ambdues">Ambdues</option>
            </select>
        </div>
        <div class="form-group row">
            <div class="col-sm-10">
                <button type="submit" class="btn btn-primary" name="action" value="add">Add patient</button>
            </div>
        </div>
    </form>
</fieldset>
