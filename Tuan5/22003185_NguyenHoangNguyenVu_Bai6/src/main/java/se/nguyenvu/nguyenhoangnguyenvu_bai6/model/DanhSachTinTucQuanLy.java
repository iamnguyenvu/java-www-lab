package se.nguyenvu.nguyenhoangnguyenvu_bai6.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DanhSachTinTucQuanLy {
    private List<TinTuc> tinTucs = new ArrayList<>();
}
