void vertxStart() {
	def eb = vertx.eventBus()
	eb.consumer("file.transcoding.Transcoding", {message ->
		def file = message.body()
		Process p="ffmpeg -i ${file} ${file}.mp3".execute()
	})
}

void vertxStop() {
	println "stopping"
}