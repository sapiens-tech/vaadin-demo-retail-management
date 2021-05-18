package io.sapiens.app.backend.service;

import io.sapiens.awesome.service.AbstractService;
import io.sapiens.app.backend.dao.StaticPageDao;
import io.sapiens.app.backend.model.StaticPage;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class StaticPageService extends AbstractService<StaticPage> {
  @Autowired
  public void setDao(StaticPageDao dao) {
    super.setDao(dao);
  }

  public void delete(io.sapiens.app.ui.models.StaticPage.Edit edit) {
    delete(edit.getId());
  }

  public void save(io.sapiens.app.ui.models.StaticPage.Edit edit) {
    StaticPage staticPage = new StaticPage();
    BeanUtils.copyProperties(edit, staticPage);
    saveOrUpdate(staticPage);
  }

  public Collection<io.sapiens.app.ui.models.StaticPage.List> retrieveAllPages() {
    List<io.sapiens.app.ui.models.StaticPage.List> result = new ArrayList<>();
    List<StaticPage> data = dao.findAll();

    for (StaticPage page : data) {
      var list = new io.sapiens.app.ui.models.StaticPage.List();
      BeanUtils.copyProperties(page, list);
      result.add(list);
    }

    return result;
  }
}
