# Fastjson + MySQL 不出网利用测试环境

这是一个用于测试 Fastjson + MySQL 条件下不出网利用的测试环境。该环境演示了在 Fastjson 1.2.83 开启 autotype 的情况下(对于老版本 Fastjson 不开启 autotype 的情况也能利用类似的手法)，通过 MySQL JDBC 的 NamedPipe 机制实现不出网 RCE 的利用方式。

## 相关文章

详细的技术分析和利用原理请参考：

**Fastjson1.2.83(开autotype)+MySQL不出网利用**  
https://fushuling.com/index.php/2025/12/04/fastjson1-2-83%e5%bc%80autotypemysql%e4%b8%8d%e5%87%ba%e7%bd%91%e5%88%a9%e7%94%a8/

## 快速开始

### 启动环境

使用 docker-compose 启动测试环境：

```bash
docker-compose up
```

启动后访问：http://127.0.0.1:8080

### 利用方式

`exp.py` 是利用脚本，默认利用的是 [java-chains](https://github.com/vulhub/java-chains) 上的 OneForAllEcho 内存马（利用的是 `rce.txt`）。

通过请求头 `X-Authorization` 可以指定执行的命令，例如：

```bash
python exp.py
```

脚本会自动：
1. 将 `rce.txt` 中的恶意数据通过 socket 发送到目标服务器
2. 通过爆破 `/proc/self/fd/{fd}` 的方式找到缓存的恶意文件
3. 构造 Fastjson payload 触发反序列化
4. 执行内存马，通过 `X-Authorization` 请求头传递命令

## 项目结构

```
fastjson_mysql/
├── docker-compose.yml          # Docker Compose 配置文件
├── Dockerfile                  # Docker 镜像构建文件
├── pom.xml                     # Maven 项目配置文件
├── exp.py                      # 利用脚本（使用 OneForAllEcho 内存马）
├── rce.txt                     # OneForAllEcho 内存马的恶意 pipe 文件
├── calc_5.txt                  # MySQL 5 版本的恶意 pipe 文件（用于弹出计算器）
├── calc_6.txt                  # MySQL 6 版本的恶意 pipe 文件（用于弹出计算器）
├── calc_8.txt                  # MySQL 8 版本的恶意 pipe 文件（用于弹出计算器）
├── src/
│   └── main/
│       └── java/
│           └── com/
│               └── suctf/
│                   ├── Application.java                    # Spring Boot 主应用类
│                   ├── Fastjson_mysql_calc_5.java         # MySQL 5 条件下 Fastjson 不出网利用示例代码（需要 calc_5.txt）
│                   ├── Fastjon_mysql_calc_6.java          # MySQL 6 条件下 Fastjson 不出网利用示例代码（需要 calc_6.txt）
│                   ├── Fastjson_mysql_calc_8.java         # MySQL 8 条件下 Fastjson 不出网利用示例代码（需要 calc_8.txt）
│                   ├── Mysql_calc.java                    # MySQL 相关测试代码
│                   └── controller/
│                       ├── IndexController.java           # 首页控制器
│                       └── JsonController.java            # JSON 解析控制器（/json/parse 接口）
└── README.md                   # 本文件
```

## 文件说明

### 利用脚本

- **exp.py**: 自动化利用脚本，使用 OneForAllEcho 内存马实现 RCE
  - 通过 `X-Authorization` 请求头传递要执行的命令
  - 使用 `rce.txt` 作为恶意 pipe 文件

### 恶意 Pipe 文件

- **rce.txt**: OneForAllEcho 内存马的恶意 pipe 文件，用于 `exp.py` 脚本
- **calc_5.txt**: MySQL 5 版本的恶意 pipe 文件，用于弹出计算器演示
- **calc_6.txt**: MySQL 6 版本的恶意 pipe 文件，用于弹出计算器演示
- **calc_8.txt**: MySQL 8 版本的恶意 pipe 文件，用于弹出计算器演示

### 示例代码

- **Fastjson_mysql_calc_5.java**: MySQL 5 条件下 Fastjson 不出网利用的示例代码
  - 需要配合 `calc_5.txt` 使用
  - 演示如何通过 Fastjson 反序列化触发 MySQL JDBC 的 NamedPipe 机制

- **Fastjon_mysql_calc_6.java**: MySQL 6 条件下 Fastjson 不出网利用的示例代码
  - 需要配合 `calc_6.txt` 使用
  - 使用 `com.mysql.cj.jdbc.ha.LoadBalancedMySQLConnection` 类

- **Fastjson_mysql_calc_8.java**: MySQL 8 条件下 Fastjson 不出网利用的示例代码
  - 需要配合 `calc_8.txt` 使用

### 应用代码

- **Application.java**: Spring Boot 应用主类
- **JsonController.java**: 提供 `/json/parse` 接口，用于接收和解析 JSON 数据
- **IndexController.java**: 首页控制器

## 技术原理

该利用方式的核心在于：

1. **Fastjson autotype 开启**: 当 Fastjson 开启 autotype 时，会走黑名单机制，可以使用一些未进入黑名单的类
2. **MySQL JDBC NamedPipe**: 通过 `NamedPipeSocketFactory` 可以指定一个本地文件作为数据源，而不需要真实的网络连接
3. **文件描述符爆破**: 利用 Linux 的 `/proc/self/fd/{fd}` 机制，通过爆破文件描述符找到缓存的恶意文件
4. **反序列化链**: 恶意 pipe 文件中包含反序列化 payload，触发后可以实现 RCE

## 注意事项

- 该环境仅用于安全研究和测试目的
- 请勿在生产环境中使用
- 确保在隔离的网络环境中运行

## 致谢

- [java-chains](https://github.com/vulhub/java-chains) - 提供恶意 pipe 文件生成功能
- [jdbc-tricks](https://github.com/yulate/jdbc-tricks) - JDBC 利用技巧总结
