package test;

import java.nio.charset.Charset;

import org.apache.commons.lang.math.NumberUtils;

import de.ailis.pherialize.MixedArray;
import de.ailis.pherialize.Pherialize;
import io.vertx.core.json.JsonObject;

public class PhTest {

	public static void main(String[] args) {
		MixedArray map = Pherialize.unserialize(
				"a:2:{s:6:\"method\";s:7:\"sendmsg\";s:6:\"params\";a:4:{i:0;i:16671561;i:1;i:306;i:2;i:0;i:3;a:4:{s:4:\"body\";s:138:\"{\"currentTime\":\"1511258995\",\"i\n"
						+ "nCircle\":\"1\",\"dutyStatus\":\"1\",\"status\":\"\",\"workInnerTime\":\"17820\",\"chatUserId\":\"16671561\",\"dayTime\":\"17820\"}\";s:5:\"title\";s:42:\"今日在线时长，班制内在线时长\";s:4:\"nick\";N;s:5:\"msgId\";s:32:\"845f81839a3249ba9f329f46ee8468e7\";}}}",
				Charset.forName("UTF-8")).toArray();

		MixedArray msgBody = map.getArray("params");

		final String userId = String.valueOf(msgBody.get(0));// userId
		int cmd = NumberUtils.toInt(String.valueOf(msgBody.get(1)));
		JsonObject data = JsonObject.mapFrom(msgBody.get(3));

		System.out.println(userId);
		System.out.println(cmd);
		System.out.println(data.encode());
	}
}
