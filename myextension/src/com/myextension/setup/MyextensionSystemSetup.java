/*
 * Copyright (c) 2021 SAP SE or an SAP affiliate company. All rights reserved.
 */
package com.myextension.setup;

import static com.myextension.constants.MyextensionConstants.PLATFORM_LOGO_CODE;

import de.hybris.platform.core.initialization.SystemSetup;

import java.io.InputStream;

import com.myextension.constants.MyextensionConstants;
import com.myextension.service.MyextensionService;


@SystemSetup(extension = MyextensionConstants.EXTENSIONNAME)
public class MyextensionSystemSetup
{
	private final MyextensionService myextensionService;

	public MyextensionSystemSetup(final MyextensionService myextensionService)
	{
		this.myextensionService = myextensionService;
	}

	@SystemSetup(process = SystemSetup.Process.INIT, type = SystemSetup.Type.ESSENTIAL)
	public void createEssentialData()
	{
		myextensionService.createLogo(PLATFORM_LOGO_CODE);
	}

	private InputStream getImageStream()
	{
		return MyextensionSystemSetup.class.getResourceAsStream("/myextension/sap-hybris-platform.png");
	}
}
