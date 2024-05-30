package in.jwt;

import javax.servlet.http.HttpServletResponse;

public class ResponseHolder {

    private static final ThreadLocal<HttpServletResponse> responseHolder = new ThreadLocal<>();

    public static void set(HttpServletResponse response) {
        responseHolder.set(response);
    }

    public static HttpServletResponse get() {
        return responseHolder.get();
    }

    public static void reset() {
        responseHolder.remove();
    }
}
