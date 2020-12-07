<template>
  <div class="container" style="margin-top: 30px;
">
    <b-form-group
      label-cols="4"
      label-cols-lg="2"
      label-size="lg"
      label="제목"
      label-for="input-lg"
      autofocus
    >
      <b-form-input v-model="postData.title" id="input-lg" size="lg"></b-form-input>
    </b-form-group>
    <v-stepper v-model="e6" vertical>
      <v-stepper-step color="red" :complete="e6 > 1" step="1">
        재료
        <small class="mt-2">드래그앤드롭으로 쉽게 요리에 필요한 재료를 입력하세요.</small>
      </v-stepper-step>
      <v-stepper-content step="1">
        <v-card class="mb-12">
          <b-container class="bv-example-row">
            <b-row>
              <b-col class="bg-my-gredient">
                <p class="text-white">요리에 필요한 재료</p>
                <div>
                  <draggable
                    tag="span"
                    v-model="postData.content.ingredients"
                    v-bind="dragOptions"
                    @start="drag = true"
                    @end="drag = false"
                  >
                    <v-chip
                      class="mr-2 mb-2"
                      v-for="tag in postData.content.ingredients"
                      :key="tag"
                      close
                      @click:close="closeChip(tag)"
                    >{{ tag }}</v-chip>
                  </draggable>
                </div>
              </b-col>
              <b-col>
                <v-row class="m-2">
                  <v-text-field
                    label="직접 추가하기"
                    v-model="addText"
                    hide-details="auto"
                    v-on:keyup.enter="plusFood"
                  ></v-text-field>
                  <v-icon large @click="plusFood">mdi-plus</v-icon>
                </v-row>
                <div class="bg-my-box">
                  나의 냉장고
                  <div>
                    <draggable
                      tag="ul"
                      v-model="list"
                      v-bind="dragOptions"
                      @start="drag = true"
                      @end="drag = false"
                    >
                      <v-chip class="m-1" v-for="tag in list" :key="tag">{{ tag }}</v-chip>
                    </draggable>
                  </div>
                </div>
              </b-col>
            </b-row>
          </b-container>
        </v-card>
        <!-- <div>{{ postData.content.ingredients }}</div> -->
        <v-btn color="error" @click="e6 = 2">완료</v-btn>
      </v-stepper-content>

      <v-stepper-step color="red" :complete="e6 > 2" step="2">요리 과정</v-stepper-step>

      <v-stepper-content step="2">
        <v-card class="mb-12">
          <b-container class="bv-example-row">
            <b-row>
              <b-col class="bg-my-step">
                과정 순서
                <draggable
                  class="list-group"
                  tag="ul"
                  v-model="postData.content.steps"
                  v-bind="dragOptions"
                  @start="drag = true"
                  @end="drag = false"
                >
                  <transition-group type="transition" :name="'flip-list'">
                    <li v-for="tag in postData.content.steps" :key="tag.description">
                      <v-overflow-btn
                        :items="dropdown_icon"
                        label="타입 선택"
                        segmented
                        target="#dropdown-example"
                      ></v-overflow-btn>
                      <b-form-input v-model="hashtag" placeholder="해시태그 입력"></b-form-input>
                      <i aria-hidden="true"></i>
                      {{ tag.description }}
                      <v-btn small @click="deleleStep(tag.description)">삭제</v-btn>
                      <v-btn small color="primary" class="ml-1">내 저장소</v-btn>
                    </li>
                  </transition-group>
                </draggable>
              </b-col>
              <b-col class="bg-plus-step">
                과정 입력
                <b-form-input
                  type="text"
                  placeholder="요리 과정을 입력해주세요."
                  v-model="postData.content.process"
                />
                <button class="btn btn-info" @click="plusStep">과정추가</button>
              </b-col>
            </b-row>
          </b-container>
        </v-card>
        <v-btn color="error" class="mr-2" @click="e6 = 3">완료</v-btn>
        <v-btn color="secondary" @click="e6 = 1">뒤로 가기</v-btn>
      </v-stepper-content>

      <v-stepper-step color="red" :complete="e6 > 3" step="3">난이도 & 소요시간</v-stepper-step>

      <v-stepper-content step="3">
        <v-card class="mb-12" height="200px">
          난이도
          <div>
            <small>별이 많을수록 어렵습니다.</small>
            <v-rating
              v-model="postData.difficulty"
              background-color="orange lighten-3"
              color="orange"
              medium
            ></v-rating>
          </div>
          <hr />소요 시간
          <b-form-input type="text" v-model="postData.time" />
        </v-card>
        <v-btn color="error" class="mr-2" @click="e6 = 4">완료</v-btn>
        <v-btn color="secondary" @click="e6 = 2">뒤로 가기</v-btn>
      </v-stepper-content>

      <v-stepper-step color="red" step="4">후기 작성</v-stepper-step>
      <v-stepper-content step="4">
        <v-card class="mb-12 p-2" height="200px">
          <v-text-field
            label="요리하면서 꿀팁이나 소감을 작성해주세요."
            v-model="postData.review"
            :rules="rules"
            hide-details="auto"
          ></v-text-field>

          <br />
          <div class="titles">썸네일 사진 넣기</div>
          <br />
          <input
            type="file"
            name="file"
            id="imageFileOpenInput"
            accept="image/*"
            style="float:left"
          />
          <br />
        </v-card>
        <v-btn color="error" class="mr-2" @click="createPost">작성 완료</v-btn>
        <v-btn color="secondary" @click="e6 = 3">뒤로 가기</v-btn>
      </v-stepper-content>
    </v-stepper>
  </div>
</template>

<script>
import draggable from "vuedraggable";
import axios from "axios";

// const message = ["소금", "밥", "간장", "돼지고기", "오이", "김치", "감자", "설탕"];

export default {
  name: "CreatePost",
  components: {
    draggable,
  },
  data() {
    return {
      hashtag: "",
      dropdown_icon: [
        { text: "재료 손질", callback: () => console.log("재료 손질") },
        { text: "요리 준비", callback: () => console.log("요리 준비") },
        { text: "플레이팅", callback: () => console.log("플레이팅") },
      ],
      e6: 1,
      rules: [(value) => !!value || "Required."],
      postData: {
        title: null,
        content: {
          ingredients: [],
          steps: [],
          process: null,
        },
        difficulty: null,
        time: null,
        review: null,
        thumbnail: null,
      },
      // list : 나의 냉장고의 데이터를 저장할 변수
      list: ["소금", "밥", "간장", "돼지고기", "오이", "김치", "감자", "설탕"],

      isDragging: false,
      delayedDragging: false,
      addText: "",
    };
  },
  created() {
    // if (!this.$cookies.isKey("auth-token")) {
    //   return this.$router.push("users/signin");
    // }
  },
  methods: {
    deleleStep(title) {
      const idx = this.postData.content.steps.findIndex(function (item) {
        return item.description === title;
      });
      this.postData.content.steps.splice(idx, 1);
    },
    plusFood() {
      // 빈값일 경우 추가 안되도록 한다.
      if (this.addText === "") {
        return;
      }
      // 중복되는 데이터일 경우 추가 안되도록 한다.
      for (var i = 0; i < this.postData.content.ingredients.length; i++) {
        if (this.addText === this.postData.content.ingredients[i]) {
          this.addText = "";
          return;
        }
      }
      // 위의 경우가 아니라면 추가한다.
      this.postData.content.ingredients.push(this.addText);
      this.addText = "";
    },
    closeChip(tag) {
      this.postData.content.ingredients.splice(
        this.postData.content.ingredients.indexOf(tag),
        1
      );
    },
    plusStep() {
      if (this.postData.content.process === "") {
        return;
      }
      this.postData.content.steps.push({
        description: this.postData.content.process,
        image: "",
        stepNumber: 1,
      });
      this.postData.content.process = "";
    },
    createPost() {
      console.log(this.postData);

      const requestHeader = {
        headers: {
          "jwt-auth-token": this.$cookies.get("token"),
        },
      };
      var ingreString = this.postData.content.ingredients.join(" ");
      console.log(ingreString);
      axios
        .post(
          "http://i3a305.p.ssafy.io:8399/api/boards/",
          {
            content: this.postData.review,
            cookingTime: this.postData.time,
            grade: this.postData.difficulty,
            ingredient: ingreString,
            steps: this.postData.content.steps,
            thumbnailImage: "no image",
            title: this.postData.title,
          },
          requestHeader
        )
        .then((response) => {
          console.log(response);
          alert("게시글이 성공적으로 작성됐습니다!");
          this.$router.push("/");
        })
        .catch((error) => {
          console.log(error);
        });
    },
  },
  computed: {
    dragOptions() {
      return {
        animation: 0,
        group: "description",
      };
    },
  },
  watch: {
    isDragging(newValue) {
      if (newValue) {
        this.delayedDragging = true;
        return;
      }
      this.$nextTick(() => {
        this.delayedDragging = false;
      });
    },
  },
};
</script>

<style>
@import url(https://cdn.jsdelivr.net/gh/moonspam/NanumSquare@1.0/nanumsquare.css);

@font-face {
  font-family: naBrush;
  src: url("NanumBarunpenB.ttf");
}
* {
  font-family: "NanumSquare", sans-serif;
}
.titles {
  float: left;
}
.flip-list-move {
  transition: transform 0.5s;
}
.bg-my-gredient {
  background-color: burlywood;
  padding: 20px;
  border-radius: 5%;
}
.bg-my-box {
  background-color: skyblue;
  padding: 20px;
  border-radius: 5%;
}
.bg-my-step {
  background-color: khaki;
  padding: 20px;
  border-radius: 2%;
}
.bg-plus-step {
  background-color: lightsteelblue;
  padding: 20px;
  border-radius: 2%;
}
</style>
