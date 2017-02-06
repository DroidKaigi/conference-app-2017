package io.github.droidkaigi.confsched2017.util;

public class StringUtil {

    public static boolean isHalfWidthCharacters(String s) {
        int[] codePointArray = toCodePointArray(s);// サロゲートペアに対応するためにコードポイントを用いる
        for (int i = 0; i < codePointArray.length; i++) {
            int c = codePointArray[i];
            if ((c <= '\u007e')) {
                // 英数字
                continue;
            } else {
                // それ以外
                return false;
            }
        }
        return true;
    }

    private static int[] toCodePointArray(String str) {
        int len = str.length();
        int[] acp = new int[str.codePointCount(0, len)];
        int j = 0;

        for (int i = 0, cp; i < len; i += Character.charCount(cp)) {
            cp = str.codePointAt(i);
            acp[j++] = cp;
        }
        return acp;
    }
}
