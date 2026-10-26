package control;

import java.net.URLDecoder;

/**
 * http://www.javapractices.com/topic/TopicAction.do?Id=2
 */
public final class Consts {
    private Consts() {
        throw new AssertionError();
    }

    public enum Manipulation {
        UPDATE, INSERT, DELETE;
    }

    protected static final String DB_FILEPATH = getDBPath();
    public static final String CONN_STR = "jdbc:ucanaccess://" + DB_FILEPATH + ";COLUMNORDER=DISPLAY";

    /**
     * find the correct path of the DB file
     * 
     * @return the path of the DB file (from eclipse or with runnable file)
     */
    private static String getDBPath() {
        try {
            String path = Consts.class.getProtectionDomain().getCodeSource().getLocation().getPath();
            String decoded = URLDecoder.decode(path, "UTF-8");
            if (decoded.contains(".jar")) {
                decoded = decoded.substring(0, decoded.lastIndexOf('/'));
                return decoded + "/database/north2000DB.accdb";
            } else {
                decoded = decoded.substring(0, decoded.lastIndexOf("bin/"));
                return decoded + "src/entity/north2000DB.accdb";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

   
}
