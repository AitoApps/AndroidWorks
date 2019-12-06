package com.payumoney.core.utils;

import com.payumoney.graphics.AssetsHelper.LARGEBANK;

public enum WalletsCID {
    AMON {
        public String getCID() {
            return "BCW003";
        }

        public String getName() {
            return "Airtel Money";
        }

        public String getShortName() {
            return "Airtel Money";
        }

        public String getBankCode() {
            return "AMON";
        }
    },
    YPAY {
        public String getCID() {
            return LARGEBANK.AXIS;
        }

        public String getName() {
            return "YPay Cash";
        }

        public String getShortName() {
            return "YPay Cash";
        }

        public String getBankCode() {
            return "YPAY";
        }
    },
    DONE {
        public String getCID() {
            return LARGEBANK.AXIS;
        }

        public String getName() {
            return "DONE Cash Card";
        }

        public String getShortName() {
            return "DONE Cash Card";
        }

        public String getBankCode() {
            return "DONE";
        }
    },
    ITZC {
        public String getCID() {
            return LARGEBANK.AXIS;
        }

        public String getName() {
            return "ItzCash";
        }

        public String getShortName() {
            return "ItzCash";
        }

        public String getBankCode() {
            return "ITZC";
        }
    },
    ICASH {
        public String getCID() {
            return LARGEBANK.AXIS;
        }

        public String getName() {
            return "ICash Card";
        }

        public String getShortName() {
            return "ICash Card";
        }

        public String getBankCode() {
            return "ICASH";
        }
    },
    PAYCASH {
        public String getCID() {
            return LARGEBANK.AXIS;
        }

        public String getName() {
            return "PAYCASH CARD";
        }

        public String getShortName() {
            return "PAYCASH CARD";
        }

        public String getBankCode() {
            return "PAYCASH";
        }
    },
    ZIPCASH {
        public String getCID() {
            return "BCW002";
        }

        public String getName() {
            return "ZIPcash card";
        }

        public String getShortName() {
            return "ZIPcash card";
        }

        public String getBankCode() {
            return "ZIPCASH";
        }
    },
    OXICASH {
        public String getCID() {
            return "BCW001";
        }

        public String getName() {
            return "Oxigen";
        }

        public String getShortName() {
            return "Oxigen";
        }

        public String getBankCode() {
            return "OXICASH";
        }
    },
    PAYZ {
        public String getCID() {
            return LARGEBANK.AXIS;
        }

        public String getName() {
            return "HDFC PayZapp";
        }

        public String getShortName() {
            return "HDFC PayZapp";
        }

        public String getBankCode() {
            return "PAYZ";
        }
    },
    AMEXZ {
        public String getCID() {
            return LARGEBANK.AXIS;
        }

        public String getName() {
            return "Amex easy click";
        }

        public String getShortName() {
            return "Amex easy click";
        }

        public String getBankCode() {
            return "AMEXZ";
        }
    },
    YESW {
        public String getCID() {
            return LARGEBANK.AXIS;
        }

        public String getName() {
            return "Yes bank";
        }

        public String getShortName() {
            return "Yes bank";
        }

        public String getBankCode() {
            return "YESW";
        }
    },
    CPMC {
        public String getCID() {
            return LARGEBANK.AXIS;
        }

        public String getName() {
            return "Citibank Reward Points";
        }

        public String getShortName() {
            return "Citibank Reward Points";
        }

        public String getBankCode() {
            return "CPMC";
        }
    },
    FREC {
        public String getCID() {
            return LARGEBANK.AXIS;
        }

        public String getName() {
            return "FreeCharge";
        }

        public String getShortName() {
            return "FreeCharge";
        }

        public String getBankCode() {
            return "FREC";
        }
    },
    OLAM {
        public String getCID() {
            return LARGEBANK.AXIS;
        }

        public String getName() {
            return "OLA Money";
        }

        public String getShortName() {
            return "OLA Money";
        }

        public String getBankCode() {
            return "OLAM";
        }
    },
    MOMWALLE {
        public String getCID() {
            return LARGEBANK.AXIS;
        }

        public String getName() {
            return "MOM Wallet";
        }

        public String getShortName() {
            return "MOM Wallet";
        }

        public String getBankCode() {
            return "MOMWALLE";
        }
    },
    MOBIKWIK {
        public String getCID() {
            return "BCW004";
        }

        public String getName() {
            return "Mobikwik";
        }

        public String getShortName() {
            return "Mobikwik";
        }

        public String getBankCode() {
            return "";
        }
    };

    public abstract String getBankCode();

    public abstract String getCID();

    public abstract String getName();

    public abstract String getShortName();

    public static WalletsCID getWalletCIDByBankCode(String bankCode) {
        WalletsCID[] values;
        for (WalletsCID walletsCID : values()) {
            if (walletsCID.getBankCode().equalsIgnoreCase(bankCode)) {
                return walletsCID;
            }
        }
        return null;
    }

    public static boolean isWalletCIDAvailable(String bankCode) {
        for (WalletsCID bankCode2 : values()) {
            if (bankCode2.getBankCode().equalsIgnoreCase(bankCode)) {
                return true;
            }
        }
        return false;
    }
}
