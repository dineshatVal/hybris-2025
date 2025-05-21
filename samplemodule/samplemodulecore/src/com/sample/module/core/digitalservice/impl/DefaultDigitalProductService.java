package com.sample.module.core.digitalservice.impl;

import com.sample.module.core.digitalservice.DigitalProductService;
import com.sample.module.core.drm.pdfstamping.PDFStampService;
import org.apache.hc.client5.http.fluent.Request;
import org.springframework.stereotype.Service;


import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Paths;

@Service("defaultDigitalProductService")
public class DefaultDigitalProductService implements DigitalProductService {

    @Resource(name = "pdfStampingService")
    private PDFStampService pdfStampingService;

    @Override
    public boolean downloadRequest(String fileUrl) throws MalformedURLException {
        //File targetFile = new File("D://downloaded.txt");
        //File targetFile = Paths.get("D:", "digitalDownload.txt").toFile();

        URL url = new URL(fileUrl);
        String fileName = new File(url.getPath()).getName();  // dynamically extract filename
        File targetFile = Paths.get("D:", fileName).toFile();


        try {
            Request.get(fileUrl)
                    .execute()
                    .saveContent(targetFile);


            if(fileName.endsWith(".pdf")) {
                pdfStampingService.stampPdfWithWatermark(fileName, targetFile, "SAP Confidential");
                if(targetFile.exists()) {
                    // Delete the original file
                    targetFile.delete();
                }
                return true;
            }

            return true;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
