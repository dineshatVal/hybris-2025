/*
 * Copyright (c) 2021 SAP SE or an SAP affiliate company. All rights reserved.
 */
package com.custom.delivery.controller;

import static com.custom.delivery.constants.MycustomdeliveryConstants.PLATFORM_LOGO_CODE;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.custom.delivery.service.MycustomdeliveryService;


@Controller
public class MycustomdeliveryHelloController
{
	@Autowired
	private MycustomdeliveryService mycustomdeliveryService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String printWelcome(final ModelMap model)
	{
		model.addAttribute("logoUrl", mycustomdeliveryService.getHybrisLogoUrl(PLATFORM_LOGO_CODE));
		return "welcome";
	}
}
