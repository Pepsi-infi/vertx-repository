<template>
  <div class="panel">
    <panel-title :title="$route.meta.title"></panel-title>
    <div class="panel-body"
         v-loading="load_data"
         element-loading-text="拼命加载中">
      <el-row>
        <el-col :span="8">
          <el-form ref="form" :model="form" :rules="rules" label-width="100px">
            <el-form-item label="内容:" prop="name">
              <el-input v-model="form.content" placeholder="请输入内容" style="width: 250px;"></el-input>
            </el-form-item>
            <el-form-item label="权重:">
              <el-input-number
                placeholder="请输入内容"
                :max="100"
                :min="1"
                :value="20"
                :controls="false"
                v-model="form.weight"
                style="width: 250px;">
              </el-input-number>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="on_submit_form" :loading="on_submit_loading">保存</el-button>
              <el-button @click="$router.back()">取消</el-button>
            </el-form-item>
          </el-form>
        </el-col>
      </el-row>
    </div>
  </div>
</template>
<script type="text/javascript">
  import {panelTitle} from 'components'
  import _ from 'lodash';

  const checkForm = (form) => {
    _.each(form, (item, index) => {
      if (typeof item === 'object') {
        item = JSON.stringify(item);
      };
    });
  };

  export default{
    data(){
      return {
        form: {
          content: null,
          weight: 1
        },
        route_id: this.$route.params.id,
        load_data: false,
        on_submit_loading: false,
        rules: {
          content: [{required: true, message: '内容不能为空', trigger: 'blur'}]
        }
      }
    },
    created(){
      this.route_id && this.get_form_data()
    },
    methods: {
      //获取数据
      get_form_data(){
        this.load_data = true
        this.$fetch.api_imCommonLanguage.get({
          id: this.route_id
        })
          .then(({data}) => {
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
          if (!valid) {
            this.$message.error(TEXT.FORM_VALIDATE_ERROR);
            return;
          }
          if (Number(this.form.weight) + '' === 'NaN') {
            this.$message.error('权重是数字');
            return;
          }
          this.submit();
        });
      },
      submit() {
        var finalForm = {
          content: this.form.content,
          weight: this.form.weight,
          type: this.$route.params.type
        };
        if (this.$route.params.id > 0) {
          finalForm.id = this.$route.params.id;
        }
        this.$fetch.api_imCommonLanguage.save(finalForm)
          .then((resp) => {
            this.$message({
              message: '保存成功！',
              type: 'success'
            });
            this.$router.push(`/imCommonLanguage/commonLanguageList/${this.$route.params.type}`);
          });
      }
    },
    components: {
      panelTitle
    }
  }
</script>
