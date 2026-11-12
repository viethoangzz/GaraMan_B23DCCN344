package vn.edu.ptit.garaman.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.SecureRandom;
import java.util.Base64;

public final class Csrf4 {
    private Csrf4() {}
    public static String token(HttpSession session) {
        String value = (String) session.getAttribute("csrf");
        if (value == null) {
            byte[] bytes = new byte[32]; new SecureRandom().nextBytes(bytes);
            value = Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
            session.setAttribute("csrf", value);
        }
        return value;
    }
    public static boolean valid(HttpServletRequest req) {
        String expected = (String) req.getSession().getAttribute("csrf");
        String provided = req.getParameter("csrf");
        return expected != null && provided != null && java.security.MessageDigest.isEqual(
            expected.getBytes(java.nio.charset.StandardCharsets.UTF_8), provided.getBytes(java.nio.charset.StandardCharsets.UTF_8));
    }
}
