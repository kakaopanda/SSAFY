<template>
  <b-container class="bv-example-row text-center">
    <div style="text-align:left; font-size:30px;">
      <b-icon-question-circle></b-icon-question-circle>
      &nbsp; Q & A
      </div>
    <b-row class="mb-5">  
    </b-row>
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
              placeholder="게시글 제목을 입력해주세요"
            ></b-form-input>
            <b-input-group-append>
              <b-button size="sm" style="background-color:#AAAAAA" :disabled="!filter" @click="filter = ''">Clear</b-button>
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
      @row-clicked="viewArticle"
    >
    <template #cell(subject)="data">
      <router-link :to="{ name: 'qnaview', params: { articleno: data.item.articleno } }">
        {{ data.item.subject }}
      </router-link>
    </template>

    </b-table>
          <b-col class="text-right">
        <b-button variant="dark" @click="moveWrite()"
        v-if="this.userInfo.userclass === 'user'">질문 등록</b-button>
      </b-col>
<b-row>
          <b-col>
        <b-row class="mb-3"></b-row>
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
    data() {
      return {
        articles: [],
        items: [],
        fields: [
          {key: 'articleno', label: '번호', sortable: true, class: 'text-center' },
          {key: 'subject', label: '제목', sortable: true, class: 'text-center' },
          {key: 'userid', label: '작성자', sortable: true, class: 'text-center' },
          {key: 'regtime', label: '작성일', sortable: true, class: 'text-center' },
          {key: 'state', label: '상태', sortable: true, class: 'text-center' },          
          {key: 'hit', label: '조회수', sortable: true, class: 'text-center' },
        ],
        totalRows: 1,
        currentPage: 1,
        perPage: 10,
        pageOptions: [{value:10, text:"페이지당 글 개수"}, {value:10, text:"10개"}, {value:25, text:"25개"}, {value:50, text:"50개"}, { value: 1000, text: "100개 이상" }],
        sortBy: 'articleno',
        sortDesc: true,
        sortDirection: 'asc',
        filter: '',
        filterOn: ['subject'],
        userInfo : {},
      }
    },
    created() {
      http.get(`/qna`).then(({ data }) => {
        this.articles = data;
        for(const article of this.articles){
          let isAnswer = "답변 대기";
          if(article.answer !== null){
            isAnswer = "답변 완료";
          }          
          const item = {            
            articleno : article.articleno,
            subject: article.subject,
            userid : article.userid,
            regtime: article.regtime,
            hit: article.hit,
            state : isAnswer,            
          };
          this.items.push(item);
        }
      });
      if (this.$store.getters["memberStore/checkUserInfo"] !== null) {
        this.userInfo = this.$store.getters["memberStore/checkUserInfo"];
      }
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
      moveWrite() {
        this.$router.push({ name: "qnawrite" });
      },
      viewArticle(article) {
        this.$router.push({
          name: "qnaview",
          params: { articleno: article.articleno },
      });
    },

    }
  }
</script>