import org.antlr.runtime.*;
import java.io.*;
import ast.*;
import type.*;
import semantics.*;
import ir.*;
import java.util.*;

public class Compiler {
	public static void main (String[] args) throws Exception {
		ANTLRInputStream input;

		if (args.length == 0 ) {
			System.out.println("Usage: Test filename.ul");
			return;
		}
		else {
			input = new ANTLRInputStream(new FileInputStream(args[0]));
		}

		// The name of the grammar here is "ulNoActions",
		// so ANTLR generates ulNoActionsLexer and ulNoActionsParser
		simpleLexer lexer = new simpleLexer(input);
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		simpleParser parser = new simpleParser(tokens);

		try {
			Program p = parser.program();
            TypeVisitor v = new TypeCheckingVisitor();
            String progName = args[0];
            progName = progName.substring(progName.lastIndexOf('/')+1,progName.lastIndexOf('.'));
            //System.out.println("program name: " + progName);
            TempVisitor irv = new IRGenerationVisitor(progName);
            p.accept(v);
            p.accept(irv);
            CodeGenerator codegen = new CodeGenerator(progName, irv.getIRFuncs());
		}
		catch (RecognitionException e )	{
			// A lexical or parsing error occured.
			// ANTLR will have already printed information on the
			// console due to code added to the grammar.  So there is
			// nothing to do here.
		}
		catch (Exception e) {
		//	System.out.println(e);
			e.printStackTrace();
		}
	}
}
