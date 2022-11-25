<template>
  <div>
    <b-navbar toggleable="lg" type="dark" variant="teal">
      <b-collapse id="nav-collapse" is-nav>
      
      <!-- After Login(Left) -->
      <b-navbar-nav class="mr-auto" v-if="userInfo">
          <b-nav-item-dropdown>
            <template #button-content>
            <b-icon icon="list" font-scale="2" variant="dark"></b-icon>
            </template>

            <b-dropdown-item href="#">
              <router-link :to="{ name: 'board' }" class="link" style="color:black;"> <b-icon icon="megaphone"></b-icon>&nbsp;&nbsp; 공지사항 </router-link>
            </b-dropdown-item>
            <b-dropdown-item href="#" v-if="userInfo.userclass=='admin'">
              <router-link :to="{ name: 'list' }" class="link" style="color:black;">
                <b-icon icon="people"></b-icon>&nbsp;&nbsp; 회원관리
              </router-link>
            </b-dropdown-item>
            <b-dropdown-item href="#" v-if="userInfo.userclass=='admin'">
              <router-link :to="{ name: 'house' }" class="link" style="color:black;">
                <b-icon icon="currency-dollar"></b-icon>&nbsp;&nbsp; 매매관리
              </router-link>
            </b-dropdown-item>
            <b-dropdown-item href="#" v-else>
              <router-link :to="{ name: 'house' }" class="link" style="color:black;">
                <b-icon icon="currency-dollar"></b-icon>&nbsp;&nbsp; 매매조회
              </router-link>
            </b-dropdown-item>
            <b-dropdown-item href="#">
              <router-link :to="{ name: 'qna' }" class="link" style="color:black;">
                <b-icon icon="question-circle"></b-icon>&nbsp;&nbsp; Q & A
              </router-link>
            </b-dropdown-item>

          </b-nav-item-dropdown>

          <b-nav-item-dropdown style="margin-left:32px; visibility : hidden;">
            <template #button-content>
            <b-icon icon="list" font-scale="2" variant="dark"></b-icon>
            </template>
          </b-nav-item-dropdown>
                    <b-nav-item-dropdown style="margin-left:8px; visibility : hidden;">
            <template #button-content>
            <b-icon icon="list" font-scale="2" variant="dark"></b-icon>
            </template>
          </b-nav-item-dropdown>
        </b-navbar-nav>

      <!-- Pre Login(Left) -->
      <b-navbar-nav class="mr-auto" v-else>
          <b-nav-item-dropdown>
            <template #button-content>
            <b-icon icon="list" font-scale="2" variant="dark"></b-icon>
            </template>

            <b-dropdown-item href="#">
              <router-link :to="{ name: 'board' }" class="link" style="color:black;"> <b-icon icon="megaphone"></b-icon>&nbsp;&nbsp; 공지사항 </router-link>
            </b-dropdown-item>
            <b-dropdown-item href="#">
              <router-link :to="{ name: 'house' }" class="link" style="color:black;">
                <b-icon icon="currency-dollar"></b-icon>&nbsp;&nbsp; 매매조회
              </router-link>
            </b-dropdown-item>
            <b-dropdown-item href="#">
              <router-link :to="{ name: 'qna' }" class="link" style="color:black;">
                <b-icon icon="question-circle"></b-icon>&nbsp;&nbsp; Q & A
              </router-link>
            </b-dropdown-item>
          </b-nav-item-dropdown>

        </b-navbar-nav>

      <!-- Pre Login(Center Logo) -->
      <b-navbar-brand href="#">
        <router-link :to="{ name: 'main' }">
          <b-img
            :src="require('@/assets/logo.png')"
            id="logo"
            class="d-inline-block align-top"
            alt="logo"
            style="width:50%; height:50%;"
          ></b-img>
        </router-link>
      </b-navbar-brand>

        <!-- After Login(Right) -->
        <b-navbar-nav class="ml-auto" v-if="userInfo">
          <b-nav-item class="align-self-center"></b-nav-item>
            <b-nav-item-dropdown right>
            <template #button-content>
              <b-avatar variant="dark" icon="
bookmark-star-fill"></b-avatar>
            </template>
            <b-dropdown-item v-on:click="onClickMypage" href="#">
                  <b-icon icon="star"></b-icon>&nbsp;&nbsp; 관심지역 설정
                </b-dropdown-item>
                <b-dropdown-item @click.prevent="onClickLogout" href="#">
                  <b-icon icon="zoom-in"></b-icon>&nbsp;&nbsp; 환경정보 조회
                </b-dropdown-item>
            </b-nav-item-dropdown>

          <b-nav-item-dropdown right>
            <template #button-content>
              <b-avatar variant="dark" icon="
messenger"></b-avatar>
            </template>
            <b-dropdown-item v-on:click="onClickMypage" href="#">
                  <b-icon icon="cursor"></b-icon>&nbsp;&nbsp; 메시지 전송
                </b-dropdown-item>
                <b-dropdown-item @click.prevent="onClickLogout" href="#">
                  <b-icon icon="envelope"></b-icon>&nbsp;&nbsp; 받은 메시지함
                </b-dropdown-item>
            </b-nav-item-dropdown>
          <b-nav-item-dropdown right>
            <template #button-content>
              <b-avatar variant="dark" icon="person-fill"></b-avatar>
            </template>
            <b-dropdown-item v-on:click="onClickMypage" href="#">
                  <b-icon icon="person"></b-icon>&nbsp;&nbsp; 마이페이지
                </b-dropdown-item>
                <b-dropdown-item @click.prevent="onClickLogout" href="#">
                  <b-icon icon="key"></b-icon>&nbsp;&nbsp; 로그아웃
                </b-dropdown-item>
            </b-nav-item-dropdown>
        </b-navbar-nav>

        <!-- Pre Login(Right) -->
        <b-navbar-nav class="ml-auto" v-else>
          <b-nav-item-dropdown right>
            <template #button-content>
              <b-icon icon="grid" font-scale="2" variant="dark"></b-icon>
            </template>
            <b-dropdown-item href="#">
              <router-link :to="{ name: 'login' }" class="link" style="color:black;"> <b-icon icon="key"></b-icon> &nbsp;&nbsp; 로그인 </router-link>
            </b-dropdown-item>
            <b-dropdown-item href="#">
              <router-link :to="{ name: 'join' }" class="link" style="color:black;">
                <b-icon icon="person-plus"></b-icon>&nbsp;&nbsp; 회원가입
              </router-link>
            </b-dropdown-item>
          </b-nav-item-dropdown>
        </b-navbar-nav>
      </b-collapse>
    </b-navbar>
    <hr/>
  </div>
</template>

<script>
import { mapState, mapGetters, mapActions } from "vuex";

const memberStore = "memberStore";

export default {
    name: "TheHeaderNavbar",
  data() {
    return {};
  },
  computed: {
    ...mapState(memberStore, ["isLogin", "userInfo"]),
    ...mapGetters(["checkUserInfo"]),
  },
  methods: {
    ...mapActions(memberStore, ["userLogout"]),
    onClickLogout() {
      console.log(this.userInfo);
      this.userLogout(this.userInfo.userid);
      sessionStorage.removeItem("access-token");
      sessionStorage.removeItem("refresh-token"); 
      if (this.$route.path != "/") this.$router.push({ name: "main" });
    },
    onClickMypage(){
      this.$router.push({ name: "mypage" });
    }
  },
};
</script>

<style>
#logo {
  width: 120px;
}

.link {
  text-decoration: none;
}

::-webkit-scrollbar {
  display: none;
}
</style>
