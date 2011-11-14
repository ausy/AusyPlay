package job;

import models.Book;
import play.jobs.Job;
import play.jobs.OnApplicationStart;
import play.test.Fixtures;

@OnApplicationStart
public class Bootstrap extends Job {

	@Override
	public void doJob() {
//		Fixtures.deleteDatabase();
		if (Book.count() == 0) {
			Fixtures.loadModels("initial-data/all.yml","initial-data/history.yml");
		}
	}
}