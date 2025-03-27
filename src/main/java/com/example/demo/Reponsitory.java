package com.example.demo;

import com.example.demo.model.NhanVien;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Reponsitory extends JpaRepository<NhanVien, Integer> {}
