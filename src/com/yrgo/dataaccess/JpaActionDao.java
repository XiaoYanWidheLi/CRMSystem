package com.yrgo.dataaccess;

import com.yrgo.domain.Action;
import com.yrgo.domain.Customer;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

public class JpaActionDao implements ActionDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void create(Action newAction) {
        em.persist(newAction);
    }

    @Override
    public List<Action> getIncompleteActions(String userId) {
        return em.createQuery("select action from Action as action" +
                " where action.owningUser =:user" +
                " and action.complete =:false" , Action.class).setParameter("user", userId).getResultList();

    }

    @Override
    public void update(Action actionToUpdate) throws RecordNotFoundException {
        if (em.find(Customer.class, actionToUpdate.getActionId()) == null) {
            throw new RecordNotFoundException("Action not found");
        }
        else {
            em.merge(actionToUpdate);
        }
    }

    @Override
    public void delete(Action oldAction) throws RecordNotFoundException {
            try {
                em.remove(oldAction);
            }
            catch (javax.persistence.EntityNotFoundException e) {
                throw new RecordNotFoundException(e.getMessage());
            }

    }
}
