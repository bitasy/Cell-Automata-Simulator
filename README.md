# cellsociety

CompSci 308 Cell Society Project

Simran, Paulo, and Brian worked on the project.
We started around the 17th and finished on October 2nd. Collectively we probably spent around 80ish hours I would say creating this project. 
Brian worked primarily on the backend, Paulo on the back end, and Simran on the front end, but there was a lot of communication between the three of us on figuring out how the individual parts worked though. 

The main files used to create the project is CellSociety in the front end. Other than that, the program requires a custom XML/ruleset pair to run a novel simulation along with the closed files. 
We tested the files with the current rulesets and XML files. We expect to handle incorrect parameters in XML files and most errors with the XML files. We also have cases for if certain classes (like the rulesets/grids) that we assume exist, don’t exist. We handle these with alert pop ups that let the user know there was an error.
The formats of the XML files are described in another README file (see src/xml_start). If the rulesets for a particular object don’t exist, they must also implement/extend the relevant ruleset objects. 

The Add Simulation button creates a new window that allows the user to select their own simulation and their own sliders. This allows the user to customize their view to their own specifications. Change the speed, have their own simulations, etc. All of this is easily implemented with new windows. Sectioning off the display for multiple simulations has a lot of problems. Resizing a lof of the different simulations in a reasonable and elegant way is unrealistic. With potentially multiple views and different shaped simulations, it could look pretty gross to section off the display.

The graph also has its own window. This was done so that the user could place it wherever s/he wanted or could delete it to begin with. It also made a lot of the spacing issues much easier to deal with. 

No major bugs/crashes/problems with project functionality in regards to what we have implemented.