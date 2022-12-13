package com.coviddata.controller;

import com.coviddata.exception.CustomException;
import com.coviddata.model.Message;
import com.coviddata.model.MessageDTO;
import com.coviddata.service.CovidServiceImpl;
import com.coviddata.util.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.coviddata.util.Utility.REP_MAP;

@RestController
@RequestMapping("api")
public class CovidController {
    @Autowired
    CovidServiceImpl service;

    @GetMapping("/oneCountryData")
    List<Message> getDataCountryByName(@RequestParam String countryName) {
        return service.getDataCountryByName(countryName);
    }

    @GetMapping("/oneCountryDataWithDate")
    MessageDTO getDataCountryByNameAndDate(@RequestParam String countryName, @RequestParam String date) {
        return service.getDataCountryByNameAndDate(countryName, date);
    }

    @GetMapping("/todayCountryData")
    MessageDTO getTodayCountryData(@RequestParam String countryName) {
        return service.getDataCountryByNameToday(countryName);
    }

    @GetMapping("/exposeCSV")
    List<Message> readCsvFile() {
        return service.getAll();
    }

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
