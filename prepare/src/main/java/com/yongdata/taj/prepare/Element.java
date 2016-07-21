package com.yongdata.taj.prepare;

public class Element {
    private String reqTime;
    private String method;
    private String path;
    private String remoteIp;
    private String host;
    private String xForwardFor;
    private String reqConnection;
    private String reqContentLength;
    private String userAgent;
    private String reqContentType;
    private String accept;
    private String referer;
    private String cookie;
    private String origin;
    private String acceptEncoding;
    private String acceptLanguage;
    private String upgradeInsecureRequests;
    private String dnt;
    private String ifModifiedSince;
    private String remoteIpAddress;
    private String remoteIpChainAddress;
    private String rspTime;
    private String processInterval;
    private String httpStatusCode;
    private String rspContentType;
    private String rspContentLength;
    private String date;
    private String rspConnection;
    private String cacheControl;
    private String setCookie;
    private String acceptCharset;
    private String pragma;
    private String expires;
    private String lastModified;
    private String location;

    public String getReqTime() {
        return reqTime;
    }

    public void setReqTime(String reqTime) {
        this.reqTime = reqTime;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getRemoteIp() {
        return remoteIp;
    }

    public void setRemoteIp(String remoteIp) {
        this.remoteIp = remoteIp;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getxForwardFor() {
        return xForwardFor;
    }

    public void setxForwardFor(String xForwardFor) {
        this.xForwardFor = xForwardFor;
    }

    public String getReqConnection() {
        return reqConnection;
    }

    public void setReqConnection(String reqConnection) {
        this.reqConnection = reqConnection;
    }

    public String getReqContentLength() {
        return reqContentLength;
    }

    public void setReqContentLength(String reqContentLength) {
        this.reqContentLength = reqContentLength;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public String getReqContentType() {
        return reqContentType;
    }

    public void setReqContentType(String reqContentType) {
        this.reqContentType = reqContentType;
    }

    public String getAccept() {
        return accept;
    }

    public void setAccept(String accept) {
        this.accept = accept;
    }

    public String getReferer() {
        return referer;
    }

    public void setReferer(String referer) {
        this.referer = referer;
    }

    public String getCookie() {
        return cookie;
    }

    public void setCookie(String cookie) {
        this.cookie = cookie;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getAcceptEncoding() {
        return acceptEncoding;
    }

    public void setAcceptEncoding(String acceptEncoding) {
        this.acceptEncoding = acceptEncoding;
    }

    public String getAcceptLanguage() {
        return acceptLanguage;
    }

    public void setAcceptLanguage(String acceptLanguage) {
        this.acceptLanguage = acceptLanguage;
    }

    public String getUpgradeInsecureRequests() {
        return upgradeInsecureRequests;
    }

    public void setUpgradeInsecureRequests(String upgradeInsecureRequests) {
        this.upgradeInsecureRequests = upgradeInsecureRequests;
    }

    public String getRspTime() {
        return rspTime;
    }

    public void setRspTime(String rspTime) {
        this.rspTime = rspTime;
    }

    public String getProcessInterval() {
        return processInterval;
    }

    public void setProcessInterval(String processInterval) {
        this.processInterval = processInterval;
    }

    public String getHttpStatusCode() {
        return httpStatusCode;
    }

    public void setHttpStatusCode(String httpStatusCode) {
        this.httpStatusCode = httpStatusCode;
    }

    public String getRspContentType() {
        return rspContentType;
    }

    public void setRspContentType(String rspContentType) {
        this.rspContentType = rspContentType;
    }

    public String getRspContentLength() {
        return rspContentLength;
    }

    public void setRspContentLength(String rspContentLength) {
        this.rspContentLength = rspContentLength;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getRspConnection() {
        return rspConnection;
    }

    public void setRspConnection(String rspConnection) {
        this.rspConnection = rspConnection;
    }

    public String getCacheControl() {
        return cacheControl;
    }

    public void setCacheControl(String cacheControl) {
        this.cacheControl = cacheControl;
    }

    public String getSetCookie() {
        return setCookie;
    }

    public void setSetCookie(String setCookie) {
        this.setCookie = setCookie;
    }

    public String getPragma() {
        return pragma;
    }

    public void setPragma(String pragma) {
        this.pragma = pragma;
    }

    public String getExpires() {
        return expires;
    }

    public void setExpires(String expires) {
        this.expires = expires;
    }

    public String getRemoteIpAddress() {
        return remoteIpAddress;
    }

    public void setRemoteIpAddress(String remoteIpAddress) {
        this.remoteIpAddress = remoteIpAddress;
    }

    public String getRemoteIpChainAddress() {
        return remoteIpChainAddress;
    }

    public void setRemoteIpChainAddress(String remoteIpChainAddress) {
        this.remoteIpChainAddress = remoteIpChainAddress;
    }

    public String getAcceptCharset() {
        return acceptCharset;
    }

    public void setAcceptCharset(String acceptCharset) {
        this.acceptCharset = acceptCharset;
    }

    public String getDnt() {
        return dnt;
    }

    public void setDnt(String dnt) {
        this.dnt = dnt;
    }

    public String getIfModifiedSince() {
        return ifModifiedSince;
    }

    public void setIfModifiedSince(String ifModifiedSince) {
        this.ifModifiedSince = ifModifiedSince;
    }

    public String getLastModified() {
        return lastModified;
    }

    public void setLastModified(String lastModified) {
        this.lastModified = lastModified;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return convert(reqTime) + ',' + convert(method) + ',' + convert(path) + ',' + convert(remoteIp) + ','
                + convert(host) + ',' + convert(xForwardFor) + ',' + convert(reqConnection) + ','
                + convert(reqContentLength) + ',' + convert(userAgent) + ',' + convert(reqContentType) + ','
                + convert(accept) + ',' + convert(referer) + ',' + convert(cookie) + ',' + convert(origin) + ','
                + convert(acceptEncoding) + ',' + convert(acceptLanguage) + ',' + convert(upgradeInsecureRequests)
                + ',' + convert(dnt) + ',' + convert(ifModifiedSince) + ',' + convert(remoteIpAddress) + ','
                + convert(remoteIpChainAddress) + ',' + convert(rspTime) + ',' + convert(processInterval) + ','
                + convert(httpStatusCode) + ',' + convert(rspContentType) + ',' + convert(rspContentLength) + ','
                + convert(date) + ',' + convert(rspConnection) + ',' + convert(cacheControl) + ',' + convert(setCookie)
                + ',' + convert(acceptCharset) + ',' + convert(pragma) + ',' + convert(expires) + ','
                + convert(lastModified) + ',' + convert(location) + '\n';
    }

    private String convert(String value) {
        if (value == null) {
            return "";
        }
        return value;
    }
}
