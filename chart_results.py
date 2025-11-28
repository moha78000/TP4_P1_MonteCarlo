import pandas as pd
import matplotlib.pyplot as plt

df = pd.read_csv("results.csv")

# Temps séquentiel : le cas n_workers = 1
t1 = df[df["n_workers"] == 1]["temps_ns"].values[0]

df["speedup"] = t1 / df["temps_ns"]

plt.figure()
plt.plot(df["n_workers"], df["speedup"], marker="o", label="Speedup mesuré")

# Speedup idéal
plt.plot([1, df["n_workers"].max()],
         [1, df["n_workers"].max()],
         linestyle='--', label="Speedup idéal")
    

plt.xlabel("Nombre de workers")
plt.ylabel("Speedup")
plt.title("Strong scaling  Monte Carlo Pi")
plt.legend()
plt.grid()
plt.show()
