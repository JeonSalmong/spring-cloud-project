# Spring Cloud Project

## 1. Discovery Service (Spring Eureka)
### http://127.0.0.1:8761/
## 2. ConfigService (Spring Config) -- optional
### http://127.0.0.1:8888/
## 3. ApiGateway (Spring Gateway)
### http://127.0.0.1:8000/
- filter : 토큰 유무, 유효성 체크
- exception : 커스텀 response
- actuator refresh : yml 파일 갱신

## 4. Sample Service
### 4.1 User-Service (port : random)
- filter : 토큰 유무 체크
- interceptor : 토큰 유효성 체크
- exception : 커스텀 response
- actuator refresh : yml 파일 갱신
- JWT 토큰 처리 (접속 Device, Auth 정보 추가)
- feign-client : Order-Service 호출

### 4.2 Order-Service (port : random)
- 동기화 이슈 : 인스턴스 2개 실행 시 별도 저장되는 현상
- 해결방법
1. 단일 DB 사용 (h2 -> mariaDB)
2. Kafka 이용 데이터 동기화
3. 위 두가지 방법 혼합 

### 4.3 Catalog-Service (port : random)

## 5. Kafka for 데이터동기화 (https://clack2933.tistory.com/20)
### Windows 로컬 환경 kafka Test
1. Zookeeper 서버 띄우기 (New CMD) http://127.0.0.1:2181/  
C:\Study\springCloud\Kafka>
```
.\bin\windows\zookeeper-server-start.bat config\zookeeper.properties
```
2. Kafka 서버 띄우기 (New CMD)  http://127.0.0.1:9092/  
C:\Study\springCloud\Kafka>
```
.\bin\windows\kafka-server-start.bat config\server.properties
```
3. Kafka 토픽 생성하기 (New CMD)
C:\Study\springCloud\Kafka\bin\windows>
.\kafka-topics.bat --create --topic [topic name] --bootstrap-server [host]:[port] --partitions 1
```
.\kafka-toics.bat --create --topic quickstart-events --bootstrap-server localhost:9092 --partitions 1
```
3-1. Topic 목록 확인
```
.\kafka-topics.bat --bootstrap-server localhost:9092 --list
```
3-2 Topic 정보 확인
```
.\kafka-topics.bat --describe --topic quickstart-events --bootstrap-server localhost:9092
```
4.Producer로 Topic에 메시지 전달하기
```
.\kafka-console-producer.bat --broker-list localhost:9092 --topic quickstart-events
```
5. 생성한 Topic Consumer로 구독해 데이터 받아오기 (new cmd)
C:\Study\springCloud\Kafka\bin\windows>
```
.\kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic quickstart-events --from-beginning
```
* cmd 하나 더 실행해서 consumer 두개 모두 메시지 받는지 확인

### Kafka Connect
1. maraidb 설치 (https://mariadb.org/)  http://127.0.0.1:3306/  
2. 초기화  
관리자권한 cmd 실행 > C:\Study\springCloud\mariadb-10.5.23-winx64 이동  
```
.\bin\mariadb-install-db.exe --datadir=C:\Study\springCloud\mariadb-10.5.23-winx64\data --service=mariaDB --port=3306 --password=test1234  
```
(윈도우 서비스에서 mariaDB 서비스 시작 해 줘야 함)  
cmd 에서 입력>> 
```
mysql -uroot -p  
```
3. 테이블 생성
```
create database mydb;  
use mydb;  
create table users ( id int auto_increment primary key, user_id varchar(20) not null, pwd varchar(20) not null, name varchar(20) not null, create_at datetime default NOW());  
```
4. kafka connect 설치
cmd에서 실행 >> 
```
curl -O https://packages.confluent.io/archive/6.1/confluent-6.1.0.tar.gz
```
5. JDBC connector 다운로드 및 설정
https://www.confluent.io/hub/confluentinc/kafka-connect-jdbc  
5.1 \etc\kafka\connect-distributed.properties 수정 (기존 주석처리하고 마지막에 추가)  
```
plugin.path=\C:\\Study\\springCloud\\Kafka-Connect\\confluentinc-kafka-connect-jdbc-10.7.4\\lib  
```
6. maria db jar 파일 복사 (mariadb-java-client-2.7.2.jar)
from : C:\Users\DCCIS067000\.gradle\caches\modules-2\files-2.1\org.mariadb.jdbc\mariadb-java-client\2.7.2\138fb9b8caee700bf4793bc42947113a1dbc8532  
to   : C:\Study\springCloud\Kafka-Connect\confluent-6.1.0\share\java\kafka  
7. connect 실행 (Zookeeper, Kafka 서버 실행되어 있어야 함)  http://127.0.0.1:8083/  
C:\Study\springCloud\Kafka-Connect\confluent-6.1.0>  
```
.\bin\windows\connect-distributed.bat .\etc\kafka\connect-distributed.properties  
```
(실행시 Classpath is empty...오류나면 \bin\windows\kafka-run-class.bat 수정)  
8. source connect 추가 
postman으로 실행 >> post : http://localhost:8083/connectors  
body 내용  
```
{  
    "name" : "my-source-connect",  
    "config" : {  
        "connector.class" : "io.confluent.connect.jdbc.JdbcSourceConnector",  
        "connection.url" : "jdbc:mysql://localhost:3306/mydb",  
        "connection.user" : "root",  
        "connection.password" : "test1234",  
        "mode" : "incrementing", 
        "incrementing.column.name" : "id", 
        "table.whitelist" : "users",    
        "topic.prefix" : "my_topic_", 
        "tasks.max" : "1" 
    }    
}  
```
참조 : https://velog.io/@anjinwoong/Kafka-Connect-%EC%9E%90%EC%A3%BC-%EC%82%AC%EC%9A%A9%ED%95%98%EB%8A%94-%EB%AA%85%EB%A0%B9%EC%96%B4API-%EC%A0%95%EB%A6%AC  
9. sink connect 추가 
```
{  
    "name" : "my-sink-connect",  
    "config" : {  
        "connector.class" : "io.confluent.connect.jdbc.JdbcSinkConnector",  
        "connection.url" : "jdbc:mysql://localhost:3306/mydb",  
        "connection.user" : "root",  
        "connection.password" : "test1234",  
        "auto.create" : "true",  
        "auto.evolve" : "true",  
        "delete.enabled" : "false",  
        "tasks.max" : "1", 
        "topics" : "my_topic_users"   
    }  
}
```
topics 이름과 동일한 테이블 자동 생성 됨    

## To-Do
1. Kafka 이용 데이터 동기화 적용
2. mariaDB + Kafka 적용
3. Resilience4J_Trace
4. Monitoring
5. Docker
6. Deployment