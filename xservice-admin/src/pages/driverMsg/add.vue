<template>
<div>
<el-form ref="form" :rules="rules" :model="form" label-width="80px">
  <el-form-item label="消息标题" prop="title">
	  <el-input v-model="form.title"></el-input>
  </el-form-item>
  <el-form-item label="消息类型" prop="msgType">
	<el-select v-model="form.msgType" placeholder="请选择消息类型">
	  <el-option label="系统消息" value="1"></el-option>
	</el-select>
  </el-form-item>
  <el-form-item label="内容梗概" prop="synopsis">
	  <el-input v-model="form.synopsis"></el-input>
  </el-form-item>
  <el-form-item label="消息内容" prop="content">
	  <el-input type="textarea" :row="10" v-model="form.content"></el-input>
  </el-form-item>
  <el-form-item label="是否弹屏" >
    <el-radio-group v-model="form.isShellsScreen">
      <el-radio label="1">是</el-radio>
      <el-radio label="0">否</el-radio>
    </el-radio-group>
  </el-form-item>
  <el-form-item label="是否重要" >
    <el-radio-group v-model="form.isImportant">
      <el-radio label="1">是</el-radio>
      <el-radio label="0">否</el-radio>
    </el-radio-group>
  </el-form-item>
  <el-form-item label="跳转url">
	  <el-input v-model="form.jumpUrl"></el-input>
  </el-form-item>
  <el-form-item label="推送司机方式" >
    <el-radio-group v-model="form.sendAll" @change="sendDriverChange">
      <el-radio label="1">按供应商</el-radio>
      <el-radio label="2">按照服务城市</el-radio>
      <el-radio label="3">选择司机推送</el-radio>
      <el-radio label="4">平台所有司机推送</el-radio>
    </el-radio-group>
  </el-form-item>
  <el-form-item label="推送供应商" v-show="showProvider">
    <el-select v-model="form.providerId" placeholder="选择供应商" >
      <el-option  label="全部" value="0" selected>
      </el-option>
	  <el-option
                         v-for="item in providerOptions"
                         :key="item.value"
                         :label="item.label"
                         :value="item.value">
      </el-option>
	</el-select>
  </el-form-item>
  <el-form-item label="推送城市" v-show="showCity">
  	<el-select v-model="form.cooperationTypeArr" multiple placeholder="请选择司机类型" size="30">
	  <el-option label="自营" value="5"></el-option>
	  <el-option label="企业加盟" value="6"></el-option>
	  <el-option label="个人加盟" value="7"></el-option>
	</el-select>
	<el-row>
	<el-checkbox :indeterminate="isIndeterminate" v-model="checkAll" @change="handleCheckAllChange">全选</el-checkbox>
    <el-checkbox-group v-model="form.cityIds">
	    <el-checkbox v-for="item in cities" :label="item" :key="item"  checked>
	    {{item.label}}
	    </el-checkbox>
  	</el-checkbox-group>
  	</el-row>
  </el-form-item>
  <el-form-item label="已选择推送司机" v-show="showDriver">
  	<el-button type="text" @click="dialogTableVisible = true">[选择]
	</el-button>
  	<el-button type="text" @click="clearDrivers">[清空]</el-button>
  	<el-input v-model="form.driverNames"></el-input>
  </el-form-item>
  <el-form-item>
    <el-button type="primary" @click="onSubmit('form')">立即创建</el-button>
    <el-button  @click="onCancel">取消</el-button>
  </el-form-item>
</el-form>
  <el-dialog title="司机列表" :visible.sync="dialogTableVisible" @open="openDriverList">
  	<el-form ref="driverForm" :rules="rules" v-bind:model="driverForm" label-width="80px">
	<el-row justify="space-between" style="margin-top: 12px;">
      <el-col :span="6">
        <el-input v-model="driverForm.driverName" placeholder="司机姓名"  style="margin-left: 18px;">
        </el-input>
      </el-col>
      <el-col :span="6">
        <el-input v-model="driverForm.driverPhone" placeholder="手机号"  style="margin-left: 18px;">
        </el-input>
      </el-col>
      <el-col :span="6" >
      	<div class="block">
      		<el-select v-model="driverForm.cityId" placeholder="所在城市" >
      			<el-option v-for="item in cities" :key="item.value" :label="item.label" :value="item.value">
      			</el-option>
      		</el-select>
      	</div>
      </el-col>
      <el-col :span="6" >
      	<div class="block">
      		<el-select v-model="driverForm.supplierId" placeholder="供应商" >
      			<el-option v-for="item in providerOptions" :key="item.value" :label="item.label" :value="item.value">
      			</el-option>
      		</el-select>
      	</div>
      </el-col>
    </el-row>
    <el-row>
    	<el-col :span="12" style="margin-left: 30px;">
       		<el-button icon="search" class=""  @click="search">查询</el-button>
       		<el-button type="primary" class=""  @click="confirm">确定</el-button>
       		<el-button type="primary" class=""  @click="reset('driverForm')">重置</el-button>
       	</el-col>
    </el-row>
    </el-form>
     <div class="panel-body">
      <el-table
        :data="table_data"
        v-loading="load_data"
        element-loading-text="拼命加载中"
        border
        @selection-change="on_batch_select"
        style="width: 100%;">
        <el-table-column type="selection" width="55">
    	</el-table-column>
        <el-table-column prop="driverId"  label="序号" width="190">
        </el-table-column>
        <el-table-column prop="driverName" label="司机姓名" width="100">
        </el-table-column>
        <el-table-column prop="driverPhone" label="手机号" width="100">
        </el-table-column>
        <el-table-column prop="carNumber" label="车牌号" width="100">
        </el-table-column>
        <el-table-column prop="supplierId" label="城市" width="100">
        </el-table-column>
        <el-table-column prop="providerId" label="供应商" width="100">
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
  </el-dialog>
</div>
</template>
<script>
  export default {
    data() {
      return {
        form: {
          title: '',
          msgType:'',
          synopsis:'',
          content: '',
          isShellsScreen: '1',
          isImportant:'0',
          sendAll:'1',
          providerId:'0',
          cooperationTypeArr:['5','6','7'],
          cityIds:[],
          driverIds:[],
          driverNames:[]       
        },
        rules: {
          title: [{required: true, message: '标题不能为空', trigger: 'blur'}],
          content: [{required: true, message: '消息内容不能为空', trigger: 'blur'}],
          synopsis: [{required: true, message: '内容梗概不能为空', trigger: 'blur'}],
          msgType: [{required: true, message: '请选择消息类型', trigger: 'change'}]
        },
        providerOptions:[],
        cities:[],
        showProvider:true,
        showCity:false,
        showDriver:false,
        isIndeterminate: true,
        checkAll:true,
        dialogTableVisible: false,
        driverForm:{
        	driverName:'',
        	driverPhone:'',
        	cityId:'',
        	supplierId:'',
        },
        table_data: null,
        //当前页码
        currentPage: 1,
        //数据总条目
        total: 0,
        //每页显示多少条数据
        length: 10,
        //请求时的loading效果
        load_data: true,
        //批量选择数据
        batch_select:[]
             
      }
      
    },
    created(){       
       this.form.id = this.$route.query.id
       if(this.form.id){   
       	this.get_form_data(this.form.id);  
       }
       this.getProviderList();
       this.getCityList();
    },
    methods: {
      //创建消息
      onSubmit(formName) {
      	this.$refs[formName].validate((valid) => {
        	if(!valid){ return false }
        	var citys = this.form.cityIds;
          	var cityIdsStr = '';
          	if(citys){
	            for(var i in citys){
	              cityIdsStr +=  ',' + citys[i].key;
	            }
	            if(cityIdsStr){
	               cityIdsStr = cityIdsStr.substring(1);
	            }
            	this.form.cityIds = cityIdsStr;
          	}
          	
        	var cops = this.form.cooperationTypeArr;
          	var copsStr = '';
          	if(cops){
	            for(var i in cops){
	              copsStr +=  ',' + cops[i];
	            }
	            if(copsStr){
	               copsStr = copsStr.substring(1);
	            }
            	this.form.cooperationTypeArr = copsStr;
          	}
        	
        	this.$http.api_driverMsg.save(this.form)
	        .then(({data:{code,msg,data}})=>{
	        	if(code==0){
	        		this.$message({
			          message: '消息添加成功',
			          type: 'success',
			          duration:1000
       				});
	        	}else{
	        		this.$message({
			          message: '消息添加失败',
			          type: 'error',
			          duration:1000
       				});
	        	}
	        	this.$router.push('/driverMsg/driverMsgList');	        
	        })
        })
        
      },
      //批量选择
      on_batch_select(val){
        this.batch_select = val;
      },
      confirm(){
      	var driverIdStr="";
      	var driverNameStr="";
      	this.batch_select.forEach((ele, index) => {      	  
          if (ele !== null) {
                driverIdStr+=','+ele.driverId;
                driverNameStr+=','+ele.driverName;
          } 
       });
       this.form.driverIds=driverIdStr.substring(1);
       this.form.driverNames=driverNameStr.substring(1);
       this.dialogTableVisible=false;
      },
      //取消消息创建
      onCancel(){
      	this.$router.push('/driverMsg/driverMsgList');
      },
      //获取司机列表
      openDriverList(){
       this.getDriverList(1,10)
      },
      //点击搜索按钮
      search(){
        this.getDriverList(1,this.length);
      },
      //清空司机
      clearDrivers(){
      	this.form.driverNames=[];
      	this.form.driverIds=[];
      },
      //重置
      reset(formName){
      	this.driverForm.driverName='';
      	this.driverForm.driverPhone='';
      	this.driverForm.cityId='';
      	this.driverForm.supplierId='';
      },
      getDriverList(page,size){
       	this.load_data = true;
       	          
        this.$http.api_driverMsg.driverList({
          page: page,
          size: size,
          driverName: this.driverForm.driverName,
          driverPhone: this.driverForm.driverPhone,
       	  cityId: this.driverForm.cityId,
       	  supplierId: this.driverForm.supplierId
        })
          .then(({data:{code, msg, data}}) => {
            let tempList = [];
            data.list.forEach((ele, index) => {
              if (ele !== null) {
                tempList.push(ele);
              }
            });
            this.table_data = data.list;
            this.currentPage = data.page;
            this.length=size;
            this.total = data.total;
            this.load_data = false;

          })
          .catch(() => {
            this.load_data = false
          })
      },
       //分页大小选择
      handleSizeChange : function(pageSize){
          this.pageSize = pageSize;
          this.getDriverList(this.currentPage,this.pageSize);
      },
      //页码选择
      handleCurrentChange : function(page){
          this.currentPage = page;
          if(isNaN(this.pageSize)){
          	this.pageSize=10;
          }
          this.getDriverList(page,this.pageSize);
      },
      //城市全选处理
      handleCheckAllChange(event){
        this.form.cityIds = event.target.checked ? this.cities : [];
        this.isIndeterminate = false;
      },    
      sendDriverChange(sendAll){
      	if(sendAll==1){
      		this.showProvider=true;
        	this.showCity=false;
        	this.showDriver=false; 
        	this.form.providerId='0'; 
      	}else if(sendAll==2){
      		this.showProvider=false;
        	this.showCity=true;
        	this.showDriver=false;
        	this.form.cooperationTypeArr=['5','6','7'];
        	this.isIndeterminate = true;
        	this.checkAll=true;
        	this.form.cityIds=[];
      	}else if(sendAll==3){
      		this.showProvider=false;
        	this.showCity=false;
        	this.showDriver=true;
      	}else{
      		this.showProvider=false;
        	this.showCity=false;
        	this.showDriver=false;
      	}
      },
      get_form_data(id){
      	if(id!==""){
      		this.$http.api_driverMsg.get({
      			msgId:id
      		})
      		.then(({data:{code, msg, data}})=>{
      			this.form.title=data.title;
      			this.form.content=data.content;
      			this.form.msgType=data.msgType+"";
      			this.form.isImportant="0";
      			this.form.sendAll="1";
      			this.form.synopsis=data.synopsis;
      		})
      	}
      },
      //获取供应商列表
      getProviderList(){
      	this.$http.api_driverMsg.providerList()
      	.then(({data:{code, msg, data}})=>{
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
      	this.$http.api_driverMsg.cityList()
      	.then(({code,msg,data})=>{
      		data.forEach((element,index)=>{
      			if(element!==null){
      				let e={};
      				e.label=element.cityName;
      				e.key=element.cityId; 
      				e.value=element.cityId;    			
      				this.cities.push(e);
      			}
      		});
      		
      	})
      }
      
      
    }
  }
</script>