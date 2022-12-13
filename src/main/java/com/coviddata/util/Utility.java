package com.coviddata.util;

import org.springframework.http.HttpStatus;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;



public class Utility {
    public enum HttpCode {
        REP_HTTP_CNABSENT,
        REP_HTTP_INVALIDPARAM,
        REP_HTTP_CNINEXISTANT,
        REP_HTTP_DTABSENT,
        REP_HTTP_DTINEXISTANT,
        REP_HTTP_IOEXCEPTION
    }
    public static final List buildRep(HttpStatus c, String m) {
        List l = new ArrayList<>();
        l.add(c); l.add(m);
        return l;
    }
    public static final Map<HttpCode, List> REP_MAP = new HashMap<>();

    static {
        REP_MAP.put(HttpCode.REP_HTTP_CNABSENT, buildRep(HttpStatus.BAD_REQUEST,"Paramètre 'countryName' est obligatoire"));
        REP_MAP.put(HttpCode.REP_HTTP_INVALIDPARAM, buildRep(HttpStatus.BAD_REQUEST,"Paramètre mal formaté"));
        REP_MAP.put(HttpCode.REP_HTTP_CNINEXISTANT, buildRep(HttpStatus.OK,"Pays inéxistant"));
        REP_MAP.put(HttpCode.REP_HTTP_DTABSENT, buildRep(HttpStatus.BAD_REQUEST,"Paramètre 'date' est obligatoire"));
        REP_MAP.put(HttpCode.REP_HTTP_DTINEXISTANT, buildRep(HttpStatus.OK,"Les données pour cette date sont inéxistants."));
        REP_MAP.put(HttpCode.REP_HTTP_IOEXCEPTION, buildRep(HttpStatus.INTERNAL_SERVER_ERROR,"Impossible de récupérer les données."));
    }


    public static String reformatDate(String inDate) {
        if (inDate == null)
            return null;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        if (inDate.trim().length() != dateFormat.toPattern().length())
            return null;
        dateFormat.setLenient(false);
        try {
            dateFormat.parse(inDate.trim());
        } catch (ParseException pe) {
            return null;
        }
    return inDate;
    }

    public static boolean isValidDate(String inDate) {
        if (inDate == null)
            return false;
        //set the format to use as a constructor argument
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        if (inDate.trim().length() != dateFormat.toPattern().length())
            return false;
        dateFormat.setLenient(false);
        try {
            //parse the inDate parameter
            dateFormat.parse(inDate.trim());
        }
        catch (ParseException pe) {
            return false;
        }
        return true;
    }
    public static String checkDate(String date){
        if( isValidDate(date))
        {
            return reformatDate(date);
        }
        return null;

    }
}

