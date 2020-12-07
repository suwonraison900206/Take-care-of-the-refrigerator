<template>
  <div class="container" style="margin-top:30px">
    <h1>{{ userData.nickname }}님의 마이 페이지</h1>
    <div class="header">
      <div class="box" style="background: #BDBDBD;">
        <img
          class="profile"
          src="https://img1.daumcdn.net/thumb/R720x0/?fname=https://t1.daumcdn.net/news/201904/19/moneytoday/20190419141606693hahz.jpg"
        />
      </div>
      <div class="introduce">
        <h3 style="text-align:left">
          팔로잉 {{ userData.followingCount }} 명 / 팔로우
          {{ userData.followerCount }} 명
        </h3>
        <h2 style="text-align:left">자기소개</h2>
        <p>
          <v-textarea name="input-7-4" label="간단하게 자신에 대해 소개해주세요." v-model="userData.introduce"></v-textarea>
          <v-btn depressed small @click="updateIntroduce">저장</v-btn>
        </p>
      </div>
    </div>
    <!-- 나의 냉장고 코드 -->
    <div class="middle">
      <h1>나의 냉장고</h1>

      <div>
        <v-row class="m-2 inputBlank" variant="danger">
          <v-text-field
            label="냉장고 속 재료를 추가해주세요."
            v-model="addText"
            hide-details="auto"
            v-on:keyup.enter="plusFood()"
          ></v-text-field>
          <v-icon large @click="plusFood()">mdi-plus</v-icon>
        </v-row>
        <v-chip
          class="m-1"
          v-for="tag in chips"
          close
          @click:close="closeChip(tag)"
          :key="tag"
        >{{ tag }}</v-chip>

        <div v-if="emptyChip">냉장고 속 요리 재료를 입력해주세요.</div>
      </div>
    </div>
    <div class="interest">
      <h1>관심 레시피</h1>
      <p>연어 킹의 연어 덮밥</p>
      <p>진주새럼의 진주비빔밥</p>
    </div>
    <hr />
    <div class="interest">
      <h1>내가 작성한 레시피 목록</h1>
      <!-- <p>{{ userData.myBoards }}</p> -->
      <li v-for="board in userData.myBoards" :key="board">
        {{ board.boardId }} : {{ board.title }}
        {{ board.createAt }}
      </li>
    </div>
    <div class="white--text">
      <b-button class="bottom-button mr-2" @click="moveCreatePost">레시피 작성하기</b-button>
      <b-button
        class="a_tag_modal bottom-button mr-2"
        @click="modalShow = !modalShow; loadData();"
      >수정하기</b-button>
      <b-button class="bottom-button mr-2" variant="danger">탈퇴하기</b-button>
    </div>

    <!-- 개인 정보 수정하기 모달 코드 -->
    <b-modal v-model="modalShow" hide-footer hide-header>
      <h1 class="text-center">{{ userData.uid }}개인 정보 수정하기</h1>
      <div class="container ml-5">
        <div class="div_item">
          <div>
            <span class="item_100px">닉네임</span>
            <input
              class="ml-4 pl-2"
              style="border: 1px solid #000000; background-color: #e2e2e2;"
              :value="userupdateData.nickname"
              @input="userupdateData.nickname = $event.target.value"
              type="text"
            />
            <b-button class="ml-2" size="sm" @click="nameCheck" variant="info">중복확인하기</b-button>
          </div>
          <p class="small ml-4 pl-5" v-if="nicknameCheck">사용가능한 닉네임입니다.</p>
        </div>
        <div class="div_item">
          <span class="item_100px">비밀번호</span>
          <input
            class="ml-2"
            style="border: 1px solid #000000; background-color: #e2e2e2;"
            @input="userData.password = $event.target.value"
            placeholder="새로운 비밀번호를 입력해주세요."
            type="password"
          />
        </div>
      </div>
      <div>
        <b-button class="mt-3 d-flex justify-content-center" @click="updateData">저장하기</b-button>
      </div>
    </b-modal>
  </div>
</template>

<script>
import axios from "axios";
const BACK_URL = "http://i3a305.p.ssafy.io:8399/api";

export default {
  data() {
    return {
      chips: [], // 마이페이지에 입력한 나의 냉장고 데이터를 넣기
      modalShow: false,
      addText: "",
      emptyChip: false,
      userData: {
        nickname: "",
        introduce: "",
        box: "",
        followingCount: "",
        followerCount: "",
        myBoards: [],
        interestBoards: [],
      },
      userupdateData: {
        // 개인정보 수정할 때 필요한 정보
        password: "",
        nickname: "",
      },
      nicknameCheck: false,
    };
  },
  created() {
    axios
      .get(`${BACK_URL}/users/mypage`, {
        headers: { "jwt-auth-token": this.$cookies.get("token") },
      })
      .then((response) => {
        console.log(response);
        this.userData.nickname = response.data.mypage.nickname;
        this.userData.introduce = response.data.mypage.introduce;
        this.userData.box = response.data.mypage.box;
        this.userData.followingCount = response.data.mypage.followingCount;
        this.userData.followerCount = response.data.mypage.followerCount;
        this.userData.myBoards = response.data.mypage.myBoards;
        this.userData.interestBoards = response.data.mypage.interestBoards;
        this.chips = this.userData.box.split(",");
      });
  },
  methods: {
    updateIntroduce() {
      // 자기소개 수정 method

      axios
        .put(
          `${BACK_URL}/users/mypage/introduce`,
          {
            introduce: this.userData.introduce,
          },
          {
            headers: { "jwt-auth-token": this.$cookies.get("token") },
          }
        )
        .then((response) => {
          console.log(response);
          if (response.status === 202) {
            alert("자기소개가 수정되었습니다!");
          }
        })
        .catch((error) => {
          alert(error);
        });
    },
    loadData() {
      // 개인 정보 수정 모달 클릭시 저장된 user 데이터 가져오는 method
      axios
        .get(`${BACK_URL}/users/info`, {
          headers: { "jwt-auth-token": this.$cookies.get("token") },
        })
        .then((response) => {
          console.log(response);
          this.userupdateData.nickname = response.data.nickname;
        })
        .catch((error) => {
          alert(error);
        });
    },
    updateData() {
      // 개인정보 수정 모달을 통해 수정하는 method
      if (this.nicknameCheck === true) {
        if (this.userupdateData.password === "") {
          // 닉네임만 변경할 경우
          console.log(this.userupdateData.nickname);
          axios
            .put(
              `${BACK_URL}/users/info`,
              {
                nickname: this.userupdateData.nickname,
              },
              {
                headers: { "jwt-auth-token": this.$cookies.get("token") },
              }
            )
            .then((response) => {
              console.log(response);
              if (response.status === 202) {
                alert("회원정보가 수정되었습니다!");
                this.modalShow = !this.modalShow;
                this.$router.push("/user/mypage");
                this.nicknameCheck = false;
              } else {
                alert("회원정보 수정이 실패했습니다");
              }
            })
            .catch((error) => {
              alert(error);
            });
        } else {
          // 닉네임과 비밀번호 모두 변경하는 경우
          axios
            .put(
              `${BACK_URL}/users/info`,
              {
                nickname: this.userupdateData.nickname,
                password: this.userupdateData.password,
              },
              {
                headers: { "jwt-auth-token": this.$cookies.get("token") },
              }
            )
            .then((response) => {
              console.log(response);
              if (response.status === 202) {
                alert("회원정보가 수정되었습니다!");
                this.modalShow = !this.modalShow;
                this.$router.push("/user/mypage");
                this.userData.nickname = this.userupdateData.nickname;
                this.nicknameCheck = false;
              } else {
                alert("회원정보 수정이 실패했습니다");
              }
            })
            .catch((error) => {
              alert(error);
            });
        }
      } else {
        alert("닉네임 중복을 확인해주세요.");
        return;
      }
    },
    nameCheck() {
      // 닉네임 중복 조회하는 메소드
      axios
        .post(
          `${BACK_URL}/users/info/nickname`,
          {
            nickname: this.userupdateData.nickname,
          },
          {
            headers: { "jwt-auth-token": this.$cookies.get("token") },
          }
        )
        .then((response) => {
          console.log(response);
          if (response.status === 200) {
            this.nicknameCheck = true;
            alert("사용 가능한 닉네임입니다.");
          } else {
            alert("새로운 닉네임을 입력해주세요.");
            this.userupdateData.nickname = "";
          }
        })
        .catch((error) => {
          alert(error);
        });
    },
    switchModal() {
      this.modalShow = !this.modalShow;
    },

    plusFood() {
      // 빈값일 경우 추가 안되도록 한다.
      if (this.addText === "") {
        return;
      }
      // 중복되는 데이터일 경우 추가 안되도록 한다.
      for (var i = 0; i < this.chips.length; i++) {
        if (this.addText === this.chips[i]) {
          this.addText = "";
          return;
        }
      }
      // 위의 경우가 아니라면 추가한다.
      this.chips.push(this.addText);
      this.addText = "";
      this.emptyChip = false;
      axios
        .put(
          `${BACK_URL}/users/mypage/box`,
          {
            box: this.chips.toString(),
          },
          {
            headers: { "jwt-auth-token": this.$cookies.get("token") },
          }
        )
        .then((response) => {
          console.log(response);
          if (response.status === 202) {
            alert("냉장고 재료가 추가되었습니다.");
          }
        })
        .catch((error) => {
          alert(error);
        });
    },
    closeChip(tag) {
      // close 버튼 누를 경우 실행되는 메소드 (리스트에서 삭제)
      this.chips.splice(this.chips.indexOf(tag), 1);
      axios
        .put(
          `${BACK_URL}/users/mypage/box`,
          {
            box: this.chips.toString(),
          },
          {
            headers: { "jwt-auth-token": this.$cookies.get("token") },
          }
        )
        .then((response) => {
          console.log(response);
          if (response.status === 202) {
            alert("냉장고 재료가 삭제되었습니다.");
          }
        })
        .catch((error) => {
          alert(error);
        });
      if (this.chips.length === 0) {
        this.emptyChip = true;
      }
    },
    moveCreatePost() {
      // recipe 작성하기 페이지로 가도록 하는 method
      this.$router.push("/create");
    },
  },
};
</script>

<style>
.text-intro {
  border: 1px dotted black;
  padding: 5px;
  height: 60px;
}
.interest {
  margin-top: 30px;
  text-align: left;
  padding: 10px;
}
.middle {
  text-align: left;
  margin-top: 50px;
  background-color: skyblue;
  border-radius: 1%;
  padding: 10px;
}
.header {
  margin-top: 50px;
  width: 100%;
  overflow: auto;
}
.small {
  font-size: 0.4em;
  margin: 2px;
}
.box {
  display: inline-block;
  width: 15%;
  height: 150px;
  float: left;
  border-radius: 30%;
  overflow: hidden;
}
.introduce {
  display: inline-block;
  width: 85%;
  padding-left: 55px;
}
.profile {
  width: 100%;
  height: 100%;
  object-fit: cover;
}
.bottom-button {
  margin-bottom: 50px;
}
input[type="password"] {
  font-family: "Avenir";
}
::placeholder {
  padding: 8px;
  color: black;
  font-size: 0.7em;
}
input[value="userData.nickname"] {
  margin-left: 2px;
}
</style>
