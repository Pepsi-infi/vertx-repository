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
export function add(params) {
  return http({
    url: passenger_table.add,
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


