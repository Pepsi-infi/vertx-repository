package module.c2c.protocol;

public class SQIMHeader {

	private Integer headerLength;

	private Integer clientVersion;

	private Integer cmdId;

	private Integer bodyLength;

	public Integer getHeaderLength() {
		return headerLength;
	}

	public void setHeaderLength(Integer headerLength) {
		this.headerLength = headerLength;
	}

	public Integer getClientVersion() {
		return clientVersion;
	}

	public void setClientVersion(Integer clientVersion) {
		this.clientVersion = clientVersion;
	}

	public Integer getCmdId() {
		return cmdId;
	}

	public void setCmdId(Integer cmdId) {
		this.cmdId = cmdId;
	}

	public Integer getBodyLength() {
		return bodyLength;
	}

	public void setBodyLength(Integer bodyLength) {
		this.bodyLength = bodyLength;
	}
}
