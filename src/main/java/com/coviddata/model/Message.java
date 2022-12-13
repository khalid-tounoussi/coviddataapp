package com.coviddata.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.csv.CSVRecord;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Message implements Serializable{
    private String date;
    private String countryName;
    private String infections;
    private String deaths;
    private String recovered;
    private String deathRate;
    private String recoveredRate;
    private String infectionRate;

    public static Message fromCSVRecord( CSVRecord record){
        String[] data = record.get(0).split(";");
        return new Message(data[0], data[1], data[2], data[3]
                , data[4], data[5], data[6], data[7]);
    }

}
