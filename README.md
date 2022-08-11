# tues

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
    - vue3
- 백엔드
    - Java 11
    - Spring MVC 5.3.14
    - Gradle
    - Spring Data JPA 2.6.2
    - JUnit5
- DBMS
    - MySQL
- 배포
    - AWS - EC2, S3, RDS
    
  ## 폴더 구조

```vim
├─java
│  └─kong
│      └─tues
│          ├─commons
│          │  ├─config
│          │  ├─dto
│          │  ├─exception
│          │  ├─interceptor
│          │  └─session
│          ├─goal
│          │  ├─dailyGoal
│          │  │  ├─application
│          │  │  │  └─dto
│          │  │  ├─domain
│          │  │  │  └─repository
│          │  │  └─presentation
│          │  │      ├─dto
│          │  │      └─validator
│          │  ├─exception
│          │  ├─goalList
│          │  │  └─application
│          │  │      └─dto
│          │  ├─mothlyGoal
│          │  │  ├─application
│          │  │  ├─domain
│          │  │  │  └─repository
│          │  │  ├─dto
│          │  │  └─presentation
│          │  │      └─validator
│          │  └─presentation
│          │      └─dto
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
  ```
## UI 프로토타입
[https://www.figma.com/file/IwOz4a8tiSH09NKfF9jVN2/Tues?node-id=0%3A1](https://www.figma.com/file/IwOz4a8tiSH09NKfF9jVN2/Tues?node-id=0%3A1)

## ERD
![image](https://user-images.githubusercontent.com/68289543/184105008-5d0dce81-3e92-4998-864b-874ebd2f0bb0.png)

## 핵심 기능
- 회원가입 및 로그인 기능
- 아이디, 비밀번호 찾기 기능
    - 찾은 아이디, 임시 비밀번호 메일로 보내기
- 월간 목표, 일간 목표 생성 기능
    - 월간 목표와 일간 목표의 종류가 같은 경우 일간 목표의 개수나 시간을 조정하면 월간 목표에도 반영됨
- 목표 수정, 삭제 기능
- 목표 상세 보기 기능
- 해당 월의 모든 목표와 달성률 보기 기능
