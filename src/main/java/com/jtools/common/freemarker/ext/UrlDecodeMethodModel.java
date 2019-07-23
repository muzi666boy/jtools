// Copyright 2017 Baidu Inc. All rights reserved.
package com.jtools.common.freemarker.ext;

import java.net.URLDecoder;
import java.util.List;

import com.jtools.common.LogMessageBuilder;
import com.jtools.common.PaConstant;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The UrlDecodeMethodModel.
 *
 * @Author Li Wenwei(liwenwei@baidu.com)
 */
public class UrlDecodeMethodModel implements TemplateMethodModelEx {
    private Logger logger = LoggerFactory.getLogger(UrlDecodeMethodModel.class);

    @Override
    public Object exec(List args) throws TemplateModelException {
        if (args.size() < 1) {
            throw new TemplateModelException("Wrong arguments");
        }
        String url = args.get(0).toString();
        String encode = PaConstant.UTF8_ENCODING;
        if (args.size() == 2) {
            encode = args.get(1).toString();
        }
        try {
            return URLDecoder.decode(url, encode);
        } catch (Exception e) {
            logger.error(new LogMessageBuilder("Url decode failed")
                    .withParameter("url", url)
                    .withParameter("encode", encode)
                    .build(), e);
            return url;
        }
    }
}
