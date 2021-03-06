package io.sapiens.app.ui.views;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.board.Row;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import io.sapiens.awesome.ui.components.FlexBoxLayout;
import io.sapiens.awesome.ui.components.ListItem;
import io.sapiens.awesome.ui.layout.size.*;
import io.sapiens.awesome.ui.util.IconSize;
import io.sapiens.awesome.ui.util.LumoStyles;
import io.sapiens.awesome.ui.util.TextColor;
import io.sapiens.awesome.ui.util.UIUtil;
import io.sapiens.awesome.ui.util.css.BorderRadius;
import io.sapiens.awesome.ui.util.css.BoxSizing;
import io.sapiens.awesome.ui.util.css.Display;
import io.sapiens.awesome.ui.util.css.Shadow;
import io.sapiens.awesome.ui.views.ViewFrame;
import io.sapiens.app.ui.BaseLayout;

@CssImport("./styles/views/statistics.css")
@PageTitle("Welcome")
@Route(value = "", layout = BaseLayout.class)
public class Home extends ViewFrame {
  private static final String CLASS_NAME = "statistics";
  public static final String MAX_WIDTH = "1024px";

  public Home() {
    setId("home");
    setViewContent(createContent());
  }

  private Component createContent() {
    Component payments = createContainer("Payments", VaadinIcon.CREDIT_CARD);
    Component transactions = createContainer("Transactions", VaadinIcon.MONEY_EXCHANGE);
    Component docs = createDocs();

    FlexBoxLayout content = new FlexBoxLayout(payments, transactions, docs);
    content.setAlignItems(FlexComponent.Alignment.CENTER);
    content.setFlexDirection(FlexLayout.FlexDirection.COLUMN);

    return content;
  }

  private FlexBoxLayout createHeader(VaadinIcon icon, String title) {
    FlexBoxLayout header =
        new FlexBoxLayout(
            UIUtil.createIcon(IconSize.M, TextColor.TERTIARY, icon), UIUtil.createH3Label(title));
    header.setAlignItems(FlexComponent.Alignment.CENTER);
    header.setMargin(Bottom.L, Horizontal.RESPONSIVE_L);
    header.setSpacing(Right.L);
    return header;
  }

  private Component createContainer(String title, VaadinIcon icon) {
    FlexBoxLayout transactions = new FlexBoxLayout(createHeader(icon, title), createAreaChart());
    transactions.setBoxSizing(BoxSizing.BORDER_BOX);
    transactions.setDisplay(Display.BLOCK);
    transactions.setMargin(Top.XL);
    transactions.setMaxWidth(MAX_WIDTH);
    transactions.setPadding(Horizontal.RESPONSIVE_L);
    transactions.setWidthFull();
    return transactions;
  }

  private Component createAreaChart() {
    FlexBoxLayout card = new FlexBoxLayout();
    card.setBackgroundColor(LumoStyles.Color.BASE_COLOR);
    card.setBorderRadius(BorderRadius.S);
    card.setBoxSizing(BoxSizing.BORDER_BOX);
    card.setHeight("400px");
    card.setPadding(Uniform.M);
    card.setShadow(Shadow.XS);
    return card;
  }

  private Component createDocs() {
    Component reports = createReports();
    Component logs = createLogs();

    Row docs = new Row(reports, logs);
    docs.addClassName(LumoStyles.Margin.Top.XL);
    UIUtil.setMaxWidth(MAX_WIDTH, docs);
    docs.setWidthFull();

    return docs;
  }

  private Component createReports() {
    FlexBoxLayout header = createHeader(VaadinIcon.RECORDS, "Reports");

    Tabs tabs = new Tabs();
    for (String label : new String[] {"All", "Archive", "Workflows", "Support"}) {
      tabs.add(new Tab(label));
    }

    Div items =
        new Div(
            new ListItem(
                UIUtil.createIcon(IconSize.M, TextColor.TERTIARY, VaadinIcon.CHART),
                "Weekly Report",
                "Generated Oct 5, 2018",
                createInfoButton()),
            new ListItem(
                UIUtil.createIcon(IconSize.M, TextColor.TERTIARY, VaadinIcon.SITEMAP),
                "Payment Workflows",
                "Last modified Oct 24, 2018",
                createInfoButton()));
    items.addClassNames(LumoStyles.Padding.Vertical.S);

    Div card = new Div(tabs, items);
    UIUtil.setBackgroundColor(LumoStyles.Color.BASE_COLOR, card);
    UIUtil.setBorderRadius(BorderRadius.S, card);
    UIUtil.setShadow(Shadow.XS, card);

    FlexBoxLayout reports = new FlexBoxLayout(header, card);
    reports.addClassName(CLASS_NAME + "__reports");
    reports.setFlexDirection(FlexLayout.FlexDirection.COLUMN);
    reports.setPadding(Bottom.XL, Left.RESPONSIVE_L);
    return reports;
  }

  private Component createLogs() {
    FlexBoxLayout header = createHeader(VaadinIcon.EDIT, "Logs");

    Tabs tabs = new Tabs();
    for (String label : new String[] {"All", "Transfer", "Security", "Change"}) {
      tabs.add(new Tab(label));
    }

    Div items =
        new Div(
            new ListItem(
                UIUtil.createIcon(IconSize.M, TextColor.TERTIARY, VaadinIcon.EXCHANGE),
                "Transfers (October)",
                "Generated Oct 31, 2018",
                createInfoButton()),
            new ListItem(
                UIUtil.createIcon(IconSize.M, TextColor.TERTIARY, VaadinIcon.SHIELD),
                "Security Log",
                "Updated 16:31 CET",
                createInfoButton()));
    items.addClassNames(LumoStyles.Padding.Vertical.S);

    Div card = new Div(tabs, items);
    UIUtil.setBackgroundColor(LumoStyles.Color.BASE_COLOR, card);
    UIUtil.setBorderRadius(BorderRadius.S, card);
    UIUtil.setShadow(Shadow.XS, card);

    FlexBoxLayout logs = new FlexBoxLayout(header, card);
    logs.addClassName(CLASS_NAME + "__logs");
    logs.setFlexDirection(FlexLayout.FlexDirection.COLUMN);
    logs.setPadding(Bottom.XL, Right.RESPONSIVE_L);
    return logs;
  }

  private Button createInfoButton() {
    Button infoButton = UIUtil.createSmallButton(VaadinIcon.INFO);
    infoButton.addClickListener(e -> UIUtil.showNotification("Not implemented yet."));
    return infoButton;
  }
}
