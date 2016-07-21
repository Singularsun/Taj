package com.yongdata.taj.prepare;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

public class PrepareData {
    private static Map<String, Element> map = new HashMap<String, Element>();
    private static Queue<String> queue = new ArrayBlockingQueue<String>(1000);

    private static BufferedWriter writer;

    public static void main(String[] args) throws IOException {
        File log = new File(PrepareData.class.getResource("/").getPath() + "../../../data/marble.log");
        File data = new File(PrepareData.class.getResource("/").getPath() + "../../../data/src_data.csv");
        if (!log.exists()) {
            System.err.println("File marble.log does not exist.");
            return;
        }
        FileInputStream fileInputStream = new FileInputStream(log);
        StringBuilder buffer = new StringBuilder();
        byte[] bytes = new byte[4096];
        int length = 0;
        try {
            while (length != -1) {
                buffer.append(new String(bytes, 0, length));
                length = fileInputStream.read(bytes);
            }
        } finally {
            fileInputStream.close();
        }
        FileWriter fileWriter = new FileWriter(data);
        writer = new BufferedWriter(fileWriter);
        writer.write("request.id,req.time,method,path,remote.ip,host,x.forwarded.for,req.connection,req.content.length," +
                             "user.agent,req.content.type,accept,referer,cookie,origin,accept.encoding,accept.language," +
                             "upgrade.insecure.requests,dnt,if.modified.since,remote.ip.address,remote.ip.chain.address," +
                             "rsp.time,process.interval,http.status.code,rsp.content.type,rsp.content.length,date," +
                             "rsp.connection,cache.control,set.cookie,accept.charset,pragma,expires,last.modified,location\n");

        String[] lines = buffer.toString().split("\n");
        StringBuffer document = new StringBuffer();
        try {
            for (String line : lines) {
                if (line == null) {
                    continue;
                }
                if (line.length() >= 11 && line.substring(1, 11).matches("^(\\d{4})-(\\d{2})-(\\d{2})$")) {
                    addDocument(document.toString());
                    document = new StringBuffer();
                }
                document.append(line + "\n");
            }
            for (Map.Entry<String, Element> entry : map.entrySet()) {
                writer.write(entry.getKey() + ',' + entry.getValue().toString());
            }
        } finally {
            writer.flush();
            writer.close();
            fileWriter.close();
        }
    }

    private static void addDocument(String document) throws IOException {
        if (document.length() == 0) {
            return;
        }
        int location = document.indexOf("RequestId");
        if (location == -1) {
            return;
        }
        String requestId = document.substring(location + 10, location + 46);
        Element element;
        if (queue.size() > 800) {
            for (int i = 0; i < 100; i++) {
                String oldRequest = queue.poll();
                writer.write(oldRequest + ',' + map.get(oldRequest).toString());
                map.remove(oldRequest);
            }
        }

        if (map.containsKey(requestId)) {
            element = map.get(requestId);
        } else {
            element = new Element();
            queue.offer(requestId);
        }
        if (document.contains("Remote IP Address")) {
            int index = document.indexOf("Remote IP Address");
            element.setRemoteIpAddress(escapeValue(document.substring(index + 18, document.length() - 2)));
        } else if (document.contains("Remote IP Chain Address")) {
            int index2 = document.indexOf("Remote IP Chain Address");
            element.setRemoteIpChainAddress(escapeValue(document.substring(index2 + 24, document.length() - 2)));
        } else if (document.contains("Starts processing")) {
            element.setReqTime(escapeValue(document.substring(1, 25)));
            int index3 = document.indexOf("Starts processing");
            int index4 = document.indexOf("\n");
            String[] url = document.substring(index3 + 27, index4).split(" ");
            element.setMethod(url[0]);
            element.setPath(url[1]);
            String[] pairs = document.split("\n");
            for (int i = 1; i < pairs.length; i++) {
                String[] pair = pairs[i].split(":");
                if (pair.length < 2) {
                    continue;
                }
                String key = escapeKey(pair[0]);
                String value = escapeValue(pairs[i].substring(key.length() + 1));
                try {
                    switch (Enum.valueOf(Key.class, key)) {
                        case remoteip:
                            element.setRemoteIp(value);
                            break;
                        case host:
                            element.setHost(value);
                            break;
                        case x_forwarded_for:
                            element.setxForwardFor(value);
                            break;
                        case connection:
                            element.setReqConnection(value);
                            break;
                        case content_length:
                            element.setReqContentLength(value);
                            break;
                        case user_agent:
                            element.setUserAgent(value);
                            break;
                        case content_type:
                            element.setReqContentType(value);
                            break;
                        case accept:
                            element.setAccept(value);
                            break;
                        case referer:
                            element.setReferer(value);
                            break;
                        case cookie:
                            element.setCookie(value);
                            break;
                        case origin:
                            element.setOrigin(value);
                            break;
                        case accept_encoding:
                            element.setAcceptEncoding(value);
                            break;
                        case accept_language:
                            element.setAcceptLanguage(value);
                            break;
                        case upgrade_insecure_requests:
                            element.setUpgradeInsecureRequests(value);
                            break;
                        case dnt:
                            element.setDnt(value);
                            break;
                        case if_modified_since:
                            element.setIfModifiedSince(value);
                            break;
                    }
                } catch (IllegalArgumentException exception) {
                    //ignore
                    // System.out.println(requestId + " : " + exception.getMessage());
                }
            }
        } else if (document.contains("Ends processing")) {
            element.setRspTime(escapeValue(document.substring(1, 25)));
            String[] pairs = document.split("\n");
            for (int i = 1; i < pairs.length; i++) {
                String[] pair = pairs[i].split(":");
                if (pair.length < 2) {
                    continue;
                }
                String key = escapeKey(pair[0]);
                String value = escapeValue(pairs[i].substring(key.length() + 1));
                try {
                    switch (Enum.valueOf(Key.class, key)) {
                        case Process_interval:
                            element.setProcessInterval(value);
                            break;
                        case HTTP_Status_Code:
                            element.setHttpStatusCode(value);
                            break;
                        case Content_Type:
                            element.setRspContentType(value);
                            break;
                        case Content_Length:
                            element.setRspContentLength(value);
                            break;
                        case x_marble_request_id:
                            break;
                        case Date:
                            element.setDate(value);
                            break;
                        case Connection:
                            element.setRspConnection(value);
                            break;
                        case Cache_Control:
                            element.setCacheControl(value);
                            break;
                        case cache_control:
                            element.setCacheControl(value);
                            break;
                        case Set_Cookie:
                            element.setSetCookie(value);
                            break;
                        case Pragma:
                            element.setPragma(value);
                            break;
                        case pragma:
                            element.setPragma(value);
                            break;
                        case accept_charset:
                            element.setAcceptCharset(value);
                            break;
                        case Expires:
                            element.setExpires(value);
                            break;
                        case Last_Modified:
                            element.setLastModified(value);
                            break;
                        case Location:
                            element.setLocation(value);
                    }
                } catch (IllegalArgumentException exception) {
                    // ignored
                    // System.out.println(requestId + " : " + exception.getMessage());
                }
            }
        } else {
            return;
        }
        map.put(requestId, element);
    }

    private static String escapeKey(String key) {
        if (key == null) {
            return "";
        }
        return key.replace(" ", "_").replace("-", "_");
    }

    private static String escapeValue(String value) {
        if (value == null) {
            return "";
        }
        return value.replace(",", "%2C");
    }
}
