
NET ID's: ss810, ra102
NAMES: Simran Singh, Adithya Raghunathan 

##Part 1

- My design is handling all visual aspects of the project. It encapsulates the idea the MVC model that is quite popular for projects of this manner. A major part of the front end design is to make sure it stays separate from a lot of the back end calculations that are happening. It just handles changes in the simulator by instantiating objects that handle the changes themselves.  

- SimulationInterface is dependent on the CellSimulator because the Simulation needs to be able to adapt to changes in the interface like speed alterations and clicking other simulations. The CellSociety has instances of the CellSimulator and the SimulationInterface as CellSociety has the main scenes. CellSimulator and SimulationInterface extend some sort of pane and are just the top and bottom of the CellSociety to keep the two separate visually, but they are still connected through their dependencies.

- For the most part, everything is meant to be closed currently, but there is potential to add more adjustable sections. The different sections of the interface currently just have different methods to create them, but they could end up being their own classes. This would make it easily adjustable to switch the interface.

- The should be no exceptions in the front end if everything in the backend is done correctly. The front end does handle displaying the errors that could occur in the xml reading.

- I believe this is good design as is separates the two parts of the interface in a reasonable way (the simulation and interactable elements). For the simulation, the front end just instantiates the backend and lets the model do all the calculations as it should. The interacble elements can also be added to relatively well by altering the methods that create them as they are broken down in left, middle, and right sections as well.  

##Part 2

- My area is linked to the other area of projects by taking in the outputs from the XML reading and acting as a visualization for the backend. It is the link between the two as the backend and xml don't communicate directly, but through me. 

- These dependencies naturally exist in the splitting of the project. It is logical to split it up into xml, backend, and front end so there has to be some sort of connection between the three sections. 

- We minimize these dependencies by only having one point of contact. For example, for xml, I only create one XML object at a time and the same for the backend. 

- There are currently no sub/super class dependencies on my end, but there are some for the backend that I will comment on. As we are implementing an interface, we only have methods that seem very reasonable for those of a ruleset like setParams, applyRules, etc. These are reasonable as they are methods you would normally expect a ruleset to have. Implementing the ruleSet interface makes it easy to add new RuleSets as well as know what each ruleset should be able to do.

##Part 3

- Case 1: Every update session the screen will need to change.
- Case 2: Handle how to change simulations as to the will of the user.
- Case 3: Handling getting the ruleset and giving it to the backed every time a new simulation is made.
- Case 4: Update the grid so it looks good no matter the sizes of the rows and columns
- Case 5: Programmatically defining all text for the interface.

- I'm most excited to create the grid with the interface. It involves a good amount of algebra and it it can get a little tricky, but it should be an interesting experience! 
- I'm most worried about how all our teams are going to communicate with each other. I'm not sure exactly how parts of the project are goingn to work and I'm worried that if an error occurs, I won't know how to fix it.