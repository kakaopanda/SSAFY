<template>
  <b-container class="bv-example-row mt-3">
    <b-row>
      <b-col>
        <b-alert variant="secondary" show><h3>게시글 수정</h3></b-alert>
      </b-col>
    </b-row>
    <board-input-item type="modify" />
  </b-container>
</template>

<script>
import BoardInputItem from "@/components/board/item/BoardInputItem";

export default {
  name: "BoardModify",
  // data() {
  //   return {
  //     article: {},
  //     userid : '',
  //   };
  // },
  components: {
    BoardInputItem,
  },
  // created() {
  //   http.get(`/board/${this.$route.params.articleno}`).then(({ data }) => {
  //     this.article = data;
  //   });
  //   userid = this.$route.params.userid;
  // },
};
</script>

<style></style>
