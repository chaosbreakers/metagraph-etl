package io.metagraph.etl.reader.rule.impl;

import com.google.common.collect.Maps;
import io.metagraph.etl.reader.rule.PropertyMappingRule;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

/**
 * Created by eguoyix on 17/5/21.
 */
public class ConfigPropertyMappingRule implements PropertyMappingRule{

    Map<String,String> keyNameMappings;

    public ConfigPropertyMappingRule(){
        //TODO : init keyNameMappings
    }

    @Override
    public Map<String, String> apply(Map<String, String> record) {
        if(keyNameMappings == null || keyNameMappings.isEmpty()){
            //no key name transform, return
            return record;
        }

        Map<String,String> transformedRecord = Maps.newHashMap(record);
        for(String filedKey : record.keySet()){
            String mappedKeyName = keyInMappings(filedKey);
            if(StringUtils.isNoneBlank(mappedKeyName)){
                transformedRecord.put(mappedKeyName,record.get(filedKey));
                transformedRecord.remove(filedKey);
            }
        }
        return transformedRecord;
    }

    private String keyInMappings(String originKey){
        if(keyNameMappings.keySet().contains(originKey)){
            return keyNameMappings.get(originKey);
        }else {
            return null;
        }
    }
}
