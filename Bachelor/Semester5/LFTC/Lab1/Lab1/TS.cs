using System;
using System.Collections.Generic;
using System.Text;

namespace Lab1
{
    class TS
    {
        private string codTS;
        private string valoareTS;

        public TS(string valoareTS)
        {
            this.valoareTS = valoareTS;
        }

        public TS(string codTS, string valoareTS)
        {
            this.codTS = codTS;
            this.valoareTS = valoareTS;
        }

        public string ValoareTS { get => valoareTS; set => valoareTS = value; }
        public string CodTS { get => codTS; set => codTS = value; }
    }
}
