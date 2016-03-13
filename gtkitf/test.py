#!/usr/bin/python2
import gi
import os
from time import sleep 
import subprocess
gi.require_version('Gtk', '3.0')
from gi.repository import Gtk
from gi.repository import Gdk
from gi.repository import GObject

def getfiles(dirpath):
    a = [s for s in os.listdir(dirpath)
         if os.path.isfile(os.path.join(dirpath, s))]
    a.sort(key=lambda s: os.path.getmtime(os.path.join(dirpath, s)))
    return a


songList = False
treeView = False

class Handler:
    def __init__(self):
        GObject.timeout_add_seconds(1, self.loop)
        self.playProcess = None

    def onDeleteWindow(self, *args):
        Gtk.main_quit(*args)

    def onButtonPressed(self, button):
        print("Hello World!")

    def loop(self):

	files = getfiles("songs")
	if len(files) == 0:
		songList.clear()
		return True 
        songNameExt = subprocess.check_output("ls -lrt songs/ | head -n2 | tail -n1 | sed \"s/ \+/ /g\" | cut -d \" \" -f 9-", stderr=subprocess.STDOUT, shell=True).rstrip()
        
	songNameExt = files[0]
	songName = subprocess.check_output("echo \"" + songNameExt + "\" | cut -d\".\" -f1", stderr=subprocess.STDOUT, shell=True)
 	
	
	songList.clear()
	for f in files:
		duration = subprocess.check_output("mediainfo \"songs/" + f + "\" | grep Duration | tail -n1 | cut -d: -f2 | sed \"s/mn /:/g\" | sed \"s/s//g\"", stderr=subprocess.STDOUT, shell=True).rstrip().lstrip()
		print duration 
		songList.append([f.split(".")[0],duration])
		treeView.show()
   

	print songNameExt 
  	if (len(songNameExt) >= 4):
		exitCode = self.playProcess.poll() if self.playProcess != None else -47
		if (exitCode != None):
		     print "exitCode: " + str(exitCode)
		     print "mpv \"songs/" + songNameExt + "\"" 
		     if ( len(files) == 1 ):
		         sleep(5)
		     self.playProcess = subprocess.Popen("mpv \"songs/" + songNameExt + "\"", shell=True)
		     if(exitCode == 0):
			os.system("rm -f \"songs/" + songNameExt + "\"")	

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
