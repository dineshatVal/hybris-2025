package com.sample.module.core.drm.pdfstamping.impl;

import com.sample.module.core.drm.pdfstamping.PDFStampService;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

public class DefaultPDFStampService implements PDFStampService {
    @Override
    public File stampPdfWithWatermark(String origFileName, File originalPdf, String watermarkText) throws IOException {
        PDDocument document = PDDocument.load(originalPdf);

        for (PDPage page : document.getPages()) {
            PDRectangle mediaBox = page.getMediaBox();
            float x = mediaBox.getWidth() / 4;
            float y = mediaBox.getHeight() / 2;

            PDPageContentStream contentStream = new PDPageContentStream(document, page,
                    PDPageContentStream.AppendMode.APPEND, true);

            contentStream.beginText();
            contentStream.setFont(PDType1Font.TIMES_BOLD, 24);
            contentStream.setNonStrokingColor(200, 0, 0); // red
            contentStream.setTextRotation(Math.toRadians(45), x, y);
            contentStream.showText(watermarkText);
            contentStream.endText();
            contentStream.close();
        }

        // Save the new stamped PDF to a temp file
        String stampedFileName = origFileName.replace(".pdf", "_stamped.pdf");
        File targetStampedFile = Paths.get("D:", stampedFileName).toFile();
        //File stampedFile = File.createTempFile("stamped_", ".pdf");
        document.save(targetStampedFile);
        document.close();
        return targetStampedFile;
    }
}
