# 路由配置

## 路由配置说明
路由采用[vue-router 2](https://github.com/vuejs/vue-router)

## vue-router 2相关文档
* [vue-router 2英文文档](https://router.vuejs.org/en/)
* [vue-router 2中文文档](https://router.vuejs.org/zh-cn/)

## vue-router 2示例
[vue-router 2](https://github.com/vuejs/vue-router)的example里面有几个使用的示例，大家可以下载学习。

## 说明
不同的文件夹存放不同类别的的路由配置。

## 目录结构介绍
<pre>
.
├── msgStat            // 统计中心
├── index.js           // 把所有的各类别的路由合并到index里，外部引用只引用这一个文件
└── readme.md          // 本文件夹的说明文档
</pre>

## 配置说明
每一个类别下面有一个`index.js`,初始的时候是个空数组，大家根据自己的url规划，在里面配置自己类别的url规则，如下所示：

```js
const routers = [];

export default routers;
```

在router的根目录下也有一个`index.js`，这个是全站的路由配置文件，这个文件会把每一个类别下面的路由配置文件`import`进来，然后拼成一个大的数组，如下所示：

```js
// 导入所有类别的路由配置
import msgStatRouters from './msgStat';


const routes = [{
  path: '/404',
  name: 'notPage',
  component: noPageComponent
}, {
  path: '*',
  redirect: '/404'
}, {
  path: '/user/login',
  name: 'login',
  component: loginComponent
}, {
  path: '/',
  redirect: '/home',
  component: viewPageComponent,
  children: [{
    path: '/home',
    name: 'home',
    component: homeComponent,
    meta: {
      title: "主页",
      auth: true
    }
  }].concat(msgStatRouters,
           ...
           ...)
}]

```
