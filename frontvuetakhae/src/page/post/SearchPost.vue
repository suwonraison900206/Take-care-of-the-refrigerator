<template>
  <div class="searchPost">
    <div class="container">
      <v-card class="p-3" color="grey lighten-1">
        <h2 class="m-3 0 4">나의 냉장고</h2>
        <div>
          <div class="left d-sm-inline-flex pa-2">
            <v-row class="m-2 inputBlank" variant="danger">
              <v-text-field
                label="직접 추가하기"
                v-model="addText"
                hide-details="auto"
                v-on:keyup.enter="
                  plusFood();
                  check();
                "
              ></v-text-field>
              <v-icon
                large
                @click="
                  plusFood();
                  check();
                "
              >mdi-plus</v-icon>
            </v-row>
          </div>
          <div>
            <v-chip
              class="m-1"
              v-for="tag in chips"
              close
              @click:close="closeChip(tag)"
              :key="tag"
            >{{ tag }}</v-chip>
            <div v-if="emptyChip">요리할 재료를 입력해주세요.</div>
          </div>
        </div>
      </v-card>
      <h2 class="m-5 white--text">지금 당장 가능한 요리 레시피</h2>
      <h3 v-if="emptyChip" class="white--text">요리할 재료를 입력해주세요. 냉뷰가 기다리고 있습니다.</h3>
      <div class="row row-cols-3 searchPostContent">
        <ul v-for="showData in showDatas" :key="showData.title">
          <v-hover v-slot:default="{ hover }" open-delay="200">
            <v-card max-width="344" class="mx-auto" :elevation="hover ? 16 : 2">
              <v-list-item @click="goDetail(showData.boardId)">
                <v-list-item-avatar color="grey"></v-list-item-avatar>
                <v-list-item-content>
                  <v-list-item-title class="headline">
                    {{
                    showData.title
                    }}
                  </v-list-item-title>
                  <v-list-item-subtitle style="text-align:right;">작성자 : {{ showData.nickname }}</v-list-item-subtitle>
                  <small style="text-align:right;">
                    {{
                    showData.createAt
                    }}
                  </small>
                </v-list-item-content>
              </v-list-item>

              <v-img
                :src="showData.thumbnailImage"
                height="194"
                @click="goDetail(showData.boardId)"
              ></v-img>

              <v-card-text @click="goDetail(showData.boardId)">
                <p class="caption">소요시간 : {{ showData.cookingTime }}시간</p>난이도
                <v-rating
                  class="p-0"
                  small
                  v-model="showData.grade"
                  background-color="orange lighten-3"
                  color="orange"
                ></v-rating>
              </v-card-text>
              <v-card-actions>
                <v-btn @click="goDetail(showData.boardId)" text color="deep-purple accent-4">자세히 보기</v-btn>
                <v-btn text color="deep-purple accent-4">즐겨찾기</v-btn>
                <v-btn icon>
                  <v-icon>mdi-heart</v-icon>
                </v-btn>

                <v-spacer></v-spacer>
                <v-btn icon @click="changeEasy">
                  <div v-if="easy">
                    <b-icon icon="emoji-smile" scale="2" variant="warning"></b-icon>
                    <p class="caption mb-0 mt-1">easy</p>
                  </div>
                  <div v-else>
                    <b-icon icon="emoji-frown" scale="2" variant="secondary"></b-icon>
                    <p class="caption mb-0 mt-1">hard</p>
                  </div>
                </v-btn>
                <v-btn icon>
                  <v-icon>mdi-share-variant</v-icon>
                </v-btn>
              </v-card-actions>
            </v-card>
          </v-hover>
        </ul>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "SearchPost",
  data() {
    return {
      easy: true,
      limit: 0,
      addText: "",
      emptyChip: false,
      backDatas: [],
      showDatas: [],
      frag: 0,
      chips: [
        // 마이페이지에 입력한 나의 냉장고 데이터를 넣기
        "간장",
        "김치",
      ],
      dummyData: [
        {
          title: "간단한 연어덮밥",
          content: "30분안에 끝나는 연어덮밥 요리 방법",
          image: "https://i.imgur.com/Ztp9hAN.jpg",
          materials: ["연어", "간장", "와사비", "밥", "양파"],
          date: "2020.07.30",
          comment: 3,
          nickname: "연어가좋아",
          like: 3,
          steps: [
            "연어를 자른다",
            "밥 위에 연어를 올린다.",
            "양파와 와사비로 고명을 해준다.",
          ],
        },
        {
          title: "쇠고기 미역국",
          content: "거의 밥도둑!",
          image: "https://i.imgur.com/O1cQ8N7.jpg",
          materials: ["소고기", "소금", "미역", "간장", "참기름"],
          date: "2020.07.30",
          comment: 3,
          nickname: "간장게장",
          like: 3,
          steps: [
            "물을 끓인다",
            "미역을 넣고 끓인다.",
            "소고기를 넣고 간장으로 간을 맞춰준다.",
            "참기름으로 감칠맛을 더한다.",
          ],
        },
        {
          title: "김치볶음밥",
          content: "백종원 선생님의 황금레시피",
          image: "https://i.imgur.com/7pNI9BA.jpg",
          materials: ["김치", "간장", "스팸", "베이컨", "밥", "계란"],
          date: "2020.07.30",
          comment: 3,
          nickname: "골목식당",
          like: 3,
          steps: [
            "김치를 식용유에 볶는다.",
            "스팸을 넣어 볶다가 밥을 넣는다.",
            "간장을 살짝 태워 밥에 섞는다.",
            "계란을 넣고 마무리한다.",
          ],
        },
        {
          title: "전주비빔밥",
          content: "계속 생각나는 그 맛",
          image: "https://i.imgur.com/oDHwKwP.jpg",
          materials: [
            "계란",
            "소고기",
            "당근",
            "콩나물",
            "간장",
            "고추장",
            "밥",
            "오이",
            "호박",
          ],
          date: "2020.07.30",
          comment: 3,
          nickname: "콩나물국밥",
          like: 3,
          steps: ["밥을 한다", "나물을 준비한다.", "나물은 얹고 섞어준다."],
        },
        {
          title: "김치전",
          content: "비오는 날 생각나는",
          image: "https://i.imgur.com/2nMSgb8.jpg",
          date: "2020.07.30",
          materials: [
            "김치",
            "소금",
            "간장",
            "다진마늘",
            "양파",
            "밀가루",
            "물",
          ],
          comment: 3,
          nickname: "비가오는날엔",
          like: 3,
          steps: [
            "밀가루와 김치를 넣고 반죽을 만든다.",
            "반죽을 굽는다.",
            "맛있게 먹는다.",
          ],
        },
      ],
    };
  },
  created() {
    axios.get("http://i3a305.p.ssafy.io:8399/api/boards").then((response) => {
      console.log(response.data);
      this.backDatas = response.data.boards;
    });
    if (this.chips.length === 0) {
      this.emptyChip = true;
    }
  },
  methods: {
    changeEasy() {
      if (this.easy) {
        this.easy = false;
      } else {
        this.easy = true;
      }
    },
    remove(item) {
      this.chips.splice(this.chips.indexOf(item), 1);
      this.chips = [...this.chips];
    },
    goDetail(boardId) {
      this.$router.push("/detail/" + boardId);
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
    },
    closeChip(tag) {
      // close 버튼 누를 경우 실행되는 메소드 (리스트에서 삭제)
      this.chips.splice(this.chips.indexOf(tag), 1);
      if (this.chips.length === 0) {
        this.emptyChip = true;
      }
      this.showDatas = [];
      for (let j = 0; j < this.dummyData.length; j++) {
        for (let k = 0; k < this.chips.length; k++) {
          console.log(this.dummyData[j].materials, "구분", this.chips[k]);
          if (this.dummyData[j].materials.includes(this.chips[k])) {
            if (this.showDatas.includes(this.backDatas[j]) === false) {
              this.showDatas.push(this.backDatas[j]);
              break;
            }
          }
        }
      }
    },
    check() {
      this.showDatas = [];
      for (let j = 0; j < this.dummyData.length; j++) {
        for (let k = 0; k < this.chips.length; k++) {
          console.log(this.dummyData[j].materials, "구분", this.chips[k]);
          if (this.dummyData[j].materials.includes(this.chips[k])) {
            if (this.showDatas.includes(this.backDatas[j]) === false) {
              this.showDatas.push(this.backDatas[j]);
              break;
            }
          }
        }
      }
    },
  },
};
</script>

<style>
.searchPost {
  background-image: url("https://user-images.githubusercontent.com/60081201/89270362-33b1d980-d676-11ea-8b50-bee693e1ee36.jpg");
  -webkit-background-size: cover;
  -moz-background-size: cover;
  -o-background-size: cover;
  background-size: cover;
}
.searchPostContent {
  min-height: 400px;
}
</style>
