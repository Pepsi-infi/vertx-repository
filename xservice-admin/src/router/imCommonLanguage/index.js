const routers = [
  {
    path: '/imCommonLanguage/list',
    name: 'list',
    component(resolve) {
      require(['../../pages/imCommonLanguage/list.vue'], resolve);
    }
  },
  {
    path: '/imCommonLanguage/commonLanguageList/:type',
    name: 'commonLanguageList',
    component(resolve) {
      require(['../../pages/imCommonLanguage/commonLanguageList.vue'], resolve);
    }
  },
  {
    path: '/imCommonLanguage/:type/commonLanguage/:id',
    name: 'commonLanguage',
    component(resolve) {
      require(['../../pages/imCommonLanguage/commonLanguage.vue'], resolve);
    }
  }
];
export default routers;
