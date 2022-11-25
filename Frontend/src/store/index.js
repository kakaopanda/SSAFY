import Vue from "vue";
import Vuex from "vuex";
import createPersistedState from "vuex-persistedstate";
// module import
import boardStore from "@/store/modules/boardStore";
import houseStore from "@/store/modules/houseStore";
import memberStore from "@/store/modules/memberStore";
import todoStore from "@/store/modules/todoStore";

Vue.use(Vuex);

export default new Vuex.Store({
  modules: {
    boardStore,
    houseStore,
    memberStore,
    todoStore,
  },
  plugins: [
    createPersistedState({
      storage: sessionStorage,
    }),
  ],
});
