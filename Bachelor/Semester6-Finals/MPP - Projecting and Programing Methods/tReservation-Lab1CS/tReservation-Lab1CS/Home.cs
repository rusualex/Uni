using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using tReservation_Lab1CS.repository;
using tReservation_Lab1CS.services;

namespace tReservation_Lab1CS
{
    public partial class Home : Form
    {
        ReservationService reservationService;
        IDictionary<String, string> props;
        public Home(IDictionary<String, string> props)
        {
            InitializeComponent();
            this.props = props;
            reservationService = new ReservationService(props);
            List<Course> courses = reservationService.ShowAllCourses();
            var bindingList = new BindingList<Course>(courses);
            var source = new BindingSource(bindingList, null);
            dataGridView1.DataSource = source;
            dataGridView1.Columns["courseId"].Visible = false;

        }

        private void dgridv_SelectionChanged(object sender, EventArgs e)
        {
            // For any other operation except, StateChanged, do nothing
            int curRow = -1;
            if (dataGridView1.CurrentRow.Index != curRow)
            {
                if (dataGridView1.SelectedRows.Count >= 1)
                {
                    int cId = -1;
                    if (dataGridView1.SelectedRows[0].Cells[0].Value!=null) {
                        cId = (int)dataGridView1.SelectedRows[0].Cells[0].Value;
                        List<Seats> seats = reservationService.FindSeats(cId);
                        var bindingList2 = new BindingList<Seats>(seats);
                        var source2 = new BindingSource(bindingList2, null);
                        dataGridView2.DataSource = source2;
                        dataGridView2.Show();
                    }
                    
                }
                curRow = dataGridView1.CurrentRow.Index;
            }
                
            // Calculate amount code goes here
        }

        private void button1_Click(object sender, EventArgs e)
        {
            if (dataGridView2.SelectedRows.Count >= 1)
            {
                for(int i=0;i<dataGridView2.SelectedRows.Count;i++) {
                    int courseId = (int)dataGridView2.SelectedRows[i].Cells[1].Value;
                    int seatNumber = (int)dataGridView2.SelectedRows[i].Cells[0].Value;
                    reservationService.reserveSeat(new SeatsHelper { CourseId = courseId,SeatNumber=seatNumber}, textBox1.Text);
                }
            }
            
        }

        private void button2_Click(object sender, EventArgs e)
        {
            if (textBox2.Text != "" && textBox3.Text != "" && textBox4.Text != "")
            {
                List<Course> courses = reservationService.FindCourses(textBox2.Text, textBox3.Text, textBox4.Text);
                var bindingList = new BindingList<Course>(courses);
                var source = new BindingSource(bindingList, null);
                dataGridView1.DataSource = source;
                dataGridView1.Columns["courseId"].Visible = false;
            }
            else
            {
                List<Course> courses = reservationService.ShowAllCourses();
                var bindingList = new BindingList<Course>(courses);
                var source = new BindingSource(bindingList, null);
                dataGridView1.DataSource = source;
                dataGridView1.Columns["courseId"].Visible = false;

            }
        }

        private void button3_Click(object sender, EventArgs e)
        {
            Login login = new Login();
            this.Hide();
            login.Show();
        }
    }
}
