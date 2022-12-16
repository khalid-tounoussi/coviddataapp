package com.coviddata.dao;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.coviddata.exception.CustomException;
import com.coviddata.model.Message;
import com.coviddata.model.MessageDTO;
import com.coviddata.util.ApiReaderUtility;
import com.coviddata.util.Utility;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import static com.coviddata.util.Utility.REP_MAP;

/**
 * The type Covid dao.
 */
@Component
@Repository
public class CovidDao {

    /**
     * The Data.
     */
    static List<Message> data;
    static{
        data = ApiReaderUtility.readCSV();
    }

    /**
     * Find by name list.
     *
     * @param countryName the country name
     * @return the list
     */
    public List<Message> findByName( String countryName){
        List<Message> messages = data.stream()
                .filter(m -> m.getCountryName().toLowerCase().equals(countryName.toLowerCase()))
                .collect(Collectors.toList());

        if( messages != null && messages.size() ==0 ){
            List temp = REP_MAP.get(Utility.HttpCode.REP_HTTP_CNINEXISTANT);
            throw new CustomException( (String)temp.get(1), (HttpStatus)temp.get(0));
        }
        return messages;
	}

    /**
     * Find by name and date message dto.
     *
     * @param countryName the country name
     * @param date        the date
     * @return the message dto
     */
    public MessageDTO findByNameAndDate(String countryName, String date){
        Message message = findByName(countryName).stream()
                .filter(m -> m.getDate().equals(date))
                 .findAny()
                 .orElse(null );
         if( message  == null) {
             List temp = REP_MAP.get(Utility.HttpCode.REP_HTTP_DTINEXISTANT);
             throw new CustomException( (String)temp.get(1), (HttpStatus)temp.get(0));
        }
         int index = findByName(countryName).indexOf(message);
         if( index < findByName(countryName).size() -1) {
             Message temp = findByName(countryName).get(index +1 );
             MessageDTO messageDTO = MessageDTO.calculateDifference( message, temp);
             return messageDTO;
         } else {
             return null;
         }

    }

    /**
     * Find all list.
     *
     * @return the list
     */
    public List<Message> findAll()
    {
        return data;
    }
}