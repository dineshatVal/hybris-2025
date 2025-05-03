/*
 * Copyright (c) 2021 SAP SE or an SAP affiliate company. All rights reserved.
 */
package com.custom.delivery.setup;

import static com.custom.delivery.constants.MycustomdeliveryConstants.PLATFORM_LOGO_CODE;

import de.hybris.platform.core.initialization.SystemSetup;

import java.io.InputStream;

import com.custom.delivery.constants.MycustomdeliveryConstants;
import com.custom.delivery.service.MycustomdeliveryService;


@SystemSetup(extension = MycustomdeliveryConstants.EXTENSIONNAME)
public class MycustomdeliverySystemSetup
{
	private final MycustomdeliveryService mycustomdeliveryService;

	public MycustomdeliverySystemSetup(final MycustomdeliveryService mycustomdeliveryService)
	{
		this.mycustomdeliveryService = mycustomdeliveryService;
	}

	@SystemSetup(process = SystemSetup.Process.INIT, type = SystemSetup.Type.ESSENTIAL)
	public void createEssentialData()
	{
		mycustomdeliveryService.createLogo(PLATFORM_LOGO_CODE);
	}

	private InputStream getImageStream()
	{
		return MycustomdeliverySystemSetup.class.getResourceAsStream("/mycustomdelivery/sap-hybris-platform.png");
	}
}
