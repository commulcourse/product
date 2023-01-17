package shop.mtcoding.orange.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import shop.mtcoding.orange.model.Product;
import shop.mtcoding.orange.model.ProductRepository;

@Controller
public class ProductController {

    @Autowired // type으로 찾아냄
    private ProductRepository productRepository;

    @Autowired
    private HttpSession session;

    @GetMapping("/main")
    public String main() {
        return "main";
    }

    @GetMapping("/test")
    public String rest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        session.setAttribute("n", "session metacoding");
        request.setAttribute("name", "metacoding");
        return "test";
    }

    // 장바구니에 담은 후 새로고침시 담긴 값이 없음.
    // @GetMapping("/redirect")
    // public void redirect(HttpServletRequest request, HttpServletResponse
    // response)
    // throws ServletException, IOException {
    // // HttpSession session = request.getSession();
    // session.setAttribute("name", "session metacoding");
    // request.setAttribute("name", "metacoding");
    // response.sendRedirect("test");
    // }

    // 장바구니에 담은 후 새로고침시 바구니에 상품이 있음.
    @GetMapping("/dispatcher")
    public void dispatcher(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dis = request.getRequestDispatcher("/test");
        request.setAttribute("name", "metacoding");
        dis.forward(request, response);
    }

    // -------------------------------------------------------------
    @GetMapping({ "/", "/product" })
    public String findAll(Model model) {
        List<Product> productList = productRepository.findAll();
        model.addAttribute("productList", productList);
        return "product/main";
    }

    @GetMapping("/product/{id}")
    public String findOne(@PathVariable int id, Model model) {
        Product product = productRepository.findOne(id);
        model.addAttribute("product", product);
        return "product/detail";
    }

    @GetMapping("/product/addForm")
    public String addForm() {
        return "product/addForm";
    }

    // 장바구니에 담은 후 새로고침시 바구니에 상품이 있음.
    @PostMapping("/product/add")
    public String add(String name, int price, int qty) {
        // 하나의 책임
        int result = productRepository.insert(name, price, qty);
        if (result == 1) {
            return "redirect:/product";
        } else {
            return "redirect:/product/addForm";
        }
    }

    @PostMapping("/product/{id}/delete")
    public String delete(@PathVariable int id) {
        int result = productRepository.delete(id);
        if (result == 1) {
            return "redirect:/";
        } else {
            return "redirect:/product/" + id;
        }
    }
    // @GetMapping("/product/{id}")
    // public String findOne(@PathVariable int id, Model model) {
    // Product product = productRepository.findOne(id);
    // model.addAttribute("product", product);
    // return "product/detail";
    // }

    @GetMapping("/product/{id}/updateForm")
    public String updateForm(@PathVariable int id, Model model) {
        Product product = productRepository.findOne(id);
        model.addAttribute("product", product);
        return "product/updateForm";

    }

    @PostMapping("/product/{id}/update")
    public String update(@PathVariable int id, String name, int price, int qty) {

        // 레파지토리 UPDATE 메서드 호출
        int result = productRepository.update(id, name, price, qty);
        if (result == 1) {
            return "redirect:/product/" + id;
        } else {
            return "redirect:/product/" + id + "/updateForm";
        }
        // 결과받기(1,-1)
        // 성공 -> 상세보기 페이지
        // 실패 -> 수정페이지
    }
}