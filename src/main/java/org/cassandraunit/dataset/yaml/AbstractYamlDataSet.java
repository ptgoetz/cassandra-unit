package org.cassandraunit.dataset.yaml;

import java.io.InputStream;

import org.cassandraunit.dataset.DataSet;
import org.cassandraunit.dataset.ParseException;
import org.cassandraunit.dataset.commons.AbstractCommonsParserDataSet;
import org.cassandraunit.dataset.commons.ParsedKeyspace;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.error.YAMLException;

public abstract class AbstractYamlDataSet extends AbstractCommonsParserDataSet implements DataSet {

	private String dataSetLocation = null;

	public AbstractYamlDataSet(String dataSetLocation) {
		this.dataSetLocation = dataSetLocation;
		if (getInputDataSetLocation(dataSetLocation) == null) {
			throw new ParseException("Dataset not found");
		}
	}

	@Override
	protected ParsedKeyspace getParsedKeyspace() {
		InputStream inputDataSetLocation = getInputDataSetLocation(dataSetLocation);
		if (inputDataSetLocation == null) {
			throw new ParseException("Dataset not found in classpath");
		}

		Yaml yaml = new Yaml();
		try {
			ParsedKeyspace keyspace = yaml.loadAs(inputDataSetLocation, ParsedKeyspace.class);
			return keyspace;
		} catch (YAMLException e) {
			throw new ParseException(e);
		}
	}

	protected abstract InputStream getInputDataSetLocation(String dataSetLocation);

}
