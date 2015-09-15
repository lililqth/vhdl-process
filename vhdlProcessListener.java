import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import org.stringtemplate.v4.*;
import java.util.*;
public class vhdlProcessListener extends vhdlBaseListener
{
    private vhdlParser parser;
    private String dumpText;
    ParseTreeProperty<String> dumpMark = new ParseTreeProperty<String>();
    String getDump(ParseTree ctx) {return dumpMark.get(ctx);}
    void setDump(ParseTree ctx, String mark) {dumpMark.put(ctx, mark);}
    private String findDuplicate(vhdlParser.Sequence_of_statementsContext a, vhdlParser.Sequence_of_statementsContext b)
    {
        int sizeOfA = a.getChildCount()+1;
        int sizeOfB = b.getChildCount()+1;
        int[][] temp = new int[sizeOfA][sizeOfB];
        String[] textA = a.getText().split(";");
        String[] textB = b.getText().split(";");
        int max = 0, maxX = 0, maxY = 0;
        for (int i=0; i<sizeOfB; i++)
        {
            temp[0][i] = 0;
        }
        for (int i=0; i<sizeOfA; i++)
        {
            temp[i][0] = 0;
        }
        for (int i=1; i<sizeOfA; i++)
        {
            for (int j=1; j<sizeOfB; j++)
            {
                if (textA[i-1].equals(textB[j-1]))
                {
                    temp[i][j] = temp[i-1][j-1] + 1;
                    if (max < temp[i][j])
                    {
                        maxX = i-1;
                        maxY = j-1;
                    }
                }
                else
                {
                    temp[i][j] = 0;
                }
            }
        }
        String ans = "";
        while (maxX>=0 && maxY>=0)
        {
            if (textA[maxX].equals(textB[maxY]))
            {
                ans += textA[maxX] + ";" + "\n";
                maxX--;
                maxY--;
            }
            else
            {
                break;
            }
        }
        this.dumpText = ans;
        return ans;
    }

    public vhdlProcessListener(vhdlParser parser)
    {
        this.parser = parser;
    }

    @Override public void enterProcess_statement(vhdlParser.Process_statementContext ctx)
    {
        TokenStream tokens = parser.getTokenStream();
        String ProcessName = tokens.getText(ctx.label_colon());
        System.out.println("方法名称：" + ProcessName);
    }
    @Override public void enterIf_statement(vhdlParser.If_statementContext ctx) {
        int childCount = ctx.getChildCount();
        List<vhdlParser.Sequence_of_statementsContext> list = ctx.sequence_of_statements();
        String dump = "";
        for (int i=1; i<list.size(); i++)
        {
            dump = findDuplicate(list.get(0), list.get(i));
        }
        setDump(ctx, dump);
    }
    @Override public void exitIf_statement(vhdlParser.If_statementContext ctx) {
        STGroup g = new STGroupFile("test.stg");
        ST st = g.getInstanceOf("IF_STATEMENT");
        st.add("condition", ctx.condition().get(0).getText());
        st.add("sequence_of_statements1", getDump(ctx.sequence_of_statements().get(0)));
        st.add("sequence_of_statements2", getDump(ctx.sequence_of_statements().get(1)));
        st.add("sequence_of_statements3", getDump(ctx));
        System.out.println(st.render());
    }
    @Override public void enterSequence_of_statements(vhdlParser.Sequence_of_statementsContext ctx) {
        if (ctx.getParent() instanceof vhdlParser.If_statementContext)
        {
            int childCount = ctx.getChildCount();
            for (int i=0; i<childCount; i++)
            {
                String text = ctx.getChild(i).getText();
                if (!this.dumpText.contains(text))
                {
                    if (getDump(ctx)!=null)
                    {
                        setDump(ctx, getDump(ctx)+"\n"+text);
                    }
                    else
                    {
                        setDump(ctx, text);
                    }
                }
            }
        }
    }
    @Override public void exitSequence_of_statements(vhdlParser.Sequence_of_statementsContext ctx) {

    }
}