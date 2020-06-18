package com.simplerobot.core.springboot.configuration;


import java.util.Random;

/**
 * @author <a href="https://github.com/ForteScarlet"> ForteScarlet </a>
 */
class FaceUtil {

    private static boolean enable = true;

    public static void close(){
        enable = false;
    }

    public static String getFace(){
        if(!enable){
            return "";
        }
        String[] faces = {
            " ~ （。＾▽＾）",
            " ~ (。・∀・)ノ",
            " ~ O(∩_∩)O",
            " ~ ε=ε=ε=(~￣▽￣)~",
            " ~ o(*////▽////*)q",
            " ~ ψ(｀∇´)ψ",
            " ~ (oﾟvﾟ)ノ",
            " ~ ˋ( ° ▽、° )",
            " ~ (*/ω＼*)",
            " ~ ヽ(￣ω￣(￣ω￣〃)ゝ",
            " ~ ^(*￣(oo)￣)^",
            " ~ []~(￣▽￣)~*",
            " ~ o(*￣︶￣*)o",
            " ~ (￣o￣) . z Z",
            " ~ ο(=•ω＜=)ρ⌒☆",
            " ~ （；´д｀）ゞ",
            " ~ (￣_￣|||)",
            " ~ （⊙ｏ⊙）",
            " ~ w(ﾟДﾟ)w",
        };
        return faces[new Random().nextInt(faces.length)];
    }
}
