<template>
  <b-row class="mb-1">
    <b-col style="text-align: left">
      <b-form @submit="onSubmit" @reset="onReset">
        <b-form-group id="userid-group" label="작성자:" label-for="userid" description="작성자를 입력하세요.">
          <b-form-input
            id="userid"
            :disabled="isUserid"
            v-model="article.userid"
            type="text"
            required
            placeholder="작성자를 입력해주세요."
            value="dd"
          ></b-form-input>
        </b-form-group>

        <b-form-group id="subject-group" label="제목:" label-for="subject" description="제목을 입력하세요.">
          <b-form-input
            id="subject"
            v-model="article.subject"
            type="text"
            required
            placeholder="제목을 입력해주세요."
          ></b-form-input>
        </b-form-group>

        <b-form-group id="content-group" label="내용:" label-for="content">
          <editor
    :options="editorOptions"
    height="500px"
    initialEditType="wysiwyg"
    previewStyle="vertical"
    ref="toastuiEditor"

  />
        </b-form-group>
          <b-button type="submit" variant="outline-dark" class="m-1" v-if="this.type === 'register'">글작성</b-button>
          <b-button type="submit" variant="outline-dark" class="m-1" v-else>글수정</b-button>
          <b-button type="reset" variant="outline-dark" class="m-1">초기화</b-button>
      </b-form>
    </b-col>
  </b-row>
</template>

<script>
import http from "@/api/index";
import '@toast-ui/editor/dist/toastui-editor.css';
import { Editor } from '@toast-ui/vue-editor';
export default {
  name: "BoardInputItem",
    components: {
      editor: Editor
    },
  data() {
    return {
      article: {
        articleno: 0,
        userid: "",
        subject: "",
        content: "",
      },
      isUserid: false,
      editorText: "",
        editorOptions: {
          hideModeSwitch: true
        },
    };
  },
  props: {
    type: { type: String },
  },
  created() {
    if (this.type === "modify") {
      if (this.$store.getters["memberStore/checkUserInfo"] === null &&
      this.$store.getters["memberStore/checkUserInfo"].userclass != "admin") {
        console.log("로그인 안함");
          alert("관리자 로그인이 필요한 기능입니다.");
          this.moveList();
      }
      http.get(`/board/${this.$route.params.articleno}`).then(({ data }) => {
        this.article = data;
        this.$refs.toastuiEditor.invoke('setMarkdown', this.article.content );
      });
      this.isUserid = true;
    }
    else{
      this.article.userid = this.$store.getters["memberStore/checkUserInfo"].userid;
      this.isUserid = true;
    }
  },
  computed: {
    message() {
      if (this.article.content) return this.article.content.split("\n").join("<br>");
      return "";
    },
  },
  methods: {
    onSubmit(event) {
      event.preventDefault();

      let err = true;
      let msg = "";
      let htmlContent = this.$refs.toastuiEditor.invoke('getMarkdown');

      this.article.content = htmlContent;
      
      !this.article.userid && ((msg = "작성자 입력해주세요"), (err = false), this.$refs.userid.focus());
      err && !this.article.subject && ((msg = "제목 입력해주세요"), (err = false), this.$refs.subject.focus());
      err && !this.article.content && ((msg = "내용 입력해주세요"), (err = false), this.$refs.content.focus());

      if (!err) alert(msg);
      else this.type === "register" ? this.registArticle() : this.modifyArticle();
    },
    onReset(event) {
      event.preventDefault();
      this.article.articleno = 0;
      this.article.subject = "";
      this.article.content = "";
      this.moveList();
    },
    registArticle() {
      http
        .post(`/board`, {
          userid: this.article.userid,
          subject: this.article.subject,
          content: this.article.content,
        })
        .then(({ data }) => {
          let msg = "등록 처리시 문제가 발생했습니다.";
          if (data === "success") {
            msg = "등록이 완료되었습니다.";
          }
          alert(msg);
          this.moveList();
        });
    },
    modifyArticle() {
      http
        .put(`/board`, {
          articleno: this.article.articleno,
          userid: this.article.userid,
          subject: this.article.subject,
          content: this.article.content,
        })
        .then(({ data }) => {
          let msg = "수정 처리시 문제가 발생했습니다.";
          if (data === "success") {
            msg = "수정이 완료되었습니다.";
          }
          alert(msg);
          this.moveList();
        });
    },
    moveList() {
      this.$router.push({ name: "boardlist" });
    },
  },
};
</script>

<style></style>