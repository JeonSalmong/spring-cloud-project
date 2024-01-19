
## log처리
### Flow
서비스단 log 발생 -> 로그 서버로 전달 ----> 로그 서버에서 로그 받아서 처리
- error 로그는 GlobalContext.java에서 처리 (현재 해당 부분 주석 처리 되어 있음)


외부설정에 따라 Log bean 주입 여부 결정

ActionLog interface로 해서 저장 구현체 구분 DB or 메시징