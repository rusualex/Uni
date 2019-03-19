using log4net;
using System;
using System.Collections.Generic;
using System.Data;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace tReservation_Lab1CS
{
    class UserRepository : IUserRepository
    {

        private static readonly ILog log = LogManager.GetLogger("UserRepository");
        IDictionary<String, string> props;
        public UserRepository(IDictionary<String, string> props)
        {
            log.Info("Creating UserRepository");
            this.props = props;
        }

        public bool logIn(User user)
        {
            log.InfoFormat("Entering login with value {0}");
            IDbConnection con = DBUtils.GetConnection(props);

            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "select * from User where userName=@userName and userPassword=@userPassword";
                IDbDataParameter paramUserName = comm.CreateParameter();
                paramUserName.ParameterName = "@userName";
                paramUserName.Value = user.UserName;
                comm.Parameters.Add(paramUserName);

                IDbDataParameter paramUserPassword = comm.CreateParameter();
                paramUserPassword.ParameterName = "@userPassword";
                paramUserPassword.Value = user.UserPassword;
                comm.Parameters.Add(paramUserPassword);

                using (var dataR = comm.ExecuteReader())
                {
                    if (dataR.Read())
                    {
                        log.InfoFormat("Exiting login with successfull authentification");
                        return true;
                    }
                }
            }
            con.Close();
            log.InfoFormat("Exiting login with unsuccessfull authentification");
            return false;
        }

        //public void delete(int id)
        //{
        //    log.InfoFormat("Entering deleteUser by ID: {0}",id);
        //    IDbConnection con = DBUtils.GetConnection();
        //    int rowsAffected = 0;
        //    using (var comm = con.CreateCommand())
        //    {
        //        comm.CommandText = "DELETE FROM User WHERE userId = @userId";
        //        IDbDataParameter paramUserId = comm.CreateParameter();
        //        paramUserId.ParameterName = "@userId";
        //        paramUserId.Value = id;
        //        comm.Parameters.Add(paramUserId);
        //        rowsAffected = comm.ExecuteNonQuery();
        //    }
        //    log.InfoFormat("Exiting deleteUser with {0} rows affected", rowsAffected);
        //    con.Close();
        //}

        //public List<User> findAll()
        //{
        //    log.InfoFormat("Entering findAll User");
        //    IDbConnection con = DBUtils.GetConnection();
        //    List<User> users = new List<User>();
        //    using (var comm = con.CreateCommand())
        //    {
        //        comm.CommandText = "select * from User";
        //        using (var dataR = comm.ExecuteReader())
        //        {
        //            while (dataR.Read()) { 
        //                int userId = dataR.GetInt32(0);
        //                string userName = dataR.GetString(1);
        //                string userPassword = dataR.GetString(2);
        //                User user = new User()
        //                {
        //                    UserId = userId,
        //                    UserName = userName,
        //                    UserPassword = userPassword
        //                };
        //                users.Add(user);
        //            }
        //        }
        //    }
        //    con.Close();
        //    log.InfoFormat("Exiting findAll User");
        //    return users;

        //}


        //    public void save(User user)
        //    {
        //        log.InfoFormat("Entering saveUser with values for {0}", user.UserName);
        //        IDbConnection con = DBUtils.GetConnection();

        //        using (var comm = con.CreateCommand())
        //        {
        //            comm.CommandText = "insert into User(userName,userPassword) values(@userName,@userPassword)";
        //            //IDbDataParameter paramUserId = comm.CreateParameter();
        //            IDbDataParameter paramUserName = comm.CreateParameter();
        //            IDbDataParameter paramUserPassword = comm.CreateParameter();
        //            //paramUserId.ParameterName = "@userId";
        //            //paramUserId.Value = "userId";
        //            //comm.Parameters.Add(paramUserId);
        //            paramUserName.ParameterName = "@userName";
        //            paramUserName.Value = user.UserName;
        //            comm.Parameters.Add(paramUserName);
        //            paramUserPassword.ParameterName = "@userPassword";
        //            paramUserPassword.Value = user.UserPassword;
        //            comm.Parameters.Add(paramUserPassword);
        //            int rowsAffected = comm.ExecuteNonQuery();
        //        }
        //        con.Close();
        //        log.InfoFormat("Exiting saveUser after values inserted");
        //    }


        //    //(PreparedStatement preStmt=con.prepareStatement("select count(*) as [SIZE] from main.Course")) {
        //    //    try(ResultSet result = preStmt.executeQuery()) {
        //    //        if (result.next()) {
        //    //            logger.traceExit(result.getInt("SIZE"));
        //    //            return result.getInt("SIZE");
        //    //        }

        //public int size()
        //    {
        //        log.InfoFormat("Entering size User");
        //        IDbConnection con = DBUtils.GetConnection();
        //        int userNumber = 0;
        //        using (var comm = con.CreateCommand())
        //        {
        //            comm.CommandText = "select count(*) over () as total_cnt,u.* from User u";
        //            using (var dataR = comm.ExecuteReader())
        //            {
        //                if (dataR.Read())
        //                {
        //                    userNumber = dataR.GetInt32(0);
        //                }
        //            }
        //        }
        //        con.Close();
        //        log.InfoFormat("Exiting size User with value{0}", userNumber);
        //        return userNumber;
        //    }

        //    public void update(int id, User user)
        //    {
        //        log.InfoFormat("Entering updateUser {0}", user.UserName);
        //        IDbConnection con = DBUtils.GetConnection();
        //        int rowsAffected = 0;
        //        using (var comm = con.CreateCommand())
        //        {
        //            comm.CommandText = "update User " +
        //                "set userName=@userName,userPassword=@userPassword" +
        //                "where userId=@userId";
        //            IDbDataParameter paramUserId = comm.CreateParameter();
        //            IDbDataParameter paramUserName = comm.CreateParameter();
        //            IDbDataParameter paramUserPassword = comm.CreateParameter();
        //            paramUserId.ParameterName = "@userId";
        //            paramUserId.Value = id;
        //            comm.Parameters.Add(paramUserId);
        //            paramUserName.ParameterName = "@userName";
        //            paramUserName.Value = user.UserName;
        //            comm.Parameters.Add(paramUserName);
        //            paramUserPassword.ParameterName = "@userPassword";
        //            paramUserPassword.Value = user.UserPassword;
        //            comm.Parameters.Add(paramUserPassword);
        //            rowsAffected = comm.ExecuteNonQuery();
        //        }
        //        con.Close();
        //        log.InfoFormat("Exiting after updateUser with {0} rows affected",rowsAffected);
        //    }
    }
}
