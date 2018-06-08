package com.jinanlongen.sparrow.util;

public class StringUtils {
  public static String stateTransfor(String state) {
    switch (state) {
      case "已禁用":

        return "disabled";
      case "已发布":

        return "published";
      case "待审核":

        return "committed";
      case "草稿":

        return "draft";
      case "回收站":

        return "trash";
      case "审核未过":

        return "declined";

      default:
        return "状态有误";
    }
  }
}
