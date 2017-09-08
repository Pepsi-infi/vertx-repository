//import driverMsgList from "../../driverMsg/driverMsgList"
//import driverMsgAdd from "../../driverMsg/add"


const routers = [{
		path: '/driverMsg/driverMsgList',
		name: 'driverMsgList',
		component(resolve) {
			require(['../../pages/driverMsg/driverMsgList.vue'], resolve);
		}
	},
	{
		path:'/driverMsg/add',
		name:'driverMsgAdd',
		component(resolve) {
			require(['../../pages/driverMsg/add.vue'], resolve);
		},
		meta: {
	            title: "公司消息 / 新建消息",
	            auth: true
	     }
	},
	{
		path:'/driverMsg/driverList',
		name:'driverList',
		component(resolve) {
			require(['../../pages/driverMsg/driverList.vue'], resolve);
		},
		meta: {
			title: "司机列表",
			auth: true
		}
	}

];
export default routers;
