package service;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;

import java.util.List;

import service.dto.ChannelCurrentProgramDto;

public interface CurrentProgramService {

    /**
     * 获得频道当前播放节目
     * 
     * @param channelIds
     * @param resultHandler
     */
    void getChannelCurrentProgram(List<String> channelIds, int clientId,
            Handler<AsyncResult<ChannelCurrentProgramDto>> resultHandler);
}
