/**
 * Copyright (c) 2012-2019 Nikita Koksharov
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.zed.infrastructure.protocol;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public class JacksonJsonSupport implements JsonSupport {

    protected final ObjectMapper objectMapper = new ObjectMapper();


    public JacksonJsonSupport() {
        this(new Module[]{});
    }

    public JacksonJsonSupport(Module... modules) {
        if (modules != null && modules.length > 0) {
            objectMapper.registerModules(modules);
        }
        init(objectMapper);
    }

    protected void init(ObjectMapper objectMapper) {
        SimpleModule module = new SimpleModule();
        objectMapper.registerModule(module);
        objectMapper.setSerializationInclusion(Include.NON_NULL);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(SerializationFeature.WRITE_BIGDECIMAL_AS_PLAIN, true);
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    }


    @Override
    public <T> T readValue(ByteBufInputStream src, Class<T> valueType) throws IOException {
        return objectMapper.readValue((InputStream) src, valueType);
    }

    @Override
    public <T> T readValue(ByteBuf src, Class<T> valueType) throws IOException {
        return readValue(new ByteBufInputStream(src), valueType);
    }

    @Override
    public void writeValue(ByteBufOutputStream out, Object value) throws IOException {
        objectMapper.writeValue((OutputStream) out, value);
    }

}
