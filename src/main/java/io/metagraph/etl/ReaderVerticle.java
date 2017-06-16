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

import io.metagraph.etl.common.Constant;
import io.metagraph.etl.reader.Reader;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

/**
 * @author Ranger Tsao(https://github.com/boliza)
 */
public class ReaderVerticle extends AbstractVerticle {

    private Logger logger = LoggerFactory.getLogger(WriterVerticle.class);
    private Reader reader;

    @Override
    public void start() throws Exception {
        JsonObject config = config();
        JsonArray files = config.getJsonArray("files");
        //init reader,one reader for each file
        for(Object file:files.getList()){
            reader = Reader.createReader(vertx, (JsonObject) file);
            assert reader != null;
            reader.handler(row -> vertx.eventBus().send(Constant.EVENTBUS_ETL_RECEIVER, row));
            reader.endHandler(end -> vertx.close());
        }
    }

    @Override
    public void stop() throws Exception {
        reader.close(event -> {
            if (event.succeeded()) {
                logger.info("stop Reader[{0}] succeed", reader);
                vertx.close();
            } else {
                logger.error("can't stop Writer[{0}] because of {1}", reader, event.cause());
            }
        });
    }
}
