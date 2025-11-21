// Estimate the value of Pi using Monte-Carlo Method, using parallel program
package assignments;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
class PiMonteCarlo {
	AtomicInteger nAtomSuccess; // ncible voir ligne 17
	int nThrows;
	double value;
	class MonteCarlo implements Runnable {
		@Override
		//  la méthode run() = 1 itération de Monte Carlo
        public void run() {
			double x = Math.random();
			double y = Math.random();
			if (x * x + y * y <= 1)
				nAtomSuccess.incrementAndGet(); // ecriture / lecture (get le résultat) donc nAtomSuccess est un moniteur
		}
	}

	public PiMonteCarlo(int i) { // le i représente n tot : taille de la boucle
		this.nAtomSuccess = new AtomicInteger(0);
		this.nThrows = i; // Nombre de lancer
		this.value = 0;
	}
	public double getPi() {
		int nProcessors = Runtime.getRuntime().availableProcessors();
		ExecutorService executor = Executors.newWorkStealingPool(nProcessors); // Ensemble de process pour du vol de tâche

        // Représente les itérations parallèles
        for (int i = 1; i <= nThrows; i++) {
			Runnable worker = new MonteCarlo();
			executor.execute(worker);
		}
		executor.shutdown(); // on les éteints
		while (!executor.isTerminated()) {
		}
		value = 4.0 * nAtomSuccess.get() / nThrows;
		return value;
	}
}
public class Assignment102 {
	public static void main(String[] args) {
		PiMonteCarlo PiVal = new PiMonteCarlo(100000);
		long startTime = System.currentTimeMillis();
		double value = PiVal.getPi();
		long stopTime = System.currentTimeMillis();
		System.out.println("Approx value:" + value);
		System.out.println("Difference to exact value of pi: " + (value - Math.PI));
		System.out.println("Error: " + (value - Math.PI) / Math.PI * 100 + " %");
		System.out.println("Available processors: " + Runtime.getRuntime().availableProcessors());
		System.out.println("Time Duration: " + (stopTime - startTime) + "ms");
	}
}