%option noyywrap

%{
    #include <stdio.h>
    #include <stdlib.h>
    #include <string.h>
    #include "parser.tab.h"
    extern FILE *yyin;
    extern FILE *yyout;
    
    int lineNumber = 1; // initialize to 1
    void ret_print(char *token_type);
    void yyerror();
%}


alpha       [a-zA-Z]
digit       [0-9]
alnum       {alpha}|{digit}
underscore  ["_"]

ID          {alpha}+{alnum}*{underscore}*{alnum}+*
ICONST      "0"|[0-9]{digit}*
NCONST      "-"[0-9]{digit}*
FCONST      "0"|{digit}*"."{digit}+


%%


"using"|"USING"         { return USING; }
"system"|"SYSTEM"       { return SYSTEM; }
"class"|"CLASS"         { return CLASS; }
"return"|"RETURN"       { return RETURN; }
"new"|"NEW"             { return NEW; }
"writeline"|"WRITELINE" { return WRITELINE; }
"ToInt32"                 { return TOINT32;}         
"Console"                   {return CONSOLE;}
"Convert"                   {return CONVERT;}
"Readline"                  {return READLINE; }
"namespace"                 {return NAMESPACE; }

"public"|"PUBLIC"       { return PUBLIC; }
"internal"|"INTERNAL"   { return INTERNAL; }
"protected"|"PROTECTED" { return PROTECTED; }
"private"|"PRIVATE"     { return PRIVATE; }
"static"|"STATIC"     { return STATIC; }


"char"|"CHAR"           { return CHAR; }
"int"|"INT"             { return INT; }
"float"|"FLOAT"         { return FLOAT; }
"double"|"DOUBLE"       { return DOUBLE; }


"if"|"IF"               { return IF; }
"while"|"WHILE"         { return WHILE; }
"for"|"FOR"             { return FOR; }


"+"                     { return PLUS; }
"-"                     { return MINUS; }
"*"                     { return MUL; }
"/"                     { return DIVIDER; }
"++"|"--"               { return INCR; }
"||"                    { return OR; }
"&&"                    { return AND; }
"=="                    { return EQUALS;}
"!="                    { return NOTEQUALS; }
">"                     { return GREATER;}
"< "                    { return SMALLER; }


"("             { return LPAREN; }
")"             { return RPAREN; }
"["             { return LBRACK; }
"]"             { return RBRACK; }
"{"             { return LBRACE; }
"}"             { return RBRACE; }
";"             { return SEMI; }
"."             { return DOT; }
","             { return COMMA; }
"="             { return ASSIGN; }


{ID}            {strcpy(yylval.idVal,yytext); return ID;}
{ICONST}         {strcpy(yylval.constVal,yytext); return CONST;}
{NCONST}         {strcpy(yylval.constVal,yytext); return CONST;}
{FCONST}         {strcpy(yylval.constVal,yytext); return CONST;}


"\n"            { lineNumber += 1; }
[ \t\r\f]+          /* eat up whitespace */

.               { yyerror("Unrecognized character"); }

%%