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

package cluster.rxjava;

import java.util.Map;
import rx.Observable;
import rx.Single;
import io.vertx.rxjava.core.Vertx;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;


@io.vertx.lang.rxjava.RxGen(cluster.ConsistentHashingService.class)
public class ConsistentHashingService {

  public static final io.vertx.lang.rxjava.TypeArg<ConsistentHashingService> __TYPE_ARG = new io.vertx.lang.rxjava.TypeArg<>(
    obj -> new ConsistentHashingService((cluster.ConsistentHashingService) obj),
    ConsistentHashingService::getDelegate
  );

  private final cluster.ConsistentHashingService delegate;
  
  public ConsistentHashingService(cluster.ConsistentHashingService delegate) {
    this.delegate = delegate;
  }

  public cluster.ConsistentHashingService getDelegate() {
    return delegate;
  }

  public static ConsistentHashingService createProxy(Vertx vertx) { 
    ConsistentHashingService ret = ConsistentHashingService.newInstance(cluster.ConsistentHashingService.createProxy(vertx.getDelegate()));
    return ret;
  }

  /**
   * 根据key获得用户所属机器内网IP
   * @param key 
   * @param resultHandler 
   */
  public void getNode(String key, Handler<AsyncResult<String>> resultHandler) { 
    delegate.getNode(key, resultHandler);
  }

  /**
   * 根据key获得用户所属机器内网IP
   * @param key 
   * @return 
   */
  public Single<String> rxGetNode(String key) { 
    return Single.create(new io.vertx.rx.java.SingleOnSubscribeAdapter<>(fut -> {
      getNode(key, fut);
    }));
  }


  public static  ConsistentHashingService newInstance(cluster.ConsistentHashingService arg) {
    return arg != null ? new ConsistentHashingService(arg) : null;
  }
}
