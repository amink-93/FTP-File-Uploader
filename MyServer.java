import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import java.io.*;

import static org.junit.Assert.assertTrue;

class MyServer {


    private FTPClient ftp = null;
    private int reply;
    private final User user;


    /**
     * Constructor
     *
     * @param user current user attempting to connect to a specified server
     */
    MyServer(User user){
        this.user = user;
    }


    /**
     * Attempts to connect to a specific server specified by the user
     *
     * @return Returns true if login successful
     */
    public boolean login(){

        boolean loginSuccessful = false;

        try {
            ftp = new FTPClient();
            ftp.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out)));
            System.out.println("Attempting to connect");
            ftp.connect(user.getServerAddress(),user.getPort());
            System.out.println("FTP URL: " + ftp.getDefaultPort());
            reply = ftp.getReplyCode();

            if(!FTPReply.isPositiveCompletion(reply)){
                ftp.disconnect();
            }

            ftp.login(user.getUsername(),user.getPassword().toString());
            ftp.setFileType(FTP.BINARY_FILE_TYPE);
            ftp.enterLocalPassiveMode();
            System.out.println("Connected");
            loginSuccessful = true;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return loginSuccessful;

    }


    /**
     * Takes a file with a destination and attempts to upload the file
     *
     * @param localFilePath Path of file to upload
     * @param fileName name of file to upload
     * @param destinationDir destination to upload the file on the server
     * @return Returns true if upload successful, otherwise returns false
     */
    public boolean uploadFile(String localFilePath, String fileName, String destinationDir){

        boolean uploadSuccessful = false;

        try{
            InputStream in = new FileInputStream(new File(localFilePath));
            uploadSuccessful = ftp.storeFile(destinationDir + fileName, in);
            in.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return uploadSuccessful;
    }


    /**
     *  Logs current user out and disconnects from the server
     *
     * @return Returns true if disconnect was successful
     */
    public boolean disconnect(){

        boolean disconnectSuccessful = false;

        if(ftp.isConnected()){
            try {
                ftp.logout();
                ftp.disconnect();
                disconnectSuccessful = true;

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return disconnectSuccessful;
    }


}
