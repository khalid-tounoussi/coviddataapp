package com.coviddata.controller;

import com.coviddata.exception.CustomException;
import com.coviddata.model.Message;
import com.coviddata.model.MessageDTO;
import com.coviddata.service.CovidServiceImpl;
import com.coviddata.util.Utility;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.coviddata.util.Utility.REP_MAP;

/**
 * The type Covid controller.
 */
@RestController
@RequestMapping("api")
@Data
public class CovidController {
    /**
     * The Service.
     */
    @Autowired
    CovidServiceImpl service;

    /**
     * Gets data country by name.
     * Cette fonction permet de retoutner les données d'un pays avec countryName pour la date spécifié
     * @param countryName the country name
     * @return the data country by name
     */
    @GetMapping("/oneCountryData")
    public List<Message> getDataCountryByName(@RequestParam String countryName) {
        System.out.println(countryName);
        return service.getDataCountryByName(countryName);
    }

    /**
     * Gets data country by name and date.
     *
     * @param countryName the country name
     * @param date        the date
     * @return the data country by name and date
     */
    @GetMapping("/oneCountryDataWithDate")
    public MessageDTO getDataCountryByNameAndDate(@RequestParam String countryName, @RequestParam String date) {
        return service.getDataCountryByNameAndDate(countryName, date);
    }

    /**
     * Gets today country data.
     *
     * @param countryName the country name
     * @return the today country data
     */
    @GetMapping("/todayCountryData")
    public MessageDTO getTodayCountryData(@RequestParam String countryName) {
        return service.getDataCountryByNameToday(countryName);
    }

    /**
     * Read csv file list.
     *
     * @return the list
     */
    @GetMapping("/exposeCSV")
    public List<Message> readCsvFile() {
        return service.getAll();
    }

    /**
     * Handle exception response entity.
     *
     * @param e the e
     * @return the response entity
     */
    @ExceptionHandler({Exception.class})
    public ResponseEntity handleException(Exception e) {
        Map<String, String> data = new HashMap<>( );
        List temp;
        data.put( "message", e.getMessage() );
        if (e instanceof MissingServletRequestParameterException) {
            if ( e.getMessage().contains("countryName") )
            {
                temp = REP_MAP.get(Utility.HttpCode.REP_HTTP_CNABSENT);
            } else{
                temp = REP_MAP.get(Utility.HttpCode.REP_HTTP_DTABSENT);
            }
            data.clear();
            data.put( "message",  (String)temp.get(1));
            return ResponseEntity.status((HttpStatus)temp.get(0)).body( data);
        } else if (e instanceof CustomException) {
            return ResponseEntity.status(((CustomException) e).getStatus()).body(data);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(data);
    }
}
