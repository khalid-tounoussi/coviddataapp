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

@Component
@Repository
public class CovidDao {

    static List<Message> data;
    static{
        data = ApiReaderUtility.readCSV();
    }

    public List<Message> findByName( String countryName){
        System.out.println(countryName);
        List<Message> messages = data.stream()
                .filter(m -> m.getCountryName().toLowerCase().equals(countryName.toLowerCase()))
                .collect(Collectors.toList());
        System.out.println(messages);

        if( messages != null && messages.size() ==0 ){
            List temp = REP_MAP.get(Utility.HttpCode.REP_HTTP_CNINEXISTANT);
            throw new CustomException( (String)temp.get(1), (HttpStatus)temp.get(0));
        }
        System.out.println("DAO " + messages);
        return messages;
	}

    public MessageDTO findByNameAndDate(String countryName, String date){
        System.out.println("********************************");
        System.out.println(findByName(countryName));
        System.out.println("DATE :  " +  date);
        System.out.println("********************************");

        Message message = findByName(countryName).stream()
                .filter(m -> m.getDate().equals(date))
                 .findAny()
                 .orElse(null );
         if( message  == null) {
             System.out.println("Message is null");
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

    public List<Message> findAll()
    {
        return data;
    }
}