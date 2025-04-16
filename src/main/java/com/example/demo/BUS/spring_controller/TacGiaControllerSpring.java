package com.example.demo.BUS.spring_controller;

import com.example.demo.model.TacGia;
import com.example.demo.BUS.services.TacGiaServices;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("TacGia")

public class TacGiaControllerSpring {
    TacGiaServices tacGiaServices = new TacGiaServices();
    @GetMapping("/getAllTacGia")
    public List<TacGia> getAllTacGia() {
        System.out.println(tacGiaServices.getTacGiaList());
        return tacGiaServices.getTacGiaList();
    }

    @PostMapping("/timKiem")
    public  List<TacGia> timKiem(@RequestParam String find){
        return tacGiaServices.searchTacGia(find);
    }
    @PostMapping("/Delete")
    public String DeleteTacGia(@RequestParam String maTacGia){
        tacGiaServices.deleteTacGia(maTacGia);
        return "delete success";
    }
    @PostMapping("/Add")
    public String AddTacGia(@RequestBody TacGia tacGia) {return tacGiaServices.addTacGia(tacGia);};
    @PostMapping("/Update")
    public String UpdateTacGia(@RequestBody TacGia tacGia) { return tacGiaServices.updateTacGia(tacGia); }


}
