package main.restaurantspringbootjdbc.dao;

import main.restaurantspringbootjdbc.model.Order;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OrderDAO {

    private final JdbcTemplate jdbcTemplate;

    public OrderDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void placeOrder(Order order) {
        String sql = "INSERT INTO orders VALUES(?,?,?,?,?)";
        jdbcTemplate.update(sql,
                order.getOrderId(),
                order.getUsername(),
                order.getItem(),
                order.getQuantity(),
                order.getStatus());
    }

    public String trackOrder(int id) {
        String sql = "SELECT status FROM orders WHERE order_id=?";
        return jdbcTemplate.queryForObject(sql, String.class, id);
    }

    public List<Order> getAllOrders() {
        String sql = "SELECT * FROM orders";
        return jdbcTemplate.query(sql, (rs, i) -> {
            Order o = new Order();
            o.setOrderId(rs.getInt("order_id"));
            o.setUsername(rs.getString("username"));
            o.setItem(rs.getString("item"));
            o.setQuantity(rs.getInt("quantity"));
            o.setStatus(rs.getString("status"));
            return o;
        });
    }

//    public List<Order> getOrdersByUsername(String username) {
//
//        String sql = "SELECT * FROM orders WHERE username=?";
//
//        return jdbcTemplate.query(sql, (rs, i) -> {
//            Order o = new Order();
//            o.setOrderId(rs.getInt("order_id"));
//            o.setUsername(rs.getString("username"));
//            o.setItem(rs.getString("item"));
//            o.setQuantity(rs.getInt("quantity"));
//            o.setStatus(rs.getString("status"));
//            return o;
//        }, username);
//    }
}