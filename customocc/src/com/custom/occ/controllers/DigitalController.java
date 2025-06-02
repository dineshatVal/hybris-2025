/*
 * Copyright (c) 2020 SAP SE or an SAP affiliate company. All rights reserved.
 */
package com.custom.occ.controllers;

import com.custom.occ.dto.CustomProductWsDTO;
import com.sample.module.facades.DigitalFacade;
import com.sample.module.facades.dto.ResponseDTO;
import de.hybris.platform.commerceservices.request.mapping.annotation.ApiVersion;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Controller
@ApiVersion("v2")
@Api(tags = "Digital Site Controller")
@RequestMapping(value = "/{baseSiteId}")
public class DigitalController
{
    private static final Logger LOG = LoggerFactory.getLogger(DigitalController.class);

    @Resource(name = "digitalFacade")
    private DigitalFacade digitalFacade;

    @Secured("ROLE_CLIENT")
    @GetMapping("/hello-digital")
    public ResponseEntity<String> sayHelloDigital() {
        return ResponseEntity.ok("Hello from my digital controller!");
    }

    /*@PostMapping("/addUrlProps")
    public ResponseEntity<String> addUrlProps(@RequestParam String code, @RequestParam String email) {
        String generateDownloadToken = digitalFacade.generateDownloadLink(code, email);
        return ResponseEntity.ok(generateDownloadToken);
    }*/
    @Secured("ROLE_CLIENT")
    @PostMapping("/addDownloadUrlProps")
    public ResponseEntity<String> addDownloadUrlProps(@RequestParam String orderNum, @RequestParam String code, @RequestParam String email) {
        String generateDownloadToken = digitalFacade.generateDownloadLink(orderNum, code, email);
        return ResponseEntity.ok(generateDownloadToken);
    }

    /*@GetMapping("/getDownloadAccess")
    public ResponseEntity<String> getDownloadAccess(@RequestParam String token, @RequestParam String email) throws Exception {
        String downloadLink = digitalFacade.getDownloadAccess(token,email);
        return ResponseEntity.ok(downloadLink);
    }*/
    @Secured("ROLE_CLIENT")
    @PostMapping("/updateDigitalProduct")
    public ResponseEntity<String> updateDigitalProduct(@RequestBody CustomProductWsDTO customProductWsDTO) throws Exception {
        String downloadLink = digitalFacade.updateDigitalProduct(customProductWsDTO);
        return ResponseEntity.ok(downloadLink);
    }

    /*@GetMapping("/getSecuredDownloadAccess")
    public ResponseEntity<String> getSecuredDownloadAccess(@RequestParam String token, @RequestParam String email) throws Exception {
        String downloadLink = digitalFacade.getSecuredDownloadAccess(token,email);
        return ResponseEntity.ok(downloadLink);
    }*/

    @Secured("ROLE_CLIENT")
    @GetMapping("/getSecuredDownloadAccess")
    public ResponseEntity<String> getSecuredDownloadAccess(@RequestParam String orderNum, @RequestParam String code, @RequestParam String email) throws Exception {
        String downloadLink = digitalFacade.getSecuredDownloadAccess(orderNum,code,email);
        return ResponseEntity.ok(downloadLink);
    }

    @Secured("ROLE_CLIENT")
    @GetMapping("/getSecuredDownloadAccessV1")
    public ResponseEntity<ResponseDTO> getSecuredDownloadAccessV1(@RequestParam String orderNum, @RequestParam String code, @RequestParam String email) throws Exception {
        ResponseDTO responseDTO = digitalFacade.getSecuredDownloadAccessV1(orderNum,code,email);
        return ResponseEntity.ok(responseDTO);
    }


}
