package com.ctepdataload;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class ProperyValueReader {
    
private String serverUrl;
private String userName;
private String password;
private boolean isAddException;
private String inputFileName;

public void initProperties() throws Exception{
    Properties properties = new Properties();
    InputStream input = null;
    
    try {
        input = new FileInputStream("ctepDataLoad.properties");
        properties.load(input);
        serverUrl = properties.getProperty("ctrp.serverUrl");
        userName = properties.getProperty("ctep.userName");
        password = properties.getProperty("ctep.password");
        inputFileName = properties.getProperty("ctep.inputfileName");
        String isAddExceptionValue = properties.getProperty("ctep.addexception");
        if(isAddExceptionValue!=null &&  isAddExceptionValue.equals("true")) {
            isAddException = true;
        }
    } 
    finally {
        input.close();
    }

}

public String getServerUrl() {
    return serverUrl;
}
public void setServerUrl(String serverUrl) {
    this.serverUrl = serverUrl;
}
public String getUserName() {
    return userName;
}
public void setUserName(String userName) {
    this.userName = userName;
}
public String getPassword() {
    return password;
}
public void setPassword(String password) {
    this.password = password;
}
public boolean isAddException() {
    return isAddException;
}
public void setAddException(boolean isAddException) {
    this.isAddException = isAddException;
}
public String getInputFileName() {
    return inputFileName;
}
public void setInputFileName(String inputFileName) {
    this.inputFileName = inputFileName;
}



}
