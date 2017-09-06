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
    baseURL: process.env.mc_config_baseURL,
    url: '/mc-config/v1/sensitiveWords.json',
    method: 'get',
    params
  })
}

//根据id查询数据
export function get(params) {
  return fetch({
    baseURL: process.env.mc_config_baseURL,
    url: '/mc-config/v1/sensitiveWords/get.json',
    method: 'get',
    params
  })
}

//根据id删除数据
export function del(params) {
  return fetch({
    baseURL: process.env.mc_config_baseURL,
    url: '/mc-config/v1/sensitiveWords/del.json',
    method: 'get',
    params
  })
}
//添加或修改数据
export function save(data) {
  return httpPost({
    baseURL: process.env.mc_config_baseURL,
    url: '/mc-config/v1/sensitiveWords.json',
    method: 'post',
    data
  })
}


