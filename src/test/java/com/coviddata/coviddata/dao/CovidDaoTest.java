package com.coviddata.coviddata.dao;

import com.coviddata.dao.CovidDao;
import com.coviddata.model.Message;
import com.coviddata.model.MessageDTO;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

/**
 * CovidDao Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>Dec 16, 2022</pre>
 */
@SpringBootTest
@AutoConfigureMockMvc
public class CovidDaoTest {

    CovidDao dao = new CovidDao();

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: findByName(String countryName)
     */
    @Test
    public void testFindByName() throws Exception {
        List<Message> response = dao.findByName("Maroc");
        assertTrue(response.size() > 10);
    }

    /**
     * Method: findByName(String countryName)
     */
    @Test
    public void testFindByInvalidName() throws Exception {
        Exception exception = assertThrows(Exception.class, () -> {
            dao.findByName("essaouira");
        });
        String expectedMessage = "Pays inéxistant";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    /**
     * Method: findByNameAndDate(String countryName, String date)
     */
    @Test
    public void testFindByNameAndDate() throws Exception {
        MessageDTO messageDTO = dao.findByNameAndDate("maroc", "2022-12-10");
        assertEquals(messageDTO.getCountryName().toLowerCase(), "maroc");
        assertEquals(messageDTO.getDate(), "2022-12-10");
    }

    /**
     * Method: findByNameAndDate(String countryName, String date)
     */
    @Test
    public void testFindByNameAndInvalidDate() throws Exception {
        Exception exception = assertThrows(Exception.class, () -> {
            MessageDTO messageDTO = dao.findByNameAndDate("maroc", "20K2-D2-1F");
        });
        String expectedMessage = "Les données pour cette date sont inéxistants.";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }


    /**
     * Method: findAll()
     */
    @Test
    public void testFindAll() throws Exception {
        List<Message> messages = dao.findAll();
        assertTrue(messages.size() >= 50);
    }


} 
