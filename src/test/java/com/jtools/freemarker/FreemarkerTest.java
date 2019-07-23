// Copyright 2017 Baidu Inc. All rights reserved.
package com.jtools.freemarker;

import java.io.IOException;
import java.util.Map;

import com.jtools.common.freemarker.FreeMarkerHelper;
import freemarker.template.TemplateException;
import org.junit.Test;

import com.google.common.collect.Maps;

/**
 * The FreemarkerTest.
 *
 * @Author Li Wenwei(liwenwei@baidu.com)
 */
public class FreemarkerTest {
    @Test
    public void testReplaceRegx() throws IOException, TemplateException {
        String from =
                "http://redirect.simba.taobao.com/rd?c=un&w=unionsem&k=b1d73646918f3371&p=mm_26632258_3504122_32554087"
                        + "&b=2wxEWtJ1XBixPSQ1bGV&s=%E7%94%9F%E6%97%A5%E6%B4%BE%E5%AF%B9%E8%BF%9E%E8%A1%A3%E8%A3%99"
                        + "&f=https%3A%2F%2Fuland.taobao.com%2Fsemm%2Ftbsearch%3Frefpid%3Dmm_26632258_3504122_32554087"
                        + "%26keyword%3D%25E7%2594%259F%25E6%2597%25A5%25E6%25B4%25BE%25E5%25AF%25B9%25E8%25BF"
                        + "%259E%25E8%25A1%25A3%25E8%25A3%2599";
        Map<String, Object> model = Maps.newHashMap();
        model.put("from", from);
        System.out.println(FreeMarkerHelper.process(model, "${from?replace(\"(_\\\\d*_\\\\d*_)\\\\d*\",\"$1131880909\",\"r\")}"));
    }
//http://redirect.simba.taobao.com/rd?c=un&w=unionsem&k=b1d73646918f3371&p=mm_26632258_3504122_131880901&b=baidu_feeds_{{IDEA_ID}}&f=https%3A%2F%2Fuland.taobao.com%2Fsemm%2Ftbsearch%3Frefpid%3Dmm_26632258_3504122_131880901%26keyword%3D%E6%99%BA%E8%83%BD%E6%9C%BA%E5%99%A8%E4%BA%BA+%E5%B7%B4%E5%B7%B4%E8%85%BE
    @Test
    public void testReplaceRegx2() throws IOException, TemplateException {
        String from =
                "http://redirect.simba.taobao.com/rd?c=un&w=unionsem&k=b1d73646918f3371&p=mm_26632258_3504122_131880901&b=baidu_feeds_{{IDEA_ID}}&f=https%3A%2F%2Fuland.taobao.com%2Fsemm%2Ftbsearch%3Frefpid%3Dmm_26632258_3504122_131880901%26keyword%3D%E6%99%BA%E8%83%BD%E6%9C%BA%E5%99%A8%E4%BA%BA+%E5%B7%B4%E5%B7%B4%E8%85%BE";
        Map<String, Object> model = Maps.newHashMap();
        model.put("from", from);
        System.out.println(FreeMarkerHelper.process(model, "${from?replace(\"(\\\\S+)keyword([^\\\\+]+)\\\\+(\\\\S+)\",\"$1keyword$2\",\"r\")}"));
    }
}
