package com.coviddata.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.csv.CSVRecord;

import java.io.Serializable;

import static java.lang.StrictMath.abs;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MessageDTO implements Serializable {
    private String date;
    private String countryName;
    private String infections;
    private String deaths;
    private String recovered;

    public static MessageDTO fromCSVRecord( Message message){
        return new MessageDTO(message.getDate(), message.getCountryName(), message.getInfections()
                , message.getDeaths(), message.getRecovered());
    }

    public static MessageDTO calculateDifference(Message messageDTO1, Message messageDTO2) {
        String infections = String.valueOf(abs(Integer.valueOf(messageDTO1.getInfections()) - Integer.valueOf(messageDTO2.getInfections())));
        String deaths = String.valueOf(abs(Integer.valueOf(messageDTO1.getDeaths()) - Integer.valueOf(messageDTO2.getDeaths())));
        return new MessageDTO(messageDTO1.getDate(), messageDTO2.getCountryName(), infections, deaths, messageDTO1.getRecovered());
    }

}
