package io.metagraph.etl.writer.impl;

import io.metagraph.etl.writer.Writer;

import java.util.Map;

/**
 * Created by eguoyix on 17/5/21.
 */
public class MockWriter implements Writer{
    @Override
    public Object write(String traversal, Map<String, Object> variables) {
        return null;
    }
}
