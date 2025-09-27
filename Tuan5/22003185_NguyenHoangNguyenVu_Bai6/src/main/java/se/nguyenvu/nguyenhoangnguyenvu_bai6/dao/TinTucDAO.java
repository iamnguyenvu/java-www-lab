package se.nguyenvu.nguyenhoangnguyenvu_bai6.dao;

import jakarta.annotation.Resource;
import se.nguyenvu.nguyenhoangnguyenvu_bai6.model.DanhMuc;
import se.nguyenvu.nguyenhoangnguyenvu_bai6.model.TinTuc;
import se.nguyenvu.nguyenhoangnguyenvu_bai6.util.DBUtil;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TinTucDAO {
    private DBUtil dbUtil;

    public TinTucDAO(DataSource dataSource) {
        dbUtil = new DBUtil(dataSource);
    }

    public List<TinTuc> findAll() throws SQLException {
        String sql = "select * from TinTuc";
        List<TinTuc> tinTucs = new ArrayList<>();

        try(Connection connection = dbUtil.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql)
        ) {
            while(resultSet.next()) {
                tinTucs.add(TinTuc.builder()
                                .maTT(resultSet.getInt("maTT"))
                                .tieuDe(resultSet.getString("tieuDe"))
                                .noiDungTT(resultSet.getString("noiDungTT"))
                                .lienKet(resultSet.getString("lienKet"))
                                .maDM(resultSet.getInt("maDM"))
                        .build());
            }
        }

        return tinTucs;
    }

    public TinTuc findByMaTT(int maTT) {
        String sql = "select * from TinTuc where maTT = ?";
        try(Connection connection = dbUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {
            preparedStatement.setInt(1, maTT);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if(resultSet.next()) {
                    return TinTuc.builder()
                            .maTT(resultSet.getInt("maTT"))
                            .tieuDe(resultSet.getString("tieuDe"))
                            .noiDungTT(resultSet.getString("noiDungTT"))
                            .lienKet(resultSet.getString("lienKet"))
                            .maDM(resultSet.getInt("maDM"))
                            .build();
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public List<TinTuc> findByMaDM(int maDM) throws SQLException {
        String sql = "select * from TinTuc where maDM = ?";
        List<TinTuc> tinTucs = new ArrayList<>();

        try(Connection connection = dbUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {
            preparedStatement.setInt(1, maDM);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while(resultSet.next()) {
                    tinTucs.add(TinTuc.builder()
                            .maTT(resultSet.getInt("maTT"))
                            .tieuDe(resultSet.getString("tieuDe"))
                            .noiDungTT(resultSet.getString("noiDungTT"))
                            .lienKet(resultSet.getString("lienKet"))
                            .maDM(resultSet.getInt("maDM"))
                            .build());
                }
            }
        }

        return tinTucs;
    }

    public void save(TinTuc tinTuc) throws SQLException {
        String sql = "insert into TinTuc(tieuDe, noiDungTT, lienKet, maDM) values(?, ?, ?, ?)";
        try(Connection connection = dbUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {
            preparedStatement.setString(1, tinTuc.getTieuDe());
            preparedStatement.setString(2, tinTuc.getNoiDungTT());
            preparedStatement.setString(3, tinTuc.getLienKet());
            preparedStatement.setInt(4, tinTuc.getMaDM());
            preparedStatement.executeUpdate();
        }
    }

    public void update(TinTuc tinTuc) throws SQLException {
        String sql = "update TinTuc set tieuDe = ?, noiDungTT = ?, lienKet = ?, maDM = ? where maTT = ?";
        try(Connection connection = dbUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {
            preparedStatement.setString(1, tinTuc.getTieuDe());
            preparedStatement.setString(2, tinTuc.getNoiDungTT());
            preparedStatement.setString(3, tinTuc.getLienKet());
            preparedStatement.setInt(4, tinTuc.getMaDM());
            preparedStatement.setInt(5, tinTuc.getMaTT());
            preparedStatement.executeUpdate();
        }
    }

    public void delete(int maTT) throws SQLException {
        String sql = "delete from TinTuc where maTT = ?";
        try(Connection connection = dbUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {
            preparedStatement.setInt(1, maTT);
            preparedStatement.executeUpdate();
        }
    }
}
