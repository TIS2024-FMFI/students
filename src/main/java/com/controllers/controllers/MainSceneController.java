package com.controllers.controllers;

import com.tis.dbf.model.*;
import com.tis.dbf.model.Event;
import com.tis.dbf.service.DataService;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

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
    private Button ButtonSocialnaPoistovna;

    @FXML
    private Button ButtonVypisZnamok;

    @FXML
    private Button ButtonDiplom;

    @FXML
    private TableView<Study> studiesTable;

    @FXML
    private TableColumn<Study, String> columnStudent;

    @FXML
    private TableColumn<Study, String> columnBirthDate;

    @FXML
    private TableColumn<Study, String> columnStudy;

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
    private DataService dataService;

    @FXML
    public void initialize() {
        // Set up the study program column (direct property in Study)
        columnStudy.setCellValueFactory(new PropertyValueFactory<>("studyProgramme"));

        // Set up the student name column (nested property in Student)
        columnStudent.setCellValueFactory(cellData -> {
            Student student = cellData.getValue().getStudent(); // Access the nested Student object
            return student != null
                    ? new SimpleStringProperty(student.getFullName()) // Extract fullName from Student
                    : new SimpleStringProperty("Unknown");
        });

        // Set up the birth date column (nested property in Student)
        columnBirthDate.setCellValueFactory(cellData -> {
            Student student = cellData.getValue().getStudent(); // Access the nested Student object
            return student != null
                    ? new SimpleStringProperty(student.getBirthDate()) // Extract birthDate from Student
                    : new SimpleStringProperty("Unknown");
        });
    }

    public void loadAllStudies(){
        Map<String, List<Study>> studyMap = dataService.getStudyMap();
        List<Student> students = dataService.getStudents().getStudents();

        // Create a map of UPN to Student for efficient lookup
        Map<String, Student> studentMap = students.stream()
                .collect(Collectors.toMap(Student::getUPN, student -> student));

        // Flatten the studies and link each study to its corresponding student
        List<Study> allStudiesWithStudents = studyMap.values().stream()
                .flatMap(List::stream)
                .peek(study -> {
                    if (studentMap.containsKey(study.getUPN())) {
                        study.setStudent(studentMap.get(study.getUPN()));
                    }
                })
                .collect(Collectors.toList());

        // Update the TableView
        ObservableList<Study> observableStudies = FXCollections.observableArrayList(allStudiesWithStudents);
        studiesTable.setItems(observableStudies);
    }

    public void setDataService(DataService dataService) {
        this.dataService = dataService;
    }

    public void handleDetails(ActionEvent actionEvent) {
    }

    public void handleSearch(ActionEvent actionEvent) {
    }

    public void handleReset(ActionEvent actionEvent) {
    }
}