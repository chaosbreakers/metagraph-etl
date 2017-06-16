package io.metagraph.etl.common.entity;

import java.util.List;
import java.util.Map;

/**
 * Created by eguoyix on 17/6/16.
 */
public class Vertex {

    private String bid;

    private String label;

    /**
     * outEdgeLable -> outEdgeBid
     */
    private Map<String,String> outEdges;

    /**
     * properties for this vertex
     */
    private Map<String,String> properties;

    public String getBid() {
        return bid;
    }

    public void setBid(String bid) {
        this.bid = bid;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Map<String, String> getOutEdges() {
        return outEdges;
    }

    public void setOutEdges(Map<String, String> outEdges) {
        this.outEdges = outEdges;
    }

    public Map<String, String> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, String> properties) {
        this.properties = properties;
    }
}
