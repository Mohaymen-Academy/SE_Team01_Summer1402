import filter.normalizer.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class NormalizerTest {
    @Test
    public void normalize_upperCaseTest() {
        Normalizer upperCase = new UpperCaseNormalizer();
        String str1 = "salam $293-AJS& bye das";
        String str2 = "s]qw!` xxJSU USB usb _l";
        String str3 = "=======2a";
        Assertions.assertEquals("SALAM $293-AJS& BYE DAS", upperCase.normalize(str1));
        Assertions.assertEquals("S]QW!` XXJSU USB USB _L", upperCase.normalize(str2));
        Assertions.assertEquals("=======2A", upperCase.normalize(str3));
    }

    @Test
    public void normalize_lowerCaseTest() {
        Normalizer lowerCase = new LowerCaseNormalizer();
        String str1 = "SALAM $293-AJS& BYE dAs";
        String str2 = "      ";
        String str3 = "=!sSD=DF][==2a";
        Assertions.assertEquals("salam $293-ajs& bye das", lowerCase.normalize(str1));
        Assertions.assertEquals("      ", lowerCase.normalize(str2));
        Assertions.assertEquals("=!ssd=df][==2a", lowerCase.normalize(str3));
    }

}
