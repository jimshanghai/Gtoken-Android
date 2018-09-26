package com.netban.edc.wallet.utils;

import java.text.DecimalFormat;
import java.util.Collections;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Evan on 2018/8/17.
 */

public class StringUtils {
    /**
     * 格式化小说点后三位
     * @param num
     * @return
     */
    public static String doubleToString(double num){
        //使用0.00不足位补0，#.##仅保留有效位

        return new DecimalFormat("0.000").format(num);
    }

    /**
     * 格式化小说点后三位
     * @param num
     * @return
     */
    public static String doubleToString(int num){
        //使用0.00不足位补0，#.##仅保留有效位
        return new DecimalFormat("0.000").format(num);
    }

    /**
     * 分割字符串，获得最后的一段
     * @param res
     * @param regex
     * @return
     */
    public static String lastofsplit(String res,String regex){
        String[] split = res.split(regex);
        if (split.length<=0)
            throw new IllegalArgumentException("regex is Illegal");
        return split[split.length-1];
    }
    private static String initjson(String str){
        StringBuffer buffer=new StringBuffer();
        for (char ca:str.toCharArray()){
            if (ca=='\n')
                continue;
            if (ca=='\b')
                continue;
            if (ca=='\r')
                continue;
            buffer.append(ca);
        }
        return buffer.toString();

    }
    /**
     * 格式化json数据
     * @param res
     * @return
     */
    public static String str2json(String res){
       res = initjson(res);
        LinkedList<Character> linkedList = new LinkedList<>();
        for (char ca:res.toCharArray()){
            linkedList.addLast(ca);
        }
        StringBuffer buffer=new StringBuffer();
        int level=0;
        while (!linkedList.isEmpty()){
            if (level<0)
                level=0;

            Character character = linkedList.getFirst();
            if (character.equals('}')){
                if(isCango(linkedList)){
                    buffer.append("\n");
                }
                level--;
                appendLevel(buffer,level);
                buffer.append(character);
                linkedList.removeFirst();
                continue;
            }
            if (character.equals(',')){
                buffer.append(character);
                if(isCango(linkedList)){
                    buffer.append("\n");
                }
                appendLevel(buffer,level);
                linkedList.removeFirst();
                continue;
            }
            if (character.equals('{')){
                buffer.append(character);
                if(isCango(linkedList)){
                    buffer.append("\n");
                }
                level++;
                appendLevel(buffer,level);
                linkedList.removeFirst();
                continue;
            }
            if (character.equals("\n")){
                appendLevel(buffer,level);
                continue;
            }
            buffer.append(character);
            linkedList.removeFirst();
        }

        return buffer.toString();
    }

    private static boolean isCango(LinkedList<Character> linkedList){
        if (linkedList.size()<3)
            return true;
        Character character = linkedList.get(1);
        Character character1 = linkedList.get(2);
        if (character.equals('\n'))
            return false;
        return true;

    }

    /**
     * 判断是否是数字或者是英文字母
     * @param c
     * @return
     */
    public static boolean judge(char c){
        if((c >='0' && c<='9')||(c >='a' && c<='z' ||  c >='A' && c<='Z')){
            return true;
        }
        return false;
    }
    public static boolean isMessyCode(String strName) {
        //去除字符串中的空格 制表符 换行 回车
        Pattern p = Pattern.compile("\\s*|\t*|\r*|\n*");
        Matcher m = p.matcher(strName);
        String after = m.replaceAll("");
        //去除字符串中的标点符号
        String temp = after.replaceAll("\\p{P}", "");
        //处理之后转换成字符数组
        char[] ch = temp.trim().toCharArray();
        for (int i = 0; i < ch.length; i++) {
            char c = ch[i];
            //判断是否是数字或者英文字符
            if (!judge(c)) {
                //判断是否是中日韩文
                if (!isChinese(c)) {
                    //如果不是数字或者英文字符也不是中日韩文则表示是乱码返回true
                    return true;
                }
            }
        }
        //表示不是乱码 返回false
        return false;
    }
    /**
     * 判断是否是中日韩文字
     * @param c     要判断的字符
     * @return      true或false
     */
    private static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
            return true;
        }
        return false;
    }
    private static void appendLevel(StringBuffer buffer,int level){
        for (int i=0;i<level;i++){
            buffer.append('\b');
        }
    }

}
