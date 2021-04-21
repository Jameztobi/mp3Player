// Project done by Oluwatobi James Student No: 3025513 and Taofeek Shittu Student No: 3017028


//Standard java imports 
import java.io.File;

import java.io.IOException;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.Scene;

//Component in this application
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
//Imports for layout 
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;




public class MP3BlastBox extends Application {

	//Declare a progress bar.
	Slider  timerSlider;

	Slider volumeSlider; 

	//Labels 
	Label lblAvailableTrack, lblSelectedTrack, lblVolume, lblStatus, lblPlay;  

	//Buttons
	Button btnAdd, btnRemove, btnRemoveAll, btnPlay, btnPause, btnStop;

	//Initiate the listview variables
	ListView <String>lvAvailable;
	ListView <String>lvSelected;


	//Array for the files.
	String [] fileList;

	//String variable
	String mySelectedMusic;

	//Get the selected item from the list view
	ArrayList<String> arrList=new ArrayList<String>();

	// media player
	MediaPlayer mediaPlayer;

	// int counter
	int counter=0;

	// Duration object
	Duration duration;

	// File object
	File file;

	// Media Object
	Media media;

	// This is a change listener object that is used to alter the duration
	ChangeListener<Duration> progressChangeListener;

	// meta data change listener
	MapChangeListener<String, Object> metadataChangeListener;


	/*
	 *An overloaded constructor 
	 * */
	public MP3BlastBox() {
		//Initialise the labels 
		lblAvailableTrack=new Label("Available Tracks: "); 
		lblSelectedTrack=new Label("Selected Tracks: ");
		lblAvailableTrack.setStyle("-fx-font: 17 arial; ");
		lblSelectedTrack.setStyle("-fx-font: 17 arial; ");
		lblAvailableTrack.setPadding(new Insets(0, 10, 20, 10));
		lblSelectedTrack.setPadding(new Insets(0, 10, 20, 10));
		lblVolume=new Label("Volume:");
		lblStatus=new Label("Status:");
		lblPlay=new Label("");

		//Initialise the button
		btnAdd=new Button("Add >");
		btnAdd.setDisable(true);
		btnRemove=new Button("<Remove");
		btnRemove.setDisable(true);
		btnRemoveAll=new Button("<<RemoveAll");
		btnRemoveAll.setDisable(true);
		btnPlay=new Button("Play");
		btnPlay.setDisable(true);
		btnPause=new Button("Pause");
		btnPause.setDisable(true);
		btnStop=new Button("Stop");
		btnStop.setDisable(true);

		//Set the width of the button
		btnAdd.setMinWidth(150);
		btnRemove.setMinWidth(150);
		btnRemoveAll.setMinWidth(150);
		btnPlay.setMinWidth(150);
		btnPause.setMinWidth(150);
		btnStop.setMinWidth(150);

		//Initialise the Slider
		timerSlider =new Slider();

		//volumeSlider=new Slider(1,100,20);
		volumeSlider=new Slider(1,100,20);

		//To set the width and height of the progressBar
		volumeSlider.setOrientation(Orientation.HORIZONTAL);

		timerSlider.setOrientation(Orientation.HORIZONTAL);


		///Initiating the listview variables
		lvAvailable=new ListView<String>();
		lvSelected=new ListView<String>();


		//set the size constraints to prevent resizing 
		lvAvailable.setMinSize(150.0, Control.USE_PREF_SIZE);
		lvSelected.setMinSize(150.0, Control.USE_PREF_SIZE);



	}

	@Override
	public void init() {

	   /*
		* This takes care of the action carried out after the click of the mouse
		* Event Handling
		*/
        
		lvAvailable.setOnMouseReleased(ae->{
			boolean check=false;
			if(check==false) {
				btnAdd.setDisable(false);

			}
			else {
				btnAdd.setDisable(true);

			}

		});

		lvSelected.setOnMouseReleased(ae->{
			boolean check=false;
			if(check==false) {
				btnPlay.setDisable(false);
				btnPause.setDisable(false);
				btnStop.setDisable(false);
				btnRemove.setDisable(false);
				btnRemoveAll.setDisable(false);
			}
			else {
				btnPlay.setDisable(true);
				btnPause.setDisable(true);
				btnStop.setDisable(true);
				btnRemove.setDisable(true);
				btnRemoveAll.setDisable(true);
			}

		});

		// button add
		btnAdd.setOnAction(ae->{

			mySelectedMusic=lvAvailable.getSelectionModel().getSelectedItem().toString();
			if(arrList.contains(mySelectedMusic)==false) {
				arrList.add(mySelectedMusic);
				lvSelected.getItems().add(arrList.get(counter));
				counter++;
			}

			btnAdd.setDisable(true);

		});
		
		// button Remove
		btnRemove.setOnAction(ae->{
			//listView.getItems().remove(selectedIdx);
			mySelectedMusic=lvSelected.getSelectionModel().getSelectedItem().toString();
			arrList.remove(mySelectedMusic);
			lvSelected.getItems().remove(mySelectedMusic);
			counter--;

			if(counter==0) {
				btnPlay.setDisable(true);
				btnPause.setDisable(true);
				btnStop.setDisable(true);
				btnRemove.setDisable(true);
				btnRemoveAll.setDisable(true);

			}

		});
		
		// button RemoveAll
		btnRemoveAll.setOnAction(ae->{
			arrList.clear();
			lvSelected.getItems().clear();
			counter=0;
			btnPlay.setDisable(true);
			btnPause.setDisable(true);
			btnStop.setDisable(true);
			btnRemoveAll.setDisable(true);
			btnRemove.setDisable(true);
		});

		// button Play
		btnPlay.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				//Boolean isnext="false";
				music(lvAvailable.getSelectionModel().getSelectedItem().toString());
				mediaPlayer.play();


			}
		});
        
		// button Play
		btnPause.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				mediaPlayer.pause();
			}
		});

		// button Stop
		btnStop.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				mediaPlayer.stop();
			}
		});


	}


	// create media player
	/*
     * This method calls the music files and sets up an error handler
     */
	public void music(String s) {
		
		// calls the music files in the music folder
		file = new File("./music/" + s);
		media=null;

		// try and catch error handling
		try {
			if(file.exists()) {
				media = new Media(file.toURI().toURL().toString());
				mediaPlayer = new MediaPlayer(media);


				mediaPlayer.setOnReady(new Runnable() {
					public void run() {
						duration = mediaPlayer.getMedia().getDuration();
						updateValues();
					}
				});

				mediaPlayer.currentTimeProperty().addListener(new InvalidationListener() {
					public void invalidated(Observable ov) {
						updateValues();
					}
				});

				mediaPlayer.setOnEndOfMedia(new Runnable() {
					@Override public void run() {
						mediaPlayer.currentTimeProperty().removeListener(progressChangeListener);
						mediaPlayer.getMedia().getMetadata().removeListener(metadataChangeListener);
						mediaPlayer.stop();
						updateValues();

					}
				});


				// setting value of volume slider
				volumeSlider.setValue(mediaPlayer.getVolume() *100.0);

                // adding listener to value property
				volumeSlider.valueProperty().addListener(new InvalidationListener() {
					public void invalidated(Observable ov) {
						if (volumeSlider.isValueChanging()) {
							mediaPlayer.setVolume(volumeSlider.getValue() / 100.0);
						}
					}
				});
				// adding listener to the value property of the timer slider
				timerSlider.valueProperty().addListener(new InvalidationListener() {
					public void invalidated(Observable ov) {
						// if statement to check condition of the changing value
						if (timerSlider.isValueChanging()) {
							// multiply duration by percentage calculated by slider position
							mediaPlayer.seek(duration.multiply(timerSlider.getValue() / 100.0));
						}
					}
				});

			}
		}
		 //catch
		catch(IOException ioe) {
			// calling tostring method
			ioe.toString();
		}

	}
   
	// public method of updated values
	public void updateValues() {
		if (timerSlider != null && volumeSlider != null) {
			Platform.runLater(new Runnable() {
				@SuppressWarnings("deprecation")
				//run
				public void run() {
					//calling the getCurrent Time method and storing it in the variable currentTime
					Duration currentTime = mediaPlayer.getCurrentTime();
					lblStatus.setText("Status "+formatTime(currentTime, duration));
					//calling the set disable function of the timer slider
					timerSlider.setDisable(duration.isUnknown());

                    // if condition to check if the timer slider is disabled
					if (!timerSlider.isDisabled()
							&& duration.greaterThan(Duration.ZERO)
							&& !timerSlider.isValueChanging()) {
						timerSlider.setValue(currentTime.divide(duration).toMillis()
								* 100.0);
					}
					// if condition to cjheck for the volume slider to enable value changing
					if (!volumeSlider.isValueChanging()) {
						volumeSlider.setValue((int) Math.round(mediaPlayer.getVolume()
								* 100));
					}
				}
			});
		}
	}


    // Format Time
	private static String formatTime(Duration elapsed, Duration duration) {
		int intElapsed = (int) Math.floor(elapsed.toSeconds());
		int elapsedHours = intElapsed / (60 * 60);
		if (elapsedHours > 0) {
			intElapsed -= elapsedHours * 60 * 60;
		}
		int elapsedMinutes = intElapsed / 60;
		int elapsedSeconds = intElapsed - elapsedHours * 60 * 60
				- elapsedMinutes * 60;

		if (duration.greaterThan(Duration.ZERO)) {
			int intDuration = (int) Math.floor(duration.toSeconds());
			int durationHours = intDuration / (60 * 60);
			if (durationHours > 0) {
				intDuration -= durationHours * 60 * 60;
			}
			int durationMinutes = intDuration / 60;
			int durationSeconds = intDuration - durationHours * 60 * 60
					- durationMinutes * 60;
			if (durationHours > 0) {
				return String.format("%d:%02d:%02d/%d:%02d:%02d",
						elapsedHours, elapsedMinutes, elapsedSeconds,
						durationHours, durationMinutes, durationSeconds);
			} else {
				return String.format("%02d:%02d/%02d:%02d",
						elapsedMinutes, elapsedSeconds, durationMinutes,
						durationSeconds);
			}
		} else {
			if (elapsedHours > 0) {
				return String.format("%d:%02d:%02d", elapsedHours,
						elapsedMinutes, elapsedSeconds);
			} else {
				return String.format("%02d:%02d", elapsedMinutes,
						elapsedSeconds);
			}
		}
	}





	// Overriden Start method
	@Override
	public void start(Stage primaryStage) throws Exception {

		//Set the Title 
		primaryStage.setTitle("MP3BlastBox");

		//Set the width and height 
		primaryStage.setWidth(800);
		primaryStage.setHeight(650);

		//Create a layout
		GridPane gpMain=new GridPane();
		VBox vbAvailable=new VBox();
		VBox vbSelected=new VBox();
		VBox vbButtons=new VBox();

		//set the spacing for the vbox
		vbAvailable.setSpacing(30);
		vbButtons.setSpacing(10);
		vbSelected.setSpacing(30);

		//set the padding
		vbAvailable.setPadding(new Insets(10));
		vbButtons.setPadding(new Insets(10));
		//vbSelected.setPadding(new Insets(5));

		//Add the tracks to the ListView.
		lvAvailable.setItems(getMusicFiles());

		//Adding components into the VBoX
		vbAvailable.getChildren().add(lvAvailable);
		vbButtons.getChildren().addAll(btnAdd, btnRemove, btnRemoveAll, btnPlay, btnPause, btnStop, lblVolume, volumeSlider);
		vbSelected.getChildren().addAll(lvSelected,lblStatus, timerSlider);

		//Set the Padding(around the outside margin)
		gpMain.setPadding(new Insets(50));

		//Add the label into the the grid
		gpMain.add(lblAvailableTrack, 0, 0);
		gpMain.add(lblSelectedTrack, 2, 0);

		//Add the vbox container into the grid
		gpMain.add(vbAvailable, 0, 1);
		gpMain.add(vbButtons, 1, 1);
		gpMain.add(vbSelected, 2, 1);

		//Create a Scene 
		Scene s =new Scene(gpMain);


		// Set Styling
		s.getStylesheets().add((new File("style_blastBox.css")).toURI().toURL().toExternalForm());

		//Set the scene
		primaryStage.setScene(s);
		
		 // get Logo
        primaryStage.getIcons().add(new Image("file:icon.png"));

		//Show the Stage
		primaryStage.show();
	}

	public ObservableList<String> getMusicFiles(){

		//ObservableList for the music files.
		ObservableList<String> musicFiles = FXCollections.observableArrayList();

		//Dot forward slash means it is local to the current location.
		File f = new File("./music");

		//Call list() to get a directory listing into the string array.
		fileList = f.list();

		//Add the array of files to the musicFiles ObservableList.
		musicFiles.addAll(fileList);

		//Return the ObservableList. Done.
		return musicFiles;

	}//getMusicFiles()

	public static void main(String[] args) {
		// This will launch the application
		launch();

	}

}
