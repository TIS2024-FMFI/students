package com.controllers.controllers;

import com.tis.dbf.model.Student;
import com.tis.dbf.model.Study;
import com.tis.dbf.service.DataService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MainSceneController {

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private DatePicker birthDateField;

    @FXML
    private TextField birthPlaceField;

    @FXML
    private Button searchButton;

    @FXML
    private Button resetButton;

    @FXML
    private TableView<Study> studiesTable;

    @FXML
    private TableColumn<Study, String> columnStudentName;

    @FXML
    private TableColumn<Study, String> columnBirthDate;

    @FXML
    private TableColumn<Study, String> columnStudyProgramme;

    private ObservableList<Study> allStudies = FXCollections.observableArrayList();
    private DataService dataService;

    @FXML
    private Label labelDetail;
    @FXML
    private Label labelFirstName;
    @FXML
    private Label labelLastName;
    @FXML
    private Label labelBirthDate;
    @FXML
    private Label labelStudyProgram;
    @FXML
    private Label labelStudyStart;
    @FXML
    private Label labelStudyEnd;

    public void setDataService(DataService dataService) {
        this.dataService = dataService;
    }

    @FXML
    public void initialize() {
        studiesTable.getColumns().clear();
        studiesTable.getColumns().addAll(columnStudentName, columnBirthDate, columnStudyProgramme);
        columnStudentName.setCellValueFactory(new PropertyValueFactory<>("studentName"));
        columnBirthDate.setCellValueFactory(new PropertyValueFactory<>("birthDate"));
        columnStudyProgramme.setCellValueFactory(new PropertyValueFactory<>("studyProgramme"));
    }

    public void loadAllStudies() {
        // Get the map of UPN to list of studies
        Map<String, List<Study>> studyMap = dataService.getStudyMap();
        List<Student> students = dataService.getStudents().getStudents();

        // Enrich studies with student details
        allStudies = FXCollections.observableArrayList(
                students.stream()
                        .flatMap(student -> {
                            List<Study> matchedStudies = studyMap.get(student.getUPN());
                            if (matchedStudies != null) {
                                return matchedStudies.stream().peek(study -> {
                                    study.setStudentName(student.getFullName());
                                    study.setBirthDate(student.getBirthDate());
                                    study.setFirstName(student.getFirstName());
                                    study.setLastName(student.getLastName());
                                });
                            }
                            return Stream.empty();
                        })
                        .collect(Collectors.toList())
        );

        // Populate the TableView with all studies
        studiesTable.setItems(allStudies);
    }

    @FXML
    private void handleSearch() {
        String firstName = firstNameField.getText().trim().toLowerCase();
        String lastName = lastNameField.getText().trim().toLowerCase();
        String birthDate = birthDateField.getValue() != null ? birthDateField.getValue().toString() : "";
        String birthPlace = birthPlaceField.getText().trim().toLowerCase();

        // Filter students based on search criteria
        List<Student> filteredStudents = dataService.getStudents().getStudents().stream()
                .filter(student -> (firstName.isEmpty() || student.getFirstName().toLowerCase().contains(firstName)) &&
                        (lastName.isEmpty() || student.getLastName().toLowerCase().contains(lastName)) &&
                        (birthDate.isEmpty() || student.getBirthDate().equals(birthDate)) &&
                        (birthPlace.isEmpty() || student.getBirthdayPlace().toLowerCase().contains(birthPlace)))
                .collect(Collectors.toList());

        // Display relevant studies
        displayRelevantStudies(filteredStudents);
    }

    private void displayRelevantStudies(List<Student> filteredStudents) {
        Map<String, List<Study>> studyMap = dataService.getStudyMap();

        // Enrich and flatten studies from filtered students
        ObservableList<Study> relevantStudies = FXCollections.observableArrayList(
                filteredStudents.stream()
                        .flatMap(student -> {
                            List<Study> matchedStudies = studyMap.get(student.getUPN());
                            if (matchedStudies != null) {
                                return matchedStudies.stream().peek(study -> {
                                    study.setStudentName(student.getFullName());
                                    study.setBirthDate(student.getBirthDate());
                                });
                            }
                            return Stream.empty();
                        })
                        .collect(Collectors.toList())
        );

        // Populate the TableView with relevant studies
        studiesTable.setItems(relevantStudies);
    }

    @FXML
    private void handleReset() {
        firstNameField.clear();
        lastNameField.clear();
        birthDateField.setValue(null);
        birthPlaceField.clear();

        // Reset the TableView to display all studies
        studiesTable.setItems(allStudies);
    }

    public void showStudyDetail(javafx.event.ActionEvent actionEvent) {
        Study selectedStudy = studiesTable.getSelectionModel().getSelectedItem();

        if (selectedStudy != null) {
            // Update labels with study details
            labelDetail.setText(selectedStudy.getUPN());
            labelFirstName.setText(selectedStudy.getStudentName());
            labelLastName.setText(selectedStudy.getStudentName().split(" ")[1]); // Assuming "First Last" format
            labelBirthDate.setText(selectedStudy.getBirthDate());
            labelStudyProgram.setText(selectedStudy.getStudyProgramme());
            labelStudyStart.setText((selectedStudy.getStudyAdmission() != null
                    ? selectedStudy.getStudyAdmission().getDate() : "N/A"));
            labelStudyEnd.setText((selectedStudy.getStudyEnd() != null
                    ? selectedStudy.getStudyEnd().getThesis().getDefenceDate() : "N/A"));
        } else {
            // Show alert if no study is selected
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Study Selected");
            alert.setHeaderText(null);
            alert.setContentText("Please select a study to view details.");
            alert.showAndWait();
        }
    }
}
