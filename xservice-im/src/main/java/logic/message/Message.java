package logic.message;

import com.baidu.bjf.remoting.protobuf.FieldType;
import com.baidu.bjf.remoting.protobuf.annotation.Protobuf;

public class Message {

	@Protobuf(fieldType = FieldType.INT32, order = 1)
	private int from;

	@Protobuf(fieldType = FieldType.INT64, order = 2)
	private long timestamp;

	@Protobuf(fieldType = FieldType.STRING, order = 3)
	private String content;

	@Protobuf(fieldType = FieldType.INT32, order = 4)
	private int to;

	public int getFrom() {
		return from;
	}

	public void setFrom(int from) {
		this.from = from;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getTo() {
		return to;
	}

	public void setTo(int to) {
		this.to = to;
	}

}
