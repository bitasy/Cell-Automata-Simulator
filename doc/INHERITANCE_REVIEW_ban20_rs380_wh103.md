Worked With: rs380

#Inheritance Review#

##Part 1##

  The back-end part that I am writing encapsulates the running of the simulation itself. Thus the other parts don't know that the grid is being updated or that the Rectangles in the display are changing. The set of rules to use (which subclass of RuleSet) is chosen in advance to Grid, which uses the abstract class. 
  
  I would like to keep as little open as possible. The Grid class will encapsulate the actual grid of Cells, and work only with rules to work. It also takes parameters as specified by SimulationInterface.
  
  There will definitely be problems dealing with updating conflicts, as "simultaneous" updating is not exactly possible. There are multiple ways to handle this, but a good idea might be to use a priority queue to record all possible states and override them to leave only one.
  
##Part 2##

  The backend requires some input from the user, as things like speed can be specified during runtime. However, the implementation of how to rest of the program gets this information is unimportant to me; I only need some numbers, or whatever else the ruleset I'm using requires. The way we minimize these dependencies is by limiting input to only one other class, Simulation Interface. Thus that should be the only class that knows or cares about how I work.
  The RulesSet hierarchy is something to keep in mind, it should be versatile enough for Grid to use, but open enough to change for a variety of simulations. 
  
##Part 3##
  
####Use Cases####
  
  1. Initialize a cell grid for a new simulation.
  2. Update a cell based on a simulation's rules.
  3. Settle a conflict between two cells that want to move to the same spot.
  4. Assign the correct rules to update with, given a simulation.
  5. Change the size of the grid.
  
  I am most excited to work on creating a workflow for rules to update for every new generation of cells. I think this will be a major milestone in my project. However, I am most worried about how I will have to solve conflicts, because it seems like a very complicated issue with no exact right answer. I will probably end up deleting a few sharks along the way, but oh well.