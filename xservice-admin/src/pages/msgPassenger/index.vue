<template>
  <div>

  <el-row justify="row-bg" style="margin-top: 12px;" :gutter="20">
          <el-col :span="4">
             <el-input v-model="search_title" placeholder="标题" >
             </el-input>
          </el-col>
          <el-col :span="4"  >
                       <el-date-picker
                             v-model="search_sendtime"
                             type="datetime"
                             placeholder="发送时间">
                           </el-date-picker>
          </el-col>
          <el-col :span="4" style="margin-left: 30px;">
              <el-button icon="search" class=""  @click="search">搜索</el-button>
          </el-col>
      </el-row>
        <el-row justify="start">
          <el-col :span="24">
            <el-button type="primary" icon="plus" size="small" @click="addMsg">添加消息</el-button>
          </el-col>
        </el-row>
      <el-row >
          <el-table :data="table_data" border
                        style="width: 100%" >
                <el-table-column label="序号" type="index" ></el-table-column>
                <el-table-column prop="id" label="ID" width="200"> </el-table-column>
                <el-table-column prop="title" label="标题" width="200"> </el-table-column>
                <el-table-column prop="sendTime" label="发送时间" width="170" :formatter="formatTime"> </el-table-column>
                <el-table-column prop="expireTime" label="过期时间" width="170" :formatter="formatTime"> </el-table-column>
                <el-table-column prop="openType" label="动作" width="200" :formatter="openType"> </el-table-column>
                <el-table-column prop="inMsgCenter" label="是否进入个人中心消息" width="200" :formatter="inCenter"> </el-table-column>
                <el-table-column prop="status" label="状态" width="80" :formatter="status"> </el-table-column>
                <el-table-column label="操作" width="400">
                   <template scope="scope">
                       <el-button
                          size="small"
                          @click="pushMsg(scope.$index, scope.row)" type="success" >消息推送</el-button>
                       <el-button
                          size="small"
                          @click="stopPush(scope.$index, scope.row)" type="warning" v-show="showButton(scope.row)">停止推送</el-button>
                        <el-button
                          size="small"
                          @click="editMsg(scope.$index, scope.row)" type="success" v-show="showButton(scope.row)">编 辑</el-button>
                        <el-button
                          size="small"
                          @click="delMsg(scope.$index, scope.row)" type="danger" v-show="showButton(scope.row)">删 除</el-button>
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
        search_title : '',
        search_sendtime : '',
        realId : '',
        isShow : false
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
      addMsg : function(){
        this.$router.push('/msgPassenger/save')
      },
      //编辑消息
      editMsg : function(index,row){
        this.$router.push({path: '/msgPassenger/save', query: {id: row.realId}});
      },
      //删除消息
      delMsg : function(index,row){
        var param = { id : row.realId };
        this.$http.api_msgPassenger.del(param).then(({data}) => {
                this.$message.success(data)
                this.load_data = true
                this.getData();
             }).catch((error) => {
                console.log(" load error :" + error);
                this.load_data = false
             })
      },
      //删除消息
      stopPush : function(index,row){
        var param = {
          id : row.realId,
          status : 2
        };
        this.$http.api_msgPassenger.addOrEdit(param).then(({data}) => {
              this.$message.success("消息已经设置为无效")
              this.load_data = true
              this.getData();
           }).catch((error) => {
              console.log(" load error :" + error);
              this.load_data = false
           })
      },
      //删除消息
      pushMsg : function(index,row){
        var param = { id : row.realId };
        this.$http.api_msgPassenger.push(param).then(({data}) => {
                this.$message.success(data)
                this.load_data = true
                this.getData();
             }).catch((error) => {
                console.log(" load error :" + error);
                this.load_data = false
             })
      },

      //加载列表数据
      getData : function(){
          this.$http.api_msgPassenger.list({
                    page : this.currentPage,
                    pageSize : this.pageSize,
                    title : this.search_title,
                    sendTime : this.search_sendtime
          })
            .then(({data: {list, page, total}}) => {
              let tempList = [];
              list.forEach((ele, index) => {
                  if (ele !== null) {
                      tempList.push(ele);
                  }
                  ele.realId = ele.id;
                  ele.id = "ad_passenger_count_" + ele.id;
                  if(ele.sendTime){
                    ele.isShow = new Date(ele.sendTime) > new Date() ? true : false;
                  }
                  //console.log (new Date(ele.sendTime));
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
      //按钮是否显示
      showButton : function(row){
         return row.isShow;
      },

      formatTime : function (row,column,val){
        let time = new Date(val).Format("yyyy-MM-dd hh:mm:ss");
        return time;
      },
      inCenter : function(row,column,val){
        let re = (val === 1) ? "是":"否";
        return re;
      },
      status : function(row,column,val){
        let re = (val === 1) ? "有效":"无效";
        return re;
      },
      openType : function(row,column,val){
        let re = (val === 1) ? "打开APP":"打开网页";
        return re;
      },
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

