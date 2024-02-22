# 🎁 포장의 민족
<p align="center">
  <img src= "https://github.com/Team-SNS/Pojang-BE/assets/78871184/ef7c6f3e-446d-47e8-a4e3-e19b9cd8d6b2" width="100%" height="100%">
</p> 
<br/>

# 📣 프로젝트 목적
> **요기요 웹서비스를 클론코딩하여 스프링과 vue를 학습**
<br/>

# 💡 서비스 소개
> **포장 전문 웹 서비스로 회원들이 본인이 등록한 주소 기반으로 주변의 가게들의 정보를 보면서 가까운 가게에서 미리 포장하여 빠르게 음식을 받을 수 있게 하는 시스템**
<br/>

# 🗓 프로젝트 기간
> **2024/02/19 ~ 2024/02/23**
<br/>

# 📱팀 명: SNS
| 팀 원 | 이름 |
|:---:|:---:|
| 팀장 | [한종승👨‍💻](https://github.com/BellWin98) |
| 서기 | [배소영👩‍💻](https://github.com/qoth-0) |
| 팀원 | [심재혁👨‍💻](https://github.com/SimJH99) |
| 팀원 | [이원태👨‍💻](https://github.com/wontae0924) |
<br/>

# 💪 기술 스택
> ## 🛠 개발환경
> 
>  ![Java11](https://img.shields.io/badge/Java11-007396.svg?&style=for-the-badge&logo=Java&logoColor=white)
>  ![Spring Boot](https://img.shields.io/badge/SpringBoot-6DB33F.svg?&style=for-the-badge&logo=SpringBoot&logoColor=white)
>  ![Spring security](https://img.shields.io/badge/SpringSecurity-6DB33F.svg?&style=for-the-badge&logo=SpringSecurity&logoColor=white)
>  ![jsonwebtokens](https://img.shields.io/badge/JWT-000000.svg?&style=for-the-badge&logo=jsonwebtokens&logoColor=white)
>  ![MariaDB](https://img.shields.io/badge/MariaDB-003545.svg?&style=for-the-badge&logo=MariaDB&logoColor=white)
>  ![vuedotjs](https://img.shields.io/badge/Vue-4FC08D.svg?&style=for-the-badge&logo=Vuedotjs&logoColor=white)
>  ![tailwindcss](https://img.shields.io/badge/tailwind-06B6D4.svg?&style=for-the-badge&logo=tailwindcss&logoColor=white)
> 
>## 🔊 협업 툴
>  ![github](https://img.shields.io/badge/Github-181717.svg?&style=for-the-badge&logo=github&logoColor=white)
>  ![Notion](https://img.shields.io/badge/Notion-000000.svg?&style=for-the-badge&logo=Notion&logoColor=white)
>  ![Slack](https://img.shields.io/badge/Slack-4A154B.svg?&style=for-the-badge&logo=Slack&logoColor=white)
>  ![Trello](https://img.shields.io/badge/Trello-0052CC.svg?&style=for-the-badge&logo=Trello&logoColor=white)
>  ![visualstudiocode](https://img.shields.io/badge/VScode-007ACC.svg?&style=for-the-badge&logo=visualstudiocode&logoColor=white)
>  ![intellijidea](https://img.shields.io/badge/IntelliJidea-000000.svg?&style=for-the-badge&logo=intellijidea&logoColor=white)
<br/>

# 🚩 Git 브랜치 전략
<p align="center">
  <img src= "https://github.com/Team-SNS/Pojang-BE/assets/78871184/b8a464a8-61ce-4bca-8082-be0affd5a342" width="100%" height="100%">
</p> 

- 저장소를 효과적으로 활용하기 위해 SNS팀은 Github-Flow 전략 브랜치를 생성한다.
1. 이슈 탭 클릭 → New Issue → 템플릿 선택 → 이슈 제목 및 작업할 내용 입력 → Assignees 본인으로 설정 → Label 선택 → issue 생성
2. 브랜치 생성: main 브랜치 → feature 브랜치 분기
3. 브랜치 규칙: feature/{엔티티}-(이슈번호)
4. 커밋 순서: [Type] 작업 내용(#이슈번호)
5. PR 날린 후 팀원들의 코드 리뷰
6. 코드 리뷰 완료되면 main 브랜치에 Merge 
<br/>

# 📈 ERD 모델링
[![포장의 민족](https://github.com/Team-SNS/Pojang-BE/assets/78871184/86609aa5-4ec8-4c0f-836a-6313136cef18)](https://www.erdcloud.com/d/xroTBFytvBCr9fm5S)
<br/>
`사진 클릭시 이동`

# 요구사항 명세서
<details>
<summary><h2>😀 회원(Member)</h2></summary>
<div markdown="1">
  
**1. 회원 가입(회원, 사장님)**

**2. 내 정보 보기(마이페이지)**

**3. 회원 정보 수정(닉네임, 휴대폰 번호, 주소)**

**4. 회원 탈퇴**

**5. 주문하기**
  - 주문 별 리뷰 작성, 수정, 삭제

**6. 내 주문 내역 조회**

**7. 가게 찜하기**
  - 가게 찜 등록
  - 가게 찜 취소
  - 내가 찜한 가게 보기

**8. 매장 전체 및 카테고리 별 목록 조회**

**9. 매장 검색**
</div>
</details>

<details>
<summary><h2>🏪 가게(Store)</h2></summary>
<div markdown="1">

**1. 가게 등록**

**2. 내 가게 리스트 보기**

**3. 가게 상세 정보 보기**

**4. 가게 정보 수정**

**5. 가게 탈퇴**

**6. 가게에 들어온 주문 내역 조회**
  - 주문 내역에 대한 상태 처리
    
**7. 매출 정보(일, 주, 월 별)**

**8. 평균 별점 수 보기**

**9. 각 매장 총 찜 수 보기**

**10. 가게에 달린 모든 리뷰 보기**
</div>
</details>

<details>
<summary><h2>📋 메뉴(Menu)</h2></summary>
<div markdown="1">

**1. 메뉴 등록**
  
**2. 메뉴 옵션 그룹 등록**

**3. 메뉴 옵션 등록**

**4. 메뉴 수정**

**5. 메뉴 삭제**

**6. 메뉴 목록 조회**

**7. 메뉴 상세 정보 조회**
</div>
</details>

<details>
<summary><h2>🧾 주문(Order)</h2></summary>
<div markdown="1">

**1. 주문 하기(회원)**

**2. 주문 상세 보기(회원, 사장님)**

**3. 주문 내역 조회(회원, 사장님)**

**4. 주문 취소(회원, 사장님)**

**5. 총 결제 금액(회원, 사장님)**

**6. 주문 상태 처리(사장님)**
  - 접수 중
  - 조리 중
  - 조리 완료

</div>
</details>

<details>
<summary><h2>🛒 장바구니(Cart)</h2></summary>
<div markdown="1">

**1. 장바구니 생성 - local storage**

**2. 주문 확정 하기**

**3. 예상 결제 금액**

**4. 주문 완료 시, 장바구니 비우기**

</div>
</details>

<details>
<summary><h2>💸 결제(Payment)</h2></summary>
<div markdown="1">

**1. 결제하기**

**2. 총 결제 금액 전송**

**3. 결제 취소**

**4. 결제 내역 조회**

**5. 총 결제 금액 조회**

</div>
</details>

<details>
<summary><h2>🔑 인증(Authentication)</h2></summary>
<div markdown="1">
  
**1. 일반 로그인 (JWT)** 
  - 토큰 발급
  - 지정 시간 후, 토큰 만료

**2. 이메일 인증(Email)**

**3. 휴대폰 인증(SMS)**

**4. 로그아웃**

**5. 각 회원, 매장, 메뉴, 주문에서 서버와의 데이터 검증**
</div>
</details>
<br/>

# 📝 API명세서
> [📂API DOCS](https://robust-skunk-0f9.notion.site/API-0f59651871d44b36a32874c9f8b4f0e0?pvs=4)
