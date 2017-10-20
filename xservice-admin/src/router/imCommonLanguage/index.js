const routers = [
  {
    path: '/imCommonLanguage/list',
    name: 'list',
    component(resolve) {
      require(['../../pages/imCommonLanguage/list.vue'], resolve);
    },
    meta: {
      title: "IM常用语库"
    }
  },
  {
    path: '/imCommonLanguage/commonLanguageList/:type',
    name: 'commonLanguageList',
    component(resolve) {
      require(['../../pages/imCommonLanguage/commonLanguageList.vue'], resolve);
    },
    meta: {
      title: 'IM常用语库'
    }
  },
  {
    path: '/imCommonLanguage/:type/commonLanguage/:id',
    name: 'commonLanguage',
    component(resolve) {
      require(['../../pages/imCommonLanguage/commonLanguage.vue'], resolve);
    },
    meta: {
      title: "IM常用语库"
    }
  }
];
export default routers;
