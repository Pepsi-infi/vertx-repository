var merge = require('webpack-merge')
var prodEnv = require('./prod.env')

module.exports = merge(prodEnv, {
  NODE_ENV: '"development"',
  //统计中心domain
  mc_statistic_baseURL: '"http://127.0.0.1:9100"',
  //配置中心domain
  mc_config_baseURL: '"http://127.0.0.1:9200"',
  //配置中心domain
  mc_push_baseURL: '"http://127.0.0.1:8989"'
})
