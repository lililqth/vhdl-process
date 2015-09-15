import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import java.io.*;
public class run{
    public static void main(String[] args) throws Exception{
        FileInputStream stream = new FileInputStream("recieveBuffer.vhd");
        ANTLRInputStream input = new ANTLRInputStream(stream);
        vhdlLexer lexer = new vhdlLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        vhdlParser parser = new vhdlParser(tokens);
        ParseTree tree= parser.design_file();
        ParseTreeWalker walker = new ParseTreeWalker();
       // vhdlBaseListener listener = new vhdlBaseListener(parser);
       // walker.walk(listener, tree);
        vhdlProcessListener listener = new vhdlProcessListener(parser);
        walker.walk(listener, tree);
        System.out.println(); // print a \n after translation
        //vhdlBaseVisitor visitor = new vhdlBaseVisitor();
        //visitor.visit(tree);
        /*
        ParseTreePattern p = parser.compileParseTreePattern("<ID> = <expr>;", vhdlParser.RULE_design_file);
        ParseTreeMatch m = p.match(t);
        if ( m.succeeded() ) {
            System.out.println("Found");
        }
        else{
            System.out.println("Not Found");
        }
        */
    }
}