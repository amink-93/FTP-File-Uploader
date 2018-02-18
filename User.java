import java.io.File;

class User {

    private String username;
    private char[] password;
    private String serverAddress;
    private int port = 21;
    private File file;

    User(String username, String password, String serverAddress){
        this.username = username;
        this.password = password.toCharArray();
        this.serverAddress = serverAddress;
    }


    public String getUsername() {
        return username;
    }

    public char[] getPassword() {
        return password;
    }

    public String getServerAddress() {
        return serverAddress;
    }

    public int getPort() {
        return port;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }




}
