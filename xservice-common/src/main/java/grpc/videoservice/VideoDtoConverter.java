package grpc.videoservice;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: xuli
 * Date：17/3/10
 * Time: 10:50
 */
public class VideoDtoConverter {

    public static Video fromVideo(cache.dto.Video video){
        if(video == null){
            return null;
        }
        Video.Builder builder = Video.newBuilder();
        if(video.getAlbumId() != null){
            builder.setAlbumId(video.getAlbumId());
        }
        if(video.getId() != null){
            builder.setId(video.getId());
        }
        if(video.getType() != null){
            builder.setType(video.getType());
        }
        if(video.getPorder() != null){
            builder.setPorder(video.getPorder());
        }
        if(video.getActor() != null){
            builder.setActor(video.getActor());
        }
        if(video.getActorPlay() != null){
            builder.setActorPlay(video.getActorPlay());
        }
        if(video.getAlias() != null){
            builder.setAlias(video.getAlias());
        }
        if(video.getArea() != null){
            builder.setArea(video.getArea());
        }
        if(video.getAreaName() != null){
            builder.setAreaName(video.getAreaName());
        }
        if (video.getAttr() != null) {
            builder.setAttr(video.getAttr());
        }
        if (video.getBtime() != null) {
            builder.setBtime(video.getBtime());
        }
        if(video.getCarfilmType() != null){
            builder.setCarfilmType(video.getCarfilmType());
        }
        if(video.getCarfilmTypeName() != null){
            builder.setCarfilmTypeName(video.getCarfilmTypeName());
        }
        if(video.getCartoonStyle() != null){
            builder.setCartoonStyle(video.getCartoonStyle());
        }
        if(video.getCartoonStyleName() != null){
            builder.setCartoonStyleName(video.getCartoonStyleName());
        }
        if (video.getCategoryId() != null) {
            builder.setCategoryId(video.getCategoryId());
        }
        if(video.getCategoryName() != null){
            builder.setCategoryName(video.getCategoryName());
        }
        if (video.getCommentCnt() != null) {
            builder.setCommentCnt(video.getCommentCnt());
        }
        if (video.getCompere() != null) {
            builder.setCompere(video.getCompere());
        }
        if (video.getContentRatingId() != null) {
            builder.setContentRatingId(video.getContentRatingId());
        }
        if (video.getContentRatingValue() != null) {
            builder.setContentRatingValue(video.getContentRatingValue());
        }
        if (video.getCoopPlatform() != null) {
            builder.setCoopPlatform(video.getCoopPlatform());
        }
        if (video.getCopyrightCompany() != null) {
            builder.setCopyrightCompany(video.getCopyrightCompany());
        }
        if (video.getCopyrightEnd() != null) {
            builder.setCopyrightEnd(video.getCopyrightEnd());
        }
        if (video.getCopyrightStart() != null) {
            builder.setCopyrightStart(video.getCopyrightStart());
        }
        if (video.getCopyrightType() != null) {
            builder.setCopyrightType(video.getCopyrightType());
        }
        if (video.getCopyrightTypeName() != null) {
            builder.setCopyrightTypeName(video.getCopyrightTypeName());
        }
        if (video.getCreateTime() != null) {
            builder.setCreateTime(video.getCreateTime());
        }
        if (video.getDeleted() != null) {
            builder.setDeleted(video.getDeleted());
        }
        if (video.getDescription() != null) {
            builder.setDescription(video.getDescription());
        }
        if (video.getDirectory() != null) {
            builder.setDirectory(video.getDirectory());
        }
        if (video.getDisableType() != null) {
            builder.setDisableType(video.getDisableType());
        }
        if (video.getDisableTypeName() != null) {
            builder.setDisableTypeName(video.getDisableTypeName());
        }

        if (video.getDolbyStreams() != null) {
            List<Stream> array = new ArrayList<>();
            video.getDolbyStreams().forEach(item -> array.add(fromStream(item)));
            builder.addAllDolbyStreams(array);
        }
        if (video.getDownloadPlatform() != null) {
            builder.setDownloadPlatform(video.getDownloadPlatform());
        }
        if (video.getDrmFlagId() != null) {
            builder.setDrmFlagId(video.getDrmFlagId());
        }

        if (video.getDtsStreams() != null) {
            List<Stream> array = new ArrayList<>();
            video.getDtsStreams().forEach(item -> array.add(fromStream(item)));
            builder.addAllDtsStreams(array);
        }
        if (video.getDuration() != null) {
            builder.setDuration(video.getDuration());
        }
        if (video.getEpisode() != null) {
            builder.setEpisode(video.getEpisode());
        }
        if (video.getEtime() != null) {
            builder.setEtime(video.getEtime());
        }
        if (video.getFieldType() != null) {
            builder.setFieldType(video.getFieldType());
        }
        if (video.getFieldTypeName() != null) {
            builder.setFieldTypeName(video.getFieldTypeName());
        }
        if (video.getFirstPlayTime() != null) {
            builder.setFirstPlayTime(video.getFirstPlayTime());
        }
        if (video.getGuest() != null) {
            builder.setGuest(video.getGuest());
        }

        if (video.getGuestIds() != null) {
            List<Long> guestIds = video.getGuestIds();
            builder.addAllGuestIds(guestIds);
        }
        if (video.getGuestList() != null) {
            List<ActorCache> array = new ArrayList<>();
            video.getGuestList().forEach(item -> array.add(fromActorCache(item)));
            builder.addAllGuestList(array);
        }
        if (video.getInstructor() != null) {
            builder.setInstructor(video.getInstructor());
        }
        if (video.getIsDanmaku() != null) {
            builder.setIsDanmaku(video.getIsDanmaku());
        }
        if (video.getIsPlayLock() != null) {
            builder.setIsPlayLock(video.getIsPlayLock());
        }
        if (video.getIsmobile() != null) {
            builder.setIsmobile(video.getIsmobile());
        }
        if (video.getIssue() != null) {
            builder.setIssue(video.getIssue());
        }
        if (video.getIssueCompany() != null) {
            builder.setIssueCompany(video.getIssueCompany());
        }
        if (video.getLanguage() != null) {
            builder.setLanguage(video.getLanguage());
        }
        if (video.getLanguageName() != null) {
            builder.setLanguageName(video.getLanguageName());
        }
        if (video.getLetvMakeStyle() != null) {
            builder.setLetvMakeStyle(video.getLetvMakeStyle());
        }
        if (video.getLetvMakeStyleName() != null) {
            builder.setLetvMakeStyleName(video.getLetvMakeStyleName());
        }

        if (video.getLetvProduceStyle() != null) {
            builder.setLetvProduceStyle(video.getLetvProduceStyle());
        }
        if (video.getLetvProduceStyleName() != null) {
            builder.setLetvProduceStyleName(video.getLetvProduceStyleName());
        }

        if (video.getLogonum() != null) {
            builder.setLogonum(video.getLogonum());
        }
        if (video.getMaker() != null) {
            builder.setMaker(video.getMaker());
        }
        if (video.getMid() != null) {
            builder.setMid(video.getMid());
        }

        if (video.getMidL() != null) {
            builder.setMidL(video.getMidL());
        }
        if (video.getMidStreams() != null) {
            builder.setMidStreams(video.getMidStreams());
        }
        if (video.getMusicAuthors() != null) {
            builder.setMusicAuthors(video.getMusicAuthors());
        }
        if (video.getMusicCompose() != null) {
            builder.setMusicCompose(video.getMusicCompose());
        }
        if (video.getMusicStyle() != null) {
            builder.setMusicStyle(video.getMusicStyle());
        }
        if (video.getMusicStyleName() != null) {
            builder.setMusicStyleName(video.getMusicStyleName());
        }
        if (video.getNameCn() != null) {
            builder.setNameCn(video.getNameCn());
        }

        if (video.getNormalStreams() != null) {
            List<Stream> array = new ArrayList<>();
            video.getNormalStreams().forEach(item -> array.add(fromStream(item)));
            builder.addAllNormalStreams(array);
        }
        if (video.getPage() != null) {
            builder.setPage(video.getPage());
        }
        if (video.getPayPlatform() != null) {
            builder.setPayPlatform(video.getPayPlatform());
        }
        if (video.getPic() != null) {
            builder.setPic(video.getPic());
        }

        if (video.getPicAll() != null) {
            List<PicAll> array = new ArrayList<>();
            video.getPicAll().forEach(item -> array.add(fromPicAll(item)));
            builder.addAllPicAll(array);
        }
        if (video.getPicOriginal() != null) {
            builder.setPicOriginal(video.getPicOriginal());
        }
        if (video.getPlayPlatform() != null) {
            builder.setPlayPlatform(video.getPlayPlatform());
        }
        if (video.getPlayStreams() != null) {
            builder.setPlayStreams(video.getPlayStreams());
        }

        if (video.getPopStyle() != null) {
            builder.setPopStyle(video.getPopStyle());
        }
        if (video.getPopStyleName() != null) {
            builder.setPopStyleName(video.getPopStyleName());
        }

        if (video.getPre() != null) {
            builder.setPre(video.getPre());
        }
        if (video.getPreName() != null) {
            builder.setPreName(video.getPreName());
        }
        if (video.getRecordCompany() != null) {
            builder.setRecordCompany(video.getRecordCompany());
        }
        if (video.getRecreationType() != null) {
            builder.setRecreationType(video.getRecreationType());
        }
        if (video.getRecreationTypeName() != null) {
            builder.setRecreationTypeName(video.getRecreationTypeName());
        }
        if (video.getRelativeContent() != null) {
            builder.setRelativeContent(video.getRelativeContent());
        }
        if (video.getReleaseDate() != null) {
            builder.setReleaseDate(video.getReleaseDate());
        }
        if (video.getRemark() != null) {
            builder.setRemark(video.getRemark());
        }
        if (video.getSchool() != null) {
            builder.setSchool(video.getSchool());
        }

        if (video.getScore() != null) {
            builder.setScore(video.getScore());
        }
        if (video.getSegmentIds() != null) {
            List<Long> array = video.getSegmentIds();
            builder.addAllSegmentIds(array);
        }
        if (video.getShortDesc() != null) {
            builder.setShortDesc(video.getShortDesc());
        }
        if (video.getSinger() != null) {
            builder.setSinger(video.getSinger());
        }
        if (video.getSingerType() != null) {
            builder.setSingerType(video.getSingerType());
        }
        if (video.getSingerTypeName() != null) {
            builder.setSingerTypeName(video.getSingerTypeName());
        }
        if (video.getSingleName() != null) {
            builder.setSingleName(video.getSingleName());
        }

        if (video.getSportsType() != null) {
            builder.setSportsType(video.getSportsType());
        }
        if (video.getSportsTypeName() != null) {
            builder.setSportsTypeName(video.getSportsTypeName());
        }
        if (video.getStarring() != null) {
            builder.setStarring(video.getStarring());
        }

        if (video.getStatus() != null) {
            builder.setStatus(video.getStatus());
        }
        if (video.getStatusCode() != null) {
            builder.setStatusCode(video.getStatusCode());
        }

        if (video.getStreams() != null) {
            List<Stream> array = new ArrayList<>();
            video.getStreams().forEach(item -> array.add(fromStream(item)));
            builder.addAllStreams(array);
        }
        if (video.getStyle() != null) {
            builder.setStyle(video.getStyle());
        }
        if (video.getStyleName() != null) {
            builder.setStyleName(video.getStyleName());
        }
        if (video.getSubCategory() != null) {
            builder.setSubCategory(video.getSubCategory());
        }
        if (video.getSubCategoryName() != null) {
            builder.setSubCategoryName(video.getSubCategoryName());
        }
        if (video.getSubTitle() != null) {
            builder.setSubTitle(video.getSubTitle());
        }
        if (video.getTag() != null) {
            builder.setTag(video.getTag());
        }
        if (video.getTeam() != null) {
            builder.setTeam(video.getTeam());
        }

        if (video.getTheatreStreams() != null) {
            List<Stream> array = new ArrayList<>();
            video.getTheatreStreams().forEach(item -> array.add(fromStream(item)));
            builder.addAllTheatreStreams(array);
        }
        if (video.getThreeDStreams() != null) {
            List<Stream> array = new ArrayList<>();
            video.getThreeDStreams().forEach(item -> array.add(fromStream(item)));
            builder.addAllThreeDStreams(array);
        }
        if (video.getTransCodePrefix() != null) {
            builder.setTransCodePrefix(video.getTransCodePrefix());
        }

        if (video.getTravelTheme() != null) {
            builder.setTravelTheme(video.getTravelTheme());
        }
        if (video.getTravelThemeName() != null) {
            builder.setTravelThemeName(video.getTravelThemeName());
        }

        if (video.getTravelType() != null) {
            builder.setTravelType(video.getTravelType());
        }
        if (video.getTravelTypeName() != null) {
            builder.setTravelTypeName(video.getTravelTypeName());
        }

        if (video.getUpdateTime() != null) {
            builder.setUpdateTime(video.getUpdateTime());
        }
        if (video.getVRstreams() != null) {
            List<Stream> array = new ArrayList<>();
            video.getVRstreams().forEach(item -> array.add(fromStream(item)));
            builder.addAllVRstreams(array);
        }
        if (video.getVideoStreams() != null) {
            List<String> array = video.getVideoStreams();
            builder.addAllVideoStreams(array);
        }
        if (video.getVtypeFlag() != null) {
            builder.setVtypeFlag(video.getVtypeFlag());
        }

        if (video.getVv() != null) {
            builder.setVv(video.getVv());
        }
        if (video.getWatchingFocus() != null) {
            List<WatchingFocusCache> array = new ArrayList<>();
            video.getWatchingFocus().forEach(item->array.add(fromWatchingFocusCache(item)));
            builder.addAllWatchingFocus(array);
        }
        return builder.build();
    }

    public static Stream fromStream(cache.dto.Stream stream){
        if(stream == null){
            return null;
        }
        Stream.Builder builder = Stream.newBuilder();
        if (stream.getBandWidth() != null) {
            builder.setBandWidth(stream.getBandWidth());
        }
        if (stream.getCanDown() != null) {
            builder.setCanDown(stream.getCanDown());
        }
        if (stream.getCanPlay() != null) {
            builder.setCanPlay(stream.getCanPlay());
        }
        if (stream.getCode() != null) {
            builder.setCode(stream.getCode());
        }
        if (stream.getFileSize() != null) {
            builder.setFileSize(stream.getFileSize());
        }
        if (stream.getIfCharge() != null) {
            builder.setIfCharge(stream.getIfCharge());
        }
        if (stream.getIsDefault() != null) {
            builder.setIsDefault(stream.getIsDefault());
        }
        if (stream.getKbps() != null) {
            builder.setKbps(stream.getKbps());
        }
        if (stream.getLiveStreams() != null) {
            List<Stream> array = new ArrayList<>();
            stream.getLiveStreams().forEach(item -> array.add(fromStream(item)));
            builder.addAllLiveStreams(array);
        }
        if (stream.getName() != null) {
            builder.setName(stream.getName());
        }
        if (stream.getPlayStreams() != null) {
            List<Stream> array = new ArrayList<>();
            stream.getPlayStreams().forEach(item -> array.add(fromStream(item)));
            builder.addAllPlayStreams(array);
        }
        return builder.build();

    }

    public static ActorCache fromActorCache(cache.dto.ActorCache obj){
        if(obj == null){
            return null;
        }
        ActorCache.Builder builder = ActorCache.newBuilder();
        if (obj.getId() != null) {
            builder.setId(obj.getId());
        }
        if (obj.getImg() != null) {
            builder.setImg(obj.getImg());
        }
        if (obj.getName() != null) {
            builder.setName(obj.getName());
        }
        if (obj.getRole() != null) {
            builder.setRole(obj.getRole());
        }
        if (obj.getType() != null) {
            builder.setType(obj.getType());
        }
        return builder.build();
    }

    public static WatchingFocusCache fromWatchingFocusCache(cache.dto.WatchingFocusCache obj){
        if(obj == null){
            return null;
        }
        WatchingFocusCache.Builder builder = WatchingFocusCache.newBuilder();
        if (obj.getDesc() != null) {
            builder.setDesc(obj.getDesc());
        }
        if (obj.getId() != null) {
            builder.setId(obj.getId());
        }
        if (obj.getTime() != null) {
            builder.setTime(obj.getTime());
        }
        return builder.build();
    }
    public static PicAll fromPicAll(cache.dto.PicAll obj){
        if(obj == null){
            return null;
        }
        PicAll.Builder builder = PicAll.newBuilder();
        if (obj.getPicKey() != null) {
            builder.setPicKey(obj.getPicKey());
        }
        if (obj.getPicValue() != null) {
            builder.setPicValue(obj.getPicValue());
        }
        return builder.build();
    }
}
