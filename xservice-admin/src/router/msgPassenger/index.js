
//乘客消息列表
import msgPassenger from '../../pages/msgPassenger'
//乘客消息新增
import msgPassengerAdd from '../../pages/msgPassenger/save'

const routers = [{
    path: '/msgPassenger',
    name: 'passengerList',
    component: msgPassenger
  },
  {
    path: '/msgPassenger/save',
    name: 'passengerAdd',
    component: msgPassengerAdd,
    meta: {
            title: "乘客端消息 / 新增消息",
            auth: true
           }
    }
];

export default routers;
