using System;
using System.Collections.Generic;
using System.Text;

namespace Lab1.BinaryTree
{
    class Node
    {
        private TS value;
        private Node left;
        private Node right;

        public Node(TS value)
        {
            this.value = value;
            Right = null;
            Left = null;
        }

        internal Node Left { get => left; set => left = value; }
        internal Node Right { get => right; set => right = value; }
        internal TS Value { get => value; set => this.value = value; }
    }
}
