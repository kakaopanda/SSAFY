<template>
<div>
      <div style="margin-left:15px; display:inline; width:48%; float:left;">
      <kakao-map></kakao-map>
     </div>
     <div style="border: 1px solid #DDDDDD; margin-right:15px; display:inline; width:48%; height:1040px; float:right;">
      <b-container class="bv-example-row text-center">
        <b-row>
          <b-col>
            <house-search-bar></house-search-bar>
          </b-col>
        </b-row>
        <b-row>
          <b-col cols="12" align="left">
            <br>
            <house-list/>
          </b-col>
        </b-row>
      </b-container>
     </div>
</div>
</template>
<script>
import HouseSearchBar from "@/components/house/HouseSearchBar.vue";
import HouseList from "@/components/house/HouseList.vue";
import KakaoMap from "@/components/map/KakaoMap.vue";

export default {
  name: "AppHouse",
  components: {
    HouseSearchBar,
    HouseList,
    // HouseDetail,
    KakaoMap,
  },
};
</script>
<style scoped>
.underline-orange {
  display: inline-block;
  background: linear-gradient(180deg, rgba(255, 255, 255, 0) 70%, rgba(231, 149, 27, 0.3) 30%);
}
</style>
