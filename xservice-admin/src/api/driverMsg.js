import http from 'common/http'
import {driverMsg} from 'common/port_uri'

//数据列表
export function list(params) {
  return http({
    baseURL: process.env.mc_push_baseURL,
    url: driverMsg.list,
    method: 'get',
    params:params
  })
}

//根据id查询数据
export function get(params) {
  return http({
    baseURL: process.env.mc_push_baseURL,
    url: driverMsg.get,
    method: 'get',  
    params
  })
}

//添加或修改数据
export function save(params) {
  return http({
    baseURL: process.env.mc_push_baseURL,
    url: driverMsg.save,
    method: 'post',
    params
  })
}

//获取供应商列表
export function providerList(){
	return http({
    baseURL: process.env.mc_push_baseURL,
		url:driverMsg.providerList,
		method:'get'
	})
}

//获取城市列表
export function cityList(){
	return http({
    baseURL: process.env.mc_push_baseURL,
		url:driverMsg.cityList,
		method:'post'	
	})
}

//获取司机列表
export function driverList(params){
	return http({
    baseURL: process.env.mc_push_baseURL,
		url:driverMsg.driverList,
		method:'post',
		params
	})
}

