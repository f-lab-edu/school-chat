# SchoolTalk

- 학교의 선생님과 학생, 학부모의 소통을 이어주는 채팅 서비스 입니다.
- 모놀리식 구조로 개발 이후 채팅 서비스 분리하여 MSA 구조로 변경을 고려해 분리하기 쉬운 구조로 개발하려 합니다.

## 주요 기능

* 그룹 채팅
    * 특정 기준으로 개설된 채팅방(예. 반별, 학년별, 동아리)
    * 선생님과 학생들이 1:N으로 참여
    * 선생님이 채팅방을 개설하며, 학생들이 참여
    * 부가 기능: 금칙어, 채팅 도배 제한
* 1:1 채팅
    * 선생님과 학생
        * 선생님과 학생이 1:1로 참여
        * 진로 상담 등을 진행
    * 선생님과 학부모
        * 선생님과 학부모가 1:1로 참여
        * 학부모 상담 등을 진행
* 공지/알림 전송
    * 선생님이 작성하여 학생 또는 학부모에게 전송
    * 공지 내용은 개별로 전송

### 권한

|                  | 선생님 | 학부모 | 학생 |
|------------------|-----|-----|----|
| 유저 생성            | O   | X   | X  |
| 그룹 채팅방 개설        | O   | X   | X  |
| 그룹 채팅방 금칙어 추가,수정 | O   | X   | X  |
| 그룹 채팅방에서 메시지 전송  | O   | O   | X  |
| 공지/알림 전송         | O   | X   | X  |
| 공지/알림 수신         | O   | O   | O  |
| 개별 채팅 시작         | O   | O   | O  |
| 개별 채팅에서 메시지 전송   | O   | O   | O  |

## 기술 스택

* Spring Boot 3.2
* Java 21
* MariaDB
* JPA
* Gradle
* Redis Cache
* Redis pub/sub
* AWS
* Jenkins
* intellij IDEA

## DB ERD

[ERD 링크](https://www.erdcloud.com/d/twGddwaPqr6JZH5XP)

![Image](https://github.com/user-attachments/assets/23e7ede8-8fd8-4a27-8371-32bdcbcb9ebc)

## 구조

![Image](https://github.com/user-attachments/assets/2e64ba46-4fdb-49bf-b6ba-66cfd3735ccb)

* Redis Cache
    * 채팅방을 기준으로 파티션 구분, 채팅방 : 파티션 = 1 : 1
    * 금칙어는 하나의 파티션에 저장(DB에도 같이 저장)
    

### 흐름

* 채팅

    * 전송

        1. web socket 데이터 수신
        2. Redis를 통해, 도배 탐지
        3. 도배가 아니면, Redis, kafka, DB에 메시지 전송(비동기)
    * 수신

        1. kafka에서 메시지 수신
        2. 금칙어 탐지 및 마스킹
        3. web socket 데이터 전송
* 알림/공지

    1. schduler를 이용해 일정 시간마다 알림 내용을 DB에서 조회
    2. notification에게 조회된 알림 작업 위임
