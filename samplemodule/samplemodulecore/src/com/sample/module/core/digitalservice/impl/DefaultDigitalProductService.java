package com.sample.module.core.digitalservice.impl;

import com.sample.module.core.digitalservice.DigitalProductService;
import org.apache.hc.client5.http.fluent.Request;
import org.springframework.stereotype.Service;


import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Paths;

@Service("defaultDigitalProductService")
public class DefaultDigitalProductService implements DigitalProductService {
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
            return true;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
