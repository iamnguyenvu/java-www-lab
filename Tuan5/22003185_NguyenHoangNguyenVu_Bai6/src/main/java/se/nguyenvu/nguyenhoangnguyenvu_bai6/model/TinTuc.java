package se.nguyenvu.nguyenhoangnguyenvu_bai6.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TinTuc {
    private int maTT;
    private String tieuDe;
    private String noiDungTT;
    private String lienKet;
    private int maDM;
}
