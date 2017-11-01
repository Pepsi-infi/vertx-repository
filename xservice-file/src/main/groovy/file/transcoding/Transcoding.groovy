def ffmpeg = "/u01/projectCAR/xservice/xservice-file/ffmpeg"

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