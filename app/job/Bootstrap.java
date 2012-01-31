package job;

import models.User;
import play.jobs.Job;
import play.jobs.OnApplicationStart;
import play.test.Fixtures;

@OnApplicationStart
public class Bootstrap extends Job<Object> {

	@Override
	public void doJob() {
		if(User.count() == 0) {
			//Fixtures.deleteDatabase();
			Fixtures.loadModels("initial-data/all.yml");
		}
	}
}