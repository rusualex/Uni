%token IDENTIFIER CONSTANT
%token INCLUDE STDIO_H MAIN SCANF PRINTF
%token SEMI INCR DECR COMMA ASSIGN
%token RETURN
%token INT



%start translation_unit
%%

translation_unit
	:	include main_function 
	|	main_function 
;

include
	:	INCLUDE STDIO_H
;

main_function
	:	INT MAIN '(' ')' '{' statements '}'
;

statements
	:	declarations other_statements
	|	other_statements
;


other_statements
	:	/* empty */
	|	assignments other_statements
	|	increments other_statements
	|	read other_statements
	|	write other_statements
	|	RETURN expression SEMI other_statements
;

read
	:	SCANF '(' IDENTIFIER ')' SEMI
;

write
	:	PRINTF '(' IDENTIFIER ')' SEMI
;

declarations
	:	declaration SEMI 
	|	declaration SEMI declarations
;

declaration
	:	INT integers
;

integers
	:	IDENTIFIER COMMA integers
	|	IDENTIFIER
;

assignments
	:	assignment SEMI
	|	assignment SEMI assignments
;

assignment
	:	IDENTIFIER ASSIGN expression	
;

increments
	:	IDENTIFIER INCR SEMI	{ $$ = $1 + 1; }
	|	IDENTIFIER DECR SEMI	{ $$ = $1 - 1; }
;

expression
	:	literal
	|	unary
	|	binary
	|	grouping
;

literal
	:	IDENTIFIER	{ $$ = $1; }
	|	CONSTANT	{ $$ = $1; }
;

unary
	:	'-' expression	{ $$ = -$1; }
;

binary
	: expression operator expression	{ $$ = $1 $2 $3; }
;

grouping
	:	'(' expression ')'	{ $$ = ($1); }
;

operator
	:	'+' 
	|	'-' 
	|	'*' 
	|	'/' 
	|	'%'
;

%%
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
extern FILE *yyin;
extern FILE *yyout;
extern int yylex();
void yyerror();

extern char yytext[];
extern int column;

void yyerror(char const *s)
{
	fflush(stdout);
	printf("\n%*s\n%*s\n", column, "^", column, s);
}

int main (int argc, char *argv[]){

	// parsing
	int flag;
	yyin = fopen(argv[1], "r");
	flag = yyparse();
	fclose(yyin);
	
	// symbol table dump
	
	return flag;
}