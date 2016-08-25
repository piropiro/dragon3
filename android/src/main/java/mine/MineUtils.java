package mine;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import mine.io.FileIO;

/**
 *
 * @author k-saito
 */
public class MineUtils {

    public static void checkNull(Object obj, String name) throws MineException {
        if (obj == null) {
            throw new MineException(name + " is null.");
        }
    }

    /**
     * OSがWindowsかどうかを判定する。
     * <p>
     *
     * @return
     */
    public static boolean isWindows() {
        String osname = System.getProperty("os.name");
        if (osname.length() < 7) {
            return false;
        }
        return osname.substring(0, 7).equals("Windows");
    }

    /**
     * オブジェクトのゲッターメソッドを実行する。
     * <p>
     *
     * @param obj
     * @param field
     * @return
     * @throws MineException
     */
    public static Object getField(Object obj, String field) throws MineException {
        try {
            String methodName = Character.toUpperCase(field.charAt(0)) + field.substring(1);
            Method method = obj.getClass().getDeclaredMethod("get" + methodName);
            return method.invoke(obj);
        } catch (ReflectiveOperationException e) {
            throw new MineException(e);
        }
    }

    /**
     * オブジェクトのセッターメソッドを実行する。
     * <p>
     *
     * @param obj
     * @param field
     * @param value
     * @throws MineException
     */
    public static void setField(Object obj, String field, Object value) throws MineException {
        try {
            String methodName = Character.toUpperCase(field.charAt(0)) + field.substring(1);
            Class<?> clazz = obj.getClass().getDeclaredMethod("get" + methodName).getReturnType();
            Method method = obj.getClass().getDeclaredMethod("set" + methodName, clazz);
            method.invoke(obj, new Object[]{value});
        } catch (ReflectiveOperationException e) {
            throw new MineException(e);
        }
    }

    /**
     * ファイルから文字列を読み込む。
     *
     * @param file
     * @return
     * @throws MineException
     */
    public static String[] readStringArray(String file) throws MineException {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(FileIO.getInputStream(file)))) {
            List<String> list = new ArrayList<>();
            for (String s = in.readLine(); s != null; s = in.readLine()) {
                list.add(s);
            }
            return list.toArray(new String[0]);
        } catch (IOException e) {
            throw new MineException(e);
        }

    }

    /**
     * ファイルに文字列を書き込む。
     *
     * @param file
     * @param list
     * @throws MineException
     */
    public static void writeStringArray(String file, String[] list) throws MineException {
        try (PrintWriter out = new PrintWriter(new OutputStreamWriter(FileIO.getOutputStream(file)))) {
            for (String str : list) {
                out.println(str);
            }
        }
    }

    /**
     * ファイルからIDとテキストを読み込む。
     * <p>
     *
     * @param file
     * @return
     * @throws MineException
     */
    public static String[][] readIdAndText(String file) throws MineException {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(FileIO.getInputStream(file)))) {
            List<String> idList = new ArrayList<>();
            List<String> textList = new ArrayList<>();
            for (String s = in.readLine(); s != null; s = in.readLine()) {
                StringTokenizer st = new StringTokenizer(s);
                if (st.countTokens() == 2) {
                    idList.add(st.nextToken());
                    textList.add(st.nextToken());
                }
            }
            String[][] result = new String[2][];
            result[0] = idList.toArray(new String[0]);
            result[1] = textList.toArray(new String[0]);
            return result;
        } catch (IOException e) {
            throw new MineException(e);
        }
    }

    public static Map<String, String> readIdAndTextMap(String file) throws MineException {
        String[][] idAndText = readIdAndText(file);
        HashMap<String, String> map = new LinkedHashMap<>(idAndText.length);
        for (int i = 0; i < idAndText[0].length; i++) {
            map.put(idAndText[0][i], idAndText[1][i]);
        }
        return map;
    }

    /**
     * エラーをファイルに出力する。
     * <p>
     *
     * @param filename
     */
    public static void setErr(String filename) {
        try {
            FileOutputStream fos = new FileOutputStream(filename);
            System.setErr(new PrintStream(fos));
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * ３つの数字の中間値を取得する。
     * <p>
     *
     * @param s
     * @param m
     * @param b
     * @return
     */
    public static int mid(int s, int m, int b) {
        return Math.max(s, Math.min(m, b));
    }

    /**
     * 反射角を求める。
     * <p>
     *
     * @param anga
     * @param angb
     * @return
     */
    public static double refAngle(double anga, double angb) {
        double angle = anga - angb;
        while (angle < 0) {
            angle += Math.PI * 2;
        }
        while (angle >= Math.PI * 2) {
            angle -= Math.PI * 2;
        }
        if (angle <= Math.PI / 2) {
            return anga;
        }
        if (angle >= Math.PI * 3 / 2) {
            return anga;
        }

        angle = angb * 2 - anga - Math.PI;
        while (angle < 0) {
            angle += Math.PI * 2;
        }
        while (angle >= Math.PI * 2) {
            angle -= Math.PI * 2;
        }
        return angle;
    }

    /**
     * 二次元配列をAffine変換する。
     * <p>
     *
     * @param src
     * @param dst
     * @param af
     */
    public static void affine(
            int[][] src,
            int[][] dst,
            int[] af) {

        for (int y = 0; y < src.length; y++) {
            for (int x = 0; x < src[0].length; x++) {
                int xs = x * af[0] + y * af[1] + af[2];
                int ys = x * af[3] + y * af[4] + af[5];
                if (xs < 0 || xs >= dst[0].length) {
                    continue;
                }
                if (ys < 0 || ys >= dst.length) {
                    continue;
                }
                dst[ys][xs] = src[y][x];
            }
        }
    }

    /**
     * 二次元配列を一次元配列に並べ替える。
     * <p>
     *
     * @param src
     * @param dst
     * @return
     */
    @SuppressWarnings("unchecked")
	public static <T> T[] linerize(T[][] src, T[] dst) {

        int size = 0;
        for (T[] src1 : src) {
            if (src1 == null) {
                continue;
            }
            size += src1.length;
        }

        if (dst.length < size) {
            dst = (T[]) java.lang.reflect.Array.newInstance(
                    dst.getClass().getComponentType(), size);
        }

        int pos = 0;
        for (T[] src1 : src) {
            if (src1 == null) {
                continue;
            }
            System.arraycopy(src1, 0, dst, pos, src1.length);
            pos += src1.length;
        }

        if (dst.length > size) {
            dst[size] = null;
        }

        return dst;
    }

    /**
     * オブジェクトの配列をリストに挿入する。
     * <p>
     *
     * @param <E>
     * @param list
     * @param objs
     * @return
     */
    @Deprecated
    public static <E> List<E> addToList(List<E> list, E[] objs) {
        list.addAll(Arrays.asList(objs));
        return list;
    }

    /**
     * 配列の比較
     *
     * @param a
     * @param b
     * @return
     */
    public static boolean compare(int[][] a, int[][] b) {
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[i].length; j++) {
                if (a[i][j] != b[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }
}
