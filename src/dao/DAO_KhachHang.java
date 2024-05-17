package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

import connectDB.ConnectDB;
import entity.KhachHang;

public class DAO_KhachHang {
	public ArrayList<KhachHang> layDSKhachHang() {
		ArrayList<KhachHang> dsKH = new ArrayList<>();
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		
		try {
			Statement stm = con.createStatement();
			String sql = "SELECT * FROM KhachHang";
			ResultSet rs = stm.executeQuery(sql);
			
			while(rs.next()) {
				String maKH = rs.getString("maKhachHang");
				String tenKH = rs.getString("tenKhachHang");
				String sdt = rs.getString("soDienThoai");
				LocalDate ngayThamGia = rs.getDate("ngayThamGia").toLocalDate();
				int diem = rs.getInt("diemTichLuy");
				
				KhachHang kh = new KhachHang(maKH, tenKH, sdt, ngayThamGia, diem);
				
				dsKH.add(kh);
			}
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return dsKH;
	}
	
	public boolean themKhachHang(KhachHang kh) {
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		int n = 0;
		
		try {
			stmt = con.prepareStatement("INSERT INTO KhachHang (maKhachHang, tenKhachHang, soDienThoai, ngayThamGia, diemTichLuy) VALUES (?, ?, ?, ?, ?)");
			stmt.setString(1, kh.getMaKhachHang());
			stmt.setString(2, kh.getTenKhachHang());
			stmt.setString(3, kh.getSoDienThoai());
			stmt.setDate(4, Date.valueOf(kh.getNgayThamGia()));
			stmt.setInt(5, kh.getDiemTichLuy());
			
			n = stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				stmt.close();
			} catch (SQLException e2) {
				// TODO: handle exception
				e2.printStackTrace();
			}
		}
		return n > 0;
	}
	
	public boolean suaGiaSanPham(KhachHang kh) {
		ConnectDB.getInstance();
	    Connection con = ConnectDB.getConnection();
	    PreparedStatement stmt = null;
	    ResultSet rs = null;
	    int n = 0;
	    try {
	    	stmt = con.prepareStatement("UPDATE KhachHang set tenKhachHang = ?, soDienThoai = ?, ngayThamGia = ? where maKhachHang = ?");
	    	stmt.setString(1, kh.getTenKhachHang());
	    	stmt.setString(2, kh.getSoDienThoai());
	    	stmt.setDate(3, Date.valueOf(kh.getNgayThamGia()));
	    	stmt.setString(4, kh.getMaKhachHang());

	        n = stmt.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        try {
				stmt.close();
			} catch (SQLException e2) {
				// TODO: handle exception
				e2.printStackTrace();
			}
	    }
	    return n > 0;
	}
	
	public boolean xoaGiaSanPham(KhachHang kh) {
		ConnectDB.getInstance();
	    Connection con = ConnectDB.getConnection();
	    PreparedStatement stmt = null;
	    ResultSet rs = null;
	    int n = 0;
	    try {
	    	stmt = con.prepareStatement("DELETE FROM KhachHang WHERE maKhachHang = ?");
	    	stmt.setString(1, kh.getMaKhachHang());

	        n = stmt.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        try {
				stmt.close();
			} catch (SQLException e2) {
				// TODO: handle exception
				e2.printStackTrace();
			}
	    }
	    return n > 0;
	}
}
