import java.nio.file.SimpleFileVisitor;

class Controller extends SimpleFileVisitor {

    private final MyServer server;


    /**
     * Constructor
     *
     * @param server Local server used to upload file to remote server
     * @param user User attempting to connect to remote server
     */
    Controller(MyServer server,User user){

        this.server = server;
    }

    /**
     * Logs user out of current connected remote server
     *
     * @return Returns true if logout was successful
     */
    public boolean logout(){
        return server.disconnect();
    }

    /**
     * Uploads specified file to current connected server
     *
     * @param localFilePath path of file to upload
     * @param fileName name of file to upload
     * @param destination
     * @return returns true if upload is successful
     */
    public boolean uploadFile(String localFilePath, String fileName, String destination){

        if(server.uploadFile(localFilePath,fileName,destination)){return true;}
        return false;
    }

    /**
     * Logs the user into the specified server with the given credentials
     *
     * @return returns true if the user was successfully logged in
     */
    public boolean login(){
        return server.login();
    }
}
