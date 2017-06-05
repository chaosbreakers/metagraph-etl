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

import io.metagraph.etl.reader.Reader;
import io.metagraph.etl.reader.config.ReaderConfig;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonArray;
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

    private SQLRowStream delegate;//remove this delegate

    private ReaderConfig readerConfig;
    private Function<JsonArray, JsonObject> transform;//init by reader config

    private transient JsonObject currentRow;

    private Handler<Throwable> exceptionHandler;
    private Handler<JsonObject> handler;
    private Handler<Void> endHandler;
    private Handler<Void> rsClosedHandler;

    public JDBCReader(Vertx vertx, JsonObject config) {
        this.vertx = vertx;
        readerConfig = ReaderConfig.readFromJson(config.getJsonObject("etl"));
        transform = objects -> null;//init transform using config
        jdbcClient = JDBCClient.createShared(vertx, config.getJsonObject("jdbc"));
    }

    @Override
    public ReaderConfig getReaderConfig() {
        return readerConfig;
    }

    @Override
    public JsonObject nextRow() {
        return currentRow;
    }

    @Override
    public void close(Handler<AsyncResult<Void>> completionHandler) {
        delegate.close(completionHandler);
    }

    @Override
    public ReadStream<JsonObject> exceptionHandler(Handler<Throwable> handler) {
        delegate.exceptionHandler(handler);
        return this;
    }

    @Override
    public final ReadStream<JsonObject> handler(Handler<JsonObject> handler) {
        delegate.handler(event -> {
            currentRow = transform.apply(event);
            handler.handle(currentRow);
        });
        return this;
    }

    @Override
    public ReadStream<JsonObject> pause() {
        delegate.pause();
        return this;
    }

    @Override
    public ReadStream<JsonObject> resume() {
        delegate.resume();
        return this;
    }

    @Override
    public ReadStream<JsonObject> endHandler(Handler<Void> endHandler) {
        delegate.endHandler(endHandler);
        return this;
    }
}
