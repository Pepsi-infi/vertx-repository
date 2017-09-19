<template>
  <div class="panel" style="padding: 15px;">
    <panel-title :title="$route.meta.title">

    </panel-title>
    <el-row justify="space-between" style="margin-top: 12px;">
      <el-col :span="6">
        <el-input v-model="driverName" placeholder="司机姓名"  style="margin-left: 18px;">
        </el-input>
      </el-col>
      <el-col :span="6">
        <el-input v-model="phone" placeholder="手机号"  style="margin-left: 18px;">
        </el-input>
      </el-col>
      <el-col :span="6" >
      	<div class="block">
      		<el-select v-model="cityId" placeholder="所在城市" >
      			<el-option v-for="item in cityOptions" :key="item.value" :label="item.label" :value="item.value">
      			</el-option>
      		</el-select>
      	</div>
      </el-col>
      <el-col :span="6" >
      	<div class="block">
      		<el-select v-model="providerId" placeholder="供应商" >
      			<el-option v-for="item in providerOptions" :key="item.value" :label="item.label" :value="item.value">
      			</el-option>
      		</el-select>
      	</div>
      </el-col>
    </el-row>
    <el-row>
    	<el-col :span="8" style="margin-left: 30px;">
       		<el-button icon="search" class=""  @click="search">查询</el-button>
       		<el-button type="primary" class=""  @click="confirm">确定</el-button>
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
        <el-table-column prop="driverId"  label="序号" width="190">
        </el-table-column>
        <el-table-column prop="driverName" label="司机姓名" width="100">
        </el-table-column>
        <el-table-column prop="手机号" label="phone" width="100">
        </el-table-column>
        <el-table-column prop="车牌号" label="carNumber" width="100">
        </el-table-column>
        <el-table-column prop="服务城市" label="cityId" width="100">
        </el-table-column>
        <el-table-column prop="所属供应商" label="providerId" width="100">
        </el-table-column>
      </el-table>
      <bottom-tool-bar>
        <div slot="page">
            <el-pagination
              @size-change="handleSizeChange"
              @current-change="handleCurrentChange"
              :current-page="currentPage"
              :page-sizes="[10, 20, 50, 100]"
              :page-size="pageSize"
              layout="total, sizes, prev, pager, next, jumper"
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
        driverName:'',
        phone:'',
        cityOptions:[],
        providerOptions:[],    
        cityId:'',
        providerId:''
           
      }
    },
    components: {
      panelTitle,
      bottomToolBar
    },
    created(){
      this.getProviderList();
      this.getCityList();
      this.get_table_data(1)
    },
    methods: {
      //刷新
      on_refresh(){
        this.get_table_data(1)
      },
      //获取供应商列表
      getProviderList(){
      	this.$fetch.api_driverMsg.providerList()
      	.then(({code,msg,data})=>{
      		data.forEach((element,index)=>{
      			if(element!==null){
      				let e={};
      				e.label=element.providerName;
      				e.value=element.providerId+"";
      				this.providerOptions.push(e);
      			}
      		});
      	})
      },
      //获取城市列表
      getCityList(){
      	this.$fetch.api_driverMsg.cityList()
      	.then(({code,msg,data})=>{
      		data.forEach((element,index)=>{
      			if(element!==null){
      				let e={};
      				e.label=element.cityName;
      				e.key=element.cityId+"";
      				this.cityOptions.push(e);
      			}
      		});
      	})
      },         
      //获取数据
      get_table_data(page = this.currentPage){
       	this.load_data = true;
       	          
        this.$fetch.api_driverMsg.driverList({
          page: page,
          size: this.length,
          driverName: this.driverName,
          phone: this.phone,
       	  cityId: this.cityId,
       	  providerId: this.providerId
        })
          .then(({code, msg, data}) => {
            let tempList = [];
            data.list.forEach((ele, index) => {
              if (ele !== null) {
                tempList.push(ele);
              }
            });
            this.table_data = data.list
            this.currentPage = data.pageNumber
            this.total = data.total
            this.load_data = false

          })
          .catch(() => {
            this.load_data = false
          })
      },
      //查询
      search() {
        this.get_table_data(1);
      },
      //确认选择
      confirm(){
      	
      },
      //页码选择
      handleCurrentChange(val) {
        this.currentPage = val
        this.get_table_data(val)
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

