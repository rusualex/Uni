using System;
using System.Data;
using System.Collections.Generic;

namespace tReservation_Lab1CS
{
    public static class DBUtils
    {


        private static IDbConnection instance = null;


        public static IDbConnection GetConnection(IDictionary<string, string> props)
        {
            if (instance == null || instance.State == System.Data.ConnectionState.Closed)
            {
                instance = GetNewConnection(props);
                instance.Open();
            }
            return instance;
        }

        private static IDbConnection GetNewConnection(IDictionary<string, string> props)
        {

            return ConnectionFactory.getInstance().createConnection(props);
        }
    }
}
