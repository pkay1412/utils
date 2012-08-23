package net.sf.ahtutils.controller.facade;

import java.util.List;

import net.sf.ahtutils.controller.interfaces.UtilsFacade;
import net.sf.ahtutils.controller.util.ParentPredicate;
import net.sf.ahtutils.exception.ejb.UtilsContraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsIntegrityException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.model.interfaces.EjbRemoveable;
import net.sf.ahtutils.model.interfaces.EjbWithCode;
import net.sf.ahtutils.model.interfaces.EjbWithId;
import net.sf.ahtutils.model.interfaces.EjbWithName;
import net.sf.ahtutils.model.interfaces.EjbWithNr;
import net.sf.ahtutils.model.interfaces.EjbWithType;
import net.sf.ahtutils.model.interfaces.with.EjbWithPosition;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AbstractDummyFacade implements UtilsFacade
{
	final static Logger logger = LoggerFactory.getLogger(AbstractDummyFacade.class);

	@Override
	public <T> T find(Class<T> type, long id) throws UtilsNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T extends EjbWithCode> T fByCode(Class<T> type, String code)
			throws UtilsNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T extends EjbWithName> T fByName(Class<T> type, String name)
			throws UtilsNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T extends EjbWithNr, P extends EjbWithId> T fByNr(Class<T> type,
			String parentName, P parent, int nr) throws UtilsNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> List<T> all(Class<T> type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T extends EjbWithPosition> List<T> allOrdered(Class<T> type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T extends EjbWithType> List<T> allForType(Class<T> clazz,
			String type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T extends EjbWithId> T save(T o)
			throws UtilsContraintViolationException, UtilsLockingException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T persist(T o) throws UtilsContraintViolationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T update(T o) throws UtilsContraintViolationException,
			UtilsLockingException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T extends EjbRemoveable> void rm(T o)
			throws UtilsIntegrityException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public <T extends EjbWithId, I extends EjbWithId> List<T> allForParent(
			Class<T> type, String p1Name, I p1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T extends EjbWithId, I extends EjbWithId> List<T> allForParent(
			Class<T> type, String p1Name, I p1, String p2Name, I p2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T extends EjbWithId, OR extends EjbWithId, AND extends EjbWithId> List<T> fForAndOrParents(
			Class<T> queryClass, List<ParentPredicate<AND>> lpAnd,
			List<ParentPredicate<OR>> lpOr) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T extends EjbWithId, P extends EjbWithId, OR extends EjbWithId, AND extends EjbWithId> List<T> fForAndOrGrandParents(
			Class<T> queryClass, Class<P> parentClass, String parentName,
			List<ParentPredicate<AND>> lpAnd, List<ParentPredicate<OR>> lpOr) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T extends EjbWithId, P extends EjbWithId, OR1 extends EjbWithId, OR2 extends EjbWithId> List<T> fGrandParents(
			Class<T> queryClass, Class<P> parentClass, String parentName,
			List<ParentPredicate<OR1>> lpOr1, List<ParentPredicate<OR2>> lpOr2) {
		// TODO Auto-generated method stub
		return null;
	}
	

}