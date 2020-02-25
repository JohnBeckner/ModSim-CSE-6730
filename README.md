ModSim-CSE-6730
Team 8

Stochastic Simulation of Emergency Department Wait Times

John Beckner, Julia Corona, and Dylan Reese

Compile/Run project on pace cluster:

Navigate to src folder:
Run command: “cd Checkpoint/Simulator/src”

Load proper java version:
module load java/1.8.0_25 

Compile project:
Run command: “javac MedicalProfessional.java Formulas.java Event.java Range.java Priority.java Status.java Patient.java  Doctor.java Nurse.java Bed.java NurseLeavesEvent.java NurseAssignedEvent.java PatientAssessedEvent.java PatientAssignedBedEvent.java PatientLeavesEvent.java DoctorExamEvent.java Simulator.java”

Run the project:
Run command: “java Simulator”
