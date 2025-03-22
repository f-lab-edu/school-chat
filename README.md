# SchoolTalk

- 학교의 선생님과 학생, 학부모의 소통을 이어주는 채팅 서비스 입니다.
- 모놀리식 구조로 개발 이후 채팅 서비스 분리하여 MSA 구조로 변경을 고려해 분리하기 쉬운 구조로 개발하려 합니다.

## 주요 기능

* 그룹 채팅
    * 특정 기준으로 개설된 채팅방(예. 반별, 학년별, 동아리)
    * 선생님과 학생들이 1:N으로 참여
    * 선생님이 채팅방을 개설하며, 학생들이 참여
    * 부가 기능: 금칙어
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
* Redis
* Kafka
* AWS
* Jenkins
* intellij IDEA

## DB ERD

[ERD 링크](https://www.erdcloud.com/d/twGddwaPqr6JZH5XP)
![Image](https://github.com/user-attachments/assets/fde0b6a2-dd6e-458f-99d6-739657a1fdfc)

## 구조
![Image](https://github.com/user-attachments/assets/2c4fe706-4bd6-415c-9e78-cf6ccb47b61a)
