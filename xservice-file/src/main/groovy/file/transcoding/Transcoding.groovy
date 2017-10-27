def ffmpeg = "/Users/wanglonghu/Downloads/ffmpeg-20171022-72c3d9a-macos64-static/bin/ffmpeg"

void vertxStart() {
	def eb = vertx.eventBus()
	eb.consumer("file.transcoding.Transcoding", { message ->
		def file = message.body()
 		Process p="ffmpeg  -i ${file}.amr ${file}.mp3".execute() 		
	})
}

void vertxStop() {
	println "stopping"
}