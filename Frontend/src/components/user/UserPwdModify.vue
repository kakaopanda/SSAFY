<template>
  <b-container class="mt-4" v-if="userInfo">
    <b-row>
      <b-col>
        <b-alert variant="secondary" show><h3>Password Modify</h3></b-alert>
      </b-col>
    </b-row>
    <b-row>
      <b-col cols="12">
        <b-jumbotron>
          <hr class="my-4" />
            <b-card class="text-center" style="max-width: 80rem">
            <b-form @submit="onSubmit">
			      <input type="hidden" name="act" value="modifypwd">   
            <div class="mb-3">
              <label style="float:left;" for="userid" class="form-label">신규 비밀번호(New Password) : </label>
              <input
                type="password"
                class="form-control"
                id="newpwd"
                name="newpwd"
                placeholder="New Password"
                v-model="newPwd" 
                @keyup="newPwdCheck"
              />
            </div> 
            <div id="newpwdcheck-result"></div>  
           <div class="mb-3">
              <label style="float:left; clear:both;" for="userid" class="form-label">신규 비밀번호 확인(New Password Confirm) : </label>
              <input
                type="password"
                class="form-control"
                id="newpwdconfirm"
                name="newpwdconfirm"
                placeholder="New Password Confirm"
                v-model="newPwdConfirm" 
                @keyup="newPwdConfirmCheck"
              />
            </div>  
            <div id="newpwdconfirmcheck-result"></div> 
           </b-form>
        </b-card>
          <hr class="my-4" />
          <b-button variant="light" @click="onSubmit" class="mr-1">비밀번호 변경</b-button>
          <b-button variant="dark" @click="myPage" class="mr-1">메인 페이지</b-button>
        </b-jumbotron>
      </b-col>
      <b-col></b-col>
    </b-row>
  </b-container>
</template>

<script>
import http from "@/api/index";
import { mapState, mapActions} from "vuex";

const memberStore = "memberStore";

export default {
  name: "UserPwdModify",
  components: {},
  computed: {
    ...mapState(memberStore, ["userInfo"]),
  },
  data() {
    return {
      newPwd : "",
      newPwdConfirm : "",
      newPwdCheckVal : true,
      newPwdConfirmCheckVal : true,
      current:{
        newPwd : "",
        userid : "",
      }
    };
  },
  methods: {
    ...mapActions(memberStore, ["userModify"]),
    async modifyPwd(){
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
    newPwdCheck(event){
      event.preventDefault();
      let resultDiv = document.querySelector("#newpwdcheck-result");
      resultDiv.setAttribute("class", "mb-3");
      resultDiv.style.float="left";

      if(this.newPwd.length == 0){
        resultDiv.style.display = "none";
        this.pwdCheckValue = false;
      }
      else if(this.newPwd.length < 4){
        resultDiv.style.display = "block";
        resultDiv.style.color="red";
    		resultDiv.textContent ="비밀번호는 4자 이상 입력해주세요.";
        this.pwdCheckValue = false;
      }
      else{
        resultDiv.style.display = "block";
        resultDiv.style.color="blue";
    		resultDiv.textContent = "사용 가능한 비밀번호입니다.";
        this.pwdCheckValue = true;
      }
    },
    newPwdConfirmCheck(event){
      event.preventDefault();
      let resultDiv = document.querySelector("#newpwdconfirmcheck-result");
      resultDiv.setAttribute("class", "mb-3");
      resultDiv.style.float="left";

      if(this.newPwdConfirm.length == 0){
        resultDiv.style.display = "none";
        this.newPwdConfirmCheckVal = false;
      }
      else if(this.newPwdConfirm.length < 4){
        resultDiv.style.display = "block";
        resultDiv.style.color="red";
    		resultDiv.textContent ="비밀번호는 4자 이상 입력해주세요.";
        this.newPwdConfirmCheckVal = false;
      }
      else if((this.newPwdConfirm == this.newPwd) &&(this.newPwdConfirm.length > 3)){
        resultDiv.style.display = "block";
        resultDiv.style.color="blue";
    		resultDiv.textContent = "입력된 비밀번호가 일치합니다.";
        this.newPwdConfirmCheckVal = true;
       }
      else{
        resultDiv.style.display = "block";
        resultDiv.style.color="red";
    		resultDiv.textContent = "입력된 비밀번호가 일치하지 않습니다.";
        this.newPwdConfirmCheckVal = false;
       }
    },
    onSubmit(event) {
      event.preventDefault();
      if(this.newPwd==""){
        alert("신규 비밀번호를 입력해주세요.");
      }
      else if(this.newPwdConfirm==""){
        alert("신규 비밀번호 확인을 입력해주세요.");
      }
      else if(!this.newPwdCheckVal || !this.pwdCheckValue){
        alert("비밀번호 정보를 다시 확인해주세요.");
      }
      else{
        this.change();
      }
    },
    change(){
        this.current.newPwd = this.newPwd;
        this.current.userid = this.userInfo.userid;
        http
        .post(`/user/change`,this.current)
        .then(({ data }) => {
          let msg = "비밀번호 변경 중 문제가 발생했습니다.";
          if (data.message == "success") {
            msg = "비밀번호 변경이 완료되었습니다.";
          }
          alert(msg);
          this.mainPage();
        });  
    },
    myPage() {
      let check = confirm("회원 정보 페이지로 되돌아가시겠습니까?");
      if(check){
          this.$router.push({ name: "mypage" });
      }
    },
    mainPage() {
      this.$router.push({ name: "main" });
    },
  },
};
</script>

<style></style>
