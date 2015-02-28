LevelCreator
============

An application which makes it easier to create levels for your games. Mainly used if you're storing your levels as 2D arrays. 
Overview
----------

This program is basically a simple level editor. Its canvas is consisted of DisplayTextures (extended JButtons), and each individual DisplayTexture represents a single texture. Here is a screenshot of what the interface looks like: 

![](https://raw.githubusercontent.com/rpusec/LevelCreator/master/github_imgs/main_interface.png)

From the left side we can see something that I labeled as <b>AvailableTextures</b> (or 'ATs'), which are textures that are available for use. 

On the right side (canvas), we can see <b>DisplayTextures</b> (or 'DTs'), which are the textures that are displayed on the canvas. 

Each DisplayTexture has a reference to an AvailableTexture, and when a DisplayTexture is changed, a different AvailableTexture is referenced. 

How to use
----------

To select a single <b>DisplayTexture</b>, simply <b>left-click</b> on it and it'll be highlighted. 

To select <b>multiple</b> DisplayTextures (one by one), hold down the <b>CTRL</b> key and click on your DTs. 

To select <b>multiple DTs <i>at once</i></b>, hold down the <b>SHIFT</b> key. 

Each AvailableTexture is denoted by a certain keyboard <b>key</b>, and to use a certain AT, you'd need to click on a DT and press the specified <b>key</b> by which your AT is denoted. As you can see in the above screenshoot, the <b>cloud texture</b> is denoted by the <b>c key</b>, so the cloud AT can be used by pressing the said key. 

Saving and Opening Levels
----------

It is possible to save your levels for later usage. The levels are saved as an <b>XML file</b>. That XML file contains all of the information about the level, such as stage height and width, AvailableTextures, and DisplayTextures. 

When the XML files are opened from the application, the application reads the XML file and assembles the canvas and the components appropriately. 

Using levels
----------

To actually use your level in your application, go to `Options -> Print Level`. This will pop-up another JFrame window which displays the level as text. 

![](https://raw.githubusercontent.com/rpusec/LevelCreator/master/github_imgs/printed_lvl.png)

The application printed out the level exactly how the level was built. Afterwards you can use that 2D array in your games in whichever way you find satisfactory. 
