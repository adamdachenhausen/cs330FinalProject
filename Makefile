# A Makefile for the Java Based Memory Latency Simulator
# Pat Baumgardner, Adam Dachenhausen, Shah Syed
#
# Based on the example Make file found at:
# https://www.cs.swarthmore.edu/~newhall/unixhelp/javamakefiles.html
#
JFLAGS = -g
JC = javac
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = \
        CPU.java \
        HDD.java \
	RAM.java \
	memSim.java \
	dataBlock.java

default: classes

classes: $(CLASSES:.java=.class)

clean:
	$(RM) *.class
