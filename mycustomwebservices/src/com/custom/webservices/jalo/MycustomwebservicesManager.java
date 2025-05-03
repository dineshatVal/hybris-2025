/*
 * Copyright (c) 2020 SAP SE or an SAP affiliate company. All rights reserved.
 */
package com.custom.webservices.jalo;

import de.hybris.platform.jalo.JaloSession;
import de.hybris.platform.jalo.extension.ExtensionManager;

import com.custom.webservices.constants.MycustomwebservicesConstants;


public class MycustomwebservicesManager extends GeneratedMycustomwebservicesManager
{
	public static final MycustomwebservicesManager getInstance()
	{
		final ExtensionManager em = JaloSession.getCurrentSession().getExtensionManager();
		return (MycustomwebservicesManager) em.getExtension(MycustomwebservicesConstants.EXTENSIONNAME);
	}

}
