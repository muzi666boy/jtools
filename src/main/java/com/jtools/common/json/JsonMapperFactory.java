// Copyright 2014 Baidu Inc. All rights reserved.

package com.jtools.common.json;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonView;
import org.codehaus.jackson.map.type.CollectionType;
import org.codehaus.jackson.map.type.MapType;
import org.codehaus.jackson.type.JavaType;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

/**
 * @author Yue Jun (yuejun@baidu.com)
 */
public class JsonMapperFactory {

    private static final ObjectMapper DEFAULT_JSON_MAPPER = new ObjectMapper();

    private static final ObjectMapper CREATIVE_JSON_MAPPER = new ObjectMapper();

    static {
        DEFAULT_JSON_MAPPER.setVisibilityChecker(
                DEFAULT_JSON_MAPPER.getVisibilityChecker().withFieldVisibility(
                        JsonAutoDetect.Visibility.ANY).withGetterVisibility(
                        JsonAutoDetect.Visibility.NONE).withIsGetterVisibility(
                        JsonAutoDetect.Visibility.NONE));

        DEFAULT_JSON_MAPPER
                .configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .configure(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS, false)
                .setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);

        CREATIVE_JSON_MAPPER.configure(SerializationConfig.Feature.DEFAULT_VIEW_INCLUSION, false)
                .configure(SerializationConfig.Feature.AUTO_DETECT_FIELDS, false)
                .configure(SerializationConfig.Feature.AUTO_DETECT_GETTERS, false)
                .configure(SerializationConfig.Feature.AUTO_DETECT_IS_GETTERS, false)
                .setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL)
                .writerWithView(JsonView.class);
    }

    /**
     * Get default json object mapper.
     */
    public static ObjectMapper getDefaultMapper() {
        return DEFAULT_JSON_MAPPER;
    }

    /**
     * Get json object mapper which processes creatives.
     */
    public static ObjectMapper getCreativeMapper() {
        return CREATIVE_JSON_MAPPER;
    }

    public static CollectionType getListType(Class<?> clazz) {
        return DEFAULT_JSON_MAPPER.getTypeFactory().constructCollectionType(
                List.class, clazz);
    }

    public static MapType getMapType(Class<?> keyClass, Class<?> valueClass) {
        return DEFAULT_JSON_MAPPER.getTypeFactory().constructMapType(HashMap.class,
                keyClass, valueClass);
    }

    public static JavaType getType(Type type) {
        return DEFAULT_JSON_MAPPER.getTypeFactory().constructType(type);
    }
}
