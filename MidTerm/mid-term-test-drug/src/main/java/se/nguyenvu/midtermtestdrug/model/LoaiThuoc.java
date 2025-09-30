package se.nguyenvu.midtermtestdrug.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "LOAITHUOC")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoaiThuoc {
    @Id @Column(name = "MALOAI")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long maLoai;

    @Column(name = "TENLOAI")
    private String tenLoai;
}
