package com.nguyenvu.springai.ui;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Giới thiệu - AI Flight Assistant")
@Route(value = "about", layout = MainLayout.class)
public class AboutView extends VerticalLayout {

    public AboutView() {
        setSizeFull();
        setPadding(true);
        setSpacing(true);

        H1 title = new H1("🛫 Về AI Flight Assistant");
        title.getStyle().set("color", "var(--lumo-primary-color)");

        Paragraph intro = new Paragraph(
                "AI Flight Assistant là trợ lý thông minh được phát triển bằng Spring AI và công nghệ RAG " +
                "(Retrieval-Augmented Generation), giúp bạn tra cứu thông tin về chuyến bay nhanh chóng và chính xác."
        );

        H2 featuresTitle = new H2("✨ Tính năng nổi bật");
        
        Div features = new Div();
        features.getElement().setProperty("innerHTML", """
                <ul style="line-height: 1.8; font-size: 1rem;">
                    <li><strong>Trả lời thông minh:</strong> Sử dụng AI để hiểu ngữ cảnh và đưa ra câu trả lời chính xác</li>
                    <li><strong>Dữ liệu cập nhật:</strong> Thông tin về giá vé, chính sách, dịch vụ được cập nhật liên tục</li>
                    <li><strong>Hỗ trợ 24/7:</strong> Trợ lý AI luôn sẵn sàng giải đáp mọi thắc mắc</li>
                    <li><strong>Giao diện thân thiện:</strong> Thiết kế đẹp mắt, dễ sử dụng trên mọi thiết bị</li>
                    <li><strong>Bảo mật:</strong> Thông tin của bạn được bảo vệ tuyệt đối</li>
                </ul>
                """);

        H2 techTitle = new H2("🔧 Công nghệ");
        
        Div tech = new Div();
        tech.getElement().setProperty("innerHTML", """
                <ul style="line-height: 1.8; font-size: 1rem;">
                    <li><strong>Spring AI 1.0.3:</strong> Framework AI hiện đại cho Java</li>
                    <li><strong>RAG (Retrieval-Augmented Generation):</strong> Kết hợp truy xuất thông tin và sinh văn bản</li>
                    <li><strong>OpenAI GPT-4:</strong> Mô hình ngôn ngữ lớn tiên tiến nhất</li>
                    <li><strong>Vector Store:</strong> Lưu trữ và tìm kiếm thông tin hiệu quả</li>
                    <li><strong>Vaadin 24:</strong> Framework UI hiện đại cho Java</li>
                    <li><strong>Spring Boot 3.5:</strong> Nền tảng phát triển ứng dụng mạnh mẽ</li>
                </ul>
                """);

        H2 contactTitle = new H2("📞 Liên hệ");
        
        Paragraph contact = new Paragraph(
                "Nếu bạn có bất kỳ thắc mắc hoặc góp ý nào, vui lòng liên hệ:\n" +
                "• Email: support@airline.vn\n" +
                "• Hotline: 1900-xxxx (24/7)\n" +
                "• Website: https://airline.vn"
        );
        contact.getStyle()
                .set("white-space", "pre-line")
                .set("background-color", "var(--lumo-contrast-5pct)")
                .set("padding", "1rem")
                .set("border-radius", "8px");

        add(title, intro, featuresTitle, features, techTitle, tech, contactTitle, contact);
    }
}
