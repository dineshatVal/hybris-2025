/*
 * Copyright (c) 2021 SAP SE or an SAP affiliate company. All rights reserved.
 */
package com.customer.feebback.setup;

import static com.customer.feebback.constants.MycustomerreviewConstants.PLATFORM_LOGO_CODE;

import de.hybris.platform.core.initialization.SystemSetup;

import java.io.InputStream;

import com.customer.feebback.constants.MycustomerreviewConstants;
import com.customer.feebback.service.MycustomerreviewService;


@SystemSetup(extension = MycustomerreviewConstants.EXTENSIONNAME)
public class MycustomerreviewSystemSetup
{
	private final MycustomerreviewService mycustomerreviewService;

	public MycustomerreviewSystemSetup(final MycustomerreviewService mycustomerreviewService)
	{
		this.mycustomerreviewService = mycustomerreviewService;
	}

	@SystemSetup(process = SystemSetup.Process.INIT, type = SystemSetup.Type.ESSENTIAL)
	public void createEssentialData()
	{
		mycustomerreviewService.createLogo(PLATFORM_LOGO_CODE);
	}

	private InputStream getImageStream()
	{
		return MycustomerreviewSystemSetup.class.getResourceAsStream("/mycustomerreview/sap-hybris-platform.png");
	}
}
