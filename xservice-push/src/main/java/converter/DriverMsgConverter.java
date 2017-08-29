package converter;

import java.util.Date;

import domain.DriverMsg;
import io.vertx.core.json.JsonObject;

public class DriverMsgConverter {

	public static void fromJson(JsonObject json, DriverMsg driverMsg) {
		if(json.getValue("id") instanceof Long){
			driverMsg.setId(json.getLong("id"));
		}
		if(json.getValue("title") instanceof String){
			driverMsg.setTitle(json.getString("title"));
		}
		if(json.getValue("synopsis") instanceof String){
			driverMsg.setSynopsis(json.getString("synopsis"));
		}
		if(json.getValue("content") instanceof String){
			driverMsg.setContent(json.getString("content"));
		}
		if(json.getValue("isShellsScreen") instanceof Integer){
			driverMsg.setIsShellsScreen(json.getInteger("isShellsScreen"));
		}
		if(json.getValue("status") instanceof Integer){
			driverMsg.setStatus(json.getInteger("status"));
		}
		if(json.getValue("msgType") instanceof Integer){
			driverMsg.setMsgType(json.getInteger("msgType"));
		}
		if(json.getValue("createdUser") instanceof String){
			driverMsg.setCreatedUser(json.getString("createdUser"));
		}
		if(json.getValue("updatedUser") instanceof String){
			driverMsg.setUpdatedUser(json.getString("updatedUser"));
		}
		if(json.getValue("jumpUrl") instanceof String){
			driverMsg.setJumpUrl(json.getString("jumpUrl"));
		}
		if(json.getValue("isImportant") instanceof Integer){
			driverMsg.setIsImportant(json.getInteger("isImportant"));
		}
		if(json.getValue("enabled") instanceof Integer){
			driverMsg.setEnabled(json.getInteger("enabled"));
		}
		if(json.getValue("createdTime") instanceof Date){
			driverMsg.setCreatedTime((Date) json.getValue("createdTime"));
		}
		if(json.getValue("updatedTime") instanceof Date){
			driverMsg.setUpdatedTime((Date) json.getValue("updatedTime"));
		}
	}

	@SuppressWarnings("static-access")
	public static void toJson(DriverMsg driverMsg, JsonObject json) {
		json=json.mapFrom(driverMsg);	
	}

}
