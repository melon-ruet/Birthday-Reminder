import java.io.*;
import java.nio.channels.*;

public class JustOneLock {
    private File file;
    private FileChannel channel;
    private FileLock lock;


    public boolean isAppActive() {
        try {
            file = new File(System.getProperty("user.home"),
                    "Birthday Reminder" + ".tmp");
            channel = new RandomAccessFile(file, "rw").getChannel();

            try {
                lock = channel.tryLock();
            }
            catch (OverlappingFileLockException e) {
                // already locked
                closeLock();
                return true;
            }

            if (lock == null) {
                closeLock();
                return true;
            }

            Runtime.getRuntime().addShutdownHook(new Thread() {
                // destroy the lock when the JVM is closing
                @Override
                public void run() {
                    closeLock();
                    deleteFile();
                }
            });
            return false;
        }
        catch (Exception e) {
            closeLock();
            return true;
        }
    }

    private void closeLock() {
        try { lock.release();  }
        catch (Exception e) {  }
        try { channel.close(); }
        catch (Exception e) {  }
    }

    private void deleteFile() {
        try { file.delete(); }
        catch (Exception e) { }
    }
}