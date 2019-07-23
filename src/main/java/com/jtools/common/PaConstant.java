// Copyright 2012 Baidu Inc. All rights reserved.

package com.jtools.common;

import java.nio.charset.Charset;
import java.util.Date;

/**
 * Constants.
 *
 * @author Jun Yue(yuejun@baidu.com)
 */
public class PaConstant {

    public static final String UTF8_ENCODING = "UTF-8";
    public static final String GBK_ENCODING = "GBK";
    public static final String ISO08859_ENCODING = "ISO8859-1";

    public static final Charset UTF8_CHARSET = Charset.forName(UTF8_ENCODING);
    public static final Charset GBK_CHARSET = Charset.forName(GBK_ENCODING);

    public static final String HTTP_PROTOCOL = "http://";
    public static final String HTTPS_PROTOCOL = "https://";
    public static final String JMX_PROTOCOL = "service:jmx:jmxmp://";
    public static final String HTTP_SLASH = "/";

    public static final String SPLIT_OF_URL = ";";              // url分隔符
    public static final String SPLIT_OF_LIST = ",";           // list分隔符

    public static final int NO_FAILURE_CODE = 0;

    // Split for properties field.
    public static final String SPLIT_IN_PROPERTIES_FIELD = ";";

    // Md5 file configuration.
    public static final String SPLIT_OF_MD5_FILE = "  ";
    public static final String MD5_SUFFIX = ".md5";
    public static final String BACKUP_SUFFIX = ".bak";

    public static final String ZIP_POSTFIX = ".zip";
    public static final String CSV_POSTFIX = ".csv";

    // 1980-01-01 00:00:00
    public static final Date MIN_SQL_DATE = new Date(315504000000L);
    // 2037-01-01 00:00:00
    public static final Date MAX_SQL_DATE = new Date(2114352000000L);

    public static final long REDIS_PERSISTENT_TTL = -1;

    public static final String CSV_CONTENT_TYPE = "text/csv";
    public static final String RESPONSE_HEAD = "Content-Disposition";
    public static final String RESPONSE_FILE_NAME_FORMAT = "attachment; filename=\"%s\"";

    public static final char COMMA = ',';
    public static final char CHINESE_COMMA = '，';

}
