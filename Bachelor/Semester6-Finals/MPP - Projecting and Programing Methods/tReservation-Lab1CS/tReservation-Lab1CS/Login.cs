using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Configuration;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using tReservation_Lab1CS.services;

namespace tReservation_Lab1CS
{
    public partial class Login : Form
    {
        IDictionary<String, string> props = new SortedList<String, String>();
            

            
        public Login()
        {
            Console.WriteLine("Configuration Settings for tasksDB {0}", GetConnectionStringByName("tasksDB"));
            props.Add("ConnectionString", GetConnectionStringByName("tasksDB"));
            InitializeComponent();
        }

        private void button1_Click(object sender, EventArgs e)
        {
            ReservationService reservationService = new ReservationService(props);
            if (textBox1.Text == "" || textBox2.Text == "")
            {
                MessageBox.Show("Numele de utilizator sau parola lipsesc!", "Avertizare");
            }
            else if (reservationService.LogInService(textBox1.Text, textBox2.Text)==true)
            {
                this.Hide();
                Home home = new Home(props);
                home.Show();
            }
            else
            {
                MessageBox.Show("Numele de utilizator sau parola is incorecte!", "Eroare");
            }
            

        }

        static string GetConnectionStringByName(string name)
        {
            // Assume failure.
            string returnValue = null;

            // Look for the name in the connectionStrings section.
            ConnectionStringSettings settings = ConfigurationManager.ConnectionStrings[name];

            // If found, return the connection string.
            if (settings != null)
                returnValue = settings.ConnectionString;

            return returnValue;
        }
    }
}
