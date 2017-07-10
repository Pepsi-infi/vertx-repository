package utils;

import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;

public class IPUtil {

    private static String innerIP;

    /**
     * 获取Linux eth0网卡IP
     */
    public static String getEth0IP() {
        String ip = null;
        try {
            if (true) {
                Enumeration<?> e1 = (Enumeration<?>) NetworkInterface.getNetworkInterfaces();
                while (e1.hasMoreElements()) {
                    NetworkInterface ni = (NetworkInterface) e1.nextElement();
                    if (!ni.getName().equals("eth0")) {
                        continue;
                    } else {
                        Enumeration<?> e2 = ni.getInetAddresses();
                        while (e2.hasMoreElements()) {
                            InetAddress ia = (InetAddress) e2.nextElement();
                            if (ia instanceof Inet6Address)
                                continue;
                            ip = ia.getHostAddress();
                        }
                        break;
                    }
                }
            } else {
                ip = InetAddress.getLocalHost().getHostAddress().toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ip;
    }

    /**
     * 获得所有网卡地址
     * @return
     */
    public static Collection<InetAddress> getAllHostAddress() {
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            Collection<InetAddress> addresses = new ArrayList<InetAddress>();

            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterface = networkInterfaces.nextElement();
                Enumeration<InetAddress> inetAddresses = networkInterface.getInetAddresses();
                while (inetAddresses.hasMoreElements()) {
                    InetAddress inetAddress = inetAddresses.nextElement();
                    addresses.add(inetAddress);
                }
            }

            return addresses;
        } catch (SocketException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    /**
     * 获得内网ip
     * @return
     */
    public static String getInnerIP() {
        if (innerIP == null) {
            Collection<InetAddress> allHost = getAllHostAddress();
            for (InetAddress host : allHost) {
                if (host.getHostAddress().startsWith("10.")) {
                    innerIP = host.getHostAddress();
                }
            }
        }

        return innerIP;
    }

    public static void main(String[] args) {
        System.out.println(getAllHostAddress());
        System.out.println(getInnerIP());
    }
}
