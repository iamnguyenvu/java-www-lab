package se.nguyenvu.nguyenhoangnguyenvu_bai6.dao;

import jakarta.annotation.Resource;
import se.nguyenvu.nguyenhoangnguyenvu_bai6.model.DanhMuc;
import se.nguyenvu.nguyenhoangnguyenvu_bai6.util.DBUtil;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DanhMucDAO {
    private DBUtil dbUtil;
    public DanhMucDAO(DataSource dataSource) {
        dbUtil = new DBUtil(dataSource);
    }

    public List<DanhMuc> findAll() throws SQLException {
        String sql = "select * from DanhMuc";
        List<DanhMuc> danhMucs = new ArrayList<>();

        try(Connection connection = dbUtil.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql)
        ) {
            while(resultSet.next()) {
                danhMucs.add(DanhMuc.builder()
                                .tenDanhMuc(resultSet.getString("tenDanhMuc"))
                                .ghiChu(resultSet.getString("ghiChu"))
                                .maDM(resultSet.getInt("maDM"))
                                .nguoiQuanLy(resultSet.getString("nguoiQuanLy"))
                        .build());
            }
        }

        return danhMucs;
    }

    public DanhMuc findByMaDM(int maDM) {
        String sql = "select * from DanhMuc where maDM = ?";
        try(Connection connection = dbUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {
            preparedStatement.setInt(1, maDM);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if(resultSet.next()) {
                    return DanhMuc.builder()
                            .tenDanhMuc(resultSet.getString("tenDanhMuc"))
                            .ghiChu(resultSet.getString("ghiChu"))
                            .maDM(resultSet.getInt("maDM"))
                            .nguoiQuanLy(resultSet.getString("nguoiQuanLy"))
                            .build();
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public void save(DanhMuc danhMuc) {
        String sql = "insert into DanhMuc(maDM, tenDanhMuc, nguoiQuanLy, ghiChu) values(?,?,?)";
        try(Connection connection = dbUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {
            preparedStatement.setInt(1, danhMuc.getMaDM());
            preparedStatement.setString(2, danhMuc.getTenDanhMuc());
            preparedStatement.setString(3, danhMuc.getNguoiQuanLy());
            preparedStatement.setString(4, danhMuc.getGhiChu());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(DanhMuc danhMuc) {
        String sql = "update DanhMuc set tenDanhMuc = ?, nguoiQuanLy = ?, ghiChu = ? where maDM = ?";
        try(Connection connection = dbUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {
            preparedStatement.setString(1, danhMuc.getTenDanhMuc());
            preparedStatement.setString(2, danhMuc.getNguoiQuanLy());
            preparedStatement.setString(3, danhMuc.getGhiChu());
            preparedStatement.setInt(4, danhMuc.getMaDM());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
