package vip.response;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

@DataObject(generateConverter = true)
public class TrialData {
    private String trialField;// 可能值 month year date
    private Integer trialDuration;// 例如：1
    private String trialDurationName;// 例如:自然月

    public TrialData() {
    }

    public TrialData(JsonObject json) {
        TrialDataConverter.fromJson(json, this);
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        TrialDataConverter.toJson(this, json);
        return json;
    }
    public String getTrialField() {
        return trialField;
    }

    public void setTrialField(String trialField) {
        this.trialField = trialField;
    }

    public Integer getTrialDuration() {
        return trialDuration;
    }

    public void setTrialDuration(Integer trialDuration) {
        this.trialDuration = trialDuration;
    }

    public String getTrialDurationName() {
        return trialDurationName;
    }

    public void setTrialDurationName(String trialDurationName) {
        this.trialDurationName = trialDurationName;
    }

    @Override
    public String toString() {
        return "TrialData [trialField=" + trialField + ", trialDuration=" + trialDuration + ", trialDurationName="
                + trialDurationName + "]";
    }

}
