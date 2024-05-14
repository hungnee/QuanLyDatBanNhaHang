package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import connectDB.ConnectDB;
import entity.DonViTinh;
import entity.GiaSanPham;
import entity.SanPham;

public class DAO_GiaSanPham {
	public ArrayList<GiaSanPham> layDSGiaSP() {
		ArrayList<GiaSanPham> dsGSP = new ArrayList<>();
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		
		try {
			Statement stm = con.createStatement();
			String sql = "SELECT * FROM GiaSanPham";
			ResultSet rs = stm.executeQuery(sql);
			
			while(rs.next()) {
				String maSGP = rs.getString("maGiaSanPham").trim();
				
				SanPham sp = new SanPham();
				sp.setMaSanPham(rs.getString("maSanPham"));
				
				DonViTinh dvt = new DonViTinh();
				dvt.setMaDonVi(rs.getString("maDonVi"));
				
				double donGia = rs.getDouble("donGia");
				
				String anhSP = rs.getString("anhSanPham");
				
				GiaSanPham gsp = new GiaSanPham(maSGP, sp, dvt, donGia, anhSP);
				
				dsGSP.add(gsp);
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return dsGSP;
	}
	
	public boolean themGSP(GiaSanPham gsp) {
		ConnectDB.getInstance();
	    Connection con = ConnectDB.getConnection();
	    PreparedStatement stmt = null;
	    ResultSet rs = null;
	    int n = 0;
	    try {
	    	stmt = con.prepareStatement("INSERT INTO GiaSanPham (maGiaSanPham, maSanPham, maDonVi, donGia, anhSanPham) VALUES (?, ?, ?, ?, ?)");
	    	stmt.setString(1, gsp.getMaGiaSanPham());
	        stmt.setString(2, gsp.getSanPham().getMaSanPham());
	        stmt.setString(3, gsp.getDonVi().getMaDonVi());
	        stmt.setDouble(4, gsp.getDonGia());
	        stmt.setString(5, gsp.getAnhSanPham());

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
	
	
	
	public String layAnh(String maGSP) {
		ConnectDB.getInstance();
	    Connection con = ConnectDB.getConnection();
	    PreparedStatement stmt = null;
	    ResultSet rs = null;
	    String anhSP = null;
	    try {
            con = ConnectDB.getConnection();
            if (con != null) {
                stmt = con.prepareStatement("SELECT anhSanPham FROM GiaSanPham WHERE maGiaSanPham = ?");
                stmt.setString(1, maGSP);
                rs = stmt.executeQuery();
                if (rs.next()) {
                	anhSP = rs.getString("maSanPham");
                }
            }
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
	    return anhSP;
	}
}
