using System;
using System.Collections.Generic;
using System.Text;

namespace Lab1.BinaryTree
{
    class Tree
    {
        private Node root;
        public List<Node> nodes = new List<Node> { };
        private Node AddRecursive(Node current, TS value)
        {
            if (current == null)
            {
                return new Node(value);
            }

            if (value.ValoareTS.CompareTo(current.Value.ValoareTS) < 0)
            {
                current.Left = AddRecursive(current.Left, value);
            }
            else if (value.ValoareTS.CompareTo(current.Value.ValoareTS) > 0)
            {
                current.Right = AddRecursive(current.Right, value);
            }
            else return current;
            return current;
        }

        public void Add(TS value)
        {
            root = this.AddRecursive(root, value);

        }

        public Node GetNodeRecursive(Node current, TS value)
        {
            if (current == null) return null;
            if (value.ValoareTS == current.Value.ValoareTS) return current;
            return value.ValoareTS.CompareTo(current.Value.ValoareTS) < 0 ?  GetNodeRecursive(current.Left, value)  :  GetNodeRecursive(current.Right, value);
        }

        public Node getNode(TS value)
        {
            return GetNodeRecursive(root, value);
        }


        public bool ContainsNodeRecursive(Node current, TS value)
        {
            if (current == null) return false;
            if (value.ValoareTS == current.Value.ValoareTS) return true;

            return value.ValoareTS.CompareTo(current.Value.ValoareTS) < 0 ? ContainsNodeRecursive(current.Left, value) : ContainsNodeRecursive(current.Right, value);
        }
        public bool containsNode(TS value)
        {
            return ContainsNodeRecursive(root, value);
        }

        public void Inorder()
        {
            if (root == null)
            {
                return;
            }
            Stack<Node> s = new Stack<Node>();
            Node curr = root;
            while (curr != null || s.Count > 0)
            {
                while (curr != null)
                {
                    s.Push(curr);
                    curr = curr.Left;
                }
                curr = s.Pop();
                nodes.Add(curr);
                curr = curr.Right;
            }

        }


    }
}