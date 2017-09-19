
//乘客消息列表
import passengerMsg from '../../pages/passengerMsg'
//乘客消息新增
import addPassengerMsg from '../../pages/passengerMsg/addPassengerMsg'
//乘客消息查看
import getPassengerMsg from '../../pages/passengerMsg/getPassengerMsg'
//乘客名单导入列表
import importFileList from '../../pages/passengerMsg/importList'


const routers = [{
    path: '/passengerMsg',
    name: 'passengerList',
    component: passengerMsg
  },
  {
    path: '/passengerMsg/addPassengerMsg',
    name: 'addPassengerMsg',
    component: addPassengerMsg,
    meta: {
            title: "乘客端消息 / 新增消息",
            auth: true
           }
    },
  {
    path: '/passengerMsg/importList',
    name: 'importFileList',
    component: importFileList
  },
  {
    path: '/passengerMsg/getPassengerMsg',
    name: 'getPassengerMsg',
    component: getPassengerMsg
  }
];

export default routers;
