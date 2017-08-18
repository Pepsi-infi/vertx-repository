#! /usr/bin/python
# -*- coding:utf-8 -*-

import MySQLdb
from sys import argv
import datetime

##### env  prod ###########
# SrcMySQLHost = '10.0.10.82'
# SrcMySQLPort = 5310
# SrcMySQLDataBase = 'rentcar'
# SrcMySQLUser = 'stat_query'
# SrcMySQLPassword = 'pggXNBviboEfl@hk'

# DestMySQLHost = '10.0.10.211'
# DestMySQLPort = 5310
# DestMySQLDataBase = 'device'
# DestMySQLUser = 'message_center'
# DestMySQLPassword = 'BzkmQM%O^U7Dy8X3'

##### env  test ###########
# SrcMySQLHost = '10.10.10.103'
# SrcMySQLPort = 3306
# SrcMySQLDataBase = 'rentcar'
# SrcMySQLUser = 'sqhc_rentcar'
# SrcMySQLPassword = 'BzkmQM%O^U7Dy8X3'


# DestMySQLHost = '10.10.10.178'
# DestMySQLPort = 3306
# DestMySQLDataBase = 'device'
# DestMySQLUser = 'sqyc_message'
# DestMySQLPassword = 'sqyc_message@01zhuanche.com'

##### env  dev ###########
SrcMySQLHost = '127.0.0.1'
SrcMySQLPort = 3306
SrcMySQLDataBase = 'device'
SrcMySQLUser = 'root'
SrcMySQLPassword = 'root'

DestMySQLHost = '127.0.0.1'
DestMySQLPort = 3306
DestMySQLDataBase = 'device'
DestMySQLUser = 'root'
DestMySQLPassword = 'root'


def getMySQLCon(host,port,db,user,passwd) :
    myscon = MySQLdb.connect(host=host, port=port, db=db, user=user, passwd=passwd, charset="utf8")
    return myscon, myscon.cursor()

def close(*src) :
    for s in src:
        s.close()

(srccon, srccur) = getMySQLCon(SrcMySQLHost,SrcMySQLPort,SrcMySQLDataBase,SrcMySQLUser,SrcMySQLPassword)
(destcon, destcur) = getMySQLCon(DestMySQLHost,DestMySQLPort,DestMySQLDataBase,DestMySQLUser,DestMySQLPassword)

sql1 = "SELECT * FROM device WHERE phone = %s and deviceToken=%s"
insertSql = "insert into device(phone,channel,deviceToken,appCode,createTime,updateTime) values (%s,4,%s,1001,%s,%s)"

def transfer(date=None):
    countSql = "select count(*) from car_biz_europ where device_token !='' %s"
    if(date is not None) :
        countSql = countSql % (" and create_date>'"+date+"'")
    else :
        countSql = countSql % ('')
    print 'countSql'+countSql
    srccur.execute(countSql)
    total = srccur.fetchone()[0]
    count = 0
    page = 0
    insertCount=0
    while count < total :
        querySql = "SELECT * FROM car_biz_europ WHERE device_token !='' %s ORDER BY id limit " + str(page*100) + ', 100'
        if(date is not None) :
            querySql = querySql % (" and create_date>'"+date+"'")
        else :
            querySql = querySql % ('')
        print 'querySql'+querySql
        count = count + srccur.execute(querySql)
        page = page + 1
        result = srccur.fetchall()
        values=[]
        nowDate = datetime.datetime.now().strftime('%Y-%m-%d %H:%M:%S')
        for row in result :
            phone=safeGetAttribute(row,4,None)
            device_token=safeGetAttribute(row,5,None)
            sql1count = destcur.execute(sql1,(phone,device_token))
            if(sql1count>0):
                continue
            data=[phone,device_token]
            data.append(nowDate)
            data.append(nowDate)
            values.append(data)
            insertCount = insertCount + 1
            print '%s >>>>>>> insert %d records to device >>>>>>> ' % (nowDate,insertCount)
        destcur.executemany(insertSql,values)
        destcon.commit()
    print '>>>>>>>total insert %d records to device >>>>>>> ' % (insertCount)


    close(srccur, srccon)
    close(destcur, destcur)
    return True

def main() :
    if 2 == len(argv) :
        param = argv[1]
        if 'all' == param.lower() :
            transfer(None)
        elif 10 == len(param) :
            transfer(param)
        return
    date = (datetime.datetime.now()+datetime.timedelta(hours=-1)).strftime('%Y-%m-%d %H:%M:%S')
    transfer(date)

def safeGetAttribute(row, name, defaultValue) :
    try :
        return row[name]
    except Exception as e :
        return defaultValue


if __name__ == '__main__' :
    main()