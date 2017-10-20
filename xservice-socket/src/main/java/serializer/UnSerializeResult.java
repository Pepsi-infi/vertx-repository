package serializer;

/**
 * 
 * Author: Ma Bingyao <andot@ujn.edu.cn> Copyright: CoolCode.CN Version: 2.1
 * LastModified: 2006-08-09 This library is free. You can redistribute it and/or
 * modify it. http://www.coolcode.cn/?p=202
 */
public class UnSerializeResult {
	public Object value;
	public int hv;

	public UnSerializeResult() {
	}

	public UnSerializeResult(Object value, int hv) {
		this.value = value;
		this.hv = hv;
	}
}
