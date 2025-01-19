package com.tis.dbf.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.*;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;

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

//    @Data
//    @XmlAccessorType(XmlAccessType.FIELD)
//    public static class AcademicYears {
//        @XmlElement(name = "AR2001-2002")
//        private List<AcademicYear> academicYearDetails;
//
//        @Data
//        @XmlAccessorType(XmlAccessType.FIELD)
//        public static class AcademicYear {
//            @XmlElement(name = "Registration.Date")
//            private String registrationDate;
//
//            @XmlElement(name = "Subjects")
//            private List<Subject> subjects;
//
//            @Data
//            @XmlAccessorType(XmlAccessType.FIELD)
//            public static class Subject {
//                @XmlElement(name = "UIDP")
//                private String uidp;
//
//                @XmlElement(name = "Grade")
//                private String grade;
//
//                @XmlElement(name = "Type")
//                private String type;
//
//                @XmlElement(name = "Attempt")
//                private String attempt;
//
//                @XmlElement(name = "End.Subject")
//                private String endSubject;
//
//                @XmlElement(name = "End.Subject.Date")
//                private String endSubjectDate;
//
//                @XmlElement(name = "Credits")
//                private int credits;
//
//                @XmlElement(name = "Semester")
//                private String semester;
//            }
//        }
//    }

//    @Data
//    @XmlAccessorType(XmlAccessType.FIELD)
//    public static class Interruption {
//        @XmlElement(name = "Reason")
//        private String reason;
//
//        @XmlElement(name = "Start.Date")
//        private String startDate;
//
//        @XmlElement(name = "End.Date")
//        private String endDate;
//    }

//    @Data
//    @XmlAccessorType(XmlAccessType.FIELD)
//    public static class AbroadProgramme {
//        @XmlElement(name = "Start.Date")
//        private String startDate;
//
//        @XmlElement(name = "End.Date")
//        private String endDate;
//
//        @XmlElement(name = "Semester")
//        private String semester;
//
//        @XmlElement(name = "University")
//        private String university;
//
//        @XmlElement(name = "Country")
//        private String country;
//    }

    @Data
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class StudyEnd {
        @XmlElement(name = "State.Exams")
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

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }
}
