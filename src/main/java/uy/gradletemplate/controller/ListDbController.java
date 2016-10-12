package uy.gradletemplate.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import uy.gradletemplate.domain.Customer;

import java.util.*;

import java.util.ArrayList;

@Controller
public class ListDbController {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @RequestMapping("/list")
    public String greeting(@RequestParam(value="name", required=false, defaultValue="World") String name, Model model) {

        model.addAttribute("name", name);

        List<String> customerList = new ArrayList<String>();

        jdbcTemplate.query(
                "SELECT id, first_name, last_name FROM customers",
                (rs, rowNum) -> new Customer(rs.getLong("id"), rs.getString("first_name"), rs.getString("last_name"))
        ).forEach(customer -> customerList.add(customer.toString()) );

        model.addAttribute("customerList", customerList);
        return "listDb";
    }
}