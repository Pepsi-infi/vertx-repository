package cache.dto;

import com.baidu.bjf.remoting.protobuf.FieldType;
import com.baidu.bjf.remoting.protobuf.annotation.Protobuf;
import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

@DataObject(generateConverter = true)
public class Page {
    @Protobuf(fieldType = FieldType.INT32,order = 1)
	private Integer page;

    @Protobuf(fieldType = FieldType.INT32,order = 2)
    private Integer pageSize;

	public Page() {

	}

	public Page(Integer page, Integer pageSize) {
		super();
		this.page = page;
		this.pageSize = pageSize;
	}

	public Page(JsonObject json) {
		// A converter is generated to easy the conversion from and to JSON.
		PageConverter.fromJson(json, this);
	}

	public JsonObject toJson() {
		JsonObject json = new JsonObject();
		PageConverter.toJson(this, json);
		return json;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

}
