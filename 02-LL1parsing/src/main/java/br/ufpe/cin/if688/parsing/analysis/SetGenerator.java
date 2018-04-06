package br.ufpe.cin.if688.parsing.analysis;

import java.util.*;

import br.ufpe.cin.if688.parsing.grammar.*;


public final class SetGenerator {

    public static Map<Nonterminal, Set<GeneralSymbol>> getFirst(Grammar g) {
        
    	if (g == null) throw new NullPointerException("g nao pode ser nula.");
        
    	Map<Nonterminal, Set<GeneralSymbol>> first = initializeNonterminalMapping(g);
        for (Nonterminal item: first.keySet()) {
            first.put(item, firstOf(item, g));
        }
        return first;
    }

    public static Set<GeneralSymbol> firstOf(Nonterminal item, Grammar g) {
        Set<GeneralSymbol> firstList = new HashSet<GeneralSymbol>();

        for (Production item2: g.getProductions()) {
            if (item2.getNonterminal().getName().equals(item.getName())) {
                for (GeneralSymbol symbol: item2.getProduction()) {
                    if (symbol instanceof Terminal || symbol instanceof SpecialSymbol ) {
                        firstList.add(symbol);
                        break;
                    }
                    else {
                        boolean contemEpson = false;
                        for (GeneralSymbol symbol2: firstOf((Nonterminal) symbol, g)) {
                            firstList.add(symbol2);
                            if (symbol2 instanceof SpecialSymbol) {
                                contemEpson = true;
                            }
                        }
                        if (!contemEpson) {
                            firstList.remove(SpecialSymbol.EPSILON);
                            break;
                        }
                    }
                }
            }
        }

        return  firstList;
    }

    public static Map<Nonterminal, Set<GeneralSymbol>> getFollow(Grammar g, Map<Nonterminal, Set<GeneralSymbol>> first) {
        if (g == null || first == null)
            throw new NullPointerException();
                
        Map<Nonterminal, Set<GeneralSymbol>> follow = initializeNonterminalMapping(g);
        for (Nonterminal item: follow.keySet()) {
            follow.put(item, followOf(item, g, first));
        }

        return follow;
    }

    private static Set<GeneralSymbol> followOf(Nonterminal item, Grammar g, Map<Nonterminal, Set<GeneralSymbol>> first) {
        Set<GeneralSymbol> followList = new HashSet<GeneralSymbol>();

        for (Production production: g.getProductions()) {
            if (production.getNonterminal() != item) {
                for (GeneralSymbol symbol: production.getProduction()) {
                    if (symbol instanceof  Nonterminal && ((Nonterminal) symbol).getName().equals(item.getName())) {

                        int indexOf = production.getProduction().indexOf(symbol);
                        if (indexOf + 1 < production.getProduction().size()) {
                            GeneralSymbol next = production.getProduction().get(production.getProduction().indexOf(symbol) + 1);
                            if (next instanceof Terminal) {
                                followList.add(next);
                                break;
                            }
                            else {
                                Set<GeneralSymbol> firsts = first.get(next);
                                for (GeneralSymbol e: firsts) {
                                    if(!(e instanceof SpecialSymbol)) {
                                        followList.add(e);
                                    }
                                }
                                if (firsts.contains(SpecialSymbol.EPSILON)) {
                                    if(indexOf + 2 < production.getProduction().size()) {
                                        GeneralSymbol next2 = production.getProduction().get(production.getProduction().indexOf(symbol) + 2);
                                        if (next2 instanceof Terminal) {
                                            followList.add(next2);
                                            break;
                                        }
                                        else {
                                            for (GeneralSymbol temp: followOf((Nonterminal) next, g, first)) {
                                                followList.add(temp);
                                            }
                                        }
                                    }
                                    else {
                                        for (GeneralSymbol temp: followOf(production.getNonterminal(), g, first)) {
                                            followList.add(temp);
                                        }
                                    }

                                }
                                break;
                            }
                        }
                        else {
                            for (GeneralSymbol temp: followOf(production.getNonterminal(), g, first)) {
                                followList.add(temp);
                            }
                        }
                    }
                }
            }
            else
                continue;
        }
        if (g.getStartSymbol() == item)
            followList.add(SpecialSymbol.EOF);
        return followList;
    }

    //método para inicializar mapeamento nãoterminais -> conjunto de símbolos
    private static Map<Nonterminal, Set<GeneralSymbol>>
    initializeNonterminalMapping(Grammar g) {
    Map<Nonterminal, Set<GeneralSymbol>> result = 
        new HashMap<Nonterminal, Set<GeneralSymbol>>();

    for (Nonterminal nt: g.getNonterminals())
        result.put(nt, new HashSet<GeneralSymbol>());

    return result;
}

} 
