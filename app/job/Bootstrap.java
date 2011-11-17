package job;

import play.jobs.Job;
import play.jobs.OnApplicationStart;
import play.test.Fixtures;

@OnApplicationStart
public class Bootstrap extends Job<Object> {

	@Override
	public void doJob() {
		Fixtures.deleteDatabase();
		Fixtures.loadModels("initial-data/all.yml");
	}
}