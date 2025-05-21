package com.sample.module.core.drm.pdfstamping;

import java.io.File;
import java.io.IOException;

public interface PDFStampService {
    File stampPdfWithWatermark(String origFileName, File originalPdf, String watermarkText) throws IOException;

}
