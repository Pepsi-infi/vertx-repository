package api;

public class RestFileConstant {

	public interface Server {
		static final int PORT = 10000;
	}

	public interface Uri {
		static final String UPLOAD_FILE_PATH = "/mc-file/im/upload.json";

		static final String DOWNLOAD_FILE_PATH = "/mc-file/im/download.json";
	}
}
