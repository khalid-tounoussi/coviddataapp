package com.coviddata.util;

import com.coviddata.model.Message;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * The type Api reader utility.
 */
public class ApiReaderUtility {

    /**
     * Save csv.
     */
    public static void saveCSV() {
        URL website = null;
        String FileName = "data.csv";
        File f = new File(FileName);
        if( !f.exists() ) {
            try {
                website = new URL("https://coronavirus.politologue.com/data/coronavirus/coronacsv.aspx?format=csv&t=pays");
                ReadableByteChannel rbc = null;
                rbc = Channels.newChannel(website.openStream());
                FileOutputStream fos = null;
                fos = new FileOutputStream(FileName);
                fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }

    /**
     * Read csv list.
     *
     * @return the list
     */
    public static List<Message> readCSV() {
        List<Message> data = new ArrayList<Message>();
        Reader in = null;
        try {
            in = new FileReader("data.csv");
            Iterable<CSVRecord> records = CSVFormat.RFC4180.builder().build().parse(in);
            for (CSVRecord record : records) {
                if (record.getRecordNumber() > 8) {
                    data.add(Message.fromCSVRecord(record));
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return data;
    }
}
