package com.coviddata.coviddata.controller;

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
@SpringBootTest
@AutoConfigureMockMvc
public class CovidControllerTest {
    @Autowired
    CovidController controller ;

    @Autowired
    CovidServiceImpl service;

    /**
     * Method: getDataCountryByName(@RequestParam String countryName)
     */
    @Test
    public void testGetDataCountryValidName() throws Exception {
        controller.setService(service);
        List<Message> response = controller.getDataCountryByName("maroc");
        assertTrue(response.size() > 10);
    }

    @Test
    public void testGetDataCountryInValidName() throws Exception {
        controller.setService(service);
        Exception exception = assertThrows(Exception.class, () -> {
            controller.getDataCountryByName("essaouira");
        });
        String expectedMessage = "Pays inéxistant";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    /**
     * Method: getDataCountryByNameAndDate(@RequestParam String countryName, @RequestParam String date)
     */
    @Test
    public void testGetDataCountryByNameAndDate() throws Exception {
        controller.setService(service);
        MessageDTO messageDTO = controller.getDataCountryByNameAndDate("maroc", "2022-12-10");
        assertEquals(messageDTO.getCountryName().toLowerCase(), "maroc");
        assertEquals(messageDTO.getDate(), "2022-12-10");
    }

    @Test
    public void testGetDataCountryByNameWithInvalidDate() throws Exception {
        controller.setService(service);
        Exception exception = assertThrows(Exception.class, () -> {
                    MessageDTO messageDTO = controller.getDataCountryByNameAndDate("maroc", "20K2-D2-1F");
        });
        String expectedMessage = "Paramètre mal formaté";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    /**
     * Method: getTodayCountryData(@RequestParam String countryName)
     */
    @Test
    public void testGetTodayCountryData() throws Exception {
        controller.setService(service);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime now = LocalDateTime.now();
        MessageDTO messageDTO = controller.getTodayCountryData("maroc");
        assertEquals(messageDTO.getCountryName().toLowerCase(), "maroc");
        assertEquals(messageDTO.getDate(), dtf.format(now));
    }

    /**
     * Method: readCsvFile()
     */
    @Test
    public void testReadCsvFile() throws Exception {
        controller.setService(service);
        List<Message> messages = controller.readCsvFile();
        assertTrue(messages.size() >= 50);
    }


} 
