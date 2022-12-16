package com.coviddata.service;

import java.util.List;

import com.coviddata.model.Message;
import com.coviddata.model.MessageDTO;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * The interface Covid service interface.
 */
public interface CovidServiceInterface {
	/**
	 * Gets data country by name.
	 *
	 * @param countryName the country name
	 * @return the data country by name
	 */
	public abstract List<Message> getDataCountryByName( String countryName);

	/**
	 * Gets data country by name and date.
	 *
	 * @param countryName the country name
	 * @param date        the date
	 * @return the data country by name and date
	 */
	public abstract MessageDTO getDataCountryByNameAndDate(String countryName, String date);

	/**
	 * Gets data country by name today.
	 *
	 * @param countryName the country name
	 * @return the data country by name today
	 */
	public abstract MessageDTO getDataCountryByNameToday( String countryName);
}
