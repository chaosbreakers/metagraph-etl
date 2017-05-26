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

import io.metagraph.etl.reader.Reader;
import io.metagraph.etl.reader.impl.JDBCReader;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

/**
 * @author Ranger Tsao(https://github.com/boliza)
 */
public class JDBCVerticle extends AbstractVerticle {

    private Logger logger = LoggerFactory.getLogger(JDBCVerticle.class);

    private int pageNum = 0;

    @Override
    public void start(Future<Void> startFuture) throws Exception {
        //read page num from task config

        JDBCReader reader = Reader.createJDBCReader(vertx, config());
        //continuous
        if (reader.continuous()) {
            long timerId = vertx.setPeriodic(config().getJsonObject("etl").getJsonObject("schedule").getLong("internal"), event -> {
                //read data by page
            });
        }

        startFuture.complete();
    }

    @Override
    public void stop(Future<Void> stopFuture) throws Exception {
        super.stop(stopFuture);
    }
}

