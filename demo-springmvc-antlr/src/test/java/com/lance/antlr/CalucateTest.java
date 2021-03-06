package com.lance.antlr;

import java.io.IOException;
import java.io.StringReader;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.TokenSource;
import org.antlr.v4.runtime.TokenStream;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Ignore;
import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.lance.antlr.ExprParser.EquationContext;

public class CalucateTest {
	Logger logger = LogManager.getLogger(getClass());

	@Test
	public void add() {
		String addString = "4 > 1";
		try {
			CharStream inputCharStream = new ANTLRInputStream(new StringReader(addString));
			TokenSource tokenSource = new ExprLexer(inputCharStream);
			TokenStream tokenStream = new CommonTokenStream(tokenSource);
			ExprParser parser = new ExprParser(tokenStream);
			parser.addErrorListener(new BaseErrorListener(){
				 public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine, String msg, RecognitionException e) {
			            throw new IllegalStateException("failed to parse at line " + line + " due to " + msg, e);
			        }
			});
			
			EquationContext context = parser.equation();
			logger.info("add: {}", JSON.toJSONString(context));
		} catch (IOException e) {
			e.printStackTrace();
		}
        //TokenSource tokenSource = new Exprlex(inputCharStream);
	}
	
	@Test
	@Ignore
	public void field() {
		try {
			CharStream charStream = new ANTLRInputStream(getClass().getResourceAsStream("/ex.field"));
			TokenSource tokenSource = new FieldLexer(charStream);
			TokenStream tokenStream = new CommonTokenStream(tokenSource);
			FieldParser fieldParser = new FieldParser(tokenStream);
			
			fieldParser.addErrorListener(new BaseErrorListener(){
				 public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine, String msg, RecognitionException e) {
			            throw new IllegalStateException("failed to parse at line " + line + " due to " + msg, e);
			        }
			});
			fieldParser.field();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
