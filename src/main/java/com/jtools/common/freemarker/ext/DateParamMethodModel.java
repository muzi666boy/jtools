// Copyright 2016 Baidu Inc. All rights reserved.

package com.jtools.common.freemarker.ext;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import freemarker.template.TemplateMethodModel;
import freemarker.template.TemplateModelException;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;

/**
 * Date model.
 *
 * @author Li Wenwei(liwenwei@baidu.com)
 */
public class DateParamMethodModel implements TemplateMethodModel {
    private static final long DATE_MILLS = 1000 * 60 * 60 * 24;
    private static final String DATE_FORMAT = "yyyy/MM/dd";
    private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public Object exec(@SuppressWarnings("rawtypes") List args) throws TemplateModelException {
        if (args.size() < 1) {
            throw new TemplateModelException("Wrong arguments");
        }

        String methodName = (String) args.get(0);
        MethodName mt = MethodName.parse(methodName);
        if (mt == null) {
            throw new TemplateModelException("Wrong arguments");
        }
        switch (mt) {
            case DATE_OFFSET:
                return dateOffset(args);
            case ADD_YEARS:
                return addYears(args);
            case ADD_DAYS:
                return addDays(args);
            default:
                break;
        }
        return StringUtils.EMPTY;
    }

    private long dateOffset(List args) throws TemplateModelException {
        if (args.size() < 2) {
            throw new TemplateModelException("Wrong arguments");
        }
        String dateStr = (String) args.get(1);
        Date date;
        try {
            date = DateUtils.parseDate(dateStr, new String[] { DATE_FORMAT });
        } catch (ParseException e) {
            return 0;
        }

        return (date.getTime() - new Date().getTime()) / DATE_MILLS;
    }

    private String addYears(List args) throws TemplateModelException {
        if (args.size() < 2) {
            throw new TemplateModelException("Wrong arguments");
        }
        Integer years = Integer.parseInt((String) args.get(1));
        SimpleDateFormat format = SIMPLE_DATE_FORMAT;
        if (args.size() > 2) {
            format = new SimpleDateFormat((String) args.get(2));
        }
        return format.format(DateUtils.addYears(new Date(), years));
    }

    private String addDays(List args) throws TemplateModelException {
        if (args.size() < 2) {
            throw new TemplateModelException("Wrong arguments");
        }
        Integer days = Integer.parseInt((String) args.get(1));
        SimpleDateFormat format = SIMPLE_DATE_FORMAT;
        if (args.size() > 2) {
            format = new SimpleDateFormat((String) args.get(2));
        }
        return format.format(DateUtils.addDays(new Date(), days));
    }

    public enum MethodName {
        DATE_OFFSET,
        ADD_YEARS,
        ADD_DAYS;

        public static MethodName parse(String methodName) {
            for (MethodName mn : MethodName.values()) {
                if (mn.name().equalsIgnoreCase(methodName)) {
                    return mn;
                }
            }
            return null;
        }
    }
}
