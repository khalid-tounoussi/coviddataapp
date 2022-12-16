package com.coviddata.util;

import org.springframework.http.HttpStatus;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * The type Utility.
 */
public class Utility {
    /**
     * The enum Http code.
     */
    public enum HttpCode {
        /**
         * Rep http cnabsent http code.
         */
        REP_HTTP_CNABSENT,
        /**
         * Rep http invalidparam http code.
         */
        REP_HTTP_INVALIDPARAM,
        /**
         * Rep http cninexistant http code.
         */
        REP_HTTP_CNINEXISTANT,
        /**
         * Rep http dtabsent http code.
         */
        REP_HTTP_DTABSENT,
        /**
         * Rep http dtinexistant http code.
         */
        REP_HTTP_DTINEXISTANT,
        /**
         * Rep http ioexception http code.
         */
        REP_HTTP_IOEXCEPTION
    }

    /**
     * Build rep list.
     *
     * @param c the c
     * @param m the m
     * @return the list
     */
    public static final List buildRep(HttpStatus c, String m) {
        List l = new ArrayList<>();
        l.add(c);
        l.add(m);
        return l;
    }

    /**
     * The constant REP_MAP.
     */
    public static final Map<HttpCode, List> REP_MAP = new HashMap<>();

    static {
        REP_MAP.put(HttpCode.REP_HTTP_CNABSENT, buildRep(HttpStatus.BAD_REQUEST, "Paramètre 'countryName' est obligatoire"));
        REP_MAP.put(HttpCode.REP_HTTP_INVALIDPARAM, buildRep(HttpStatus.BAD_REQUEST, "Paramètre mal formaté"));
        REP_MAP.put(HttpCode.REP_HTTP_CNINEXISTANT, buildRep(HttpStatus.OK, "Pays inéxistant"));
        REP_MAP.put(HttpCode.REP_HTTP_DTABSENT, buildRep(HttpStatus.BAD_REQUEST, "Paramètre 'date' est obligatoire"));
        REP_MAP.put(HttpCode.REP_HTTP_DTINEXISTANT, buildRep(HttpStatus.OK, "Les données pour cette date sont inéxistants."));
        REP_MAP.put(HttpCode.REP_HTTP_IOEXCEPTION, buildRep(HttpStatus.INTERNAL_SERVER_ERROR, "Impossible de récupérer les données."));
    }


    /**
     * Reformat date string.
     *
     * @param inDate the in date
     * @return the string
     */
    public static String reformatDate(String inDate) {
        String[] date = inDate.split("-");

        String year = date[0];
        String month = date[1];
        String day = date[2];

        if (month.length() == 1) month = "0" + month;
        if (day.length() == 1) day = "0" + day;
        return year + "-" + month + "-" + day;
    }

    /**
     * Is valid date boolean.
     *
     * @param inDate the in date
     * @return the boolean
     */
    public static boolean isValidDate(String inDate) {
        if (inDate == null)
            return false;
        //set the format to use as a constructor argument
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        if (inDate.trim().length() != dateFormat.toPattern().length())
            return false;
        dateFormat.setLenient(false);
        try {
            dateFormat.parse(inDate.trim());
        } catch (ParseException pe) {
            return false;
        }
        return true;
    }

    /**
     * Check date string.
     *
     * @param date the date
     * @return the string
     */
    public static String checkDate(String date) {
        date = reformatDate(date);
        if (isValidDate(date)) {
            return date;
        }
        return null;

    }
}

