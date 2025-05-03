/*
 * Copyright (c) 2020 SAP SE or an SAP affiliate company. All rights reserved.
 */
package com.custom.webservices.facades;

import de.hybris.platform.core.servicelayer.data.SearchPageData;

import java.util.List;

import com.custom.webservices.data.UserData;
import com.custom.webservices.dto.SampleWsDTO;
import com.custom.webservices.dto.TestMapWsDTO;


public interface SampleFacades
{
	SampleWsDTO getSampleWsDTO(final String value);

	UserData getUser(String id);

	List<UserData> getUsers();

	SearchPageData<UserData> getUsers(SearchPageData<?> params);

	void updateUser(String id, UserData user);

	TestMapWsDTO getMap();
}
