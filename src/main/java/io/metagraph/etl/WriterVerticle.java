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
import io.metagraph.etl.writer.Writer;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Handler;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

/**
 * @author Ranger Tsao(https://github.com/boliza)
 */
public class WriterVerticle extends AbstractVerticle {

    private Logger logger = LoggerFactory.getLogger(WriterVerticle.class);
    private Writer writer;

    @Override
    public void start() throws Exception {
        // init writer
        logger.info("init graph writer using config[{0}]", config());
        writer = Writer.createWriter(vertx, config());

        //consumer message writer to graph
        vertx.eventBus().consumer(Constant.EVENTBUS_ETL_RECEIVER, (Handler<Message<JsonObject>>) handle -> {
            assert writer != null;
            writer.write(handle.body().getString("traversal"), handle.body().getJsonObject("variables").getMap());
        });
    }

    @Override
    public void stop() throws Exception {
        writer.close(event -> {
            if (event.succeeded()) {
                logger.info("stop Writer[{0}] succeed");
                vertx.close();
            } else {
                logger.error("can't stop Writer[{0}] because of {1}", writer, event.cause());
            }
        });
    }
}
