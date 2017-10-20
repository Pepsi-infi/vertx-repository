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
# DestMySQLDataBase = 'statistic'
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

(destcon, destcur) = getMySQLCon(DestMySQLHost,DestMySQLPort,DestMySQLDataBase,DestMySQLUser,DestMySQLPassword)


sql1 = "SELECT id from msg_stat where clickIosSum=1 and clickSum=1"
def delete():
    destcur.execute(sql1)
    result = destcur.fetchall()
    for row in result :
        id=safeGetAttribute(row,0,None)
        print '>>>> %s ' %(id)
        deleteSql = "delete from msg_stat where id= %d" %(id)
        destcur.execute(deleteSql)
        destcon.commit()
    close(destcur, destcur)
    return True



def safeGetAttribute(row, name, defaultValue) :
    try :
        return row[name]
    except Exception as e :
        return defaultValue


if __name__ == '__main__' :
    delete()