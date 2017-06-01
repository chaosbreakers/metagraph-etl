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

import io.metagraph.etl.reader.Reader;
import io.metagraph.etl.reader.config.ReaderConfig;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.core.streams.ReadStream;
import io.vertx.ext.jdbc.JDBCClient;
import io.vertx.ext.sql.SQLRowStream;

/**
 * @author Ranger Tsao(https://github.com/boliza)
 */
public class JDBCReader implements Reader {

    private Vertx vertx;

    private JDBCClient jdbcClient;
    private ReaderConfig readerConfig;

    private SQLRowStream delegate;

    public JDBCReader(Vertx vertx, JsonObject config) {
        this.vertx = vertx;
        jdbcClient = JDBCClient.createShared(vertx, config);
        vertx.executeBlocking(h -> {
                                  //open connection and query by stream
                              },
                              r -> {
                                  //TODO SQLRowStream
                              });
    }

    @Override
    public ReaderConfig getReaderConfig() {
        return readerConfig;
    }

    @Override
    public JsonObject nextRow() {
        return null;
    }

    @Override
    public void close(Handler<AsyncResult<Void>> completionHandler) {

    }

    @Override
    public ReadStream<JsonObject> exceptionHandler(Handler<Throwable> handler) {
        return null;
    }

    @Override
    public ReadStream<JsonObject> handler(Handler<JsonObject> handler) {
        return null;
    }

    @Override
    public ReadStream<JsonObject> pause() {
        return null;
    }

    @Override
    public ReadStream<JsonObject> resume() {
        return null;
    }

    @Override
    public ReadStream<JsonObject> endHandler(Handler<Void> endHandler) {
        return null;
    }
}
