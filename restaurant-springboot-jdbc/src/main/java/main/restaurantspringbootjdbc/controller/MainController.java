package main.restaurantspringbootjdbc.controller;

import main.restaurantspringbootjdbc.dao.OrderDAO;
import main.restaurantspringbootjdbc.dao.UserDAO;
import main.restaurantspringbootjdbc.model.Order;
import jakarta.servlet.http.HttpSession;
import main.restaurantspringbootjdbc.util.Check;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Random;

@Controller
public class MainController {

    private final UserDAO userDAO;
    private final OrderDAO orderDAO;
    private final Random rand = new Random();

    public MainController(UserDAO userDAO, OrderDAO orderDAO) {
        this.userDAO = userDAO;
        this.orderDAO = orderDAO;
    }


    @GetMapping("/")
    public String home() {
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    // Register
    @PostMapping("/register")
    public String register(@RequestParam String username,
                           @RequestParam String password,
                           Model model) {

        if (userDAO.existsByUsername(username)) {
            model.addAttribute("msg", "User already exists!");
            return "register";
        }

        if (!Check.isValidPassword(password,model)) {
            return "register";
        }

        userDAO.register(username, password);
        model.addAttribute("msg", "Registration Successful");

        return "login";   // 👈 redirect to login page
    }
    // Login
    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session,
                        Model model) {

        if (userDAO.login(username, password)) {
            session.setAttribute("user", username);
            return "redirect:/dashboard";
        }

        model.addAttribute("msg", "Invalid Credentials");
        return "login";
    }
    // Dashboard
    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {

        String user = (String) session.getAttribute("user");

        if (user == null) {
            return "redirect:/";
        }

        model.addAttribute("user", user);
        return "dashboard";
    }

    // Place Order
    @PostMapping("/order")
    public String placeOrder(@RequestParam int item,
                             @RequestParam int quantity,
                             HttpSession session,
                             Model model) {

        String username = (String) session.getAttribute("user");

        if (username == null) {
            return "redirect:/";
        }

        String itemName = switch (item) {
            case 1 -> "Pizza";
            case 2 -> "Burger";
            case 3 -> "Pasta";
            default -> "Invalid";
        };

        Order order = new Order();
        order.setOrderId(rand.nextInt(10000));
        order.setUsername(username);
        order.setItem(itemName);
        order.setQuantity(quantity);
        order.setStatus("Preparing");

        orderDAO.placeOrder(order);

        model.addAttribute("msg", "Order ID: " + order.getOrderId());
        model.addAttribute("user", username);

        return "dashboard";
    }

    // Track Order
    @GetMapping("/track")
    public String track(@RequestParam int id,
                        HttpSession session,
                        Model model) {

        String user = (String) session.getAttribute("user");

        if (user == null) {
            return "redirect:/";
        }

        String status = orderDAO.trackOrder(id);

        model.addAttribute("msg", "Status: " + status);
        model.addAttribute("user", user);

        return "dashboard";
    }

    // 🔥 LOGOUT (MAIN FEATURE)
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();   // destroy session
        return "redirect:/";
    }
}