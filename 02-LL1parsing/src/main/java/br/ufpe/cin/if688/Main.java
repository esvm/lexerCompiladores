package br.ufpe.cin.if688;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import br.ufpe.cin.if688.parser.ErrorOnParseException;
import br.ufpe.cin.if688.parser.ParseTree;
import br.ufpe.cin.if688.parser.Parser;
import br.ufpe.cin.if688.parser.ParserGenerator;
import br.ufpe.cin.if688.parser.ParserUtils;
import br.ufpe.cin.if688.parsing.analysis.*;
import br.ufpe.cin.if688.parsing.grammar.*;
import br.ufpe.cin.if688.table.NotLL1Exception;
import br.ufpe.cin.if688.table.LL1Key;
import br.ufpe.cin.if688.table.Table;


public class Main {

	public static void main(String[] args) throws NotLL1Exception, ErrorOnParseException {
		/*
		 * Gramatica de exemplo
		 * S -> aABe
		 * A -> bK
		 * K -> bcK | ε
		 * B -> d
		 */

		Nonterminal S = new Nonterminal("S");
		Nonterminal A = new Nonterminal("A");
		Nonterminal B = new Nonterminal("B");
		Nonterminal K = new Nonterminal("K");

		Terminal a = new Terminal("a");
		Terminal b = new Terminal("b");
		Terminal c = new Terminal("c");
		Terminal d = new Terminal("d");
		Terminal e = new Terminal("e");

		List<GeneralSymbol> prodS = new ArrayList<GeneralSymbol>();
		prodS.add(a);
		prodS.add(A);
		prodS.add(B);
		prodS.add(e);

		List<GeneralSymbol> prodA = new ArrayList<GeneralSymbol>();
		prodA.add(b);
		prodA.add(K);

		List<GeneralSymbol> prodK1 = new ArrayList<GeneralSymbol>();
		prodK1.add(b);
		prodK1.add(c);
		prodK1.add(K);

		List<GeneralSymbol> prodK2 = new ArrayList<GeneralSymbol>();
		prodK2.add(SpecialSymbol.EPSILON);

		List<GeneralSymbol> prodB = new ArrayList<GeneralSymbol>();
		prodB.add(d);

		Production pS = new Production(S, prodS);
		Production pA = new Production(A, prodA);
		Production pK1 = new Production(K, prodK1);
		Production pK2 = new Production(K, prodK2);
		Production pB = new Production(B, prodB);

		Collection<Production> collection = new ArrayList<Production>();
		collection.add(pS);
		collection.add(pA);
		collection.add(pK1);
		collection.add(pK2);
		collection.add(pB);

		Grammar g = new Grammar(collection, S);

		Map<Nonterminal, Set<GeneralSymbol>> first = SetGenerator.getFirst(g);
		Map<Nonterminal, Set<GeneralSymbol>> follow = SetGenerator.getFollow(g, first);
//		Map<LL1Key, List<GeneralSymbol>> table = Table.createTable(g);
//		Parser parser = ParserGenerator.createParser(g);
//		ParseTree parseTree = ParserUtils.parseSequence(parser, example);
		
		System.out.println("Exemplo 1:\n"
						  + "A -> aB\n"
						  + "B -> cC\n"
						  + "C -> d");		
		System.out.println("Conjunto first: " + first.toString());
//		System.out.println("Conjunto follow: " + follow.toString());
//		System.out.println("Tabela de parsing: " + table.toString());
//		System.out.println("Exemplo de parsing: " + parseTree.toString() + "\n");
		

	}

}
