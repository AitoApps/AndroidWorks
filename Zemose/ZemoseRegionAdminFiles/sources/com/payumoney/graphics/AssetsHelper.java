package com.payumoney.graphics;

import android.support.annotation.Keep;
import com.payumoney.core.PayUmoneyConstants;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Keep
public class AssetsHelper {

    @Keep
    @Retention(RetentionPolicy.SOURCE)
    public @interface BRANDING {
        public static final String BRANDING_FOOTER = "trust-footer-v1";
        public static final String BRANDING_WALLET_FOOTER = "footer_wallet_branding";
        public static final String CITRUS_LOGO = "citrus-logo";
        public static final String WALLET_LOGO = "citrus-cash-logo";
    }

    @Keep
    @Retention(RetentionPolicy.SOURCE)
    public @interface CARD {
        public static final String AMEX = "AMEX";
        public static final String CIRRUS = "CIRRUS";
        public static final String DEFAULT = "DEFAULT";
        public static final String DINERCLUB = "DINER";
        public static final String DISCOVER = "DISCOVER";
        public static final String JCB = "JCB";
        public static final String MAESTRO = "MTRO";
        public static final String MCRD = "MCRD";
        public static final String RUPAY = "RPAY";
        public static final String UNKNOWN = "UNKNOWN";
        public static final String VISA = "VISA";
    }

    @Keep
    @Retention(RetentionPolicy.SOURCE)
    public @interface LARGEBANK {
        public static final String AXIS = "CID002";
        public static final String CITI = "CID003";
        public static final String DEFAULT = "CIDOOO";
        public static final String HDFC = "CID010";
        public static final String ICICI = "CID001";
        public static final String KOTAK = "CID033";
        public static final String SBI = "CID005";
    }

    @Keep
    @Retention(RetentionPolicy.SOURCE)
    public @interface LARGECARD {
        public static final String MCRD = "MCRD";
        public static final String VISA = "VISA";
    }

    @Keep
    @Retention(RetentionPolicy.SOURCE)
    public @interface MASTERPASS {
        public static final String BUTTON = "MPASSW";
        public static final String COLORBG = "Masterpass_color";
        public static final String GRAYBG = "Masterpass_bw";
    }

    @Keep
    @Retention(RetentionPolicy.SOURCE)
    public @interface SDK_TYPE {
        public static final String CORE_SDK = "CORE_SDK";
        public static final String FLASH_SDK = "FLASH_SDK";
        public static final String PLUG_N_PLAY = "PLUG_N_PLAY";
    }

    @Keep
    @Retention(RetentionPolicy.SOURCE)
    public @interface ScreenDensity {
        public static final String HDPI = "HDPI";
        public static final String XHDPI = "XHDPI";
        public static final String XXHDPI = "XXHDPI";
    }

    public static String getCard(String card) {
        char c;
        switch (card.hashCode()) {
            case -891831603:
                if (card.equals(PayUmoneyConstants.MASTER_CARD)) {
                    c = 1;
                    break;
                }
            case 73257:
                if (card.equals("JCB")) {
                    c = 8;
                    break;
                }
            case 2012639:
                if (card.equals("AMEX")) {
                    c = 0;
                    break;
                }
            case 2521846:
                if (card.equals("RPAY")) {
                    c = 3;
                    break;
                }
            case 2634817:
                if (card.equals("VISA")) {
                    c = 6;
                    break;
                }
            case 1055811561:
                if (card.equals(CARD.DISCOVER)) {
                    c = 2;
                    break;
                }
            case 1545480463:
                if (card.equals("MAESTRO")) {
                    c = 4;
                    break;
                }
            case 1988094532:
                if (card.equals(CARD.CIRRUS)) {
                    c = 7;
                    break;
                }
            case 2016591933:
                if (card.equals(PayUmoneyConstants.DINERS)) {
                    c = 5;
                    break;
                }
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
                return "AMEX";
            case 1:
                return "MCRD";
            case 2:
                return CARD.DISCOVER;
            case 3:
                return "RPAY";
            case 4:
                return CARD.MAESTRO;
            case 5:
                return CARD.DINERCLUB;
            case 6:
                return "VISA";
            case 7:
                return CARD.CIRRUS;
            case 8:
                return "JCB";
            default:
                return CARD.UNKNOWN;
        }
    }
}
