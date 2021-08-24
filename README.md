# Matcherloper (개발자 매칭 프로젝트) [![Build Status](https://app.travis-ci.com/MatcherLoper/MatcherLoper_Server.svg?token=hyUJYqs7AAdXxo2iuzQs&branch=develop)](https://app.travis-ci.com/MatcherLoper/MatcherLoper_Server)

## 🤷‍♂️서비스 소개

"저는 초보 개발자인데 프로젝트를 할 수 있는 마땅한 기회가 없어요!!😂"

때마침, 나타난 `Matcherloper`⁉

</br >

`Matcherloper`는 개발자들이 프로젝트를 경험할 수 있는 기회를 제공합니다.

특히, 프로젝트 경험이 전무후무 한 많은 주니어 개발자들에게 필요한 어플입니다.

</br >

한 `사용자`(creatUser)가 필요한 `포지션의 수`와 `방 이름`을 명시한 `방`을 형성하고 그 외 방을 참여하고 싶은 `사용자`(participantUser)는 `매칭`을 통해 선택한 포지션에 맞는 빈 방을 찾아 랜덤으로 방에 참여할 수 있도록 합니다.

</br >

## 🖐개발 기간

- 2021.07.14 ~ 2021.08.24

</br >

## 📚기술 스택

- JAVA 8
- Gradle
- Spring Boot 2.3.4
  - Spring Data JPA
- Spring Security
- Firebase
- QueryDsl
- MySQL 8.0.23
- AWS EC2, RDS, S3, CodeDelploy
- Junit5

- Travis CI

</br >

## 🍻기능

### 회원

- 회원가입 및 로그인
  - 로그인 시 토큰을  발급받습니다.
  - 앱을 통해 구글 계정으로 로그인할 수 있습니다.
- 방을 생성할 수 있습니다.
  - 필요한 포지션의 수, 오프라인 가능 지역 등 정보를 입력할 수 있습니다.
- 방에 참여할 수 있습니다.
  - 참여할 회원의 포지션을 정해 랜덤으로 매칭되도록 합니다.

</br >

### 방 참여

- 이벤트 핸들러를 통해 새로운 방이 생성되거나 방의 상태가 OPEN으로 바뀌었을 때 매칭중인 개발자에게 알림이 가도록 하여 방을 참여할 수 있습니다.

