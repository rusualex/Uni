using Lab1.BinaryTree;
using System;
using System.Collections.Generic;

namespace Lab1
{
    class Controller
    {
        private TokenRepo tokenRepo;
        private InnerList innerList;

        public Controller(TokenRepo tokenRepo, InnerList innerList)
        {
            this.tokenRepo = tokenRepo;
            this.innerList = innerList;
        }


        public void fipCreator() {
            List<FIP> fipTable = new List<FIP> { };
            Tree binaryTree = new Tree();
            List<string> tokenList = tokenRepo.TokenList;
            List<string> errorList = new List<string> { };
            int symbolCounter = 100;
            int treeNodeCounter = 0;
            int line = 0;

            TS auxValue = null;

            

            tokenList.ForEach(token =>
            {
                if (innerList.Tokens.FindIndex(innerListElem => innerListElem.Equals(token)) >= 0)
                    fipTable.Add(new FIP(innerList.Tokens.FindIndex(innerListElem => innerListElem.Equals(token)) + "", "-"));
                else
                {
                    if(binaryTree.containsNode(new TS(token)))
                    {
                        if (innerList.checkIfCONST(token))
                            fipTable.Add(new FIP("1", binaryTree.getNode(new TS(token)).Value.CodTS));
                        else if (innerList.checkIfID(token))
                        {
                            fipTable.Add(new FIP("0", binaryTree.getNode(new TS(token)).Value.CodTS));
                            if (innerList.checkIfIDGreater(token))
                            {
                                Console.WriteLine("Error at:    "+token);
                            }
                        }

                    }
                    else
                    {
                        if (innerList.checkIfCONST(token))
                        {
                            fipTable.Add(new FIP("1", symbolCounter.ToString()));
                            binaryTree.Add(new TS(symbolCounter.ToString(), token));
                        }
                        else if (innerList.checkIfID(token))
                        {
                            fipTable.Add(new FIP("0", symbolCounter.ToString()));
                            binaryTree.Add(new TS(symbolCounter.ToString(), token));
                            if (innerList.checkIfIDGreater(token))
                            {
                                Console.WriteLine("Error at:    " + token);
                            }
                        }
                        symbolCounter++;
                    }
                }
                //if (x.Equals("ID")) addID("ID");
                //if (x.Equals("CONST")) addCONST("CONST");
                //    if (innerList.Tokens.FindIndex(innerListElem => innerListElem.Equals(token)) >= 0)
                //        fipTable.Add(new FIP(innerList.Tokens.FindIndex(innerListElem => innerListElem.Equals(token)) + "", "-"));
                //    else
                //    {
                //        
                //        else
                //        {
                //            bool OK = false;
                //            for (int k = 0; k < treeNodeCounter; k++)
                //            {
                //                //Console.WriteLine("debuug   "+tree.get(k).ValoareTS + " " + token);
                //                if (tree.get(k).ValoareTS.Equals(token))
                //                {
                //                    if (innerList.checkIfCONST(token))
                //                    {
                //                        fipTable.Add(new FIP("1", tree.get(k).CodTS));
                //                        //Console.WriteLine("constantaaaaa");
                //                    }
                //                    else if (innerList.checkIfID(token))
                //                    {

                //                        if (innerList.checkIfIDGreater(token)) errorList.Add("Line " + line + " ID :" + token + " greater than 8 chars");
                //                        fipTable.Add(new FIP("0", tree.get(k).CodTS));
                //                        //Console.WriteLine("iddddddddd");
                //                    }
                //                    OK = true;
                //                }
                //            }
                //            if(OK == false)
                //            {
                //                if (innerList.checkIfCONST(token))
                //                {
                //                    fipTable.Add(new FIP("1", symbolCounter.ToString()));
                //                    tree.put(treeNodeCounter, new TS(symbolCounter.ToString(), token));
                //                }
                //                else if (innerList.checkIfID(token))
                //                {
                //                    if (innerList.checkIfIDGreater(token)) errorList.Add("Line " + line + " ID :" + token + " greater than 8 chars");
                //                    fipTable.Add(new FIP("0", symbolCounter.ToString()));
                //                    tree.put(treeNodeCounter, new TS(symbolCounter.ToString(), token));
                //                }
                //                symbolCounter++;
                //                treeNodeCounter++;
                //            }
                //        };
                //    }
                //    line++;

                //});
                //Console.WriteLine("Tokens:");
                //tokenList.ForEach(token => { Console.WriteLine(token); });
                //Console.WriteLine("FIP:");
            });
            Console.WriteLine();
            Console.WriteLine("TS:");
            Console.WriteLine();
            binaryTree.Inorder();
                //binaryTree.traverseInOrder(new Node(new TS("100", tokenList[])));
            binaryTree.nodes.ForEach(x => { Console.WriteLine(x.Value.CodTS+"   "+x.Value.ValoareTS); });
            Console.WriteLine();
            Console.WriteLine("FIP:");
            Console.WriteLine();
            fipTable.ForEach(x => { Console.WriteLine(x.CodFIP + "   TS:    " + x.TsFIP); });
        }
               


        public void analizor()
        {
            Console.WriteLine("Tabela FIP");

            Console.WriteLine("Tabela TS");

            Console.WriteLine("Errori la urmatoarele linii");
        }
    }
}
