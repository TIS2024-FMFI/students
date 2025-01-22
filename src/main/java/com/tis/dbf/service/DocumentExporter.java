package com.tis.dbf.service;

import com.itextpdf.io.font.PdfEncodings;
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

import java.io.File;

public class DocumentExporter {
    private final Study study;
    private final Student student;

    public DocumentExporter(Study study) {
        this.study = study;
        this.student = this.study.getStudent();
    }

    public void socialInsurance() {
        int randomNumber = 1000 + (int) (Math.random() * 4001);
        String pdfPath = getDownloadsPath(student.getNameForFile() + "-" + randomNumber +"soc_poist.pdf");
        System.out.println("som v soc poist");

        if (study == null) {
            System.out.println("Student or study data is missing.");
            return;
        }

        try (PdfWriter writer = new PdfWriter(pdfPath);
             PdfDocument pdf = new PdfDocument(writer);
             Document document = new Document(pdf, PageSize.A4)) {

            PdfFont boldFont = PdfFontFactory.createFont("C:/Windows/Fonts/timesbd.ttf", PdfEncodings.IDENTITY_H);
            PdfFont italicFont = PdfFontFactory.createFont("C:/Windows/Fonts/timesi.ttf", PdfEncodings.IDENTITY_H);
            PdfFont regularFont = PdfFontFactory.createFont("C:/Windows/Fonts/times.ttf", PdfEncodings.IDENTITY_H);


            header(document, boldFont, regularFont);

            document.add(new Paragraph("PRIEBEH ŠTÚDIA\n\n")
                    .setTextAlignment(TextAlignment.CENTER)
                    .setFont(boldFont)
                    .setFontSize(12));

            studentTable(document, boldFont, regularFont);

            document.add(createStudyTable(boldFont, italicFont, regularFont));

            document.add(new Paragraph("\nAkademické roky:").setFont(boldFont).setMultipliedLeading(0.8f));
            for (AcademicYear academicYear : study.getAcademicYears()) {
                document.add(new Paragraph(academicYear.getYears() + ", zápis " +
                        academicYear.getRegistrationDate()).setFont(regularFont).setMultipliedLeading(0.8f));
            }

            document.add(new Paragraph("\nPrerušenia:").setFont(boldFont).setMultipliedLeading(0.8f));
            for (Interruption interruption : study.getInterruptions()) {
                document.add(new Paragraph(interruption.getStartDate() + " - " +
                        interruption.getEndDate() + ", " +
                        interruption.getReason()).setFont(regularFont).setMultipliedLeading(0.8f));
            }

            document.add(new Paragraph("\nŠtúdium v zahraničí:").setFont(boldFont).setMultipliedLeading(0.8f));
            for (AbroadProgramme abroadProgramme : study.getAbroadProgrammes()) {
                document.add(new Paragraph(abroadProgramme.getStartDate() + " - " +
                        abroadProgramme.getEndDate() + ", " + abroadProgramme.getUniversity() + ", " + abroadProgramme.getCountry() + ", " +
                        abroadProgramme.getSemester()).setFont(regularFont).setMultipliedLeading(0.8f));
            }

            document.add(new Paragraph("\nŠtátne skúšky:").setFont(boldFont).setMultipliedLeading(0.8f));
            for (Study.StudyEnd.StateExam stateExam : study.getStudyEnd().getStateExams()) {
                document.add(new Paragraph("Hodnotenie: " + stateExam.getGrade()).setFont(regularFont).setMultipliedLeading(0.8f));
                document.add(new Paragraph("Dátum obhajoby: " + stateExam.getDate()).setFont(regularFont).setMultipliedLeading(0.8f));
            }

            document.add(new Paragraph("\nZaverečna práca:").setFont(boldFont).setMultipliedLeading(0.8f));
            Study.StudyEnd.Thesis thesis = study.getStudyEnd().getThesis();
            if (thesis != null) {
                document.add(new Paragraph("Hodnotenie: " + thesis.getGrade()).setFont(regularFont).setMultipliedLeading(0.8f));
                document.add(new Paragraph("Dátum obhajoby: " + thesis.getDefenceDate()).setFont(regularFont).setMultipliedLeading(0.8f));
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void markExport() {
        int randomNumber = 1000 + (int) (Math.random() * 4001);
        String pdfPath = getDownloadsPath(student.getNameForFile() + "-" + randomNumber +"vypis_znamok.pdf");

        if (study == null) {
            System.out.println("Student or study data is missing.");
            return;
        }

        try (PdfWriter writer = new PdfWriter(pdfPath);
             PdfDocument pdf = new PdfDocument(writer);
             Document document = new Document(pdf, PageSize.A4)) {

            PdfFont boldFont = PdfFontFactory.createFont("C:/Windows/Fonts/timesbd.ttf", PdfEncodings.IDENTITY_H);
            PdfFont italicFont = PdfFontFactory.createFont("C:/Windows/Fonts/timesi.ttf", PdfEncodings.IDENTITY_H);
            PdfFont regularFont = PdfFontFactory.createFont("C:/Windows/Fonts/times.ttf", PdfEncodings.IDENTITY_H);

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

            Table table = new Table(5);
            table.setWidth(UnitValue.createPercentValue(100));

            String[] headers = {"Zimný + Letný semester:", "Povinnosť:", "Dátum:", "Výsledok:", "Termín:"};
            for (String headerText : headers) {
                table.addCell(createTableCell(headerText, italicFont, true));
            }

            for (AcademicYear year : study.getAcademicYears()) {
                for (Subject subject : year.getSubjects().getSubjectList()) {
                    table.addCell(createTableCell(stringSubjectName(subject), italicFont, false));
                    table.addCell(createTableCell(subject.getId1(), regularFont, false)
                            .setTextAlignment(TextAlignment.CENTER));
                    table.addCell(createTableCell("placeH", regularFont, false)
                            .setTextAlignment(TextAlignment.CENTER));
                    table.addCell(createTableCell("placeH", regularFont, false)
                            .setTextAlignment(TextAlignment.CENTER));
                    table.addCell(createTableCell("placeH", regularFont, false)
                            .setTextAlignment(TextAlignment.CENTER));
                }
            }

            for (int i = 0; i < 5; i++) {
                table.addCell(new Cell().setBorder(Border.NO_BORDER).setBorderTop(new SolidBorder(1)));
            }

            document.add(table);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getDownloadsPath(String fileName) {
        String userHome = System.getProperty("user.home");
        return userHome + File.separator + "Downloads" + File.separator + fileName;
    }

    private Table createStudyTable(PdfFont boldFont, PdfFont italicFont, PdfFont regularFont) {
        float[] columnWidths = {100f, 300f};
        Table table = new Table(columnWidths);

        addTableRow(table, "Študijný program:", study.getStudyProgramme(), italicFont, boldFont);
        addTableRow(table, "Forma štúdia:", study.getForm(), italicFont, regularFont);
        addTableRow(table, "Začiatok štúdia:", study.getAcademicYears().get(0).getRegistrationDate(), italicFont, regularFont);
        addTableRow(table, "Koniec štúdia:", study.getNewestFinishDate(), italicFont, regularFont);
        addTableRow(table, "Stupeň štúdia:", study.getDegree(), italicFont, regularFont);
        addTableRow(table, "Doba štúdia:", getStudyLength(), italicFont, regularFont);
        addTableRow(table, "Status štúdia:", study.getStudyStatus(), italicFont, regularFont);

        table.setBorder(Border.NO_BORDER);
        return table;
    }

    private void addTableRow(Table table, String label, String value, PdfFont labelFont, PdfFont valueFont) {
        table.addCell(new Cell().add(new Paragraph(label).setMultipliedLeading(0.8f).setFont(labelFont)).setBorder(Border.NO_BORDER));
        table.addCell(new Cell().add(new Paragraph(value).setMultipliedLeading(0.8f).setFont(valueFont)).setBorder(Border.NO_BORDER));
    }

    private String stringSubjectName(Subject subject) {
        return subject.getSlovakName() + (subject.getPartName() != null ? " " + subject.getPartName() : "");
    }

    private Cell createTableCell(String content, PdfFont font, boolean isHeader) {
        Cell cell = new Cell().add(new Paragraph(content).setMultipliedLeading(0.8f).setFont(font));
        cell.setBorder(Border.NO_BORDER);
        if (isHeader) {
            cell.setBorderBottom(new SolidBorder(1));
        }
        return cell;
    }

    private void header(Document document, PdfFont boldFont, PdfFont regularFont) {
        document.add(new Paragraph("Univerzita Komenského v Bratislave")
                .setTextAlignment(TextAlignment.CENTER)
                .setFont(boldFont)
                .setFontSize(14));
        document.add(new Paragraph(study.getFaculty())
                .setTextAlignment(TextAlignment.CENTER)
                .setFont(regularFont)
                .setFontSize(12));
    }

    private void studentTable(Document document, PdfFont boldFont, PdfFont regularFont) {
        float[] columnWidthsStudent = {300f, 200f};
        Table table = new Table(columnWidthsStudent);

        table.addCell(new Cell().add(new Paragraph(new Text("Študent: ").setFont(boldFont))
                        .add(new Text(student.getFullName()).setFont(regularFont)).setMultipliedLeading(0.8f))
                .setBorder(Border.NO_BORDER));

        table.setBorder(Border.NO_BORDER);
        document.add(table);

        document.add(new Paragraph(new Text("Dátum a miesto narodenia: ").setFont(boldFont))
                .add(new Text(student.getBirthDate() + ", " + student.getBirthPlace() + "\n\n").setFont(regularFont))
                .setMultipliedLeading(0.8f));
    }

    private String getStudyLength() {
        return "-";
    }

    public static void main(String[] args) {
        Study study = new Study();
        DocumentExporter exporter = new DocumentExporter(study);
        System.out.println("hallo");
        exporter.socialInsurance();
        System.out.println("ddd");
        exporter.markExport();
    }
}
