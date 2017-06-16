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
import io.vertx.core.buffer.Buffer;
import io.vertx.core.file.AsyncFile;
import io.vertx.core.file.OpenOptions;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.core.parsetools.RecordParser;
import io.vertx.core.streams.ReadStream;

/**
 * @author Ranger Tsao(https://github.com/boliza)
 */
public abstract class FileReader implements Reader {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    private AsyncFile file;
    private transient JsonObject currentRow;
    /**
     * file config
     */
    protected JsonObject config;

    public FileReader(Vertx vertx, JsonObject config) {
        this.config = config;
        String filePath = config.getString("file_path");
        vertx.fileSystem().open(filePath, new OpenOptions(), result -> {
            file = result.result();
            file.handler(parser());
        });
        vertx.executeBlocking(h -> {
                              },
                              r -> {
                              });
    }

    public abstract RecordParser parser();

    public abstract Function<Buffer, JsonObject> transformer();

    @Override
    public ReaderConfig getReaderConfig() {
        return null;
    }

    @Override
    public JsonObject nextRow() {
        return currentRow;
    }

    @Override
    public void close(Handler<AsyncResult<Void>> completionHandler) {
        file.close(completionHandler);
    }

    @Override
    public ReadStream<JsonObject> exceptionHandler(Handler<Throwable> handler) {
        file.exceptionHandler(handler);
        return this;
    }

    @Override
    public ReadStream<JsonObject> handler(Handler<JsonObject> handler) {
        file.handler(event -> {
            currentRow = transformer().apply(event);
            handler.handle(currentRow);
        });
        return this;
    }

    @Override
    public ReadStream<JsonObject> pause() {
        file.pause();
        return this;
    }

    @Override
    public ReadStream<JsonObject> resume() {
        file.resume();
        return this;
    }

    @Override
    public ReadStream<JsonObject> endHandler(Handler<Void> endHandler) {
        file.endHandler(endHandler);
        return this;
    }
}
