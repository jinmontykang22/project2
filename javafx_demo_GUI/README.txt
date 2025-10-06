This is a demo for running queries with javafx instead of the jdbc example done in class. 

The hierarchy/file structure should remain the exact same as how it's organized in this folder ('javafx_demo_GUI')

here are the commands to compile + run while in terminal (I used WINDOWS powershell, may not exactly work for Mac):

cd to_folder_location_on_your_device
javac --module-path lib --add-modules javafx.controls,javafx.fxml -cp "lib/*" -d bin src/jdbc_scenebuilder/*.java
java "-Djava.library.path=lib/bin" --module-path lib --add-modules javafx.controls,javafx.fxml -cp "bin;lib/*" jdbc_scenebuilder.DatabaseApp
