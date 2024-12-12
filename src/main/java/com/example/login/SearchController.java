package com.example.login;

import com.tis.dbf.model.Student;
import com.tis.dbf.model.Students;
import com.tis.dbf.service.XMLParsingServes;
import jakarta.xml.bind.JAXBException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.text.Normalizer;
import java.util.List;
import java.util.stream.Collectors;

public class SearchController {

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

    @FXML
    public void initialize() {
        columnStudent.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        columnBirthDate.setCellValueFactory(new PropertyValueFactory<>("birthDate"));
        columnStudy.setCellValueFactory(new PropertyValueFactory<>("studyProgram"));

        loadStudentsFromXML();
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
                .filter(student -> (firstName.isEmpty() || normalizeInput(student.getFirstName().toLowerCase()).startsWith(firstName)) &&
                        (lastName.isEmpty() || normalizeInput(student.getSecondName().toLowerCase()).startsWith(lastName)) &&
                        (lastOrigName.isEmpty() || normalizeInput(student.getBirthName().toLowerCase()).startsWith(lastOrigName)) &&
                        (birthDate.isEmpty() || student.getBirthDate().equals(birthDate)) &&
                        (birthPlace.isEmpty() || normalizeInput(student.getBirthdayPlace().toLowerCase()).startsWith(birthPlace)))
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
            displayStudents(studentList);
        } catch (JAXBException e) {
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

}
