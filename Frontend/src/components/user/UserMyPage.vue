<template>
  <b-container v-if="userInfo">
    <b-row>
    <div style="text-align:center; font-size:30px;">
      &nbsp;&nbsp;<b-icon-person></b-icon-person>
      &nbsp; 마이페이지
      </div>
      </b-row>
    <b-row class="mb-5"></b-row>
    <b-row>
      <b-col cols="12">
        <b-jumbotron>
          <hr class="my-4" />
              <b-card class="text-center" style="max-width: 80rem">
          <form id="form-join" method="POST" action="">
			      <input type="hidden" name="act" value="join">
            <div class="mb-3">
              <label style="float:left;" for="username" class="form-label">이름(Name) : </label>
              <input
                style="display:inline-block;"
                type="text"
                class="form-control"
                id="username"
                name="username"
                placeholder="Name"
                :value=userInfo.username
                readonly
              />
            </div>
            <div class="mb-3">
              <label style="float:left;" for="userid" class="form-label">아이디(Identification) : </label>
              <input
                type="text"
                class="form-control"
                id="userid"
                name="userid"
                placeholder="ID"
                :value=userInfo.userid
                readonly
              />
            </div>
              <label style="float:left;" for="emailid" class="form-label">이메일(E-mail) :</label>
              <div class="mb-3 input-group">
                <input
                  type="text"
                  class="form-control"
                  id="emailid"
                  name="emailid"
                  placeholder="E-mail ID"
                  :value=userInfo.emailid
                  readonly
                />
                <span class="input-group-text">@</span>
                <input
                  type="text"
                  class="form-control"
                  id="emailid"
                  name="emailid"
                  placeholder="E-mail ID"
                  :value=userInfo.emaildomain
                  readonly
                />
              </div>
              
            <div class="mb-3">
              <label style="float:left;" for="userclass" class="form-label">회원 구분(Class) : </label>
                <input v-if="userInfo.userclass=='admin'"
                type="text"
                class="form-control"
                id="userclass"
                name="userclass"
                placeholder="Class"
                value="관리자"
                readonly
              />
                <input v-else
                type="text"
                class="form-control"
                id="userclass"
                name="userclass"
                placeholder="Class"
                value="일반 회원"
                readonly
              />
            </div>

            <div class="mb-3">
              <label style="float:left;" for="userclass" class="form-label">가입 일시(Join Date) : </label>
                <input
                type="text"
                class="form-control"
                id="joinDate"
                name="joinDate"
                placeholder="Class"
                :value=userInfo.joindate
                readonly
              />
            </div>
          </form>
        </b-card>
          <hr class="my-4" />
          <b-button variant="light" @click="modifyUser" class="mr-1">정보수정</b-button>
          <b-button variant="secondary" @click="modifyPwd" class="mr-1">비밀번호 변경</b-button>
          <b-button variant="dark" @click="deleteUser">회원탈퇴</b-button>
        </b-jumbotron>
      </b-col>
      <b-col></b-col>
    </b-row>
  </b-container>
</template>

<script>
import { mapState, mapActions} from "vuex";

const memberStore = "memberStore";

export default {
  name: "UserMyPage",
  components: {},
  computed: {
    ...mapState(memberStore, ["userInfo"]),
  },
  methods: {
    ...mapActions(memberStore, ["userDelete"]),
    async deleteUser() {
      var check = confirm("정말 탈퇴하시겠습니까?");
      if(check){
        await this.userDelete(this.userInfo.userid);
        alert("회원 탈퇴가 완료되었습니다.");
        this.$router.push({ name: "main" });
      }
      else{
        this.$router.push({ name: "mypage" });
      }
    },

    async modifyUser(){
      var check = confirm("회원 정보를 수정하시겠습니까?");
        if(check){
        this.$router.push({ name: "modify" });
      }
    },

    async modifyPwd(){
      var check = confirm("비밀번호를 변경하시겠습니까?");
        if(check){
        this.$router.push({ name: "pwdconfirm" });
      }
    },
  },
};
</script>

<style></style>
