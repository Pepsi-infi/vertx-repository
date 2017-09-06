<template>
  <div class="panel" style="padding: 15px;">
    <panel-title :title="$route.meta.title">
      <el-button type="primary" icon="plus" size="small" @click="addRow()">添加</el-button>
    </panel-title>

    <div class="panel-body">
      <el-table
        :data="table_data"
        v-loading=""
        element-loading-text="拼命加载中"
        border
        @selection-change=""
        style="width: 100%;">
        <el-table-column
          prop="weight"
          label="排序"
          width="0">
        </el-table-column>
        <el-table-column
          prop="content"
          label="内容"
          width="300">
        </el-table-column>
        <el-table-column
          label="操作"
          width="200">
          <template scope="props">
            <el-button type="info" size="small" icon="edit" @click="updateRow(props.row)">修改</el-button>
            <el-button type="danger" size="small" icon="delete" @click="deleteRow(props.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>

    </div>
  </div>
</template>
<script type="text/javascript">
  import Vue from 'vue';
  import ElementUI from 'element-ui';
  Vue.use(ElementUI);
  import {panelTitle, bottomToolBar} from 'components'

  export default{
    data(){
      return {
        table_data: null
      }
    },
    components: {
      panelTitle,
      bottomToolBar
    },
    created(){
      this.get_table_data()
    },
    methods: {
      //获取数据
      get_table_data(){
        this.load_data = true
        this.$fetch.api_imCommonLanguage.list({
          type: this.$route.params.type
        })
          .then(({data}) => {
            console.log("data" + data)
            this.table_data = data
            this.load_data = false

          })
          .catch(() => {
            this.load_data = false
          })
      },
      addRow(){
        this.$router.push(`/imCommonLanguage/${this.$route.params.type}/commonLanguage/0`);
      },
      updateRow(row){
        this.$router.push(`/imCommonLanguage/${this.$route.params.type}/commonLanguage/${row.id}`);
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
            this.$fetch.api_imCommonLanguage.del({
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
      }

    }
  }
</script>

