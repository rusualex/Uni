using System;
using System.IO;

namespace Lab1
{
    class Program
    {
        static void Main(string[] args)
        {
            string fileName = "Lab1_c.txt";
            string path = Path.Combine(Environment.CurrentDirectory, @"../../../", fileName);
            FileReader fileReader = new FileReader(path);
            TokenRepo tokenRepo = new TokenRepo(fileReader.ReadText);
            InnerList innerList = new InnerList();
            Controller controller = new Controller(tokenRepo, innerList);
            controller.fipCreator();


            Console.ReadLine();
        }
    }
}
