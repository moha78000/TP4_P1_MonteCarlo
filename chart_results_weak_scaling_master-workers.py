import pandas as pd
import matplotlib.pyplot as plt

# Charger les résultats
df = pd.read_csv("pi_results_with_weak_scaling_master-workers.csv")

# Temps pour 1 worker (référence)
t1 = df[df["n_workers"] == 1]["temps_ms"].values[0]

# Efficacité weak scaling : ratio du temps de référence
df["efficiency"] = t1 / df["temps_ms"]

plt.figure(figsize=(8,5))

# Courbe d'efficacité mesurée
plt.plot(df["n_workers"], df["efficiency"], marker="o", label="Weak scaling mesuré")

# Courbe idéale (efficacité = 1)
plt.axhline(y=1, linestyle='--', label="Idéal (E=1)")

plt.xlabel("Nombre de workers")
plt.ylabel("Efficacité (T1 / Tp)")
plt.title("Weak Scaling - Monte Carlo Pi")
plt.xticks(df["n_workers"])
plt.ylim(0, 1.2)  # Pour mieux visualiser
plt.grid(True)
plt.legend()
plt.show()
