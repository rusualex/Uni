using System;
using System.Collections.Generic;
using System.Text;

namespace Lab1
{
    class InnerList
    {
        private List<string> tokens = new List<string> { };

        public List<string> Tokens { get => tokens; set => tokens = value; }

        public InnerList()
        {
            tokens = populateTokens();
        }

        public bool checkIfCONST (string possibleConst)
        {
            if(possibleConst[0].Equals('"')&&possibleConst[possibleConst.Length-1].Equals('"'))
               return true;
            
            string symbols="";
            for (int i = 0; i <= possibleConst.Length-1; i++)
                  if(!Char.IsNumber(possibleConst[i]))
                      symbols += possibleConst[i];

            if (symbols.Length == 0) return true;
            else if (symbols.Length == 1 && symbols[0] == '-' || symbols[0] == '.') return true;
            else if (symbols.Length == 2 && symbols[0] == '-' && symbols[1] == '.') return true;
            else return false;
        }

        public bool checkIfID (string possibleID)
        {
            return !this.checkIfCONST(possibleID);
        }

        public bool checkIfIDGreater (string possibleID)
        {
            return possibleID.Length > 8;
        }



        private List<string> populateTokens()
        {
            List<string> ourTokens = new List<string> {"ID","CONST","{","}","(",")","[","]","public","private","protected","boolean","int",",",";","=","+","-","%","/","*","++","+=","void","String","!=","while","Scanner","hasNextInt","nextInt","hasNextDouble", "nextDouble", "for","if","return","true","false","else","==","<=",">=","<",">","double","class","new",".", "System","in"};
            return ourTokens;
        }
    }
}
