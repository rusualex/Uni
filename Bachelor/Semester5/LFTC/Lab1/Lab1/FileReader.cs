using System;
using System.Collections.Generic;
using System.Text;

namespace Lab1
{
    public class FileReader
    {
        private string readText;
        public string ReadText
        {
            get { return readText; }
        }
        
        public FileReader(string filePath)
        {
            try
            {
                readText = System.IO.File.ReadAllText(filePath);
            }
            catch (Exception e)
            {
                throw e;
            }
        }

    }
}
