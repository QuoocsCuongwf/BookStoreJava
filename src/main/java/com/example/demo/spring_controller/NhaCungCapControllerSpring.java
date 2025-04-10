package com.example.demo.spring_controller;

import com.example.demo.model.NhaCungCap;
import com.example.demo.services.NhaCungCapServices;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/nhaCungCap")

public class NhaCungCapControllerSpring {
    NhaCungCapServices nhaCungCapServices=new NhaCungCapServices();

    @GetMapping("/getAllNhaCungCap")
    public List<NhaCungCap> getAllNhaCungCap() {
        System.out.println(nhaCungCapServices.getListNhaCungCap());
        return nhaCungCapServices.getListNhaCungCap();
    }

    @PostMapping("/timKiem")
    public List<NhaCungCap> timKiem(@RequestParam String find) {
        return nhaCungCapServices.searchNhaCungCap(find);
    }

    @PostMapping("/Delete")
    public String DeleteNhaCungCap(@RequestParam String maNhaCungCap) {
        nhaCungCapServices.deleteNhaCungCap(maNhaCungCap);
        return "success";
    }
    @PostMapping("/Add")
    public String AddNhaCungCap(@RequestBody NhaCungCap nhaCungCap) {
        return nhaCungCapServices.addNhaCungCap(nhaCungCap);
    }
    @PostMapping("/Update")
    public String UpdateNhaCungCap(@RequestBody NhaCungCap nhaCungCap) {
        return nhaCungCapServices.updateNhaCungCap(nhaCungCap);
    }
}
