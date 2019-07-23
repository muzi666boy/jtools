// Copyright 2015 Baidu Inc. All rights reserved.

package com.jtools.common;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.common.base.Preconditions;

/**
 * The util tools for net.
 *
 * @author Li Wenwei(liwenwei@baidu.com)
 */
public class NetworkUtils {

    private static final Log log = LogFactory.getLog(NetworkUtils.class);

    /**
     * Get remote adr.
     */
    public static String getRemoteAdr(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
            // 多次反向代理后会有多个ip值，第一个ip才是真实ip
            int index = ip.indexOf(",");
            if (index != -1) {
                return ip.substring(0, index);
            } else {
                return ip;
            }
        }
        ip = request.getHeader("X-Real-IP");
        if (StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
            return ip;
        }
        String xssl = request.getHeader("x-ssl-header");
        if (xssl != null && xssl.equals("1")) {
            return request.getHeader("CLIENTIP");
        }
        return request.getRemoteAddr();
    }

    /**
     * Get one IP address, not 127.0.0.1.
     */
    public static String getIpAddress() {
        try {
            Enumeration<NetworkInterface> allNetInterfaces = NetworkInterface
                    .getNetworkInterfaces();
            InetAddress ip = null;
            while (allNetInterfaces.hasMoreElements()) {
                NetworkInterface netInterface = allNetInterfaces.nextElement();
                Enumeration<InetAddress> addresses = netInterface.getInetAddresses();
                while (addresses.hasMoreElements()) {
                    ip = addresses.nextElement();
                    if (ip != null && ip instanceof Inet4Address && !ip.isLoopbackAddress()) {
                        return ip.getHostAddress();
                    }
                }
            }
        } catch (Exception e) {
            log.error("Error while getting IP address.", e);
        }
        return null;
    }

    /**
     * Parse socket address.
     *
     * @param address The socket address, like "127.0.9.2:9090".
     * @return The {@link InetSocketAddress} object.
     */
    public static InetSocketAddress parseSocketAddress(String address) {
        Preconditions.checkNotNull(address);

        String[] items = address.split(":");
        Preconditions.checkState(items.length == 2);

        String host = items[0];
        int port = Integer.parseInt(items[1]);
        return new InetSocketAddress(host, port);
    }
}
