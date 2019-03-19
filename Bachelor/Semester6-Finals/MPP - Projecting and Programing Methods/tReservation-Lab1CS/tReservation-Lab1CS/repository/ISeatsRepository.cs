using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using tReservation_Lab1CS.repository;

namespace tReservation_Lab1CS
{
    interface ISeatsRepository
    {
        void delete(SeatsHelper seatsHelper);
        List<Seats> findAllByCourse(int courseId);
        Seats findOneBySeatNumber(SeatsHelper seatsHelper);
        void save(Seats seats);
        void update(SeatsHelper seatsHelper, string seatClient);
    }
}
