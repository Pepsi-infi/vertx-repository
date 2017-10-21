<template>
  <div class="panel" style="padding: 15px;">
    <panel-title :title="$route.meta.title">
      <el-button type="primary" icon="plus" size="small" @click="addRow()">添加</el-button>
    </panel-title>
    <el-row justify="space-between" style="margin-top: 12px;">
      <el-col :span="6">
        <el-input v-model="queryWord" placeholder="敏感词" style="margin-left: 18px;">
        </el-input>
      </el-col>
      <el-col :span="4" style="margin-left: 30px;">
        <el-button icon="search" class="" @click="search">搜索</el-button>
      </el-col>
    </el-row>
    <div class="panel-body">
      <el-table
        :data="table_data"
        v-loading="load_data"
        element-loading-text="拼命加载中"
        border
        @selection-change=""
        style="width: 100%;">
        <el-table-column
          prop="id"
          label="id"
          width="200">
        </el-table-column>
        <el-table-column
          prop="word"
          label="敏感词"
          width="190">
        </el-table-column>
        <el-table-column
          prop="status"
          label="状态"
          width="100" :formatter="formatter">
        </el-table-column>
        <el-table-column
          label="操作"
          width="300">
          <template scope="props">
            <el-button type="info" size="small" icon="edit" v-if="props.row.status === 1" @click="updateRow(props.row)">
              修改
            </el-button>
            <el-button type="danger" size="small" icon="delete" v-if="props.row.status === 1" @click="deleteRow(props.row)">
              删除
            </el-button>
            <el-button type="info" size="small" icon="edit" v-if="props.row.status === 1" @click="online(props.row)">
              上线
            </el-button>
            <el-button type="info" size="small" icon="edit" v-if="props.row.status === 5" @click="offline(props.row)">
              下线
            </el-button>
          </template>
        </el-table-column>
      </el-table>
      <bottom-tool-bar>
        <div slot="page">
          <el-pagination
            @current-change="handleCurrentChange"
            :current-page="currentPage"
            :page-size="15"
            layout="total, prev, pager, next"
            :total="total">
          </el-pagination>
        </div>
      </bottom-tool-bar>
    </div>
  </div>
</template>
<script type="text/javascript">
  import Vue from 'vue';
  import ElementUI from 'element-ui';
  Vue.use(ElementUI);
  import {panelTitle, bottomToolBar} from 'components'

  const STATUS = {
    status: {
      1: '下线',
      5: '上线'
    }
  }

  export default{
    data(){
      return {
        table_data: null,
        //当前页码
        currentPage: 1,
        //数据总条目
        total: 0,
        //每页显示多少条数据
        length: 15,
        //请求时的loading效果
        load_data: true,
        queryWord: ''
      }
    },
    components: {
      panelTitle,
      bottomToolBar
    },
    created(){
      this.get_table_data(1)
    },
    methods: {
      //刷新
      on_refresh(){
        this.get_table_data(1)
      },
      //获取数据
      get_table_data(page = this.currentPage){
        this.load_data = true
        this.$fetch.api_sensitiveWord.list({
          page: page,
          size: this.length,
          word: this.queryWord
        })
          .then(({data: {list, page, total}}) => {
            this.table_data = list
            this.currentPage = page
            this.total = total
            this.load_data = false

          })
          .catch(() => {
            this.load_data = false
          })
      },
      formatter(row, column) {
        return STATUS[column.property][row[column.property]];
      },
      search() {
        this.get_table_data(1);
      },
      //单个删除
      delete_data(item){
        this.$confirm('此操作将删除该数据, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        })
          .then(() => {
            this.load_data = true
            this.$fetch.api_msgStat.del(item)
              .then(({msg}) => {
                this.get_table_data(1)
                this.$message.success(msg)
              })
              .catch(() => {
              })
          })
          .catch(() => {
          })
      },
      //页码选择
      handleCurrentChange(val) {
        this.currentPage = val
        this.get_table_data(val)
      },
      addRow(){
        this.$router.push(`/sensitiveWord/sensitiveWord/0`);
      },
      updateRow(row){
        this.$router.push(`/sensitiveWord/sensitiveWord/${row.id}`);
      },
      deleteRow(row){
        this.$confirm('此操作将删除该数据, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        })
          .then(() => {
            this.load_data = true
            console.log(row.id)
            this.$fetch.api_sensitiveWord.del({
              id: row.id
            })
              .then(({msg}) => {
                this.get_table_data()
                this.$message.success(msg)
              })
              .catch(() => {
              })
          })
          .catch(() => {
          })
      },
      //上线
      online(row){
        var finalForm = {
          id: row.id,
          word: row.word,
          status: 5
        };

        this.$fetch.api_sensitiveWord.save(finalForm)
          .then((resp) => {
            this.$message({
            message: '上线成功！',
            type: 'success'
            });
            this.get_table_data();
          });
      },
      //下线
      offline(row){
        var finalForm = {
          id: row.id,
          word: row.word,
          status: 1
        };

        this.$fetch.api_sensitiveWord.save(finalForm)
          .then((resp) => {
            this.$message({
            message: '下线成功！',
            type: 'success'
            });
            this.get_table_data();
          });
      }
    }
  }
</script>

