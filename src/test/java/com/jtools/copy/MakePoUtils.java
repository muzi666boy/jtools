// Copyright 2019 Baidu Inc. All rights reserved.
package com.jtools.copy;

import org.springframework.util.Assert;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * The MakePoUtils.
 *
 * @Author Li Wenwei(liwenwei@baidu.com)
 */
public class MakePoUtils {

    /**
     * 递归获取字段，包含父类
     * @param list
     * @param clazz
     * @return
     */
    private static List<Field> recursive(List<Field> list, Class<?> clazz) {
        if (clazz != null) {
            list.addAll(Arrays.asList(clazz.getDeclaredFields()));
            return recursive(list, clazz.getSuperclass());
        } else {
            return list;
        }
    }

    /**
     * 通用DTO组装新对象方法
     * 约定：
     * 1，dto和po中对应的属性名，类型，必须一致
     * 2，getter/setter方法自动生成驼峰结构
     * 当检测到传入的属性和po中属性匹配时，反射获取属性值
     *
     * @param dto 传入的DTO
     * @return 带值的po对象
     */
    public static <T, D> D makeFor(T dto, D po) {
        Assert.notNull(dto, "dto is null");
        Assert.notNull(po, "dto is null");
        List<Field> dtoFieldList = recursive(new ArrayList<>(), dto.getClass());
        List<Field> poFieldList = recursive(new ArrayList<>(), po.getClass());

        dtoFieldList.stream().filter(fd->!Modifier.isStatic(fd.getModifiers())).peek(p -> p.setAccessible(true)).forEach(fd -> {
            poFieldList.stream().filter(fc->!Modifier.isStatic(fc.getModifiers())).peek(p -> p.setAccessible(true)).forEach(fc -> {
                if (fd.getName().equals(fc.getName()) && fd.getType().equals(fc.getType())) {
                    try {
                        if (isBaseType(fd.get(dto))) {
                            fc.set(po, fd.get(dto));
                            return;
                        }
                        Object dest = fd.getType().newInstance();
                        makeFor(dest, fd.get(dto));
                        fc.set(po, dest);
                    } catch (Exception e) {
                        e.printStackTrace();
                        throw new RuntimeException("反射赋值异常" + fd.getType(), e);
                    }
                }
            });
        });
        return po;
    }

    public static boolean isBaseType(Object object) {
        if (object == null) {
            return true;
        }
        Class className = object.getClass();
        if (className.equals(java.lang.Integer.class) ||
                className.equals(java.lang.Byte.class) ||
                className.equals(java.lang.Long.class) ||
                className.equals(java.lang.Double.class) ||
                className.equals(java.lang.Float.class) ||
                className.equals(java.lang.Character.class) ||
                className.equals(java.lang.Short.class) ||
                className.equals(java.lang.Boolean.class) ||
                className.equals(java.lang.String.class)) {
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        Date d1 = new Date();
        SAPUser src = new SAPUser("zhangsan","111111",d1,d1);
        src.setRole(new SAPRole("admin"));
        src.getRole().setRolename("admin");
        SAPUser dest = new SAPUser();
        makeFor(src, dest);
        System.out.println(src);
        System.out.println(dest);
        src.getRole().setRolename("lalalal");
        System.out.println(src);
        System.out.println(dest);
    }
}
