using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace tReservation_Lab1CS
{
    interface IUserRepository
    {
        //int size();
        //void save(User user);
        //void delete(int id);
        //void update(int id, User user);
        bool logIn(User user);
        //List<User> findAll();
    }
}

