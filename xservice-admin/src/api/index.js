/**
 * @file: index.
 * @intro: api请求索引.
 * @author: zzmhot.
 * @email: zzmhot@163.com.
 * @Date: 2017/5/8 15:31.
 * @Copyright(©) 2017 by zzmhot.
 *
 */

//导入模块
import * as api_file from './file'
import * as api_msgStat from './msgStat'
import * as api_user from './user'
import * as api_msgPassenger from './msgPassenger'
import * as api_driverMsg from './driverMsg'  //引入driverMsg.js文件

const apiObj = {
  api_file,
  api_msgStat,
  api_user,
  api_msgPassenger,
  api_driverMsg
}

const install = function (Vue) {
  if (install.installed) return
  install.installed = true

  //定义属性到Vue原型中
  Object.defineProperties(Vue.prototype, {
    $fetch: {
      get() {
        return apiObj
      }
    }
  })

  //定义属性到Vue原型中
  Object.defineProperties(Vue.prototype, {
    $http: {
      get() {
        return apiObj
      }
    }
  })
}

export default {
  install
}
