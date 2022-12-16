package com.coviddata.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import com.coviddata.dao.CovidDao;
import com.coviddata.exception.CustomException;
import com.coviddata.model.Message;
import com.coviddata.model.MessageDTO;
import com.coviddata.util.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import static com.coviddata.util.Utility.REP_MAP;
import static com.coviddata.util.Utility.checkDate;

/**
 * The type Covid service.
 */
@Service
@Component
public class CovidServiceImpl implements CovidServiceInterface{
	/**
	 * The Dao.
	 */
	@Autowired
	CovidDao dao;

	/**
	 * Get all list.
	 *
	 * @return the list
	 */
	public List<Message> getAll(){
		return dao.findAll();
	}

	/**
	 * Only letters spaces boolean.
	 *
	 * @param s the s
	 * @return the boolean
	 */
	public static boolean onlyLettersSpaces(String s){
		for(int i=0;i<s.length();i++){
			char ch = s.charAt(i);
			if (Character.isLetter(ch) || ch == ' ') {
				continue;
			}
			return false;
		}
		return true;
	}

	/**
	 * Check country name.
	 *
	 * @param countryName the country name
	 */
	void checkCountryName(String countryName){
		List temp;

		if( (countryName != null) && (!countryName.equals("")) && ( !onlyLettersSpaces(countryName) ) )
		{
			temp = REP_MAP.get(Utility.HttpCode.REP_HTTP_INVALIDPARAM);

			throw new CustomException( (String)temp.get(1), (HttpStatus) temp.get(0));
		}
	}
	@Override
	public List<Message> getDataCountryByName(String countryName) {
		System.out.println(countryName);
		List temp;
		checkCountryName(countryName);
		List<Message> messages = dao.findByName(countryName);
		if( messages.size() == 0 )
		{
			temp = REP_MAP.get(Utility.HttpCode.REP_HTTP_CNINEXISTANT);
			throw new CustomException((String)temp.get(1), (HttpStatus) temp.get(0));
		}
		return dao.findByName(countryName);
	}

	@Override
	public MessageDTO getDataCountryByNameAndDate(String countryName, String date) {
		List temp ;
		if( Utility.checkDate(date) == null)
		{
			temp = REP_MAP.get(Utility.HttpCode.REP_HTTP_INVALIDPARAM);
			throw new CustomException( (String) temp.get(1) ,(HttpStatus) temp.get(0));

		}
		date = Utility.checkDate(date);
		checkCountryName(countryName);
		MessageDTO messageDTO =  dao.findByNameAndDate(countryName, date);
		if (messageDTO == null)
		{
			temp = REP_MAP.get(Utility.HttpCode.REP_HTTP_DTINEXISTANT);
			throw new CustomException( (String) temp.get(1) ,(HttpStatus) temp.get(0));
		}
		return messageDTO;
	}

	@Override
	public MessageDTO getDataCountryByNameToday(String countryName) {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDateTime now = LocalDateTime.now();
		return getDataCountryByNameAndDate(countryName, dtf.format
				(now));
	}

}
