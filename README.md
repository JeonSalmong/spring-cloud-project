# Spring Cloud Project

## 1. Discovery Service (Spring Eureka)

## 2. ConfigService (Spring Config) -- optional

## 3. ApiGateway (Spring Gateway)
- filter : 토큰 유무, 유효성 체크
- exception : 커스텀 response
- actuator refresh : yml 파일 갱신

## 4. Sample Service
### 4.1 User-Service
- filter : 토큰 유무 체크
- interceptor : 토큰 유효성 체크
- exception : 커스텀 response
- actuator refresh : yml 파일 갱신
- JWT 토큰 처리 (접속 Device, Auth 정보 추가)
- feign-client : Order-Service 호출

### 4.2 Order-Service
- 동기화 이슈 : 인스턴스 2개 실행 시 별도 저장되는 현상
- 해결방법
1. 단일 DB 사용 (h2 -> mariaDB)
2. Kafka 이용 데이터 동기화
3. 위 두가지 방법 혼합 

### 4.3 Catalog-Service
