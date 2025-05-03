/*
 * Copyright (c) 2019 SAP SE or an SAP affiliate company. All rights reserved.
 */
package com.sample.module.fulfilmentprocess.jalo;

import de.hybris.platform.jalo.JaloSession;
import de.hybris.platform.jalo.extension.ExtensionManager;
import com.sample.module.fulfilmentprocess.constants.SamplemoduleFulfilmentProcessConstants;

public class SamplemoduleFulfilmentProcessManager extends GeneratedSamplemoduleFulfilmentProcessManager
{
	public static final SamplemoduleFulfilmentProcessManager getInstance()
	{
		ExtensionManager em = JaloSession.getCurrentSession().getExtensionManager();
		return (SamplemoduleFulfilmentProcessManager) em.getExtension(SamplemoduleFulfilmentProcessConstants.EXTENSIONNAME);
	}
	
}
