%{
    #include <stdio.h>
    #include <stdlib.h>
    #include <string.h>
    extern FILE *yyin;
    extern int lineNumber;
    extern int yylex();
    void yyerror();
    char dataSegment[2000];
    char codeSegment[5000];
    void addToDataSegment(char *s);
    void addToCodeSegment(char *s);
    char varname[10];
%}

%union {
	char idVal[20];
  int constVal;
}

/* token definition */
%token<idVal> ID
%token<constVal> CONST
%token USING SYSTEM NAMESPACE CLASS RETURN NEW WRITELINE TOINT32 CONSOLE CONVERT READLINE
%token PUBLIC INTERNAL PROTECTED PRIVATE STATIC
%token PLUS MINUS MUL DIVIDER EQUALS NOTEQUALS GREATER SMALLER INCR
%token LPAREN RPAREN LBRACK RBRACK LBRACE RBRACE SEMI DOT COMMA ASSIGN
%token IF FOR WHILE
%token OR AND
%token INT STRING DOUBLE FLOAT CHAR




%start program

/* expression priorities and rules */

%%

program: namespaceDefinition;
namespaceDefinition: NAMESPACE ID LBRACE listOfClasses RBRACE;
listOfClasses: class | class listOfClasses;
class: CLASS ID LBRACE listOfMethods RBRACE;
listOfMethods: method | method listOfMethods;
method: access staticModifier type ID 
                    LPAREN  RPAREN LBRACE listOfInstructions RBRACE;
access: PUBLIC | INTERNAL | PROTECTED | PRIVATE | ;
staticModifier: STATIC | ;
type: DOUBLE | STRING | INT | ;
listOfParameters:  type ID | type ID COMMA listOfParameters | ;
listOfInstructions: instruction | instruction listOfInstructions;
instruction: if | for | return | atr | ;
operator: PLUS | MUL | MINUS | DIVIDER | ASSIGN;
expr: ID | ID LBRACK expr RBRACK | CONST | expr operator expr;
atr: type ID ASSIGN expr SEMI {
  //declaration
  char *var = (char *)malloc(sizeof(char)*100);
							sprintf(var, "%s dw 10\n", $2);
							addToDataSegment(var);
							free(var);

  // assign
  char *var = (char *)malloc(sizeof(char)*100);
							sprintf(var, "mov ax, %s\n", $2);
							addToCodeSegment(var);
							sprintf(var, "mov %s, ax\n", $4);
							addToCodeSegment(var);
							free(var);
}  | type LBRACK RBRACK ID EQUALS NEW type LBRACK expr RBRACK SEMI;

if: IF LPAREN ListOfConditions RPAREN LBRACE listOfInstructions RBRACE;
ListOfConditions: cond;
cond: expr comparer expr;
comparer: EQUALS | GREATER | SMALLER | ASSIGN | NOTEQUALS ;
for: FOR LPAREN atr ListOfConditions SEMI expr RPAREN LBRACE listOfInstructions RBRACE;
nextStep: ID incOperator;
incOperator: INCR;
return: RETURN expr; | RETURN ID LPAREN callParameterList RPAREN SEMI;
callParameterList: expr | expr COMMA callParameterList;

%%

void addToDataSegment(char *s) {
	strcat(dataSegment, s);		
}
void addToCodeSegment(char *s) {
	strcat(codeSegment, s);		
}

void yyerror ()
{
  fprintf(stderr, "Syntax error at line %d\n", lineNumber);
  exit(1);
}

int main (int argc, char *argv[]){

    // parsing
    int flag;



    FILE *f = fopen("output.out", "w");
      if(f == NULL) {
      perror("Mayday -> file out.out has failed.");
      exit(1);
	  }

    char begin[100] = "assume cs:code, ds:data\n data SEGMENT\n";
                      
    fwrite(begin, strlen(begin), 1, f);

    yyin = fopen(argv[1], "r");
    flag = yyparse();
    fclose(yyin);
    
    fwrite(dataSegment, strlen(dataSegment), 1, f);

    char endDataBeginCode[100] = "data ENDS\n code SEGMENT\n start:\n mov ax, data\nmov ds, ax\n";

    fwrite(endDataBeginCode, strlen(endDataBeginCode),1,f);



    fclose(f);

    return flag;
}