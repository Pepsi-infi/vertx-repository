package test;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class OldSocketTest {

	public static void main(String[] args) {
		// Vertx vertx = Vertx.vertx();
		// NetClient client = vertx.createNetClient();
		//
		// // 1
		// String req = "get
		// /mobile?user=13666098&hash=UDE4NTEwMjUyNzk5fDE1MDU4Nzg4NzcyMzY.&mid=iphone&cid=AppStore&ver=5.2.0
		// HTTP/1.0";
		//
		// client.connect(8088, "111.206.162.233", con -> {
		// if (con.succeeded()) {
		// NetSocket socket = con.result();
		// socket.write(req);
		// } else {
		//
		// }
		// });

		System.out.println(UUID.randomUUID().toString().replaceAll("-", ""));

		// 1506042848
		// 1506417304350
		// 1506417322
		System.out.println(System.currentTimeMillis() / 1000);

		//"2017-09-22 13:30:22";// TODO
		LocalDateTime arrivalDate = LocalDateTime.now();
		try {
			DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
			String landing = arrivalDate.format(format);
			System.out.printf("Arriving at : %s %n", landing);
		} catch (DateTimeException ex) {
			System.out.printf("%s can't be formatted!%n", arrivalDate);
			ex.printStackTrace();
		}

	}

}
