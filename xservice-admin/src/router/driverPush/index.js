const routers = [{
  path: '/driverPush/driverPushList',
  name: 'driverPushList',
  component(resolve) {
    require(['../../pages/driverPush/driverPushList.vue'], resolve);
  }
}];
export default routers;
