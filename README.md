# SchoolTalk

- 학교의 선생님과 학생, 학부모의 소통을 이어주는 채팅 서비스 입니다.
- 모놀리식 구조로 개발 이후 채팅 서비스 분리하여 MSA 구조로 변경을 고려해 분리하기 쉬운 구조로 개발하려 합니다.


## 주요 기능
 
* 실시간 채팅: WebSocket + Redis Pub/Sub + MariaDB
  * 학생 ↔ 선생님, 학부모 ↔ 선생님의 1:1 채팅
  * 반별 채팅: 학급 단위 채팅방 관리
* 공지/알림 기능: WebSocket 활용
  * 선생님이 학급 또는 학부모에게 공지 전송
* 사용자 인증 및 권한 관리: Spring Security + JWT + Redis Cache
  * 선생님만 채팅방과 공지를 설정할 수 있다.


## 기술 스택

* Spring Boot
* Java 17
* MariaDB
* JPA
* Maven
* Redis
* AWS
* Docker
* Jenkins
* intellij IDEA


## DB ERD
[ERD 링크](https://www.erdcloud.com/d/twGddwaPqr6JZH5XP)
![image](https://github.com/user-attachments/assets/a8e33331-a53c-4df5-9f81-a7ed8885b813)

## 구조
![image](https://github.com/user-attachments/assets/d5b4ba4c-c3e9-4594-bfe9-00ce6441a5b3)
