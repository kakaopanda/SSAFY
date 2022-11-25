<template>
  <b-container class="mt-4" v-if="userInfo">
    <b-row>
      <b-col>
        <b-alert variant="secondary" show><h3>Password Confirm</h3></b-alert>
      </b-col>
    </b-row>
    <b-row>
      <b-col cols="12">
        <b-jumbotron>
          <hr class="my-4" />
            <b-card class="text-center" style="max-width: 80rem">
            <b-form @submit="onSubmit">
			      <input type="hidden" name="act" value="confirm">
            <div class="mb-3">
              <label style="float:left;" for="userid" class="form-label">현재 비밀번호를 입력해주세요. </label>
              <input
                type="password"
                class="form-control"
                id="currentpwd"
                name="currentpwd"
                placeholder="Current Password"
                v-model="current.currentPwd" 
              />
            </div>
           </b-form>
        </b-card>
          <hr class="my-4" />
          <b-button variant="light" @click="confirmPwd" class="mr-1">비밀번호 확인</b-button>
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
  name: "UserPwdConfirm",
  components: {},
  computed: {
    ...mapState(memberStore, ["userInfo"]),
  },
  data() {
    return {
      current:{
        currentPwd : "",
        userid : "",
      }   
    };
  },
  methods: {
    ...mapActions(memberStore, ["userPwdConfirm"]),
    async confirmPwd(){
          this.current.userid = this.userInfo.userid;
          console.log(this.current);
          await this.userPwdConfirm(this.current);
          // this.$router.push({name: "pwdmodify"});
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
