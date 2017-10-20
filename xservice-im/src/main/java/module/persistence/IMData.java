package module.persistence;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import module.c2c.protocol.SQIMBody;

@JsonInclude(Include.NON_NULL)
public class IMData extends SQIMBody {

	private int cmdId;

	public int getCmdId() {
		return cmdId;
	}

	public void setCmdId(int cmdId) {
		this.cmdId = cmdId;
	}
}
