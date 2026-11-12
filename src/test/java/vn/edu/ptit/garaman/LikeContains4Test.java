package vn.edu.ptit.garaman;

import org.junit.jupiter.api.Test;
import vn.edu.ptit.garaman.dao.DAO4;
import static org.junit.jupiter.api.Assertions.assertEquals;

class LikeContains4Test {
    @Test void plainKeywordIsWrappedAndTrimmed() {
        assertEquals("%phanh%", DAO4.likeContains("  phanh "));
    }
    @Test void wildcardsAreEscaped() {
        assertEquals("%50\\%%", DAO4.likeContains("50%"));
        assertEquals("%a\\_b%", DAO4.likeContains("a_b"));
    }
    @Test void backslashIsEscaped() {
        assertEquals("%a\\\\b%", DAO4.likeContains("a\\b"));
    }
    @Test void nullBehavesLikeEmpty() {
        assertEquals("%%", DAO4.likeContains(null));
    }
}
