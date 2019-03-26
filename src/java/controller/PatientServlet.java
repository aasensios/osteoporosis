package controller;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// Model
import model.Patient;
import model.persist.PatientDAO;

// Graphics
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.*;
import java.util.HashMap;
import java.util.Map;
import org.jfree.data.category.DefaultCategoryDataset;

// PDF creation
import java.io.FileOutputStream;
import java.util.Date;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.OutputStream;

//@WebServlet(name = "PatientServlet", urlPatterns = {"/patients"})
@WebServlet("/patients")
public class PatientServlet extends HttpServlet {

    private String path;
    private PatientDAO patientDAO;
    private List<Patient> filteredPatients;
    private Map<String, String> messages;
    private static Font font = new Font(Font.FontFamily.HELVETICA, 12,
            Font.NORMAL, BaseColor.BLACK);

    // (!) When executed, overrides any previuos file with this filename
    private static String FILE = "patients.pdf";

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     * @throws java.lang.ClassNotFoundException
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException {

        path = getServletContext().getRealPath("/WEB-INF/");
        patientDAO = new PatientDAO(path);

        // Prepare messages.
        messages = new HashMap<>();
        request.setAttribute("messages", messages);

        String action = request.getParameter("action");

        // Switch block does NOT accept null values.
        if (action == null) {
            action = "";
        }

        // Decide which method has to be called
        switch (action) {
            case "":
                response.sendRedirect("index.jsp");
                break;
            case "list":
                list(request, response);
                break;
            case "add_form":
                showFormAdd(request, response);
                break;
            case "add":
                addPatient(request, response);
                break;
            case "modify_form":
                showModifyButtons(request, response);
                break;
            case "patient_to_modify":
                modifyPatient(request, response);
                break;
            case "modify":
                modifyThatPatient(request, response);
                break;
            case "delete_form":
                showDeleteButtons(request, response);
                break;
            case "patient_to_delete":
                deletePatient(request, response);
                break;
            case "filter_form":
                showFormFilter(request, response);
                break;
            case "filter":
                filterPatient(request, response);
                break;
            case "make_graphic_ageGroups":
                makeGraphicAgeGroups(request, response);
                break;
            case "createPDF":
                createPDF(request, response);
                break;
            default:
                response.sendRedirect("index.jsp");
        }

        // Refresh the patients JSP page after any action
        request.getRequestDispatcher("patients.jsp").forward(request, response);

    }

    /**
     * Lists all patients.
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void list(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Reset the filter attribute, to hide the create PDF button
        request.setAttribute("filtered", false);

        List<Patient> patients = patientDAO.list();
        request.setAttribute("patients", patients);

        if (patients.isEmpty()) {
            messages.put("error", "Patients list is empty");
        } else {
            messages.put("success", String.format("Total patients: %d", patients.size()));
        }

    }

    /**
     * Shows the form to add a new patient.
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void showFormAdd(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setAttribute("showFormAdd", true);

    }

    /**
     * Adds a new patient.
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void addPatient(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Instantiate patient object
        Patient patient = new Patient();

        // Postprocess request: gather and validate submitted data and display the result in the same JSP.
        // Get and validate age.
        String age = request.getParameter("age");
        if (age == null || age.trim().isEmpty()) {
            messages.put("age", "Please enter age");
        } else if (!age.matches("\\d+")) {
            messages.put("age", "Please enter digits only");
        } else {
            patient.setAge(Integer.parseInt(age));
        }

        // Get and validate weight.
        String weight = request.getParameter("weight");
        if (weight == null || weight.trim().isEmpty()) {
            messages.put("weight", "Please enter weight");
        } else if (!weight.matches("\\d+")) {
            messages.put("weight", "Please enter digits only");
        } else {
            patient.setWeight(Integer.parseInt(weight));
        }

        // Get and validate height.
        String height = request.getParameter("height");
        if (height == null || height.trim().isEmpty()) {
            messages.put("height", "Please enter height");
        } else if (!height.matches("\\d+")) {
            messages.put("height", "Please enter digits only");
        } else {
            patient.setHeight(Integer.parseInt(height));
        }

        // Get and validate classification.
        String classification = request.getParameter("classification");
        if (classification == null || classification.trim().isEmpty()) {
            messages.put("classification", "Please select classification");
        } else {
            patient.setClassification(classification.toUpperCase());
        }

        // Get and validate menarche.
        String menarche = request.getParameter("menarche");
        if (menarche == null || menarche.trim().isEmpty()) {
            messages.put("menarche", "Please enter menarche");
        } else if (!height.matches("\\d+")) {
            messages.put("menarche", "Please enter digits only");
        } else {
            patient.setMenarche(Integer.parseInt(menarche));
        }

        // Get and validate menopause.
        String menopause = request.getParameter("menopause");
        if (menopause == null || menopause.trim().isEmpty()) {
            messages.put("menopause", "Please pick menopause");
        } else {
            patient.setMenopause(menopause.equals("yes"));
        }

        // Get and validate menopauseType.
        String menopauseType = request.getParameter("menopauseType");
        if (menopauseType == null || menopauseType.trim().isEmpty()) {
            messages.put("menopauseType", "Please select menopause type");
        } else {
            patient.setMenopauseType(menopauseType.toUpperCase());
        }

        // No validation errors? Do the business job!
        if (messages.isEmpty()) {
//            Patient newPatient = new Patient(
//                    Integer.parseInt(age),
//                    Integer.parseInt(weight),
//                    Integer.parseInt(height),
//                    classification.toUpperCase(),
//                    Integer.parseInt(menarche),
//                    menopause.equals("yes"),
//                    menopauseType.toUpperCase()
//            );
            // Calculate the non-fillable properties
            patient.setAgeGroup(patient.getAge());
            patient.setImc(patient.getWeight(), patient.getHeight());
            if (patientDAO.insert(patient) == 1) {
                messages.put("success", "Patient has been added successfully.");
            }
        } else {
            messages.put("error", "Please check the fields");
            request.setAttribute("patient", patient);
        }

        // Keep showing the form, no matter success or error.
        showFormAdd(request, response);

    }

    /**
     * Gets all patients and sends them to the view.
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void showModifyButtons(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        list(request, response);
        request.setAttribute("showModifyButtons", true);

    }

    /**
     * Modifies an existing patient.
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void modifyPatient(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Get patient from form with fields separated by semicolon ';'
        String patient = request.getParameter("patient");
        String[] fields = patient.split(";");

        // Construct a patient
        Patient patientToBeModified = new Patient(
                Integer.parseInt(fields[0]),
                Integer.parseInt(fields[1]),
                fields[2],
                Integer.parseInt(fields[3]),
                Integer.parseInt(fields[4]),
                Double.parseDouble(fields[5]),
                fields[6],
                Integer.parseInt(fields[7]),
                fields[8].toUpperCase().equals("YES"),
                fields[9]
        );

        request.setAttribute("patient", patientToBeModified);

    }

    private void modifyThatPatient(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Get form fields
        String registerId = request.getParameter("registerId");
        String age = request.getParameter("age");
        String weight = request.getParameter("weight");
        String height = request.getParameter("height");
        String classification = request.getParameter("classification").toUpperCase();
        String menarche = request.getParameter("menarche");
        String menopause = request.getParameter("menopause");
        String menopauseType = request.getParameter("menopauseType").toUpperCase();

        // Null input patient handling
        if (age == null
                || weight == null
                || height == null
                || classification == null
                || menarche == null
                || menopause == null
                || menopauseType == null) {
            response.sendRedirect("patients.jsp");
        }

        // Patient construction
        Patient patientModified = new Patient(
                Integer.parseInt(registerId),
                Integer.parseInt(age),
                Integer.parseInt(weight),
                Integer.parseInt(height),
                classification.toUpperCase(),
                Integer.parseInt(menarche),
                menopause.equals("YES"),
                menopauseType.toUpperCase()
        );

        // Modify the patient in database
        if (patientDAO.update(patientModified) > 0) {
            request.setAttribute("success", "Patient has been modified successfully.");
        } else {
            request.setAttribute("error", "Patient has not been modified.");
        }

        showModifyButtons(request, response);

    }

    /**
     * Gets all patients and sends them to the view.
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void showDeleteButtons(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        list(request, response);
        request.setAttribute("showDeleteButtons", true);

    }

    private void deletePatient(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Get patient from form with fields separated by semicolon ';'.
        String patientCSV = request.getParameter("patient");
        String[] fields = patientCSV.split(";");

        // Prapare the patient to be deleted
        Patient patientToBeDeleted = new Patient();
        patientToBeDeleted.setRegisterId(Integer.parseInt(fields[0]));

        // Delete the patient from database
        int rowsAffected = patientDAO.delete(patientToBeDeleted);

        if (rowsAffected > 0) {
            messages.put("success", "Patient has been deleted successfully.");
        } else {
            switch (rowsAffected) {
                case -1:
                    messages.put("error", "Patient has not been deleted due to a constraint fail.");
                    break;
                case -2:
                    messages.put("error", "Patient has not been deleted due to an error, contact administrator.");
                    break;
                default:
                    showDeleteButtons(request, response);
            }
        }

        showDeleteButtons(request, response);

    }

    /**
     * Shows the form to filter the patients list.
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void showFormFilter(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        list(request, response);
        request.setAttribute("showFormFilter", true);

    }

    /**
     * Filters the patients list by any search criteria.
     *
     * @param request
     * @param response
     */
    private void filterPatient(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // TODO global search bar on header -----------
//        String searchCriteria = request.getParameter("searchCriteria");
//        ArrayList<Patient> patientsFiltered = patientDAO.filter(searchCriteria);
//        request.setAttribute("patients", patientsFiltered);
//        RequestDispatcher dispatcher = request.getRequestDispatcher("patients.jsp");
//        dispatcher.forward(request, response);
        //-------------
        // Get form fields from the row-inline-form
        String classification = request.getParameter("classification");
        String menopause = request.getParameter("menopause");
        String menopauseType = request.getParameter("menopauseType");

        // Filter construction
        Patient patientAsFilter = new Patient();
        patientAsFilter.setClassification(classification);
        patientAsFilter.setMenopause(menopause.equals("YES"));
        patientAsFilter.setMenopauseType(menopauseType);

        // Filtering
        filteredPatients = patientDAO.filter(patientAsFilter);
        request.setAttribute("patients", filteredPatients);
        messages.put("success", String.format("Patients filtered: %d", filteredPatients.size()));

        // Set attribute to control if the createPDF button has to be displayed or not
        request.setAttribute("filtered", true);

    }

    /**
     * Makes a graphic of the age groups of all patients.
     *
     * @param request
     * @param response
     */
    private void makeGraphicAgeGroups(HttpServletRequest request, HttpServletResponse response) {

        // Get all patients
        HashMap<String, Integer> ageGroups = patientDAO.countAgeGroups();

        // Create a simple barplot for AgeGroups
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        for (Map.Entry<String, Integer> ageGroup : ageGroups.entrySet()) {
            dataset.setValue(ageGroup.getValue(), "AgeGroups", ageGroup.getKey());
        }

        JFreeChart chart = ChartFactory.createBarChart("Ages of Osteoporosis Patients",
                "Age Group", "Patients number",
                dataset, PlotOrientation.VERTICAL, false, true, false);

        try {
            response.setContentType("image/png");
            OutputStream os = response.getOutputStream();
            ChartUtilities.writeChartAsPNG(os, chart, 625, 500);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.err.println("An error occurred while creating the graphic.");
            request.setAttribute("error", "An error occurred while creating the graphic.");
        }

    }

    /**
     * Creates a PDF file with the content of the view of the current web page.
     *
     * @param request
     * @param response
     */
    private void createPDF(HttpServletRequest request, HttpServletResponse response) {

        // TODO
        
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(FILE));

            // Open FILE
            document.open();

            // Add data to FILE
            addMetaData(document);
            addTitlePage(document);
            addContent(document);

            // Close FILE
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Add metadata
        // Add ...
//            filteredPatients AS A TABLE...
    }

    // iText allows to add metadata to the PDF which can be viewed in your Adobe
    // Reader
    // under File -> Properties
    private static void addMetaData(Document document) {
        document.addTitle("Filtered Patients");
        document.addSubject("Using iText");
        document.addKeywords("Java, PDF, iText");
        document.addAuthor("Alejandro Asensio");
        document.addCreator("Alejandro Asensio");
    }

    private static void addTitlePage(Document document)
            throws DocumentException {
        Paragraph preface = new Paragraph();
        // We add one empty line
        addEmptyLine(preface, 1);
        // Lets write a big header
        preface.add(new Paragraph("Title of the document", font));

        addEmptyLine(preface, 1);
        // Will create: Report generated by: _name, _date
        preface.add(new Paragraph(
                "Report generated by: " + System.getProperty("user.name") + ", " + new Date(), font));
        addEmptyLine(preface, 3);
        preface.add(new Paragraph(
                "This document describes something which is very important ", font));

        addEmptyLine(preface, 8);

        preface.add(new Paragraph(
                "This document is a preliminary version and not subject to your license agreement or any other agreement with vogella.com ;-).", font));

        document.add(preface);
        // Start a new page
//        document.newPage();
    }

    private static void addContent(Document document) throws DocumentException, BadElementException, IOException {
        Image img = Image.getInstance("src/images/icon-bone.jpg");
        document.add(img);

        Anchor anchor = new Anchor("First Chapter", font);
        anchor.setName("First Chapter");

        // Second parameter is the number of the chapter
        Chapter catPart = new Chapter(new Paragraph(anchor), 1);

        Paragraph subPara = new Paragraph("Subcategory 1", font);
        Section subCatPart = catPart.addSection(subPara);
        subCatPart.add(new Paragraph("Hello"));

        subPara = new Paragraph("Subcategory 2", font);
        subCatPart = catPart.addSection(subPara);
        subCatPart.add(new Paragraph("Paragraph 1"));
        subCatPart.add(new Paragraph("Paragraph 2"));
        subCatPart.add(new Paragraph("Paragraph 3"));

        // add a table
        createTable(subCatPart);

        // now add all this to the document
        document.add(catPart);

        // Next section
        anchor = new Anchor("Second Chapter", font);
        anchor.setName("Second Chapter");

        // Second parameter is the number of the chapter
        catPart = new Chapter(new Paragraph(anchor), 1);

        subPara = new Paragraph("Subcategory", font);
        subCatPart = catPart.addSection(subPara);
        subCatPart.add(new Paragraph("This is a very important message"));

        // now add all this to the document
        document.add(catPart);

    }

    private static void createTable(Section subCatPart)
            throws BadElementException {
        PdfPTable table = new PdfPTable(3);

        // t.setBorderColor(BaseColor.GRAY);
        // t.setPadding(4);
        // t.setSpacing(4);
        // t.setBorderWidth(1);
        PdfPCell c1 = new PdfPCell(new Phrase("Table Header 1"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Table Header 2"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Table Header 3"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);
        table.setHeaderRows(1);

        table.addCell("1.0");
        table.addCell("1.1");
        table.addCell("1.2");
        table.addCell("2.1");
        table.addCell("2.2");
        table.addCell("2.3");

        subCatPart.add(table);

    }

    private static void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }

// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PatientServlet.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PatientServlet.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
