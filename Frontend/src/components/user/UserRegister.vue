<template>
  <b-container class="bv-example-row mt-4">
    <b-row>
      <b-col>
        <b-alert variant="dark" show><h3>Sign up</h3></b-alert>
      </b-col>
    </b-row>
    <br>

    <b-row>
      <b-col cols="12">
        <b-card class="text-center" style="max-width: 80rem">
          <b-form @submit="onSubmit" @reset="onReset">
			      <input type="hidden" name="act" value="join">
            <div class="mb-3">
              <label style="float:left;" for="username" class="form-label">이름(Name) : </label>
              <b-form-input
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
              <b-form-input
                type="text"
                class="form-control"
                id="userid"
                name="userid"
                placeholder="ID"
                v-model="userInfo.userid"
                @keyup="idCheck"
                clear="both" 
              />
            </div>
            <div id="idcheck-result"></div>
            <div class="mb-3">
              <label style="float:left; clear:both;" for="userpwd" class="form-label">비밀번호(Password) : </label>
              <b-form-input
                type="password"
                class="form-control"
                id="userpwd"
                name="userpwd"
                placeholder="Password"
                v-model="userInfo.userpwd"
                @keyup="pwdLengthCheck"
              />
            </div>
            <div id="pwdlengthcheck-result"></div>
            <div class="mb-3">
              <label style="float:left; clear:both;" for="pwdcheck" class="form-label">비밀번호 확인(Confirm) : </label>
              <input type="password" class="form-control" id="pwdcheck" placeholder="Password Confirm" v-model="pwdCheckVal" @keyup="pwdCheck"/>
            </div>
            <div id="pwdcheck-result"></div>
            <div class="mb-3">
              <label style="float:left; clear:both;" for="emailid" class="form-label">이메일(E-mail) : </label>
              <div class="input-group">
                <b-form-input
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
            </div>
            <div class="mb-3">
              <label style="float:left;" for="userclass" class="form-label">회원 구분(Class) : </label>
              <div class="input-group">
                <select
                  class="form-select"
                  id="userclass"
                  name="userclass"
                  aria-label="회원 등급 선택"
                  select style="height:35px; width:100%;"
                  v-model="userInfo.userclass"
                >
                  <option selected>회원 등급 선택</option>
                  <option value="admin">관리자 회원</option>
                  <option value="user">일반 회원</option>
                </select>
              </div>
            </div>
            <div class="col-auto text-center">
              <b-button type="submit" variant="light" class="m-1">회원가입</b-button>
            <b-button type="button" variant="dark" class="m-1" @click="mainPage">메인페이지</b-button>
            </div>
          </b-form>
        </b-card>
      </b-col>
    </b-row>
  </b-container>
</template>

<script>
import http from "@/api/index";

export default {
  name: "UserRegister",
  data() {
    return {
      idCheckValue : true,
      pwdCheckValue : true,
      userInfo: {
        username: "",
        userid: "",
        userpwd: "",
        emailid: "",
        emaildomain: "도메인 선택",
        userclass: "회원 등급 선택",
      },
      pwdCheckVal : "",
    };
  },
  methods: {
    idCheck(event){
       event.preventDefault();
       let userid = this.userInfo.userid;
    	 let resultDiv = document.querySelector("#idcheck-result");
    	 
       if(userid.length === 0){
        resultDiv.style.display = "none";
        this.idCheckValue = false;
       }
       else if(userid.length < 5 || userid.length > 16) {
    		 resultDiv.setAttribute("class", "mb-3");
         resultDiv.style.display = "block";
         resultDiv.style.float="left";
         resultDiv.style.color="red";
    		 resultDiv.textContent = "아이디는 5자 이상 16자 이하로 입력해주세요.";
         this.idCheckValue = false;
    	 }
       else{
        resultDiv.setAttribute("class", "mb-3");
        resultDiv.style.display = "block";
        resultDiv.style.float="left";

        let userid = this.userInfo.userid;

        http
        .get(`/user/check/${userid}`)
        .then(({ data }) => {
          if (data.message == "success") {
            resultDiv.style.color="blue";
    		    resultDiv.textContent = "아이디 사용이 가능합니다.";
            this.idCheckValue = true;
          }
          else{
          resultDiv.style.color="red";
    		  resultDiv.textContent = "중복된 아이디가 존재합니다.";
          this.idCheckValue = false;
          }
        });
       }
    },
    
    pwdLengthCheck(event){
      event.preventDefault();
      let userpwd = this.userInfo.userpwd;
    	let resultDiv = document.querySelector("#pwdlengthcheck-result");
      resultDiv.setAttribute("class", "mb-3");
      resultDiv.style.float="left";

      if(userpwd.length == 0){
        resultDiv.style.display = "none";
        this.pwdCheckValue = false;
      }
      else if(userpwd.length>=4){
        resultDiv.style.display = "block";
        resultDiv.style.color="blue";
    		resultDiv.textContent = "비밀번호 사용이 가능합니다.";
        this.pwdCheckValue = true;
       }
      else{
        resultDiv.style.display = "block";
        resultDiv.style.color="red";
    		resultDiv.textContent = "비밀번호는 4자 이상 입력해주세요.";
        this.pwdCheckValue = false;
       }
    },
    pwdCheck(event){
      event.preventDefault();
      let userpwd = this.userInfo.userpwd;
    	let resultDiv = document.querySelector("#pwdcheck-result");
      resultDiv.setAttribute("class", "mb-3");
      resultDiv.style.float="left";

      if(this.pwdCheckVal.length == 0){
        resultDiv.style.display = "none";
        this.pwdCheckValue = false;
      }
      else if(this.pwdCheckVal.length < 4){
        resultDiv.style.display = "block";
        resultDiv.style.color="red";
    		resultDiv.textContent ="비밀번호는 4자 이상 입력해주세요.";
        this.pwdCheckValue = false;
      }
      else if((this.pwdCheckVal == userpwd) &&(this.pwdCheckVal.length > 3)){
        resultDiv.style.display = "block";
        resultDiv.style.color="blue";
    		resultDiv.textContent = "입력된 비밀번호가 일치합니다.";
        this.pwdCheckValue = true;
       }
      else{
        resultDiv.style.display = "block";
        resultDiv.style.color="red";
    		resultDiv.textContent = "입력된 비밀번호가 일치하지 않습니다.";
        this.pwdCheckValue = false;
       }
    },
    onSubmit(event) {
      event.preventDefault();
      if(this.userInfo.username==""){
        alert("이름을 입력해주세요.");
      }
      else if(this.userInfo.userid=="" || !this.idCheckValue){
        alert("아이디 정보를 다시 확인해주세요.");
      }
      else if(this.userInfo.userpwd=="" || !this.pwdCheckValue){
        alert("비밀번호 정보를 다시 확인해주세요.");
      }
      else if(this.userInfo.emailid==""){
        alert("이메일 아이디를 입력해주세요.");
      }
      else if(this.userInfo.emaildomain=="도메인 선택"){
        alert("이메일 도메인을 선택해주세요.");
      }
      else if(this.userInfo.userclass=="회원 등급 선택"){
        alert("회원 구분을 선택해주세요.");
      }
      else{
        this.join();
      }
    },
    join(){
      http
        .post(`/user/join`,this.userInfo)
        .then(({ data }) => {
          let msg = "회원가입 중 문제가 발생했습니다.";
          if (data.message == "success") {
            msg = "회원가입이 완료되었습니다.";
          }
          alert(msg);
          this.mainPage();
        });
    },
    onReset(event) {
      event.preventDefault();
      this.article.articleno = 0;
      this.article.subject = "";
      this.article.content = "";
      this.mainPage();
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
    mainPage() {
      this.$router.push({ name: "main" });
    },
  },
};
</script>

<style></style>
