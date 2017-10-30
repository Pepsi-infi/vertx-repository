<template>
  <div class="panel">
    <panel-title :title="$route.meta.title"></panel-title>
    <div class="panel-body" v-loading="load_data" element-loading-text="拼命加载中">
      <el-row>
        <el-col :span="8">
          <el-form ref="form" :model="form" :rules="rules" label-width="100px">
            <el-form-item label="敏感词:" prop="word">
              <el-input v-model="form.word" placeholder="请输入敏感词" style="width: 250px;"></el-input>
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
import { panelTitle } from 'components'
import _ from 'lodash';

const checkForm = (form) => {
  _.each(form, (item, index) => {
    if (typeof item === 'object') {
      item = JSON.stringify(item);
    };
  });
};

export default {
  data() {
    return {
      form: {
        word: null
      },
      route_id: this.$route.params.id,
      load_data: false,
      on_submit_loading: false,
      rules: {
        word: [{ required: true, message: '敏感词不能为空', trigger: 'blur' }]
      }
    }
  },
  created() {
    this.route_id && this.get_form_data()
  },
  methods: {
    //获取数据
    get_form_data() {
      this.load_data = true
      this.$fetch.api_sensitiveWord.get({
          id: this.route_id
        })
        .then(({ data }) => {
          this.form = data
          this.load_data = false
        })
        .catch(() => {
          this.load_data = false
        })
    },
    //提交
    on_submit_form() {
      this.$refs.form.validate((valid) => {
        if (!valid) {
          this.$message.error(TEXT.FORM_VALIDATE_ERROR);
          return;
        }
        this.submit();
      });
    },
    submit() {
      var finalForm = {
        word: this.form.word,
        status: this.form.status
      };
      if (this.$route.params.id > 0) {
        finalForm.id = this.$route.params.id;
      }
      this.$fetch.api_sensitiveWord.save(finalForm)
        .then((resp) => {
          this.$message({
            message: '保存成功！',
            type: 'success'
          });
          this.$router.push(`/sensitiveWord/list`);
        });
    }
  },
  components: {
    panelTitle
  }
}

</script>
