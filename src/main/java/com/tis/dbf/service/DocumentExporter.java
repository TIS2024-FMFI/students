package com.tis.dbf.service;

import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Cell;
import com.tis.dbf.model.AcademicYear;
import com.tis.dbf.model.Interruption;
import com.tis.dbf.model.Student;
import com.tis.dbf.model.Study;

import java.util.List;

public class DocumentExporter {
    private static Student student = null;
    private static Study study = null;

    public DocumentExporter(Student student, Study study) {
        DocumentExporter.student = student;
        DocumentExporter.study = study;
    }

    public static void socialInsurance() {
        String pdfPath = "student_record.pdf";

        if (student == null || study == null) {
            System.out.println("Student or study data is missing.");
            return;
        }

        try {
            PdfWriter writer = new PdfWriter(pdfPath);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            // Create fonts for this document
            // TODO find correct encoding
            PdfFont boldFont = PdfFontFactory.createFont("Times-Bold", "ISO-8859-2");
            PdfFont italicFont = PdfFontFactory.createFont("Times-Italic", "ISO-8859-2");
            PdfFont regularFont = PdfFontFactory.createFont("Times-Roman", "ISO-8859-2");

            // Header
            header(document, boldFont, regularFont);


            document.add(new Paragraph("UKONČENÉ ŠTÚDIUM\n\n").setTextAlignment(TextAlignment.CENTER).setFont(boldFont).setFontSize(12));
            // Student info
            studentTable(document, boldFont, regularFont);

            // Table for study program details
            float[] columnWidthsStudies = {100f, 300f};
            Table studyTable = new Table(columnWidthsStudies);

            studyTable.addCell(new Cell().add(new Paragraph("Študijný program:").setMultipliedLeading(0.8f)
                    .setFont(italicFont)).setBorder(Border.NO_BORDER));
            studyTable.addCell(new Cell().add(new Paragraph(study.getStudyProgramme()).setMultipliedLeading(0.8f)
                    .setFont(boldFont)).setBorder(Border.NO_BORDER));

            studyTable.addCell(new Cell().add(new Paragraph("Forma štúdia:").setMultipliedLeading(0.8f)
                    .setFont(italicFont)).setBorder(Border.NO_BORDER));
            studyTable.addCell(new Cell().add(new Paragraph(study.getForm()).setMultipliedLeading(0.8f)
                    .setFont(regularFont)).setBorder(Border.NO_BORDER));

            studyTable.addCell(new Cell().add(new Paragraph("Začiatok štúdia:").setMultipliedLeading(0.8f)
                    .setFont(italicFont)).setBorder(Border.NO_BORDER));
            studyTable.addCell(new Cell().add(new Paragraph(study.getAcademicYears().get(0).getRegistrationDate()).setMultipliedLeading(0.8f)
                    .setFont(regularFont)).setBorder(Border.NO_BORDER));

            studyTable.addCell(new Cell().add(new Paragraph("Koniec štúdia:").setMultipliedLeading(0.8f)
                    .setFont(italicFont)).setBorder(Border.NO_BORDER));
            List<Study.StudyEnd.StateExam> stateExams = study.getStudyEnd().getStateExams();
            if (!stateExams.isEmpty()) {
                studyTable.addCell(new Cell().add(new Paragraph(stateExams.get(stateExams.size() - 1).getDate()).setMultipliedLeading(0.8f)
                        .setFont(regularFont)).setBorder(Border.NO_BORDER));
            } else {
                // TODO - what to do in this case?
                studyTable.addCell(new Cell().add(new Paragraph("štúdium neukončné štátnou skúškou").setMultipliedLeading(0.8f)
                        .setFont(regularFont)).setBorder(Border.NO_BORDER));
            }
            studyTable.addCell(new Cell().add(new Paragraph("Stupeň štúdia:").setMultipliedLeading(0.8f)
                    .setFont(italicFont)).setBorder(Border.NO_BORDER));
            studyTable.addCell(new Cell().add(new Paragraph(study.getDegree()).setMultipliedLeading(0.8f)
                    .setFont(regularFont)).setBorder(Border.NO_BORDER));

            studyTable.addCell(new Cell().add(new Paragraph("Doba štúdia").setMultipliedLeading(0.8f)
                    .setFont(italicFont)).setBorder(Border.NO_BORDER));
            studyTable.addCell(new Cell().add(new Paragraph(String.valueOf(getStudyLength()) + " need to calculate").setMultipliedLeading(0.8f) // TODO need to calculate
                    .setFont(regularFont)).setBorder(Border.NO_BORDER));

            studyTable.addCell(new Cell().add(new Paragraph("Spôsob ukončenia:").setMultipliedLeading(0.8f)
                    .setFont(italicFont)).setBorder(Border.NO_BORDER));
            studyTable.addCell(new Cell().add(new Paragraph("PLACEHOLDER").setMultipliedLeading(0.8f)
                    .setFont(regularFont)).setBorder(Border.NO_BORDER));

            studyTable.setBorder(Border.NO_BORDER);
            document.add(studyTable);

            // Interruptions
            document.add(new Paragraph("\nPrerušenia:").setFont(boldFont)
                    .setMultipliedLeading(0.8f));

            for (int i = 0; i < study.getInterruptions().size(); i++) {
                document.add(new Paragraph(study.getInterruptions().get(i).getStartDate() + " - " +
                        study.getInterruptions().get(i).getEndDate() + ", " +
                        study.getInterruptions().get(i).getReason()).setFont(regularFont)
                        .setMultipliedLeading(0.8f));
            }

            document.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void markExport() {
        String pdfPath = "mark_record.pdf";

        if (student == null || study == null) {
            System.out.println("Student or study data is missing.");
            return;
        }

        try {
            PdfWriter writer = new PdfWriter(pdfPath);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            // Create fonts for this document
            PdfFont boldFont = PdfFontFactory.createFont("Times-Bold", "ISO-8859-2");
            PdfFont italicFont = PdfFontFactory.createFont("Times-Italic", "ISO-8859-2");
            PdfFont regularFont = PdfFontFactory.createFont("Times-Roman", "ISO-8859-2");

            // Header
            header(document, boldFont, regularFont);

            document.add(new Paragraph("VÝPIS ZNÁMOK\n\n").setTextAlignment(TextAlignment.CENTER).setFont(boldFont).setFontSize(12));

            // Student info
            studentTable(document, boldFont, regularFont);

            document.add(new Paragraph(new Text("Odbor: ").setFont(boldFont))
                    .add(new Text(study.getStudyField()).setFont(regularFont))
                    .setMultipliedLeading(0.8f));

            document.add(new Paragraph(new Text("Zameranie: ").setFont(boldFont))
                    .add(new Text(study.getStudyProgramme()).setFont(regularFont))
                    .setMultipliedLeading(0.8f));

            document.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void header(Document document, PdfFont boldFont, PdfFont regularFont) {
        document.add(new Paragraph("Univerzita Komenského v Bratislave")
                .setTextAlignment(TextAlignment.CENTER)
                .setFont(boldFont)
                .setFontSize(14));
        document.add(new Paragraph(study.getFaculty())
                .setTextAlignment(TextAlignment.CENTER)
                .setFont(regularFont)
                .setFontSize(12));
    }

    private static void studentTable(Document document, PdfFont boldFont, PdfFont regularFont) {
        // Student info
        float[] columnWidthsStudent = {300f, 200f};
        Table studentTable = new Table(columnWidthsStudent);
        // meno
        studentTable.addCell(new Cell().add(new Paragraph(new Text("Študent: ").setFont(boldFont))
                .add(new Text(student.getFullName()).setFont(regularFont))
                .setMultipliedLeading(0.8f)).setBorder(Border.NO_BORDER));

        if (!student.getLastName().equals(student.getBirthName())) {      // ak sa rodne priezvisko nerovna rodnemu priezvisku
            if (student.getSex().equals("muž")) {                           // format pre muza
                // rodeny
                studentTable.addCell(new Cell().add(new Paragraph(new Text("Rodený: ").setFont(boldFont))
                        .add(new Text(student.getBirthName()).setFont(regularFont))
                        .setMultipliedLeading(0.8f)).setBorder(Border.NO_BORDER));
            } else {                                                        // format pre zenu
                // rodena
                studentTable.addCell(new Cell().add(new Paragraph(new Text("Rodená: ").setFont(boldFont))
                        .add(new Text(student.getBirthName()).setFont(regularFont))
                        .setMultipliedLeading(0.8f)).setBorder(Border.NO_BORDER));
            }
        }
        studentTable.setBorder(Border.NO_BORDER);
        document.add(studentTable);

        // datum a miesto narodenia
        document.add(new Paragraph(new Text("Dátum a miesto narodenia: ").setFont(boldFont))
                .add(new Text(student.getBirthDate() + " " + student.getBirthPlace() + "\n\n").setFont(regularFont))
                .setMultipliedLeading(0.8f));

    }

    private static int getStudyLength() {
        return 0;
    }

    public static void main(String[] args) {
        Student student = new Student();
        Study study = new Study();
        student.setSex("muž");
        student.setFirstName("Janko");
        student.setLastName("Hraško");
        student.setBirthCountry("Slovesnko");
        student.setBirthDate("01.01.1990");
        student.setBirthPlace("Bratislava");
        student.setBirthName("Mrkvicka");
        study.setDegree("Bakalársky");
        study.setStudyProgramme("Informatika");
        study.setDegree("Bakalársky");
        study.setStudyProgramme("Informatika");

        Interruption i1 = new Interruption();
        Interruption i2 = new Interruption();
        i1.setReason("materská dovolenka");
        i1.setStartDate("01.09.2015");
        i1.setEndDate("31.08.2016");
        i2.setReason("práce neschopnosť");
        i2.setStartDate("01.08.2019");
        i2.setEndDate("31.08.2021");
        study.setInterruptions(List.of(i1, i2));

        study.setFaculty("Fakulta Socialnych a Ekonomickych Vied");
        study.setForm("Denná");
        study.setStudyField("Informatika");

        AcademicYear a1 = new AcademicYear();
        AcademicYear a2 = new AcademicYear();
        a1.setRegistrationDate("01.09.2015");
        a1.setYears("2015/2016");
        a2.setRegistrationDate("01.09.2016");
        a2.setYears("2016/2017");

        Study.StudyEnd.StateExam SE1 = new Study.StudyEnd.StateExam();
        SE1.setDate("01.06.2019");
        Study.StudyEnd studyEnd = new Study.StudyEnd();
        studyEnd.setStateExams(List.of(SE1));
        study.setStudyEnd(studyEnd);

        study.setAcademicYears(List.of(a1, a2));
        DocumentExporter exporter = new DocumentExporter(student, study);
        exporter.socialInsurance();
        exporter.markExport();
    }
}