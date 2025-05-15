package com.sample.module.core.digitalservice;

import java.net.MalformedURLException;

public interface DigitalProductService {
    boolean downloadRequest(String url) throws MalformedURLException;
}
