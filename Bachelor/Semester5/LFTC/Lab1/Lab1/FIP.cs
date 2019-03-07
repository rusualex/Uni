using System;
using System.Collections.Generic;
using System.Text;

namespace Lab1
{
    class FIP
    {
        private string codFIP;
        private string tsFIP;

        public FIP(string codFIP, string tsFIP)
        {
            this.codFIP = codFIP;
            this.tsFIP = tsFIP;
        }

        public string TsFIP { get => tsFIP; set => tsFIP = value; }
        public string CodFIP { get => codFIP; set => codFIP = value; }
    }
}
