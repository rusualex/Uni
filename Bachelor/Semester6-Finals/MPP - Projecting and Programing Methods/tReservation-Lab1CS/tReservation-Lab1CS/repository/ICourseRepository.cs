using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using tReservation_Lab1CS;

namespace tReservation_Lab1CS
{
    interface ICourseRepository
    {
        int size();
        void save(Course course);
        void delete(int id);
        void update(int id, Course course);
        Course FindOne(int courseId);
        Course FindOne(string departureCity,string destination,string dateOfDeparture);
        List<Course> FindAll();
    }
}
