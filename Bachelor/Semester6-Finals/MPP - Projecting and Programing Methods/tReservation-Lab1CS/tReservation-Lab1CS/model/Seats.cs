using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace tReservation_Lab1CS
{
    class Seats
    {
        public int SeatNumber { get; set; }
        public int CourseId { get; set; }
        public string SeatClient { get; set; }

        public override bool Equals(object obj)
        {
            var seats = obj as Seats;
            return seats != null &&
                   SeatNumber == seats.SeatNumber &&
                   CourseId == seats.CourseId &&
                   SeatClient == seats.SeatClient;
        }

        public override int GetHashCode()
        {
            var hashCode = 1011409597;
            hashCode = hashCode * -1521134295 + SeatNumber.GetHashCode();
            hashCode = hashCode * -1521134295 + CourseId.GetHashCode();
            hashCode = hashCode * -1521134295 + EqualityComparer<string>.Default.GetHashCode(SeatClient);
            return hashCode;
        }
    }
}


namespace tReservation.repository
{
    public class SeatsHelper
    {
        public int SeatNumber { get; set; }
        public int CourseId { get; set; }
    }
}