package admiral_plasma.poetry.java.simple;

import java.util.ArrayList;
import java.util.List;

public class Later<E extends Throwable> {

	private final List<Postponed<E>> postponed = new ArrayList<>();

	public void isNow() throws E {
		for (Postponed<E> runnable : postponed) {
			runnable.run();
		}
	}

	public void run(Postponed<E> runnable) {
		postponed.add(runnable);
	}

	public static interface Postponed<E extends Throwable> {

		void run() throws E;
	}
}
