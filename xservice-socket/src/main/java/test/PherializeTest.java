package test;

import de.ailis.pherialize.Mixed;
import de.ailis.pherialize.MixedArray;
import de.ailis.pherialize.Pherialize;

public class PherializeTest {

	public static void main(String[] args) {
		MixedArray a = Pherialize.unserialize(
				"a:2:{s:6:\"method\";s:7:\"sendmsg\";s:6:\"params\";a:4:{i:0;i:14838829;i:1;i:302;i:2;i:0;i:3;a:4:{s:4:\"nick\";N;s:5:\"msgId\";s:32:\"c4f4c46e1ff148b9af9593c651a95f66\";s:5:\"title\";s:12:\"司机抢单\";s:4:\"body\";s:638:\"{\"orderType\":\"2\",\"sysCurrentTime\":\"1511186142867\",\"startAddName\":\"紫竹院路1号-1人济山庄(西北3门)\",\"orderStatus\":\"10\",\"title\":\"robOrder\",\"isPointDriver\":\"0\",\"isShowBookingPhone\":\"1\",\"tips\":\"\",\"pushId\":\"865414037049753\",\"chatUserId\":\"14838829\",\"serviceId\":\"2\",\"channelsType\":\"\",\"estimateToBookingStartDistance\":\"32.7\",\"bookingTime\":\"1511218800000\",\"sameCity\":\"true\",\"orderNo\":\"P7511186069984335\",\"isUpgradeCar\":\"0\",\"orderPredictAmount\":\"80.0\",\"endAddName\":\"长椿街45号首都医科大学宣武医院\",\"carTypeForUpgrade\":\"\",\"cumulative\":\"5.54\",\"channelsNum\":\"Vmall_1\",\"estimateToBookingStartFee\":\"33.3\",\"stopRobTime\":\"300000\"}\";}}}")
				.toArray();
		Mixed b = Pherialize.unserialize(
				"a:2:{s:6:\"method\";s:7:\"sendmsg\";s:6:\"params\";a:4:{i:0;i:14838829;i:1;i:302;i:2;i:0;i:3;a:4:{s:4:\"nick\";N;s:5:\"msgId\";s:32:\"c4f4c46e1ff148b9af9593c651a95f66\";s:5:\"title\";s:12:\"司机抢单\";s:4:\"body\";s:638:\"{\"orderType\":\"2\",\"sysCurrentTime\":\"1511186142867\",\"startAddName\":\"紫竹院路1号-1人济山庄(西北3门)\",\"orderStatus\":\"10\",\"title\":\"robOrder\",\"isPointDriver\":\"0\",\"isShowBookingPhone\":\"1\",\"tips\":\"\",\"pushId\":\"865414037049753\",\"chatUserId\":\"14838829\",\"serviceId\":\"2\",\"channelsType\":\"\",\"estimateToBookingStartDistance\":\"32.7\",\"bookingTime\":\"1511218800000\",\"sameCity\":\"true\",\"orderNo\":\"P7511186069984335\",\"isUpgradeCar\":\"0\",\"orderPredictAmount\":\"80.0\",\"endAddName\":\"长椿街45号首都医科大学宣武医院\",\"carTypeForUpgrade\":\"\",\"cumulative\":\"5.54\",\"channelsNum\":\"Vmall_1\",\"estimateToBookingStartFee\":\"33.3\",\"stopRobTime\":\"300000\"}\";}}}");

		String msgBody = String.valueOf(a.getArray("params").get(0));
		
		System.out.println(msgBody);
		System.out.println(b.toString());
	}
}
