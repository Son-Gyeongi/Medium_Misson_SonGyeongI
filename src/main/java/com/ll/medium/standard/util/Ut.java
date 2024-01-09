package com.ll.medium.standard.util;

public class Ut {

    public static class url {

        public static String deleteQueryParam(String url, String paramName) {
            int startPoint = url.indexOf(paramName + "=");

            if (startPoint == -1) return url; // 값이 없는 경우

            int endPoint = url.substring(startPoint).indexOf("&");

            if (endPoint == -1) {
                return url.substring(0, startPoint - 1);
            }

            String urlAfter = url.substring(startPoint + endPoint + 1);

            return url.substring(0, startPoint) + urlAfter;
        }
    }
}
