package com.payumoney.core.response;

public class ErrorResponse extends PayUMoneyAPIResponse {
    private String a;
    private String b;
    private String c;
    private String d;

    public String getGuid() {
        return this.d;
    }

    public void setGuid(String guid) {
        this.d = guid;
    }

    public String getStatus() {
        return this.c;
    }

    public void setStatus(String status) {
        this.c = status;
    }

    public String getMessage() {
        return this.a;
    }

    public void setMessage(String message) {
        this.a = message;
    }

    public String getErrorCode() {
        return this.b;
    }

    public void setErrorCode(String errorCode) {
        this.b = errorCode;
    }

    public String toString() {
        String str = "";
        String str2 = this.c;
        if (str2 != null && str2.equals("")) {
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append("Status: ");
            sb.append(this.c);
            str = sb.toString();
        }
        String str3 = this.b;
        if (str3 != null && !str3.equals("")) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append(str);
            sb2.append("ERROR CODE: ");
            sb2.append(this.b);
            sb2.append(" ");
            str = sb2.toString();
        }
        String str4 = this.a;
        if (str4 != null && !str4.equals("")) {
            StringBuilder sb3 = new StringBuilder();
            sb3.append(str);
            sb3.append("Description: ");
            sb3.append(this.a);
            sb3.append(" ");
            str = sb3.toString();
        }
        String str5 = this.d;
        if (str5 != null && !str5.equals("")) {
            StringBuilder sb4 = new StringBuilder();
            sb4.append(str);
            sb4.append("guid: ");
            sb4.append(this.d);
            sb4.toString();
        }
        return super.toString();
    }
}
