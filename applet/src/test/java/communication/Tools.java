package communication;

public class Tools {
    public static byte[] fromHex(String hex){
        // ukradeno
        byte[] res = new byte[hex.length() / 2];

        for (int i = 0; i < res.length; i++) {
            int index = i * 2;
            int j = Integer.parseInt(hex.substring(index, index + 2), 16);
            res[i] = (byte) j;
        }

        return res;
    }

    public static String bytesToHex(byte[] bytes) {
        StringBuilder res = new StringBuilder();
        int cb;

        for (byte aByte : bytes) {
            cb = aByte & 0xFF;
            res.append(Integer.toHexString(cb / 16));
            res.append(Integer.toHexString(cb % 16));
        }
        return res.toString();
    }
}
