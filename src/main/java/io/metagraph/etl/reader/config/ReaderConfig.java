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

package io.metagraph.etl.reader.config;

import java.util.List;

import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;

/**
 * @author Ranger Tsao(https://github.com/boliza)
 */
public interface ReaderConfig {

    static ReaderConfig readFromJson(JsonObject jsonObject) {
        return null;
    }

    static ReaderConfig readFromJdbc(Vertx vertx, JsonObject jsonObject) {
        return null;
    }

    /**
     * get label for the file to read, all record in a file have the same label
     *
     * @return label
     */
    String getLabel();

    /**
     * get bid field
     *
     * @return bid key
     */
    String getBidField();

    /**
     * get out edge filed
     *
     * @return
     */
    List<String> getOutEdgeFields();

}
