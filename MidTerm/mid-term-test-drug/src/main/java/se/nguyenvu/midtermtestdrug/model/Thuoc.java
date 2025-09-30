package se.nguyenvu.midtermtestdrug.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "THUOC")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Thuoc {
    @Id @Column(name = "MATHUOC")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long maThuoc;

    @Column(name = "TENTHUOC")
    private String tenThuoc;

    @Column(name = "GIA")
    private BigDecimal gia;

    @Column(name = "NAMSX")
    private int namSX;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MALOAI", nullable = false)
    private LoaiThuoc loaiThuoc;
}
