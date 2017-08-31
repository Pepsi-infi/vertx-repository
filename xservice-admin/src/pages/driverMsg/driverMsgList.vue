<template>
  <div class="panel" style="padding: 15px;">
    <panel-title :title="$route.meta.title">

    </panel-title>
    <el-row justify="space-between" style="margin-top: 12px;">
      <el-col :span="6">
        <el-input v-model="title" placeholder="消息标题"  style="margin-left: 18px;">
        </el-input>
      </el-col>
      <el-col :span="6" >
      	<div class="block">
      		<el-select v-model="msgType" placeholder="消息类型" >
      			<el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value">
      			</el-option>
      		</el-select>
      	</div>
      </el-col>
      <el-col :span="6">
      	<div class="block">
      		<el-date-picker v-model="startTime" type="datetime" placeholder="开始时间" :picker-options="pickerOption">
      		</el-date-picker>
      	</div>
      </el-col>
      <el-col :span="6">
      	<div class="block">
      		<el-date-picker v-model="endTime" type="datetime" placeholder="结束时间" :picker-options="pickerOption">
      		</el-date-picker>
      	</div>
      </el-col>
    </el-row>
    <el-row>
    	<el-col :span="8" style="margin-left: 30px;">
       		<el-button icon="search" class=""  @click="search">搜索</el-button>
          	<el-button type="primary" icon="plus"  @click="addDriverMsg">新增消息</el-button>
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
          prop="id"
          label="id"
          width="200">
        </el-table-column>
        <el-table-column
          prop="title"
          label="消息标题"
          width="190">
        </el-table-column>
        <el-table-column
          prop=""
          label="消息类型"
          width="100">
        </el-table-column>
        <el-table-column
          prop=""
          label="已读人数"
          width="100">
        </el-table-column>
        <el-table-column
          prop=""
          label="未读人数"
          width="100">
        </el-table-column>
        <el-table-column
          prop=""
          label="创建时间"
          width="100">
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
        title:"",
        options:[
        	{
        	value:'1',
        	label:'系统消息'
        	}
        ],
        msgType:'',
        pickerOption:{
          shortcuts: [
          {
            text: '今天',
            onClick(picker) {
              picker.$emit('pick', new Date());
            }
          }, 
          {
            text: '昨天',
            onClick(picker) {
              const date = new Date();
              date.setTime(date.getTime() - 3600 * 1000 * 24);
              picker.$emit('pick', date);
            }
          }, 
          {
            text: '一周前',
            onClick(picker) {
              const date = new Date();
              date.setTime(date.getTime() - 3600 * 1000 * 24 * 7);
              picker.$emit('pick', date);
           }
          }]
        },
        startTime: '',
        endTime: ''
            
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
       	this.load_data = true;
       	          
        this.$fetch.api_driverMsg.list({
          page: page,
          size: this.length,
          title: this.title,
          msgType: this.msgType,
       	  startTime: this.startTime,
       	  endTime: this.endTime
        })
          .then(({data: {list, pageNumber, total}}) => {
          	alert(data);
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

