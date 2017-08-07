#!/bin/sh

cd $WORKSPACE/xservice-admin

#
echo "编译中....."
npm run build || { echo "编译失败，请检查后重试"; exit 1; }

#
echo "压缩打包中......"
mkdir -p output

tar zcvf xservice-admin.tgz dist/ index.html static/

