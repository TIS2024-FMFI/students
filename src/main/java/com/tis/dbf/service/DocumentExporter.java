package com.tis.dbf.service;

import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.properties.UnitValue;
import com.tis.dbf.model.*;

import java.util.List;

public class DocumentExporter {
    private static Student student;
    private static Study study;

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

        try (PdfWriter writer = new PdfWriter(pdfPath);
             PdfDocument pdf = new PdfDocument(writer);
             Document document = new Document(pdf, PageSize.A4)) {

            // Create fonts for this document
            // TODO find correct encoding
            PdfFont boldFont = PdfFontFactory.createFont("Times-Bold", "ISO-8859-1");
            PdfFont italicFont = PdfFontFactory.createFont("Times-Italic", "ISO-8859-1");
            PdfFont regularFont = PdfFontFactory.createFont("Times-Roman", "ISO-8859-1");

            // Header
            header(document, boldFont, regularFont);

            document.add(new Paragraph("UKONČENÉ ŠTÚDIUM\n\n")
                    .setTextAlignment(TextAlignment.CENTER)
                    .setFont(boldFont)
                    .setFontSize(12));

            // Student info
            studentTable(document, boldFont, regularFont);

            // Study program details
            document.add(createStudyTable(boldFont, italicFont, regularFont));

            // Interruptions
            document.add(new Paragraph("\nPrerušenia:").setFont(boldFont).setMultipliedLeading(0.8f));
            for (Interruption interruption : study.getInterruptions()) {
                document.add(new Paragraph(interruption.getStartDate() + " - " +
                        interruption.getEndDate() + ", " +
                        interruption.getReason()).setFont(regularFont).setMultipliedLeading(0.8f));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Table createStudyTable(PdfFont boldFont, PdfFont italicFont, PdfFont regularFont) {
        float[] columnWidths = {100f, 300f};
        Table table = new Table(columnWidths);

        addTableRow(table, "Študijný program:", study.getStudyProgramme(), italicFont, boldFont);
        addTableRow(table, "Forma štúdia:", study.getForm(), italicFont, regularFont);
        addTableRow(table, "Začiatok štúdia:", study.getAcademicYears().get(0).getRegistrationDate(), italicFont, regularFont);
        addTableRow(table, "Koniec štúdia:", getStudyEndDate(), italicFont, regularFont);
        addTableRow(table, "Stupeň štúdia:", study.getDegree(), italicFont, regularFont);
        addTableRow(table, "Doba štúdia:", getStudyLength() + " need to calculate", italicFont, regularFont); // TODO need to calculate
        addTableRow(table, "Spôsob ukončenia:", "PLACEHOLDER", italicFont, regularFont);

        table.setBorder(Border.NO_BORDER);
        return table;
    }

    private static void addTableRow(Table table, String label, String value, PdfFont labelFont, PdfFont valueFont) {
        table.addCell(new Cell().add(new Paragraph(label).setMultipliedLeading(0.8f).setFont(labelFont)).setBorder(Border.NO_BORDER));
        table.addCell(new Cell().add(new Paragraph(value).setMultipliedLeading(0.8f).setFont(valueFont)).setBorder(Border.NO_BORDER));
    }

    private static String getStudyEndDate() {
        List<Study.StudyEnd.StateExam> stateExams = study.getStudyEnd().getStateExams();
        return stateExams.isEmpty() ? "štúdium neukončné štátnou skúškou" : stateExams.get(stateExams.size() - 1).getDate();
    }

    public static void markExport() {
        String pdfPath = "mark_record.pdf";

        if (student == null || study == null) {
            System.out.println("Student or study data is missing.");
            return;
        }

        try (PdfWriter writer = new PdfWriter(pdfPath);
             PdfDocument pdf = new PdfDocument(writer);
             Document document = new Document(pdf, PageSize.A4)) {

            PdfFont boldFont = PdfFontFactory.createFont("Times-Bold", "ISO-8859-2");
            PdfFont italicFont = PdfFontFactory.createFont("Times-Italic", "ISO-8859-2");
            PdfFont regularFont = PdfFontFactory.createFont("Times-Roman", "ISO-8859-2");

            header(document, boldFont, regularFont);

            document.add(new Paragraph("VÝPIS ZNÁMOK\n\n")
                    .setTextAlignment(TextAlignment.CENTER)
                    .setFont(boldFont)
                    .setFontSize(12));

            studentTable(document, boldFont, regularFont);

            document.add(new Paragraph(new Text("Odbor: ").setFont(boldFont))
                    .add(new Text(study.getStudyField()).setFont(regularFont)).setMultipliedLeading(0.8f));

            document.add(new Paragraph(new Text("Zameranie: ").setFont(boldFont))
                    .add(new Text(study.getStudyProgramme()).setFont(regularFont)).setMultipliedLeading(0.8f));

            // Table for study subjects
            Table table = new Table(5);
            table.setWidth(UnitValue.createPercentValue(100));

            // Adding headers to the table
            String[] headers = {"Zimný + Letný semester:", "Povinnosť:", "Dátum:", "Výsledok:", "Termín:"};
            for (String headerText : headers) {
                table.addCell(createTableCell(headerText, italicFont, true));
            }

            // Populate table with academic years and subjects
            for (AcademicYear year : study.getAcademicYears()) {
                for (Subject subject : year.getSubjects().getSubjectList()) {
                    table.addCell(createTableCell(stringSubjectName(subject), italicFont, false));

                    table.addCell(createTableCell(subject.getId1(), regularFont, false)
                            .setTextAlignment(TextAlignment.CENTER));

                    table.addCell(createTableCell("placeH", regularFont, false) // TODO: replace with actual date
                            .setTextAlignment(TextAlignment.CENTER));

                    table.addCell(createTableCell("placeH", regularFont, false) // TODO: replace with actual result
                            .setTextAlignment(TextAlignment.CENTER));

                    table.addCell(createTableCell("placeH", regularFont, false) // TODO: replace with actual term
                            .setTextAlignment(TextAlignment.CENTER));
                }
            }

            // Add bottom border to the table
            for (int i = 0; i < 5; i++) {
                table.addCell(new Cell().setBorder(Border.NO_BORDER).setBorderTop(new SolidBorder(1)));
            }

            document.add(table);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String stringSubjectName(Subject subject) {
        return subject.getSlovakName() + (subject.getPartName() != null ? " " + subject.getPartName() : "");
    }

    private static Cell createTableCell(String content, PdfFont font, boolean isHeader) {
        Cell cell = new Cell().add(new Paragraph(content).setMultipliedLeading(0.8f).setFont(font));
        cell.setBorder(Border.NO_BORDER);
        if (isHeader) {
            cell.setBorderBottom(new SolidBorder(1));
        }
        return cell;
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
        float[] columnWidthsStudent = {300f, 200f};
        Table table = new Table(columnWidthsStudent);

        table.addCell(new Cell().add(new Paragraph(new Text("Študent: ").setFont(boldFont))
                        .add(new Text(student.getFullName()).setFont(regularFont)).setMultipliedLeading(0.8f))
                .setBorder(Border.NO_BORDER));

        if (!student.getLastName().equals(student.getBirthName())) {
            String label = student.getSex().equals("muž") ? "Rodený: " : "Rodená: ";
            table.addCell(new Cell().add(new Paragraph(new Text(label).setFont(boldFont))
                            .add(new Text(student.getBirthName()).setFont(regularFont)).setMultipliedLeading(0.8f))
                    .setBorder(Border.NO_BORDER));
        }

        table.setBorder(Border.NO_BORDER);
        document.add(table);

        document.add(new Paragraph(new Text("Dátum a miesto narodenia: ").setFont(boldFont))
                .add(new Text(student.getBirthDate() + ", " + student.getBirthPlace() + "\n\n").setFont(regularFont))
                .setMultipliedLeading(0.8f));
    }

    private static int getStudyLength() {
        return 0; // TODO Placeholder value
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


        Subject subject1 = new Subject();
        subject1.setSlovakName("Calculus");
        subject1.setPartName("1");
        subject1.setId1("A");


        Subject subject2 = new Subject();
        subject2.setSlovakName("Introduction to Programming");
        subject2.setId1("B");

        Subject subject3 = new Subject();
        subject3.setSlovakName("Physics");
        subject3.setPartName("1");
        subject3.setId1("A");

        Subject subject4 = new Subject();
        subject4.setSlovakName("English Composition");
        subject4.setId1("C");

        Subjects subjects1 = new Subjects();
        subjects1.setSubjectList(List.of(subject1, subject2));


        Subjects subjects2 = new Subjects();
        subjects2.setSubjectList(List.of(subject3, subject4));

        a1.setYears("2023/2024");
        a1.setSubjects(subjects1);

        a2.setYears("2024/2025");
        a2.setSubjects(subjects2);

        study.setAcademicYears(List.of(a1, a2));
        DocumentExporter exporter = new DocumentExporter(student, study);
        exporter.socialInsurance();
        exporter.markExport();
    }
}