import org.stringtemplate.v4.*;
import java.util.*;
public class Hello {
    public static void main(String[] args) {
        STGroup g = new STGroupFile("test.stg");
        ST st = g.getInstanceOf("IF_STATEMENT");
        st.add("condition", "B_0(8) /= '0'");
        st.add("sequence_of_statements1", "B := add_temp(7 downto 0);");
        st.add("sequence_of_statements2", "B := \"11111111\";");
        System.out.println(st.render());
    }
}
