package com.example.login;

import com.tis.dbf.model.*;
import com.tis.dbf.model.Event;
import com.tis.dbf.service.XMLParsingServes;
import jakarta.xml.bind.JAXBException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.awt.*;
import java.text.Normalizer;
import java.util.List;
import java.util.stream.Collectors;

public class MainSceneController {

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField secondNameField;

    @FXML
    private TextField secondOriginNameField;

    @FXML
    private DatePicker birthDateField;

    @FXML
    private TextField birthPlaceField;

    @FXML
    private Button ButtonSocialnaPoistovna;

    @FXML
    private Button ButtonVypisZnamok;

    @FXML
    private Button ButtonDiplom;

    @FXML
    private TableView<Student> studentTable;

    @FXML
    private TableColumn<Student, String> columnStudent;

    @FXML
    private TableColumn<Student, String> columnBirthDate;

    @FXML
    private TableColumn<Student, String> columnStudy;

    @FXML
    private Label labelFirstName;

    @FXML
    private Label labelLastName;

    @FXML
    private Label labelBirthDate;

    @FXML
    private Label FixLabelFirstName;

    @FXML
    private Label FixLabelDetails;

    @FXML
    private Label FixLabelPersonalData;

    @FXML
    private Label FixLabelLastName;

    @FXML
    private Label FixLabelBirthDate;

    @FXML
    private Label FixLabelStudyData;

    @FXML
    private Label FixLabelStudyProgramme;

    @FXML
    private Label FixLabelStudyStart;

    @FXML
    private Label FixLabelGraduate;

    @FXML
    private Label FixLabelYears;

    @FXML
    private Label labelStudyStartDate;

    @FXML
    private Label labelStudyProgramme;

    @FXML
    private Label labelGraduate;

    @FXML
    private Label labelCountYears;

    @FXML
    private Label FixLabelTo;

    @FXML
    private Label labelFinishStudy;

    @FXML
    private Label labelStartStudy;

    @FXML
    private Label FixLabelFrom;

    @FXML
    private Label FixLabelCountYears;

    @FXML
    private TableView<Event> eventsTable;

    @FXML
    private TableColumn<Event, String> columnReason;

    @FXML
    private TableColumn<Event, String> columnStartDate;

    @FXML
    private TableColumn<Event, String> columnEndDate;

    private ObservableList<Event> extraList = FXCollections.observableArrayList();




    @FXML
    private Button showDetailsButton;

    private final XMLParsingServes xmlParsingService = new XMLParsingServes();

    private ObservableList<Student> studentList = FXCollections.observableArrayList();

    private ObservableList<Study> studyList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        columnStudent.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        columnBirthDate.setCellValueFactory(new PropertyValueFactory<>("birthDate"));
        columnStudy.setCellValueFactory(new PropertyValueFactory<>("studyProgramDeg"));
        loadStudiesFromXML();
        loadStudentsFromXML();
        columnReason.setCellValueFactory(new PropertyValueFactory<>("reason"));
        columnStartDate.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        columnEndDate.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        handleReset();

//        displayStudents(studentList);
    }

    @FXML
    private void handleSearch() {
//        System.out.println("Stlacene");

        String firstName = firstNameField.getText().trim().toLowerCase();
        String lastName = secondNameField.getText().trim().toLowerCase();
        String lastOrigName = secondOriginNameField.getText().trim().toLowerCase();
        String birthDate = birthDateField.getValue() != null ? birthDateField.getValue().toString() : "";
        String birthPlace = birthPlaceField.getText().trim().toLowerCase();

        List<Student> filteredStudents = studentList.stream()
                .filter(student -> (firstName.isEmpty() || normalizeInput(student.getFirstName().toLowerCase()).startsWith(normalizeInput(firstName))) &&
                        (lastName.isEmpty() || normalizeInput(student.getSecondName().toLowerCase()).startsWith(normalizeInput(lastName))) &&
                        (lastOrigName.isEmpty() || normalizeInput(student.getBirthName().toLowerCase()).startsWith(normalizeInput(lastOrigName))) &&
                        (birthDate.isEmpty() || student.getBirthDate().equals(birthDate)) &&
                        (birthPlace.isEmpty() || normalizeInput(student.getBirthdayPlace().toLowerCase()).startsWith(normalizeInput(birthPlace))))
                .collect(Collectors.toList());

        displayStudents(filteredStudents);
    }
    //
    @FXML
    private void handleReset() {
        firstNameField.clear();
        secondNameField.clear();
        secondOriginNameField.clear();
        birthDateField.setValue(null);
        birthPlaceField.clear();
        displayStudents(studentList);
        FixLabelFirstName.setText("");
        FixLabelLastName.setText("");
        FixLabelBirthDate.setText("");
        FixLabelStudyData.setText("");
        FixLabelPersonalData.setText("");
        FixLabelDetails.setText("");
        FixLabelGraduate.setText("");
        FixLabelStudyStart.setText("");
        FixLabelStudyProgramme.setText("");
        labelFirstName.setText("");
        labelLastName.setText("");
        labelBirthDate.setText("");
        FixLabelYears.setText("");
        labelStudyStartDate.setText("");
        labelStudyProgramme.setText("");
        labelGraduate.setText("");
        labelCountYears.setText("");
        FixLabelFrom.setText("");
        FixLabelCountYears.setText("");
        FixLabelTo.setText("");
        labelCountYears.setText("");
        labelStartStudy.setText("");
        labelFinishStudy.setText("");
        ButtonSocialnaPoistovna.setVisible(false);
        ButtonVypisZnamok.setVisible(false);
        ButtonDiplom.setVisible(false);
        eventsTable.setVisible(false);
    }
    //
    private void loadStudentsFromXML() {
        try {
            Students students = xmlParsingService.parseStudents("studentsExample.xml");
            studentList = FXCollections.observableArrayList(students.getStudents());
            for (Student student : studentList) {
                Study matchedStudy = null;
                for (Study study : studyList) {
                    if (study.getUPN() != null
                            && study.getUPN().equals(student.getUPN())) {
                        System.out.println(study);
                        matchedStudy = study;
                        break;
                    }
                }
                if (matchedStudy != null) {
                    student.setStudyProgramme(matchedStudy.getStudyProgramme());
                    student.setDegree(matchedStudy.getDegree());
                    student.setStudyRegistration(matchedStudy.getStudyRegistration());
                    student.setStudyStatus(matchedStudy.getStudyStatus());
                    student.setNumOfYears(matchedStudy.getNumOfYears());
                    student.setStudyStartYear(matchedStudy.getStudyStartYear());
                    student.setStudyEndYear(matchedStudy.getStudyEndYear());
                    student.setInterruptions(matchedStudy.getInterruptions());
                    student.setAbroadProgrammes(matchedStudy.getAbroadProgrammes());
    //                student.setAcademicYears(matchedStudy.getAcademicYears());
                } else {
                    student.setStudyProgramme("nenačítané");
                    student.setDegree("nenačítané");
                    student.setStudyRegistration("nenačítané");
                    student.setStudyStatus("nenačítané");
                    student.setNumOfYears(-1);
                    student.setStudyStartYear("nenačítané");
                    student.setStudyEndYear("nenačítané");

                }
            }
            displayStudents(studentList);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    private void loadStudiesFromXML() {
        try {
            Studies studies = xmlParsingService.parseStudiesXml("StudiesTEST.xml");
            studyList = FXCollections.observableArrayList(studies.getStudies());
            displayStudents(studentList);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }


    //
    private void displayStudents(List<Student> students) {
        studentTable.setItems(FXCollections.observableArrayList(students));
        System.out.println("tabulka ma: " + students.size());
    }
    private String normalizeInput(String input) {
        return Normalizer.normalize(input, Normalizer.Form.NFD)
                .replaceAll("[^\\p{ASCII}]", "")
                .toLowerCase();
    }

    @FXML
    private void handleDetails() {
        // Získať vybraného študenta z tabuľky
        Student selectedStudent = studentTable.getSelectionModel().getSelectedItem();

        if (selectedStudent != null) {
            // Nastaviť detaily do štítkov
            labelFirstName.setText(selectedStudent.getFirstName());
            labelLastName.setText(selectedStudent.getSecondName());
            labelBirthDate.setText(selectedStudent.getBirthDate());
            FixLabelDetails.setText("DETAIL");
            FixLabelFirstName.setText("Meno");
            FixLabelLastName.setText("Priezvisko");
            FixLabelBirthDate.setText("Dátum narodenia");
            FixLabelStudyData.setText("ŠTÚDIJNÉ ÚDAJE");
            FixLabelPersonalData.setText("OSOBNÉ ÚDAJE");
            FixLabelGraduate.setText("Status štúdia");
            FixLabelStudyStart.setText("Začiatok štúdia");
            FixLabelStudyProgramme.setText("Študijný program");
            FixLabelYears.setText("Priebeh štúdia / akademické roky");
            labelStudyProgramme.setText(selectedStudent.getStudyProgramme());
            labelStudyStartDate.setText(selectedStudent.getStudyRegistration());
            labelGraduate.setText(selectedStudent.getStudyStatus());

//            if (selectedStudent.getStudyStatus()=="Absolvované") {
//                labelGraduate.setText("Áno");
//            }
            ButtonSocialnaPoistovna.setVisible(true);
            ButtonVypisZnamok.setVisible(true);
            ButtonDiplom.setVisible(true);
            FixLabelFrom.setText("Od");
            FixLabelCountYears.setText("Doba štúdia študenta: ");
            FixLabelTo.setText("Do");
            if (selectedStudent.getNumOfYears() == 1) {
                labelCountYears.setText(String.valueOf(selectedStudent.getNumOfYears()) + " rok");
            } else if (selectedStudent.getNumOfYears() == 2 || selectedStudent.getNumOfYears() == 3 || selectedStudent.getNumOfYears() == 4) {
                labelCountYears.setText(String.valueOf(selectedStudent.getNumOfYears()) + " roky");
            } else if (selectedStudent.getNumOfYears() > 4) {
                labelCountYears.setText(String.valueOf(selectedStudent.getNumOfYears()) + " rokov");
            } else labelCountYears.setText("nenacitane");
            labelStartStudy.setText(selectedStudent.getStudyStartYear());
            labelFinishStudy.setText(selectedStudent.getStudyEndYear());
            Study matchedStudy = studyList.stream()
                    .filter(study -> study.getUPN().equals(selectedStudent.getUPN()))
                    .findFirst()
                    .orElse(null);
            extraList.clear();

            // prerusenie
            if (selectedStudent.getInterruptions() != null && !selectedStudent.getInterruptions().isEmpty()) {
                for (Interruption interruption : selectedStudent.getInterruptions()) {
                    if (interruption.getReason() != null && !interruption.getReason().contains("PRERUŠENIE")) {
                        String combinedReason = "PRERUŠENIE: " + interruption.getReason();
                        interruption.setReason(combinedReason);
                    }
                    extraList.add(new Event(interruption));
                }
            }

            // studium zahranicie
            if (selectedStudent.getAbroadProgrammes() != null && !selectedStudent.getAbroadProgrammes().isEmpty()) {
                for (AbroadProgramme abroadProgramme : selectedStudent.getAbroadProgrammes()) {
                    if (abroadProgramme.getUniversity() != null && !abroadProgramme.getUniversity().contains("ERAZMUS")) {
                        String combinedReason = "ERAZMUS: " + abroadProgramme.getUniversity();
                        abroadProgramme.setUniversity(combinedReason);
                    }
                    extraList.add(new Event(abroadProgramme));
                }
            }
            eventsTable.setItems(extraList);

            if (extraList.isEmpty()) {
                eventsTable.setVisible(false);
            } else {
                eventsTable.setVisible(true);
            }
        } else {
            clearLabels();
            eventsTable.setVisible(false);
        }

    }




    private void clearLabels() {
        Label[] labels = {
                FixLabelFirstName, FixLabelLastName, FixLabelBirthDate,
                FixLabelStudyData, FixLabelPersonalData, FixLabelDetails,
                FixLabelGraduate, FixLabelStudyStart, FixLabelStudyProgramme,
                labelFirstName, labelLastName, labelBirthDate, FixLabelYears, labelStudyStartDate,
                labelGraduate
        };

        for (Label label : labels) {
            label.setText("");
        }
    }





}