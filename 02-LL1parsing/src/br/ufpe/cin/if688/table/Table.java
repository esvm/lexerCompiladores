package br.ufpe.cin.if688.table;


import br.ufpe.cin.if688.parsing.analysis.*;
import br.ufpe.cin.if688.parsing.grammar.*;
import java.util.*;


public final class Table {
	private Table() {    }

	public static Map<LL1Key, List<GeneralSymbol>> createTable(Grammar g) throws NotLL1Exception {
        if (g == null) throw new NullPointerException();

        Map<Nonterminal, Set<GeneralSymbol>> first =
            SetGenerator.getFirst(g);
        Map<Nonterminal, Set<GeneralSymbol>> follow =
            SetGenerator.getFollow(g, first);

        Map<LL1Key, List<GeneralSymbol>> parsingTable = 
            new HashMap<LL1Key, List<GeneralSymbol>>();

        for (Production prod: g.getProductions()) {
            for (GeneralSymbol symbol: first.get(prod.getNonterminal())) {
               if (symbol instanceof Terminal) {
                   if(parsingTable.get(new LL1Key(prod.getNonterminal(), symbol)) == null || prod.getProduction().contains(symbol))
                           parsingTable.put(new LL1Key(prod.getNonterminal(), symbol), prod.getProduction());
               }
            }
            if(first.get(prod.getNonterminal()).contains(SpecialSymbol.EPSILON)) {
                for (GeneralSymbol symbol: follow.get(prod.getNonterminal())) {
                    parsingTable.put(new LL1Key(prod.getNonterminal(), symbol), prod.getProduction());
                }
            }
        }

        return parsingTable;
    }
}
