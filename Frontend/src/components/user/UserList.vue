<template>
  <b-container class="bv-example-row">
    <div style="text-align:left; font-size:30px;">
      <b-icon-people></b-icon-people>
      &nbsp; 회원관리
      </div>
    <b-row class="mb-5"></b-row>
    <b-row>
      <b-col sm="5" md="3" class="my-1">
          <b-form-select
            id="per-page-select"
            v-model="perPage"
            :options="pageOptions"
            size="sm"
          ></b-form-select>
      </b-col>
      <b-col sm="5" md="5" class="my-1"></b-col>
      <b-col lg="4" class="my-1">
          <b-input-group size="sm">
            <b-form-input
              id="filter-input"
              v-model="filter"
              type="search"
              placeholder="회원 이름을 입력하세요"
            ></b-form-input>

            <b-input-group-append>
              <b-button :disabled="!filter" @click="filter = ''">Clear</b-button>
            </b-input-group-append>
          </b-input-group>
      </b-col>
      <b-col lg="4" class="my-2"></b-col>
    </b-row>
    <b-table
      :items="items"
      :fields="fields"
      :current-page="currentPage"
      :per-page="perPage"
      :filter="filter"
      :filter-included-fields="filterOn"
      
      :sort-by.sync="sortBy"
      :sort-desc.sync="sortDesc"
      :sort-direction="sortDirection"
      stacked="md"
      show-empty
      big
      @filtered="onFiltered"
      striped hover
    >

  </b-table>
    <b-row class="mb-3"></b-row>
    <b-row>
      <b-col>
        <b-pagination
          v-model="currentPage"
          pills :total-rows="rows"
          :per-page="perPage"
          align="center"
          class="my-0"
        ></b-pagination>
      </b-col>
    </b-row>
  </b-container>
</template>

<script>
import http from "@/api/index";
  export default {
      name: "UserList",
    data() {
      return {
        articles: [],
        items: [],
        fields: [
          {key: "userid", label: "아이디", sortable: true, class: 'text-center' },
          { key: "username", label: "이름", sortable: true, class: 'text-center' },
          {key: "userpwd", label: "비밀번호", sortable: true, class: 'text-center' },
          {key: "email", label: "이메일", sortable: true, class: 'text-center' },
          { key: "joindate", label: "가입일", sortable: true, class: 'text-center' },
        ],
        totalRows: 1,
        currentPage: 1,
        perPage: 10,
        pageOptions: [{value:10, text:"페이지당 글 개수"}, {value:10, text:"10개"}, {value:25, text:"25개"}, {value:50, text:"50개"}, { value: 1000, text: "100개 이상" }],
        sortBy: 'userid',
        sortDesc: true,
        sortDirection: 'asc',
        filter: '',
        filterOn: ['username'],
      }
    },
    created() {
      http.get(`/user`).then(({ data }) => {
        this.articles = data;
        for(const article of this.articles){
          const item = {
            userid : article.userid,
            username: article.username,
            userpwd : article.userpwd,
            email: article.emailid+'@'+article.emaildomain,
            joindate: article.joindate,
          };
          this.items.push(item);
        }
      });
  },
    computed: {
      sortOptions() {
        return this.fields
          .filter(f => f.sortable)
          .map(f => {
            return { text: f.label, value: f.key }
          })
      },
      rows() {
        return this.items.length
      }
    },
    mounted() {
      this.totalRows = this.items.length
    },
    methods: {
      onFiltered(filteredItems) {
        this.totalRows = filteredItems.length
        this.currentPage = 1
      },
      viewArticle(article) {
        this.$router.push({
          name: "userview",
          params: { articleno: article.articleno },
        });
      },
    }
  }
</script>