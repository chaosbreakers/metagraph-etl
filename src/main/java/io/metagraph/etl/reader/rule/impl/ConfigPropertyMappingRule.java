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

package io.metagraph.etl.reader.rule.impl;


import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

import io.metagraph.etl.reader.rule.PropertyMappingRule;

/**
 * Created by eguoyix on 17/5/21.
 */
public class ConfigPropertyMappingRule implements PropertyMappingRule {

    Map<String, String> keyNameMappings;

    public ConfigPropertyMappingRule() {
        //TODO : init keyNameMappings
    }

    @Override
    public Map<String, String> apply(Map<String, String> record) {
        if (keyNameMappings == null || keyNameMappings.isEmpty()) {
            //no key name transform, return
            return record;
        }

        Map<String, String> transformedRecord = new HashMap(record);
        for (String filedKey : record.keySet()) {
            String mappedKeyName = keyInMappings(filedKey);
            if (StringUtils.isNoneBlank(mappedKeyName)) {
                transformedRecord.put(mappedKeyName, record.get(filedKey));
                transformedRecord.remove(filedKey);
            }
        }
        return transformedRecord;
    }

    private String keyInMappings(String originKey) {
        if (keyNameMappings.keySet().contains(originKey)) {
            return keyNameMappings.get(originKey);
        } else {
            return null;
        }
    }
}
