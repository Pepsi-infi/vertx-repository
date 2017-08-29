import fetch from 'common/fetch'
import {driverMsg} from 'common/port_uri'

//数据列表
export function list(params) {
  return fetch({
    url: driverMsg.list,
    method: 'get',
    params:params
  })
}

//根据id查询数据
export function get(params) {
  return fetch({
    url: driverMsg.get,
    method: 'get',
    params
  })
}

//添加或修改数据
export function save(data) {
  return fetch({
    url: driverMsg.save,
    method: 'post',
    data
  })
}

