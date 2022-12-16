package com.coviddata.coviddata.service;

import com.coviddata.controller.CovidController;
import com.coviddata.model.Message;
import com.coviddata.model.MessageDTO;
import com.coviddata.service.CovidServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.Assert.*;


/**
 * CovidController Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>Dec 15, 2022</pre>
 */
//@WebMvcTest(CovidController.class)
@AutoConfigureMockMvc
@SpringBootTest
public class CovidServiceImplTest {

    @Autowired
    CovidServiceImpl service;

    /**
     * Method: getAll()
     */
    @Test
    public void testGetAll() throws Exception {
        List<Message> messages = service.getAll();
        assertTrue( messages.size() >= 50);
    }

    /**
     * Method: getDataCountryByName(String countryName)
     */
    @Test
    public void testGetDataCountryByName() throws Exception {
        List<Message> response = service.getDataCountryByName("maroc");
        assertTrue(response.size() > 10);
    }

    /**
     * Method: getDataCountryByName(String countryName)
     */
    @Test
    public void testGetDataCountryInvalidName() throws Exception {
        Exception exception = assertThrows(Exception.class, () -> {
            service.getDataCountryByName("essaouira");
        });
        String expectedMessage = "Pays inéxistant";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    /**
     * Method: getDataCountryByNameAndDate(String countryName, String date)
     */
    @Test
    public void testGetDataCountryByNameAndDate() throws Exception {
        MessageDTO messageDTO = service.getDataCountryByNameAndDate("maroc", "2022-12-10");
        assertEquals(messageDTO.getCountryName().toLowerCase(), "maroc");
        assertEquals(messageDTO.getDate(), "2022-12-10");
    }

    /**
     * Method: getDataCountryByNameAndDate(String countryName, String date)
     */
    @Test
    public void testGetDataCountryByNameWithInvalidDate() throws Exception {
        Exception exception = assertThrows(Exception.class, () -> {
            MessageDTO messageDTO = service.getDataCountryByNameAndDate("maroc", "20K2-D2-1F");
        });
        String expectedMessage = "Paramètre mal formaté";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    /**
     * Method: getDataCountryByNameToday(String countryName)
     */
    @Test
    public void testGetDataCountryByNameToday() throws Exception {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime now = LocalDateTime.now();
        MessageDTO messageDTO = service.getDataCountryByNameToday("maroc");
        assertEquals(messageDTO.getCountryName().toLowerCase(), "maroc");
        assertEquals(messageDTO.getDate(), dtf.format(now));
    }


} 
