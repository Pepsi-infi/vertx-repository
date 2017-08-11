const routers = [{
  path: '/msgStat/msgStatList',
  name: 'msgStatList',
  component(resolve) {
    require(['../../pages/msgStat/msgStatList.vue'], resolve);
  }
}];
export default routers;
