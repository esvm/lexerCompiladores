/* Edjan Michiles - esvm */

package atividade1;

%%

/* N�o altere as configura��es a seguir */

%line
%column
%unicode
//%debug
%public
%standalone
%class Minijava
%eofclose

/* Insira as regras l�xicas abaixo */

%{
int qtdeID=0;
%}

%eof{
System.out.println("Quantidade de Identificadores encontrados: "+qtdeID);
%eof}

/* PADRÕES */
letter          = [A-Za-z]
digit           = [0-9]
integer         = {digit}+
alphanumeric    = {letter}|{digit}
identifier      = ({letter} | [_])({alphanumeric} | [_])*
linebrak        = \r|\n|\r\n
nolinebreak     = [^\r\n]
whitespace      = {linebrak} | [ \t\f]
operator        = ("&&") | ("<") | ("==") | ("!=") | ("\+") | ("-") | ("\*") | ("!")
delimiter       = (";") | ("\.") | (",") | ("=") | ("(") | (")") | ("{") | ("}") | ("[") | ("]")
comment1        = "/*" [^*] ~"*/" | "/*" "*"+ "/"
comment2        = "//" {nolinebreak}* {linebrak}?
comment         = {comment1} | {comment2}

%%

/* REGRAS */
{comment}               { System.out.println("Comentario"); }
"System.out.println"    { System.out.println("Token System.out.println"); }
"public"				{ System.out.println("Token public"); }
"static"				{ System.out.println("Token static"); }
"class"				    { System.out.println("Token class"); }
"extends"				{ System.out.println("Token extends"); }
"boolean"               { System.out.println("Token boolean"); }
"void"  				{ System.out.println("Token void"); }
"main"  				{ System.out.println("Token main"); }
"String"				{ System.out.println("Token String"); }
"int"				    { System.out.println("Token int"); }
"while" 				{ System.out.println("Token while"); }
"if"				    { System.out.println("Token if"); }
"else"  				{ System.out.println("Token else"); }
"return"				{ System.out.println("Token return"); }
"length"				{ System.out.println("Token length"); }
"true"  				{ System.out.println("Token true"); }
"false" 				{ System.out.println("Token false"); }
"this"  				{ System.out.println("Token this"); }
"new"				    { System.out.println("Token new"); }
{identifier}            { qtdeID++; System.out.println("Token ID ("+yytext()+")"); }
{integer}               { System.out.println("Token INT ("+yytext()+")"); }
{whitespace}            { /* Ignorar whitespace. */ }
{operator}              { System.out.println("Token operator ("+yytext()+")"); }
{delimiter}             { System.out.println("Token delimiter ("+yytext()+")"); }
.                       { System.out.println("Illegal char, '" + yytext() +
                         "' line: " + yyline + ", column: " + yycolumn); }

%
    
/* Insira as regras l�xicas no espa�o acima */     
     
. { throw new RuntimeException("Caractere ilegal! '" + yytext() + "' na linha: " + yyline + ", coluna: " + yycolumn); }
