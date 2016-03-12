#!/usr/bin/env python

import pygtk
pygtk.require('2.0')
import gtk

class HelloWorld:

    def hello(self, widget, data=None):
        print "Hello World"

    def delete_event(self, widget, event, data=None):
        print "delete event occurred"

        return False

    def destroy(self, widget, data=None):
        print "destroy signal occurred"
        gtk.main_quit()

    def __init__(self):
        self.window = gtk.Window(gtk.WINDOW_TOPLEVEL)
	self.window.fullscreen()    
        self.window.connect("delete_event", self.delete_event)
        self.window.connect("destroy", self.destroy)
        
	self.window.set_border_width(10)
        
	self.l1 = gtk.Label("Welcome")
	self.l2 = gtk.Label("Pepe")
	self.l3 = gtk.Label("Configuring your car...")

	self.grid = gtk.Grid()	
	self.add(grid)

	self.grid.attach(self.l1)
	self.grid.attach(self.l2)
	self.grid.attach(self.l3)
	
	self.add(self.grid)

	self.l1.show()
	self.l2.show()
	self.l3.show()
        self.window.show()

    def main(self):
        gtk.main()

if __name__ == "__main__":
    hello = HelloWorld()
    hello.main()
