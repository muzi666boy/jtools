// Copyright 2016 Baidu Inc. All rights reserved.

package com.jtools.common.freemarker;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

import com.jtools.common.Md5Utils;
import com.jtools.common.PaConstant;
import com.jtools.common.freemarker.ext.DateParamMethodModel;
import com.jtools.common.freemarker.ext.MathParamMethodModel;
import com.jtools.common.freemarker.ext.UrlDecodeMethodModel;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import org.apache.commons.lang.time.DateUtils;


/**
 * Freemarker helper for xunke.
 *
 * @author Li Wenwei(liwenwei@baidu.com)
 */
public class FreeMarkerHelper {

    private static final String DATE_FORMAT = "yyyy-MM-dd";
    private static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    private static Configuration cfg;
    private static MathParamMethodModel mathParamMethodModel;
    private static DateParamMethodModel dateParamMethodModel;
    private static UrlDecodeMethodModel urlDecodeMethodModel;
    private static StringTemplateLoader stringTemplateLoader;

    static {
        cfg = new Configuration(Configuration.VERSION_2_3_23);
        cfg.setDefaultEncoding(PaConstant.UTF8_ENCODING);
        cfg.setNumberFormat("#");
        cfg.setDateFormat(DATE_FORMAT);
        cfg.setDateTimeFormat(DATE_TIME_FORMAT);
        cfg.setClassicCompatible(true);
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        cfg.setObjectWrapper(new DefaultObjectWrapper(Configuration.VERSION_2_3_23));
        stringTemplateLoader = new StringTemplateLoader();
        cfg.setTemplateLoader(stringTemplateLoader);
        mathParamMethodModel = new MathParamMethodModel();
        dateParamMethodModel = new DateParamMethodModel();
        urlDecodeMethodModel = new UrlDecodeMethodModel();

    }

    public static String process(Map<String, Object> model, String templateContent) throws IOException,
            TemplateException {
        // TODO : likaihua need to wrapper exception.
        String templateName = Md5Utils.md5(templateContent);
        Object foundTemplateSource = stringTemplateLoader.findTemplateSource(templateName);
        if (foundTemplateSource == null) {
            synchronized (FreeMarkerHelper.class) {
                foundTemplateSource = stringTemplateLoader.findTemplateSource(templateName);
                if (foundTemplateSource == null) {
                    stringTemplateLoader.putTemplate(templateName, templateContent);
                }
            }
        }

        Template template = cfg.getTemplate(templateName);
        // Add template mothod.
        model.put("math", mathParamMethodModel);
        model.put("dateParam", dateParamMethodModel);
        model.put("urlDecode", urlDecodeMethodModel);
        model.put("now", new Date());
        model.put("tomorrow", DateUtils.addDays(new Date(), 1));
        model.put("thirdDay", DateUtils.addDays(new Date(), 2));
        model.put("forthDay", DateUtils.addDays(new Date(), 3));
        model.put("fifthDay", DateUtils.addDays(new Date(), 4));
        model.put("sixthDay", DateUtils.addDays(new Date(), 5));
        model.put("seventhDay", DateUtils.addDays(new Date(), 6));
        model.put("eighthDay", DateUtils.addDays(new Date(), 7));
        model.put("ninthDay", DateUtils.addDays(new Date(), 8));
        model.put("tenthDay", DateUtils.addDays(new Date(), 9));
        model.put("eleventhDay", DateUtils.addDays(new Date(), 10));
        model.put("twelfthDay", DateUtils.addDays(new Date(), 11));
        model.put("thirteenthDay", DateUtils.addDays(new Date(), 12));
        model.put("fourteenthDay", DateUtils.addDays(new Date(), 13));
        model.put("fifteenthDay", DateUtils.addDays(new Date(), 14));
        model.put("uuid", UUID.randomUUID());

        Writer writer = new StringWriter();
        String content = null;
        try {
            template.process(model, writer);
            content = writer.toString();
        } finally {
            writer.close();
        }
        return content;
    }

}
