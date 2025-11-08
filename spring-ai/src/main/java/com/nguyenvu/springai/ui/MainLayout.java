package com.nguyenvu.springai.ui;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;
import com.vaadin.flow.theme.lumo.LumoUtility;

public class MainLayout extends AppLayout {

    public MainLayout() {
        createHeader();
        createDrawer();
    }

    private void createHeader() {
        H1 appName = new H1("AI Flight Assistant");
        appName.getStyle()
                .set("font-size", "var(--lumo-font-size-l)")
                .set("margin", "0");

        var header = new HorizontalLayout(new DrawerToggle(), appName);
        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        header.setWidthFull();
        header.addClassNames(
                LumoUtility.Padding.Vertical.NONE,
                LumoUtility.Padding.Horizontal.MEDIUM
        );

        addToNavbar(header);
    }

    private void createDrawer() {
        SideNav nav = new SideNav();
        
        nav.addItem(new SideNavItem("Chat", ChatView.class, VaadinIcon.CHAT.create()));
        nav.addItem(new SideNavItem("Giới thiệu", AboutView.class, VaadinIcon.INFO_CIRCLE.create()));
        nav.addItem(new SideNavItem("Tài liệu", DocumentationView.class, VaadinIcon.BOOK.create()));

        VerticalLayout drawerContent = new VerticalLayout(nav);
        drawerContent.setSizeFull();
        drawerContent.setPadding(false);
        drawerContent.setSpacing(false);
        drawerContent.getStyle().set("background-color", "var(--lumo-contrast-5pct)");

        addToDrawer(drawerContent);
    }
}
