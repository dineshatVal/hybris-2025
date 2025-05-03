/*
 * Copyright (c) 2019 SAP SE or an SAP affiliate company. All rights reserved.
 */
package com.sample.module.core.jalo;

import de.hybris.platform.jalo.JaloSession;
import de.hybris.platform.jalo.extension.ExtensionManager;
import com.sample.module.core.constants.SamplemoduleCoreConstants;
import com.sample.module.core.setup.CoreSystemSetup;


/**
 * Do not use, please use {@link CoreSystemSetup} instead.
 * 
 */
public class SamplemoduleCoreManager extends GeneratedSamplemoduleCoreManager
{
	public static final SamplemoduleCoreManager getInstance()
	{
		final ExtensionManager em = JaloSession.getCurrentSession().getExtensionManager();
		return (SamplemoduleCoreManager) em.getExtension(SamplemoduleCoreConstants.EXTENSIONNAME);
	}
}
