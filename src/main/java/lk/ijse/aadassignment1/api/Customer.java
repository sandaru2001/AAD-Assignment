package lk.ijse.aadassignment1.api;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.ijse.aadassignment1.db.CustomerDB;
import lk.ijse.aadassignment1.dto.CustomerDTO;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet(urlPatterns = "/customer")
public class Customer extends HttpServlet {
    Connection connection;

    @Override
    public void init() throws ServletException {
        try {
            InitialContext initialContext = new InitialContext();
            DataSource dataSource = (DataSource) initialContext.lookup("java:/comp/env/jdbc/pos");
            this.connection = dataSource.getConnection();
        } catch (SQLException | NamingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Jsonb jsonb = JsonbBuilder.create();
        CustomerDTO customerDTO = jsonb.fromJson(req.getReader(), CustomerDTO.class);

        resp.getWriter().write("Came Json Object Successfully");

        System.out.println(customerDTO.getId());
        System.out.println(customerDTO.getName());
        System.out.println(customerDTO.getAddress());
        System.out.println(customerDTO.getContact());

        CustomerDB customerDB = new CustomerDB();
        boolean result = customerDB.saveCustomer(customerDTO, connection);
        if (result) {
            resp.setStatus(HttpServletResponse.SC_OK);
        } else {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
