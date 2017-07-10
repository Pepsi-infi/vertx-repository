package tp.cms.response;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: xuli
 * Date：16/11/1
 * Time: 11:42
 */
@DataObject(generateConverter = true)
public class RatingRankResponse {
    private List<RatingAndPlayRankTp> ratingAndPlayRankTps;

    public RatingRankResponse() {
    }

    public RatingRankResponse(JsonObject json) {
        // A converter is generated to easy the conversion from and to JSON.
        RatingRankResponseConverter.fromJson(json, this);
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        RatingRankResponseConverter.toJson(this, json);
        return json;
    }

    public List<RatingAndPlayRankTp> getRatingAndPlayRankTps() {
        return ratingAndPlayRankTps;
    }

    public void setRatingAndPlayRankTps(List<RatingAndPlayRankTp> ratingAndPlayRankTps) {
        this.ratingAndPlayRankTps = ratingAndPlayRankTps;
    }

    @Override
    public String toString() {
        return "RatingRankResponse{" +
                "ratingAndPlayRankTps=" + ratingAndPlayRankTps +
                '}';
    }
}
