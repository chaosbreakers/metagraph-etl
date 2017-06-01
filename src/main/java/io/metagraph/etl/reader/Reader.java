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

package io.metagraph.etl.reader;

import io.metagraph.etl.reader.config.ReaderConfig;
import io.vertx.core.Closeable;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.core.streams.ReadStream;

/**
 * @author Ranger Tsao(https://github.com/boliza)
 */
public interface Reader extends ReadStream<JsonObject>, Closeable {

    static Reader createReader(Vertx vertx, JsonObject config) {
        return null;
    }

    /**
     * file verticle can get file path from this config to read.
     *
     * @return
     */
    ReaderConfig getReaderConfig();

    /**
     * etl data continuous
     *
     * @return boolean
     */
    default boolean continuous() {
        return false;
    }
}
