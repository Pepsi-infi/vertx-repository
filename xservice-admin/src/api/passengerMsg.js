/**
 * @file: table.
 * @intro: table请求数据配置.
 * @author: zzmhot.
 * @email: zzmhot@163.com.
 * @Date: 2017/5/8 15:25.
 * @Copyright(©) 2017 by thinkive.
 *
 */

import http from 'common/http'
import {passengerMsg} from 'common/port_uri'

//数据列表
export function list(params) {
  return http({
    baseURL: process.env.mc_push_baseURL,
    url: passengerMsg.list,
    method: 'get',
    params
  })
}

//根据id查询数据
export function addOrEdit(params) {
  return http({
    baseURL: process.env.mc_push_baseURL,
    url: passengerMsg.addOrEdit,
    method: 'post',
    params
  })
}

//根据id查询数据
export function get(params) {
  return http({
    baseURL: process.env.mc_push_baseURL,
    url: passengerMsg.get,
    method: 'get',
    params
  })
}

//根据id查询数据
export function del(params) {
  return http({
    baseURL: process.env.mc_push_baseURL,
    url: passengerMsg.del,
    method: 'get',
    params
  })
}

//根据id查询数据
export function push(params) {
  return http({
    baseURL: process.env.mc_push_baseURL,
    url: passengerMsg.push,
    method: 'get',
    params
  })
}

//根据id查询数据
export function getImportFileList(params) {
  return http({
    baseURL: process.env.mc_push_baseURL,
    url: passengerMsg.getImportFileList,
    method: 'get',
    params
  })
}

//根据id查询数据
export function getCityList(params) {
  return http({
    baseURL: process.env.mc_push_baseURL,
    url: passengerMsg.getCityList,
    method: 'get',
    params
  })
}

//根据id查询数据
export function importFilePage(params) {
  return http({
    baseURL: process.env.mc_push_baseURL,
    url: passengerMsg.importFilePage,
    method: 'get',
    params
  })
}
