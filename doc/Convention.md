# Convention

# 1. 개요

코드를 최초로 작성한 사람이 끝까지 유지보수할 확률은 매우 낮다. 따라서, 최초 개발자가 아닌 사람도 코드를 빠르고 정확하게 이해할 수 있도록 작성하는 것은 코드 유지보수 비용을 절감하고 업무 효율을 높이는 데 결정적인 역할을 한다.

코딩 컨벤션을 준수하면 프로젝트 멤버 간 코드 공유도 쉬워지고, 일관성 있게 코드를 작성할 수 있다. "**통일된 기준으로 소스 코드를 작성하는 것이 중요하다.**"

---

# 2. JavaScript 코드 작성 규칙

📝참고 자료

[자바스크립트 스타일 가이드 - 코딩 컨벤션 편](https://velog.io/@cada/%EC%9E%90%EB%B0%94%EC%8A%A4%ED%81%AC%EB%A6%BD%ED%8A%B8-%EC%BD%94%EB%94%A9-%EB%B0%8F-%EB%84%A4%EC%9D%B4%EB%B0%8D-%EC%BB%A8%EB%B2%A4%EC%85%98-1%ED%8E%B8)

# 2.1 소스파일 기본 📰

- 소스파일의 이름은 **알파벳 소문자, 하이픈( - ), 밑줄( _ )** 으로만 작성한다.
- 소스파일의 확장자명은 **.js** 으로 작성한다.
- 소스파일의 인코딩은 **UTF-8** 으로 작성한다.
- 탭을 이용한 들여쓰기는 하지 않는다.

# 형식 ✔️

### 중괄호

- 중괄호는 하나의 구문만을 포함하고 있더라도 모든 제어문*(i.e. if, else, for, do, while..)*에 사용되어야 한다.
- if문의 경우, 하나의 구문만 포함할 경우, 같은 라인에 작성하고 중괄호를 생략할 수 있다.

```
// bad
if (someVeryLongCondition())
  doSomething();

// No
for (let i = 0; i < foo.length; i++) bar(foo[i]);

// Yes
if (shortCondition()) foo(); 
```

### 들여쓰기

- 새로운 블록이 나타날 때 마다 **두 칸의 공백**(스페이스바)를 통해 들여쓴다.
- 배열, 객체는 블록과 동일하게 들여쓴다.
- 클래스
    - 클래스 리터럴은 블록과 동일하게 들여쓴다.
    - 클래스의 각 메소드와 닫는 중괄호 다음에는 세미콜론을 사용하지 않는다.
    - 클래스의 상속 키워드로 @extends가 아닌 extends를 사용한다.
- 함수 표현식을 작성할 대에는 함수를 이전 레벨보다 2칸 들여써 작성한다.

- **점( . )**을 이용하여 긴 메소드 체인을 작성할 때는 각 메소드마다 들여쓴다.

```
// bad
$('#items').find('.selected').highlight().end().find('.open').updateCount();

// bad
$('#items').
  find('.selected').
    highlight().
    end().
  find('.open').
    updateCount();
```

# 변수 💾

- 한 줄에 하나의 변수를 선언한다.

    ```
    // bad
    let a = 1, b = 3;

    // good
    let a = 1;
    let b = 2;
    ```

# 배열 🏢

- 배열을 선언할 때는 Array 생성자가 아닌 **리터럴 구문**을 사용한다.

    ```
    // bad
    const items = new Array();

    // good
    const items = [];
    ```

- 배열에 값을 넣을 때는 **Array.push**를 사용한다.

```
const someStack = [];

// bad
someStack[someStack.length] = 'abracadabra';

// good
someStack.push('abracadabra');
```

# 객체 🎍

- 오브젝트를 선언할 때는 Object 생성자가 아닌 **리터럴 구문**을 사용한다.

    ```
    // bad
    const item = new Object();

    // good
    const item = {};
    ```

- 메소드 단축 구문을 사용한다.

```
// bad
const atom = {
  value: 1,

  addValue: function (value) {
    return atom.value + value;
  },
};

// good
const atom = {
  value: 1,

  addValue(value) {
    return atom.value + value;
  },
};
```

# 화살표 함수 (Arrow Function) ➡️

- 함수 바디가 하나의 식으로 구성된 경우, 중괄호와 return문을 생략할 수 있다.중괄호를 생략하지 않을 경우, **return**문을 포함시켜야 한다.

    ```
    // good
    [1, 2, 3].map(number => `A string containing the ${number}.`);

    // bad
    [1, 2, 3].map(number => {
      const nextNumber = number + 1;
      `A string containing the ${nextNumber}.`;
    });

    // good
    [1, 2, 3].map(number => {
      const nextNumber = number + 1;
      return `A string containing the ${nextNumber}.`;
    });
    ```

---

# 3. Vue.js 코드 작성 규칙

## 3.1. 필수 규칙

### 3.1.1. 컴포넌트 이름에 합성어 사용

root 컴포넌트인 `App` 과 `<transition>`, `<component>` 등 Vue에서 제공되는 빌트인 컴포넌트를 제외하고 컴포넌트의 이름은 항상 합성어를 사용한다.

모든 HTML 엘리먼트의 이름은 한 단어이기 때문에 합성어를 사용하는 것은 기존 그리고 향후 HTML 엘리먼트와의 **충돌을 방지해준다**.

bad eg.

```jsx
Vue.componet('todo', {
    // ...
})

``` js
exmport default {
    name : 'Todo',
    // ...
}
```

good eg.

```jsx
Vue.compoent('todo-item', {
    // ...
})

```` js
exmport default {
    name : 'TodoItem',
    // ...
}
```

### 3.1.2. 컴포넌트 데이터

컴포넌트의 `data` 는 반드시 함수여야 한다.

컴포넌트(eg. `new Vue` 를 제외한 모든곳)의 `data` 프로퍼티의 값은 반드시 객체 (object)를 반환하는 함수여야 한다.

bad eg.

```jsx
Vue.componet('some-comp', {
    data : {
        foo : 'bar'
    }
})

export default {
    data : {
        foo : 'bar'
    }
}
```

good eg.

```jsx
Vue.componet('some-comp', {
    data : function () {
        return {
            foo : 'bar'
        }
    }
})

// In a .vue file
export default {
    data () {
        return {
            foo : 'bar'
        }
    }
}

// It's OK to use an object directly in a root
// Vue instance, since only a single instance
// will ever exist.
new Vue({
    data : {
        foo : 'bar'
    }
})
```

### 3.1.3. Props 정의

Prop은 가능한 상세하게 정의되어야 합니다.

커밋 된 코드에서, prop 정의는 적어도 타입은 명시되도록 가능한 상세하게 정의되어야 한다.

bad eg.

```jsx
// This is only OK when prototyping
props : ['status']
```

good eg.

```jsx
props: {
  status: String
}

// Even better!
props: {
  status: {
    type: String,
    required: true,
    validator: function (value) {
      return [
        'syncing',
        'synced',
        'version-conflict',
        'error'
      ].indexOf(value) !== -1
    }
  }
}
```

### 3.1.4. v-for 에 key 지정

`v-for` 는 `key` 와 항상 함께 사용한다.

서브트리의 내부 컴포넌트 상태를 유지하기 위해 `v-for` 는 *항상* `key` 와 함께 요구된다. 비록 엘리먼트이긴 하지만 애니메이션의 객체 불변성과 같이 예측 가능한 행동을 유지하는 것은 좋은 습관이다.

bad eg.

```jsx
<ul>
  <li v-for="todo in todos">
    {{ todo.text }}
  </li>
</ul>
```

good eg.

```jsx
<ul>
  <li
    v-for="todo in todos"
    :key="todo.id"
  >
    {{ todo.text }}
  </li>
</ul>
```

### 3.1.5. v-if 와 v-for 를 동시에 사용하지 않는다.

`v-for` 가 사용된 엘리먼트에 절대 `v-if` 를 사용하지 앖는다.

사용 가능해 보이는 두 가지 경우:

- 리스트 목록을 필터링하기 위해서

    `v-for="user in users" v-if="user.isActive"` 이 경우 users를 새로운 computed 속성으로 필터링된 목록으로 대체 한다. `activeUsers`

- 숨기기 위해 리스트의 랜더링을 피핼 때

    `v-for="user in users" v-if="shouldShowUsers"` 이 경우 `v-if` 를 컨테이너 엘리먼트로 옮긴다. `ul` , `ol` 

bad eg.

```html
<ul>
  <li
    v-for="user in users"
    v-if="user.isActive"
    :key="user.id"
  >
    {{ user.name }}
  </li>
</ul>

<ul>
  <li
    v-for="user in users"
    v-if="shouldShowUsers"
    :key="user.id"
  >
    {{ user.name }}
  </li>
</ul>
```

good eg.

```html
<ul>
  <li
    v-for="user in activeUsers"
    :key="user.id"
  >
    {{ user.name }}
  </li>
</ul>

<ul v-if="shouldShowUsers">
  <li
    v-for="user in users"
    :key="user.id"
  >
    {{ user.name }}
  </li>
</ul>
```

### 3.1.6. 컴포넌트 스타일 스코프

For applications, styles in a top-level `App` component and in layout components may be global, but all other components should always be scoped.

This is only relevant for single-file components. It does not require that the scoped attribute be used. Scoping could be through CSS modules, a class-based strategy such as BEM, or another library/convention.

Component libraries, however, should prefer a class-based strategy instead of using the scoped attribute.

This makes overriding internal styles easier, with human-readable class names that don’t have too high specificity, but are still very unlikely to result in a conflict.

bad eg.

```html
<template>
  <button class="btn btn-close">X</button>
</template>

<style>
.btn-close {
  background-color: red;
}
</style>
```

good eg.

```html
<template>
  <button class="button button-close">X</button>
</template>

<!-- Using the `scoped` attribute -->
<style scoped>
.button {
  border: none;
  border-radius: 2px;
}

.button-close {
  background-color: red;
}
</style>

<template>
  <button :class="[$style.button, $style.buttonClose]">X</button>
</template>

<!-- Using CSS modules -->
<style module>
.button {
  border: none;
  border-radius: 2px;
}

.buttonClose {
  background-color: red;
}
</style>

<template>
  <button class="c-Button c-Button--close">X</button>
</template>

<!-- Using the BEM convention -->
<style>
.c-Button {
  border: none;
  border-radius: 2px;
}

.c-Button--close {
  background-color: red;
}
</style>
```

### 3.1.7. Private 속성 이름

플러그인, mixin 등에서 커스텀 사용자 private 프로터피에는 항상 접두사 `$_` 를 사용하라. 그 다음 다른 사람의 코드와 충돌을 피하려면 named scope를 포함하라. (e.g. *`&_yourPluginName_`* ).

bad eg.

```jsx
var myGreatMixin = {
  // ...
  methods: {
    update: function () {
      // ...
    }
  }
}

var myGreatMixin = {
  // ...
  methods: {
    _update: function () {
      // ...
    }
  }
}

var myGreatMixin = {
  // ...
  methods: {
    $update: function () {
      // ...
    }
  }
}

var myGreatMixin = {
  // ...
  methods: {
    $_update: function () {
      // ...
    }
  }
}
```

good eg.

```jsx
var myGreatMixin = {
  // ...
  methods: {
    $_myGreatMixin_update: function () {
      // ...
    }
  }
}

// Even better!
var myGreatMixin = {
  // ...
  methods: {
    publicMethod() {
      // ...
      myPrivateFunction()
    }
  }
}

function myPrivateFunction() {
  // ...
}

export default myGreatMixin
```

---

# 4. Java 코드 작성 규칙

## 4.1. 네이밍 규칙 (카멜 표기법 사용)

네이밍 규칙의 식별자 타입 6가지 :

- Packages
- Classes
- Interfaces
- Methods
- Variables
- Constants

### 4.1.1. Packages

- 패키지 이름의 최상위 레벨은 항상 ASCII 문자에 포함되어 있는 소문자로 쓰고, 가장 높은 레벨의 도메인 이름 중 하나이어야 한다.
- 현재는 com, edu, gov, mil, net, org, 또는 1981년 ISO Standard 316에 명시된 영어 두 문자로 표현되는 나라 구별 코드가 사용된다.
- 패키지 이름의 나머지 부분은 조직 내부의 명명 규칙을 따르면 된다. 이러한 규칙을 따라 만들어진 이름은 디렉토리 구조에서 디렉토리 이름으로도 사용된다.
- 예를 들면 부서명, 팀명, 프로젝트명, 컴퓨터 이름, 또는 로그인 이름 등이다.

eg. com.sun.eng

### 4.1.2. Classes

- 클래스 이름은 명사이어야 하며, 복합 단어일 경우 각 단어의 **첫 글자는 대문자**이어야 한다.
- 클래스 이름은 간단하고 명시적으로 작성해야 한다.
- 완전한 단어를 사용하고 두 문자어와 약어는 피하도록 한다(만약, 약어가 URL이나 HTML과 같이 더 많이 ,더 넓게 사용되고 있다면 약어를 사용하는 것도 괜찮다).

eg. class ImageSprite;

### 4.1.3. Interfaces

- 인터페이스 이름도 클래스 이름과 같은 **대문자 사용 규칙**을 적용해야 한다.

eg. interface RasterDelegate;

### 4.1.4. Methods

- 메서드의 이름은 동사이어야 하며, 복합 단어일 경우 **첫 단어는 소문자로 시작**하고 그 이후에 나오는 단어의 첫 문자는 대문자로 사용해야 한다.

eg. runFast();

### 4.1.5. Variables

- 변수 이름의 첫 번째 문자는 소문자로 시작하고, 각각의 내부 단어의 첫 번째 문자는 대문자로 시작해야 한다.
- 변수 이름이 언더바 (_) 또는 달러 표시 문자로 시작하는 것이 허용되기는 하지만, 이 문자들로 시작하지 않도록 주의하자.
- 이름은 짧지만 의미 있어야 한다.
- 이름의 선택은 그 변수의 사용 의도를 알아낼 수 있도록 의미적이어야 한다.
- 한 문자로만 이루어진 변수 이름은 암시적으로만 사용하고 버릴 변수일 경우를 제외하고는 피해야 한다.
- 보통의 **임시 변수**들의 이름은 **integer 일 경우에는 i, j, k, m, n**을 사용하고, **character 일 경우에는 c, d, e**를 사용한다.

eg. float myWidth;

### 4.1.6. Constants (상수)

- 클래스 상수로 선언된 변수들과 ANSI 상수들의 이름은 모두 대문자로 쓰고 각각의 단어는 언더바("_")로 분리 해야 한다.
- 디버깅을 쉽게 하기 위해서 ANSI 상수들의 사용은 자제하도록 한다.

eg. static final int MIN_WIDTH = 4;

## 4.2. 파일 구조

[4.2.1. 파일 확장자](Convention%2002826aa663a248c79c3c691da1313ec6/4%202%201%20%E1%84%91%E1%85%A1%E1%84%8B%E1%85%B5%E1%86%AF%20%E1%84%92%E1%85%AA%E1%86%A8%E1%84%8C%E1%85%A1%E1%86%BC%E1%84%8C%E1%85%A1%20aaef8b6740f94572872676fea477ca8a.csv)

### 4.2.2. 자바 소스 파일

자바 소스 파일은 다음과 같은 순서를 가진다.

1. Package 문과 Import 문
2. 클래스와 인터페이스 선언

### 4.2.2.1. Package 문과 Import 문

package 문 다음에 import 문이 뒤따라온다.

주의: 첫번째 레벨의 도메인 이름들(com, edu, gov, mi, net, org) 중 하나이어야 한다.

### 4.2.2.2. Class 와 Interfasce 선언 : 순서

클래스 변수 ⇒ 인스턴스 변수 ⇒ 생성자 ⇒ (public - 관련된 private ) 반복

```java
class Car{
    //클래스 변수
    private final static int MAX_NUM = 10;
    private final static int FORWARD_NUM = 4; 
    
    //인스턴스 변수
    private String name;
    private int position;
 
    //생성자
    public Car(final String name){
        this.car = car;
    }
 
    //public 
    public void move(){
        if(getRandomNum() > FORWARD_NUM){ 
            position++;            
        }
    }
 
    //private (public과 관련된 private)
    private int getRandomNum(){
        Random random = new Random();
        return random.nextInt(10);
    }
 
    //이후에는 public / private 반복
    //제일 밑에는 equals, hashcode, toString 등을 
}
```

## 4.3. 들여쓰기

**4개의 빈 칸(space)을 들여쓰기 단위**로 사용한다.

### 4.3.1. 줄 나누기

- 콤마 후에 두 줄로 나눈다.
- 연산자(operator) 앞에서 두 줄로 나눈다.
- 레벨이 낮은 원칙 보다는 레벨이 높은 원칙에 따라 두 줄로 나눈다.
- 앞줄과 같은 레벨의 식(expression)이 시작되는 새로운 줄은 앞줄과 들여쓰기를 일치시킨다.
- 만약 위의 원칙들이 코드를 더 복잡하게 하거나 오른쪽 끝을 넘어간다면, 대신에 8개의 빈 칸을 사용해 들여쓴다.

eg.

```java
someMethod(longExpression1, longExpression2, longExpression3, 
        longExpression4, longExpression5);
  
var = someMethod1(longExpression1,
                someMethod2(longExpression2,
                        longExpression3)
```

```java
longName1 = longName2 * (longName3 + longName4 - longName5)
           + 4 * longname6; // 될 수 있으면 이 방법을 사용한다.
  
longName1 = longName2 * (longName3 + longName4
                       - longName5) + 4 * longname6; // 될 수 있으면 피한다.
```

```java
// 일반적인 들여쓰기
someMethod(int anArg, Object anotherArg, String yetAnotherArg,
           Object andStillAnother) {
    ...
}
  
// 너무 멀리 들여쓰는 것을 피하기 위해 8개의 빈 칸으로 들여쓰기
private static synchronized horkingLongMethodName(int anArg,
        Object anotherArg, String yetAnotherArg,
        Object andStillAnother) {
    ...
}
```

```java
// 아래와 같은 들여쓰기는 사용하지 말자.
if ((condition1 && condition2)
    || (condition3 && condition4)
    ||!(condition5 && condition6)) { // 좋지 않은 들여쓰기
    doSomethingAboutIt();            // 메서드 본문 시작이 명확하지가 않다.
} 
  
// 대신에 아래와 같은 들여쓰기를 사용한다.
if ((condition1 && condition2)
        || (condition3 && condition4)
        ||!(condition5 && condition6)) {
    doSomethingAboutIt();
} 
  
// 또는 아래와 같은 들여쓰기를 사용한다.
if ((condition1 && condition2) || (condition3 && condition4)
        ||!(condition5 && condition6)) {
    doSomethingAboutIt();
}
```

```java
alpha = (aLongBooleanExpression) ? beta : gamma;  
  
alpha = (aLongBooleanExpression) ? beta
                                 : gamma;  
  
alpha = (aLongBooleanExpression)
        ? beta 
        : gamma;
```

## 4.4. 주석

자바 주석 :

- 구현 주석  :
    - 블록(block) 주석
    - 한줄(single-line) 주석
    - 꼬리(trailing) 주석
    - 줄 끝(end-of-line) 주석
- 문서화 주석

### 4.4.1. 블록(block) 주석

블록 주석은 파일, 메서드, 자료 구조, 알고리즘에 대한 설명을 제공할 때 사용된다. 블록 주석은 각각의 파일이 시작될 때와 메서드 전에 사용된다. 또한 메서드 안에서와 같이 다른 장소에서 사용될 수도 있다. 메서드 안에 존재하는 블록 주석은 주석이
설명하는 코드와 같은 단위로 들여쓰기를 해야 한다.

블록 주석은 다른 코드들과 구분하기 위해서 처음 한 줄은 비우고 사용한다.

eg.

```java
/*
 * 여기에 블록 주석을 작성한다.
 */

/*-
 * 여기에 들여쓰기를 무시하는 특별한
 * 블록 주석을 작성한다.
 *
 *    one
 *        two
 *            three
 */
```

### 4.4.2. 한줄(single-line) 주석

짧은 주석은 뒤따라 오는 코드와 같은 동일한 들여쓰기를 하는 한 줄로 작성할 수 있다. 만약 주석이 한 줄에 다 들어가지 않는다면, 블록 주석 형식을 따라야 한다.

eg.

```java
if (condition) {
  
    /* 이 조건을 만족하면 실행된다. */
    ...
}
```

### 4.4.3. 꼬리(trailing) 주석

아주 짧은 주석이 필요한 경우 주석이 설명하는 코드와 같은 줄에 작성한다. 하지만 실제 코드와는 구분될 수 있도록 충분히 멀리 떨어뜨려야 한다.

eg.

```java
if (a == 2) {
    return TRUE;            /* 특별한 경우 */
} else {
    return isPrime(a);      /* a 가 홀수인 경우 */
}
```

### 4.4.4. 줄 끝(end-of-line) 주석

주석 기호 // 는 한 줄 모두를 주석 처리하거나 한 줄의 일부분을 주석 처리해야 할 때 사용할 수 있다. 긴 내용을 주석에 포함하기 위해서 연속되는 여러 줄에 이 주석을 사용하는 것은 안되지만, 어떤 코드의 일부분을 주석 처리하기 위해서 여러 줄에 연속해서 사용하는 것은 허락된다.

eg.

```java
if (foo > 1) {
  
    // double-flip을 실행한다.
    ...
}
else {
    return false;          // 이유를 여기에 설명한다.
}
//if (bar > 1) {
//
//    // double-flip을 실행한다.
//    ...
//}
//else {
//    return false;
//}
```

### 4.4.5. 문서화(Documentation) 주석

문서화 주석은 자바 클래스, 인터페이스, 생성자, 메서드 그리고 필드들을 설명한다. 각각의 문서화 주석은 주석 경계 기호인 /**…*/ 안으로 들어간다. 그리고, 각각의 문서화 주석은 클래스, 인터페이스 그리고 멤버 당 하나씩 가진다. 문서화 주석은 선언 바로 전에 나와야 한다.

eg.

```java
/**
 * Example 클래스는 ...
 */
public class Example { ...
```

---

# 5. Python 코드 작성 규칙

PEP8 (python coding comvention) 을 따르자.

## 5.1. 화이트스페이스(whitespace)

파이썬에서 화이트스페이스 공백 는 문법적으로 의미가 있다. 파이썬 프로그래머는 특히 코드의 명료성 때문에 화이트스페이스의 영향에 민감한 편이다.

- 탭이 아닌 스페이스로 들여쓴다.
- 문법적으로 의미 있는 들여쓰기는 각 수준마다 **스페이스 네 개**를 사용한다.
- 한 줄의 문자 길이가 79자 이하여야 한다.
- 표현식이 길어서 다음 줄로 이어지면 일반적인 들여쓰기 수준에 추가로 스페이스 네 개를 사용한다.
- 파일에서 **함수와 클래스는 빈 줄 두 개**로 구분해야 한다.
- 클래스에서 **메서드는 빈 줄 하나**로 구분해야 한다.
- 리스트 인덱스, 함수 호출 키워드 인수 할당에는 스페이스를 사용하지 않는다.
- 변수 할당 앞뒤에 스페이스를 하나만 사용한다.

## 5.2. 네이밍 규칙

PEP 8은 언어의 부분별로 독자적인 명명 스타일을 제안한다. 이 스타일을 따르면 코드를 읽을 때 각 이름에 대응하는 타입을 구별하기 쉽다.

- 함수 변수 속성은 lowercase_underscore 형식을 따른다.
- 보호(protected)인스턴스 속성은 _leading_underscore 형식을 따른다.
- 비공개(private)인스턴스 속성은 __double_leading_underscore 형식을 따른다.
- 클래스와 예외는 CapitalizedWord 형식을 따른다.
- 모듈 수준 상수는 ALL_CAPS 형식을 따른다.
- 클래스의 인스턴스 메서드에서는 첫 번째 파라미터 (해당 객체를 참조)의 이름을 self로 지정한다.
- 클래스 메서드에서는 첫 번째 파라미터 (해당 클래스를 참조)의 이름을 cls로 지정한다.

## 5.3. 표현식과 문장

파이썬의 계명(The Zen of Python)에는 "어떤 일을 하는 확실한 방법이 (될 수 있으면 하나만) 있어야 한다." 는 표현이 있다. PEP 8은 표현식과 문장의 본보기로 이 스타일을 정리하고 있다.

- 긍정 표현식의 부정(if not a is b) 대신에 인라인 부정(if a is not b)을 사용한다.
- 길이를 확인(if len(somelist) = = 0)하여 빈 값([] 또는 '') 을 확인하지 않는다. if not somelist를 사용하고, 빈 값은 암시적으로 False가 된다고 가정한다.
- 비어 있지 않은 값([1] 또는 'hi') 에도 위와 같은 방식이 적용된다. 값이 비어 있지 않으면 if somelist 문이 암시적으로 True가 된다.
- 한 줄로 된 if 문, for와 while 루프, except 복합문을 쓰지 않는다. 이런 문장은 여러 줄로 나눠서 명료하게 작성한다.
- 항상 파일의 맨 위에 import 문을 놓는다.
- 모듈을 임포트할 때는 항상 모듈의 절대 이름을 사용하며 현재 모듈의 경로를 기준으로 상대 경로로 된 이름을 사용하지 않는다. 예를 들어 bar 패키지의 foo 모듈을 임포트하려면 그냥 import foo 가 아닌 from bar import foo 라고 해야 한다.
- 상대적인 임포트를 해야 한다면 명시적인 구문을 써서 from . import foo 라고 한다.
- 임포트는 '표준 라이브러리 모듈, 서드파티 모듈, 자신이 만든 모듈' 섹션 순으로 구분해야 한다. 각각의 하위 섹션에서는 알파벳 순서로 임포트한다.

---

# 6. Database 작성 규칙

### 6.1. **공통**

1. 소문자를 사용한다.
2. 단어를 임의로 축약하지 않는다.
3. register_date (O) / reg_date (X)
4. 가능하면 약어의 사용을 피한다.
5. 약어를 사용해야 하는 경우, 약어 역시 소문자를 사용한다.
6. 동사는 능동태를 사용한다.
7. register_date (O) / registered_date (X)

### 6.2. **TABLE**

1. 복수형을 사용한다.
2. 이름을 구성하는 각각의 단어를 **underscore** 로 연결하는 **snake case** 를 사용한다.
3. 교차 테이블 (many-to-many) 의 이름에 사용할 수 있는 직관적인 단어가 있다면 해당 단어를 사용한다.
4. 적절한 단어가 없다면 relationship을 맺고 있는 각 테이블의 이름을 "*and*" 또는 "*has*" 로 연결한다.

예:

1. articles, movies : 복수형vip_members : 약어도 예외 없이 소문자 & 단어의 연결에 underbar를 사용articles_and_movies : 교차 테이블을 "*and*" 로 연결

### 6.3. **COLUMN**

1. auto increment 속성의 PK를 대리키로 사용하는 경우, "테이블 이름의 단수형"_id 의 규칙으로 명명한다.
2. 이름을 구성하는 각각의 단어를 **underscore** 로 연결하는 **snake case** 를 사용한다.
3. foreign key 컬럼은 부모 테이블의 primary key 컬럼 이름을 그대로 사용한다.
4. self 참조인 경우, primary key 컬럼 이름 앞에 적절한 접두어를 사용한다.같은 primary key 컬럼을 자식 테이블에서 2번 이상 참조하는 경우, primary key 컬럼 이름 앞에 적절한 접두어를 사용한다.
5. boolean 유형의 컬럼이면 "_flag" 접미어를 사용한다.
6. date, datetime 유형의 컬럼이면 "_date" 접미어를 사용한다.

예:

1. article_id, movie_id : "테이블 이름의 단수형" + "_id"complete_flag : boolean 유형의 컬럼issue_date : 날짜 유형의 컬럼

### 6.4. **INDEX**

1. 이름을 구성하는 각각의 단어를 **hyphen** 으로 연결하는 **snake case** 를 사용한다.
2. 접두어
3. unique index : uixspatial index : sixindex : nix
4. "접두어"-"테이블 이름"-"컬럼 이름"-"컬럼 이름"

예:

1. uix-accounts-login_email

### 6.5. **FOREIGN KEY**

1. 이름을 구성하는 각각의 단어를 **hyphen** 으로 연결하는 **snake case** 를 사용한다.
2. "fk"-"부모 테이블 이름"-"자식 테이블 이름"
3. 같은 부모-자식 테이블에 2개 이상의 foreign key가 있는 경우, numbering합니다.

예:

1. fk-movies-articles : article 테이블이 movie 테이블을 참조fk-admins-notices-1 / fk-admins-notices-2 : notices 테이블이 admins 테이블을 2회 이상 참조하여 numbering

### 6.6. **VIEW**

1. 접두어 "v"를 사용한다.
2. 기타 규칙은 TABLE과 동일

예:

1. v_privileges

**Stored Procedure**

**[2018/06/01 - [MySQL/Stored Procedure] - [MySQL/Stored Procedure] 명명 규칙](http://purumae.tistory.com/194)**

### 6.7. **FUNCTION**

1. 접두어 "usf"를 사용한다.
2. 이름을 구성하는 각각의 단어를 **underscore** 로 연결하는 **snake case** 를 사용한다.

예:

1. usf_random_key

### 6.8. **TRIGGER**

1. 이름을 구성하는 각각의 단어를 **underscore** 로 연결하는 **snake case** 를 사용한다.
2. 접두어
3. tra : AFTER 트리거trb : BEFORE 트리거
4. "접두어"*"테이블 이름"*"트리거 이벤트"

예:

1. tga_movies_ins : AFTER INSERT 트리거tga_movies_upd : AFTER UPDATE 트리거tgb_movies_del : BEFORE DELETE 트리거

![Convention%2002826aa663a248c79c3c691da1313ec6/Untitled.png](Convention%2002826aa663a248c79c3c691da1313ec6/Untitled.png)