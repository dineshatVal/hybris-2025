/*
 * Copyright (c) 2021 SAP SE or an SAP affiliate company. All rights reserved.
 */
package com.customer.feebback.service;

public interface MycustomerreviewService
{
	String getHybrisLogoUrl(String logoCode);

	void createLogo(String logoCode);
}
