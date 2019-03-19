using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace tReservation_Lab1CS
{
    class User
    {
        public int UserId { get; set; }
        public string UserName { get; set; }
        public string UserPassword { get; set; }

        public override bool Equals(object obj)
        {
            var user = obj as User;
            return user != null &&
                   UserId == user.UserId &&
                   UserName == user.UserName &&
                   UserPassword == user.UserPassword;
        }

        public override int GetHashCode()
        {
            var hashCode = 1418792015;
            hashCode = hashCode * -1521134295 + EqualityComparer<string>.Default.GetHashCode(UserName);
            hashCode = hashCode * -1521134295 + EqualityComparer<string>.Default.GetHashCode(UserPassword);
            return hashCode;
        }


    }
}
