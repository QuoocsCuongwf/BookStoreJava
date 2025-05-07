package com.example.demo.BUS.spring_controller;

import com.example.demo.BUS.services.TheLoaiServices;
import com.example.demo.model.TheLoai;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/theLoai")
public class TheLoaiControllerSpring {
    private final TheLoaiServices theLoaiServices = new TheLoaiServices();

    @GetMapping("/getAllTheLoai")
    public List<TheLoai> getAllTheLoai() {
        System.out.println(theLoaiServices.getListTheLoai());
        return theLoaiServices.getListTheLoai();
    }

    @PostMapping("/timKiem")
    public List<TheLoai> timKiem(@RequestParam String find) {
        return theLoaiServices.searchTheLoai(find);
    }

    @PostMapping("/Delete")
    public String deleteTheLoai(@RequestParam String maTheLoai) {
        theLoaiServices.deleteTheLoai(maTheLoai);
        return "Success";
    }

    @PostMapping("/Add")
    public String addTheLoai(@RequestBody TheLoai theLoai) {
        return theLoaiServices.addTheLoai(theLoai);
    }

    @PostMapping("/Update")
    public String updateTheLoai(@RequestBody TheLoai theLoai) {
        return theLoaiServices.updateTheLoai(theLoai);
    }
}