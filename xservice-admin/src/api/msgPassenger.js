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
import {passenger_table} from 'common/port_uri'

//数据列表
export function list(params) {
  return http({
    url: passenger_table.list,
    method: 'get',
    params
  })
}

//根据id查询数据
export function addOrEdit(params) {
  return http({
    url: passenger_table.addOrEdit,
    method: 'post',
    params
  })
}

//根据id查询数据
export function get(params) {
  return http({
    url: passenger_table.get,
    method: 'get',
    params
  })
}

//根据id查询数据
export function del(params) {
  return http({
    url: passenger_table.del,
    method: 'get',
    params
  })
}


