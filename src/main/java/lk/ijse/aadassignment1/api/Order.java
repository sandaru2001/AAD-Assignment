package lk.ijse.aadassignment1.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.ijse.aadassignment1.db.CustomerDB;
import lk.ijse.aadassignment1.db.OrderDetailDB;
import lk.ijse.aadassignment1.dto.CustomerDTO;
import lk.ijse.aadassignment1.dto.OrderDetailDTO;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class Order extends HttpServlet {
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
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<OrderDetailDTO> allOrderDetail = new OrderDetailDB().getAllOrderDetail(connection);
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonResult = objectMapper.writeValueAsString(allOrderDetail);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(jsonResult);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Jsonb jsonb = JsonbBuilder.create();
        OrderDetailDTO orderDetailDTO = jsonb.fromJson(req.getReader(), OrderDetailDTO.class);

        resp.getWriter().write("Came Json Object Successfully!");

        System.out.println(orderDetailDTO.getOrderId());
        System.out.println(orderDetailDTO.getItemId());
        System.out.println(orderDetailDTO.getPrice());
        System.out.println(orderDetailDTO.getQty());

        OrderDetailDB orderDetailDB = new OrderDetailDB();
        boolean result = orderDetailDB.saveOrderDetails(orderDetailDTO, connection);
        if (result) {
            resp.setStatus(HttpServletResponse.SC_OK);
        } else {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
