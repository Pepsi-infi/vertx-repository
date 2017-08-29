import fetch from 'common/fetch'
import {driverPush} from 'common/port_uri'

//数据列表
export function list(params) {
  return fetch({
    url: driverPush.list,
    method: 'get',
    params
  })
}

//根据id查询数据
export function get(params) {
  return fetch({
    url: driverPush.get,
    method: 'get',
    params
  })
}

//添加或修改数据
export function save(data) {
  return fetch({
    url: driverPush.save,
    method: 'post',
    data
  })
}

