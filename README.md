LevelCreator
============

An application which makes it easier to create levels for your games. Mainly used if you're storing your levels as 2D arrays. 

Overview
----------

This program is basically a simple level editor. Its canvas is consisted of JButtons, and each individual JButton represents a single texture. Here is a screenshot of what the interface looks like: 

![](https://raw.githubusercontent.com/rpusec/LevelCreator/master/github_imgs/main_interface.png)

From the left side we can see something that I labeled as <b>AvailableTextures</b> (or 'ATs'), which are textures that are available for use. 

On the right side, we can see <b>DisplayTextures</b> (or 'DTs'), which are the textures that are displayed on the canvas. 

Each DisplayTexture has a reference to an AvailableTexture, and when a DisplayTexture is changed, a different AvailableTexture is referenced. 
