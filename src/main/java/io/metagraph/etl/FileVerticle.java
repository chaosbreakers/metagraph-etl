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

package io.metagraph.etl;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.metagraph.etl.reader.Reader;
import io.metagraph.etl.reader.config.ReaderConfig;
import io.metagraph.etl.reader.impl.CSVReader;
import io.metagraph.etl.writer.Writer;
import io.metagraph.etl.writer.impl.MockWriter;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.file.AsyncFile;
import io.vertx.core.file.FileSystem;
import io.vertx.core.file.OpenOptions;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.core.parsetools.RecordParser;

/**
 * Created by eguoyix on 17/5/21.
 */
public class FileVerticle extends AbstractVerticle {

    ReaderConfig readerConfig = new ReaderConfig() {
        @Override
        public String getLabel() {
            return null;
        }

        @Override
        public String getBidField() {
            return null;
        }

        @Override
        public List<String> getOutEdgeFields() {
            return null;
        }
    };
    Map<Integer, String> csvHeader = new HashMap<>();
    private Logger logger = LoggerFactory.getLogger(Startup.class);
    private Reader reader = new CSVReader(csvHeader, readerConfig);

    private Writer writer = new MockWriter();

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new FileVerticle());
    }

    @Override
    public void start() throws Exception {
        super.start();
        FileSystem fs = vertx.fileSystem();
        RecordParser parser = RecordParser.newDelimited(System.getProperty("line.separator", "\n"),
                                                        record -> logger.info("record read from file: {0}", record.toString()));
        fs.open("names.txt", new OpenOptions(), ar -> {
            if (ar.succeeded()) {
                AsyncFile file = ar.result();
                file.handler(parser);
                file.endHandler(h -> {
                    parser.handle(Buffer.buffer(System.getProperty("line.separator", "\n")));// add this to read the last buffer
                    vertx.close();
                });
            } else {
                logger.error("can't find file");
            }
        });

    }

    private Object processRecord(String record) {
        Map<String, Object> vertexAndEdges = reader.read(readerConfig, record);
        return writer.write("", vertexAndEdges);
    }

    @Override
    public void stop() throws Exception {
        super.stop();
    }
}
