package io.metagraph.etl.reader.config;

import java.util.List;

/**
 * Created by eguoyix on 17/5/21.
 */
public interface ReaderConfig {

    /**
     * get label for the file to read, all record in a file have the same label
     * @return label
     */
    String getLabel();

    /**
     * get bid field
     * @return bid key
     */
    String getBidField();

    /**
     * get out edge filed
     * @return
     */
    List<String> getOutEdgeFields();

}
