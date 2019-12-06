package com.payu.custombrowser.util;

import android.support.v7.widget.helper.ItemTouchHelper.Callback;
import com.bumptech.glide.load.Key;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.PushbackInputStream;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.URLDecoder;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TimeZone;

public abstract class e {
    private final String a;
    private final int b;
    /* access modifiers changed from: private */
    public ServerSocket c;
    private Set<Socket> d;
    private Thread e;
    /* access modifiers changed from: private */
    public a f;
    /* access modifiers changed from: private */
    public o g;

    public interface a {
        void a(Runnable runnable);
    }

    public static class b {
        private String a;
        private String b;
        private String c;

        public String a() {
            return String.format("%s=%s; expires=%s", new Object[]{this.a, this.b, this.c});
        }
    }

    public class c implements Iterable<String> {
        private HashMap<String, String> b = new HashMap<>();
        private ArrayList<b> c = new ArrayList<>();

        public c(Map<String, String> map) {
            String str = (String) map.get("cookie");
            if (str != null) {
                for (String trim : str.split(";")) {
                    String[] split = trim.trim().split("=");
                    if (split.length == 2) {
                        this.b.put(split[0], split[1]);
                    }
                }
            }
        }

        public Iterator<String> iterator() {
            return this.b.keySet().iterator();
        }

        public void a(k kVar) {
            Iterator it = this.c.iterator();
            while (it.hasNext()) {
                kVar.a("Set-Cookie", ((b) it.next()).a());
            }
        }
    }

    public static class d implements a {
        private long a;

        public void a(Runnable runnable) {
            this.a++;
            Thread thread = new Thread(runnable);
            thread.setDaemon(true);
            StringBuilder sb = new StringBuilder();
            sb.append("NanoHttpd Request Processor (#");
            sb.append(this.a);
            sb.append(")");
            thread.setName(sb.toString());
            thread.start();
        }
    }

    /* renamed from: com.payu.custombrowser.util.e$e reason: collision with other inner class name */
    public static class C0002e implements m {
        private File a;
        private OutputStream b = new FileOutputStream(this.a);

        public C0002e(String str) throws IOException {
            this.a = File.createTempFile("NH-", "", new File(str));
        }

        public void a() throws Exception {
            e.b((Closeable) this.b);
            this.a.delete();
        }

        public String b() {
            return this.a.getAbsolutePath();
        }
    }

    public static class f implements n {
        private final String a = System.getProperty("java.io.tmpdir");
        private final List<m> b = new ArrayList();

        public m a() throws Exception {
            C0002e eVar = new C0002e(this.a);
            this.b.add(eVar);
            return eVar;
        }

        public void b() {
            for (m a2 : this.b) {
                try {
                    a2.a();
                } catch (Exception e) {
                }
            }
            this.b.clear();
        }
    }

    private class g implements o {
        private g() {
        }

        public n a() {
            return new f();
        }
    }

    protected class h implements i {
        private final n b;
        private final OutputStream c;
        private PushbackInputStream d;
        private int e;
        private int f;
        private String g;
        private j h;
        private Map<String, String> i;
        private Map<String, String> j;
        private c k;
        private String l;

        public h(n nVar, InputStream inputStream, OutputStream outputStream, InetAddress inetAddress) {
            this.b = nVar;
            this.d = new PushbackInputStream(inputStream, 8192);
            this.c = outputStream;
            String str = (inetAddress.isLoopbackAddress() || inetAddress.isAnyLocalAddress()) ? "127.0.0.1" : inetAddress.getHostAddress().toString();
            this.j = new HashMap();
            this.j.put("remote-addr", str);
            this.j.put("http-client-ip", str);
        }

        public void a() throws IOException {
            try {
                byte[] bArr = new byte[8192];
                this.e = 0;
                this.f = 0;
                int read = this.d.read(bArr, 0, 8192);
                if (read != -1) {
                    while (true) {
                        if (read <= 0) {
                            break;
                        }
                        this.f += read;
                        this.e = a(bArr, this.f);
                        if (this.e > 0) {
                            break;
                        }
                        read = this.d.read(bArr, this.f, 8192 - this.f);
                    }
                    if (this.e < this.f) {
                        this.d.unread(bArr, this.e, this.f - this.e);
                    }
                    this.i = new HashMap();
                    if (this.j == null) {
                        this.j = new HashMap();
                    }
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(bArr, 0, this.f)));
                    HashMap hashMap = new HashMap();
                    a(bufferedReader, hashMap, this.i, this.j);
                    this.h = j.lookup((String) hashMap.get("method"));
                    if (this.h != null) {
                        this.g = (String) hashMap.get("uri");
                        this.k = new c(this.j);
                        k a2 = e.this.a((i) this);
                        if (a2 != null) {
                            this.k.a(a2);
                            a2.a(this.h);
                            a2.a(this.c);
                            this.b.b();
                            return;
                        }
                        throw new l(b.INTERNAL_ERROR, "SERVER INTERNAL ERROR: Serve() returned a null response.");
                    }
                    throw new l(b.BAD_REQUEST, "BAD REQUEST: Syntax error.");
                }
                e.b((Closeable) this.d);
                e.b((Closeable) this.c);
                throw new SocketException("NanoHttpd Shutdown");
            } catch (Exception e2) {
                e.b((Closeable) this.d);
                e.b((Closeable) this.c);
                throw new SocketException("NanoHttpd Shutdown");
            } catch (SocketException e3) {
                throw e3;
            } catch (SocketTimeoutException e4) {
                throw e4;
            } catch (IOException e5) {
                StringBuilder sb = new StringBuilder();
                sb.append("SERVER INTERNAL ERROR: IOException: ");
                sb.append(e5.getMessage());
                new k(b.INTERNAL_ERROR, "text/plain", sb.toString()).a(this.c);
                e.b((Closeable) this.c);
            } catch (l e6) {
                try {
                    new k(e6.a(), "text/plain", e6.getMessage()).a(this.c);
                    e.b((Closeable) this.c);
                } catch (Throwable th) {
                    this.b.b();
                    throw th;
                }
            }
        }

        public void a(Map<String, String> map) throws IOException, l {
            BufferedReader bufferedReader;
            RandomAccessFile randomAccessFile;
            long j2;
            MappedByteBuffer map2;
            String str;
            Map<String, String> map3 = map;
            StringTokenizer stringTokenizer = null;
            try {
                randomAccessFile = g();
                try {
                    if (this.j.containsKey("content-length")) {
                        j2 = (long) Integer.parseInt((String) this.j.get("content-length"));
                    } else if (this.e < this.f) {
                        j2 = (long) (this.f - this.e);
                    } else {
                        j2 = 0;
                    }
                    byte[] bArr = new byte[512];
                    while (this.f >= 0 && j2 > 0) {
                        this.f = this.d.read(bArr, 0, (int) Math.min(j2, 512));
                        j2 -= (long) this.f;
                        if (this.f > 0) {
                            randomAccessFile.write(bArr, 0, this.f);
                        }
                    }
                    map2 = randomAccessFile.getChannel().map(MapMode.READ_ONLY, 0, randomAccessFile.length());
                    randomAccessFile.seek(0);
                    bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(randomAccessFile.getFD())));
                } catch (Throwable th) {
                    th = th;
                    bufferedReader = null;
                    e.b((Closeable) randomAccessFile);
                    e.b((Closeable) bufferedReader);
                    throw th;
                }
                try {
                    if (j.POST.equals(this.h)) {
                        String str2 = "";
                        String str3 = (String) this.j.get("content-type");
                        if (str3 != null) {
                            stringTokenizer = new StringTokenizer(str3, ",; ");
                            if (stringTokenizer.hasMoreTokens()) {
                                str2 = stringTokenizer.nextToken();
                            }
                        }
                        if (!"multipart/form-data".equalsIgnoreCase(str2)) {
                            String str4 = "";
                            StringBuilder sb = new StringBuilder();
                            char[] cArr = new char[512];
                            for (int read = bufferedReader.read(cArr); read >= 0 && !str4.endsWith("\r\n"); read = bufferedReader.read(cArr)) {
                                str4 = String.valueOf(cArr, 0, read);
                                sb.append(str4);
                            }
                            String trim = sb.toString().trim();
                            if (CBConstant.HTTP_URLENCODED.equalsIgnoreCase(str2)) {
                                a(trim, this.i);
                            } else if (trim.length() != 0) {
                                map3.put(CBConstant.POST_DATA, trim);
                            }
                        } else if (stringTokenizer.hasMoreTokens()) {
                            String str5 = "boundary=";
                            String substring = str3.substring(str3.indexOf(str5) + str5.length(), str3.length());
                            if (!substring.startsWith("\"") || !substring.endsWith("\"")) {
                                str = substring;
                            } else {
                                str = substring.substring(1, substring.length() - 1);
                            }
                            a(str, map2, bufferedReader, this.i, map);
                        } else {
                            throw new l(b.BAD_REQUEST, "BAD REQUEST: Content type is multipart/form-data but boundary missing. Usage: GET /example/file.html");
                        }
                    } else if (j.PUT.equals(this.h)) {
                        map3.put("content", a(map2, 0, map2.limit()));
                    }
                    e.b((Closeable) randomAccessFile);
                    e.b((Closeable) bufferedReader);
                } catch (Throwable th2) {
                    th = th2;
                    e.b((Closeable) randomAccessFile);
                    e.b((Closeable) bufferedReader);
                    throw th;
                }
            } catch (Throwable th3) {
                th = th3;
                randomAccessFile = null;
                bufferedReader = null;
                e.b((Closeable) randomAccessFile);
                e.b((Closeable) bufferedReader);
                throw th;
            }
        }

        private void a(BufferedReader bufferedReader, Map<String, String> map, Map<String, String> map2, Map<String, String> map3) throws l {
            String str;
            try {
                String readLine = bufferedReader.readLine();
                if (readLine != null) {
                    StringTokenizer stringTokenizer = new StringTokenizer(readLine);
                    if (stringTokenizer.hasMoreTokens()) {
                        map.put("method", stringTokenizer.nextToken());
                        if (stringTokenizer.hasMoreTokens()) {
                            String nextToken = stringTokenizer.nextToken();
                            int indexOf = nextToken.indexOf(63);
                            if (indexOf >= 0) {
                                a(nextToken.substring(indexOf + 1), map2);
                                str = e.this.a(nextToken.substring(0, indexOf));
                            } else {
                                str = e.this.a(nextToken);
                            }
                            if (stringTokenizer.hasMoreTokens()) {
                                String readLine2 = bufferedReader.readLine();
                                while (readLine2 != null && readLine2.trim().length() > 0) {
                                    int indexOf2 = readLine2.indexOf(58);
                                    if (indexOf2 >= 0) {
                                        map3.put(readLine2.substring(0, indexOf2).trim().toLowerCase(Locale.US), readLine2.substring(indexOf2 + 1).trim());
                                    }
                                    readLine2 = bufferedReader.readLine();
                                }
                            }
                            map.put("uri", str);
                            return;
                        }
                        throw new l(b.BAD_REQUEST, "BAD REQUEST: Missing URI. Usage: GET /example/file.html");
                    }
                    throw new l(b.BAD_REQUEST, "BAD REQUEST: Syntax error. Usage: GET /example/file.html");
                }
            } catch (IOException e2) {
                b bVar = b.INTERNAL_ERROR;
                StringBuilder sb = new StringBuilder();
                sb.append("SERVER INTERNAL ERROR: IOException: ");
                sb.append(e2.getMessage());
                throw new l(bVar, sb.toString(), e2);
            }
        }

        private void a(String str, ByteBuffer byteBuffer, BufferedReader bufferedReader, Map<String, String> map, Map<String, String> map2) throws l {
            String str2 = str;
            ByteBuffer byteBuffer2 = byteBuffer;
            try {
                int[] a2 = a(byteBuffer2, str.getBytes());
                String readLine = bufferedReader.readLine();
                int i2 = 1;
                while (readLine != null) {
                    if (readLine.contains(str2)) {
                        i2++;
                        HashMap hashMap = new HashMap();
                        String readLine2 = bufferedReader.readLine();
                        while (readLine2 != null && readLine2.trim().length() > 0) {
                            int indexOf = readLine2.indexOf(58);
                            if (indexOf != -1) {
                                hashMap.put(readLine2.substring(0, indexOf).trim().toLowerCase(Locale.US), readLine2.substring(indexOf + 1).trim());
                            }
                            readLine2 = bufferedReader.readLine();
                        }
                        if (readLine2 != null) {
                            String str3 = (String) hashMap.get("content-disposition");
                            if (str3 != null) {
                                StringTokenizer stringTokenizer = new StringTokenizer(str3, ";");
                                HashMap hashMap2 = new HashMap();
                                while (stringTokenizer.hasMoreTokens()) {
                                    String trim = stringTokenizer.nextToken().trim();
                                    int indexOf2 = trim.indexOf(61);
                                    if (indexOf2 != -1) {
                                        hashMap2.put(trim.substring(0, indexOf2).trim().toLowerCase(Locale.US), trim.substring(indexOf2 + 1).trim());
                                    }
                                }
                                String str4 = (String) hashMap2.get("name");
                                String substring = str4.substring(1, str4.length() - 1);
                                String str5 = "";
                                if (hashMap.get("content-type") == null) {
                                    while (readLine2 != null && !readLine2.contains(str2)) {
                                        readLine2 = bufferedReader.readLine();
                                        if (readLine2 != null) {
                                            int indexOf3 = readLine2.indexOf(str2);
                                            if (indexOf3 == -1) {
                                                StringBuilder sb = new StringBuilder();
                                                sb.append(str5);
                                                sb.append(readLine2);
                                                str5 = sb.toString();
                                            } else {
                                                StringBuilder sb2 = new StringBuilder();
                                                sb2.append(str5);
                                                sb2.append(readLine2.substring(0, indexOf3 - 2));
                                                str5 = sb2.toString();
                                            }
                                        }
                                    }
                                    Map<String, String> map3 = map2;
                                } else if (i2 <= a2.length) {
                                    int a3 = a(byteBuffer2, a2[i2 - 2]);
                                    map2.put(substring, a(byteBuffer2, a3, (a2[i2 - 1] - a3) - 4));
                                    String str6 = (String) hashMap2.get("filename");
                                    str5 = str6.substring(1, str6.length() - 1);
                                    while (true) {
                                        readLine2 = bufferedReader.readLine();
                                        if (readLine2 == null) {
                                            break;
                                        } else if (readLine2.contains(str2)) {
                                            break;
                                        }
                                    }
                                } else {
                                    throw new l(b.INTERNAL_ERROR, "Error processing request");
                                }
                                map.put(substring, str5);
                            } else {
                                throw new l(b.BAD_REQUEST, "BAD REQUEST: Content type is multipart/form-data but no content-disposition info found. Usage: GET /example/file.html");
                            }
                        } else {
                            Map<String, String> map4 = map;
                            Map<String, String> map5 = map2;
                        }
                        readLine = readLine2;
                    } else {
                        throw new l(b.BAD_REQUEST, "BAD REQUEST: Content type is multipart/form-data but next chunk does not start with boundary. Usage: GET /example/file.html");
                    }
                }
            } catch (IOException e2) {
                b bVar = b.INTERNAL_ERROR;
                StringBuilder sb3 = new StringBuilder();
                sb3.append("SERVER INTERNAL ERROR: IOException: ");
                sb3.append(e2.getMessage());
                throw new l(bVar, sb3.toString(), e2);
            }
        }

        private int a(byte[] bArr, int i2) {
            int i3 = 0;
            while (true) {
                int i4 = i3 + 3;
                if (i4 >= i2) {
                    return 0;
                }
                if (bArr[i3] == 13 && bArr[i3 + 1] == 10 && bArr[i3 + 2] == 13 && bArr[i4] == 10) {
                    return i3 + 4;
                }
                i3++;
            }
        }

        private int[] a(ByteBuffer byteBuffer, byte[] bArr) {
            ArrayList arrayList = new ArrayList();
            int i2 = 0;
            int i3 = 0;
            int i4 = -1;
            while (i2 < byteBuffer.limit()) {
                if (byteBuffer.get(i2) == bArr[i3]) {
                    if (i3 == 0) {
                        i4 = i2;
                    }
                    i3++;
                    if (i3 == bArr.length) {
                        arrayList.add(Integer.valueOf(i4));
                        i3 = 0;
                        i4 = -1;
                    }
                } else {
                    i2 -= i3;
                    i3 = 0;
                    i4 = -1;
                }
                i2++;
            }
            int[] iArr = new int[arrayList.size()];
            for (int i5 = 0; i5 < iArr.length; i5++) {
                iArr[i5] = ((Integer) arrayList.get(i5)).intValue();
            }
            return iArr;
        }

        private String a(ByteBuffer byteBuffer, int i2, int i3) {
            String str = "";
            if (i3 > 0) {
                FileOutputStream fileOutputStream = null;
                try {
                    m a2 = this.b.a();
                    ByteBuffer duplicate = byteBuffer.duplicate();
                    FileOutputStream fileOutputStream2 = new FileOutputStream(a2.b());
                    try {
                        FileChannel channel = fileOutputStream2.getChannel();
                        duplicate.position(i2).limit(i2 + i3);
                        channel.write(duplicate.slice());
                        str = a2.b();
                        e.b((Closeable) fileOutputStream2);
                    } catch (Exception e2) {
                        e = e2;
                        fileOutputStream = fileOutputStream2;
                        try {
                            throw new Error(e);
                        } catch (Throwable th) {
                            th = th;
                            e.b((Closeable) fileOutputStream);
                            throw th;
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        fileOutputStream = fileOutputStream2;
                        e.b((Closeable) fileOutputStream);
                        throw th;
                    }
                } catch (Exception e3) {
                    e = e3;
                    throw new Error(e);
                }
            }
            return str;
        }

        private RandomAccessFile g() {
            try {
                return new RandomAccessFile(this.b.a().b(), "rw");
            } catch (Exception e2) {
                throw new Error(e2);
            }
        }

        private int a(ByteBuffer byteBuffer, int i2) {
            while (i2 < byteBuffer.limit()) {
                if (byteBuffer.get(i2) == 13) {
                    i2++;
                    if (byteBuffer.get(i2) == 10) {
                        i2++;
                        if (byteBuffer.get(i2) == 13) {
                            i2++;
                            if (byteBuffer.get(i2) == 10) {
                                break;
                            }
                        } else {
                            continue;
                        }
                    } else {
                        continue;
                    }
                }
                i2++;
            }
            return i2 + 1;
        }

        private void a(String str, Map<String, String> map) {
            if (str == null) {
                this.l = "";
                return;
            }
            this.l = str;
            StringTokenizer stringTokenizer = new StringTokenizer(str, "&");
            while (stringTokenizer.hasMoreTokens()) {
                String nextToken = stringTokenizer.nextToken();
                int indexOf = nextToken.indexOf(61);
                if (indexOf >= 0) {
                    map.put(e.this.a(nextToken.substring(0, indexOf)).trim(), e.this.a(nextToken.substring(indexOf + 1)));
                } else {
                    map.put(e.this.a(nextToken).trim(), "");
                }
            }
        }

        public final Map<String, String> b() {
            return this.i;
        }

        public String c() {
            return this.l;
        }

        public final Map<String, String> d() {
            return this.j;
        }

        public final String e() {
            return this.g;
        }

        public final j f() {
            return this.h;
        }
    }

    public interface i {
        void a(Map<String, String> map) throws IOException, l;

        Map<String, String> b();

        String c();

        Map<String, String> d();

        String e();

        j f();
    }

    public enum j {
        GET,
        PUT,
        POST,
        DELETE,
        HEAD,
        OPTIONS;

        static j lookup(String method) {
            j[] values;
            for (j jVar : values()) {
                if (jVar.toString().equalsIgnoreCase(method)) {
                    return jVar;
                }
            }
            return null;
        }
    }

    public static class k {
        private a a;
        private String b;
        private InputStream c;
        private Map<String, String> d;
        private j e;
        private boolean f;

        public interface a {
            String getDescription();
        }

        public enum b implements a {
            SWITCH_PROTOCOL(101, "Switching Protocols"),
            OK(Callback.DEFAULT_DRAG_ANIMATION_DURATION, "OK"),
            CREATED(201, "Created"),
            ACCEPTED(202, "Accepted"),
            NO_CONTENT(204, "No Content"),
            PARTIAL_CONTENT(206, "Partial Content"),
            REDIRECT(301, "Moved Permanently"),
            NOT_MODIFIED(304, "Not Modified"),
            BAD_REQUEST(400, "Bad Request"),
            UNAUTHORIZED(401, "Unauthorized"),
            FORBIDDEN(403, "Forbidden"),
            NOT_FOUND(404, "Not Found"),
            METHOD_NOT_ALLOWED(405, "Method Not Allowed"),
            RANGE_NOT_SATISFIABLE(416, "Requested Range Not Satisfiable"),
            INTERNAL_ERROR(500, "Internal Server Error");
            
            private final String description;
            private final int requestStatus;

            private b(int i, String str) {
                this.requestStatus = i;
                this.description = str;
            }

            public int getRequestStatus() {
                return this.requestStatus;
            }

            public String getDescription() {
                StringBuilder sb = new StringBuilder();
                sb.append("");
                sb.append(this.requestStatus);
                sb.append(" ");
                sb.append(this.description);
                return sb.toString();
            }
        }

        public k(String str) {
            this(b.OK, "text/html", str);
        }

        public k(a aVar, String str, String str2) {
            ByteArrayInputStream byteArrayInputStream;
            this.d = new HashMap();
            this.a = aVar;
            this.b = str;
            if (str2 != null) {
                try {
                    byteArrayInputStream = new ByteArrayInputStream(str2.getBytes(Key.STRING_CHARSET_NAME));
                } catch (UnsupportedEncodingException e2) {
                    e2.printStackTrace();
                    return;
                }
            } else {
                byteArrayInputStream = null;
            }
            this.c = byteArrayInputStream;
        }

        public void a(String str, String str2) {
            this.d.put(str, str2);
        }

        /* access modifiers changed from: protected */
        /* JADX WARNING: Removed duplicated region for block: B:16:0x008f A[Catch:{ IOException -> 0x010d }] */
        /* JADX WARNING: Removed duplicated region for block: B:28:0x00e4 A[Catch:{ IOException -> 0x010d }] */
        /* JADX WARNING: Removed duplicated region for block: B:29:0x00eb A[Catch:{ IOException -> 0x010d }] */
        public void a(OutputStream outputStream) {
            String str = this.b;
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("E, d MMM yyyy HH:mm:ss 'GMT'", Locale.US);
            simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
            try {
                if (this.a != null) {
                    PrintWriter printWriter = new PrintWriter(outputStream);
                    StringBuilder sb = new StringBuilder();
                    sb.append("HTTP/1.1 ");
                    sb.append(this.a.getDescription());
                    sb.append(" \r\n");
                    printWriter.print(sb.toString());
                    if (str != null) {
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append("Content-Type: ");
                        sb2.append(str);
                        sb2.append("\r\n");
                        printWriter.print(sb2.toString());
                    }
                    if (this.d != null) {
                        if (this.d.get("Date") != null) {
                            if (this.d != null) {
                                for (String str2 : this.d.keySet()) {
                                    String str3 = (String) this.d.get(str2);
                                    StringBuilder sb3 = new StringBuilder();
                                    sb3.append(str2);
                                    sb3.append(": ");
                                    sb3.append(str3);
                                    sb3.append("\r\n");
                                    printWriter.print(sb3.toString());
                                }
                            }
                            a(printWriter, this.d);
                            if (this.e != j.HEAD || !this.f) {
                                int available = this.c == null ? this.c.available() : 0;
                                a(printWriter, this.d, available);
                                printWriter.print("\r\n");
                                printWriter.flush();
                                a(outputStream, available);
                            } else {
                                a(outputStream, printWriter);
                            }
                            outputStream.flush();
                            e.b((Closeable) this.c);
                            return;
                        }
                    }
                    StringBuilder sb4 = new StringBuilder();
                    sb4.append("Date: ");
                    sb4.append(simpleDateFormat.format(new Date()));
                    sb4.append("\r\n");
                    printWriter.print(sb4.toString());
                    if (this.d != null) {
                    }
                    a(printWriter, this.d);
                    if (this.e != j.HEAD) {
                    }
                    if (this.c == null) {
                    }
                    a(printWriter, this.d, available);
                    printWriter.print("\r\n");
                    printWriter.flush();
                    a(outputStream, available);
                    outputStream.flush();
                    e.b((Closeable) this.c);
                    return;
                }
                throw new Error("sendResponse(): Status can't be null.");
            } catch (IOException e2) {
            }
        }

        /* access modifiers changed from: protected */
        public void a(PrintWriter printWriter, Map<String, String> map, int i) {
            if (!a(map, "content-length")) {
                StringBuilder sb = new StringBuilder();
                sb.append("Content-Length: ");
                sb.append(i);
                sb.append("\r\n");
                printWriter.print(sb.toString());
            }
        }

        /* access modifiers changed from: protected */
        public void a(PrintWriter printWriter, Map<String, String> map) {
            if (!a(map, "connection")) {
                printWriter.print("Connection: keep-alive\r\n");
            }
        }

        private boolean a(Map<String, String> map, String str) {
            boolean z = false;
            for (String equalsIgnoreCase : map.keySet()) {
                z |= equalsIgnoreCase.equalsIgnoreCase(str);
            }
            return z;
        }

        private void a(OutputStream outputStream, PrintWriter printWriter) throws IOException {
            printWriter.print("Transfer-Encoding: chunked\r\n");
            printWriter.print("\r\n");
            printWriter.flush();
            byte[] bytes = "\r\n".getBytes();
            byte[] bArr = new byte[16384];
            while (true) {
                int read = this.c.read(bArr);
                if (read > 0) {
                    outputStream.write(String.format("%x\r\n", new Object[]{Integer.valueOf(read)}).getBytes());
                    outputStream.write(bArr, 0, read);
                    outputStream.write(bytes);
                } else {
                    outputStream.write(String.format("0\r\n\r\n", new Object[0]).getBytes());
                    return;
                }
            }
        }

        private void a(OutputStream outputStream, int i) throws IOException {
            if (this.e != j.HEAD && this.c != null) {
                byte[] bArr = new byte[16384];
                while (i > 0) {
                    int read = this.c.read(bArr, 0, i > 16384 ? 16384 : i);
                    if (read > 0) {
                        outputStream.write(bArr, 0, read);
                        i -= read;
                    } else {
                        return;
                    }
                }
            }
        }

        public void a(j jVar) {
            this.e = jVar;
        }
    }

    public static final class l extends Exception {
        private final b a;

        public l(b bVar, String str) {
            super(str);
            this.a = bVar;
        }

        public l(b bVar, String str, Exception exc) {
            super(str, exc);
            this.a = bVar;
        }

        public b a() {
            return this.a;
        }
    }

    public interface m {
        void a() throws Exception;

        String b();
    }

    public interface n {
        m a() throws Exception;

        void b();
    }

    public interface o {
        n a();
    }

    public e(int i2) {
        this(null, i2);
    }

    public e(String str, int i2) {
        this.d = new HashSet();
        this.a = str;
        this.b = i2;
        a((o) new g());
        a((a) new d());
    }

    /* access modifiers changed from: private */
    public static final void b(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e2) {
            }
        }
    }

    /* access modifiers changed from: private */
    public static final void d(Socket socket) {
        if (socket != null) {
            try {
                socket.close();
            } catch (IOException e2) {
            }
        }
    }

    private static final void a(ServerSocket serverSocket) {
        if (serverSocket != null) {
            try {
                serverSocket.close();
            } catch (IOException e2) {
            }
        }
    }

    public void a() throws IOException {
        this.c = new ServerSocket();
        ServerSocket serverSocket = this.c;
        String str = this.a;
        serverSocket.bind(str != null ? new InetSocketAddress(str, this.b) : new InetSocketAddress(this.b));
        this.e = new Thread(new Runnable() {
            public void run() {
                do {
                    try {
                        final Socket accept = e.this.c.accept();
                        e.this.a(accept);
                        accept.setSoTimeout(5000);
                        final InputStream inputStream = accept.getInputStream();
                        e.this.f.a(new Runnable() {
                            public void run() {
                                OutputStream outputStream = null;
                                try {
                                    outputStream = accept.getOutputStream();
                                    h hVar = new h(e.this.g.a(), inputStream, outputStream, accept.getInetAddress());
                                    while (!accept.isClosed()) {
                                        hVar.a();
                                    }
                                } catch (Exception e) {
                                    if (e instanceof SocketException) {
                                        if (!"NanoHttpd Shutdown".equals(e.getMessage())) {
                                        }
                                    }
                                    e.printStackTrace();
                                } catch (Throwable th) {
                                    e.b((Closeable) null);
                                    e.b((Closeable) inputStream);
                                    e.d(accept);
                                    e.this.b(accept);
                                    throw th;
                                }
                                e.b((Closeable) outputStream);
                                e.b((Closeable) inputStream);
                                e.d(accept);
                                e.this.b(accept);
                            }
                        });
                    } catch (IOException e) {
                    }
                } while (!e.this.c.isClosed());
            }
        });
        this.e.setDaemon(true);
        this.e.setName("NH Main Listener");
        this.e.start();
    }

    public void b() {
        try {
            a(this.c);
            c();
            if (this.e != null) {
                this.e.join();
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public synchronized void a(Socket socket) {
        this.d.add(socket);
    }

    public synchronized void b(Socket socket) {
        this.d.remove(socket);
    }

    public synchronized void c() {
        for (Socket d2 : this.d) {
            d(d2);
        }
    }

    @Deprecated
    public k a(String str, j jVar, Map<String, String> map, Map<String, String> map2, Map<String, String> map3) {
        return new k(b.NOT_FOUND, "text/plain", "Not Found");
    }

    public k a(i iVar) {
        HashMap hashMap = new HashMap();
        j f2 = iVar.f();
        if (j.PUT.equals(f2) || j.POST.equals(f2)) {
            try {
                iVar.a(hashMap);
            } catch (IOException e2) {
                StringBuilder sb = new StringBuilder();
                sb.append("SERVER INTERNAL ERROR: IOException: ");
                sb.append(e2.getMessage());
                return new k(b.INTERNAL_ERROR, "text/plain", sb.toString());
            } catch (l e3) {
                return new k(e3.a(), "text/plain", e3.getMessage());
            }
        }
        Map b2 = iVar.b();
        b2.put("NanoHttpd.QUERY_STRING", iVar.c());
        return a(iVar.e(), f2, iVar.d(), b2, hashMap);
    }

    /* access modifiers changed from: protected */
    public String a(String str) {
        try {
            return URLDecoder.decode(str, "UTF8");
        } catch (UnsupportedEncodingException e2) {
            return null;
        }
    }

    public void a(a aVar) {
        this.f = aVar;
    }

    public void a(o oVar) {
        this.g = oVar;
    }
}
