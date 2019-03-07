echo "Executing flexer"
flex lexer.l
echo "Executing parser"
bison parser.y
echo "Executing flexer again"
flex lexer.l

echo "Compiling"
gcc -w -o gimmePASS parser.tab.c lex.yy.c

echo "Runing on $1"
./gimmePASS "$1"

