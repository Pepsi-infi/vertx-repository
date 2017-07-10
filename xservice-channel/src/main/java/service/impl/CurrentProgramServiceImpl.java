package service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import function.Functional;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.rxjava.core.Vertx;
import service.CurrentProgramService;
import service.dto.ChannelCurrentProgramDto;
import service.dto.ProgramDto;
import tp.live.LiveTpDao;
import tp.live.impl.LiveTpDaoImpl;
import tp.live.request.PlayBillCurrentRequest;
import tp.live.response.PlayBillCurrentTpResponse;
import tp.live.response.PlayBillCurrentTpRows;
import tp.live.response.ProgramTp;

public class CurrentProgramServiceImpl implements CurrentProgramService {

    private LiveTpDao liveTpDao;

    public CurrentProgramServiceImpl() {

    }

    public CurrentProgramServiceImpl(Vertx vertx) {
        this.liveTpDao = new LiveTpDaoImpl();
    }

    @Override
    public void getChannelCurrentProgram(List<String> channelIds, int clientId,
            Handler<AsyncResult<ChannelCurrentProgramDto>> result) {
        ChannelCurrentProgramDto channelCurrentProgramDto = new ChannelCurrentProgramDto();
        if (CollectionUtils.isNotEmpty(channelIds) && channelIds.size() < 20) {
            List<ProgramDto> dtoList = new ArrayList<>();
            List<Future<PlayBillCurrentTpResponse>> tpFutureList = new ArrayList<Future<PlayBillCurrentTpResponse>>();
            for (String id : channelIds) {
                Future<PlayBillCurrentTpResponse> tpFuture = Future.future();
                PlayBillCurrentRequest request = new PlayBillCurrentRequest();
                request.setChannelIds(Integer.valueOf(id));
                request.setClientId(clientId);// TV LIVE client id.
                liveTpDao.getTpPlayBillCurrent(request, tpFuture.completer());
                tpFutureList.add(tpFuture);
            }

            Future<List<PlayBillCurrentTpResponse>> resultFuture = Functional.sequenceFuture(tpFutureList);
            resultFuture.setHandler(r -> {
                if (r.succeeded()) {
                    List<PlayBillCurrentTpResponse> tpResponseList = r.result();
                    for (PlayBillCurrentTpResponse tpRes : tpResponseList) {
                        if (tpRes != null) {
                            dtoList.addAll(getCurrentProgram(tpRes));
                        }
                    }
                    channelCurrentProgramDto.setPrograms(dtoList);
                    result.handle(Future.succeededFuture(channelCurrentProgramDto));
                } else {
                    result.handle(Future.failedFuture(r.cause()));
                }
            });
        } else {
            result.handle(Future.failedFuture("Max length of channelIds is 20."));
        }
    }

    private List<ProgramDto> getCurrentProgram(PlayBillCurrentTpResponse tpRes) {
        List<ProgramDto> programDtoList = null;
        List<PlayBillCurrentTpRows> tpRows = tpRes.getRows();
        if (CollectionUtils.isNotEmpty(tpRows)) {
            programDtoList = new ArrayList<ProgramDto>();
            for (PlayBillCurrentTpRows row : tpRows) {
                ProgramTp programTp = row.getCur();
                if (programTp != null) {
                    ProgramDto programDto = new ProgramDto();
                    programDto.setId(programTp.getId());
                    programDto.setChannelId(row.getChannelId());
                    programDto.setDuration(programTp.getDuration());
                    programDto.setPlayTime(programTp.getPlayTime());
                    programDto.setEndTime(programTp.getEndTime());
                    programDto.setTitle(programTp.getTitle());
                    programDto.setVid(programTp.getVid());
                    programDto.setAid(programTp.getAid());
                    programDto.setProgramType(programTp.getProgramType());

                    programDtoList.add(programDto);
                }
            }
        }

        return programDtoList;
    }
}
