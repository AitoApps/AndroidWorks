package com.payumoney.core;

import android.text.TextUtils;
import android.util.Log;
import com.payumoney.core.utils.SdkHelper;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class PayUmoneySdkInitializer {
    private static Boolean a = Boolean.valueOf(false);
    private static String b = "https://www.payumoney.com";
    private static String c = "https://file.payumoney.com";
    private static String d = "https://secure.payu.in/_payment";
    private static String e = "https://www.payumoney.com/tnc.html";

    public static class PaymentParam {
        private HashMap<String, String> params;
        private String pipedHash;

        public static class Builder {
            /* access modifiers changed from: private */
            public String amount;
            /* access modifiers changed from: private */
            public String email;
            /* access modifiers changed from: private */
            public String fUrl;
            /* access modifiers changed from: private */
            public String firstName;
            /* access modifiers changed from: private */
            public boolean isDebug;
            /* access modifiers changed from: private */
            public String key;
            /* access modifiers changed from: private */
            public String merchantId;
            /* access modifiers changed from: private */
            public String phone;
            /* access modifiers changed from: private */
            public String productName;
            /* access modifiers changed from: private */
            public String sUrl;
            /* access modifiers changed from: private */
            public String txnId;
            /* access modifiers changed from: private */
            public String udf1 = "";
            /* access modifiers changed from: private */
            public String udf10 = "";
            /* access modifiers changed from: private */
            public String udf2 = "";
            /* access modifiers changed from: private */
            public String udf3 = "";
            /* access modifiers changed from: private */
            public String udf4 = "";
            /* access modifiers changed from: private */
            public String udf5 = "";
            /* access modifiers changed from: private */
            public String udf6 = "";
            /* access modifiers changed from: private */
            public String udf7 = "";
            /* access modifiers changed from: private */
            public String udf8 = "";
            /* access modifiers changed from: private */
            public String udf9 = "";

            public Builder setIsDebug(boolean isDebug2) {
                this.isDebug = isDebug2;
                return this;
            }

            public Builder setAmount(String amount2) {
                this.amount = amount2;
                return this;
            }

            public Builder setKey(String key2) {
                this.key = key2;
                return this;
            }

            public Builder setMerchantId(String merchantId2) {
                this.merchantId = merchantId2;
                return this;
            }

            public Builder setTxnId(String txnId2) {
                this.txnId = txnId2;
                return this;
            }

            public Builder setsUrl(String sUrl2) {
                this.sUrl = sUrl2;
                return this;
            }

            public Builder setfUrl(String fUrl2) {
                this.fUrl = fUrl2;
                return this;
            }

            public Builder setProductName(String productName2) {
                this.productName = productName2;
                return this;
            }

            public Builder setFirstName(String firstName2) {
                this.firstName = firstName2;
                return this;
            }

            public Builder setEmail(String email2) {
                this.email = email2;
                return this;
            }

            public Builder setPhone(String phone2) {
                this.phone = phone2;
                return this;
            }

            public Builder setUdf1(String udf12) {
                this.udf1 = udf12;
                return this;
            }

            public Builder setUdf2(String udf22) {
                this.udf2 = udf22;
                return this;
            }

            public Builder setUdf3(String udf32) {
                this.udf3 = udf32;
                return this;
            }

            public Builder setUdf4(String udf42) {
                this.udf4 = udf42;
                return this;
            }

            public Builder setUdf5(String udf52) {
                this.udf5 = udf52;
                return this;
            }

            public Builder setUdf6(String udf62) {
                this.udf6 = udf62;
                return this;
            }

            public Builder setUdf7(String udf72) {
                this.udf7 = udf72;
                return this;
            }

            public Builder setUdf8(String udf82) {
                this.udf8 = udf82;
                return this;
            }

            public Builder setUdf9(String udf92) {
                this.udf9 = udf92;
                return this;
            }

            public Builder setUdf10(String udf102) {
                this.udf10 = udf102;
                return this;
            }

            public PaymentParam build() throws Exception {
                return new PaymentParam(this);
            }
        }

        private PaymentParam(Builder builder) {
            this.params = new LinkedHashMap();
            this.pipedHash = "";
            PayUmoneySdkInitializer.setDebugMode(builder.isDebug);
            if (!TextUtils.isEmpty(builder.key)) {
                this.params.put("key", builder.key.trim());
                if (!TextUtils.isEmpty(builder.merchantId)) {
                    this.params.put("merchantId", builder.merchantId.trim());
                    if (!TextUtils.isEmpty(builder.txnId)) {
                        this.params.put("txnid", builder.txnId.trim());
                        if (!TextUtils.isEmpty(builder.amount)) {
                            try {
                                double parseDouble = Double.parseDouble(builder.amount);
                                if (parseDouble <= 0.0d || parseDouble > 1000000.0d) {
                                    throw new RuntimeException("Amount should be greater than 0 and  less than 1000000.00  ");
                                } else if (SdkHelper.isValidAmount(Double.valueOf(parseDouble))) {
                                    this.params.put(PayUmoneyConstants.AMOUNT, builder.amount);
                                    if (!TextUtils.isEmpty(builder.sUrl)) {
                                        this.params.put("surl", builder.sUrl.trim());
                                        if (!TextUtils.isEmpty(builder.fUrl)) {
                                            this.params.put("furl", builder.fUrl.trim());
                                            if (!TextUtils.isEmpty(builder.productName)) {
                                                this.params.put(PayUmoneyConstants.PRODUCT_INFO, builder.productName.trim());
                                                if (TextUtils.isEmpty(builder.email)) {
                                                    throw new RuntimeException("Email is missing");
                                                } else if (SdkHelper.isValidEmail(builder.email.trim())) {
                                                    this.params.put("email", builder.email.trim());
                                                    if (!TextUtils.isEmpty(builder.firstName)) {
                                                        this.params.put(PayUmoneyConstants.FIRSTNAME, builder.firstName.trim());
                                                        if (TextUtils.isEmpty(builder.phone)) {
                                                            throw new RuntimeException("Phone number is missing");
                                                        } else if (SdkHelper.isValidNumber(builder.phone.trim())) {
                                                            this.params.put("phone", builder.phone.trim().substring(builder.phone.trim().length() - 10));
                                                            if (builder.udf1 != null) {
                                                                this.params.put(PayUmoneyConstants.UDF1, builder.udf1.trim());
                                                                if (builder.udf2 != null) {
                                                                    this.params.put(PayUmoneyConstants.UDF2, builder.udf2.trim());
                                                                    if (builder.udf3 != null) {
                                                                        this.params.put(PayUmoneyConstants.UDF3, builder.udf3.trim());
                                                                        if (builder.udf4 != null) {
                                                                            this.params.put(PayUmoneyConstants.UDF4, builder.udf4.trim());
                                                                            if (builder.udf5 != null) {
                                                                                this.params.put(PayUmoneyConstants.UDF5, builder.udf5.trim());
                                                                                if (builder.udf6 != null) {
                                                                                    this.params.put(PayUmoneyConstants.UDF6, builder.udf6.trim());
                                                                                    if (builder.udf7 != null) {
                                                                                        this.params.put(PayUmoneyConstants.UDF7, builder.udf7.trim());
                                                                                        if (builder.udf8 != null) {
                                                                                            this.params.put(PayUmoneyConstants.UDF8, builder.udf8.trim());
                                                                                            if (builder.udf9 != null) {
                                                                                                this.params.put(PayUmoneyConstants.UDF9, builder.udf9.trim());
                                                                                                if (builder.udf10 != null) {
                                                                                                    this.params.put(PayUmoneyConstants.UDF10, builder.udf10.trim());
                                                                                                    if (PayUmoneySdkInitializer.IsDebugMode().booleanValue()) {
                                                                                                        Log.d("hashSeq", this.pipedHash);
                                                                                                    }
                                                                                                    String hashCal = hashCal(this.pipedHash);
                                                                                                    if (PayUmoneySdkInitializer.IsDebugMode().booleanValue()) {
                                                                                                        Log.d("hash", hashCal);
                                                                                                    }
                                                                                                    if (PayUmoneySdkInitializer.IsDebugMode().booleanValue()) {
                                                                                                        for (String str : this.params.keySet()) {
                                                                                                            String str2 = (String) this.params.get(str);
                                                                                                            StringBuilder sb = new StringBuilder();
                                                                                                            sb.append(str);
                                                                                                            sb.append(" - ");
                                                                                                            sb.append(str2);
                                                                                                            Log.d("param : ", sb.toString());
                                                                                                        }
                                                                                                        return;
                                                                                                    }
                                                                                                    return;
                                                                                                }
                                                                                                throw new RuntimeException("udf10 is null, put some value or empty e.g. Builder.setUdf5(\"\")");
                                                                                            }
                                                                                            throw new RuntimeException("udf9 is null, put some value or empty e.g. Builder.setUdf5(\"\")");
                                                                                        }
                                                                                        throw new RuntimeException("udf8 is null, put some value or empty e.g. Builder.setUdf5(\"\")");
                                                                                    }
                                                                                    throw new RuntimeException("udf7 is null, put some value or empty e.g. Builder.setUdf5(\"\")");
                                                                                }
                                                                                throw new RuntimeException("udf6 is null, put some value or empty e.g. Builder.setUdf5(\"\")");
                                                                            }
                                                                            throw new RuntimeException("udf5 is null, put some value or empty e.g. Builder.setUdf5(\"\")");
                                                                        }
                                                                        throw new RuntimeException("udf4 is null, put some value or empty e.g. Builder.setUdf4(\"\")");
                                                                    }
                                                                    throw new RuntimeException("udf3 is null, put some value or empty e.g. Builder.setUdf3(\"\")");
                                                                }
                                                                throw new RuntimeException("udf2 is null, put some value or empty e.g. Builder.setUdf2(\"\")");
                                                            }
                                                            throw new RuntimeException("udf1 is null, put some value or empty e.g. Builder.setUdf1(\"\")");
                                                        } else {
                                                            throw new RuntimeException("Phone number is invalid");
                                                        }
                                                    } else {
                                                        throw new RuntimeException("First name is missing");
                                                    }
                                                } else {
                                                    throw new RuntimeException("Email is invalid");
                                                }
                                            } else {
                                                throw new RuntimeException("Product info is missing");
                                            }
                                        } else {
                                            throw new RuntimeException("Furl is missing");
                                        }
                                    } else {
                                        throw new RuntimeException("Surl is missing");
                                    }
                                } else {
                                    throw new RuntimeException("Amount should be positive and upto 2 decimal places");
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                                throw new RuntimeException("Invalid Amount format");
                            }
                        } else {
                            throw new RuntimeException("Amount is missing");
                        }
                    } else {
                        throw new RuntimeException("TxnId Id missing");
                    }
                } else {
                    throw new RuntimeException(" Merchant id missing");
                }
            } else {
                throw new RuntimeException("Merchant Key missing");
            }
        }

        private static String hashCal(String str) {
            byte[] bytes = str.getBytes();
            StringBuilder sb = new StringBuilder();
            try {
                MessageDigest instance = MessageDigest.getInstance("SHA-512");
                instance.reset();
                instance.update(bytes);
                for (byte b : instance.digest()) {
                    String hexString = Integer.toHexString(b & 255);
                    if (hexString.length() == 1) {
                        sb.append("0");
                    }
                    sb.append(hexString);
                }
            } catch (NoSuchAlgorithmException e) {
            }
            return sb.toString();
        }

        public HashMap<String, String> getParams() {
            return this.params;
        }

        public void setMerchantHash(String serverCalculatedHash) {
            this.params.put("hash", serverCalculatedHash);
        }

        public String toString() {
            return this.pipedHash;
        }
    }

    public static String getBaseUrl() {
        return b;
    }

    public static void setBaseUrl(String url) {
        b = url;
    }

    public static String getBaseUrlImage() {
        return c;
    }

    public static void setBaseUrlImage(String url) {
        c = url;
    }

    public static String getWebviewRedirectionUrl() {
        return d;
    }

    public static String getTermsAndConditionsUrl() {
        return e;
    }

    public static void setWebviewRedirectionUrl(String url) {
        d = url;
    }

    public static Boolean IsDebugMode() {
        return a;
    }

    public static void setDebugMode(boolean isDebugMode) {
        a = Boolean.valueOf(isDebugMode);
        if (isDebugMode) {
            a();
        } else {
            b();
        }
    }

    private static void a() {
        setBaseUrl("https://www.payumoney.com/sandbox");
        setBaseUrlImage("https://www.payumoney.com/sandbox");
        setWebviewRedirectionUrl("https://sandboxsecure.payu.in/_payment");
    }

    private static void b() {
        setBaseUrl("https://www.payumoney.com");
        setBaseUrlImage("https://file.payumoney.com");
        setWebviewRedirectionUrl("https://secure.payu.in/_payment");
    }
}
