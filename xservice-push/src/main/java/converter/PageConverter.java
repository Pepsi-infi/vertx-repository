package converter;

import domain.Page;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

public class PageConverter {

	public static void fromJson(JsonObject json, Page obj) {
		if (json.getValue("hasNextPage") instanceof Boolean) {
			obj.setHasNextPage((Boolean) json.getValue("hasNextPage"));
		}
		if (json.getValue("list") instanceof JsonArray) {
			java.util.ArrayList<domain.DriverMsg> list = new java.util.ArrayList<>();
			json.getJsonArray("list").forEach(item -> {
				if (item instanceof JsonObject)
					list.add(new domain.DriverMsg((JsonObject) item));
			});
			obj.setList(list);
		}
		if (json.getValue("page") instanceof Number) {
			obj.setPageNumber(((Number) json.getValue("page")).intValue());
		}
		if (json.getValue("pageSize") instanceof Number) {
			obj.setPageSize(((Number) json.getValue("pageSize")).intValue());
		}
		if (json.getValue("total") instanceof Number) {
			obj.setTotal(((Number) json.getValue("total")).longValue());
		}
	}

	public static void toJson(Page obj, JsonObject json) {
		json.put("hasNextPage", obj.isHasNextPage());
		if (obj.getList() != null) {
			JsonArray array = new JsonArray();
			obj.getList().forEach(item -> array.add(item.toJson()));
			json.put("list", array);
		}
		json.put("page", obj.getPageNumber());
		json.put("pageSize", obj.getPageSize());
		if (obj.getTotal() != null) {
			json.put("total", obj.getTotal());
		}
	}

}
