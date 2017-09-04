const routers = [{
  path: '/sensitiveWord/list',
  name: 'sensitiveWordList',
  component(resolve) {
    require(['../../pages/sensitiveWord/list.vue'], resolve);
  },
  meta: {
    title: "敏感词管理"
  }
},
  {
    path: '/sensitiveWord/sensitiveWord/:id',
    name: 'sensitiveWord',
    component(resolve) {
      require(['../../pages/sensitiveWord/sensitiveWord.vue'], resolve);
    },
    meta: {
      title: "敏感词信息"
    }
  }

];
export default routers;
