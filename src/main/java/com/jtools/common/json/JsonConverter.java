// Copyright 2013 Baidu Inc. All rights reserved.

package com.jtools.common.json;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.JsonNode;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

/**
 * Class for simple json convert.
 *
 * @author Shen Dayu (shendayu@baidu.com)
 * @author Bao Dayong(baodayong@baidu.com)
 */
public class JsonConverter {

    private static final Log log = LogFactory.getLog(JsonConverter.class);

    public static <T> String serialize(T obj) {
        try {
            return JsonMapperFactory.getDefaultMapper().writeValueAsString(obj);
        } catch (IOException e) {
            log.error("Serialize json failed.", e);
            return null;
        }
    }

    public static <T> byte[] serialize(T obj, String encoding) {
        try {
            String json = JsonMapperFactory.getDefaultMapper().writeValueAsString(obj);
            return json.getBytes(encoding);
        } catch (IOException e) {
            log.error("Serialize json failed.", e);
            return null;
        }
    }

    public static <T> T deserialize(String json, Class<T> clazz) {
        try {
            return JsonMapperFactory.getDefaultMapper().readValue(json, clazz);
        } catch (IOException e) {
            log.error("Deserialize json failed.", e);
            return null;
        }
    }

    public static <T> T deserialize(byte[] bytes, Class<T> clazz, String encoding) {
        try {
            return JsonMapperFactory.getDefaultMapper().readValue(
                    new String(bytes, encoding), clazz);
        } catch (IOException e) {
            log.error("Deserialize json failed.", e);
            return null;
        }
    }

    public static Object deserialize(String json, Type type) {
        try {
            return JsonMapperFactory.getDefaultMapper().readValue(json,
                    JsonMapperFactory.getType(type));
        } catch (IOException e) {
            log.error("Deserialize json failed.", e);
            return null;
        }
    }

    public static <T> List<T> deserializeList(String json, Class<T> elementClazz) {
        try {
            return JsonMapperFactory.getDefaultMapper().readValue(json,
                    JsonMapperFactory.getListType(elementClazz));
        } catch (IOException e) {
            log.error("Deserialize json failed.", e);
            return null;
        }
    }

    public static <T, E> Map<T, E> deserializeMap(String json, Class<T> key, Class<E> value) {
        try {
            return JsonMapperFactory.getDefaultMapper().readValue(json,
                    JsonMapperFactory.getMapType(key, value));
        } catch (IOException e) {
            log.error("Deserialize json failed.", e);
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    public static <T, E> T deserializeGenerics(String json, Class<T> type, Class<E> parameterType) {
        try {
            return (T) JsonMapperFactory.getDefaultMapper().readValue(json,
                    JsonMapperFactory.getDefaultMapper().getTypeFactory()
                            .constructParametricType(type, parameterType));
        } catch (IOException e) {
            log.error("Deserialize json failed.", e);
            return null;
        }
    }

    public static int extractInt(String json, String key) throws IOException {
        JsonNode jsonNode = JsonMapperFactory.getDefaultMapper().readTree(json);
        return jsonNode.get(key).asInt();
    }

}
