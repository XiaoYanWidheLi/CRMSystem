package com.yrgo.dataaccess;

import java.util.List;

import com.yrgo.domain.Action;

public interface ActionDao {
	void create(Action newAction);
	List<Action> getIncompleteActions(String userId);
	void update(Action actionToUpdate) throws RecordNotFoundException;
	void delete(Action oldAction) throws RecordNotFoundException;
}
