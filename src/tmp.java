import java.util.ArrayList;

public class tmp {
    public static void main(String[] args) {
        ArrayList<String>splitMarks=new ArrayList<>();
        splitMarks.add(" ");
        splitMarks.add(",");
       String regex = "[" + String.join("", splitMarks) + "]";
       String[]ans="salam,azizm chtori, d".split(regex);
       for (String s:ans){
           System.out.println(s.length());
       }
    }
}
