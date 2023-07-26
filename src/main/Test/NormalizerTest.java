import filter.normalizer.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class NormalizerTest {
    @Test
    public void UpperCaseNormalizer(){
        Normalizer upperCase=new UpperCaseNormalizer();
        String str1="salam $293-AJS& bye das";
        String str2="s]qw!` xxJSU USB usb _l";
        String str3="=======2a";
        Assertions.assertEquals(str1.toUpperCase(),upperCase.normalize(str1));
        Assertions.assertEquals(str2.toUpperCase(),upperCase.normalize(str2));
        Assertions.assertEquals(str3.toUpperCase(),upperCase.normalize(str3));
    }
    @Test
    public void LowerCaseNormalizer(){
        Normalizer lowerCase=new LowerCaseNormalizer();
        String str1="SALAM $293-AJS& BYE dAs";
        String str2="      ";
        String str3="=!sSD=DF][==2a";
        Assertions.assertEquals(str1.toLowerCase(),lowerCase.normalize(str1));
        Assertions.assertEquals(str2.toLowerCase(),lowerCase.normalize(str2));
        Assertions.assertEquals(str3.toLowerCase(),lowerCase.normalize(str3));
    }

}
