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

import java.util.function.Function;

import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.core.parsetools.RecordParser;

/**
 * @author Ranger Tsao(https://github.com/boliza)
 */
public class CSVReader extends FileReader {

    public CSVReader(Vertx vertx, JsonObject config) {
        super(vertx, config);
    }

    @Override
    public RecordParser parser() {
        return RecordParser.newDelimited(System.getProperty("line.separator", "\n"),
                                         buffer -> {
                                             logger.info("string of this line is: {0}", buffer.toString());
                                             transformer().apply(buffer);
                                         });
    }

    @Override
    public Function<Buffer, JsonObject> transformer() {
        String vertexLable = config.getString("vertex_label");
        JsonObject rule = config.getJsonObject("rule");
        String bidColumn = rule.getString("bid_column");
        JsonArray edges = rule.getJsonArray("edges");
        return new Function<Buffer, JsonObject>() {
            @Override
            public JsonObject apply(Buffer buffer) {
                String bufferStr = buffer.toString();
                JsonObject result = new JsonObject();
                return result;
            }
        };
    }
}
