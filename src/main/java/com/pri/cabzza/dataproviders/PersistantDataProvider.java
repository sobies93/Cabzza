package com.pri.cabzza.dataproviders;

/**
 *
 * @author rasgrass
 */
import com.pri.cabzza.dataproviders.exceptions.DataProviderPersistanceException;

import java.util.List;

/**
 *
 * @author Mikolaj Manski
 * @param <T>
 */
public interface PersistantDataProvider<T extends List<?>> extends DataProvider {

	void storeData(T quotes) throws DataProviderPersistanceException;

}
