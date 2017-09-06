<template>
  <div class="panel" style="padding: 15px;">
    <panel-title :title="$route.meta.title">

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
          prop="type"
          label="序号"
          width="0">
        </el-table-column>
        <el-table-column
          prop="item"
          label="常用语"
          width="200">
        </el-table-column>
        <el-table-column
          label="操作"
          width="100">
          <template scope="props">
            <router-link :to="{name: 'commonLanguageList', params: {type: props.row.type}}" tag="span">
              <el-button type="info" size="small" icon="edit">修改</el-button>
            </router-link>
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
        table_data: [{
          type: 1,
          item: '司机常用语'
        }, {
          type: 2,
          item: '乘客常用语'
        }]
      }
    },
    components: {
      panelTitle,
      bottomToolBar
    },
    created(){

    },
    methods: {



    }
  }
</script>

