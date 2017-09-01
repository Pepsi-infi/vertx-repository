//import driverMsgList from "../../driverMsg/driverMsgList"
//import driverMsgAdd from "../../driverMsg/add"


const routers = [{
	path: '/driverMsg/driverMsgList',
	name: 'driverMsgList',
	component(resolve) {
		require(['../../pages/driverMsg/driverMsgList.vue'], resolve);
	}
	},
//	path: '/driverMsg/driverMsgList',
//	name: 'driverMsgList',
//	component:driverMsgList
//	},
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
	}

];
export default routers;
