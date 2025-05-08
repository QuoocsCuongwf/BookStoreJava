package com.example.demo.BUS.spring_controller;

import com.example.demo.model.TacGia;
import com.example.demo.BUS.services.TacGiaServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/TacGia")
public class TacGiaControllerSpring {
    private final TacGiaServices tacGiaServices;

    @Autowired
    public TacGiaControllerSpring(TacGiaServices tacGiaServices) {
        this.tacGiaServices = tacGiaServices;
    }

    @GetMapping("/getAllTacGia")
    public List<TacGia> getAllTacGia() {
        return tacGiaServices.getTacGiaList();
    }

    @PostMapping("/timKiem")
    public List<TacGia> timKiem(@RequestParam String find) {
        return tacGiaServices.searchTacGia(find);
    }

    @PostMapping("/Delete")
    public String deleteTacGia(@RequestParam String maTacGia) {
        if (maTacGia == null || maTacGia.isEmpty()) {
            return "Delete Fail: Invalid MaTacGia";
        }
        tacGiaServices.deleteTacGia(maTacGia);
        return "Delete Success";
    }

    @PostMapping("/Add")
    public String addTacGia(@RequestBody TacGia tacGia) {
        if (tacGia == null || tacGia.getMatg() == null || tacGia.getMatg().isEmpty()) {
            return "Add Fail: Invalid TacGia or MaTacGia";
        }
        return tacGiaServices.addTacGia(tacGia);
    }

    @PostMapping("/Update")
    public String updateTacGia(@RequestBody TacGia tacGia) {
        if (tacGia == null || tacGia.getMatg() == null || tacGia.getMatg().isEmpty()) {
            return "Update Fail: Invalid TacGia or MaTacGia";
        }
        return tacGiaServices.updateTacGia(tacGia);
    }
}