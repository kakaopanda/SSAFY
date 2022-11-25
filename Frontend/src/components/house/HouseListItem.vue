<template>
  <b-row
    class="m-2"
    @click="selectHouse"
    @mouseover="colorChange(true)"
    @mouseout="colorChange(false)"
    :class="{ 'mouse-over-bgcolor': isColor }"
    style="white-space: nowrap; overflow: hidden;"
  >
    <!-- <b-col cols="2" class="text-center align-self-center">
      <b-img thumbnail src="https://picsum.photos/250/250/?image=58" alt="Image 1"></b-img>
    </b-col> -->
    <!-- <b-col cols="4" class="align-self-center"> {{ house.sidoName }} {{ house.gugunName }} {{ house.dongName }}</b-col> -->
    <b-col style="overflow: hidden; text-align:center;" cols="4"> {{ house.aptName }} </b-col>
    <b-col style="overflow: hidden; text-align:right;" cols="1"> {{ house.floor }}층 </b-col>
    <b-col style="overflow: hidden; text-align:right;" cols="2"> {{ house.area }}㎡ </b-col>
    <b-col style="overflow: hidden; text-align:right;" cols="3"> {{ house.dealYear }}.{{ house.dealMonth }}.{{ house.dealDay }} </b-col>
    <b-col style="overflow: hidden; text-align:right;" cols="2"> {{ house.recentPrice }}만원 </b-col>
  </b-row>
</template>

<script>
import { mapActions } from "vuex";

const houseStore = "houseStore";

export default {
  name: "HouseListItem",
  data() {
    return {
      isColor: false,
    };
  },
  props: {
    house: Object,
  },
  methods: {
    ...mapActions(houseStore, ["detailHouse"]),
    selectHouse() {
      // console.log("listRow : ", this.house);
      // this.$store.dispatch("getHouse", this.house);
      this.detailHouse(this.house);
    },
    colorChange(flag) {
      this.isColor = flag;
    },
  },
};
</script>

<style scoped>
.apt {
  width: 50px;
}
.mouse-over-bgcolor {
  background-color: #F0F0F0;
}
</style>
