[Imgur](http://i.imgur.com/AcYZHut.gifv)

'ant' should be sufficient to build and run the program, provided you have
ant installed. Tested on OS X.

See Controls.txt for precise instructions on how to manipulate the models.

This project features forward kinematics via keyboard input and inverse
kinematics via click-and-drag end effectors. The end effectors are separate
objects from the model that act as control points. I've taken care to implement
this functionality in an abstract way so that it can be used easily in the
future. Most of the code that supports the forward and inverse kinematics
is in the 'engine' package.

Selection of the end effectors is accomplished through raycasting. See
engine.physics.Raycaster.
