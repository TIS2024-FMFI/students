package others.service;

import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import com.itextpdf.layout.properties.VerticalAlignment;
import others.model.*;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

public class DocumentExporter {
    private final Study study;
    private final Student student;
    PdfFont boldFont;
    PdfFont italicFont;
    PdfFont regularFont;
    Map<String, Subject> subjectMap;


    public DocumentExporter(Study study, Map<String, Subject> subjectMap) throws IOException {
        this.study = study;
        this.student = this.study.getStudent();
        InputStream boldFontStream = getClass().getResourceAsStream("/fonts/timesbd.ttf");
        assert boldFontStream != null;
        this.boldFont = PdfFontFactory.createFont(boldFontStream.readAllBytes(), PdfEncodings.IDENTITY_H);
        InputStream italicFontStream = getClass().getResourceAsStream("/fonts/timesi.ttf");
        assert italicFontStream != null;
        this.italicFont = PdfFontFactory.createFont(italicFontStream.readAllBytes(), PdfEncodings.IDENTITY_H);
        InputStream regularFontStream = getClass().getResourceAsStream("/fonts/times.ttf");
        assert regularFontStream != null;
        this.regularFont = PdfFontFactory.createFont(regularFontStream.readAllBytes(), PdfEncodings.IDENTITY_H);
        this.subjectMap = subjectMap;
    }

    public void socialInsurance() {
        int randomNumber = 1000 + (int) (Math.random() * 4001);
        String pdfPath = getDownloadsPath(student.getNameForFile() + "-" + "soc_poist" + randomNumber + ".pdf");

        if (study == null) {
            return;
        }

        try (PdfWriter writer = new PdfWriter(pdfPath);
             PdfDocument pdf = new PdfDocument(writer);
             Document document = new Document(pdf, PageSize.A4)) {

            header(document);

            document.add(new Paragraph("PRIEBEH ŠTÚDIA\n\n")
                    .setTextAlignment(TextAlignment.CENTER)
                    .setFont(boldFont)
                    .setFontSize(12));

            studentTable(document);

            document.add(createStudyTable());

            addStudyData(document);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addStudyData(Document document) {
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
    }

    public void markExport() {
        int randomNumber = 1000 + (int) (Math.random() * 4001);
        String pdfPath = getDownloadsPath(student.getNameForFile() + "-" + "vypis_znamok" + randomNumber + ".pdf");

        if (study == null) {
            return;
        }

        try (PdfWriter writer = new PdfWriter(pdfPath);
             PdfDocument pdf = new PdfDocument(writer);
             Document document = new Document(pdf, PageSize.A4)) {

            header(document);

            document.add(new Paragraph("VÝPIS ZNÁMOK\n\n")
                    .setTextAlignment(TextAlignment.CENTER)
                    .setFont(boldFont)
                    .setFontSize(12));

            studentTable(document);

            document.add(new Paragraph(new Text("Odbor: ").setFont(boldFont))
                    .add(new Text(study.getStudyField()).setFont(regularFont)).setMultipliedLeading(0.8f));

            document.add(new Paragraph(new Text("Zameranie: ").setFont(boldFont))
                    .add(new Text(study.getStudyProgramme()).setFont(regularFont)).setMultipliedLeading(0.8f));
            document.add(new Paragraph(""));


            Table table = new Table(6);
            table.setWidth(UnitValue.createPercentValue(100));

            String[] headers = {"Názov predmetu:", "Semester", "Povinnosť:", "Dátum:", "Výsledok:", "Termín:"};
            for (String headerText : headers) {
                table.addCell(createTableCell(headerText, italicFont, true));
            }

            for (AcademicYear academicYear : study.getAcademicYears()) {
                // Pridanie rozdeľovača s názvom akademického roka
                table.addCell(new Cell(1, 6) // Spája 6 stĺpcov
                        .add(new Paragraph("Akademický rok: " + academicYear.getYears())
                                .setFont(boldFont)
                                .setTextAlignment(TextAlignment.LEFT))
                        .setBorder(Border.NO_BORDER)
                        .setPaddingTop(10f)
                        .setPaddingBottom(10f));

                // Overenie, či StudySubjects existuje a nie je null
                StudySubjects studySubjects = academicYear.getStudySubjects();
                if (studySubjects == null || studySubjects.getStudySubjectList() == null) {
                    continue; // Preskočí tento akademický rok, ak chýbajú údaje
                }

                // Prechod cez predmety v akademickom roku
                for (StudySubject.SubjectDetail studySubject : studySubjects.getStudySubjectList()) {
                    // Overenie, či UIDP nie je null
                    if (studySubject.getUIDP() == null) {
                        continue; // Preskočí tento predmet, ak chýba UIDP
                    }

                    // Nájsť spárovaný predmet
                    Subject matchingSubject = subjectMap.get(studySubject.getUIDP());
                    if (matchingSubject != null) {
                        // Pridanie detailov spárovaného predmetu do tabuľky
                        table.addCell(createTableCell(stringSubjectName(matchingSubject), italicFont, false));
                        table.addCell(createTableCell(studySubject.getSemester(), regularFont, false)
                                .setTextAlignment(TextAlignment.CENTER));
                        table.addCell(createTableCell(studySubject.getType(), regularFont, false)
                                .setTextAlignment(TextAlignment.CENTER));
                        table.addCell(createTableCell(studySubject.getEndSubjectDate(), regularFont, false)
                                .setTextAlignment(TextAlignment.CENTER));
                        table.addCell(createTableCell(studySubject.getEndSubject(), regularFont, false)
                                .setTextAlignment(TextAlignment.CENTER));
                        table.addCell(createTableCell(studySubject.getAttempt(), regularFont, false)
                                .setTextAlignment(TextAlignment.CENTER));
                    } else {
                        // Spracovanie nespárovaných predmetov
                        table.addCell(createTableCell("Unknown Subject", italicFont, false));
                        for (int i = 0; i < 5; i++) {
                            table.addCell(createTableCell("", regularFont, false).setTextAlignment(TextAlignment.CENTER));
                        }
                    }
                }
            }


            addEmptyRow(table);
            for (int i = 0; i < 6; i++) {
                table.addCell(new Cell().setBorder(Border.NO_BORDER).setBorderTop(new SolidBorder(1)));
            }

            document.add(table);
            table.complete();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addEmptyRow(Table table) {
        for (int i = 0; i < 6; i++) {
            table.addCell(new Cell()
                    .setBorder(Border.NO_BORDER)
                    .add(new Paragraph(""))); // Add an empty paragraph for spacing
        }
    }

    private String getDownloadsPath(String fileName) {
        String userHome = System.getProperty("user.home");
        return userHome + File.separator + "Downloads" + File.separator + fileName;
    }

    private Table createStudyTable() {
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

    private void header(Document document) throws IOException {
        String path = "logo/" + getFacultyShortName(study.getFaculty()) + "_logo.png";

        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(path);

        if (inputStream == null) {
            throw new IOException("Logo not found at: " + path);
        }

        byte[] imageBytes = inputStream.readAllBytes();
        ImageData imageData = ImageDataFactory.create(imageBytes);
        Image logo = new Image(imageData);

        logo.setWidth(100);
        logo.setAutoScaleHeight(true);

        // Create a container for the header
        Div container = new Div();
        container.setWidth(UnitValue.createPercentValue(100));

        // Create a div for the logo (ABSOLUTE LEFT)
        Div logoDiv = new Div();
        logoDiv.add(logo);
        logoDiv.setFixedPosition(45, 715, 100); // X=50, Y=760, Width=100 (adjust Y if needed)

        // Create a div for the centered text
        Div textDiv = new Div();
        textDiv.setTextAlignment(TextAlignment.CENTER);

        textDiv.add(new Paragraph("Univerzita Komenského v Bratislave")
                .setFont(boldFont)
                .setFontSize(16));  // Increased font size for better balance

        textDiv.add(new Paragraph(study.getFaculty())
                .setFont(regularFont)
                .setFontSize(14));

        // Add elements to the document
        document.add(logoDiv);
        document.add(textDiv);
    }

    private void studentTable(Document document) {
        float[] columnWidthsStudent = {300f, 200f};
        Table table = new Table(columnWidthsStudent);

        table.addCell(new Cell().add(new Paragraph(new Text("Študent: ").setFont(boldFont))
                        .add(new Text(student.getFullName()).setFont(regularFont)).setMultipliedLeading(0.8f))
                .setBorder(Border.NO_BORDER));

        table.setBorder(Border.NO_BORDER);
        document.add(table);
        table.complete();

        document.add(new Paragraph(new Text("Dátum a miesto narodenia: ").setFont(boldFont))
                .add(new Text(student.getBirthDate() + ", " + student.getBirthPlace() + "\n\n").setFont(regularFont))
                .setMultipliedLeading(0.8f));
    }

    private String getStudyLength() {
        return "-";
    }

    private String getFacultyShortName(String facultyName) {
        switch (facultyName.toLowerCase()) {
            case "lekárska fakulta":
                return "lf";
            case "právnická fakulta":
                return "praf";
            case "filozofická fakulta":
                return "fif";
            case "prírodovedecká fakulta":
                return "prif";
            case "pedagogická fakulta":
                return "pdf";
            case "farmaceutická fakulta":
                return "faf";
            case "fakulta telesnej výchovy a športu":
                return "ftvs";
            case "jesseniova lekárska fakulta v martine":
                return "jlf";
            case "fakulta matematiky, fyziky a informatiky":
                return "fmfi";
            case "rímskokatolícka cyrilometodská bohoslovecká fakulta":
                return "rkcmbf";
            case "evanjelická bohoslovecká fakulta":
                return "ebf";
            case "fakulta manažmentu":
                return "fm";
            case "fakulta sociálnych a ekonomických vied":
                return "fsev";
            default:
                return "uk"; // uniba
        }
    }
}
