# 🎁 포장의 민족
<p align="center">
  <img src= "https://github.com/Team-SNS/Pojang-BE/assets/78871184/ef7c6f3e-446d-47e8-a4e3-e19b9cd8d6b2" width="100%" height="100%">
</p> 
<br/>

# 📣 프로젝트 목적
> **요기요를 클론코딩하여 스프링과 vue를 학습**
<br/>

# 💡 서비스 소개
> **포장 전문 웹 서비스로 회원들이 본인이 등록한 주소 기반으로 주변의 가게들의 정보를 보면서 가까운 가게에서 미리 포장하여 빠르게 음식을 받을 수 있게 하는 서비스**
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

# 📌 요구사항 명세서
<details>
<summary ><h2>😀 회원(Member)</h2></summary>
<div markdown="1">

1. 일반 회원은 내 정보를 조회할 수 있다.
2. 일반 회원은 주문 내역을 조회할 수 있다.
3. 일반 회원은 가게를 찜 및 찜 취소를 할 수 있다.
4. 일반 회원은 가게에 리뷰를 남길 수 있다. (주문 확정된 경우)
5. 일반 회원은 가게 찜 목록을 조회할 수 있다.
6. 일반 회원은 가게 리뷰 목록을 조회할 수 있다.
7. 일반 회원은 개인 정보를 수정할 수 있다.
8. 일반 회원은 회원을 탈퇴할 수 있다.
</div>
</details>

<details>
<summary><h2>🏪 가게(Store)</h2></summary>
<div markdown="1">

1. 홈 화면에서 가게명을 검색할 수 있다.
2. 등록된 가게를 전체 조회할 수 있다.
3. 등록된 가게를 카테고리 별로 조회할 수 있다.
4. 사장은 신규 가게를 여러개 등록할 수 있다.
5. 사장은 등록한 가게 리스트를 조회할 수 있다.
6. 사장은 등록한 가게의 상세 정보를 조회할 수 있다.
7. 사장은 등록한 가게에 달린 리뷰를 조회할 수 있다.
8. 사장은 매출 정보를 일, 주, 월별로 확인할 수 있다.
9. 사장은 등록한 가게의 정보를 수정할 수 있다.
10. 사장은 등록한 가게를 삭제할 수 있다.
11. 가게의 평점을 확인할 수 있다.
12. 가게의 찜 수를 확인할 수 있다.

</div>
</details>

<details>
<summary><h2>📋 메뉴(Menu)</h2></summary>
<div markdown="1">

1. 사장은 메뉴를 등록할 수 있다.
2. 사장은 메뉴에 속한 메뉴 옵션 그룹을 등록할 수 있다.
3. 사장은 메뉴 옵션 그룹에 속한 메뉴 옵션을 등록할 수 있다.
4. 사장은 메뉴, 메뉴 옵션 그룹, 메뉴 옵션을 수정 및 삭제할 수 있다.
5. 회원은 가게의 메뉴 목록을 조회할 수 있다.
6. 회원은 메뉴의 상세 정보를 조회할 수 있다.
</div>
</details>

<details>
<summary><h2>🧾 주문(Order)</h2></summary>
<div markdown="1">

1. 회원은 장바구니에 담긴 메뉴를 주문할 수 있다.
2. 회원은 주문 단계에서 결제수단을 선택할 수 있다.
3. 회원은 주문 단계에서 요청사항을 입력할 수 있다.
4. 회원은 총 주문 금액과 주문 내역을 확인할 수 있다.
5. 회원은 주문을 취소할 수 있다.
6. 주문이 완료되면 장바구니가 초기화된다.
7. 회원은 주문 내역을 조회할 수 있다.
8. 사장은 등록한 가게의 주문 내역을 조회할 수 있다.
9. 회원과 사장은 접수 대기 상태일 때 주문을 취소할 수 있다.
10. 사장은 회원의 주문을 접수할 수 있다.
11. 회원이 포장을 완료하면 사장은 주문을 확정할 수 있다.
12. 주문 상태는 접수 대기, 주문 취소, 주문 접수, 주문 확정으로 구분된다.

</div>
</details>

<details>
<summary><h2>🛒 장바구니(Cart)</h2></summary>
<div markdown="1">

1. 회원은 장바구니에 메뉴를 담을 수 있다.
2. 회원은 장바구니를 비울 수 있다.
3. 장바구니에 이미 담긴 메뉴와 동일한 메뉴를 선택한 수량만큼 갯수가 증가한다.
4. 회원은 장바구니에 담긴 메뉴를 주문할 수 있다.
5. 회원은 장바구니에 담긴 메뉴, 수량, 단가, 예상 결제 금액을 조회할 수 있다.
</div>
</details>
<details>
<summary><h2>🔑 인증(Authentication)</h2></summary>
<div markdown="1">

1. 일반 회원가입을 할 수 있다.
2. 사장 회원가입을 할 수 있다.
3. 회원 가입 시 이메일 인증을 할 수 있다.
4. 회원 가입 시 SMS 인증을 할 수 있다.
5. 로그인 / 로그아웃을 할 수 있다.
6. 가입된 회원만 서비스를 이용할 수 있다.

</div>
</details>
<br/>

# 📈 ERD 모델링
[![포장의 민족](https://github.com/Team-SNS/Pojang-BE/assets/78871184/86609aa5-4ec8-4c0f-836a-6313136cef18)](https://www.erdcloud.com/d/xroTBFytvBCr9fm5S)
<br/>
`사진 클릭시 이동`

# 📝 API명세서
> [📂API DOCS](https://robust-skunk-0f9.notion.site/API-0f59651871d44b36a32874c9f8b4f0e0?pvs=4)
<br/>

<details>
<summary><h1>📝 중점 기술 명세</h1></summary>
<div markdown="1">

  ## API명
  | 기술 영역 | 설명 |
  | --- | --- |
  | Front | 여기다가 도메인에 대한 기술 명세를 와바바바박 적어주면 될것 같습니다. </br>테스트|
  | Backend | 22222 |

<br/>

## API명
| 기술 영역 | 설명 |
| --- | --- |
| Front | 여기다가 도메인에 대한 기술 명세를 와바바바박 적어주면 될것 같습니다. |
| Backend | 22222 |

</div>
</details>
<br/>


# 테스트 결과
<details>
<summary><h2>😀 회원(Member)</h2></summary>
<div markdown="1">
<p align="center">
  <h4>회원가입</h4>
  <img src="https://github.com/Team-SNS/Pojang-FE/assets/112849147/ed03c100-6cbd-4e9a-80cf-3eb9871ae9e1">
</p>

<p align="center">
  <h4>로그인</h4>
  <img src="https://github.com/Team-SNS/Pojang-FE/assets/112849147/e42aa5f7-8e7f-4935-9be8-b771e4326c75">
</p>

  
</div>
</details>

<details>
<summary><h2>🏪 가게(Store)</h2></summary>
<div markdown="1">

<p align="center">
  <h4>검색 및 찜</h4>
  <img src="https://github.com/Team-SNS/Pojang-FE/assets/112849147/8936c5c4-06b9-4516-88d6-5c2c77c721e7">
</p>

<p align="center">
  <h4>매장 등록</h4>
  <img src="https://github.com/Team-SNS/Pojang-FE/assets/112849147/12092707-bb52-4497-be59-20fb10728c5d">
</p>

</div>
</details>

<details>
<summary><h2>📋 메뉴(Menu)</h2></summary>
<div markdown="1">

여기에 gif 넣기

</div>
</details>

<details>
<summary><h2>🧾 주문(Order)</h2></summary>
<div markdown="1">

<p align="center">
  <h4>주문등록</h4>
  <img src="https://github.com/Team-SNS/Pojang-FE/assets/112849147/4d4109bd-1539-44b6-b6ad-da6224f30124">
</p>

<p align="center">
  <h4>주문상태 변경 및 리뷰등록</h4>
  <img src="https://github.com/Team-SNS/Pojang-FE/assets/112849147/9e9b7aac-7e86-4cd3-8c7d-ff6dd4d98e2b">
</p>

<p align="center">
  <h4>리뷰조회</h4>
  <img src="https://github.com/Team-SNS/Pojang-FE/assets/112849147/2faf019a-4993-43b2-b68f-f2093ce83e8b">
</p>


</div>
</details>

<details>
<summary><h2>🛒 장바구니(Cart)</h2></summary>
<div markdown="1">

여기에 gif 넣기

</div>
</details>
