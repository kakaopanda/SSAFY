<template>
  <b-container class="mt-4" v-if="userInfo">
    <b-row>
      <b-col>
        <b-alert variant="secondary" show><h3>MyPage</h3></b-alert>
      </b-col>
    </b-row>
    <b-row>
      <b-col cols="12">
        <b-jumbotron>
          <hr class="my-4" />
            <b-card class="text-center" style="max-width: 80rem">
            <b-form @submit="onSubmit">
			<input type="hidden" name="act" value="modify">
            <div class="mb-3">
              <label style="float:left;" for="username" class="form-label">이름(Name) : </label>
              <input
                style="display:inline-block;"
                type="text"
                class="form-control"
                id="username"
                name="username"
                placeholder="Name"
                v-model="userInfo.username"
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
                v-model="userInfo.userid"
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
                  v-model="userInfo.emailid"
                />
                <span class="input-group-text">@</span>
                <select
                  class="form-select"
                  id="emaildomain"
                  name="emaildomain"
                  aria-label="이메일 도메인 선택"
                  select style="width:50%;"
                  v-model="userInfo.emaildomain"
                >
                  <option selected>도메인 선택</option>
                  <option value="ssafy.com">ssafy.com</option>
                  <option value="google.com">google.com</option>
                  <option value="naver.com">naver.com</option>
                  <option value="kakao.com">kakao.com</option>
                </select>
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
           </b-form>
        </b-card>
          <hr class="my-4" />
          <b-button variant="light" @click="modifyUser" class="mr-1">정보수정</b-button>
          <b-button variant="dark" @click="myPage" class="mr-1">메인 페이지</b-button>
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
  name: "UserModify",
  components: {},
  computed: {
    ...mapState(memberStore, ["userInfo"]),
  },
  methods: {
    ...mapActions(memberStore, ["userModify"]),
    async modifyUser(){
      let check = confirm("회원 정보를 수정하시겠습니까?");
        if(check){
          if(this.userInfo.username==""){
            alert("이름을 입력해주세요.");
            return;
          }
          else if(this.userInfo.emailid==""){
            alert("이메일 아이디를 입력해주세요.");
            return;
          }
          else if(this.userInfo.emaildomain=="도메인 선택"){
            alert("이메일 도메인을 선택해주세요.");
            return;
          }
            await this.userModify(this.userInfo);
            alert("회원 정보 수정이 완료되었습니다.");
            this.$router.push({ name: "mypage" });
        }
    },
     onSubmit(event) {
      event.preventDefault();
    },
    myPage() {
      let check = confirm("회원 정보 페이지로 되돌아가시겠습니까?");
      if(check){
          this.$router.push({ name: "mypage" });
      }
    },
  },
};
</script>

<style></style>
