<template>
  <div class="panel" style="padding: 15px;">
    <panel-title :title="$route.meta.title">

    </panel-title>
    <el-row justify="space-between" style="margin-top: 12px;">
        <el-col :span="6">
           <el-input v-model="queryMsgId" placeholder="消息Id"  style="margin-left: 18px;">
           </el-input>
        </el-col>
        <el-col :span="4" style="margin-left: 30px;">
            <el-button icon="search" class=""  @click="search">搜索</el-button>
        </el-col>
    </el-row>
    <div class="panel-body">
      <el-table
        :data="table_data"
        v-loading="load_data"
        element-loading-text="拼命加载中"
        border
        @selection-change="on_batch_select"
        style="width: 100%;">
        <el-table-column
          prop="msgId"
          label="消息id"
          width="200">
        </el-table-column>
        <el-table-column
          prop="statTime"
          label="统计时间"
          width="190">
        </el-table-column>
        <el-table-column
          prop="sendSum"
          label="发送总数"
          width="100">
        </el-table-column>
        <el-table-column
          prop="arriveSum"
          label="到达总数"
          width="100">
        </el-table-column>
        <!--
        <el-table-column
          prop="clickSum"
          label="点击总数"
          width="100">
        </el-table-column>
        -->
        <el-table-column
          prop="sendAndroidSum"
          label="Android发送数"
          width="100">
        </el-table-column>
        <el-table-column
          prop="arriveAndroidSum"
          label="Android到达数"
          width="100">
        </el-table-column>
        <el-table-column
          prop="clickAndroidSum"
          label="Android点击数"
          width="100">
        </el-table-column>
        <el-table-column
           prop="sendIosSum"
           label="IOS发送数"
           width="100">
        </el-table-column>
        <el-table-column
          prop="arriveIosSum"
          label="IOS到达数"
          width="100">
        </el-table-column>
        <!--
        <el-table-column
          prop="clickIosSum"
          label="IOS点击数"
          width="100">
        </el-table-column>
        -->
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
        //批量选择数组
        batch_select: [],
        queryMsgId:''
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
        var msgId = this.queryMsgId;
        var searchMsgId;
        if(msgId !== undefined){
            let pos = msgId.lastIndexOf('_');
            searchMsgId = msgId.substring(pos+1, msgId.length);
        }
        this.$fetch.api_msgStat.list({
          page: page,
          size: this.length,
          msgId: searchMsgId
        })
          .then(({data: {list, page, total}}) => {
            let tempList = [];
            list.forEach((ele, index) => {
                if (ele !== null) {
                    tempList.push(ele);
                }
                ele.msgId = 'AD_PASSENGER_COUNT_'+ele.msgId;
            });
            this.table_data = list
            this.currentPage = page
            this.total = total
            this.load_data = false

          })
          .catch(() => {
            this.load_data = false
          })
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
      //批量选择
      on_batch_select(val){
        this.batch_select = val
      },
      //批量删除
      on_batch_del(){
        this.$confirm('此操作将批量删除选择数据, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        })
          .then(() => {
            this.load_data = true
            this.$fetch.api_msgStat.batch_del(this.batch_select)
              .then(({msg}) => {
                this.get_table_data(1)
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

