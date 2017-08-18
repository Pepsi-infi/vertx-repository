###
migrate_device.py 迁移 ios 设备数据脚本

启动命令：
nohup python migrate_device.py > ../log/migrate_device.log 2>&1 &

* */1 * * *  python ~/script/migrate_device.py >> ~/log/migrate_device.log
根据不同启动参数，支持全量和增量导入
##1、传参 all 导入全量
  2、指定日期导入该日期之后的增量(格式 2017-08-16)
  3、默认增量导入一小时之前的数据
