입력한 주소정보와 가까운 약국 3곳을 추천하는 서비스를 제공하는 프로젝트입니다.

## 개발 환경
- Intellij IDEA Ultimate 2023.3.2
- Java 17
- Gradle 8.5
- Spring Boot 2.6.7

## 기술 세부 스택
Spring Boot
- Spring Web
- Spring Data JPA
- Spring Retry
- Handlebars
- Lombok
- Docker
- AWS EC2
- Redis
- MariaDB
- Spock
- Testcontainers

## Feature List
* jpa를 이용한 crud 구현
* Spock를 이용한 테스트 코드 작성
* Testcontainers를 이용하여 독립 테스트 환경 구축
* 카카오 주소검색 API 이용하여 주소를 위도, 경도로 변환
* 추천 결과를 카카오 지도 URL로 연동하여 제공
* 공공 데이터를 활용하여 개발 (약국 현황 데이터)
* Handlebars를 이용한 간단한 View 구현
* Docker를 이용하여 다중 컨테이너 애플리케이션 만들기
* 애플리케이션을 AWS EC2에 배포
* Spring retry를 이용한 재처리 구현
* base62를 이용한 shorten url 개발 (길안내 URL)
* redis를 이용하여 성능 최적화
