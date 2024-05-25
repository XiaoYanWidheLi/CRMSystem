package com.yrgo.dataaccess;

import com.yrgo.domain.Action;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class JpaActionDao implements ActionDao {
    @Override
    public void create(Action newAction) {

    }

    @Override
    public List<Action> getIncompleteActions(String userId) {
        return null;
    }

    @Override
    public void update(Action actionToUpdate) throws RecordNotFoundException {

    }

    @Override
    public void delete(Action oldAction) throws RecordNotFoundException {

    }
}
