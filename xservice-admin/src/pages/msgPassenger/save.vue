<template>
  <div class="panel">
    <panel-title :title="$route.meta.title"></panel-title>
    <div class="panel-body"
         v-loading="load_data"
         element-loading-text="拼命加载中">
      <el-form :model="form" :rules="rules" ref="form" label-width="150px" class="demo-form-inline">
        <el-row>
            <el-col :span="10">
              <el-form-item label="标  题:" prop="title">
                <el-input v-model="form.title" placeholder="请输入标题" style="width: 250px;" ></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="10">
              <el-form-item label="状  态:">
                <el-radio-group v-model="form.status">
                  <el-radio :label="1">有效</el-radio>
                  <el-radio :label="2">无效</el-radio>
                </el-radio-group>
              </el-form-item>
            </el-col>
        </el-row>
        <el-row>
          <el-col :span="20">
              <el-form-item label="消息内容" prop="content">
                 <el-input type="textarea" v-model="form.content" style="width: 650px;"></el-input>
              </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="10">
             <el-form-item label="动  作:">
               <el-radio-group v-model="form.openType" @change="openTypeChange">
                 <el-radio :label="1">打开APP</el-radio>
                 <el-radio :label="2">打开网页</el-radio>
               </el-radio-group>
             </el-form-item>
          </el-col>
          <el-col :span="10">
             <el-form-item label="网页地址:" >
               <el-input v-model="form.openUrl" placeholder="" style="width: 250px;"></el-input>
             </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="10">
             <el-form-item label="是否进入消息中心:">
               <el-radio-group v-model="form.inMsgCenter" >
                 <el-radio :label="1">是</el-radio>
                 <el-radio :label="2">否</el-radio>
               </el-radio-group>
             </el-form-item>
          </el-col>
          <el-col :span="10">
             <el-form-item label="消息中心图片URL:">
               <el-input v-model="form.msgCenterImgUrl" placeholder="700*250(高*宽)，格式jpg或png" style="width: 250px;"></el-input>
             </el-form-item>
          </el-col>
        </el-row>
        <el-row>
            <el-col :span="10">
              <el-form-item label="发送时间" prop="sendTime">
                <el-date-picker
                   type="datetime"
                   v-model="form.sendTime"
                   placeholder="发送时间">
                 </el-date-picker>
              </el-form-item>
            </el-col>
            <el-col :span="10">
              <el-form-item label="过期时间" prop="expireTime">
                <el-date-picker
                   v-model="form.expireTime"
                   type="datetime"
                   placeholder="过期时间">
                 </el-date-picker>
              </el-form-item>
            </el-col>
        </el-row>
        <el-row>
          <el-col :span="10">
             <el-form-item label="发送对象:" prop="sendType">
               <el-radio-group v-model="form.sendType">
                 <el-radio label="1" value="1">所有用户</el-radio>
                 <el-radio label="2" value="2">指定用户</el-radio>
                 <el-radio label="3" value="3">指定城市</el-radio>
               </el-radio-group>
             </el-form-item>
          </el-col>
          <el-col :span="10">
             <el-form-item label="跳转类型:" prop="action" >
                <el-select v-model="form.action" v-bind:disabled="actionShow" placeholder="请选择" >
                    <el-option label="我的行程页" value="3"></el-option>
                    <el-option label="充值页" value="4"></el-option>
                    <el-option label="优惠券页" value="5"></el-option>
                    <el-option label="信用卡页" value="6"></el-option>
                    <el-option label="用户等级页" value="7"></el-option>
                    <el-option label="消息中心页" value="8"></el-option>
                </el-select>
             </el-form-item>
          </el-col>
        </el-row>
        <el-row  type="flex" justify="center">
            <el-col :span="12" >
              <el-form-item>
                <el-button type="primary" @click="on_submit_form" :loading="on_submit_loading">保 存</el-button>
                <el-button @click="$router.back()">返 回</el-button>
              </el-form-item>
            </el-col>
        </el-row>
      </el-form>
    </div>
  </div>
</template>
<script type="text/javascript">
  import {panelTitle} from 'components'
  import moment from 'moment'

  export default{
    data(){
      return {
        form: {
          openType : 1,
          status : 1,
          inMsgCenter : 1,
          sendType : 1,
          sendTime : '',
          expireTime : '',
          action : '',
          title : '',
          content : '',
          id : ''
        },
        load_data: false,
        on_submit_loading: false,
        rules: {
          title: [{required: true, message: '标题不能为空', trigger: 'blur'}],
          content: [{required: true, message: '消息内容不能为空', trigger: 'blur'}],
          sendType: [{required: true, message: '请选择发送对象', trigger: 'change'}],
          sendTime: [{ type: 'date', required: true, message: '请选择发送时间', trigger: 'change' }],
          expireTime: [{ type: 'date', required: true, message: '请选择过期时间', trigger: 'change' }],
          action: [{required: true, message: '请选择跳转类型', trigger: 'change'}]
        },
         //是否禁用跳转
         actionShow : false
      }
    },
    created(){
       this.form.id = this.$route.query.id
       if(this.form.id){
         this.get_form_data(this.form.id);
       }

    },
    methods: {
      //获取数据
      get_form_data(val){
        this.load_data = true
        this.$http.api_msgPassenger.get({
          id: val
        })
          .then(({data}) => {
            //vue validate验证rules date时需要date对象，所以这里需要转一下。
            data.sendTime = new Date(data.sendTime);
            data.expireTime = new Date(data.expireTime);
            //vue的类型对不上，不会自动匹配（后台数字类型，页面上字符串）
            data.action = data.action + "";
            data.sendType = data.sendType + "";
            this.form = data
            this.load_data = false
          })
          .catch(() => {
            this.load_data = false
          })
      },
      //提交
      on_submit_form(){
        this.$refs.form.validate((valid) => {
          if(!valid){ return false }
          //console.log(JSON.stringify(this.form));
          this.on_submit_loading = true
          this.$http.api_msgPassenger.addOrEdit(this.form)
            .then(({data}) => {
              var msg = this.form.id ? "编辑乘客消息成功":"新增乘客消息成功";
              this.$message.success(msg)
              setTimeout(this.$router.back(), 1000)
            })
            .catch(() => {
              this.on_submit_loading = false
            })
        })
      },
      getLocalDate : function(val){
        return moment(val).format('YYYY-MM-DD HH:mm:ss');
      },

      //打开类型为打开网页时，跳转类型不可选
      openTypeChange : function(val){
        if(val == 2){
          this.actionShow = true;
        }else{
          this.actionShow = false;
        }
      }
    },
    components: {
      panelTitle
    }
  }
  //映射转换
  var convertAction = {
          3 : '我的行程页',
          4 : '充值页',
          5 : '优惠券页',
          6 : '信用卡页',
          7 : '用户等级页',
          8 : '消息中心页'
  }

</script>
