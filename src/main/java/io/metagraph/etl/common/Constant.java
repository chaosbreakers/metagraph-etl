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

package io.metagraph.etl.common;

/**
 * Created by eguoyix on 17/5/21.
 */
public final class Constant {

    public static final String LABEL_KEY = "label";

    public static final String BID_KEY = "bid";

    public static final String VERTEX_KEY = "vertex_key";

    public static final String EDGES_KEY = "edges_key";

    //event bus address for message
    public static final String EVENTBUS_ETL_RECEIVER = "io.metagraph.etl:receiver";
    public static final String EVENTBUS_ETL_METRICS = "io.metagraph.etl:metrics";

}
