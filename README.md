ModSim-CSE-6730
Team 8

Stochastic Simulation of Emergency Department Wait Times

John Beckner, Julia Corona, and Dylan Reese


How to compile and run project on the PACE Cluster:

Login to PACE Cluster: https://docs.pace.gatech.edu/gettingStarted/logon/

Set up git: https://docs.pace.gatech.edu/storage/git/
Run command: “module load git/2.13.4”

Clone the project repository:
Run command: “git clone https://github.com/JohnBeckner/ModSim-CSE-6730.git”

Navigate to src folder:
Run command: “cd ModSim-CSE-6730/Simulator”

Submit job to job scheduler in PACE cluster:
Run command: “qsub -I -q coc-ice -l nodes=1 -l walltime=00:05:00”

Compile project:
Run command: “javac Main.java”

Run the project:
Run command: “java Main”
