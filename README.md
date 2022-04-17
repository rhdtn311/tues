# Readme

## 제작 기간

2022년 1월 9일 ~ 2022년 2월 7일

## 프로젝트 동기

인프런에서 Spring MVC 강의를 들으면서 여러 기술들을 배웠으나 직접 사용해본 적이 없기 때문에 배운 기술들에 자신이 없었다. 그리고 REST API 방식으로 백엔드 파트만 맡아서 프로젝트를 진행해본 경험은 있지만 Spring MVC를 사용하여 서버와 클라이언트까지 모두 직접 개발한 경험은 없었기 때문에 꼭 개인 프로젝트 하나를 해야겠다고 생각하고 있었다. 

원래 공부하기 전에 어떤 공부를 할지 계획하고 하나씩 실행하는 것을 좋아하는데 마땅히 계획을 작성하고 내가 얼마나 목표를 성취했는지 확인할 수 있는 툴이 없어서 Notion에 템플릿을 만들어 하루 계획과 월간 계획을 세워서 실천하고 있었다.

![image](https://user-images.githubusercontent.com/68289543/155852064-486acd16-c4a8-49f4-89bf-5202b60cb9b4.png)

하지만 Notion 템플릿을 사용하다 보니 불편한 점이 많았고 시간이나 개수로 내가 얼마나 공부를 했는지 확인하고 싶었다. 따라서 내가 원하는 요구사항으로 직접 어플리케이션을 제작해봐야겠다고 생각했고 실행에 옮기게 되었다.

## 사용 기술

- 프론트엔드
    - Css / Javascript
    - Thymeleaf 2.6.2
    - ajax
    - JQuery
- 백엔드
    - Java 11
    - Spring MVC 5.3.14
    - Gradle
    - Spring Data JPA 2.6.2
    - JUnit5
- DBMS
    - MariaDB
- 배포
    - AWS - EC2, S3, RDS

## 폴더 구조
```vim
├─java
│  └─kong
│      └─tues
│          ├─accessTime
│          │  ├─application
│          │  ├─domain
│          │  │  └─repository
│          │  └─presentation
│          ├─achievement
│          │  ├─application
│          │  ├─domain
│          │  │  └─repository
│          │  └─presentation
│          ├─commons
│          │  ├─argumentresolver
│          │  ├─config
│          │  ├─exception
│          │  ├─image
│          │  ├─interceptor
│          │  └─session
│          ├─goal
│          │  ├─dailyGoal
│          │  │  ├─application
│          │  │  │  ├─dailyGoalManager
│          │  │  │  └─dto
│          │  │  ├─domain
│          │  │  │  └─repository
│          │  │  └─presentation
│          │  │      ├─dto
│          │  │      └─validator
│          │  ├─exception
│          │  ├─mothlyGoal
│          │  │  ├─application
│          │  │  │  └─monthlyGoalManager
│          │  │  ├─domain
│          │  │  │  └─repository
│          │  │  ├─dto
│          │  │  └─presentation
│          │  │      └─validator
│          │  └─presentation
│          ├─mail
│          │  ├─application
│          │  └─dto
│          └─member
│              ├─application
│              │  └─dto
│              ├─domain
│              │  └─repository
│              ├─exception
│              └─presentation
│                  └─validation
└─resources
├─static
│  ├─images
│  └─styles
│      └─css
└─templates
├─error
├─goal
└─member
```
## UI 프로토타입

[https://www.figma.com/file/IwOz4a8tiSH09NKfF9jVN2/Tues?node-id=0%3A1](https://www.figma.com/file/IwOz4a8tiSH09NKfF9jVN2/Tues?node-id=0%3A1)

## ERD 설계

![image](https://user-images.githubusercontent.com/68289543/155852070-7698a405-717a-478f-945b-01b27f38aafa.png)

## 핵심 기능

- 회원가입 및 로그인 기능
- 아이디, 비밀번호 찾기 기능
    - 찾은 아이디, 임시 비밀번호 메일로 보내기
- 월간 목표, 일간 목표 생성 기능
    - 월간 목표와 일간 목표의 종류가 같은 경우 일간 목표의 개수나 시간을 조정하면 월간 목표에도 반영됨
- 목표 수정, 삭제 기능
- 목표 상세 보기 기능
- 해당 월의 모든 목표와 달성률 보기 기능

## 프로젝트 진행 시 발생한 문제들

어려운 프로젝트가 아니었기 때문에 많은 문제가 발생하진 않았다. 보통 문제는 거의 처음 접해보는 템플릿 부분에서 발생하였다.

### 목표 성공 및 실패 처리 시 이미지 깜빡임

기존에는 목표의 개수를 올리거나 개수를 내리거나 성공, 실패 버튼을 누르면 목표의 개수가 변화하기 때문에 모든 목표를 다시 불러와서 화면을 다시 띄우도록 개발하였다. 하지만 모든 데이터를 다시 불러오기 때문에 화면이 깜빡거렸고 특히 이미지 부분이 깜빡임이 심했다. 자주 사용하게 될 기능이기 때문에 어떻게 하면 깜빡임을 최소화 할 수 있을지 생각하였다.

![이미지 깜빡임](https://user-images.githubusercontent.com/68289543/155852101-baa6719a-460f-43b0-8307-c7a6ef8e1f9a.gif)

고민하던 중 정보처리기사 자격증을 딸 때 공부했던 AJAX가 떠올랐다. Thymeleaf와 Jquery를 사용하여 ajax를 호출하는 방법을 사용하여 버튼을 누르는 API 호출 시 전체 페이지를 불러오지 않고 값이 바뀌는 text 부분만 불러와서 교체하도록 하여 문제를 해결했다.

### 느낀점 및 회고

Tues라는 뜻은 내가 가장 좋아하는 말 중 하나인 “Just do it”을 독일어로 번역한 것인데 내가 세운 목표이기 때문에 심적으로 하기 싫은 일이라도 “그냥 하자”라는 마인드로 목표를 해내기 위해 ‘Tues’라는 이름으로 만들었다. 

Html, Css부터 시작하여 서버와 배포까지 처음으로 프로젝트 하나를 클라이언트부터 서버까지 만들어 보았다. 프로젝트를 진행하면서 Spring MVC에 익숙해 질 수 있었고  뷰 템플릿(Thymeleaf), Validation, Session, Interceptor를 이용한 로그인, Controller Advice, Exception Handler를 이용한 예외처리, Arguments Resolver 활용 등 Spring MVC 강의를 들으면서 배웠던 여러 기술들을 사용해볼 수 있어서 좋은 경험이었다. 또 오로지 나를 위한 앱이고 처음 하는 개인 프로젝트였기 때문에 앱 로고부터 목표 종류 이미지, 월마다 바뀌는 일러스트, 버튼 css 등 디자인에 관련된 일 뿐만 아니라 배포까지 직접 하였는데 이런 작은 프로젝트에도 해야 할게 많다는 것을 느꼈다.

앞으로 내 모든 월별, 년도별 목표를 모아 달성률을 확인할 수 있는 기능, 테스트 코드를 작성할 계획이다.
