<template>
  <div>

  <el-row justify="row-bg" style="margin-top: 12px;" :gutter="20">
          <el-col :span="4">
             <el-input v-model="queryMsgId" placeholder="标题" >
             </el-input>
          </el-col>
          <el-col :span="4"  >
                       <el-date-picker
                             v-model="value1"
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
          <el-table :data="tableData"
                        border
                        style="width: 100%">
                <el-table-column prop="seq" label="序号" width="100"> </el-table-column>
                <el-table-column prop="msgId" label="ID" width="100"> </el-table-column>
                <el-table-column prop="title" label="标题" width="100"> </el-table-column>
                <el-table-column prop="sendTime" label="发送时间" width="150"> </el-table-column>
                <el-table-column prop="expireTime" label="过期时间" width="150"> </el-table-column>
                <el-table-column prop="dongzuo" label="动作" width="80"> </el-table-column>
                <el-table-column prop="inCenter" label="是否进入个人中心消息" width="200"> </el-table-column>
                <el-table-column prop="status" label="状态" width="80"> </el-table-column>
                <el-table-column label="操作" width="400">
                   <template scope="scope">
                               <el-button
                                 size="small"
                                 @click="handleEdit(scope.$index, scope.row)">消息推送</el-button>
                               <el-button
                                size="small"
                                @click="handleEdit(scope.$index, scope.row)">停止推送</el-button>
                    </template>
                </el-table-column>
          </el-table>
      </el-row>
  </div>
</template>
<script>
  export default {
    data() {
      return {
        tableData: [{'seq': 1, 'msgId' : '001', 'title': 'mess-1', 'sendTime': '2016-05-02', 'expireTime': '2016-05-02', 'dongzuo': 1, 'inCenter': 1, 'status': 1},
        {'seq': 1, 'msgId' : '001', 'title': 'mess-1', 'sendTime': '2016-05-02', 'expireTime': '2016-05-02', 'dongzuo': 1, 'inCenter': 1, 'status': 1},
        {'seq': 1, 'msgId' : '001', 'title': 'mess-1', 'sendTime': '2016-05-02', 'expireTime': '2016-05-02', 'dongzuo': 1, 'inCenter': 1, 'status': 1},
        {'seq': 1, 'msgId' : '001', 'title': 'mess-1', 'sendTime': '2016-05-02', 'expireTime': '2016-05-02', 'dongzuo': 1, 'inCenter': 1, 'status': 1},
        {'seq': 1, 'msgId' : '001', 'title': 'mess-1', 'sendTime': '2016-05-02', 'expireTime': '2016-05-02', 'dongzuo': 1, 'inCenter': 1, 'status': 1}]
        ,
        value1: ''
      }
    },
    methods:{
      //新增消息
      addMsg : function(){
        this.$router.push('/msgPassenger/save')
      }
    }
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
