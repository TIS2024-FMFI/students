package com.controllers.controllers;

import com.tis.dbf.model.Student;
import com.tis.dbf.model.Students;
import com.tis.dbf.model.Studies;
import com.tis.dbf.model.Study;
import com.tis.dbf.service.DataService;
import com.tis.dbf.service.XMLParsingServes;
import jakarta.xml.bind.JAXBException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.text.Normalizer;
import java.util.List;
import java.util.Map;
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
    private Button searchButton;

    @FXML
    private Button resetButton;

    @FXML
    private TableView<Student> studentTable;

    @FXML
    private TableColumn<Student, String> columnStudent;

    @FXML
    private TableColumn<Student, String> columnBirthDate;

    @FXML
    private TableColumn<Student, String> columnStudy;

    private final XMLParsingServes xmlParsingService = new XMLParsingServes();

    private ObservableList<Student> studentList = FXCollections.observableArrayList();

    private ObservableList<Study> studyList = FXCollections.observableArrayList();

    private DataService dataService;

    public void setDataService(DataService dataService) {
        this.dataService = dataService;
    }

    @FXML
    public void initialize() {
        columnStudent.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        columnBirthDate.setCellValueFactory(new PropertyValueFactory<>("birthDate"));
        columnStudy.setCellValueFactory(new PropertyValueFactory<>("studyProgram")); // Use studyProgram column
    }

    private void displayStudents(List<Student> students) {
        studentTable.setItems(FXCollections.observableArrayList(students));
        System.out.println("Table has " + students.size() + " students.");
    }

    public void displayStudies(){
        try {
            Map<String, Study> studyMap = dataService.getStudyMap();

            Students students = dataService.getStudents();
            if (students != null) {
                studentList = FXCollections.observableArrayList(students.getStudents());

                for (Student student : studentList) {
                    Study matchedStudy = studyMap.get(student.getUPN());
                    if (matchedStudy != null) {
                        student.setStudyProgram(matchedStudy.getStudyProgramme());
                    } else {
                        student.setStudyProgram("nenačítané");
                    }
                }
            }

            displayStudents(studentList);

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Failed to initialize data: " + e.getMessage());
        }
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
                    matchedStudy = study;
                    break;
                }
            }
            if (matchedStudy != null) {
                student.setStudyProgram(matchedStudy.getStudyProgramme());
                student.setDegree(matchedStudy.getDegree());
            } else {
                student.setStudyProgram("nenačítané");
                student.setDegree("nenačítané");
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
    private String normalizeInput(String input) {
        return Normalizer.normalize(input, Normalizer.Form.NFD)
                .replaceAll("[^\\p{ASCII}]", "")
                .toLowerCase();
    }

}
