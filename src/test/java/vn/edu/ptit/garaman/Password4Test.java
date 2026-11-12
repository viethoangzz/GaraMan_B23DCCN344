package vn.edu.ptit.garaman;
import org.junit.jupiter.api.Test;
import vn.edu.ptit.garaman.security.Password4;
import static org.junit.jupiter.api.Assertions.*;
class Password4Test {
    @Test void passwordHashVerifiesAndRejectsMismatch(){
        String hash=Password4.hash("StrongPassword!123");
        assertTrue(Password4.verify("StrongPassword!123",hash));
        assertFalse(Password4.verify("wrong",hash));
        assertFalse(Password4.verify("StrongPassword!123","broken"));
        assertTrue(hash.startsWith("pbkdf2$"));
    }
}
