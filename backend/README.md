# Calendar 프로젝트 백엔드

본 프로젝트는 Spring Boot를 사용하여 구축된 캘린더 애플리케이션의 백엔드 서버입니다. 사용자 인증 및 캘린더 관리를 위한 REST API를 제공합니다.

## ✨ 주요 기능

- **사용자 관리**:
  - 회원가입 및 로그인 기능
  - JWT(JSON Web Token)를 사용한 인증 및 인가
  - Refresh Token을 이용한 액세스 토큰 재발급
- **캘린더 관리**:
  - 캘린더 일정 생성, 조회, 수정, 삭제 (CRUD)
  - 사용자별 캘린더 데이터 관리

## 🛠️ 기술 스택

- **언어**: Java 21
- **프레임워크**: Spring Boot 3.5.0, Spring Security, Spring Data JPA
- **빌드 도구**: Gradle
- **인증**: JSON Web Token (JWT) 0.12.6

## 📂 프로젝트 구조

```
src
└── main
    ├── java
    │   └── com
    │       └── example
    │           └── jocso
    │               ├── CalendarApplication.java      # Spring Boot 메인 애플리케이션
    │               ├── WebConfig.java                # CORS 등 웹 관련 설정
    │               │
    │               ├── accounts                      # 사용자 계정 및 인증 관련 패키지
    │               │   ├── config                    # Spring Security 및 JWT 필터 설정
    │               │   ├── controller                # 사용자 API 컨트롤러
    │               │   ├── domain                    # User, RefreshToken 등 도메인 엔티티
    │               │   ├── dto                       # 데이터 전송 객체 (Request/Response)
    │               │   ├── jwt                       # JWT 생성 및 검증 유틸리티
    │               │   ├── repository                # 데이터베이스 접근을 위한 Repository
    │               │   └── service                   # 비즈니스 로직 서비스
    │               │
    │               ├── calendars                     # 캘린더 기능 관련 패키지
    │               │   ├── controller                # 캘린더 API 컨트롤러
    │               │   ├── dto                       # 캘린더 데이터 전송 객체
    │               │   ├── entity                    # Calendar 엔티티
    │               │   ├── exception                 # 캘린더 관련 커스텀 예외
    │               │   ├── repository                # Calendar Repository
    │               │   └── service                   # 캘린더 비즈니스 로직 서비스
    │               │
    │               └── exception                     # 전역 예외 처리 핸들러
    │
    └── resources
        ├── application.properties        # 애플리케이션 설정 파일 (DB, JWT 등)
        ├── static                        # 정적 리소스
        └── templates                     # 템플릿 파일
```

## 🚀 시작하기

### 사전 요구사항

- Java 17 이상
- Gradle

### 설치 및 실행

1. **프로젝트 클론**
   ```bash
   git clone {저장소 URL}
   cd backend
   ```

2. **프로젝트 빌드**
   ```bash
   ./gradlew build
   ```

3. **애플리케이션 실행**
   ```bash
   ./gradlew bootRun
   ```

서버는 `application.properties`에 설정된 포트(기본: 8080)에서 실행됩니다.
