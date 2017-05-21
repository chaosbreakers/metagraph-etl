/*
 *
 *   Copyright (C) 2015-2017 Monogram Inc.
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */

package io.metagraph.etl.reader.impl;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.metagraph.etl.common.Constant;
import io.metagraph.etl.reader.Reader;
import io.metagraph.etl.reader.config.ReaderConfig;
import io.metagraph.etl.reader.rule.EdgeLabelNamingRule;
import io.metagraph.etl.reader.rule.PropertyMappingRule;

/**
 * Created by eguoyix on 17/5/21.
 */
public class CSVReader implements Reader {

    private static final String CSV_SPLITTER = ",";

    private ReaderConfig readerConfig;

    private PropertyMappingRule propertyMappingRule;

    private EdgeLabelNamingRule edgeLabelNamingRule;

    private Map<Integer, String> csvHeader;


    public CSVReader(Map<Integer, String> csvHeader, ReaderConfig readerConfig) {
        this.csvHeader = csvHeader;
        this.readerConfig = readerConfig;
    }

    public Map<String, Object> read(ReaderConfig readerConfig, String record) {
        String[] recordFields = null;
        if (StringUtils.isNotBlank(record)) {
            recordFields = record.split(CSV_SPLITTER);
            if (recordFields.length != csvHeader.size()) {
                //TODO: yixi.guo define specific exception type
                throw new RuntimeException("csv file format or reader config error");
            }
        }
        Map<String, Object> vertex = constructVertex(csvHeader, recordFields, readerConfig);
        List<Map<String, Object>> edges = constructEdges(csvHeader, recordFields, readerConfig);

        Map<String, Object> result = new HashMap<>();
        //TODO: use class hash code as key?
        result.put(Constant.VERTEX_KEY, vertex);
        result.put(Constant.EDGES_KEY, edges);
        return result;
    }

    private List<Map<String, Object>> constructEdges(Map<Integer, String> csvHeader, String[] recordFields, ReaderConfig readerConfig) {
        List<Map<String, Object>> result = new ArrayList<>();
        List<String> outEdgeFields = readerConfig.getOutEdgeFields();
        for (String outEdgeField : outEdgeFields) {
            String fromBidField = readerConfig.getBidField();
            String label = edgeLabelNamingRule.getLabel(fromBidField, outEdgeField);
            Map<String, Object> edge = new HashMap<>();
            edge.put(Constant.LABEL_KEY, label);
            edge.put("fromBizId", "");
            edge.put("", "");
        }
        return null;
    }

    private Map<String, Object> constructVertex(Map<Integer, String> csvHeader, String[] recordFields, ReaderConfig readerConfig) {
        Map<String, Object> vertext = new HashMap<>();
        vertext.put(Constant.LABEL_KEY, readerConfig.getLabel());
        return null;
    }

    private String getBid(ReaderConfig readerConfig, String record) {
        return null;
    }

    private String getLabel(ReaderConfig readerConfig) {
        return null;
    }

    public ReaderConfig getReaderConfig() {
        return this.readerConfig;
    }


}
