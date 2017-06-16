package io.metagraph.etl.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by eguoyix on 17/6/16.
 */
public class CSVUtil {

    public static final String SPLITTER = ",";

    public static Map<String,String> convertRecordLineToMap(String csvHeader,String recordLine){
        Map<String,String> result = new HashMap<String,String>();
        String[] headers = csvHeader.split(SPLITTER);
        String[] fieldValues = recordLine.split(SPLITTER);
        assert headers.length == fieldValues.length;
        for(int index =0;index< headers.length;index++){
            result.put(headers[index],fieldValues[index]);
        }
        return result;
    }
}
