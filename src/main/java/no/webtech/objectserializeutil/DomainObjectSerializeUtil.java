package no.webtech.objectserializeutil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

public class DomainObjectSerializeUtil {
	
	private static Logger logger = LoggerFactory.getLogger(DomainObjectSerializeUtil.class);

    public static String serializedObjectFileName(String uniqFilenameBase) {
        // TODO: Lag denne med ENV-variable for docker-config
        //return System.getProperty("supersnap.dir", "src/test/data/snap_") + uniqFilenameBase + ".ser";
        return "/tmp/snap_" + uniqFilenameBase + ".ser";
    }

	public static Serializable loadFromFile(String fileName) {
		byte[] ba = readWholeFileAsBytes(fileName);
		logger.debug("File: " + fileName + " Loaded buffer size: " + (ba!=null? ba.length:"<ba is null>"));
		if (ba == null) return null;
		return (Serializable) SerializableObjectHelper.createInstance(ba, 0).getObject();		
	}


	public static void dumpToFile(Serializable serializableObject, String uniqFilenameBase, String comment) {
		StringBuffer sb = SnapperUtil.createSerializedFormattedOutput(serializableObject);
		String fn = serializedObjectFileName(uniqFilenameBase);
		File f = new File(fn);
		if (f.exists()){
			logger.warn("Warning: File exists, will be replaced");
		}
		
		// TODO: Fix if more than last directory does not exist...
		// Create directory for f if not exists
		File dirn = f.getParentFile();
		if (!dirn.exists()) {
			logger.info("Make directory " + dirn.getAbsolutePath());
			boolean b = dirn.mkdir();
			if (!b) {
				logger.error("Could not create directory, maybe parent-directories does not exist, try create parent dirs. This will fail!");
				throw new IllegalArgumentException("Could not create directory, maybe parent-directories does not exist, try create parent dirs. This will fail!");
			}
		}
		
		OutputStream os;
		try {
			os = new FileOutputStream(f);
			if (comment != null) {
				os.write(comment.getBytes());
				os.write('\n');
			}
			os.write(sb.toString().getBytes());
			os.flush();
			os.close();
			logger.info("Created file " + f.getPath());
		} catch (IOException e) {
			logger.error("dumpToFile(Serializable rf, String id, String comment): "+stringifyException(e));
		}
	}
	
	private static byte[] readWholeFileAsBytes(String fileName) {
		File f = new File(fileName);
		byte[] ba = null;
		if (f.exists()) {
			int len = (int) f.length();
			ba = new byte[len];
			FileInputStream fis = null;
			try {
				fis = new FileInputStream(f);
				int i = 0;
				int rest = len - i;
				while (rest > 0 && (i = fis.read(ba, i, rest)) != -1) {
					rest -= i;
				}
			} catch (IOException e) {
				logger.error("loadFromFile(String fn): "+stringifyException(e));
			} finally {
				try {
					if (fis != null)
						fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return ba;
	}
	
	private static String stringifyException(Exception e) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream pr = new PrintStream(baos);
		e.printStackTrace(pr);
		pr.close();
		String ret = new String(baos.toByteArray());
		try {
			baos.close();
		} catch (IOException e1) { // Will never happen :-)
			e1.printStackTrace();
		}
		return ret;
	}

}