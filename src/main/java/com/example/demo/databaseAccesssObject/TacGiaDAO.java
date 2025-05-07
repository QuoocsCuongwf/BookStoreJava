package com.example.demo.databaseAccesssObject;

import com.example.demo.model.TacGia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class TacGiaDAO {

    private final DataSource dataSource;

    @Autowired
    public TacGiaDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<TacGia> getListTacGia() {
        List<TacGia> tacGiaList = new ArrayList<>();
        String sql = "SELECT * FROM TAC_GIA";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                TacGia tacGia = new TacGia();
                tacGia.setMatg(rs.getString("MATG"));
                tacGia.setHotg(rs.getString("HOTG"));
                tacGia.setTentg(rs.getString("TENTG"));
                tacGia.setQuequan(rs.getString("QUEQUAN"));
                tacGia.setNamsinh(rs.getInt("NAMSINH"));
                tacGiaList.add(tacGia);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving TacGia list: " + e.getMessage(), e);
        }

        return tacGiaList;
    }

    public void addTacGia(TacGia tacGia) {
        String sql = "INSERT INTO TAC_GIA (MATG, HOTG, TENTG, QUEQUAN, NAMSINH) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, tacGia.getMatg());
            pstmt.setString(2, tacGia.getHotg());
            pstmt.setString(3, tacGia.getTentg());
            pstmt.setString(4, tacGia.getQuequan());
            pstmt.setInt(5, tacGia.getNamsinh());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error adding TacGia: " + e.getMessage(), e);
        }
    }

    public void updateTacGia(TacGia tacGia) {
        String sql = "UPDATE TAC_GIA SET HOTG = ?, TENTG = ?, QUEQUAN = ?, NAMSINH = ? WHERE MATG = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, tacGia.getHotg());
            pstmt.setString(2, tacGia.getTentg());
            pstmt.setString(3, tacGia.getQuequan());
            pstmt.setInt(4, tacGia.getNamsinh());
            pstmt.setString(5, tacGia.getMatg());

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new RuntimeException("No TacGia found with MATG: " + tacGia.getMatg());
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error updating TacGia: " + e.getMessage(), e);
        }
    }

    public void deleteTacGia(String maTacGia) {
        String sql = "DELETE FROM TAC_GIA WHERE MATG = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, maTacGia);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new RuntimeException("No TacGia found with MATG: " + maTacGia);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting TacGia: " + e.getMessage(), e);
        }
    }
}