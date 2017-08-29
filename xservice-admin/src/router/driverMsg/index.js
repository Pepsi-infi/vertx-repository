const routers = [{
  path: '/driverMsg/driverMsgList',
  name: 'driverMsgList',
  component(resolve) {
    require(['../../pages/driverMsg/driverMsgList.vue'], resolve);
  }
}];
export default routers;
