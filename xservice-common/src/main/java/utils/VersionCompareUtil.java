package utils;

public class VersionCompareUtil {

	public static int hisCompare2Current(String hisVersion, String currentVersion) {
		String[] his = hisVersion.split("\\.");
		String[] current = currentVersion.split("\\.");

		int hisFirstVersion = Integer.valueOf(his[0]);
		int hisMiddleVersion = Integer.valueOf(his[1]);
		int hisLastVersion = Integer.valueOf(his[2]);

		int curFirstVersion = Integer.valueOf(current[0]);
		int curMiddleVersion = Integer.valueOf(current[1]);
		int currLastVersion = Integer.valueOf(current[2]);

		int cmpResult;

		int firstCmpResult = compareVersion(hisFirstVersion, curFirstVersion);

		if (firstCmpResult == 0) {

			int middleCmpResult = compareVersion(hisMiddleVersion, curMiddleVersion);

			if (middleCmpResult == 0) {

				cmpResult = compareVersion(hisLastVersion, currLastVersion);

			} else {

				cmpResult = middleCmpResult;
			}

		} else {
			cmpResult = firstCmpResult;
		}

		return cmpResult;

	}

	private static int compareVersion(int hisVersion, int curFirstVersion) {
		return hisVersion - curFirstVersion;
	}
	
	public static void main(String[] args) {
		System.out.println(hisCompare2Current("5.2.2","5.2.4"));
		System.out.println(hisCompare2Current("5.2.2","4.2.4"));
		System.out.println(hisCompare2Current("5.20.2","5.2.4"));
		System.out.println(hisCompare2Current("5.2.20","5.2.4"));
		System.out.println(hisCompare2Current("5.2.20","5.20.4"));
	}

}
