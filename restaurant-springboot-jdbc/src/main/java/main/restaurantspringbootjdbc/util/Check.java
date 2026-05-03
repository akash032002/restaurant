package main.restaurantspringbootjdbc.util;

import org.springframework.ui.Model;

public class Check {
    static public boolean isValidPassword(String password, Model model) {

        if (password.length() < 8) {
            model.addAttribute("msg", "Password must be at least 8 characters");
            return false;
        }

        boolean hasUpper = false;
        boolean hasLower = false;
        boolean hasDigit = false;
        boolean hasSpecial = false;

        for (char ch : password.toCharArray()) {
            if (Character.isUpperCase(ch)) {
                hasUpper = true;
            } else if (Character.isLowerCase(ch)) {
                hasLower = true;
            } else if (Character.isDigit(ch)) {
                hasDigit = true;
            } else {
                hasSpecial = true; // anything not letter/number
            }
        }

        if (!hasUpper) {
            model.addAttribute("msg", "Password must contain at least one uppercase letter");
            return false;
        }

        if (!hasLower) {
            model.addAttribute("msg", "Password must contain at least one lowercase letter");
            return false;
        }

        if (!hasDigit) {
            model.addAttribute("msg", "Password must contain at least one number");
            return false;
        }

        if (!hasSpecial) {
            model.addAttribute("msg", "Password must contain at least one special character");
            return false;
        }

        return true;
    }
}
