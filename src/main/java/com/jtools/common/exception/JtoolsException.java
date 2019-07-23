// Copyright 2018 Baidu Inc. All rights reserved.
package com.jtools.common.exception;

/**
 * The JtoolsException.
 *
 * @Author Li Wenwei(liwenwei@baidu.com)
 */
public class JtoolsException extends RuntimeException  {

    private static final long serialVersionUID = 1L;

    public JtoolsException() {
        super();
    }

    public JtoolsException(String message) {
        super(message);
    }

    public JtoolsException(String message, Throwable cause) {
        super(message, cause);
    }

    public JtoolsException(Throwable cause) {
        super(cause);
    }

}