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

public class DocumentExporter {

    public static void socialInsurance() {
        String pdfPath = "student_record.pdf";

        try {
            PdfWriter writer = new PdfWriter(pdfPath);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            PdfFont boldFont = PdfFontFactory.createFont("Times-Bold");
            PdfFont italicFont = PdfFontFactory.createFont("Times-Italic");
            PdfFont regularFont = PdfFontFactory.createFont("Times-Roman");

            // Title
            document.add(new Paragraph("Univerzita Komenského v Bratislave")
                    .setTextAlignment(TextAlignment.CENTER)
                    .setFont(boldFont)
                    .setFontSize(14));
            document.add(new Paragraph("Fakulta matematiky, fyziky a informatiky")
                    .setTextAlignment(TextAlignment.CENTER)
                    .setFont(regularFont)
                    .setFontSize(12));
            document.add(new Paragraph("\nŠTÚDIÁ ŠTUDENTA\n")
                    .setTextAlignment(TextAlignment.CENTER)
                    .setFont(boldFont)
                    .setFontSize(12));

            // Student info
            document.add(new Paragraph(new Text("Študent: ").setFont(boldFont))
                    .add(new Text("Jozko Cibula").setFont(regularFont))
                    .setMultipliedLeading(0.5f));

            document.add(new Paragraph(new Text("Rodená: ").setFont(boldFont))
                    .setMultipliedLeading(0.5f));

            document.add(new Paragraph(new Text("Dátum a miesto narodenia: ").setFont(boldFont))
                    .add(new Text("32.13.4562").setFont(regularFont))
                    .setMultipliedLeading(0.5f));

            document.add(new Paragraph("\nUKONČENÉ ŠTÚDIÁ").setFont(boldFont).setFontSize(12));

            // Table for study program details
            float[] columnWidths = {100f, 300f};
            Table table = new Table(columnWidths);

            table.addCell(new Cell().add(new Paragraph("Študijný program:").setMultipliedLeading(0.5f)
                    .setFont(italicFont)).setBorder(Border.NO_BORDER));
            table.addCell(new Cell().add(new Paragraph("matematika").setMultipliedLeading(0.5f)
                    .setFont(boldFont)).setBorder(Border.NO_BORDER));
            table.addCell(new Cell().add(new Paragraph("Forma štúdia:").setMultipliedLeading(0.5f)
                    .setFont(italicFont)).setBorder(Border.NO_BORDER));
            table.addCell(new Cell().add(new Paragraph("denná").setMultipliedLeading(0.5f)
                    .setFont(regularFont)).setBorder(Border.NO_BORDER));
            table.addCell(new Cell().add(new Paragraph("Začiatok štúdia:").setMultipliedLeading(0.5f)
                    .setFont(italicFont)).setBorder(Border.NO_BORDER));
            table.addCell(new Cell().add(new Paragraph("05.09.2008").setMultipliedLeading(0.5f)
                    .setFont(regularFont)).setBorder(Border.NO_BORDER));
            table.addCell(new Cell().add(new Paragraph("Koniec štúdia:").setMultipliedLeading(0.5f)
                    .setFont(italicFont)).setBorder(Border.NO_BORDER));
            table.addCell(new Cell().add(new Paragraph("29.06.2011").setMultipliedLeading(0.5f)
                    .setFont(regularFont)).setBorder(Border.NO_BORDER));
            table.addCell(new Cell().add(new Paragraph("Stupeň štúdia:").setMultipliedLeading(0.5f)
                    .setFont(italicFont)).setBorder(Border.NO_BORDER));
            table.addCell(new Cell().add(new Paragraph("I. bakalársky stupeň").setMultipliedLeading(0.5f)
                    .setFont(regularFont)).setBorder(Border.NO_BORDER));
            table.addCell(new Cell().add(new Paragraph("Doba štúdia").setMultipliedLeading(0.5f)
                    .setFont(italicFont)).setBorder(Border.NO_BORDER));
            table.addCell(new Cell().add(new Paragraph("3").setMultipliedLeading(0.5f)
                    .setFont(regularFont)).setBorder(Border.NO_BORDER));
            table.addCell(new Cell().add(new Paragraph("Spôsob ukončenia:").setMultipliedLeading(0.5f)
                    .setFont(italicFont)).setBorder(Border.NO_BORDER));
            table.addCell(new Cell().add(new Paragraph("Absolvovanie - riadne ukončenie štúdia (§65)").setMultipliedLeading(0.5f)
                    .setFont(regularFont)).setBorder(Border.NO_BORDER));

            // Add table to document
            table.setBorder(Border.NO_BORDER);
            document.add(table);

            // Interruptions section
            document.add(new Paragraph("\nPrerušenia:").setFont(boldFont)
                    .setMultipliedLeading(0.5f));
            document.add(new Paragraph("01.09.2015 - 31.08.2016, materská dovolenka").setFont(regularFont)
                    .setMultipliedLeading(0.5f));
            document.add(new Paragraph("01.08.2019 - 31.08.2021, materská dovolenka").setFont(regularFont)
                    .setMultipliedLeading(0.5f));

            // Close document
            document.close();

            System.out.println("PDF created successfully: " + pdfPath);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        socialInsurance();
    }
}
