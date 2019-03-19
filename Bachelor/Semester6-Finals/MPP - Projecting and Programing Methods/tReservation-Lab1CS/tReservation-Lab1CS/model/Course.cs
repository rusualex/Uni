using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace tReservation_Lab1CS
{
    class Course
    {
        public int CourseId { get; set; }
        public string Destination { get; set; }
        public string DateOfDeparture { get; set; }
        public string DepartureCity { get; set; }
        public int AvailableSeats { get; set; }

        public override bool Equals(object obj)
        {
            var course = obj as Course;
            return course != null &&
                   CourseId == course.CourseId &&
                   Destination == course.Destination &&
                   DateOfDeparture == course.DateOfDeparture &&
                   DepartureCity == course.DepartureCity &&
                   AvailableSeats == course.AvailableSeats;
        }

        public override int GetHashCode()
        {
            var hashCode = -793467742;
            hashCode = hashCode * -1521134295 + CourseId.GetHashCode();
            hashCode = hashCode * -1521134295 + EqualityComparer<string>.Default.GetHashCode(Destination);
            hashCode = hashCode * -1521134295 + EqualityComparer<string>.Default.GetHashCode(DateOfDeparture);
            hashCode = hashCode * -1521134295 + EqualityComparer<string>.Default.GetHashCode(DepartureCity);
            hashCode = hashCode * -1521134295 + AvailableSeats.GetHashCode();
            return hashCode;
        }
    }
}
