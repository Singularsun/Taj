package com.yongdata.taj.prepare;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class PrepareData {

    private static BufferedWriter requestWriter;
    private static BufferedWriter responseWriter;

    public static void main(String[] args) throws IOException {
        File log = new File(PrepareData.class.getResource("/").getPath() + "../../../data/marble.log");
        File request = new File(PrepareData.class.getResource("/").getPath() + "../../../data/request.csv");
        File response = new File(PrepareData.class.getResource("/").getPath() + "../../../data/response.csv");
        if (!log.exists()) {
            System.err.println("File marble.log does not exist.");
            return;
        }
        FileReader fileReader = new FileReader(log);
        BufferedReader reader = new BufferedReader(fileReader);
        FileWriter requestFileWriter = new FileWriter(request);
        requestWriter = new BufferedWriter(requestFileWriter);
        FileWriter responseFileWriter = new FileWriter(response);
        responseWriter = new BufferedWriter(responseFileWriter);
        requestWriter.write("time,request.id,method,path,remote.ip,host,x.forwarded.for,connection,content.length,user.agent,content.type,accept,referer,cookie,origin,accept.encoding,accept.language,upgrade.insecure.requests\n");
        responseWriter.write("time,request.id,remote.ip.address,remote.ip.chain.address,process.interval,http.status.code,x.marble.request.id,content.type,content.length,data,connection,cache.control,set.cookie,pragma,expires\n");

        String temp = reader.readLine();
        Map<String, String> map = new HashMap<String, String>();
        try {
            while (temp != null) {
                if (temp.equals(".")) {
                    convertData(map);
                    map = new HashMap<String, String>();
                } else {
                    if (temp.equals("")) {
                        return;
                    }
                    if (temp.length() < 11
                            || !temp.substring(1, 11).matches("^(\\d{4})-(\\d{2})-(\\d{2})$")) {
                        String[] pair = temp.split(":");
                        map.put(pair[0], temp.substring(temp.indexOf(":") + 1));
                    } else {
                        map.put("time", temp.substring(1, 25));
                        int index = temp.indexOf("RequestId");
                        map.put("requestId", temp.substring(index + 10, index + 46));
                        if (temp.contains("Starts processing")) {
                            int index1 = temp.indexOf("Starts processing");
                            String[] pair = temp.substring(index1 + 27).split(" ");
                            map.put("method", pair[0]);
                            map.put("path", pair[1]);
                        } else if (temp.contains("Remote IP Address")) {
                            int index2 = temp.indexOf("Remote IP Address");
                            map.put("remote-ip-address", temp.substring(index2 + 18, temp.length() - 1));
                        } else if (temp.contains("Remote IP Chain Address")) {
                            int index3 = temp.indexOf("Remote IP Chain Address");
                            map.put("remote-ip-chain-address", temp.substring(index3 + 24, temp.length() - 1));
                        }
                    }
                }
                temp = reader.readLine();
            }
        } finally {
            requestWriter.flush();
            requestFileWriter.close();
            requestFileWriter.close();
            responseWriter.flush();
            responseWriter.close();
            responseFileWriter.close();
            reader.close();
            fileReader.close();
        }
    }

    private static void convertData(Map<String, String> document) throws IOException {
        if (document.containsKey("method")) {
            StringBuffer buffer = new StringBuffer();
            buffer.append(escape(document.get("time")));
            buffer.append("," + escape(document.get("requestId")));
            buffer.append("," + escape(document.get("method")));
            buffer.append("," + escape(document.get("path")));
            buffer.append("," + escape(document.get("remoteip")));
            buffer.append("," + escape(document.get("host")));
            buffer.append("," + escape(document.get("x-forwarded-for")));
            buffer.append("," + escape(document.get("connection")));
            buffer.append("," + escape(document.get("content-length")));
            buffer.append("," + escape(document.get("user-agent")));
            buffer.append("," + escape(document.get("content-type")));
            buffer.append("," + escape(document.get("accept")));
            buffer.append("," + escape(document.get("referer")));
            buffer.append("," + escape(document.get("cookie")));
            buffer.append("," + escape(document.get("origin")));
            buffer.append("," + escape(document.get("accept-encoding")));
            buffer.append("," + escape(document.get("accept-language")));
            buffer.append("," + escape(document.get("upgrade-insecure-requests")));
            buffer.append("\n");
            requestWriter.write(buffer.toString());
        } else {
            StringBuffer buffer = new StringBuffer();
            buffer.append(escape(document.get("time")));
            buffer.append("," + escape(document.get("requestId")));
            buffer.append("," + escape(document.get("remote-ip-address")));
            buffer.append("," + escape(document.get("remote-ip-chain-address")));
            buffer.append("," + escape(document.get("Process interval")));
            buffer.append("," + escape(document.get("HTTP Status Code")));
            buffer.append("," + escape(document.get("x-marble-request-id")));
            buffer.append("," + escape(document.get("Content-Type")));
            buffer.append("," + escape(document.get("Content-Length")));
            buffer.append("," + escape(document.get("Date")));
            buffer.append("," + escape(document.get("Connection")));
            buffer.append("," + escape(document.get("Cache-Control")));
            buffer.append("," + escape(document.get("Set-Cookie")));
            buffer.append("," + escape(document.get("Pragma")));
            buffer.append("," + escape(document.get("Expires")));
            buffer.append("\n");
            responseWriter.write(buffer.toString());
        }
    }

    private static String escape(String src) {
        if (src == null) {
            return "";
        }
        return src.replace(",", "%2C");
    }
}
