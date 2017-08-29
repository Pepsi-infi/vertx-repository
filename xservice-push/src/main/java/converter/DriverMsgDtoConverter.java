package converter;

import java.util.Date;

import dto.DriverMsgDto;
import io.vertx.core.json.JsonObject;

public class DriverMsgDtoConverter {

	public static void fromJson(JsonObject json, DriverMsgDto driverMsgDto) {
		if(json.getValue("title") instanceof String){
			driverMsgDto.setTitle(json.getString("title"));
		}
		if(json.getValue("msgType") instanceof Number){
			driverMsgDto.setMsgType(((Number) json.getValue("msgType")).intValue());
		}
		if(json.getValue("pageNumber") instanceof Number){
			driverMsgDto.setPageNumber(((Number)json.getValue("pageNumber")).intValue());
		}
		if(json.getValue("pageSize") instanceof Number){
			driverMsgDto.setPageSize(((Number)json.getValue("pageSize")).intValue());
		}
		if(json.getValue("startTime") instanceof Date){
			driverMsgDto.setStartTime((Date)json.getValue("startTime"));
		}
		if(json.getValue("endTime") instanceof Date){
			driverMsgDto.setEndTime((Date)json.getValue("endTime"));
		}
	}

	@SuppressWarnings("static-access")
	public static void toJson(DriverMsgDto driverMsgDto, JsonObject json) {
		json=json.mapFrom(driverMsgDto);
	}

}
