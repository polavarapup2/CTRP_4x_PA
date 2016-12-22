package com.ctepdataload;

public class CtrpResponse {
    
    private boolean isSucess = false;
    private String failureMessage = null;
    private String siteIdAdded ;
    public boolean isSucess() {
        return isSucess;
    }
    public void setSucess(boolean isSucess) {
        this.isSucess = isSucess;
    }
    public String getFailureMessage() {
        return failureMessage;
    }
    public void setFailureMessage(String failureMessage) {
        this.failureMessage = failureMessage;
    }
    public String getSiteIdAdded() {
        return siteIdAdded;
    }
    public void setSiteIdAdded(String siteIdAdded) {
        this.siteIdAdded = siteIdAdded;
    }

}
