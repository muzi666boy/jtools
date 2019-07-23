// Copyright 2016 Baidu Inc. All rights reserved.

package com.jtools.common.freemarker.ext;

import java.util.List;

import freemarker.template.TemplateMethodModel;
import freemarker.template.TemplateModelException;

/**
 * Math model.
 *
 * @author Li Wenwei(liwenwei@baidu.com)
 */
public class MathParamMethodModel implements TemplateMethodModel {

    @Override
    public Object exec(@SuppressWarnings("rawtypes") List args) throws TemplateModelException {
        if (args.size() < 3) {
            throw new TemplateModelException("Wrong arguments");
        }

        String methodName = (String) args.get(0);
        Double param1 = Double.parseDouble((String) args.get(1));
        Double param2 = Double.parseDouble((String) args.get(2));
        MethodName mt = MethodName.parse(methodName);
        if (mt == null) {
            throw new TemplateModelException("Wrong arguments");
        }
        switch (mt) {
            case POW:
                return Math.pow(param1, param2);
            default:
                break;
        }
        return 0;
    }

    public enum MethodName {
        POW;

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
