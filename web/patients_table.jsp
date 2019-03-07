<%@page import="java.util.List"%>
<%@page import="model.Patient"%>
<%
    if (request.getAttribute("patients") != null) {
        List<Patient> patients = (List<Patient>) request.getAttribute("patients");
        StringBuilder dynamicTable = new StringBuilder("");
        if (session.getAttribute("username") == null) {
            dynamicTable.append("<table><thead><tr>"
                    + "<th scope='col'>Age Group</th>"
                    + "<th scope='col'>Weight</th>"
                    + "<th scope='col'>IMC</th>"
                    + "<th scope='col'>Classification</th>"
                    + "</tr></thead><tbody>");
            for (Patient patient : patients) {
                dynamicTable.append(String.format("<tr>"
                        + "<td scope='row'>%s</td>"
                        + "<td>%d</td>"
                        + "<td>%.2f</td>"
                        + "<td>%s</td>"
                        + "</tr>",
                        patient.getAgeGroup(),
                        patient.getWeight(),
                        patient.getImc(),
                        patient.getClassification()));
            }
        } else {
            dynamicTable.append("<table><thead><tr>"
                    + "<th scope='col'>Age</th>"
                    + "<th scope='col'>Age Group</th>"
                    + "<th scope='col'>Weight</th>"
                    + "<th scope='col'>Height</th>"
                    + "<th scope='col'>IMC</th>"
                    + "<th scope='col'>Classification</th>"
                    + "<th scope='col'>Menarche</th>"
                    + "<th scope='col'>Menopause</th>"
                    + "<th scope='col'>Menopause type</th>"
                    + "</tr></thead><tbody>");
            for (Patient patient : patients) {
                dynamicTable.append(String.format("<tr>"
                        + "<td scope='row'>%d</td>"
                        + "<td>%s</td>"
                        + "<td>%d</td>"
                        + "<td>%d</td>"
                        + "<td>%.2f</td>"
                        + "<td>%s</td>"
                        + "<td>%d</td>"
                        + "<td>%s</td>"
                        + "<td>%s</td>"
                        + "</tr>",
                        patient.getAge(),
                        patient.getAgeGroup(),
                        patient.getWeight(),
                        patient.getHeight(),
                        patient.getImc(),
                        patient.getClassification(),
                        patient.getMenarche(),
                        patient.isMenopause() ? "SÍ" : "NO",
                        patient.getMenopauseType()));
            }
        }
        out.println(dynamicTable.toString());
    }
%>