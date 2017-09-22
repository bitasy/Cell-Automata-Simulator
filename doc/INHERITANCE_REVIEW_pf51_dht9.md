
NET ID's: pf51, dht9
NAMES: Paulo Flecha, David Tran

##Part 1

- **Implementation decision my design is encapsulating** for other areas of the program is the painting and updating elements of the Screen. It will receive the nodes and elements of it from the SimulationInterface, and will be responsible for adding them to the view, such that SimulationInterface does not have direct access to the Screen itself.

- **Inheritance hierarchies** I intend to build within my area will likely be Screen inheriting a Scene class; they are based around the idea of having the user interact with elements of the scene itself in a dynamic way.

- Parts within my area I'm trying to **make open** include the XML file that will include all the starting positions and other parameters about the simulations; this will be changed when new classes are changed. Parts within my area I'm trying to **make closed** would be SimulationLoader, since that class will essentially read the XML and shouldn't have to change assuming the XML sticks to a particular format of passing on relevant information.

- **One exception** that may occur in my area can include the XML file not being able to be read by the correct class; the way I'd handle would be to pop up a display saying there was an error with the XML reading and recommending certain steps of what to do.

- **Design is good** because the classes are (so far) seeming that they will be somewhat short and simple as to what they do, thus delegating functionality efficiently such that the code is clean.

##Part 2

- My area is **linked to the project** such that it defines the parameters of each simulation with the XML data, and also handles the GUI elements on the Screen, and lastly, initializes the whole program with CellSociety.java. It is dependent on the Grid and Cells and back end calculations updating the simulation.

- There are a **lot of dependencies**, but one way to minimize one of our dependencies can be to have the SimulationLoader directly exchange information with SimulationInterface, instead of having to pass through Screen and eventually get to SimulationInterface, as SimulationLoader will then be the only class having a dependency with Screen itself. This will minimize one of our dependencies.

##Part 3

- **Use case 1:** Specifying labels of all elements, sliders and buttons on screen with XML file.
- **Use case 2:** Specifying initial conditions of certain simulation.
- **Use case 3:** Specifying set of rules that will govern a particular simulation by giving what class will dictate how the cells in the grid are updated.
- **Use case 4:** Handle all aspects of GUI and displaying information on the screen.
- **Use case 5:** Starting the whole program in CellSociety.java

- Feature I'm **excited** to work on is finding a way to integrate XML with relevant information for the simulations; will be an interesting challenge;
- Design problem I'm most **worried** about is integrating all classes and pieces of the puzzle together.