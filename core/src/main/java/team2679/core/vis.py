import matplotlib.pyplot as plt
import pandas as pd

ves = pd.read_csv(r"C:\Users\OWNER\git\repository\TigerCode\velocities.csv")
max_ves = pd.read_csv(r"C:\Users\OWNER\git\repository\TigerCode\max_velocities.csv")
time_ves = pd.read_csv(r"C:\Users\OWNER\git\repository\TigerCode\time_velocities.csv")
points = pd.read_csv(r"C:\Users\OWNER\git\repository\TigerCode\points.csv")

time_ves.plot(title='Motors velocities for time')
ves.plot(title='Motors velocties for distance')
max_ves.plot(title='Robot max velocities')
points.plot(x='Right', y=' Left', title='Path')

plt.show()