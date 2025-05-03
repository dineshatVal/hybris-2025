/*
 * Copyright (c) 2021 SAP SE or an SAP affiliate company. All rights reserved.
 */
package com.customer.feebback.controller;

import static com.customer.feebback.constants.MycustomerreviewConstants.PLATFORM_LOGO_CODE;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.customer.feebback.service.MycustomerreviewService;


@Controller
public class MycustomerreviewHelloController
{
	@Autowired
	private MycustomerreviewService mycustomerreviewService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String printWelcome(final ModelMap model)
	{
		model.addAttribute("logoUrl", mycustomerreviewService.getHybrisLogoUrl(PLATFORM_LOGO_CODE));
		return "welcome";
	}
}
