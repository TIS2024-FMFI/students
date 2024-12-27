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
import com.tis.dbf.model.Student;
import com.tis.dbf.model.Study;

import java.io.IOException;

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
            PdfFont boldFont = PdfFontFactory.createFont("Times-Bold");
            PdfFont italicFont = PdfFontFactory.createFont("Times-Italic");
            PdfFont regularFont = PdfFontFactory.createFont("Times-Roman");

            // Header
            header(document, boldFont, regularFont);

            // Student info
            studentTable(document, boldFont, regularFont);

            document.add(new Paragraph("\nUKONČENÉ ŠTÚDIÁ").setFont(boldFont).setFontSize(12));

            // Table for study program details
            float[] columnWidthsStudies = {100f, 300f};
            Table studyTable = new Table(columnWidthsStudies);

            studyTable.addCell(new Cell().add(new Paragraph("Študijný program:").setMultipliedLeading(0.8f)
                    .setFont(italicFont)).setBorder(Border.NO_BORDER));
            studyTable.addCell(new Cell().add(new Paragraph(study.getStudyProgramme()).setMultipliedLeading(0.8f)
                    .setFont(boldFont)).setBorder(Border.NO_BORDER));
            studyTable.addCell(new Cell().add(new Paragraph("Forma štúdia:").setMultipliedLeading(0.8f)
                    .setFont(italicFont)).setBorder(Border.NO_BORDER));
            studyTable.addCell(new Cell().add(new Paragraph("PLACEHOLDER").setMultipliedLeading(0.8f)
                    .setFont(regularFont)).setBorder(Border.NO_BORDER));
            studyTable.addCell(new Cell().add(new Paragraph("Začiatok štúdia:").setMultipliedLeading(0.8f)
                    .setFont(italicFont)).setBorder(Border.NO_BORDER));
            studyTable.addCell(new Cell().add(new Paragraph("PLACEHOLDER").setMultipliedLeading(0.8f)
                    .setFont(regularFont)).setBorder(Border.NO_BORDER));
            studyTable.addCell(new Cell().add(new Paragraph("Koniec štúdia:").setMultipliedLeading(0.8f)
                    .setFont(italicFont)).setBorder(Border.NO_BORDER));
            studyTable.addCell(new Cell().add(new Paragraph("PLACEHOLDER").setMultipliedLeading(0.8f)
                    .setFont(regularFont)).setBorder(Border.NO_BORDER));
            studyTable.addCell(new Cell().add(new Paragraph("Stupeň štúdia:").setMultipliedLeading(0.8f)
                    .setFont(italicFont)).setBorder(Border.NO_BORDER));
            studyTable.addCell(new Cell().add(new Paragraph(study.getDegree()).setMultipliedLeading(0.8f)
                    .setFont(regularFont)).setBorder(Border.NO_BORDER));
            studyTable.addCell(new Cell().add(new Paragraph("Doba štúdia").setMultipliedLeading(0.8f)
                    .setFont(italicFont)).setBorder(Border.NO_BORDER));
            studyTable.addCell(new Cell().add(new Paragraph("PLACEHOLDER").setMultipliedLeading(0.8f)
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
            document.add(new Paragraph("01.09.2015 - 31.08.2016, materská dovolenka").setFont(regularFont)
                    .setMultipliedLeading(0.8f));
            document.add(new Paragraph("01.08.2019 - 31.08.2021, materská dovolenka").setFont(regularFont)
                    .setMultipliedLeading(0.8f));

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
            PdfFont boldFont = PdfFontFactory.createFont("Times-Bold");
            PdfFont italicFont = PdfFontFactory.createFont("Times-Italic");
            PdfFont regularFont = PdfFontFactory.createFont("Times-Roman");

            // Header
            header(document, boldFont, regularFont);

            // Student info
            studentTable(document, boldFont, regularFont);

            document.add(new Paragraph(new Text("Odbor: ").setFont(boldFont))
                    .add(new Text("PLACEHOLDER").setFont(regularFont))
                    .setMultipliedLeading(0.8f));

            document.add(new Paragraph(new Text("Zameranie: ").setFont(boldFont))
                    .add(new Text(student.getStudyProgram()).setFont(regularFont))
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
        document.add(new Paragraph("Fakulta sociálnych a ekonomických vied")
                .setTextAlignment(TextAlignment.CENTER)
                .setFont(regularFont)
                .setFontSize(12));
        document.add(new Paragraph("\nŠTÚDIÁ ŠTUDENTA\n")
                .setTextAlignment(TextAlignment.CENTER)
                .setFont(boldFont)
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

        if (!student.getSecondName().equals(student.getBirthName())) {      // ak sa rodne priezvisko nerovna rodnemu priezvisku
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

        // datum a miesto narodenia
        document.add(new Paragraph(new Text("Dátum a miesto narodenia: ").setFont(boldFont))
                .add(new Text(student.getBirthDate() + " " + student.getBirthdayPlace()).setFont(regularFont))
                .setMultipliedLeading(0.8f));

        studentTable.setBorder(Border.NO_BORDER);
        document.add(studentTable);
    }

    public static void main(String[] args) {
        // need to add args to the class, so it has data
        Student student = new Student();
        Study study = new Study();
        student.setSex("muž");
        student.setFirstName("Janko");
        student.setSecondName("Hraško");
        student.setBirthCountry("Slovesnko");
        student.setBirthDate("01.01.1990");
        student.setBirthdayPlace("Bratislava");
        student.setBirthName("Mrkvicka");
        student.setDegree("Bakalársky");
        student.setStudyProgram("Informatika");
        study.setDegree("Bakalársky");
        study.setStudyProgramme("Informatika");
        DocumentExporter exporter = new DocumentExporter(student, study);
        exporter.socialInsurance();
        exporter.markExport();
    }
}