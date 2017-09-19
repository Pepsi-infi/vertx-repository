package domain;

import java.io.Serializable;
import java.util.Date;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

@DataObject(generateConverter=true)
public class DriverMsgDto implements Serializable{


	
		/**
		 * 
		 */
		private static final long serialVersionUID = 7239153321321627091L;
		private String title;//标题
		private Integer msgType;//消息类型 扩展用
		private Date startTime;//开始时间
		private Date endTime;//结束时间
		private Integer pageSize;//分页大小
		private Integer pageNumber;//页码
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		public Integer getMsgType() {
			return msgType;
		}
		public void setMsgType(Integer msgType) {
			this.msgType = msgType;
		}
		public Date getStartTime() {
			return startTime;
		}
		public void setStartTime(Date startTime) {
			this.startTime = startTime;
		}
		public Date getEndTime() {
			return endTime;
		}
		public void setEndTime(Date endTime) {
			this.endTime = endTime;
		}
		public Integer getPageSize() {
			return pageSize;
		}
		public void setPageSize(Integer pageSize) {
			this.pageSize = pageSize;
		}
		public Integer getPageNumber() {
			return pageNumber;
		}
		public void setPageNumber(Integer pageNumber) {
			this.pageNumber = pageNumber;
		}
		
		public DriverMsgDto() {
			
			super();
		};
		
		public DriverMsgDto(JsonObject json) {
			DriverMsgDtoConverter.fromJson(json, this);
		}

		public JsonObject toJson() {
			JsonObject json = new JsonObject();
			DriverMsgDtoConverter.toJson(this, json);
			return json;
		}

		
		


}
