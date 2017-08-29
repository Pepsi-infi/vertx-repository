package domain;

import java.io.Serializable;
import java.util.List;

import converter.PageConverter;
import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

@DataObject(generateConverter = true)
public class Page implements Serializable {

	private static final long serialVersionUID = 123424534523452346L;
	private int pageNumber;
	private int pageSize;
	private List<DriverMsg> list;

	private boolean hasNextPage;
	private Long total;

	public Page() {
	}

	public Page(int pageNumber, int pageSize, List<DriverMsg> list, Long total) {
		this.pageNumber = pageNumber;
		this.pageSize = pageSize;
		this.list = list;
		this.total = total;
		if (total > pageNumber * pageSize) {
			hasNextPage = true;
		}
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public List<DriverMsg> getList() {
		return list;
	}

	public void setList(List<DriverMsg> list) {
		this.list = list;
	}

	public boolean isHasNextPage() {
		return hasNextPage;
	}

	public void setHasNextPage(boolean hasNextPage) {
		this.hasNextPage = hasNextPage;
	}

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
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

}
