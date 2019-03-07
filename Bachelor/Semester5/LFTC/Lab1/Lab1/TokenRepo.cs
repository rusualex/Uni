using System;
using System.Collections.Generic;
using System.Text;

namespace Lab1
{
    public class TokenRepo
    {
        private readonly List<string> tokenList;
        public List<string> TokenList
        {
            get { return tokenList; }
        }


        public TokenRepo (string text)
        {
            tokenList = Splitter(text);
        }

        private List<string> Splitter(string text)
        {
            List<string> ourWords = new List<string> { };
            char[] delimiterChars = { ' ', '\n', '\t' ,'\r'};
            //System.Console.WriteLine($"Original text: '{text}'");
            string[] words = text.Split(delimiterChars);

            foreach (var word in words)
            {
                if (word != "")
                    ourWords.Add(word);
            }
            return ourWords;
        }

        
    }
}
