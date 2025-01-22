package controllers;

import others.model.*;
import others.service.DataService;
import others.service.DocumentExporter;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.text.Normalizer;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MainSceneController {

    private static final DateTimeFormatter INPUT_DATE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");


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

    private ObservableList<Event> eventsList = FXCollections.observableArrayList();

    @FXML
    private Button showDetailsButton;

    private DataService dataService;
    private ObservableList<Study> studyList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        columnStudy.setCellValueFactory(new PropertyValueFactory<>("studyProgramme"));

        columnStudent.setCellValueFactory(cellData -> {
            Student student = cellData.getValue().getStudent();
            return student != null
                    ? new SimpleStringProperty(student.getFullName())
                    : new SimpleStringProperty("Unknown");
        });
        columnBirthDate.setCellValueFactory(cellData -> {
            Student student = cellData.getValue().getStudent();
            return student != null
                    ? new SimpleStringProperty(student.getBirthDate())
                    : new SimpleStringProperty("Unknown");
        });

        columnReason.setCellValueFactory(new PropertyValueFactory<>("reason"));
        columnStartDate.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        columnEndDate.setCellValueFactory(new PropertyValueFactory<>("endDate"));

    }

    public void loadAllStudies() {
        try {
            Map<String, List<Study>> studyMap = dataService.getStudyMap();
            List<Student> students = dataService.getStudents().getStudents();

            Map<String, Student> studentMap = students.stream()
                    .collect(Collectors.toMap(Student::getUPN, student -> student));

            List<Study> allStudiesWithStudents = studyMap.values().stream()
                    .flatMap(List::stream)
                    .peek(study -> {
                        if (studentMap.containsKey(study.getUPN())) {
                            study.setStudent(studentMap.get(study.getUPN()));
                        }
                    })
                    .collect(Collectors.toList());

            studyList.setAll(allStudiesWithStudents);
            studiesTable.setItems(studyList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setDataService(DataService dataService) {
        this.dataService = dataService;
    }

    public void handleDetails(ActionEvent actionEvent) {
        Study selectedStudy = studiesTable.getSelectionModel().getSelectedItem();

        if (selectedStudy != null) {
            labelStudyProgramme.setText(selectedStudy.getStudyProgramme());
            labelGraduate.setText(selectedStudy.getDegree() + " " + selectedStudy.getStudyStatus());
            labelStudyStartDate.setText(selectedStudy.getStudyAdmission().getDate());
            labelCountYears.setText(String.valueOf(selectedStudy.getStandardLength()));
            if (selectedStudy.getAcademicYears() != null && !selectedStudy.getAcademicYears().isEmpty()) {
                String firstAcademicYearStartDate = selectedStudy.getAcademicYears()
                        .get(0)
                        .getRegistrationDate();
                labelStartStudy.setText(firstAcademicYearStartDate != null ? firstAcademicYearStartDate : "Unknown");
            } else {
                labelStartStudy.setText("Unknown");
            }
            String finishDate = selectedStudy.getNewestFinishDate();
            labelFinishStudy.setText(finishDate);


            Student student = selectedStudy.getStudent();
            if (student != null) {
                /*
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
                ButtonSocialnaPoistovna.setVisible(true);
                ButtonVypisZnamok.setVisible(true);
                ButtonDiplom.setVisible(true);
                FixLabelFrom.setText("Od");
                FixLabelCountYears.setText("Doba štúdia študenta: ");
                FixLabelTo.setText("Do");

                 */
                labelFirstName.setText(student.getFirstName());
                labelLastName.setText(student.getLastName());
                labelBirthDate.setText(student.getBirthDate());

                eventsList.clear();

                // prerusenie
                if (selectedStudy.getInterruptions() != null && !selectedStudy.getInterruptions().isEmpty()) {
                    for (Interruption interruption : selectedStudy.getInterruptions()) {
                        if (interruption.getReason() != null && !interruption.getReason().contains("PRERUŠENIE")) {
                            String combinedReason = "PRERUŠENIE: " + interruption.getReason();
                            interruption.setReason(combinedReason);
                        }
                        eventsList.add(new Event(interruption));
                    }
                }

                // studium zahranicie
                if (selectedStudy.getAbroadProgrammes() != null && !selectedStudy.getAbroadProgrammes().isEmpty()) {
                    for (AbroadProgramme abroadProgramm : selectedStudy.getAbroadProgrammes()) {
                        if (abroadProgramm.getUniversity() != null && !abroadProgramm.getUniversity().contains("ERAZMUS")) {
                            String combinedReason = "ERAZMUS: " + abroadProgramm.getUniversity();
                            abroadProgramm.setUniversity(combinedReason);
                        }
                        eventsList.add(new Event(abroadProgramm));
                    }
                }
                eventsTable.setItems(eventsList);

                if (eventsList.isEmpty()) {
                    eventsTable.setVisible(false);
                } else {
                    eventsTable.setVisible(true);
                }

            } else {
                clearLabels();
            }
        } else {
            clearLabels();
        }
    }


    public void handleSearch(ActionEvent actionEvent) {
        String firstName = normalizeInput(firstNameField.getText());
        String secondName = normalizeInput(secondNameField.getText());
        String marriedName = normalizeInput(secondOriginNameField.getText());
        String birthPlace = normalizeInput(birthPlaceField.getText());
        String birthDate = birthDateField.getValue() != null
                ? birthDateField.getValue().format(INPUT_DATE_FORMATTER)
                : "";

        List<Study> filteredStudies = studyList.stream()
                .filter(study -> {
                    Student student = study.getStudent();
                    return (firstName.isEmpty() || (student != null && normalizeInput(student.getFirstName()).contains(firstName))) &&
                            (secondName.isEmpty() || (student != null && normalizeInput(student.getLastName()).contains(secondName))) &&
                            (marriedName.isEmpty() || (student != null && normalizeInput(student.getMarriedName()).contains(marriedName))) &&
                            (birthPlace.isEmpty() || (student != null && normalizeInput(student.getBirthPlace()).contains(birthPlace))) &&
                            (birthDate.isEmpty() || (student != null && student.getBirthDate().equals(birthDate)));
                })
                .collect(Collectors.toList());

        studiesTable.setItems(FXCollections.observableArrayList(filteredStudies));
    }

    public void handleReset(ActionEvent actionEvent) {
        studiesTable.getSelectionModel().clearSelection();
        firstNameField.clear();
        secondNameField.clear();
        secondOriginNameField.clear();
        birthDateField.setValue(null);
        birthPlaceField.clear();
        labelFirstName.setText("");
        labelLastName.setText("");
        labelBirthDate.setText("");
        labelStudyStartDate.setText("");
        labelStudyProgramme.setText("");
        labelGraduate.setText("");
        labelCountYears.setText("");
        labelCountYears.setText("");
        labelStartStudy.setText("");
        labelFinishStudy.setText("");
        eventsList.clear();
        loadAllStudies();
    }

    private void clearLabels() {
        Label[] labels = {
                labelFirstName, labelLastName, labelBirthDate, FixLabelYears, labelStudyStartDate,
                labelGraduate
        };

        for (Label label : labels) {
            label.setText("");
        }
    }

    private String normalizeInput(String input) {
        return Normalizer.normalize(input != null ? input.trim().toLowerCase() : "", Normalizer.Form.NFD)
                .replaceAll("[^\\p{ASCII}]", "");
    }

    public void handleMarkExport(ActionEvent actionEvent) throws IOException {
        if (studiesTable.getSelectionModel().getSelectedItem() != null) {
            DocumentExporter documentExporter = new DocumentExporter(studiesTable.getSelectionModel().getSelectedItem(), dataService.getSubjectMap());
            documentExporter.markExport();
        }
    }

    public void handleSocialInsuranceExport(ActionEvent actionEvent) throws IOException {
        if (studiesTable.getSelectionModel().getSelectedItem() != null) {
            DocumentExporter documentExporter = new DocumentExporter(studiesTable.getSelectionModel().getSelectedItem(), dataService.getSubjectMap());
            documentExporter.socialInsurance();
        }
    }
}


