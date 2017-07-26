/*
 * Copyright 2014 Red Hat, Inc.
 *
 * Red Hat licenses this file to you under the Apache License, version 2.0
 * (the "License"); you may not use this file except in compliance with the
 * License.  You may obtain a copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

package statistic.tp.msg.rxjava;

import java.util.Map;
import rx.Observable;
import rx.Single;
import io.vertx.rxjava.core.Vertx;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;

/**
 * Created by lufei
 * Date : 2017/7/25 15:14
 * Description :
 *
 * <p/>
 * NOTE: This class has been automatically generated from the {@link statistic.tp.msg.MsgStatDao original} non RX-ified interface using Vert.x codegen.
 */

@io.vertx.lang.rxjava.RxGen(statistic.tp.msg.MsgStatDao.class)
public class MsgStatDao {

  public static final io.vertx.lang.rxjava.TypeArg<MsgStatDao> __TYPE_ARG = new io.vertx.lang.rxjava.TypeArg<>(
    obj -> new MsgStatDao((statistic.tp.msg.MsgStatDao) obj),
    MsgStatDao::getDelegate
  );

  private final statistic.tp.msg.MsgStatDao delegate;
  
  public MsgStatDao(statistic.tp.msg.MsgStatDao delegate) {
    this.delegate = delegate;
  }

  public statistic.tp.msg.MsgStatDao getDelegate() {
    return delegate;
  }

  public static MsgStatDao createProxy(Vertx vertx) { 
    MsgStatDao ret = MsgStatDao.newInstance(statistic.tp.msg.MsgStatDao.createProxy(vertx.getDelegate()));
    return ret;
  }

  public static MsgStatDao createLocalProxy(Vertx vertx) { 
    MsgStatDao ret = MsgStatDao.newInstance(statistic.tp.msg.MsgStatDao.createLocalProxy(vertx.getDelegate()));
    return ret;
  }

  public static String getLocalAddress() { 
    String ret = statistic.tp.msg.MsgStatDao.getLocalAddress();
    return ret;
  }

  /**
   * 统计push消息
   * @param action 消息动作 1：send 2：Arrive
   * @param msgId 
   * @param osType 
   * @param result 
   */
  public void statPushMsg(int action, String msgId, int osType, Handler<AsyncResult<String>> result) { 
    delegate.statPushMsg(action, msgId, osType, result);
  }

  /**
   * 统计push消息
   * @param action 消息动作 1：send 2：Arrive
   * @param msgId 
   * @param osType 
   * @return 
   */
  public Single<String> rxStatPushMsg(int action, String msgId, int osType) { 
    return Single.create(new io.vertx.rx.java.SingleOnSubscribeAdapter<>(fut -> {
      statPushMsg(action, msgId, osType, fut);
    }));
  }


  public static  MsgStatDao newInstance(statistic.tp.msg.MsgStatDao arg) {
    return arg != null ? new MsgStatDao(arg) : null;
  }
}
