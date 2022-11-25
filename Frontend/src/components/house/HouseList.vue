<template>
  <b-container fluid v-if="houses && houses.length != 0">
    <b-row class="mb-3">
      <b-col sm="5" md="3" class="my-1">
          <b-form-select
            id="per-page-select"
            v-model="perPage"
            :options="pageOptions"
            size="sm"
          ></b-form-select>
      </b-col>
      <b-col sm="5" md="3" class="my-1"></b-col>
      <b-col lg="6" class="my-1">
          <b-input-group size="sm">
            <b-form-input
              id="filter-input"
              v-model="filter"
              type="search"
              placeholder="아파트 이름을 입력하세요"
            ></b-form-input>

            <b-input-group-append>
              <b-button :disabled="!filter" @click="filter = ''">Clear</b-button>
            </b-input-group-append>
          </b-input-group>
      </b-col>

    </b-row>
    <b-table
      :items="this.houses"
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
      small
      @filtered="onFiltered()"
      striped hover
    >
    </b-table>
    
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
import { mapState } from "vuex";

const houseStore = "houseStore";

  export default {
    name: "HouseList",
    components: {
    },
    data() {
      return {
        articles: [],
        items: [],
        fields: [
          {key: 'aptName', label: '아파트 이름', sortable: true, class: 'text-center' },
          {key: 'floor', label: '층', sortable: true, class: 'text-center' },
          {key: 'area', label: '면적', sortable: true, class: 'text-center' },
          {key: 'dealYear', label: '거래일자', sortable: true, class: 'text-center' },
          {key: 'recentPrice', label: '거래가격(만원)', sortable: true, class: 'text-center' },
        ],
        totalRows: 1,
        currentPage: 1,
        perPage: 10,
        pageOptions: [{value:10, text:"페이지당 글 개수"}, {value:10, text:"10개"}, {value:25, text:"25개"}, {value:50, text:"50개"}, { value: 1000, text: "100개 이상" }],
        sortBy: 'aptName',
        sortDesc: true,
        sortDirection: 'asc',
        filter: '',
        filterOn: ['aptName'],
      }
    },
    created() {
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
        return this.houses.length;
      },
      ...mapState(houseStore, ["houses"]),
    },
    mounted() {
      this.totalRows = this.houses.length
    },
    methods: {
      onFiltered(filteredItems) {
        this.totalRows = filteredItems.length
        this.currentPage = 1
      },
    }
  }
</script>

<style>
 /* link - 아직 클릭하지 않은 경우 red 색상 설정 */
 a:link { color: black; text-decoration: none;}	
 /* visited - 한번 클릭하거나 전에 클릭한적 있을 경우 #c71d44 설정 */
 a:visited { color: black; text-decoration: none;}	
 /* hover - 마우스를 해당 링크에 위치했을 경우 #006DD7 설정 */
 a:hover { color: black; text-decoration: underline;}
 .page-link {color:#BBBBBB;}
 .page-item.active .page-link {background-color:#AAAAAA; border-color:#AAAAAA;}
</style>