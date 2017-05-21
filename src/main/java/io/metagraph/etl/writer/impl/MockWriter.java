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

package io.metagraph.etl.writer.impl;

import java.util.Map;

import io.metagraph.etl.writer.Writer;

/**
 * Created by eguoyix on 17/5/21.
 */
public class MockWriter implements Writer {
    @Override
    public Object write(String traversal, Map<String, Object> variables) {
        return null;
    }
}
