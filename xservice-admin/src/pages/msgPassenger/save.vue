<template>
  <div class="panel">
    <panel-title :title="$route.meta.title"></panel-title>
    <div class="panel-body"
         v-loading="load_data"
         element-loading-text="拼命加载中">
      <el-form ref="form" :inline="true" :model="form" :rules="rules" label-width="150px" class="demo-form-inline">
        <el-row>
            <el-col :span="10">
              <el-form-item label="标  题:" >
                <el-input v-model="form.title" placeholder="请输入标题" style="width: 250px;"></el-input>
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
              <el-form-item label="消息内容" >
                 <el-input type="textarea" v-model="form.content" style="width: 650px;"></el-input>
              </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="10">
             <el-form-item label="动  作:">
               <el-radio-group v-model="form.openType">
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
               <el-radio-group v-model="form.inMsgCenter">
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
              <el-form-item label="发送时间">
                <el-date-picker
                   type="datetime"
                   v-model="form.sendTime"
                   formatter="getLocalDate"
                   placeholder="发送时间">
                 </el-date-picker>
              </el-form-item>
            </el-col>
            <el-col :span="10">
              <el-form-item label="过期时间">
                <el-date-picker
                   v-model="form.expireTime"
                   type="datetime"
                   formatter="getLocalDate"
                   placeholder="过期时间">
                 </el-date-picker>
              </el-form-item>
            </el-col>
        </el-row>
        <el-row>
          <el-col :span="10">
             <el-form-item label="发送对象:">
               <el-radio-group v-model="form.sendType">
                 <el-radio :label="1">所有用户</el-radio>
                 <el-radio :label="2">指定用户</el-radio>
                 <el-radio :label="3">指定城市</el-radio>
               </el-radio-group>
             </el-form-item>
          </el-col>
          <el-col :span="10">
             <el-form-item label="跳转类型:" >
                                    <el-select v-model="form.action" placeholder="请选择">
                                      <el-option
                                        v-for="item in options"
                                        :key="item.value"
                                        :label="item.label"
                                        :value="item.value">
                                      </el-option>
                                    </el-select>
             </el-form-item>
          </el-col>
        </el-row>
        <el-row  type="flex" justify="center">
            <el-col :span="4" >
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
  import moment from 'moment';

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
          content : ''
        },
        load_data: false,
        on_submit_loading: false,
        rules: {
          title: [{required: true, message: '标题不能为空', trigger: 'blur'}]
        },
        options: [{
                  value: 3,
                  label: '我的行程页'
                }, {
                  value: 4,
                  label: '充值页'
                }, {
                  value: 5,
                  label: '优惠券页'
                }, {
                  value: 6,
                  label: '信用卡页'
                }, {
                  value: 7,
                  label: '用户等级页'
                }, {
                   value: 8,
                   label: '消息中心页'
                 }]
      }
    },
    created(){
       this.get_form_data();

    },
    methods: {
      //获取数据
      get_form_data(){
        this.load_data = true
        this.$http.api_msgPassenger.get({
          id: 60
        })
          .then(({data}) => {
            var action = data.action;
            this.form = data
            this.load_data = false
          })
          .catch(() => {
            this.load_data = false
          })
      },
      //提交
      on_submit_form(){
        var fromData = JSON.stringify(this.form);
        console.log(fromData);
        this.$refs.form.validate((valid) => {
          if (!valid) return false
          this.on_submit_loading = true
          this.$http.api_msgPassenger.add(this.form)
            .then(({msg}) => {
              this.$message.success(msg)
              setTimeout(this.$router.back(), 500)
            })
            .catch(() => {
              this.on_submit_loading = false
            })
        })
      },
      getLocalDate : function(val){
        return moment(val).format('YYYY-MM-DD HH:mm:ss');
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
