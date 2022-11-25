<template>
  <b-container class="bv-example-row mt-3">
    <b-row>
      <b-col>
        <b-alert variant="secondary" show><h3>게시글 보기</h3></b-alert>
      </b-col>
    </b-row>
    <b-row class="mb-1">
      <b-col class="text-left">
        <b-button variant="outline-dark" @click="moveList">목록</b-button>
      </b-col>
      <b-col class="text-right">
        <b-button variant="outline-dark" size="sm" @click="moveModifyArticle" class="mr-2" 
        v-if="this.userInfo.userid === this.article.userid">글수정</b-button>
        <b-button variant="outline-dark" size="sm" @click="deleteArticle"
        v-if="this.userInfo.userid === this.article.userid">글삭제</b-button>
      </b-col>
    </b-row>
    <b-row class="mb-1">
      <b-col style="text-align: left">
        <b-card
          :header-html="`<h3>${article.articleno}.
          ${article.subject} [${article.hit}]</h3><div><h6>${article.userid}</div><div>${article.regtime}</h6></div>`"
          class="mb-2"
          border-variant="dark"
          no-body
        >
          <!-- <b-card-body class="text-left">
            <div v-html="message"></div>
          </b-card-body> -->
       <viewer 
        height="500px"
        ref="toastuiViewer"
         />
        </b-card>
      </b-col>
    </b-row>
  </b-container>
</template>

<script>
// import moment from "moment";
import http from "@/api/index";
import '@toast-ui/editor/dist/toastui-editor-viewer.css';

import { Viewer } from '@toast-ui/vue-editor';

export default {
  name: "BoardDetail",
  components: {
     viewer: Viewer
  },
  data() {
    return {
      article: {},
      userInfo : {},
      viewerText: '# This is Viewer.\n Hello World.',
    };
  },
  computed: {
    message() {
      if (this.article.content) return this.article.content.split("\n").join("<br>");
      return "";
    },
  },
  created() {
    http.get(`/board/${this.$route.params.articleno}`).then(({ data }) => {
      this.article = data;
      console.log(this.article.content);
      this.$refs.toastuiViewer.invoke('setMarkdown', this.article.content );
    });
    if (this.$store.getters["memberStore/checkUserInfo"] !== null) {
        this.userInfo = this.$store.getters["memberStore/checkUserInfo"];
    }
  },
  methods: {
    moveModifyArticle() {
      this.$router.replace({
        name: "boardmodify",
        params: { articleno: this.article.articleno, userid :this.article.userid  },
      });
      //   this.$router.push({ path: `/board/modify/${this.article.articleno}` });
    },
    deleteArticle() {
      if (confirm("게시글을 삭제하시겠습니까?")) {
        this.$router.replace({
          name: "boarddelete",
          params: { articleno: this.article.articleno},
        });
      }
    },
    moveList() {
      this.$router.push({ name: "boardlist" });
    },
  },
  // filters: {
  //   dateFormat(regtime) {
  //     return moment(new Date(regtime)).format("YY.MM.DD hh:mm:ss");
  //   },
  // },
};
</script>

<style></style>
