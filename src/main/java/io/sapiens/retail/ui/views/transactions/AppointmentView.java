package io.sapiens.retail.ui.views.transactions;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.tabs.TabsVariant;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import io.sapiens.awesome.ui.components.FlexBoxLayout;
import io.sapiens.awesome.ui.components.ListItem;
import io.sapiens.awesome.ui.components.detailsdrawer.DetailsDrawer;
import io.sapiens.awesome.ui.components.detailsdrawer.DetailsDrawerHeader;
import io.sapiens.awesome.ui.components.navigation.bar.AppBar;
import io.sapiens.awesome.ui.layout.size.Bottom;
import io.sapiens.awesome.ui.layout.size.Horizontal;
import io.sapiens.awesome.ui.layout.size.Top;
import io.sapiens.awesome.ui.layout.size.Vertical;
import io.sapiens.awesome.ui.util.FontSize;
import io.sapiens.awesome.ui.util.LumoStyles;
import io.sapiens.awesome.ui.util.TextColor;
import io.sapiens.awesome.ui.util.UIUtil;
import io.sapiens.awesome.ui.util.css.BoxSizing;
import io.sapiens.awesome.ui.util.css.WhiteSpace;
import io.sapiens.awesome.ui.views.SplitViewFrame;
import io.sapiens.retail.backend.dummy.Payment;
import io.sapiens.retail.ui.BaseLayout;

@PageTitle("Appointments")
@Route(value = "appointments", layout = BaseLayout.class)
public class AppointmentView extends SplitViewFrame {

  private Grid<Payment> grid;
  private ListDataProvider<Payment> dataProvider;
  private DetailsDrawer detailsDrawer;

  @Override
  protected void onAttach(AttachEvent attachEvent) {
    super.onAttach(attachEvent);
    initAppBar();
    setViewContent(createContent());
    setViewDetails(createDetailsDrawer());
    filter();
  }

  private void initAppBar() {
    AppBar appBar = BaseLayout.get().getAppBar();
    for (Payment.Status status : Payment.Status.values()) {
      appBar.addTab(status.getName());
    }
    appBar.addTabSelectionListener(
        e -> {
          filter();
          detailsDrawer.hide();
        });
    appBar.centerTabs();
  }

  private Component createContent() {
    FlexBoxLayout content = new FlexBoxLayout(createGrid());
    content.setBoxSizing(BoxSizing.BORDER_BOX);
    content.setHeightFull();
    content.setPadding(Horizontal.RESPONSIVE_X, Top.RESPONSIVE_X, Bottom.RESPONSIVE_X);
    return content;
  }

  private Component createGrid() {
    // Create a new calendar instance and attach it to our layout
    //    FullCalendar calendar = FullCalendarBuilder.create().build();
    //
    //    // Create a initial sample entry
    //    Entry entry = new Entry();
    //    entry.setTitle("Some event");
    //    entry.setStart(LocalDate.now().withDayOfMonth(3).atTime(10, 0), calendar.getTimezone());
    //    entry.setEnd(entry.getStart().plusHours(2), calendar.getTimezone());
    //    entry.setColor("#ff3333");
    //    calendar.addEntries(entry);
    //    return calendar;
    return new Div();
  }

  private Component createFromInfo(Payment payment) {
    ListItem item = new ListItem(payment.getFrom(), payment.getFromIBAN());
    item.setPadding(Vertical.XS);
    return item;
  }

  private Component createToInfo(Payment payment) {
    ListItem item = new ListItem(payment.getTo(), payment.getToIBAN());
    item.setPadding(Vertical.XS);
    return item;
  }

  private Component createAmount(Payment payment) {
    Double amount = payment.getAmount();
    return UIUtil.createAmountLabel(amount);
  }

  private DetailsDrawer createDetailsDrawer() {
    detailsDrawer = new DetailsDrawer(DetailsDrawer.Position.RIGHT);

    // Header
    Tab details = new Tab("Details");
    Tab attachments = new Tab("Attachments");
    Tab history = new Tab("History");

    Tabs tabs = new Tabs(details, attachments, history);
    tabs.addThemeVariants(TabsVariant.LUMO_EQUAL_WIDTH_TABS);
    tabs.addSelectedChangeListener(
        e -> {
          Tab selectedTab = tabs.getSelectedTab();
          if (selectedTab.equals(details)) {
            detailsDrawer.setContent(
                createDetails(grid.getSelectionModel().getFirstSelectedItem().get()));
          } else if (selectedTab.equals(attachments)) {
            detailsDrawer.setContent(createAttachments());
          } else if (selectedTab.equals(history)) {
            detailsDrawer.setContent(createHistory());
          }
        });

    DetailsDrawerHeader detailsDrawerHeader = new DetailsDrawerHeader("Payment Details", tabs);
    detailsDrawerHeader.addCloseListener(buttonClickEvent -> detailsDrawer.hide());
    detailsDrawer.setHeader(detailsDrawerHeader);

    return detailsDrawer;
  }

  private void showDetails(Payment payment) {
    detailsDrawer.setContent(createDetails(payment));
    detailsDrawer.show();
  }

  private Component createDetails(Payment payment) {
    ListItem status =
        new ListItem(payment.getStatus().getIcon(), payment.getStatus().getName(), "Status");

    status.getContent().setAlignItems(FlexComponent.Alignment.BASELINE);
    status.getContent().setSpacing(Bottom.XS);
    UIUtil.setTheme(payment.getStatus().getTheme().getThemeName(), status.getPrimary());
    UIUtil.setTooltip(payment.getStatus().getDesc(), status);

    ListItem from =
        new ListItem(
            UIUtil.createTertiaryIcon(VaadinIcon.UPLOAD_ALT),
            payment.getFrom() + "\n" + payment.getFromIBAN(),
            "Sender");
    ListItem to =
        new ListItem(
            UIUtil.createTertiaryIcon(VaadinIcon.DOWNLOAD_ALT),
            payment.getTo() + "\n" + payment.getToIBAN(),
            "Receiver");
    ListItem amount =
        new ListItem(
            UIUtil.createTertiaryIcon(VaadinIcon.DOLLAR),
            UIUtil.formatAmount(payment.getAmount()),
            "Amount");
    ListItem date =
        new ListItem(
            UIUtil.createTertiaryIcon(VaadinIcon.CALENDAR),
            UIUtil.formatDate(payment.getDate()),
            "Date");

    for (ListItem item : new ListItem[] {status, from, to, amount, date}) {
      item.setReverse(true);
      item.setWhiteSpace(WhiteSpace.PRE_LINE);
    }

    Div details = new Div(status, from, to, amount, date);
    details.addClassName(LumoStyles.Padding.Vertical.S);
    return details;
  }

  private Component createAttachments() {
    Label message = UIUtil.createLabel(FontSize.S, TextColor.SECONDARY, "Not implemented yet.");
    message.addClassNames(
        LumoStyles.Padding.Responsive.Horizontal.L, LumoStyles.Padding.Vertical.L);
    return message;
  }

  private Component createHistory() {
    Label message = UIUtil.createLabel(FontSize.S, TextColor.SECONDARY, "Not implemented yet.");
    message.addClassNames(
        LumoStyles.Padding.Responsive.Horizontal.L, LumoStyles.Padding.Vertical.L);
    return message;
  }

  private void filter() {
    Tab selectedTab = BaseLayout.get().getAppBar().getSelectedTab();
    if (selectedTab != null)
      dataProvider.setFilterByValue(
          Payment::getStatus, Payment.Status.valueOf(selectedTab.getLabel().toUpperCase()));
  }
}
