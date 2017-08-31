/**
 * @file: table.
 * @intro: table请求数据配置.
 * @author: zzmhot.
 * @email: zzmhot@163.com.
 * @Date: 2017/5/8 15:25.
 * @Copyright(©) 2017 by thinkive.
 *
 */

import fetch from 'common/fetch'
import {httpPost} from 'common/fetch'

//数据列表
export function list(params) {
  return fetch({
    url: '/mc-config/im/commonLanguage.json',
    method: 'get',
    params
  })
}

//根据id查询数据
export function get(params) {
  return fetch({
    url: '/mc-config/im/commonLanguage/get.json',
    method: 'get',
    params
  })
}

//根据id删除数据
export function del(params) {
  return fetch({
    url: '/mc-config/im/commonLanguage/del.json',
    method: 'get',
    params
  })
}
//添加或修改数据
export function save(data) {
  return httpPost({
    url: '/mc-config/im/commonLanguage.json',
    method: 'post',
    data
  })
}


