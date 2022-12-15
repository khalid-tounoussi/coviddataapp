package com.coviddata.coviddata.controller;

import com.coviddata.controller.CovidController;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * CovidController Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>Dec 15, 2022</pre>
 */
//@WebMvcTest(CovidController.class)
@WebMvcTest(CovidController.class)
public class CovidControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    protected String mapToJson(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }

    protected <T> T mapFromJson(String json, Class<T> clazz)
            throws JsonParseException, JsonMappingException, IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(json, clazz);
    }

    /**
     * Method: getDataCountryByName(@RequestParam String countryName)
     */
    @Test
    public void testGetDataCountryByName() throws Exception {
//        mockMvc.perform(get("/api/oneCountryData?countryName=maroc"))
//                .andExpect(status().isOk());
        mockMvc.perform(get("/api/oneCountryData?countryName=maroc"))
                .andExpect(status().isNotFound())
                .andDo(print());

//        String uri = "/api/oneCountryData?countryName=maroc";
//
//        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(uri)
//                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
//        int status = mvcResult.getResponse().getStatus();
//
//        assertEquals(200, status);
//        String content = mvcResult.getResponse().getContentAsString();
//        System.out.println(content);

//        Message[] productlist = mapFromJson(content, Message[].class);
//        assertTrue(productlist.length > 0);
    }

    /**
     * Method: getDataCountryByNameAndDate(@RequestParam String countryName, @RequestParam String date)
     */
    @Test
    public void testGetDataCountryByNameAndDate() throws Exception {
        String uri = "/api/oneCountryDataWithDate?countryName=maroc&date=2022-12-12";

        MvcResult mvcResult = mockMvc.perform(get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();


        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        System.out.println("STATUS  : " + status);
        String content = mvcResult.getResponse().getContentAsString();
        System.out.println("CONTENT " + content);
//        Message[] productlist = mapFromJson(content, Message[].class);
//        assertTrue(productlist.length > 0);
    }

    /**
     * Method: getTodayCountryData(@RequestParam String countryName)
     */
    @Test
    public void testGetTodayCountryData() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: readCsvFile()
     */
    @Test
    public void testReadCsvFile() throws Exception {
//TODO: Test goes here... 
    }


} 
