# Sub-PJT2 `냉장고를 Vue 탁해` 

## 1. 프로젝트 소개 :smiley:

### 프로젝트 이름

<img src="https://user-images.githubusercontent.com/60081201/88865312-83417100-d242-11ea-9cc2-c83c84303b21.png" alt="logo png" style="zoom:50%;" />

### 프로젝트 소개 

`냉장고를 Vue탁해 프로젝트`는 **사용자가 등록한 재료를 활용하여 맞춤 레시피를 제공하는 웹 블로그**입니다.

핵심 기능은 다음과 같습니다.

- 사용자가 자신의 냉장고 재료들을 등록하면 재료 데이터를 기반으로 맞춤 레시피를 제공합니다.
- 자신만의 요리 레시피 글을 작성할 때 `냉장고를 vue탁해`에서 제공하는 에디터를 통해 쉽고 즐겁게 등록할 수 있습니다.

### 사용 언어, 기술 스택

<img src="https://img.shields.io/badge/platform-Web-yellow" alt="기술스택" style="zoom:120%;" /><img src="https://img.shields.io/badge/framework-Vue.js-green" alt="기술스택" style="zoom:120%;" /><img src="https://img.shields.io/badge/database-MySQL-yellowgreen" alt="기술스택" style="zoom:120%;" /><img src="https://img.shields.io/badge/backend-Springboot-ff69b4" alt="기술스택" style="zoom:120%;" /><img src="https://img.shields.io/badge/server-AWS-9cf" alt="기술스택" style="zoom:120%;" /><img src="https://img.shields.io/badge/language-Java, JavaScript, Python-important" alt="기술스택" style="zoom:120%;" />



## 2. 프로젝트 사용법 :key:

### 로컬 웹 서버 실행 방법

#### Frontend

```bash
cd frontvuetakhae
yarn install
yarn add vue bootstrap-vue bootstrap
yarn serve
```

#### Backend

```bash
cd ~/app/backend/
./run.sh start
```

#### Brower Support

호환 브라우저는 다음과 같습니다.

| chrome<br /><img src="https://user-images.githubusercontent.com/60081201/88929957-cab51500-d2b5-11ea-98aa-013452b4eec4.PNG" alt="chrome" style="zoom: 25%;" /> | firefox<br /><img src="https://user-images.githubusercontent.com/60081201/88929960-cbe64200-d2b5-11ea-8399-c54eac3ea214.PNG" alt="firefox" style="zoom: 25%;" /> |
| :----------------------------------------------------------: | :----------------------------------------------------------: |
|                            Latest                            |                            Latest                            |



## 3. 프로젝트 설명 :clipboard:

### 파일 목록

```markdown
...
├─node_modules
├─public
└─src
    ├─assets
    │  └─img
    │      └─toolbar
    ├─components
    │  └─common
    │      └─Header.vue	
    ├─lib
    │  └─constants.js
    ├─page
    │  ├─post
    │  |   ├─CreatePost.vue	
    │  |   ├─DetailPost.vue	
    │  |   └─List.vue	
    │  └─user
    │      └─MyPage.vue	
    ├─router
    |  └─index.js
    ├─App.vue 
    └─main.js
├─.gitignore
├─babel.config.js
├─package.json
├─package-lock.json
├─yarn.lock
└─README.md
```

#### 파일 정보

- `src`
  - `assets` : 이미지 등 어플리케이션에서 사용되는 파일들이 모여 있는 디렉터리
  - `components` : Vue 컴포넌트들이 모여 있는 디렉터리

- `main.js` : Vue 인스턴스(new Vue()) 생성, 가장 먼저 실행되는 javascript 파일

- router/`index.js`: vue-router에 사용될 라우터를 정의한 파일

  ---

- `App.vue` : 가장 최상위 컴포넌트, 가장 먼저 마운팅
- `Header.vue` : 헤더에 대한 컴포넌트
  
  - 사용자의 회원가입, 로그인, 로그아웃의 역할을 담당하고 로그인 유무에 따른 헤더 변화를 제공한다.
- page/user/`MyPage.vue` : 로그인 후에 들어갈 수 있는 컴포넌트
  
  - 사용자의 나의 냉장고 재료 담기, 관심 레시피, 작성한 레시피 목록과 같은 사용자에 대한 정보를 볼 수 있다.
- page/post/`CreatePost.vue` : 레시피 글 작성하기 컴포넌트
  - 사용자의 나의 냉장고의 데이터를 통해 쉽게 재료 입력이 가능하다.
  - 과정 입력 단계에서는 차별화된 에디터를 통해 요리 순서 템플릿을 제공한다.
- page/post/`DetailPost.vue` : 레시피 게시글 클릭 시 보이는 컴포넌트
  - 레시피 게시글에 대한 자세한 정보를 볼 수 있다.
  - 좋아요, 댓글 기능을 제공한다.
- page/post/`List.vue` : 메인 홈 화면을 담당하는 컴포넌트
  - 레시피 검색 기능을 제공한다.
  - 인기순 레시피를 카드 형식으로 보여준다.

### ERD

![ERD](https://user-images.githubusercontent.com/60081201/88983004-aaf90d80-d304-11ea-9097-cb083397c008.png)

### 와이어 프레임

![와이어프레임1](https://user-images.githubusercontent.com/60081201/88252944-01ee5980-cceb-11ea-9d48-5c899841fdec.PNG)



![와이어프레임7](https://user-images.githubusercontent.com/60081201/88254814-644a5880-ccf1-11ea-8349-28119bb2d55c.PNG)

### 개발한 페이지/기능 소개

#### 홈 페이지

<img src="https://user-images.githubusercontent.com/60081201/88983478-1b545e80-d306-11ea-91d7-ccc2170e9f5c.PNG" alt="Homepage" style="zoom: 50%;" />

- 검색창을 통해 원하는 레시피를 검색가능하다. (개발중)
- 인기순으로 레시피를 보여준다.



#### 마이페이지

<img src="https://user-images.githubusercontent.com/60081201/88986920-498a6c00-d30f-11ea-84b5-899161e9edb4.png" alt="마이페이지" style="zoom:50%;" />

- 자기소개와 팔로잉, 팔로우 수가 표시된다.
- 나의 냉장고 블럭을 통해 냉장고에 있는 재료를 저장한다.
- 작성한 레시피 목록, 관심 레시피 목록을 볼 수 있다.



#### 게시물 작성 페이지 

<img src="https://user-images.githubusercontent.com/60081201/88986781-e0a2f400-d30e-11ea-90ce-41e2d5c9180f.png" alt="요리 작성페이지" style="zoom:50%;" />

- 나의 냉장고에 있는 재료를 드래그앤드롭으로 재료입력칸(요리에 필요한 재료)에 옮길 수 있다.
- 제목, 재료입력, 과정입력, 난이도, 소요시간, 후기, 대표 사진을 넣을 수 있다. 



#### 게시물 디테일 페이지 

<img src="https://user-images.githubusercontent.com/60081201/88986779-ded93080-d30e-11ea-9270-fdb52c5948c8.png" alt="디테일 페이지" style="zoom:50%;" />

- 게시물을 클릭했을 때 보이는 화면으로 게시물에 대한 내용을 상세하게 보여준다.
- 댓글 입력이 가능하다.
- 좋아요 기능을 통해 마이페이지에서 관심 레시피에 저장된다.



## 4. 기타

### 브랜치 전략

저희 팀의 브랜치 전략은 기능별로 나누는 것입니다. 브랜치 이름은 앞부분에는 front, back을 명시하고 개발할 기능을 뒤에 작성하여 만들었습니다.

- 예시 형태
  - front/{Front 의 담당 기능}
    - ex) front/signin
  - back/{BackEnd의 담당 기능}



### 개발일정 :calendar: 

![일정](https://user-images.githubusercontent.com/60081201/88985715-01b61580-d30c-11ea-8e3a-9a133759ee44.PNG)



### 팀원소개 :school:

<img src="https://user-images.githubusercontent.com/60081201/88927797-ecf96380-d2b2-11ea-8347-550fe89453cb.PNG" alt="전자파컴공레인저표지" style="zoom: 33%;" />



### 유용한 사이트 정리 :pushpin: 

- 뱃지를 커스터마이징하여 기술스택 표현하는 사이트

  > https://shields.io/
  >
  > - https://img.shields.io/badge/<LABEL>-<MESSAGE>-<COLOR>
  >
  >   다음과 같이 LABEL, MESSAGE, COLOR 순서로 입력하면 된다.

- ERD 작성하는 사이트

  > https://www.erdcloud.com/
  >
  > - 팀으로 생성하면 ERD 수정, 공유 가능하다.