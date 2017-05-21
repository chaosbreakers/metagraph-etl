package io.metagraph.etl.reader.rule;

import java.util.Map;

/**
 * Created by eguoyix on 17/5/21.
 */
public interface PropertyMappingRule {

    /**
     * rule to mapping property key names from source to graph database property key names.
     * @param record
     * @return
     */
    Map<String,String> apply(Map<String,String> record);

}
