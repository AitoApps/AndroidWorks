package com.google.gson.internal.bind.util;

import java.text.ParseException;
import java.text.ParsePosition;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

public class ISO8601Utils {
    private static final TimeZone TIMEZONE_UTC = TimeZone.getTimeZone(UTC_ID);
    private static final String UTC_ID = "UTC";

    public static String format(Date date) {
        return format(date, false, TIMEZONE_UTC);
    }

    public static String format(Date date, boolean millis) {
        return format(date, millis, TIMEZONE_UTC);
    }

    public static String format(Date date, boolean millis, TimeZone tz) {
        Calendar calendar = new GregorianCalendar(tz, Locale.US);
        calendar.setTime(date);
        StringBuilder formatted = new StringBuilder("yyyy-MM-ddThh:mm:ss".length() + (millis ? ".sss".length() : 0) + (tz.getRawOffset() == 0 ? "Z" : "+hh:mm").length());
        padInt(formatted, calendar.get(1), "yyyy".length());
        char c = '-';
        formatted.append('-');
        padInt(formatted, calendar.get(2) + 1, "MM".length());
        formatted.append('-');
        padInt(formatted, calendar.get(5), "dd".length());
        formatted.append('T');
        padInt(formatted, calendar.get(11), "hh".length());
        formatted.append(':');
        padInt(formatted, calendar.get(12), "mm".length());
        formatted.append(':');
        padInt(formatted, calendar.get(13), "ss".length());
        if (millis) {
            formatted.append('.');
            padInt(formatted, calendar.get(14), "sss".length());
        }
        int offset = tz.getOffset(calendar.getTimeInMillis());
        if (offset != 0) {
            int hours = Math.abs((offset / 60000) / 60);
            int minutes = Math.abs((offset / 60000) % 60);
            if (offset >= 0) {
                c = '+';
            }
            formatted.append(c);
            padInt(formatted, hours, "hh".length());
            formatted.append(':');
            padInt(formatted, minutes, "mm".length());
        } else {
            formatted.append('Z');
        }
        return formatted.toString();
    }

    /* JADX WARNING: Removed duplicated region for block: B:111:0x0227  */
    /* JADX WARNING: Removed duplicated region for block: B:112:0x0229  */
    public static Date parse(String date, ParsePosition pos) throws ParseException {
        IllegalArgumentException fail;
        String input;
        String msg;
        int offset;
        int offset2;
        TimeZone timezone;
        String str;
        int offset3;
        String str2 = date;
        ParsePosition parsePosition = pos;
        try {
            int offset4 = pos.getIndex();
            int offset5 = offset4 + 4;
            int offset6 = parseInt(str2, offset4, offset5);
            if (checkOffset(str2, offset5, '-')) {
                offset5++;
            }
            int offset7 = offset5 + 2;
            int offset8 = parseInt(str2, offset5, offset7);
            if (checkOffset(str2, offset7, '-')) {
                offset7++;
            }
            int offset9 = offset7 + 2;
            int offset10 = parseInt(str2, offset7, offset9);
            int hour = 0;
            int minutes = 0;
            int seconds = 0;
            int milliseconds = 0;
            boolean hasT = checkOffset(str2, offset9, 'T');
            if (!hasT) {
                try {
                    if (date.length() <= offset9) {
                        Calendar calendar = new GregorianCalendar(offset6, offset8 - 1, offset10);
                        parsePosition.setIndex(offset9);
                        return calendar.getTime();
                    }
                } catch (IndexOutOfBoundsException e) {
                    e = e;
                    fail = e;
                    if (str2 == null) {
                        input = null;
                    } else {
                        StringBuilder sb = new StringBuilder();
                        sb.append('\"');
                        sb.append(str2);
                        sb.append("'");
                        input = sb.toString();
                    }
                    msg = fail.getMessage();
                    if (msg == null || msg.isEmpty()) {
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append("(");
                        sb2.append(fail.getClass().getName());
                        sb2.append(")");
                        msg = sb2.toString();
                    }
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append("Failed to parse date [");
                    sb3.append(input);
                    sb3.append("]: ");
                    sb3.append(msg);
                    ParseException ex = new ParseException(sb3.toString(), pos.getIndex());
                    ex.initCause(fail);
                    throw ex;
                } catch (NumberFormatException e2) {
                    e = e2;
                    fail = e;
                    if (str2 == null) {
                    }
                    msg = fail.getMessage();
                    StringBuilder sb22 = new StringBuilder();
                    sb22.append("(");
                    sb22.append(fail.getClass().getName());
                    sb22.append(")");
                    msg = sb22.toString();
                    StringBuilder sb32 = new StringBuilder();
                    sb32.append("Failed to parse date [");
                    sb32.append(input);
                    sb32.append("]: ");
                    sb32.append(msg);
                    ParseException ex2 = new ParseException(sb32.toString(), pos.getIndex());
                    ex2.initCause(fail);
                    throw ex2;
                } catch (IllegalArgumentException e3) {
                    e = e3;
                    fail = e;
                    if (str2 == null) {
                    }
                    msg = fail.getMessage();
                    StringBuilder sb222 = new StringBuilder();
                    sb222.append("(");
                    sb222.append(fail.getClass().getName());
                    sb222.append(")");
                    msg = sb222.toString();
                    StringBuilder sb322 = new StringBuilder();
                    sb322.append("Failed to parse date [");
                    sb322.append(input);
                    sb322.append("]: ");
                    sb322.append(msg);
                    ParseException ex22 = new ParseException(sb322.toString(), pos.getIndex());
                    ex22.initCause(fail);
                    throw ex22;
                }
            }
            if (hasT) {
                int offset11 = offset9 + 1;
                int offset12 = offset11 + 2;
                hour = parseInt(str2, offset11, offset12);
                if (checkOffset(str2, offset12, ':')) {
                    offset12++;
                }
                int offset13 = offset12 + 2;
                minutes = parseInt(str2, offset12, offset13);
                if (checkOffset(str2, offset13, ':')) {
                    offset3 = offset13 + 1;
                } else {
                    offset3 = offset13;
                }
                if (date.length() > offset3) {
                    char c = str2.charAt(offset3);
                    if (!(c == 'Z' || c == '+' || c == '-')) {
                        offset = offset3 + 2;
                        int offset14 = parseInt(str2, offset3, offset);
                        if (offset14 <= 59 || offset14 >= 63) {
                            seconds = offset14;
                        } else {
                            seconds = 59;
                        }
                        if (checkOffset(str2, offset, '.')) {
                            int offset15 = offset + 1;
                            int endOffset = indexOfNonDigit(str2, offset15 + 1);
                            int parseEndOffset = Math.min(endOffset, offset15 + 3);
                            int fraction = parseInt(str2, offset15, parseEndOffset);
                            switch (parseEndOffset - offset15) {
                                case 1:
                                    milliseconds = fraction * 100;
                                    break;
                                case 2:
                                    milliseconds = fraction * 10;
                                    break;
                                default:
                                    milliseconds = fraction;
                                    break;
                            }
                            offset = endOffset;
                        }
                    }
                }
                offset = offset3;
            } else {
                offset = offset9;
            }
            if (date.length() > offset) {
                char timezoneIndicator = str2.charAt(offset);
                if (timezoneIndicator == 'Z') {
                    timezone = TIMEZONE_UTC;
                    offset2 = offset + 1;
                    char c2 = timezoneIndicator;
                    boolean z = hasT;
                } else {
                    if (timezoneIndicator != '+') {
                        if (timezoneIndicator != '-') {
                            StringBuilder sb4 = new StringBuilder();
                            try {
                                sb4.append("Invalid time zone indicator '");
                                sb4.append(timezoneIndicator);
                                sb4.append("'");
                                throw new IndexOutOfBoundsException(sb4.toString());
                            } catch (IndexOutOfBoundsException e4) {
                                e = e4;
                                fail = e;
                                if (str2 == null) {
                                }
                                msg = fail.getMessage();
                                StringBuilder sb2222 = new StringBuilder();
                                sb2222.append("(");
                                sb2222.append(fail.getClass().getName());
                                sb2222.append(")");
                                msg = sb2222.toString();
                                StringBuilder sb3222 = new StringBuilder();
                                sb3222.append("Failed to parse date [");
                                sb3222.append(input);
                                sb3222.append("]: ");
                                sb3222.append(msg);
                                ParseException ex222 = new ParseException(sb3222.toString(), pos.getIndex());
                                ex222.initCause(fail);
                                throw ex222;
                            } catch (NumberFormatException e5) {
                                e = e5;
                                fail = e;
                                if (str2 == null) {
                                }
                                msg = fail.getMessage();
                                StringBuilder sb22222 = new StringBuilder();
                                sb22222.append("(");
                                sb22222.append(fail.getClass().getName());
                                sb22222.append(")");
                                msg = sb22222.toString();
                                StringBuilder sb32222 = new StringBuilder();
                                sb32222.append("Failed to parse date [");
                                sb32222.append(input);
                                sb32222.append("]: ");
                                sb32222.append(msg);
                                ParseException ex2222 = new ParseException(sb32222.toString(), pos.getIndex());
                                ex2222.initCause(fail);
                                throw ex2222;
                            } catch (IllegalArgumentException e6) {
                                e = e6;
                                fail = e;
                                if (str2 == null) {
                                }
                                msg = fail.getMessage();
                                StringBuilder sb222222 = new StringBuilder();
                                sb222222.append("(");
                                sb222222.append(fail.getClass().getName());
                                sb222222.append(")");
                                msg = sb222222.toString();
                                StringBuilder sb322222 = new StringBuilder();
                                sb322222.append("Failed to parse date [");
                                sb322222.append(input);
                                sb322222.append("]: ");
                                sb322222.append(msg);
                                ParseException ex22222 = new ParseException(sb322222.toString(), pos.getIndex());
                                ex22222.initCause(fail);
                                throw ex22222;
                            }
                        }
                    }
                    String timezoneOffset = str2.substring(offset);
                    if (timezoneOffset.length() >= 5) {
                        str = timezoneOffset;
                    } else {
                        StringBuilder sb5 = new StringBuilder();
                        sb5.append(timezoneOffset);
                        sb5.append("00");
                        str = sb5.toString();
                    }
                    String timezoneOffset2 = str;
                    offset2 = offset + timezoneOffset2.length();
                    if ("+0000".equals(timezoneOffset2)) {
                        char c3 = timezoneIndicator;
                        boolean z2 = hasT;
                    } else if ("+00:00".equals(timezoneOffset2)) {
                        String str3 = timezoneOffset2;
                        char c4 = timezoneIndicator;
                        boolean z3 = hasT;
                    } else {
                        StringBuilder sb6 = new StringBuilder();
                        sb6.append("GMT");
                        sb6.append(timezoneOffset2);
                        String timezoneId = sb6.toString();
                        timezone = TimeZone.getTimeZone(timezoneId);
                        String act = timezone.getID();
                        if (!act.equals(timezoneId)) {
                            String str4 = timezoneOffset2;
                            char c5 = timezoneIndicator;
                            String cleaned = act.replace(":", "");
                            if (cleaned.equals(timezoneId)) {
                                boolean z4 = hasT;
                            } else {
                                String str5 = cleaned;
                                StringBuilder sb7 = new StringBuilder();
                                boolean z5 = hasT;
                                sb7.append("Mismatching time zone indicator: ");
                                sb7.append(timezoneId);
                                sb7.append(" given, resolves to ");
                                sb7.append(timezone.getID());
                                throw new IndexOutOfBoundsException(sb7.toString());
                            }
                        } else {
                            char c6 = timezoneIndicator;
                            boolean z6 = hasT;
                        }
                    }
                    timezone = TIMEZONE_UTC;
                }
                Calendar calendar2 = new GregorianCalendar(timezone);
                calendar2.setLenient(false);
                calendar2.set(1, offset6);
                calendar2.set(2, offset8 - 1);
                calendar2.set(5, offset10);
                calendar2.set(11, hour);
                calendar2.set(12, minutes);
                calendar2.set(13, seconds);
                calendar2.set(14, milliseconds);
                parsePosition.setIndex(offset2);
                return calendar2.getTime();
            }
            boolean z7 = hasT;
            throw new IllegalArgumentException("No time zone indicator");
        } catch (IndexOutOfBoundsException e7) {
            e = e7;
            fail = e;
            if (str2 == null) {
            }
            msg = fail.getMessage();
            StringBuilder sb2222222 = new StringBuilder();
            sb2222222.append("(");
            sb2222222.append(fail.getClass().getName());
            sb2222222.append(")");
            msg = sb2222222.toString();
            StringBuilder sb3222222 = new StringBuilder();
            sb3222222.append("Failed to parse date [");
            sb3222222.append(input);
            sb3222222.append("]: ");
            sb3222222.append(msg);
            ParseException ex222222 = new ParseException(sb3222222.toString(), pos.getIndex());
            ex222222.initCause(fail);
            throw ex222222;
        } catch (NumberFormatException e8) {
            e = e8;
            fail = e;
            if (str2 == null) {
            }
            msg = fail.getMessage();
            StringBuilder sb22222222 = new StringBuilder();
            sb22222222.append("(");
            sb22222222.append(fail.getClass().getName());
            sb22222222.append(")");
            msg = sb22222222.toString();
            StringBuilder sb32222222 = new StringBuilder();
            sb32222222.append("Failed to parse date [");
            sb32222222.append(input);
            sb32222222.append("]: ");
            sb32222222.append(msg);
            ParseException ex2222222 = new ParseException(sb32222222.toString(), pos.getIndex());
            ex2222222.initCause(fail);
            throw ex2222222;
        } catch (IllegalArgumentException e9) {
            e = e9;
            fail = e;
            if (str2 == null) {
            }
            msg = fail.getMessage();
            StringBuilder sb222222222 = new StringBuilder();
            sb222222222.append("(");
            sb222222222.append(fail.getClass().getName());
            sb222222222.append(")");
            msg = sb222222222.toString();
            StringBuilder sb322222222 = new StringBuilder();
            sb322222222.append("Failed to parse date [");
            sb322222222.append(input);
            sb322222222.append("]: ");
            sb322222222.append(msg);
            ParseException ex22222222 = new ParseException(sb322222222.toString(), pos.getIndex());
            ex22222222.initCause(fail);
            throw ex22222222;
        }
    }

    private static boolean checkOffset(String value, int offset, char expected) {
        return offset < value.length() && value.charAt(offset) == expected;
    }

    private static int parseInt(String value, int beginIndex, int endIndex) throws NumberFormatException {
        if (beginIndex < 0 || endIndex > value.length() || beginIndex > endIndex) {
            throw new NumberFormatException(value);
        }
        int digit = beginIndex;
        int result = 0;
        if (digit < endIndex) {
            int i = digit + 1;
            int digit2 = Character.digit(value.charAt(digit), 10);
            if (digit2 >= 0) {
                result = -digit2;
                digit = i;
            } else {
                StringBuilder sb = new StringBuilder();
                sb.append("Invalid number: ");
                sb.append(value.substring(beginIndex, endIndex));
                throw new NumberFormatException(sb.toString());
            }
        }
        while (digit < endIndex) {
            int i2 = digit + 1;
            int digit3 = Character.digit(value.charAt(digit), 10);
            if (digit3 >= 0) {
                result = (result * 10) - digit3;
                digit = i2;
            } else {
                StringBuilder sb2 = new StringBuilder();
                sb2.append("Invalid number: ");
                sb2.append(value.substring(beginIndex, endIndex));
                throw new NumberFormatException(sb2.toString());
            }
        }
        return -result;
    }

    private static void padInt(StringBuilder buffer, int value, int length) {
        String strValue = Integer.toString(value);
        for (int i = length - strValue.length(); i > 0; i--) {
            buffer.append('0');
        }
        buffer.append(strValue);
    }

    private static int indexOfNonDigit(String string, int offset) {
        for (int i = offset; i < string.length(); i++) {
            char c = string.charAt(i);
            if (c < '0' || c > '9') {
                return i;
            }
        }
        return string.length();
    }
}
