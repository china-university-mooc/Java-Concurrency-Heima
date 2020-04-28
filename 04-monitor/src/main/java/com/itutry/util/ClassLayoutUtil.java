package com.itutry.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 只返回对象头的 MarkWord
 *
 * @author itutry
 * @create 2020-04-28_12:06
 */
public class ClassLayoutUtil {

  public static String toSimple(String printable) {
    Pattern pattern = Pattern.compile(" \\(object header\\).*?\\((.*?)\\)");
    final Matcher matcher = pattern.matcher(printable);
    StringBuilder builder = new StringBuilder();

    for (int i = 0; i < 2; i++) {
      if (matcher.find()) {
        final String group = matcher.group(1);
        final List<String> strs = Arrays.asList(group.split(" "));
        Collections.reverse(strs);
        final String str = String.join(" ", strs);
        builder.insert(0, str + " ");
      }
    }
    return builder.toString();
  }
}
