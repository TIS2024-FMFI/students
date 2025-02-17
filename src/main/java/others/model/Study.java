package others.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.*;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Data
@XmlRootElement(name = "Study")
@XmlAccessorType(XmlAccessType.FIELD)
public class Study {

    @XmlElement(name = "id.UPN")
    private String UPN;

    @XmlElement(name = "Study.Programme")
    private String studyProgramme;

    @XmlElement(name = "Study.Field")
    private String studyField;

    @XmlElement(name = "Faculty")
    private String faculty;

    @XmlElement(name = "Level")
    private String level;

    @XmlElement(name = "Degree")
    private String degree;

    @XmlElement(name = "Standard.Length")
    private int standardLength;

    @XmlElement(name = "Study.Model")
    private String studyModel;

    @XmlElement(name = "Language")
    private String language;

    @XmlElement(name = "Form")
    private String form;

    @XmlElement(name = "Study.Admission")
    private Admission studyAdmission;

    @XmlElement(name = "Total.Credits")
    private int totalCredits;

    @XmlElementWrapper(name = "Academic.Years")
    @XmlElement(name = "AcademicYear")
    private List<AcademicYear> academicYears;

    @XmlElementWrapper(name = "Interruptions")
    @XmlElement(name = "Interruption")
    private List<Interruption> interruptions;

    @XmlElementWrapper(name = "Abroad.Programmes")
    @XmlElement(name = "Abroad.Programme")
    private List<AbroadProgramme> abroadProgrammes;

    @XmlElement(name = "Study.End")
    private StudyEnd studyEnd;

    @XmlElement(name = "Study.Status")
    private String studyStatus;

    @Data
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Admission {
        @XmlElement(name = "Type")
        private String type;

        @XmlElement(name = "Date")
        private String date;
    }

    @Data
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class StudyEnd {
        @XmlElementWrapper(name = "State.Exams")
        @XmlElement(name = "State.Exam")
        private List<StateExam> stateExams;

        @XmlElement(name = "Thesis")
        private Thesis thesis;

        @XmlElement(name = "Rector.FullName")
        private String rectorFullName;

        @Data
        @XmlAccessorType(XmlAccessType.FIELD)
        public static class StateExam {
            @XmlElement(name = "UIDP")
            private String uidp;

            @XmlElement(name = "Date")
            private String date;

            @XmlElement(name = "Grade")
            private String grade;

            @XmlElement(name = "Credits")
            private int credits;
        }

        @Data
        @XmlAccessorType(XmlAccessType.FIELD)
        public static class Thesis {
            @XmlElement(name = "NameSK")
            private String nameSk;

            @XmlElement(name = "NameENG")
            private String nameEng;

            @XmlElement(name = "Grade")
            private String grade;

            @XmlElement(name = "Defence.Date")
            private String defenceDate;
        }
    }

    private Student student;

    public String getNewestFinishDate() {
        if (this.getStudyEnd() == null) {
            return "Unknown";
        }

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate newestDate = null;

        // Collect all dates
        List<String> allDates = new ArrayList<>();

        // Add state exam dates
        if (this.getStudyEnd().getStateExams() != null) {
            for (Study.StudyEnd.StateExam exam : this.getStudyEnd().getStateExams()) {
                if (exam.getDate() != null && !exam.getDate().isEmpty()) {
                    allDates.add(exam.getDate());
                }
            }
        }

        // Add thesis defense date
        if (this.getStudyEnd().getThesis() != null) {
            String thesisDate = this.getStudyEnd().getThesis().getDefenceDate();
            if (thesisDate != null && !thesisDate.isEmpty()) {
                allDates.add(thesisDate);
            }
        }

        for (String dateString : allDates) {
            try {
                LocalDate parsedDate = LocalDate.parse(dateString, dateFormatter);
                if (newestDate == null || parsedDate.isAfter(newestDate)) {
                    newestDate = parsedDate;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // Return the newest date as a string or "Unknown" if none found
        return newestDate != null ? newestDate.format(dateFormatter) : "Unknown";
    }
}
