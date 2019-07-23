// Copyright 2019 Baidu Inc. All rights reserved.
package com.jtools.protobuf;

import com.jtools.protobuf.PersonProto.PhoneType;
import org.javers.common.collections.Lists;
import org.junit.Test;

import com.google.protobuf.InvalidProtocolBufferException;

/**
 * The PbTest.
 *
 * @Author Li Wenwei(liwenwei@baidu.com)
 */
public class PbTest {

    @Test
    public void testPb() throws InvalidProtocolBufferException {
        PersonProto.Person.Builder builder = PersonProto.Person.newBuilder();
        builder.setEmail("xiaobai@baidu.com");
        builder.setName("xiaobai");
        builder.setPhoneType(PhoneType.HOME);
        builder.setId(123);
        builder.addPhones(PersonProto.PhoneNumber.newBuilder().setNumber("123456"));
        System.out.println(builder.build());
        System.out.println(Lists.asList(builder.build().toByteArray().length));

        PersonProto.Person person = PersonProto.Person.parseFrom(builder.build().toByteArray());

        System.out.println(person);
    }
}
