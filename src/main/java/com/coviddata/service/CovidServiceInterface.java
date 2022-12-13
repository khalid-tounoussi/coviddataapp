package com.coviddata.service;

import java.util.List;

import com.coviddata.model.Message;
import com.coviddata.model.MessageDTO;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

public interface CovidServiceInterface {
	public abstract List<Message> getDataCountryByName( String countryName);
	public abstract MessageDTO getDataCountryByNameAndDate(String countryName, String date);
	public abstract MessageDTO getDataCountryByNameToday( String countryName);
}
