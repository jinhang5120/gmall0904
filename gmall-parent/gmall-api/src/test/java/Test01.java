import jodd.util.StringUtil;

public class Test01 {
    public static void main(String[] args) {
        String replace = StringUtil.replace("abcdefg", "cde", "---");
        System.out.println("replace = " + replace);
    }
}
