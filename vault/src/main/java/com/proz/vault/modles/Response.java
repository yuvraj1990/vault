package com.proz.vault.modles;

/**
 * @author yubraj.singh
 */
public class Response {
  private String message;
  private Object data;
    public Response(String message,
                    Object data) {
        this.message = message;
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
