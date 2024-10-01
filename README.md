## 중고거래가 가능한 쇼핑몰 REST API
- 기존에 만들었던 쇼핑몰 애플리케이션을 수정하고 발전시킨 리팩토링 프로젝트 입니다. 
- 개발 기간 : 24.07.19 ~
- 참여 인원 : 1명
 
## 💻 기술 스택
- Language : `Java 17`
- Framework
  - `Spring Boot 3.3.1`, 
    - `Spring Security`
    - `querydsl 5.1.0`
    - `jjwt 0.12.3`
    - `OAuth2`
  - `React`
- Build : `gradle`
- DB : `PostgreSQL`, `Redis`
- ORM : `Spring Data JPA` 
- Documentation 
  - [Postman : API 명세 ](src%2FMarket.postman_collection.json)

  
## 아키텍처


## 패키지 구조

<details>
<summary>패키지 구조</summary>
<div markdown="1">

```bash
    ├── front
    ├── main
    │   ├── java
    │   │   └── com
    │   │       └── example
    │   │           └── market
    │   │               ├── MarketApplication.java
    │   │               ├── domain
    │   │               │   ├── auth
    │   │               │   │   ├── constant
    │   │               │   │   │   ├── BusinessStatus.java
    │   │               │   │   │   ├── Role.java
    │   │               │   │   │   └── SocialType.java
    │   │               │   │   ├── controller
    │   │               │   │   │   └── UserController.java
    │   │               │   │   ├── dto
    │   │               │   │   │   ├── AccessTokenDto.java
    │   │               │   │   │   ├── BusinessDto.java
    │   │               │   │   │   ├── CreateUserDto.java
    │   │               │   │   │   ├── LoginDto.java
    │   │               │   │   │   ├── PrincipalDetails.java
    │   │               │   │   │   ├── ReissuanceDto.java
    │   │               │   │   │   ├── UpdateUserDto.java
    │   │               │   │   │   ├── UserDto.java
    │   │               │   │   │   └── oauth2
    │   │               │   │   │       ├── GoogleResponse.java
    │   │               │   │   │       ├── NaverResponse.java
    │   │               │   │   │       └── OAuth2Response.java
    │   │               │   │   ├── entity
    │   │               │   │   │   ├── RefreshToken.java
    │   │               │   │   │   └── User.java
    │   │               │   │   ├── handler
    │   │               │   │   │   └── OAuth2LoginSuccessHandler.java
    │   │               │   │   ├── jwt
    │   │               │   │   │   ├── JwtTokenDto.java
    │   │               │   │   │   ├── JwtTokenFilter.java
    │   │               │   │   │   ├── JwtTokenUtils.java
    │   │               │   │   │   └── TokenType.java
    │   │               │   │   ├── repository
    │   │               │   │   │   ├── RefreshTokenRepository.java
    │   │               │   │   │   └── UserRepository.java
    │   │               │   │   └── service
    │   │               │   │       ├── PrincipalDetailsService.java
    │   │               │   │       └── PrincipalOAuth2UserService.java
    │   │               │   ├── shop
    │   │               │   │   ├── constant
    │   │               │   │   │   ├── DiscountRate.java
    │   │               │   │   │   ├── OrderStatus.java
    │   │               │   │   │   ├── ShopCategory.java
    │   │               │   │   │   └── ShopStatus.java
    │   │               │   │   ├── controller
    │   │               │   │   │   ├── ItemController.java
    │   │               │   │   │   ├── OrderController.java
    │   │               │   │   │   └── ShopController.java
    │   │               │   │   ├── dto
    │   │               │   │   │   ├── CategoryDto.java
    │   │               │   │   │   ├── CreateItemDto.java
    │   │               │   │   │   ├── DiscountDto.java
    │   │               │   │   │   ├── ItemDto.java
    │   │               │   │   │   ├── OrderDto.java
    │   │               │   │   │   ├── OrderItemDto.java
    │   │               │   │   │   ├── PurchaseRequestDto.java
    │   │               │   │   │   ├── SearchItemDto.java
    │   │               │   │   │   ├── SearchShopDto.java
    │   │               │   │   │   ├── ShopDto.java
    │   │               │   │   │   ├── SubCategoryDto.java
    │   │               │   │   │   └── UpdateShopDto.java
    │   │               │   │   ├── entity
    │   │               │   │   │   ├── Category.java
    │   │               │   │   │   ├── Item.java
    │   │               │   │   │   ├── Order.java
    │   │               │   │   │   ├── OrderItem.java
    │   │               │   │   │   ├── Shop.java
    │   │               │   │   │   └── SubCategory.java
    │   │               │   │   ├── repository
    │   │               │   │   │   ├── CategoryRepository.java
    │   │               │   │   │   ├── ItemRepository.java
    │   │               │   │   │   ├── ItemRepositoryCustom.java
    │   │               │   │   │   ├── ItemRepositoryImpl.java
    │   │               │   │   │   ├── OrderItemRepository.java
    │   │               │   │   │   ├── OrderRepository.java
    │   │               │   │   │   ├── ShopRepository.java
    │   │               │   │   │   ├── ShopRepositoryCustom.java
    │   │               │   │   │   ├── ShopRepositoryImpl.java
    │   │               │   │   │   └── SubCategoryRepository.java
    │   │               │   │   └── service
    │   │               │   │       ├── ItemService.java
    │   │               │   │       ├── OrderService.java
    │   │               │   │       └── ShopService.java
    │   │               │   └── used_trade
    │   │               │       ├── controller
    │   │               │       │   ├── TradeItemController.java
    │   │               │       │   └── TradeOfferController.java
    │   │               │       ├── dto
    │   │               │       │   ├── TradeItemDto.java
    │   │               │       │   └── TradeOfferDto.java
    │   │               │       ├── entity
    │   │               │       │   ├── TradeItem.java
    │   │               │       │   └── TradeOffer.java
    │   │               │       ├── repository
    │   │               │       │   ├── TradeItemRepository.java
    │   │               │       │   ├── TradeOfferRepository.java
    │   │               │       │   ├── TradeOfferRepositoryCustom.java
    │   │               │       │   └── TradeOfferRepositoryImpl.java
    │   │               │       └── service
    │   │               │           ├── TradeItemService.java
    │   │               │           └── TradeOfferService.java
    │   │               └── global
    │   │                   ├── common
    │   │                   │   ├── AuthenticationFacade.java
    │   │                   │   └── BaseEntity.java
    │   │                   ├── config
    │   │                   │   ├── CorsMvcConfig.java
    │   │                   │   ├── JpaConfig.java
    │   │                   │   ├── PasswordEncoderConfig.java
    │   │                   │   ├── RedisConfig.java
    │   │                   │   ├── SwaggerConfig.java
    │   │                   │   └── WebSecurityConfig.java
    │   │                   ├── error
    │   │                   │   └── exception
    │   │                   │       ├── ErrorCode.java
    │   │                   │       ├── ErrorResponse.java
    │   │                   │       ├── GlobalCustomException.java
    │   │                   │       └── GlobalExceptionHandler.java
    │   │                   ├── infra
    │   │                   │   └── DataInitializer.java
    │   │                   └── util
    │   │                       ├── CookieUtil.java
    │   │                       ├── FileHandlerUtils.java
    │   │                       └── MultipartJackson2HttpMessageConverter.java
    │   └── resources
    │       ├── application-dev.yaml
    │       ├── application-prod.yaml
    │       ├── application-test.yaml
    │       ├── static
    │       └── templates
    │           ├── home.html
    │           ├── my-page.html
    │           ├── sign-in.html
    │           └── sign-up.html
```

</div>
</details>

## ERD
![img.png](img.png)


## 기능

<details>
<summary>회원 인증 및 권한 처리</summary>
<div markdown="1">

</div>
</details>

<details>
<summary>중고거래 중개</summary>
<div markdown="1">

</div>
</details>

<details>
<summary>쇼핑몰 운영</summary>
<div markdown="1">

</div>
</details>


## 기술적 도전
- 외부 DB 사용 : SQlite -> PostgreSQL
- 회원 엔티티 키 구성 uuid
- 더미데이터 추가 : applicationRunner 사용
- 테스트 코드 도입 
- Spring Security + JWT + Redis 
  - JWT accessToken 만 사용 -> refreshtoken 추가 및 Redis 에 저장 (RTR 기법 적용)
  - JWT 토큰 쿠키에 담아 발급
- 커스텀 예외 처리 중앙화 (feat. `@RestControllerAdvice`)
- Querydsl 도입, 페이지 네이션 구현
- Swagger 도입
- OAuth2 도입 : `Naver`, `Google`
  - OAuth2 테스트를 위한 리액트 스프링부트 연동 
  - CORS 설정

## 트러블 슈팅
- 필터에서 RefreshToken 자동 발급 시 문제점 및 토큰 재발급에 대한 방향 (RTR)
- JWT 토큰을 헤더에 노출시키면 위험한 이유
- querydsl 쇼핑몰 조회 쿼리 작성 후 결과 dto 로 받는방법 - Projections 필드명 불일치 오류
- 커스텀 에러 처리
- 일반 회원가입, OAuth2 회원가입 통합 - PrincipalDetails, 쿠키 도입 후 필터 수정
- 필터를 거치지 않는 URL 경로 설정 : shouldNotFilter 오버라이딩
- 멀티파트 데이터 전송 시 Swagger 오류 : `'application/octet-stream' not supported`
- 스웨거 쿠키 헤더 자동 삽입이 가능한지

## 학습 내용 정리

## 느낀점



