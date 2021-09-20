# Backend
마음을 잇는 현명한 소비 '잇다'🤝 (현재 진행 중인 프로젝트 입니다.)

![스크린샷 2021-09-15 오전 11 28 30](https://user-images.githubusercontent.com/65011131/133360545-f99e490f-c2c1-417e-8af3-458272cfda7b.png)

- 모티브는 KBS 프로그램, '동행'. 스토리텔링 + 판매플랫폼 구축
  - ['동행' 공식사이트](http://program.kbs.co.kr/1tv/culture/accompany/pc/index.html)
- 1차 산업에 종사하면서 상품성이 있는 제품들을 생산하고 있지만 소비자와의 접점이 없어 생활고가 있는 사람들에게 판매 플랫폼을 제공한다. 주문을 받을 수 있는 시스템을 만들어서 소비자와 직접적인 거래가 가능할 수 있는 판매루트를 구축해준다.

</br>

### ⚒ 사용 기술 스택

Backend - Java, Spring Boot, Spring Data JPA, Gradle, Redis

Test - JUnit5, Mockito

Database - MySQL

Server - Google Cloud Platform, Nginx, Docker

CI/CD - Jenkins

<img width="674" alt="스크린샷 2021-09-15 오전 11 36 05" src="https://user-images.githubusercontent.com/65011131/133361154-6b6cc533-2100-430f-8519-f41166f913ad.png">



### 주요 구현 기능

**제품, 카테고리**

- 판매자의 제품 등록
- 전체 제품 조회
- 제품 이름으로 조회
- 제품의 판매자 이름으로 조회
- 리뷰 추가, 조회

**로그인, 회원가입**

- 네이버, 카카오 소셜 로그인(OAuth)

**장바구니**

- Redis를 이용한 장바구니 물건 담기, 수정, 삭제
- 장바구니 조회 

**주문하기**

- Redis를 이용한 연속적인 주문 요청 방지
- 주문하기 
