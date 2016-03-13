import gi
import subprocess
gi.require_version('Gtk', '3.0')
from gi.repository import Gtk
from gi.repository import Gdk
from gi.repository import GObject

songList = False
treeView = False

class Handler:
    def __init__(self):
        GObject.timeout_add_seconds(1, self.loop)
        self.lastSong = 27
	self.lastSongPath = ""
	self.playingLastSong = False
        self.playProcess = None

    def onDeleteWindow(self, *args):
        Gtk.main_quit(*args)

    def onButtonPressed(self, button):
        print("Hello World!")

    def loop(self):
        songNameExt = subprocess.check_output("ls -lt songs/ | head -n2 | tail -n1 | sed \"s/ \+/ /g\" | cut -d \" \" -f 9-", stderr=subprocess.STDOUT, shell=True).rstrip()
        songName = subprocess.check_output("echo \"" + songNameExt + "\" | cut -d\".\" -f1", stderr=subprocess.STDOUT, shell=True)
  
  	if (songName != ""):
 		if (self.playProcess == None or (self.playProcess.poll() != None)) and (not self.playingLastSong) and (self.lastSong != 27):
       		     self.playingLastSong = True
                     print "mpv \"songs/" + songNameExt + "\"" 
	     	     self.playProcess = subprocess.Popen("mpv \"songs/" + songNameExt + "\"", shell=True)

		if songName != self.lastSong and songList != None and treeView != None:
		     self.playingLastSong = False
		     self.lastSong = songName
		     self.lastSongPath = "songs/" + songNameExt
		     songList.append([self.lastSong,"5:10"])
		     treeView.show()

	return True

builder = Gtk.Builder()
builder.add_from_file("Pantalla 3.glade")
builder.connect_signals(Handler())

window = builder.get_object("window1")
songList = builder.get_object("liststore2")
treeView = builder.get_object("treeview1")
window.connect("delete-event", Gtk.main_quit)
window.show_all()
window.fullscreen()

Gtk.main()
