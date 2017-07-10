package util;

import cache.dto.Album;
import cache.dto.Video;
import constants.ChannelConstants;
import constants.CommonConstants;
import video.VideoConstants;
import org.apache.commons.lang.StringUtils;

/**
 * Created by zhushenghao1 on 16/12/28.
 */
public class ChannelHelper {

    public String fillCornerLabelByCache(Album album, Video video, String dType, String pageId, String terminalApplication) {
        if (album == null && video == null) {
            return null;
        }
        String isPay = null;
        if ("lecom".equals(terminalApplication)) {
            if (album != null && ChannelConstants.DataType.DATA_TYPE_ALBUM.equals(dType)) {
                isPay = album.getIsyuanxian() == null ? null : String.valueOf(album.getIsyuanxian());
            }
            if (video != null && ChannelConstants.DataType.DATA_TYPE_VIDEO.equals(dType)
                    && video.getType().intValue() == VideoConstants.VideoType.ZHENG_PIAN) {
                if (StringUtils.isNotBlank(video.getPayPlatform())) {
                    isPay = video.getPayPlatform().contains(CommonConstants.Platform.MOBILE) ? "1" : null;
                }
            }
        }
        return this.getCornerLabel(isPay);
    }

    private String getCornerLabel(String isPay) {
        String cType = null;
        if (StringUtils.isNotBlank(isPay) && "1".equals(isPay)) {
            // 付费角标
            cType = ChannelConstants.CornerLabel.IS_PAY;
        }
        return cType;
    }
}

