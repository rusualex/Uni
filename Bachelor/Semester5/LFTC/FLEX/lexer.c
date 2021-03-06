%option noyywrap

%{
   #include <stdio.h>
   #include <stdlib.h>
   #include <string.h>
   int line = 1; // initialize to 1
   void ret_print(char *token_type);
   void yyerror();
%}

%x ML_COMMENT

alpha      [a-zA-Z]
digit      [0-9]
alnum      {alpha}|{digit}
print      [ -~]

ID         {alpha}+{alnum}*
ICONST      "0"|[0-9]{digit}*
FCONST      "0"|{digit}*"."{digit}+
CCONST      (\'{print}\')|(\'\\[nftrbv]\')
STRING      \"{print}*\"

%%

"//".*		{ printf("Eat up comment at line %d\n", line); } 

"/*"		{ printf("Eat up comment from line %d ", line); BEGIN(ML_COMMENT); }
<ML_COMMENT>"*/" 	{ printf("to line %d\n", line); BEGIN(INITIAL); }
<ML_COMMENT>[^*\n]+		
<ML_COMMENT>"*"			
<ML_COMMENT>"\n"	{ line += 1; }


"char"|"CHAR"          { ret_print("KEYWORD_CHAR"); }
"int"|"INT"            { ret_print("KEYWORD_INT"); }
"float"|"FLOAT"        { ret_print("KEYWORD_FLOAT"); }
"double"|"DOUBLE"      { ret_print("KEYWORD_DOUBLE"); }
"if"|"IF"              { ret_print("KEYWORD_IF"); }
"else"|"ELSE"          { ret_print("KEYWORD_ELSE"); }
"while"|"WHILE"	       { ret_print("KEYWORD_WHILE"); }
"for"|"FOR"	       { ret_print("KEYWORD_FOR"); }
"continue"|"CONTINUE"  { ret_print("KEYWORD_CONTINUE"); }
"break"|"BREAK"	       { ret_print("KEYWORD_BREAK"); }
"void"|"VOID"	       { ret_print("KEYWORD_VOID"); }
"return"|"RETURN"      { ret_print("KEYWORD_RETURN"); }
"public"|"private"|"protected"	{ ret_print("KEYWORD_ACCES"); }
"class"					{ ret_print("KEYWORD_CLASS"); }
"new"					{ ret_print("KEYWORD_NEW");}
"Scanner"				{ ret_print("KEYWORD_SCANNER");}
"hasNextInt"|"nextInt"|"hasNextDouble"|"nextDouble"	{ ret_print("KEYWORD_METHOD");}

"%"			{ ret_print("MODULOP"); }
"+"|"-"      { ret_print("ADDOP"); }
"*"	     { ret_print("MULOP"); }
"/"	     { ret_print("DIVOP"); }
"++"|"--"    { ret_print("INCR"); }
"||"         { ret_print("OROP"); }
"&&"	     { ret_print("ANDOP"); }
"!"	     { ret_print("NOTOP"); }
"=="|"!="    { ret_print("EQUOP"); }
">"|"<"|">="|"<="      { ret_print("RELOP"); }


"("      { ret_print("LPAREN"); }
")"      { ret_print("RPAREN"); }
"]"      { ret_print("LBRACK"); }
"["      { ret_print("RBRACK"); }
"{"      { ret_print("LBRACE"); }
"}"      { ret_print("RBRACE"); }
";"      { ret_print("SEMI"); }
"."      { ret_print("DOT"); }
","      { ret_print("COMMA"); }
"="      { ret_print("ASSIGN"); }
"&"      { ret_print("REFER"); }


{ID}         { ret_print("ID"); }
{ICONST}     { ret_print("ICONST"); }
{FCONST}     { ret_print("FCONST"); }
{CCONST}     { ret_print("CCONST"); }
{STRING}     { ret_print("STRING"); }


"\n"		   { line += 1; }
[ \t\r\f]+	   /* eat up whitespace */

.		   { yyerror("Unrecognized character"); }

%%

void ret_print(char *token_type){
   if((strlen(yytext)>=8) && (token_type=="ID"))
	{printf("Error: \"%s%s\" in line %d. Token = %s\n", yytext, " - name over 8 characters", line, yytext);
	exit(1);
	}
   printf("Token: %s\ttoken: %s\tline: %d\n", yytext, token_type, line);
}

void yyerror(char *message){
   printf("Error: \"%s\" in line %d. Token = %s\n", message, line, yytext);
   exit(1);
}

int main(int argc, char *argv[]){
   yyin = fopen(argv[1], "r");
   yylex();
   fclose(yyin);
   return 0;
}