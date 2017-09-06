<template>
  <div>

  <el-row justify="row-bg" style="margin-top: 12px;" :gutter="20">
          <el-col :span="4"  >
                       <el-date-picker
                             v-model="createTime"
                             type="date"
                             placeholder="导入时间">
                           </el-date-picker>
          </el-col>
          <el-col :span="4" style="margin-left: 30px;">
              <el-button icon="search" class=""  @click="search">搜索</el-button>
          </el-col>
      </el-row>
      <el-row >
          <el-table :data="table_data" border
                        style="width: 100%" >
                <el-table-column label="序号" type="index" width="100"></el-table-column>
                <el-table-column prop="fileName" label="标题" width="300"> </el-table-column>
                <el-table-column prop="createTime" label="导入时间" width="200" :formatter="formatTime"> </el-table-column>
                <el-table-column prop="createUser" label="导入人员" width="150" > </el-table-column>
                <el-table-column label="操作" width="400">
                   <template scope="scope">
                        <el-button
                          size="small"
                          @click="del(scope.$index, scope.row)" type="danger" >删 除</el-button>
                    </template>
                </el-table-column>
          </el-table>
      </el-row>
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
</template>
<script>
  import Vue from 'vue';
  import ElementUI from 'element-ui';
  Vue.use(ElementUI);
  import {panelTitle, bottomToolBar} from 'components'

  export default {
    data() {
      return {
        table_data : null,
        currentPage : 1,
        pageSize : 10,
        total : 0,
        load_data : true,
        createTime : ''
      }
    },
    components: {
      panelTitle,
      bottomToolBar
    },
    created(){
      this.getData()
    },
    methods:{
      //新增消息
      add : function(){
        this.$router.push('/')
      },
      //删除消息
      del : function(index,row){

      },
      //加载列表数据
      getData : function(){
          this.$http.api_passengerMsg.importFilePage({
                    page : this.currentPage,
                    pageSize : this.pageSize,
                    createTime : this.createTime
          })
            .then(({data: {list, page, total}}) => {
              let tempList = [];
              list.forEach((ele, index) => {
                  if (ele !== null) {
                      tempList.push(ele);
                  }
              });
              this.table_data = list
              this.currentPage = page
              this.total = total
              this.load_data = false
            })
            .catch((error) => {
              console.log(" load error :" + error);
              this.load_data = false
            })
      },
      search : function (){
          this.getData();
      },
      handleSizeChange : function(pageSize){
          this.pageSize = pageSize;
          this.getData();
      },
      handleCurrentChange : function(page){
          this.currentPage = page;
          this.getData();
      },

      formatTime : function (row,column,val){
        let time;
        if(val){
          time = new Date(val).Format("yyyy-MM-dd hh:mm:ss");
        }
        return time;
      }
    }
  }
 Date.prototype.Format = function (fmt) {
     var o = {
         "M+": this.getMonth() + 1, //月份
         "d+": this.getDate(), //日
         "h+": this.getHours(), //小时
         "m+": this.getMinutes(), //分
         "s+": this.getSeconds(), //秒
         "q+": Math.floor((this.getMonth() + 3) / 3), //季度
         "S": this.getMilliseconds() //毫秒
     };
     if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
     for (var k in o)
     if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
     return fmt;
 }
</script>
<style>
 .el-row {
    margin-bottom: 20px;
    &:last-child {
      margin-bottom: 0;
    }
  }
</style>

