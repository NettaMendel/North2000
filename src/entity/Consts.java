package entity;
import java.net.URLDecoder;

/**
 * http://www.javapractices.com/topic/TopicAction.do?Id=2
 */
public final class Consts {
    private Consts() {
        throw new AssertionError();
    }

	protected static final String DB_FILEPATH = getDBPath();
	public static final String CONN_STR = "jdbc:ucanaccess://" + DB_FILEPATH + ";COLUMNORDER=DISPLAY";
	
	
    
    public static final String SEL_PRODUCTS = "SELECT * FROM TblProducts";
    
	/**
	 * find the correct path of the DB file
     * @return the path of the DB file (from eclipse or with runnable file)
	 */
	private static String getDBPath() {
		try {
			String path = Consts.class.getProtectionDomain().getCodeSource().getLocation().getPath();
			String decoded = URLDecoder.decode(path, "UTF-8");
			// System.out.println(decoded) - Can help to check the returned path
			if (decoded.contains(".jar")) {
				decoded = decoded.substring(0, decoded.lastIndexOf('/'));
				return decoded + "/database/Tirgul05_north2000.accdb";
			} else {
				decoded = decoded.substring(0, decoded.lastIndexOf("bin/"));
				System.out.println(decoded);
				return decoded + "src/entity/Tirgul05_north2000.accdb";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
