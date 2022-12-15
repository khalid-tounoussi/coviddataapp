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
import org.springframework.web.bind.MissingServletRequestParameterException;

import static com.coviddata.util.Utility.REP_MAP;

@Service
@Component
public class CovidServiceImpl implements CovidServiceInterface{
	@Autowired
	CovidDao dao;

	public List<Message> getAll(){
		return dao.findAll();
	}

	@Override
	public List<Message> getDataCountryByName(String countryName) {
		List temp;
		if( (countryName != null) && (!countryName.equals("")) && ( !countryName.matches("[a-zA-Z]+") ) )
		{
			temp = REP_MAP.get(Utility.HttpCode.REP_HTTP_INVALIDPARAM);
			System.out.println("INVALID " +countryName);

			throw new CustomException( (String)temp.get(1), (HttpStatus) temp.get(0));
		}
		List<Message> messages = dao.findByName(countryName);
		System.out.println( "SERVICE " + messages);
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
		System.out.println(dtf.format(now));
		return getDataCountryByNameAndDate(countryName, dtf.format
				(now));
	}

}
