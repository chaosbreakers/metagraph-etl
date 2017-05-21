package io.metagraph.etl.reader.rule;

/**
 * Created by eguoyix on 17/5/21.
 */
public interface EdgeLabelNamingRule {


    String getLabel(String vertexFromFieldName,String vertexToFieldName);
}
