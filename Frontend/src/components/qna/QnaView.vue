<template>
  <b-container class="bv-example-row mt-4">
    <b-row>
      <b-col>
        <b-alert variant="secondary" show><h3>Q & A</h3></b-alert>
      </b-col>
    </b-row>
    <b-row class="mb-1">
      <b-col class="text-left">
        <b-button variant="light" @click="moveList">목록</b-button>
      </b-col>
      <b-col class="text-right">
        <b-button variant="light" size="sm" @click="moveModifyArticle" 
        class="mr-2" v-if="isNotMessage && this.userInfo.userid === this.article.userid">글수정</b-button>
        <b-button variant="dark" size="sm" @click="deleteArticle"
        v-if="this.userInfo.userid === this.article.userid">글삭제</b-button>
      </b-col>
    </b-row>
    <b-row class="mb-1">
      <b-col>
        <b-card style="text-align:left;"
          :header-html="`<h3>
          질문. ${article.subject}</h3><div><h6>작성자 : ${article.userid}</div><div>작성일시 : ${article.regtime}</h6></div>`"
          class="mb-2"
          border-variant="dark"
          no-body
        >
        </b-card>
      </b-col>
    </b-row>
    <b-row class="mb-1" v-if="isMessage && this.article.answer !== null ">
      <b-col>
          <b-card style="text-align:left;"
          :header-html="`<h3>
          답변.</h3>`"
          class="mb-2"
          border-variant="dark"
          no-body>
            <b-card-body class="text-left">
              <div v-html="ansMessage"></div>
            </b-card-body>
          </b-card>
        <b-button variant="light" size="sm" @click="modifyAnswer" class="mr-2"
        v-if="this.userInfo.userclass === 'admin'">답변 수정</b-button>
        <b-button variant="dark" size="sm" @click="deleteAnswer"
        v-if="this.userInfo.userclass === 'admin'">답변 삭제</b-button>
      </b-col>
    </b-row>
    <b-row class="mb-1" v-else-if="this.userInfo.userclass === 'admin'">
      <b-col style="text-align: left">
        <b-form @submit="onSubmit" @reset="onReset">
          <b-form-group id="answer-group" label="" label-for="answer">
                                <editor
    :options="editorOptions"
    height="500px"
    initialEditType="wysiwyg"
    previewStyle="vertical"
    ref="toastuiEditor"
    v-html="ansMessage"
  />
          </b-form-group>

          <b-button type="submit" variant="light" class="m-1" v-if="this.userInfo.userclass === 'admin'">답변 작성</b-button>
          <b-button type="reset" variant="dark" class="m-1" v-if="this.userInfo.userclass === 'admin'">초기화</b-button>
        </b-form>
      </b-col>
    </b-row>
 
  </b-container>
</template>

<script>
import http from "@/api/index";
import '@toast-ui/editor/dist/toastui-editor.css';
import { Editor } from '@toast-ui/vue-editor';
export default {
  name: "QnaDetail",
        components: {
      editor: Editor
    },
  data() {
    return {
      article: {},
      tmpAns:"",
      userInfo : {},
      editorText: "",
      editorOptions: {
        hideModeSwitch: true
      },
    };
  },
  computed: {
    message() {
      if (this.article.content) return this.article.content.split("\n").join("<br>");
      return "";
    },
    isMessage() {
      if (this.tmpAns !== null) return true;
      return false;
    },
    isNotMessage() {
      if (this.tmpAns === null) return true;
      return false;
    },
    ansMessage() {
      if (this.article.answer) return this.article.answer.split("\n").join("<br>");
      return "";
    },

  },
  created() {
    http.get(`/qna/${this.$route.params.articleno}`).then(({ data }) => {
      this.article = data;
    });
    if (this.$store.getters["memberStore/checkUserInfo"] !== null) {
        this.userInfo = this.$store.getters["memberStore/checkUserInfo"];
    }
  },
  methods: {

    moveModifyArticle() {
      this.$router.replace({
        name: "qnamodify",
        params: { articleno: this.article.articleno },
      });
    },
    deleteArticle() {
      if (confirm("정말로 삭제?")) {
        this.$router.replace({
          name: "qnadelete",
          params: { articleno: this.article.articleno },
        });
      }
    },
    moveList() {
      this.$router.push({ name: "qnalist" });
    },
    modifyAnswer(){
      this.tmpAns = null;
    },
    deleteAnswer(){
      this.tmpAns = null;
      this.article.answer = null;
      this.registAnswer();
    },
    onSubmit(event) {
      event.preventDefault();

      let err = true;
      let msg = "";

      let htmlContent = this.$refs.toastuiEditor.invoke('getHTML');
      this.article.answer = htmlContent;
      this.tmpAns = htmlContent;
      !this.tmpAns && ((msg = "답변 입력해주세요"), (err = false));

      if (!err) alert(msg);
      else this.registAnswer();
    },
    onReset(event) {
      event.preventDefault();
      this.article.articleno = 0;
      this.article.subject = "";
      this.article.content = "";
      this.moveList();
    },
    registAnswer() {
       http
        .put(`/qna`, {
          articleno: this.article.articleno,
          userid: this.article.userid,
          subject: this.article.subject,
          content: this.article.content,
          answer: this.article.answer,
        })
        .then(({ data }) => {
          let msg = "처리시 문제가 발생했습니다.";
          if (data === "success") {
            msg = "완료되었습니다.";
          }
          alert(msg);
          this.moveList();
        });

    },

  },
};
</script>

<style></style>
