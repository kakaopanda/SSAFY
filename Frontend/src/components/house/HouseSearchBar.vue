<template>
<div>
  <b-row class="mt-4 mb-4 text-center">
    <b-col class="sm-3">
      <b-form-select v-model="sidoCode" :options="sidos" @change="gugunList"></b-form-select>
    </b-col>
    <b-col class="sm-3">
      <b-form-select v-model="gugunCode" :options="guguns" @change="dongList"></b-form-select>
    </b-col>
    <!-- 동 추가 -->
    <b-col class="sm-3">
      <b-form-select v-model="dongCode" :options="dongs" @change="searchApt"></b-form-select>
    </b-col>
  </b-row>
  <b-row>
    <b-col sm="10">
      <b-form-input
        v-model.trim="dongCode"
        placeholder="검색할 동코드를 입력해주세요."
        align="left"
        @keypress.enter="sendKeyword"
      ></b-form-input>
    </b-col>
    <b-col sm="2" align="right">
      <b-button variant="outline-dark" @click="sendKeyword">검색</b-button>
    </b-col>
  </b-row>
</div>
</template>

<script>
import { mapState, mapActions, mapMutations } from "vuex";

/*
  namespaced: true를 사용했기 때문에 선언해줍니다.
  index.js 에서 modules 객체의 '키' 이름입니다.

  modules: {
    키: 값
    memberStore: memberStore,
    houseStore: houseStore
  }  
*/
const houseStore = "houseStore";

export default {
  name: "HouseSearchBar",
  data() {
    return {
      sidoCode: null,
      gugunCode: null,
      dongCode: null,
    };
  },
  computed: {
    ...mapState(houseStore, ["sidos", "guguns", "dongs", "houses"]),
    // sidos() {
    //   return this.$store.state.sidos;
    // },
  },
  created() {
    // this.$store.dispatch("getSido");
    // this.sidoList();
    this.CLEAR_SIDO_LIST();
    this.CLEAR_APT_LIST();
    this.getSido();
  },
  methods: {
    ...mapActions(houseStore, ["getSido", "getGugun", "getDong",  "getHouseList"]),
    ...mapMutations(houseStore, ["CLEAR_SIDO_LIST", "CLEAR_GUGUN_LIST", "CLEAR_DONG_LIST", "CLEAR_APT_LIST"]),
    // sidoList() {
    //   this.getSido();
    // },
    gugunList() {
      // console.log(this.sidoCode);
      this.CLEAR_GUGUN_LIST();
      this.gugunCode = null;
      if (this.sidoCode) this.getGugun(this.sidoCode);
    },
    dongList() {
      // console.log(this.sidoCode);
      this.CLEAR_DONG_LIST();
      this.dongCode = null;
      if (this.gugunCode) this.getDong(this.gugunCode);
    },
    searchApt() {
      // console.log("searchApt() 호출!");
      // console.log(this.dongCode);
      if (this.dongCode) this.getHouseList(this.dongCode);
    },
    sendKeyword(){
      if (this.dongCode) this.getHouseList(this.dongCode);
    }
  },
};
</script>

<style></style>
