package com.nguyenvu.springai.ui;

import com.vaadin.flow.component.accordion.Accordion;
import com.vaadin.flow.component.accordion.AccordionPanel;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Tài liệu - AI Flight Assistant")
@Route(value = "documentation", layout = MainLayout.class)
public class DocumentationView extends VerticalLayout {

    public DocumentationView() {
        setSizeFull();
        setPadding(true);
        setSpacing(true);

        H1 title = new H1("📚 Tài liệu hướng dẫn");
        title.getStyle().set("color", "var(--lumo-primary-color)");

        Paragraph intro = new Paragraph(
                "Dưới đây là các thông tin và hướng dẫn chi tiết về dịch vụ của chúng tôi."
        );

        Accordion accordion = new Accordion();

        // Panel 1: Đặt vé
        Div bookingContent = createContent("""
                <h3>Các bước đặt vé</h3>
                <ol>
                    <li>Truy cập website hoặc app</li>
                    <li>Nhập thông tin chuyến bay: điểm đi, điểm đến, ngày bay</li>
                    <li>Chọn chuyến bay phù hợp</li>
                    <li>Điền thông tin hành khách</li>
                    <li>Chọn ghế ngồi (tùy chọn)</li>
                    <li>Thanh toán</li>
                    <li>Nhận mã đặt chỗ qua email/SMS</li>
                </ol>
                
                <h3>Phương thức thanh toán</h3>
                <ul>
                    <li>Thẻ tín dụng/Ghi nợ (Visa, Mastercard, JCB)</li>
                    <li>Ví điện tử (Momo, ZaloPay, VNPay)</li>
                    <li>Chuyển khoản ngân hàng</li>
                    <li>Thanh toán tại quầy</li>
                </ul>
                
                <h3>Mẹo đặt vé</h3>
                <ul>
                    <li>Đặt trước 3-6 tuần để được giá tốt nhất</li>
                    <li>Linh hoạt về ngày giờ bay</li>
                    <li>Theo dõi chương trình khuyến mãi</li>
                    <li>Đăng ký thành viên để tích điểm</li>
                </ul>
                """);
        accordion.add(new AccordionPanel("🎫 Đặt vé và Thanh toán", bookingContent));

        // Panel 2: Chính sách
        Div policyContent = createContent("""
                <h3>Chính sách hủy chuyến bay</h3>
                <ul>
                    <li>Hủy trong 24h: Miễn phí</li>
                    <li>Hủy 7-30 ngày trước: Phí 20%</li>
                    <li>Hủy 3-6 ngày trước: Phí 50%</li>
                    <li>Hủy trong 2 ngày: Phí 80%</li>
                </ul>
                
                <h3>Chính sách đổi chuyến bay</h3>
                <ul>
                    <li>Đổi trong 24h: Miễn phí 1 lần</li>
                    <li>Đổi trước 7 ngày: Phí 100.000đ + chênh lệch giá</li>
                    <li>Đổi trong 7 ngày: Phí 300.000đ + chênh lệch giá</li>
                </ul>
                
                <h3>Hành lý</h3>
                <ul>
                    <li>Xách tay: Tối đa 7kg</li>
                    <li>Ký gửi hạng Phổ thông: 20kg miễn phí</li>
                    <li>Ký gửi hạng Thương gia: 30kg miễn phí</li>
                    <li>Hành lý vượt cước: 50.000đ/kg</li>
                </ul>
                """);
        accordion.add(new AccordionPanel("📋 Chính sách & Điều khoản", policyContent));

        // Panel 3: Check-in
        Div checkinContent = createContent("""
                <h3>Check-in Online</h3>
                <ul>
                    <li>Mở từ 24h đến 1h trước giờ bay</li>
                    <li>Truy cập website hoặc app</li>
                    <li>Nhập mã đặt chỗ và thông tin</li>
                    <li>Chọn hoặc đổi ghế ngồi</li>
                    <li>Tải boarding pass về điện thoại</li>
                </ul>
                
                <h3>Check-in tại sân bay</h3>
                <ul>
                    <li>Đóng cửa 40 phút trước giờ bay</li>
                    <li>Mang CMND/Passport và mã đặt chỗ</li>
                    <li>Ký gửi hành lý tại quầy</li>
                    <li>Nhận boarding pass</li>
                </ul>
                
                <h3>Lưu ý</h3>
                <ul>
                    <li>Đến sân bay trước 2h (chuyến bay quốc tế)</li>
                    <li>Đến sân bay trước 1.5h (chuyến bay nội địa)</li>
                    <li>Kiểm tra thông tin trên vé</li>
                    <li>Chuẩn bị giấy tờ tùy thân</li>
                </ul>
                """);
        accordion.add(new AccordionPanel("✈️ Check-in và Lên máy bay", checkinContent));

        // Panel 4: Giá vé
        Div priceContent = createContent("""
                <h3>Tuyến bay phổ biến</h3>
                <table style="width: 100%; border-collapse: collapse;">
                    <tr style="background-color: var(--lumo-contrast-5pct);">
                        <th style="padding: 8px; text-align: left; border: 1px solid var(--lumo-contrast-10pct);">Tuyến bay</th>
                        <th style="padding: 8px; text-align: left; border: 1px solid var(--lumo-contrast-10pct);">Thời gian</th>
                        <th style="padding: 8px; text-align: left; border: 1px solid var(--lumo-contrast-10pct);">Giá từ</th>
                    </tr>
                    <tr>
                        <td style="padding: 8px; border: 1px solid var(--lumo-contrast-10pct);">Hà Nội - TP.HCM</td>
                        <td style="padding: 8px; border: 1px solid var(--lumo-contrast-10pct);">2h 15'</td>
                        <td style="padding: 8px; border: 1px solid var(--lumo-contrast-10pct);">1.200.000đ</td>
                    </tr>
                    <tr>
                        <td style="padding: 8px; border: 1px solid var(--lumo-contrast-10pct);">Hà Nội - Đà Nẵng</td>
                        <td style="padding: 8px; border: 1px solid var(--lumo-contrast-10pct);">1h 20'</td>
                        <td style="padding: 8px; border: 1px solid var(--lumo-contrast-10pct);">800.000đ</td>
                    </tr>
                    <tr>
                        <td style="padding: 8px; border: 1px solid var(--lumo-contrast-10pct);">TP.HCM - Đà Nẵng</td>
                        <td style="padding: 8px; border: 1px solid var(--lumo-contrast-10pct);">1h 15'</td>
                        <td style="padding: 8px; border: 1px solid var(--lumo-contrast-10pct);">700.000đ</td>
                    </tr>
                </table>
                
                <h3>Hạng vé</h3>
                <ul>
                    <li><strong>Phổ thông:</strong> Từ giá niêm yết</li>
                    <li><strong>Phổ thông Đặc biệt:</strong> +30-50%</li>
                    <li><strong>Thương gia:</strong> +100-150%</li>
                </ul>
                
                <h3>Khuyến mãi thường xuyên</h3>
                <ul>
                    <li>Thứ 2 hàng tuần: Flash sale giảm 20-30%</li>
                    <li>Sinh nhật hãng: Giảm đến 50%</li>
                    <li>Đặt nhóm từ 10 người: Giảm 10-15%</li>
                </ul>
                """);
        accordion.add(new AccordionPanel("💰 Giá vé và Khuyến mãi", priceContent));

        // Panel 5: Dịch vụ
        Div serviceContent = createContent("""
                <h3>Dịch vụ trên chuyến bay</h3>
                <ul>
                    <li>Suất ăn đặc biệt (chay, halal, diabetic)</li>
                    <li>Giải trí: phim, nhạc, game</li>
                    <li>WiFi trên máy bay: 100.000đ/chuyến</li>
                    <li>Mua sắm miễn thuế</li>
                </ul>
                
                <h3>Dịch vụ sân bay</h3>
                <ul>
                    <li>Phòng chờ VIP: 500.000-800.000đ</li>
                    <li>Fast Track: 200.000-300.000đ</li>
                    <li>Đưa đón sân bay: Từ 300.000đ</li>
                    <li>Phòng nghỉ Transit: 200.000đ/4h</li>
                </ul>
                
                <h3>Dịch vụ hành lý</h3>
                <ul>
                    <li>Mua thêm hành lý: 150.000đ/10kg (online)</li>
                    <li>Gửi xe đạp: 500.000đ</li>
                    <li>Bảo hiểm hành lý: Từ 50.000đ</li>
                    <li>Dịch vụ gói hành lý: 50.000đ/kiện</li>
                </ul>
                
                <h3>Hỗ trợ đặc biệt</h3>
                <ul>
                    <li>Xe lăn miễn phí cho người khuyết tật</li>
                    <li>Hỗ trợ người cao tuổi</li>
                    <li>Dịch vụ cho phụ nữ mang thai</li>
                    <li>Chăm sóc trẻ nhỏ</li>
                </ul>
                """);
        accordion.add(new AccordionPanel("🎁 Dịch vụ bổ sung", serviceContent));

        add(title, intro, accordion);
    }

    private Div createContent(String html) {
        Div content = new Div();
        content.getElement().setProperty("innerHTML", html);
        content.getStyle()
                .set("line-height", "1.8")
                .set("font-size", "1rem");
        return content;
    }
}
